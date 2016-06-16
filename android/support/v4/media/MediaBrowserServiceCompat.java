package android.support.v4.media;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserServiceCompatApi23.ItemCallback;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class MediaBrowserServiceCompat extends Service {
    private static final boolean DBG = false;
    public static final String KEY_MEDIA_ITEM = "media_item";
    private static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    private static final String TAG = "MediaBrowserServiceCompat";
    private final ArrayMap<IBinder, ConnectionRecord> mConnections;
    private final ServiceHandler mHandler;
    private MediaBrowserServiceImpl mImpl;
    Token mSession;

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.1 */
    class C00581 implements Runnable {
        final /* synthetic */ Token val$token;

        C00581(Token token) {
            this.val$token = token;
        }

        public void run() {
            for (IBinder key : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(key);
                try {
                    connection.callbacks.onConnect(connection.root.getRootId(), this.val$token, connection.root.getExtras());
                } catch (RemoteException e) {
                    Log.w(MediaBrowserServiceCompat.TAG, "Connection for " + connection.pkg + " is no longer valid.");
                    MediaBrowserServiceCompat.this.mConnections.remove(key);
                }
            }
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.2 */
    class C00592 implements Runnable {
        final /* synthetic */ Bundle val$options;
        final /* synthetic */ String val$parentId;

        C00592(String str, Bundle bundle) {
            this.val$parentId = str;
            this.val$options = bundle;
        }

        public void run() {
            for (IBinder binder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(binder);
                List<Bundle> optionsList = (List) connection.subscriptions.get(this.val$parentId);
                if (optionsList != null) {
                    for (Bundle bundle : optionsList) {
                        if (MediaBrowserCompatUtils.hasDuplicatedItems(this.val$options, bundle)) {
                            MediaBrowserServiceCompat.this.performLoadChildren(this.val$parentId, connection, bundle);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String rootId, @Nullable Bundle extras) {
            if (rootId == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.mRootId = rootId;
            this.mExtras = extras;
        }

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }

    private class ConnectionRecord {
        ServiceCallbacks callbacks;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Bundle>> subscriptions;

        private ConnectionRecord() {
            this.subscriptions = new HashMap();
        }
    }

    interface MediaBrowserServiceImpl {
        IBinder onBind(Intent intent);

        void onCreate();
    }

    public static class Result<T> {
        private Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendResultCalled;

        Result(Object debug) {
            this.mDebug = debug;
        }

        public void sendResult(T result) {
            if (this.mSendResultCalled) {
                throw new IllegalStateException("sendResult() called twice for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent(result, this.mFlags);
        }

        public void detach() {
            if (this.mDetachCalled) {
                throw new IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
            } else if (this.mSendResultCalled) {
                throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
            } else {
                this.mDetachCalled = true;
            }
        }

        boolean isDone() {
            return (this.mDetachCalled || this.mSendResultCalled) ? true : MediaBrowserServiceCompat.DBG;
        }

        void setFlags(int flags) {
            this.mFlags = flags;
        }

        void onResultSent(T t, int flags) {
        }
    }

    private interface ServiceCallbacks {
        IBinder asBinder();

        void onConnect(String str, Token token, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(String str, List<MediaItem> list, Bundle bundle) throws RemoteException;
    }

    private final class ServiceHandler extends Handler {
        private final ServiceImpl mServiceImpl;

        private ServiceHandler() {
            this.mServiceImpl = new ServiceImpl(null);
        }

        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case MediaBrowserServiceCompat.RESULT_FLAG_OPTION_NOT_HANDLED /*1*/:
                    this.mServiceImpl.connect(data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS), new ServiceCallbacksCompat(msg.replyTo));
                case ItemTouchHelper.DOWN /*2*/:
                    this.mServiceImpl.disconnect(new ServiceCallbacksCompat(msg.replyTo));
                case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                    this.mServiceImpl.addSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), data.getBundle(MediaBrowserProtocol.DATA_OPTIONS), new ServiceCallbacksCompat(msg.replyTo));
                case ItemTouchHelper.LEFT /*4*/:
                    this.mServiceImpl.removeSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), data.getBundle(MediaBrowserProtocol.DATA_OPTIONS), new ServiceCallbacksCompat(msg.replyTo));
                case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                    this.mServiceImpl.getMediaItem(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER));
                case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                    this.mServiceImpl.registerCallbacks(new ServiceCallbacksCompat(msg.replyTo));
                default:
                    Log.w(MediaBrowserServiceCompat.TAG, "Unhandled message: " + msg + "\n  Service version: " + MediaBrowserServiceCompat.RESULT_FLAG_OPTION_NOT_HANDLED + "\n  Client version: " + msg.arg1);
            }
        }

        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            Bundle data = msg.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        public void postOrRun(Runnable r) {
            if (Thread.currentThread() == getLooper().getThread()) {
                r.run();
            } else {
                post(r);
            }
        }

        public ServiceImpl getServiceImpl() {
            return this.mServiceImpl;
        }
    }

    private class ServiceImpl {

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.ServiceImpl.1 */
        class C00601 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ String val$pkg;
            final /* synthetic */ Bundle val$rootHints;
            final /* synthetic */ int val$uid;

            C00601(ServiceCallbacks serviceCallbacks, String str, Bundle bundle, int i) {
                this.val$callbacks = serviceCallbacks;
                this.val$pkg = str;
                this.val$rootHints = bundle;
                this.val$uid = i;
            }

            public void run() {
                IBinder b = this.val$callbacks.asBinder();
                MediaBrowserServiceCompat.this.mConnections.remove(b);
                ConnectionRecord connection = new ConnectionRecord(null);
                connection.pkg = this.val$pkg;
                connection.rootHints = this.val$rootHints;
                connection.callbacks = this.val$callbacks;
                connection.root = MediaBrowserServiceCompat.this.onGetRoot(this.val$pkg, this.val$uid, this.val$rootHints);
                if (connection.root == null) {
                    Log.i(MediaBrowserServiceCompat.TAG, "No root for client " + this.val$pkg + " from service " + getClass().getName());
                    try {
                        this.val$callbacks.onConnectFailed();
                        return;
                    } catch (RemoteException e) {
                        Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + this.val$pkg);
                        return;
                    }
                }
                try {
                    MediaBrowserServiceCompat.this.mConnections.put(b, connection);
                    if (MediaBrowserServiceCompat.this.mSession != null) {
                        this.val$callbacks.onConnect(connection.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connection.root.getExtras());
                    }
                } catch (RemoteException e2) {
                    Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnect() failed. Dropping client. pkg=" + this.val$pkg);
                    MediaBrowserServiceCompat.this.mConnections.remove(b);
                }
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.ServiceImpl.2 */
        class C00612 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;

            C00612(ServiceCallbacks serviceCallbacks) {
                this.val$callbacks = serviceCallbacks;
            }

            public void run() {
                if (((ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.remove(this.val$callbacks.asBinder())) == null) {
                }
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.ServiceImpl.3 */
        class C00623 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ String val$id;
            final /* synthetic */ Bundle val$options;

            C00623(ServiceCallbacks serviceCallbacks, String str, Bundle bundle) {
                this.val$callbacks = serviceCallbacks;
                this.val$id = str;
                this.val$options = bundle;
            }

            public void run() {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(this.val$callbacks.asBinder());
                if (connection == null) {
                    Log.w(MediaBrowserServiceCompat.TAG, "addSubscription for callback that isn't registered id=" + this.val$id);
                } else {
                    MediaBrowserServiceCompat.this.addSubscription(this.val$id, connection, this.val$options);
                }
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.ServiceImpl.4 */
        class C00634 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ String val$id;
            final /* synthetic */ Bundle val$options;

            C00634(ServiceCallbacks serviceCallbacks, String str, Bundle bundle) {
                this.val$callbacks = serviceCallbacks;
                this.val$id = str;
                this.val$options = bundle;
            }

            public void run() {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(this.val$callbacks.asBinder());
                if (connection == null) {
                    Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription for callback that isn't registered id=" + this.val$id);
                } else if (!MediaBrowserServiceCompat.this.removeSubscription(this.val$id, connection, this.val$options)) {
                    Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription called for " + this.val$id + " which is not subscribed");
                }
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.ServiceImpl.5 */
        class C00645 implements Runnable {
            final /* synthetic */ String val$mediaId;
            final /* synthetic */ ResultReceiver val$receiver;

            C00645(String str, ResultReceiver resultReceiver) {
                this.val$mediaId = str;
                this.val$receiver = resultReceiver;
            }

            public void run() {
                MediaBrowserServiceCompat.this.performLoadItem(this.val$mediaId, this.val$receiver);
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.ServiceImpl.6 */
        class C00656 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;

            C00656(ServiceCallbacks serviceCallbacks) {
                this.val$callbacks = serviceCallbacks;
            }

            public void run() {
                IBinder b = this.val$callbacks.asBinder();
                MediaBrowserServiceCompat.this.mConnections.remove(b);
                ConnectionRecord connection = new ConnectionRecord(null);
                connection.callbacks = this.val$callbacks;
                MediaBrowserServiceCompat.this.mConnections.put(b, connection);
            }
        }

        private ServiceImpl() {
        }

        public void connect(String pkg, int uid, Bundle rootHints, ServiceCallbacks callbacks) {
            if (MediaBrowserServiceCompat.this.isValidPackage(pkg, uid)) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new C00601(callbacks, pkg, rootHints, uid));
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + uid + " package=" + pkg);
        }

        public void disconnect(ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new C00612(callbacks));
        }

        public void addSubscription(String id, Bundle options, ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new C00623(callbacks, id, options));
        }

        public void removeSubscription(String id, Bundle options, ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new C00634(callbacks, id, options));
        }

        public void getMediaItem(String mediaId, ResultReceiver receiver) {
            if (!TextUtils.isEmpty(mediaId) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new C00645(mediaId, receiver));
            }
        }

        public void registerCallbacks(ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new C00656(callbacks));
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.3 */
    class C02883 extends Result<List<MediaItem>> {
        final /* synthetic */ ConnectionRecord val$connection;
        final /* synthetic */ Bundle val$options;
        final /* synthetic */ String val$parentId;

        C02883(Object x0, ConnectionRecord connectionRecord, String str, Bundle bundle) {
            this.val$connection = connectionRecord;
            this.val$parentId = str;
            this.val$options = bundle;
            super(x0);
        }

        void onResultSent(List<MediaItem> list, int flag) {
            if (MediaBrowserServiceCompat.this.mConnections.get(this.val$connection.callbacks.asBinder()) == this.val$connection) {
                List<MediaItem> filteredList;
                if ((flag & MediaBrowserServiceCompat.RESULT_FLAG_OPTION_NOT_HANDLED) != 0) {
                    filteredList = MediaBrowserCompatUtils.applyOptions(list, this.val$options);
                } else {
                    filteredList = list;
                }
                try {
                    this.val$connection.callbacks.onLoadChildren(this.val$parentId, filteredList, this.val$options);
                } catch (RemoteException e) {
                    Log.w(MediaBrowserServiceCompat.TAG, "Calling onLoadChildren() failed for id=" + this.val$parentId + " package=" + this.val$connection.pkg);
                }
            }
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.4 */
    class C02894 extends Result<MediaItem> {
        final /* synthetic */ ResultReceiver val$receiver;

        C02894(Object x0, ResultReceiver resultReceiver) {
            this.val$receiver = resultReceiver;
            super(x0);
        }

        void onResultSent(MediaItem item, int flag) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, item);
            this.val$receiver.send(0, bundle);
        }
    }

    class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl {
        private Object mServiceObj;

        MediaBrowserServiceImplApi21() {
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi21.createService();
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj, new ServiceImplApi21());
        }

        public IBinder onBind(Intent intent) {
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, intent);
        }
    }

    class MediaBrowserServiceImplApi23 implements MediaBrowserServiceImpl {
        private Object mServiceObj;

        MediaBrowserServiceImplApi23() {
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi23.createService();
            MediaBrowserServiceCompatApi23.onCreate(this.mServiceObj, new ServiceImplApi23(null));
        }

        public IBinder onBind(Intent intent) {
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, intent);
        }
    }

    class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger;

        MediaBrowserServiceImplBase() {
        }

        public void onCreate() {
            this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
        }

        public IBinder onBind(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.mMessenger.getBinder();
            }
            return null;
        }
    }

    private class ServiceCallbacksApi21 implements ServiceCallbacks {
        final android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks mCallbacks;
        Messenger mMessenger;

        ServiceCallbacksApi21(android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks callbacks) {
            this.mCallbacks = callbacks;
        }

        public IBinder asBinder() {
            return this.mCallbacks.asBinder();
        }

        public void onConnect(String root, Token session, Bundle extras) throws RemoteException {
            if (extras == null) {
                extras = new Bundle();
            }
            this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
            BundleCompat.putBinder(extras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder());
            extras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, MediaBrowserServiceCompat.RESULT_FLAG_OPTION_NOT_HANDLED);
            this.mCallbacks.onConnect(root, session.getToken(), extras);
        }

        public void onConnectFailed() throws RemoteException {
            this.mCallbacks.onConnectFailed();
        }

        public void onLoadChildren(String mediaId, List<MediaItem> list, Bundle options) throws RemoteException {
            List<Parcel> parcelList = null;
            if (list != null) {
                parcelList = new ArrayList();
                for (MediaItem item : list) {
                    Parcel parcel = Parcel.obtain();
                    item.writeToParcel(parcel, 0);
                    parcelList.add(parcel);
                }
            }
            this.mCallbacks.onLoadChildren(mediaId, parcelList);
        }
    }

    private class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger mCallbacks;

        ServiceCallbacksCompat(Messenger callbacks) {
            this.mCallbacks = callbacks;
        }

        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        public void onConnect(String root, Token session, Bundle extras) throws RemoteException {
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, MediaBrowserServiceCompat.RESULT_FLAG_OPTION_NOT_HANDLED);
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, root);
            data.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, session);
            data.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, extras);
            sendRequest(MediaBrowserServiceCompat.RESULT_FLAG_OPTION_NOT_HANDLED, data);
        }

        public void onConnectFailed() throws RemoteException {
            sendRequest(2, null);
        }

        public void onLoadChildren(String mediaId, List<MediaItem> list, Bundle options) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, mediaId);
            data.putBundle(MediaBrowserProtocol.DATA_OPTIONS, options);
            if (list != null) {
                String str = MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST;
                if (list instanceof ArrayList) {
                    list = (ArrayList) list;
                } else {
                    Object list2 = new ArrayList(list);
                }
                data.putParcelableArrayList(str, list);
            }
            sendRequest(3, data);
        }

        private void sendRequest(int what, Bundle data) throws RemoteException {
            Message msg = Message.obtain();
            msg.what = what;
            msg.arg1 = MediaBrowserServiceCompat.RESULT_FLAG_OPTION_NOT_HANDLED;
            msg.setData(data);
            this.mCallbacks.send(msg);
        }
    }

    private class ServiceImplApi21 implements android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceImplApi21 {
        final ServiceImpl mServiceImpl;

        ServiceImplApi21() {
            this.mServiceImpl = MediaBrowserServiceCompat.this.mHandler.getServiceImpl();
        }

        public void connect(String pkg, Bundle rootHints, android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks callbacks) {
            this.mServiceImpl.connect(pkg, Binder.getCallingUid(), rootHints, new ServiceCallbacksApi21(callbacks));
        }

        public void disconnect(android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks callbacks) {
            this.mServiceImpl.disconnect(new ServiceCallbacksApi21(callbacks));
        }

        public void addSubscription(String id, android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks callbacks) {
            this.mServiceImpl.addSubscription(id, null, new ServiceCallbacksApi21(callbacks));
        }

        public void removeSubscription(String id, android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks callbacks) {
            this.mServiceImpl.removeSubscription(id, null, new ServiceCallbacksApi21(callbacks));
        }
    }

    private class ServiceImplApi23 extends ServiceImplApi21 implements android.support.v4.media.MediaBrowserServiceCompatApi23.ServiceImplApi23 {

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat.ServiceImplApi23.1 */
        class C02901 extends ResultReceiver {
            final /* synthetic */ ItemCallback val$cb;

            C02901(Handler x0, ItemCallback itemCallback) {
                this.val$cb = itemCallback;
                super(x0);
            }

            protected void onReceiveResult(int resultCode, Bundle resultData) {
                MediaItem item = (MediaItem) resultData.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
                Parcel itemParcel = null;
                if (item != null) {
                    itemParcel = Parcel.obtain();
                    item.writeToParcel(itemParcel, 0);
                }
                this.val$cb.onItemLoaded(resultCode, resultData, itemParcel);
            }
        }

        private ServiceImplApi23() {
            super();
        }

        public void getMediaItem(String mediaId, ItemCallback cb) {
            this.mServiceImpl.getMediaItem(mediaId, new C02901(MediaBrowserServiceCompat.this.mHandler, cb));
        }
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaItem>> result);

    public MediaBrowserServiceCompat() {
        this.mConnections = new ArrayMap();
        this.mHandler = new ServiceHandler();
    }

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserServiceImplApi23();
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserServiceImplApi21();
        } else {
            this.mImpl = new MediaBrowserServiceImplBase();
        }
        this.mImpl.onCreate();
    }

    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
    }

    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaItem>> result, @NonNull Bundle options) {
        result.setFlags(RESULT_FLAG_OPTION_NOT_HANDLED);
        onLoadChildren(parentId, result);
    }

    public void onLoadItem(String itemId, Result<MediaItem> result) {
        result.sendResult(null);
    }

    public void setSessionToken(Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        } else if (this.mSession != null) {
            throw new IllegalStateException("The session token has already been set.");
        } else {
            this.mSession = token;
            this.mHandler.post(new C00581(token));
        }
    }

    @Nullable
    public Token getSessionToken() {
        return this.mSession;
    }

    public void notifyChildrenChanged(@NonNull String parentId) {
        notifyChildrenChangedInternal(parentId, null);
    }

    public void notifyChildrenChanged(@NonNull String parentId, @NonNull Bundle options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
        notifyChildrenChangedInternal(parentId, options);
    }

    private void notifyChildrenChangedInternal(String parentId, Bundle options) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mHandler.post(new C00592(parentId, options));
    }

    private boolean isValidPackage(String pkg, int uid) {
        if (pkg == null) {
            return DBG;
        }
        String[] packages = getPackageManager().getPackagesForUid(uid);
        int N = packages.length;
        for (int i = 0; i < N; i += RESULT_FLAG_OPTION_NOT_HANDLED) {
            if (packages[i].equals(pkg)) {
                return true;
            }
        }
        return DBG;
    }

    private void addSubscription(String id, ConnectionRecord connection, Bundle options) {
        List<Bundle> optionsList = (List) connection.subscriptions.get(id);
        if (optionsList == null) {
            optionsList = new ArrayList();
        }
        for (Bundle bundle : optionsList) {
            if (MediaBrowserCompatUtils.areSameOptions(options, bundle)) {
                return;
            }
        }
        optionsList.add(options);
        connection.subscriptions.put(id, optionsList);
        performLoadChildren(id, connection, options);
    }

    private boolean removeSubscription(String id, ConnectionRecord connection, Bundle options) {
        boolean removed = DBG;
        List<Bundle> optionsList = (List) connection.subscriptions.get(id);
        if (optionsList != null) {
            for (Bundle bundle : optionsList) {
                if (MediaBrowserCompatUtils.areSameOptions(options, bundle)) {
                    removed = true;
                    optionsList.remove(bundle);
                    break;
                }
            }
            if (optionsList.size() == 0) {
                connection.subscriptions.remove(id);
            }
        }
        return removed;
    }

    private void performLoadChildren(String parentId, ConnectionRecord connection, Bundle options) {
        Result<List<MediaItem>> result = new C02883(parentId, connection, parentId, options);
        if (options == null) {
            onLoadChildren(parentId, result);
        } else {
            onLoadChildren(parentId, result, options);
        }
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connection.pkg + " id=" + parentId);
        }
    }

    private List<MediaItem> applyOptions(List<MediaItem> list, Bundle options) {
        int page = options.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int pageSize = options.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (page == -1 && pageSize == -1) {
            return list;
        }
        int fromIndex = pageSize * (page - 1);
        int toIndex = fromIndex + pageSize;
        if (page < RESULT_FLAG_OPTION_NOT_HANDLED || pageSize < RESULT_FLAG_OPTION_NOT_HANDLED || fromIndex >= list.size()) {
            return Collections.emptyList();
        }
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }

    private void performLoadItem(String itemId, ResultReceiver receiver) {
        Result<MediaItem> result = new C02894(itemId, receiver);
        onLoadItem(itemId, result);
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + itemId);
        }
    }
}
