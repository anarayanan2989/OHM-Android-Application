package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.animation.Interpolator;

class ValueAnimatorCompatImplHoneycombMr1 extends Impl {
    final ValueAnimator mValueAnimator;

    /* renamed from: android.support.design.widget.ValueAnimatorCompatImplHoneycombMr1.1 */
    class C00171 implements AnimatorUpdateListener {
        final /* synthetic */ AnimatorUpdateListenerProxy val$updateListener;

        C00171(AnimatorUpdateListenerProxy animatorUpdateListenerProxy) {
            this.val$updateListener = animatorUpdateListenerProxy;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.val$updateListener.onAnimationUpdate();
        }
    }

    /* renamed from: android.support.design.widget.ValueAnimatorCompatImplHoneycombMr1.2 */
    class C00182 extends AnimatorListenerAdapter {
        final /* synthetic */ AnimatorListenerProxy val$listener;

        C00182(AnimatorListenerProxy animatorListenerProxy) {
            this.val$listener = animatorListenerProxy;
        }

        public void onAnimationStart(Animator animator) {
            this.val$listener.onAnimationStart();
        }

        public void onAnimationEnd(Animator animator) {
            this.val$listener.onAnimationEnd();
        }

        public void onAnimationCancel(Animator animator) {
            this.val$listener.onAnimationCancel();
        }
    }

    ValueAnimatorCompatImplHoneycombMr1() {
        this.mValueAnimator = new ValueAnimator();
    }

    public void start() {
        this.mValueAnimator.start();
    }

    public boolean isRunning() {
        return this.mValueAnimator.isRunning();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mValueAnimator.setInterpolator(interpolator);
    }

    public void setUpdateListener(AnimatorUpdateListenerProxy updateListener) {
        this.mValueAnimator.addUpdateListener(new C00171(updateListener));
    }

    public void setListener(AnimatorListenerProxy listener) {
        this.mValueAnimator.addListener(new C00182(listener));
    }

    public void setIntValues(int from, int to) {
        this.mValueAnimator.setIntValues(new int[]{from, to});
    }

    public int getAnimatedIntValue() {
        return ((Integer) this.mValueAnimator.getAnimatedValue()).intValue();
    }

    public void setFloatValues(float from, float to) {
        this.mValueAnimator.setFloatValues(new float[]{from, to});
    }

    public float getAnimatedFloatValue() {
        return ((Float) this.mValueAnimator.getAnimatedValue()).floatValue();
    }

    public void setDuration(int duration) {
        this.mValueAnimator.setDuration((long) duration);
    }

    public void cancel() {
        this.mValueAnimator.cancel();
    }

    public float getAnimatedFraction() {
        return this.mValueAnimator.getAnimatedFraction();
    }

    public void end() {
        this.mValueAnimator.end();
    }

    public long getDuration() {
        return this.mValueAnimator.getDuration();
    }
}
