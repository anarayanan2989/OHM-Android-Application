package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.C0000R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.Snackbar.SnackbarLayout;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatImageHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import com.example.adithya_2.medicareapp.C0211R;
import java.util.List;

@DefaultBehavior(Behavior.class)
public class FloatingActionButton extends VisibilityAwareImageButton {
    private static final String LOG_TAG = "FloatingActionButton";
    private static final int SIZE_MINI = 1;
    private static final int SIZE_NORMAL = 0;
    private ColorStateList mBackgroundTint;
    private Mode mBackgroundTintMode;
    private int mBorderWidth;
    private boolean mCompatPadding;
    private AppCompatImageHelper mImageHelper;
    private int mImagePadding;
    private FloatingActionButtonImpl mImpl;
    private int mRippleColor;
    private final Rect mShadowPadding;
    private int mSize;

    public static abstract class OnVisibilityChangedListener {
        public void onShown(FloatingActionButton fab) {
        }

        public void onHidden(FloatingActionButton fab) {
        }
    }

    /* renamed from: android.support.design.widget.FloatingActionButton.1 */
    class C02641 implements InternalVisibilityChangedListener {
        final /* synthetic */ OnVisibilityChangedListener val$listener;

        C02641(OnVisibilityChangedListener onVisibilityChangedListener) {
            this.val$listener = onVisibilityChangedListener;
        }

        public void onShown() {
            this.val$listener.onShown(FloatingActionButton.this);
        }

        public void onHidden() {
            this.val$listener.onHidden(FloatingActionButton.this);
        }
    }

    public static class Behavior extends android.support.design.widget.CoordinatorLayout.Behavior<FloatingActionButton> {
        private static final boolean SNACKBAR_BEHAVIOR_ENABLED;
        private float mFabTranslationY;
        private ValueAnimatorCompat mFabTranslationYAnimator;
        private Rect mTmpRect;

        /* renamed from: android.support.design.widget.FloatingActionButton.Behavior.1 */
        class C02651 implements AnimatorUpdateListener {
            final /* synthetic */ FloatingActionButton val$fab;

            C02651(FloatingActionButton floatingActionButton) {
                this.val$fab = floatingActionButton;
            }

            public void onAnimationUpdate(ValueAnimatorCompat animator) {
                ViewCompat.setTranslationY(this.val$fab, animator.getAnimatedFloatValue());
            }
        }

        static {
            SNACKBAR_BEHAVIOR_ENABLED = VERSION.SDK_INT >= 11;
        }

        public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            return SNACKBAR_BEHAVIOR_ENABLED && (dependency instanceof SnackbarLayout);
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            if (dependency instanceof SnackbarLayout) {
                updateFabTranslationForSnackbar(parent, child, dependency);
            } else if (dependency instanceof AppBarLayout) {
                updateFabVisibility(parent, (AppBarLayout) dependency, child);
            }
            return false;
        }

        private boolean updateFabVisibility(CoordinatorLayout parent, AppBarLayout appBarLayout, FloatingActionButton child) {
            if (((LayoutParams) child.getLayoutParams()).getAnchorId() != appBarLayout.getId() || child.getUserSetVisibility() != 0) {
                return false;
            }
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect(parent, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                child.hide(null, false);
            } else {
                child.show(null, false);
            }
            return true;
        }

        private void updateFabTranslationForSnackbar(CoordinatorLayout parent, FloatingActionButton fab, View snackbar) {
            float targetTransY = getFabTranslationYForSnackbar(parent, fab);
            if (this.mFabTranslationY != targetTransY) {
                float currentTransY = ViewCompat.getTranslationY(fab);
                if (this.mFabTranslationYAnimator != null && this.mFabTranslationYAnimator.isRunning()) {
                    this.mFabTranslationYAnimator.cancel();
                }
                if (!fab.isShown() || Math.abs(currentTransY - targetTransY) <= ((float) fab.getHeight()) * 0.667f) {
                    ViewCompat.setTranslationY(fab, targetTransY);
                } else {
                    if (this.mFabTranslationYAnimator == null) {
                        this.mFabTranslationYAnimator = ViewUtils.createAnimator();
                        this.mFabTranslationYAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                        this.mFabTranslationYAnimator.setUpdateListener(new C02651(fab));
                    }
                    this.mFabTranslationYAnimator.setFloatValues(currentTransY, targetTransY);
                    this.mFabTranslationYAnimator.start();
                }
                this.mFabTranslationY = targetTransY;
            }
        }

