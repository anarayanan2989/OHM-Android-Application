package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;

class FloatingActionButtonIcs extends FloatingActionButtonEclairMr1 {
    private boolean mIsHiding;

    /* renamed from: android.support.design.widget.FloatingActionButtonIcs.1 */
    class C00051 extends AnimatorListenerAdapter {
        private boolean mCancelled;
        final /* synthetic */ boolean val$fromUser;
        final /* synthetic */ InternalVisibilityChangedListener val$listener;

        C00051(boolean z, InternalVisibilityChangedListener internalVisibilityChangedListener) {
            this.val$fromUser = z;
            this.val$listener = internalVisibilityChangedListener;
        }

        public void onAnimationStart(Animator animation) {
            FloatingActionButtonIcs.this.mIsHiding = true;
            this.mCancelled = false;
            FloatingActionButtonIcs.this.mView.internalSetVisibility(0, this.val$fromUser);
        }

        public void onAnimationCancel(Animator animation) {
            FloatingActionButtonIcs.this.mIsHiding = false;
            this.mCancelled = true;
        }

        public void onAnimationEnd(Animator animation) {
            FloatingActionButtonIcs.this.mIsHiding = false;
            if (!this.mCancelled) {
                FloatingActionButtonIcs.this.mView.internalSetVisibility(8, this.val$fromUser);
                if (this.val$listener != null) {
                    this.val$listener.onHidden();
                }
            }
        }
    }

    /* renamed from: android.support.design.widget.FloatingActionButtonIcs.2 */
    class C00062 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$fromUser;
        final /* synthetic */ InternalVisibilityChangedListener val$listener;

        C00062(boolean z, InternalVisibilityChangedListener internalVisibilityChangedListener) {
            this.val$fromUser = z;
            this.val$listener = internalVisibilityChangedListener;
        }

        public void onAnimationStart(Animator animation) {
            FloatingActionButtonIcs.this.mView.internalSetVisibility(0, this.val$fromUser);
        }

        public void onAnimationEnd(Animator animation) {
            if (this.val$listener != null) {
                this.val$listener.onShown();
            }
        }
    }

    FloatingActionButtonIcs(VisibilityAwareImageButton view, ShadowViewDelegate shadowViewDelegate) {
        super(view, shadowViewDelegate);
    }

    boolean requirePreDrawListener() {
        return true;
    }

    void onPreDraw() {
        updateFromViewRotation(this.mView.getRotation());
    }

    void hide(@Nullable InternalVisibilityChangedListener listener, boolean fromUser) {
        if (this.mIsHiding || this.mView.getVisibility() != 0) {
            if (listener != null) {
                listener.onHidden();
            }
        } else if (!ViewCompat.isLaidOut(this.mView) || this.mView.isInEditMode()) {
            this.mView.internalSetVisibility(8, fromUser);
            if (listener != null) {
                listener.onHidden();
            }
        } else {
            this.mView.animate().cancel();
            this.mView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener(new C00051(fromUser, listener));
        }
    }

    void show(@Nullable InternalVisibilityChangedListener listener, boolean fromUser) {
        if (!this.mIsHiding && this.mView.getVisibility() == 0) {
            return;
        }
        if (!ViewCompat.isLaidOut(this.mView) || this.mView.isInEditMode()) {
            this.mView.internalSetVisibility(0, fromUser);
            this.mView.setAlpha(1.0f);
            this.mView.setScaleY(1.0f);
            this.mView.setScaleX(1.0f);
            if (listener != null) {
                listener.onShown();
                return;
            }
            return;
        }
        this.mView.animate().cancel();
        if (this.mView.getVisibility() != 0) {
            this.mView.setAlpha(0.0f);
            this.mView.setScaleY(0.0f);
            this.mView.setScaleX(0.0f);
        }
        this.mView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener(new C00062(fromUser, listener));
    }

    private void updateFromViewRotation(float rotation) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setRotation(-rotation);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setRotation(-rotation);
        }
    }
}
