package android.support.v4.media;

import android.os.IBinder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class IMediaBrowserServiceCallbacksAdapterApi21 {
    private Method mAsBinderMethod;
    Object mCallbackObject;
    private Method mOnConnectFailedMethod;
    private Method mOnConnectMethod;
    private Method mOnLoadChildrenMethod;

    static class Stub {
        static Method sAsInterfaceMethod;

        Stub() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r2 = "android.service.media.IMediaBrowserServiceCallbacks$Stub";
            r1 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x0017, NoSuchMethodException -> 0x001c }
            r2 = "asInterface";
            r3 = 1;
            r3 = new java.lang.Class[r3];	 Catch:{ ClassNotFoundException -> 0x0017, NoSuchMethodException -> 0x001c }
            r4 = 0;
            r5 = android.os.IBinder.class;
            r3[r4] = r5;	 Catch:{ ClassNotFoundException -> 0x0017, NoSuchMethodException -> 0x001c }
            r2 = r1.getMethod(r2, r3);	 Catch:{ ClassNotFoundException -> 0x0017, NoSuchMethodException -> 0x001c }
            sAsInterfaceMethod = r2;	 Catch:{ ClassNotFoundException -> 0x0017, NoSuchMethodException -> 0x001c }
        L_0x0016:
            return;
        L_0x0017:
            r0 = move-exception;
        L_0x0018:
            r0.printStackTrace();
            goto L_0x0016;
        L_0x001c:
            r0 = move-exception;
            goto L_0x0018;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.IMediaBrowserServiceCallbacksAdapterApi21.Stub.<clinit>():void");
        }

        static Object asInterface(IBinder binder) {
            ReflectiveOperationException e;
            Object result = null;
            try {
                result = sAsInterfaceMethod.invoke(null, new Object[]{binder});
            } catch (IllegalAccessException e2) {
                e = e2;
                e.printStackTrace();
                return result;
            } catch (InvocationTargetException e3) {
                e = e3;
                e.printStackTrace();
                return result;
            }
            return result;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    IMediaBrowserServiceCallbacksAdapterApi21(java.lang.Object r8) {
        /*
        r7 = this;
        r7.<init>();
        r7.mCallbackObject = r8;
        r3 = "android.service.media.IMediaBrowserServiceCallbacks";
        r2 = java.lang.Class.forName(r3);	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = "android.content.pm.ParceledListSlice";
        r1 = java.lang.Class.forName(r3);	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = "asBinder";
        r4 = 0;
        r4 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = r2.getMethod(r3, r4);	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r7.mAsBinderMethod = r3;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = "onConnect";
        r4 = 3;
        r4 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r5 = 0;
        r6 = java.lang.String.class;
        r4[r5] = r6;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r5 = 1;
        r6 = android.media.session.MediaSession.Token.class;
        r4[r5] = r6;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r5 = 2;
        r6 = android.os.Bundle.class;
        r4[r5] = r6;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = r2.getMethod(r3, r4);	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r7.mOnConnectMethod = r3;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = "onConnectFailed";
        r4 = 0;
        r4 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = r2.getMethod(r3, r4);	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r7.mOnConnectFailedMethod = r3;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = "onLoadChildren";
        r4 = 2;
        r4 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r5 = 0;
        r6 = java.lang.String.class;
        r4[r5] = r6;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r5 = 1;
        r4[r5] = r1;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r3 = r2.getMethod(r3, r4);	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
        r7.mOnLoadChildrenMethod = r3;	 Catch:{ ClassNotFoundException -> 0x0055, NoSuchMethodException -> 0x005a }
    L_0x0054:
        return;
    L_0x0055:
        r0 = move-exception;
    L_0x0056:
        r0.printStackTrace();
        goto L_0x0054;
    L_0x005a:
        r0 = move-exception;
        goto L_0x0056;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.IMediaBrowserServiceCallbacksAdapterApi21.<init>(java.lang.Object):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    android.os.IBinder asBinder() {
        /*
        r6 = this;
        r2 = 0;
        r3 = r6.mAsBinderMethod;	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r4 = r6.mCallbackObject;	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r3 = r3.invoke(r4, r5);	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r0 = r3;
        r0 = (android.os.IBinder) r0;	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r2 = r0;
    L_0x0010:
        return r2;
    L_0x0011:
        r1 = move-exception;
    L_0x0012:
        r1.printStackTrace();
        goto L_0x0010;
    L_0x0016:
        r1 = move-exception;
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.IMediaBrowserServiceCallbacksAdapterApi21.asBinder():android.os.IBinder");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void onConnect(java.lang.String r6, java.lang.Object r7, android.os.Bundle r8) throws android.os.RemoteException {
        /*
        r5 = this;
        r1 = r5.mOnConnectMethod;	 Catch:{ IllegalAccessException -> 0x0014, InvocationTargetException -> 0x0019 }
        r2 = r5.mCallbackObject;	 Catch:{ IllegalAccessException -> 0x0014, InvocationTargetException -> 0x0019 }
        r3 = 3;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalAccessException -> 0x0014, InvocationTargetException -> 0x0019 }
        r4 = 0;
        r3[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0014, InvocationTargetException -> 0x0019 }
        r4 = 1;
        r3[r4] = r7;	 Catch:{ IllegalAccessException -> 0x0014, InvocationTargetException -> 0x0019 }
        r4 = 2;
        r3[r4] = r8;	 Catch:{ IllegalAccessException -> 0x0014, InvocationTargetException -> 0x0019 }
        r1.invoke(r2, r3);	 Catch:{ IllegalAccessException -> 0x0014, InvocationTargetException -> 0x0019 }
    L_0x0013:
        return;
    L_0x0014:
        r0 = move-exception;
    L_0x0015:
        r0.printStackTrace();
        goto L_0x0013;
    L_0x0019:
        r0 = move-exception;
        goto L_0x0015;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.IMediaBrowserServiceCallbacksAdapterApi21.onConnect(java.lang.String, java.lang.Object, android.os.Bundle):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void onConnectFailed() throws android.os.RemoteException {
        /*
        r4 = this;
        r1 = r4.mOnConnectFailedMethod;	 Catch:{ IllegalAccessException -> 0x000b, InvocationTargetException -> 0x0010 }
        r2 = r4.mCallbackObject;	 Catch:{ IllegalAccessException -> 0x000b, InvocationTargetException -> 0x0010 }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalAccessException -> 0x000b, InvocationTargetException -> 0x0010 }
        r1.invoke(r2, r3);	 Catch:{ IllegalAccessException -> 0x000b, InvocationTargetException -> 0x0010 }
    L_0x000a:
        return;
    L_0x000b:
        r0 = move-exception;
    L_0x000c:
        r0.printStackTrace();
        goto L_0x000a;
    L_0x0010:
        r0 = move-exception;
        goto L_0x000c;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.IMediaBrowserServiceCallbacksAdapterApi21.onConnectFailed():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void onLoadChildren(java.lang.String r6, java.lang.Object r7) throws android.os.RemoteException {
        /*
        r5 = this;
        r1 = r5.mOnLoadChildrenMethod;	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r2 = r5.mCallbackObject;	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r4 = 0;
        r3[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r4 = 1;
        r3[r4] = r7;	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
        r1.invoke(r2, r3);	 Catch:{ IllegalAccessException -> 0x0011, InvocationTargetException -> 0x0016 }
    L_0x0010:
        return;
    L_0x0011:
        r0 = move-exception;
    L_0x0012:
        r0.printStackTrace();
        goto L_0x0010;
    L_0x0016:
        r0 = move-exception;
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.IMediaBrowserServiceCallbacksAdapterApi21.onLoadChildren(java.lang.String, java.lang.Object):void");
    }
}
