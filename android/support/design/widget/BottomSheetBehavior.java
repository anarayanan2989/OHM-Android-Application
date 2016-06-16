package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.design.C0000R;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View> extends Behavior<V> {
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private int mActivePointerId;
    private BottomSheetCallback mCallback;
    private final Callback mDragCallback;
    private boolean mHideable;
    private boolean mIgnoreEvents;
    private int mInitialY;
    private int mLastNestedScrollDy;
    private int mMaxOffset;
    private float mMaximumVelocity;
    private int mMinOffset;
    private boolean mNestedScrolled;
    private WeakReference<View> mNestedScrollingChildRef;
    private int mParentHeight;
    private int mPeekHeight;
    private int mState;
    private boolean mTouchingScrollingChild;
    private VelocityTracker mVelocityTracker;
    private ViewDragHelper mViewDragHelper;
    private WeakReference<V> mViewRef;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    protected static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR;
        final int state;

        /* renamed from: android.support.design.widget.BottomSheetBehavior.SavedState.1 */
        static class C00021 implements Creator<SavedState> {
            C00021() {
            }

            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        public SavedState(Parcel source) {
            super(source);
            this.state = source.readInt();
        }

        public SavedState(Parcelable superState, int state) {
            super(superState);
            this.state = state;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
        }

        static {
            CREATOR = new C00021();
        }
    }

    private class SettleRunnable implements Runnable {
        private final int mTargetState;
        private final View mView;

        SettleRunnable(View view, int targetState) {
            this.mView = view;
            this.mTargetState = targetState;
        }

        public void run() {
            if (BottomSheetBehavior.this.mViewDragHelper == null || !BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
                BottomSheetBehavior.this.setStateInternal(this.mTargetState);
            } else {
                ViewCompat.postOnAnimation(this.mView, this);
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    /* renamed from: android.support.design.widget.BottomSheetBehavior.1 */
    class C02591 extends Callback {
        C02591() {
        }

        public boolean tryCaptureView(View child, int pointerId) {
            boolean z = true;
            if (BottomSheetBehavior.this.mState == BottomSheetBehavior.STATE_DRAGGING || BottomSheetBehavior.this.mTouchingScrollingChild) {
                return false;
            }
            if (BottomSheetBehavior.this.mState == BottomSheetBehavior.STATE_EXPANDED && BottomSheetBehavior.this.mActivePointerId == pointerId) {
                View scroll = (View) BottomSheetBehavior.this.mNestedScrollingChildRef.get();
                if (scroll != null && ViewCompat.canScrollVertically(scroll, -1)) {
                    return false;
                }
            }
            if (BottomSheetBehavior.this.mViewRef == null || BottomSheetBehavior.this.mViewRef.get() != child) {
                z = false;
            }
            return z;
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            BottomSheetBehavior.this.dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int state) {
            if (state == BottomSheetBehavior.STATE_DRAGGING) {
                BottomSheetBehavior.this.setStateInternal(BottomSheetBehavior.STATE_DRAGGING);
            }
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top;
            int targetState;
            if (yvel < 0.0f) {
                top = BottomSheetBehavior.this.mMinOffset;
                targetState = BottomSheetBehavior.STATE_EXPANDED;
            } else if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(releasedChild, yvel)) {
                top = BottomSheetBehavior.this.mParentHeight;
                targetState = BottomSheetBehavior.STATE_HIDDEN;
            } else if (yvel == 0.0f) {
                int currentTop = releasedChild.getTop();
                if (Math.abs(currentTop - BottomSheetBehavior.this.mMinOffset) < Math.abs(currentTop - BottomSheetBehavior.this.mMaxOffset)) {
                    top = BottomSheetBehavior.this.mMinOffset;
                    targetState = BottomSheetBehavior.STATE_EXPANDED;
                } else {
                    top = BottomSheetBehavior.this.mMaxOffset;
                    targetState = BottomSheetBehavior.STATE_COLLAPSED;
                }
            } else {
                top = BottomSheetBehavior.this.mMaxOffset;
                targetState = BottomSheetBehavior.STATE_COLLAPSED;
            }
            if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                BottomSheetBehavior.this.setStateInternal(BottomSheetBehavior.STATE_SETTLING);
                ViewCompat.postOnAnimation(releasedChild, new SettleRunnable(releasedChild, targetState));
                return;
            }
            BottomSheetBehavior.this.setStateInternal(targetState);
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return MathUtils.constrain(top, BottomSheetBehavior.this.mMinOffset, BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset);
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return child.getLeft();
        }

        public int getViewVerticalDragRange(View child) {
            if (BottomSheetBehavior.this.mHideable) {
                return BottomSheetBehavior.this.mParentHeight - BottomSheetBehavior.this.mMinOffset;
            }
            return BottomSheetBehavior.this.mMaxOffset - BottomSheetBehavior.this.mMinOffset;
        }
    }

    public BottomSheetBehavior() {
        this.mState = STATE_COLLAPSED;
        this.mDragCallback = new C02591();
    }

    public BottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mState = STATE_COLLAPSED;
        this.mDragCallback = new C02591();
        TypedArray a = context.obtainStyledAttributes(attrs, C0000R.styleable.BottomSheetBehavior_Params);
        setPeekHeight(a.getDimensionPixelSize(C0000R.styleable.BottomSheetBehavior_Params_behavior_peekHeight, 0));
        setHideable(a.getBoolean(C0000R.styleable.BottomSheetBehavior_Params_behavior_hideable, false));
        a.recycle();
        this.mMaximumVelocity = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), this.mState);
    }

    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        if (ss.state == STATE_DRAGGING || ss.state == STATE_SETTLING) {
            this.mState = STATE_COLLAPSED;
        } else {
            this.mState = ss.state;
        }
    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (!(this.mState == STATE_DRAGGING || this.mState == STATE_SETTLING)) {
            parent.onLayoutChild(child, layoutDirection);
        }
        this.mParentHeight = parent.getHeight();
        this.mMinOffset = Math.max(0, this.mParentHeight - child.getHeight());
        this.mMaxOffset = Math.max(this.mParentHeight - this.mPeekHeight, this.mMinOffset);
        if (this.mState == STATE_EXPANDED) {
            ViewCompat.offsetTopAndBottom(child, this.mMinOffset);
        } else if (this.mHideable && this.mState == STATE_HIDDEN) {
            ViewCompat.offsetTopAndBottom(child, this.mParentHeight);
        } else if (this.mState == STATE_COLLAPSED) {
            ViewCompat.offsetTopAndBottom(child, this.mMaxOffset);
        }
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(parent, this.mDragCallback);
        }
        this.mViewRef = new WeakReference(child);
        this.mNestedScrollingChildRef = new WeakReference(findScrollingChild(child));
        return true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        boolean z = true;
        if (!child.isShown()) {
            return false;
        }
        int action = MotionEventCompat.getActionMasked(event);
        if (action == 0) {
            reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        switch (action) {
            case ItemTouchHelper.ACTION_STATE_IDLE /*0*/:
                int initialX = (int) event.getX();
                this.mInitialY = (int) event.getY();
                View scroll = (View) this.mNestedScrollingChildRef.get();
                if (scroll != null && parent.isPointInChildBounds(scroll, initialX, this.mInitialY)) {
                    this.mActivePointerId = event.getPointerId(event.getActionIndex());
                    this.mTouchingScrollingChild = true;
                }
                boolean z2 = this.mActivePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.mInitialY);
                this.mIgnoreEvents = z2;
                break;
            case STATE_DRAGGING /*1*/:
            case STATE_EXPANDED /*3*/:
                this.mTouchingScrollingChild = false;
                this.mActivePointerId = -1;
                if (this.mIgnoreEvents) {
                    this.mIgnoreEvents = false;
                    return false;
                }
                break;
        }
        if (!this.mIgnoreEvents && this.mViewDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        scroll = (View) this.mNestedScrollingChildRef.get();
        if (action != STATE_SETTLING || scroll == null || this.mIgnoreEvents || this.mState == STATE_DRAGGING || parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) || Math.abs(((float) this.mInitialY) - event.getY()) <= ((float) this.mViewDragHelper.getTouchSlop())) {
            z = false;
        }
        return z;
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = MotionEventCompat.getActionMasked(event);
        if (this.mState == STATE_DRAGGING && action == 0) {
            return true;
        }
        this.mViewDragHelper.processTouchEvent(event);
        if (action == 0) {
            reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        if (action != STATE_SETTLING || Math.abs(((float) this.mInitialY) - event.getY()) <= ((float) this.mViewDragHelper.getTouchSlop())) {
            return true;
        }
        this.mViewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
        return true;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View directTargetChild, View target, int nestedScrollAxes) {
        this.mLastNestedScrollDy = 0;
        this.mNestedScrolled = false;
        if ((nestedScrollAxes & STATE_SETTLING) != 0) {
            return true;
        }
        return false;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        if (target == ((View) this.mNestedScrollingChildRef.get())) {
            int currentTop = child.getTop();
            int newTop = currentTop - dy;
            if (dy > 0) {
                if (newTop < this.mMinOffset) {
                    consumed[STATE_DRAGGING] = currentTop - this.mMinOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[STATE_DRAGGING]);
                    setStateInternal(STATE_EXPANDED);
                } else {
                    consumed[STATE_DRAGGING] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(STATE_DRAGGING);
                }
            } else if (dy < 0 && !ViewCompat.canScrollVertically(target, -1)) {
                if (newTop <= this.mMaxOffset || this.mHideable) {
                    consumed[STATE_DRAGGING] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(STATE_DRAGGING);
                } else {
                    consumed[STATE_DRAGGING] = currentTop - this.mMaxOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[STATE_DRAGGING]);
                    setStateInternal(STATE_COLLAPSED);
                }
            }
            dispatchOnSlide(child.getTop());
            this.mLastNestedScrollDy = dy;
            this.mNestedScrolled = true;
        }
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        if (child.getTop() == this.mMinOffset) {
            setStateInternal(STATE_EXPANDED);
        } else if (target == this.mNestedScrollingChildRef.get() && this.mNestedScrolled) {
            int top;
            int targetState;
            if (this.mLastNestedScrollDy > 0) {
                top = this.mMinOffset;
                targetState = STATE_EXPANDED;
            } else if (this.mHideable && shouldHide(child, getYVelocity())) {
                top = this.mParentHeight;
                targetState = STATE_HIDDEN;
            } else if (this.mLastNestedScrollDy == 0) {
                int currentTop = child.getTop();
                if (Math.abs(currentTop - this.mMinOffset) < Math.abs(currentTop - this.mMaxOffset)) {
                    top = this.mMinOffset;
                    targetState = STATE_EXPANDED;
                } else {
                    top = this.mMaxOffset;
                    targetState = STATE_COLLAPSED;
                }
            } else {
                top = this.mMaxOffset;
                targetState = STATE_COLLAPSED;
            }
            if (this.mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                setStateInternal(STATE_SETTLING);
                ViewCompat.postOnAnimation(child, new SettleRunnable(child, targetState));
            } else {
                setStateInternal(targetState);
            }
            this.mNestedScrolled = false;
        }
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        return target == this.mNestedScrollingChildRef.get() && (this.mState != STATE_EXPANDED || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY));
    }

    public final void setPeekHeight(int peekHeight) {
        this.mPeekHeight = Math.max(0, peekHeight);
        this.mMaxOffset = this.mParentHeight - peekHeight;
    }

    public final int getPeekHeight() {
        return this.mPeekHeight;
    }

    public void setHideable(boolean hideable) {
        this.mHideable = hideable;
    }

    public boolean isHideable() {
        return this.mHideable;
    }

    public void setBottomSheetCallback(BottomSheetCallback callback) {
        this.mCallback = callback;
    }

    public final void setState(int state) {
        View child = (View) this.mViewRef.get();
        if (child != null) {
            int top;
            if (state == STATE_COLLAPSED) {
                top = this.mMaxOffset;
            } else if (state == STATE_EXPANDED) {
                top = this.mMinOffset;
            } else if (this.mHideable && state == STATE_HIDDEN) {
                top = this.mParentHeight;
            } else {
                throw new IllegalArgumentException("Illegal state argument: " + state);
            }
            setStateInternal(STATE_SETTLING);
            if (this.mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                ViewCompat.postOnAnimation(child, new SettleRunnable(child, state));
            }
        }
    }

    public final int getState() {
        return this.mState;
    }

    private void setStateInternal(int state) {
        if (this.mState != state) {
            this.mState = state;
            View bottomSheet = (View) this.mViewRef.get();
            if (bottomSheet != null && this.mCallback != null) {
                this.mCallback.onStateChanged(bottomSheet, state);
            }
        }
    }

    private void reset() {
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private boolean shouldHide(View child, float yvel) {
        if (child.getTop() >= this.mMaxOffset && Math.abs((((float) child.getTop()) + (HIDE_FRICTION * yvel)) - ((float) this.mMaxOffset)) / ((float) this.mPeekHeight) > HIDE_THRESHOLD) {
            return true;
        }
        return false;
    }

    private View findScrollingChild(View view) {
        if (view instanceof NestedScrollingChild) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i += STATE_DRAGGING) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    private float getYVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        return VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
    }

    private void dispatchOnSlide(int top) {
        View bottomSheet = (View) this.mViewRef.get();
        if (bottomSheet != null && this.mCallback != null) {
            if (top > this.mMaxOffset) {
                this.mCallback.onSlide(bottomSheet, ((float) (this.mMaxOffset - top)) / ((float) this.mPeekHeight));
            } else {
                this.mCallback.onSlide(bottomSheet, ((float) (this.mMaxOffset - top)) / ((float) (this.mMaxOffset - this.mMinOffset)));
            }
        }
    }

    public static <V extends View> BottomSheetBehavior<V> from(V view) {
        LayoutParams params = view.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (behavior instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) behavior;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }
}
