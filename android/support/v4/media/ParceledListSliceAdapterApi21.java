package android.support.v4.media;

import android.media.browse.MediaBrowser.MediaItem;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    ParceledListSliceAdapterApi21() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r2 = "android.content.pm.ParceledListSlice";
        r1 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        r2 = 1;
        r2 = new java.lang.Class[r2];	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        r3 = 0;
        r4 = java.util.List.class;
        r2[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        r2 = r1.getConstructor(r2);	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        sConstructor = r2;	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
    L_0x0014:
        return;
    L_0x0015:
        r0 = move-exception;
    L_0x0016:
        r0.printStackTrace();
        goto L_0x0014;
    L_0x001a:
        r0 = move-exception;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.ParceledListSliceAdapterApi21.<clinit>():void");
    }

    static Object newInstance(List<MediaItem> itemList) {
        ReflectiveOperationException e;
        Object result = null;
        try {
            result = sConstructor.newInstance(new Object[]{itemList});
        } catch (InstantiationException e2) {
            e = e2;
            e.printStackTrace();
            return result;
        } catch (IllegalAccessException e3) {
            e = e3;
            e.printStackTrace();
            return result;
        } catch (InvocationTargetException e4) {
            e = e4;
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
