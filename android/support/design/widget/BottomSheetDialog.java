package android.support.design.widget;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.C0000R;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class BottomSheetDialog extends AppCompatDialog {
    private BottomSheetCallback mBottomSheetCallback;

    /* renamed from: android.support.design.widget.BottomSheetDialog.1 */
    class C00031 implements OnClickListener {
        C00031() {
        }

        public void onClick(View view) {
            if (BottomSheetDialog.this.isShowing()) {
                BottomSheetDialog.this.cancel();
            }
        }
    }

    /* renamed from: android.support.design.widget.BottomSheetDialog.2 */
    class C02602 extends BottomSheetCallback {
        C02602() {
        }

        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == 5) {
                BottomSheetDialog.this.dismiss();
            }
        }

        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }

    public BottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, getThemeResId(context, theme));
        this.mBottomSheetCallback = new C02602();
        supportRequestWindowFeature(1);
    }

    protected BottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mBottomSheetCallback = new C02602();
        supportRequestWindowFeature(1);
    }

    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(-1, -1);
    }

    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    public void setContentView(View view, LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    private View wrapInBottomSheet(int layoutResId, View view, LayoutParams params) {
        CoordinatorLayout coordinator = (CoordinatorLayout) View.inflate(getContext(), C0000R.layout.design_bottom_sheet_dialog, null);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        FrameLayout bottomSheet = (FrameLayout) coordinator.findViewById(C0000R.id.design_bottom_sheet);
        BottomSheetBehavior.from(bottomSheet).setBottomSheetCallback(this.mBottomSheetCallback);
        if (params == null) {
            bottomSheet.addView(view);
        } else {
            bottomSheet.addView(view, params);
        }
        if (shouldWindowCloseOnTouchOutside()) {
            coordinator.findViewById(C0000R.id.touch_outside).setOnClickListener(new C00031());
        }
        return coordinator;
    }

    private boolean shouldWindowCloseOnTouchOutside() {
        if (VERSION.SDK_INT < 11) {
            return true;
        }
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(16843611, value, true)) {
            return false;
        }
        if (value.data == 0) {
            return false;
        }
        return true;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId != 0) {
            return themeId;
        }
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0000R.attr.bottomSheetDialogTheme, outValue, true)) {
            return outValue.resourceId;
        }
        return C0000R.style.Theme_Design_Light_BottomSheetDialog;
    }
}