        private float getFabTranslationYForSnackbar(CoordinatorLayout parent, FloatingActionButton fab) {
            float minOffset = 0.0f;
            List<View> dependencies = parent.getDependencies(fab);
            int z = dependencies.size();
            for (int i = 0; i < z; i += FloatingActionButton.SIZE_MINI) {
                View view = (View) dependencies.get(i);
                if ((view instanceof SnackbarLayout) && parent.doViewsOverlap(fab, view)) {
                    minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - ((float) view.getHeight()));
                }
            }
            return minOffset;
        }

        public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
            List<View> dependencies = parent.getDependencies(child);
            int count = dependencies.size();
            for (int i = 0; i < count; i += FloatingActionButton.SIZE_MINI) {
                View dependency = (View) dependencies.get(i);
                if ((dependency instanceof AppBarLayout) && updateFabVisibility(parent, (AppBarLayout) dependency, child)) {
                    break;
                }
            }
            parent.onLayoutChild(child, layoutDirection);
            offsetIfNeeded(parent, child);
            return true;
        }

        private void offsetIfNeeded(CoordinatorLayout parent, FloatingActionButton fab) {
            Rect padding = fab.mShadowPadding;
            if (padding != null && padding.centerX() > 0 && padding.centerY() > 0) {
                LayoutParams lp = (LayoutParams) fab.getLayoutParams();
                int offsetTB = 0;
                int offsetLR = 0;
                if (fab.getRight() >= parent.getWidth() - lp.rightMargin) {
                    offsetLR = padding.right;
                } else if (fab.getLeft() <= lp.leftMargin) {
                    offsetLR = -padding.left;
                }
                if (fab.getBottom() >= parent.getBottom() - lp.bottomMargin) {
                    offsetTB = padding.bottom;
                } else if (fab.getTop() <= lp.topMargin) {
                    offsetTB = -padding.top;
                }
                fab.offsetTopAndBottom(offsetTB);
                fab.offsetLeftAndRight(offsetLR);
            }
        }
    }

    private class ShadowDelegateImpl implements ShadowViewDelegate {
        private ShadowDelegateImpl() {
        }

        public float getRadius() {
            return ((float) FloatingActionButton.this.getSizeDimension()) / 2.0f;
        }

        public void setShadowPadding(int left, int top, int right, int bottom) {
            FloatingActionButton.this.mShadowPadding.set(left, top, right, bottom);
            FloatingActionButton.this.setPadding(FloatingActionButton.this.mImagePadding + left, FloatingActionButton.this.mImagePadding + top, FloatingActionButton.this.mImagePadding + right, FloatingActionButton.this.mImagePadding + bottom);
        }

        public void setBackgroundDrawable(Drawable background) {
            super.setBackgroundDrawable(background);
        }

        public boolean isCompatPaddingEnabled() {
            return FloatingActionButton.this.mCompatPadding;
        }
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShadowPadding = new Rect();
        ThemeUtils.checkAppCompatTheme(context);
        TypedArray a = context.obtainStyledAttributes(attrs, C0000R.styleable.FloatingActionButton, defStyleAttr, C0000R.style.Widget_Design_FloatingActionButton);
        this.mBackgroundTint = a.getColorStateList(C0000R.styleable.FloatingActionButton_backgroundTint);
        this.mBackgroundTintMode = parseTintMode(a.getInt(C0000R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.mRippleColor = a.getColor(C0000R.styleable.FloatingActionButton_rippleColor, 0);
        this.mSize = a.getInt(C0000R.styleable.FloatingActionButton_fabSize, 0);
        this.mBorderWidth = a.getDimensionPixelSize(C0000R.styleable.FloatingActionButton_borderWidth, 0);
        float elevation = a.getDimension(C0000R.styleable.FloatingActionButton_elevation, 0.0f);
        float pressedTranslationZ = a.getDimension(C0000R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        this.mCompatPadding = a.getBoolean(C0000R.styleable.FloatingActionButton_useCompatPadding, false);
        a.recycle();
        this.mImageHelper = new AppCompatImageHelper(this, AppCompatDrawableManager.get());
        this.mImageHelper.loadFromAttributes(attrs, defStyleAttr);
        this.mImagePadding = (getSizeDimension() - ((int) getResources().getDimension(C0000R.dimen.design_fab_image_size))) / 2;
        getImpl().setBackgroundDrawable(this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        getImpl().setElevation(elevation);
        getImpl().setPressedTranslationZ(pressedTranslationZ);
        getImpl().updatePadding();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int preferredSize = getSizeDimension();
        int d = Math.min(resolveAdjustedSize(preferredSize, widthMeasureSpec), resolveAdjustedSize(preferredSize, heightMeasureSpec));
        setMeasuredDimension((this.mShadowPadding.left + d) + this.mShadowPadding.right, (this.mShadowPadding.top + d) + this.mShadowPadding.bottom);
    }

    public void setRippleColor(@ColorInt int color) {
        if (this.mRippleColor != color) {
            this.mRippleColor = color;
            getImpl().setRippleColor(color);
        }
    }

    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.mBackgroundTint;
    }

    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        if (this.mBackgroundTint != tint) {
            this.mBackgroundTint = tint;
            getImpl().setBackgroundTintList(tint);
        }
    }

    @Nullable
    public Mode getBackgroundTintMode() {
        return this.mBackgroundTintMode;
    }

    public void setBackgroundTintMode(@Nullable Mode tintMode) {
        if (this.mBackgroundTintMode != tintMode) {
            this.mBackgroundTintMode = tintMode;
            getImpl().setBackgroundTintMode(tintMode);
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int resid) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setBackgroundColor(int color) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setImageResource(@DrawableRes int resId) {
        this.mImageHelper.setImageResource(resId);
    }

    public void show() {
        show(null);
    }

    public void show(@Nullable OnVisibilityChangedListener listener) {
        show(listener, true);
    }

    private void show(OnVisibilityChangedListener listener, boolean fromUser) {
        getImpl().show(wrapOnVisibilityChangedListener(listener), fromUser);
    }

    public void hide() {
        hide(null);
    }

    public void hide(@Nullable OnVisibilityChangedListener listener) {
        hide(listener, true);
    }

    private void hide(@Nullable OnVisibilityChangedListener listener, boolean fromUser) {
        getImpl().hide(wrapOnVisibilityChangedListener(listener), fromUser);
    }

    public void setUseCompatPadding(boolean useCompatPadding) {
        if (this.mCompatPadding != useCompatPadding) {
            this.mCompatPadding = useCompatPadding;
            getImpl().onCompatShadowChanged();
        }
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    @Nullable
    private InternalVisibilityChangedListener wrapOnVisibilityChangedListener(@Nullable OnVisibilityChangedListener listener) {
        if (listener == null) {
            return null;
        }
        return new C02641(listener);
    }

    final int getSizeDimension() {
        switch (this.mSize) {
            case SIZE_MINI /*1*/:
                return getResources().getDimensionPixelSize(C0000R.dimen.design_fab_size_mini);
            default:
                return getResources().getDimensionPixelSize(C0000R.dimen.design_fab_size_normal);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getImpl().onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getImpl().onDetachedFromWindow();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().onDrawableStateChanged(getDrawableState());
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().jumpDrawableToCurrentState();
    }

    public boolean getContentRect(@NonNull Rect rect) {
        if (!ViewCompat.isLaidOut(this)) {
            return false;
        }
        rect.set(0, 0, getWidth(), getHeight());
        rect.left += this.mShadowPadding.left;
        rect.top += this.mShadowPadding.top;
        rect.right -= this.mShadowPadding.right;
        rect.bottom -= this.mShadowPadding.bottom;
        return true;
    }

    @NonNull
    public Drawable getContentBackground() {
        return getImpl().getContentBackground();
    }

    private static int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case LinearLayoutManager.INVALID_OFFSET /*-2147483648*/:
                return Math.min(desiredSize, specSize);
            case ItemTouchHelper.ACTION_STATE_IDLE /*0*/:
                return desiredSize;
            case 1073741824:
                return specSize;
            default:
                return result;
        }
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                return Mode.SRC_OVER;
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return Mode.SRC_IN;
            case C0211R.styleable.Toolbar_popupTheme /*9*/:
                return Mode.SRC_ATOP;
            case C0211R.styleable.Toolbar_titleMarginEnd /*14*/:
                return Mode.MULTIPLY;
            case C0211R.styleable.Toolbar_titleMarginTop /*15*/:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }

    public float getCompatElevation() {
        return getImpl().getElevation();
    }

    public void setCompatElevation(float elevation) {
        getImpl().setElevation(elevation);
    }

    private FloatingActionButtonImpl getImpl() {
        if (this.mImpl == null) {
            this.mImpl = createImpl();
        }
        return this.mImpl;
    }

    private FloatingActionButtonImpl createImpl() {
        int sdk = VERSION.SDK_INT;
        if (sdk >= 21) {
            return new FloatingActionButtonLollipop(this, new ShadowDelegateImpl());
        }
        if (sdk >= 14) {
            return new FloatingActionButtonIcs(this, new ShadowDelegateImpl());
        }
        return new FloatingActionButtonEclairMr1(this, new ShadowDelegateImpl());
    }
}
