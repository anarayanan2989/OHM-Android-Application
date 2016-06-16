package android.support.v7.util;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ParallelExecutorCompat;
import android.support.v7.util.ThreadUtil.BackgroundCallback;
import android.support.v7.util.ThreadUtil.MainThreadCallback;
import android.support.v7.util.TileList.Tile;
import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil<T> implements ThreadUtil<T> {

    static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() {
        }

        synchronized SyncQueueItem next() {
            SyncQueueItem syncQueueItem;
            if (this.mRoot == null) {
                syncQueueItem = null;
            } else {
                syncQueueItem = this.mRoot;
                this.mRoot = this.mRoot.next;
            }
            return syncQueueItem;
        }

        synchronized void sendMessageAtFrontOfQueue(SyncQueueItem item) {
            item.next = this.mRoot;
            this.mRoot = item;
        }

        synchronized void sendMessage(SyncQueueItem item) {
            if (this.mRoot == null) {
                this.mRoot = item;
            } else {
                SyncQueueItem last = this.mRoot;
                while (last.next != null) {
                    last = last.next;
                }
                last.next = item;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        synchronized void removeMessages(int r5) {
            /*
            r4 = this;
            monitor-enter(r4);
        L_0x0001:
            r3 = r4.mRoot;	 Catch:{ all -> 0x0019 }
            if (r3 == 0) goto L_0x001c;
        L_0x0005:
            r3 = r4.mRoot;	 Catch:{ all -> 0x0019 }
            r3 = r3.what;	 Catch:{ all -> 0x0019 }
            if (r3 != r5) goto L_0x001c;
        L_0x000b:
            r0 = r4.mRoot;	 Catch:{ all -> 0x0019 }
            r3 = r4.mRoot;	 Catch:{ all -> 0x0019 }
            r3 = r3.next;	 Catch:{ all -> 0x0019 }
            r4.mRoot = r3;	 Catch:{ all -> 0x0019 }
            r0.recycle();	 Catch:{ all -> 0x0019 }
            goto L_0x0001;
        L_0x0019:
            r3 = move-exception;
            monitor-exit(r4);
            throw r3;
        L_0x001c:
            r3 = r4.mRoot;	 Catch:{ all -> 0x0019 }
            if (r3 == 0) goto L_0x003a;
        L_0x0020:
            r2 = r4.mRoot;	 Catch:{ all -> 0x0019 }
            r0 = r2.next;	 Catch:{ all -> 0x0019 }
        L_0x0026:
            if (r0 == 0) goto L_0x003a;
        L_0x0028:
            r1 = r0.next;	 Catch:{ all -> 0x0019 }
            r3 = r0.what;	 Catch:{ all -> 0x0019 }
            if (r3 != r5) goto L_0x0038;
        L_0x0030:
            r2.next = r1;	 Catch:{ all -> 0x0019 }
            r0.recycle();	 Catch:{ all -> 0x0019 }
        L_0x0036:
            r0 = r1;
            goto L_0x0026;
        L_0x0038:
            r2 = r0;
            goto L_0x0036;
        L_0x003a:
            monitor-exit(r4);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.util.MessageThreadUtil.MessageQueue.removeMessages(int):void");
        }
    }

    static class SyncQueueItem {
        private static SyncQueueItem sPool;
        private static final Object sPoolLock;
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        private SyncQueueItem next;
        public int what;

        SyncQueueItem() {
        }

        static {
            sPoolLock = new Object();
        }

        void recycle() {
            this.next = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            synchronized (sPoolLock) {
                if (sPool != null) {
                    this.next = sPool;
                }
                sPool = this;
            }
        }

        static SyncQueueItem obtainMessage(int what, int arg1, int arg2, int arg3, int arg4, int arg5, Object data) {
            SyncQueueItem item;
            synchronized (sPoolLock) {
                if (sPool == null) {
                    item = new SyncQueueItem();
                } else {
                    item = sPool;
                    sPool = sPool.next;
                    item.next = null;
                }
                item.what = what;
                item.arg1 = arg1;
                item.arg2 = arg2;
                item.arg3 = arg3;
                item.arg4 = arg4;
                item.arg5 = arg5;
                item.data = data;
            }
            return item;
        }

        static SyncQueueItem obtainMessage(int what, int arg1, int arg2) {
            return obtainMessage(what, arg1, arg2, 0, 0, 0, null);
        }

        static SyncQueueItem obtainMessage(int what, int arg1, Object data) {
            return obtainMessage(what, arg1, 0, 0, 0, 0, data);
        }
    }

    /* renamed from: android.support.v7.util.MessageThreadUtil.1 */
    class C03171 implements MainThreadCallback<T> {
        private static final int ADD_TILE = 2;
        private static final int REMOVE_TILE = 3;
        private static final int UPDATE_ITEM_COUNT = 1;
        private final Handler mMainThreadHandler;
        private Runnable mMainThreadRunnable;
        private final MessageQueue mQueue;
        final /* synthetic */ MainThreadCallback val$callback;

        /* renamed from: android.support.v7.util.MessageThreadUtil.1.1 */
        class C01591 implements Runnable {
            C01591() {
            }

            public void run() {
                SyncQueueItem msg = C03171.this.mQueue.next();
                while (msg != null) {
                    switch (msg.what) {
                        case C03171.UPDATE_ITEM_COUNT /*1*/:
                            C03171.this.val$callback.updateItemCount(msg.arg1, msg.arg2);
                            break;
                        case C03171.ADD_TILE /*2*/:
                            C03171.this.val$callback.addTile(msg.arg1, (Tile) msg.data);
                            break;
                        case C03171.REMOVE_TILE /*3*/:
                            C03171.this.val$callback.removeTile(msg.arg1, msg.arg2);
                            break;
                        default:
                            Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                            break;
                    }
                    msg = C03171.this.mQueue.next();
                }
            }
        }

        C03171(MainThreadCallback mainThreadCallback) {
            this.val$callback = mainThreadCallback;
            this.mQueue = new MessageQueue();
            this.mMainThreadHandler = new Handler(Looper.getMainLooper());
            this.mMainThreadRunnable = new C01591();
        }

        public void updateItemCount(int generation, int itemCount) {
            sendMessage(SyncQueueItem.obtainMessage((int) UPDATE_ITEM_COUNT, generation, itemCount));
        }

        public void addTile(int generation, Tile<T> tile) {
            sendMessage(SyncQueueItem.obtainMessage((int) ADD_TILE, generation, (Object) tile));
        }

        public void removeTile(int generation, int position) {
            sendMessage(SyncQueueItem.obtainMessage((int) REMOVE_TILE, generation, position));
        }

        private void sendMessage(SyncQueueItem msg) {
            this.mQueue.sendMessage(msg);
            this.mMainThreadHandler.post(this.mMainThreadRunnable);
        }
    }

    /* renamed from: android.support.v7.util.MessageThreadUtil.2 */
    class C03182 implements BackgroundCallback<T> {
        private static final int LOAD_TILE = 3;
        private static final int RECYCLE_TILE = 4;
        private static final int REFRESH = 1;
        private static final int UPDATE_RANGE = 2;
        private Runnable mBackgroundRunnable;
        AtomicBoolean mBackgroundRunning;
        private final Executor mExecutor;
        private final MessageQueue mQueue;
        final /* synthetic */ BackgroundCallback val$callback;

        /* renamed from: android.support.v7.util.MessageThreadUtil.2.1 */
        class C01601 implements Runnable {
            C01601() {
            }

            public void run() {
                while (true) {
                    SyncQueueItem msg = C03182.this.mQueue.next();
                    if (msg != null) {
                        switch (msg.what) {
                            case C03182.REFRESH /*1*/:
                                C03182.this.mQueue.removeMessages(C03182.REFRESH);
                                C03182.this.val$callback.refresh(msg.arg1);
                                break;
                            case C03182.UPDATE_RANGE /*2*/:
                                C03182.this.mQueue.removeMessages(C03182.UPDATE_RANGE);
                                C03182.this.mQueue.removeMessages(C03182.LOAD_TILE);
                                C03182.this.val$callback.updateRange(msg.arg1, msg.arg2, msg.arg3, msg.arg4, msg.arg5);
                                break;
                            case C03182.LOAD_TILE /*3*/:
                                C03182.this.val$callback.loadTile(msg.arg1, msg.arg2);
                                break;
                            case C03182.RECYCLE_TILE /*4*/:
                                C03182.this.val$callback.recycleTile((Tile) msg.data);
                                break;
                            default:
                                Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                                break;
                        }
                    }
                    C03182.this.mBackgroundRunning.set(false);
                    return;
                }
            }
        }

        C03182(BackgroundCallback backgroundCallback) {
            this.val$callback = backgroundCallback;
            this.mQueue = new MessageQueue();
            this.mExecutor = ParallelExecutorCompat.getParallelExecutor();
            this.mBackgroundRunning = new AtomicBoolean(false);
            this.mBackgroundRunnable = new C01601();
        }

        public void refresh(int generation) {
            sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage((int) REFRESH, generation, null));
        }

        public void updateRange(int rangeStart, int rangeEnd, int extRangeStart, int extRangeEnd, int scrollHint) {
            sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(UPDATE_RANGE, rangeStart, rangeEnd, extRangeStart, extRangeEnd, scrollHint, null));
        }

        public void loadTile(int position, int scrollHint) {
            sendMessage(SyncQueueItem.obtainMessage((int) LOAD_TILE, position, scrollHint));
        }

        public void recycleTile(Tile<T> tile) {
            sendMessage(SyncQueueItem.obtainMessage((int) RECYCLE_TILE, 0, (Object) tile));
        }

        private void sendMessage(SyncQueueItem msg) {
            this.mQueue.sendMessage(msg);
            maybeExecuteBackgroundRunnable();
        }

        private void sendMessageAtFrontOfQueue(SyncQueueItem msg) {
            this.mQueue.sendMessageAtFrontOfQueue(msg);
            maybeExecuteBackgroundRunnable();
        }

        private void maybeExecuteBackgroundRunnable() {
            if (this.mBackgroundRunning.compareAndSet(false, true)) {
                this.mExecutor.execute(this.mBackgroundRunnable);
            }
        }
    }

    MessageThreadUtil() {
    }

    public MainThreadCallback<T> getMainThreadProxy(MainThreadCallback<T> callback) {
        return new C03171(callback);
    }

    public BackgroundCallback<T> getBackgroundProxy(BackgroundCallback<T> callback) {
        return new C03182(callback);
    }
}
