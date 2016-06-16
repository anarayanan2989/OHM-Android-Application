package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private ArrayList<ViewHolder> mAddAnimations;
    private ArrayList<ArrayList<ViewHolder>> mAdditionsList;
    private ArrayList<ViewHolder> mChangeAnimations;
    private ArrayList<ArrayList<ChangeInfo>> mChangesList;
    private ArrayList<ViewHolder> mMoveAnimations;
    private ArrayList<ArrayList<MoveInfo>> mMovesList;
    private ArrayList<ViewHolder> mPendingAdditions;
    private ArrayList<ChangeInfo> mPendingChanges;
    private ArrayList<MoveInfo> mPendingMoves;
    private ArrayList<ViewHolder> mPendingRemovals;
    private ArrayList<ViewHolder> mRemoveAnimations;

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.1 */
    class C01771 implements Runnable {
        final /* synthetic */ ArrayList val$moves;

        C01771(ArrayList arrayList) {
            this.val$moves = arrayList;
        }

        public void run() {
            Iterator i$ = this.val$moves.iterator();
            while (i$.hasNext()) {
                MoveInfo moveInfo = (MoveInfo) i$.next();
                DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
            }
            this.val$moves.clear();
            DefaultItemAnimator.this.mMovesList.remove(this.val$moves);
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.2 */
    class C01782 implements Runnable {
        final /* synthetic */ ArrayList val$changes;

        C01782(ArrayList arrayList) {
            this.val$changes = arrayList;
        }

        public void run() {
            Iterator i$ = this.val$changes.iterator();
            while (i$.hasNext()) {
                DefaultItemAnimator.this.animateChangeImpl((ChangeInfo) i$.next());
            }
            this.val$changes.clear();
            DefaultItemAnimator.this.mChangesList.remove(this.val$changes);
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.3 */
    class C01793 implements Runnable {
        final /* synthetic */ ArrayList val$additions;

        C01793(ArrayList arrayList) {
            this.val$additions = arrayList;
        }

        public void run() {
            Iterator i$ = this.val$additions.iterator();
            while (i$.hasNext()) {
                DefaultItemAnimator.this.animateAddImpl((ViewHolder) i$.next());
            }
            this.val$additions.clear();
            DefaultItemAnimator.this.mAdditionsList.remove(this.val$additions);
        }
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public ViewHolder newHolder;
        public ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public ViewHolder holder;
        public int toX;
        public int toY;

        private MoveInfo(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        private VpaListenerAdapter() {
        }

        public void onAnimationStart(View view) {
        }

        public void onAnimationEnd(View view) {
        }

        public void onAnimationCancel(View view) {
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.4 */
    class C03484 extends VpaListenerAdapter {
        final /* synthetic */ ViewPropertyAnimatorCompat val$animation;
        final /* synthetic */ ViewHolder val$holder;

        C03484(ViewHolder viewHolder, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.val$holder = viewHolder;
            this.val$animation = viewPropertyAnimatorCompat;
            super();
        }

        public void onAnimationStart(View view) {
            DefaultItemAnimator.this.dispatchRemoveStarting(this.val$holder);
        }

        public void onAnimationEnd(View view) {
            this.val$animation.setListener(null);
            ViewCompat.setAlpha(view, 1.0f);
            DefaultItemAnimator.this.dispatchRemoveFinished(this.val$holder);
            DefaultItemAnimator.this.mRemoveAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.5 */
    class C03495 extends VpaListenerAdapter {
        final /* synthetic */ ViewPropertyAnimatorCompat val$animation;
        final /* synthetic */ ViewHolder val$holder;

        C03495(ViewHolder viewHolder, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.val$holder = viewHolder;
            this.val$animation = viewPropertyAnimatorCompat;
            super();
        }

        public void onAnimationStart(View view) {
            DefaultItemAnimator.this.dispatchAddStarting(this.val$holder);
        }

        public void onAnimationCancel(View view) {
            ViewCompat.setAlpha(view, 1.0f);
        }

        public void onAnimationEnd(View view) {
            this.val$animation.setListener(null);
            DefaultItemAnimator.this.dispatchAddFinished(this.val$holder);
            DefaultItemAnimator.this.mAddAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.6 */
    class C03506 extends VpaListenerAdapter {
        final /* synthetic */ ViewPropertyAnimatorCompat val$animation;
        final /* synthetic */ int val$deltaX;
        final /* synthetic */ int val$deltaY;
        final /* synthetic */ ViewHolder val$holder;

        C03506(ViewHolder viewHolder, int i, int i2, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.val$holder = viewHolder;
            this.val$deltaX = i;
            this.val$deltaY = i2;
            this.val$animation = viewPropertyAnimatorCompat;
            super();
        }

        public void onAnimationStart(View view) {
            DefaultItemAnimator.this.dispatchMoveStarting(this.val$holder);
        }

        public void onAnimationCancel(View view) {
            if (this.val$deltaX != 0) {
                ViewCompat.setTranslationX(view, 0.0f);
            }
            if (this.val$deltaY != 0) {
                ViewCompat.setTranslationY(view, 0.0f);
            }
        }

        public void onAnimationEnd(View view) {
            this.val$animation.setListener(null);
            DefaultItemAnimator.this.dispatchMoveFinished(this.val$holder);
            DefaultItemAnimator.this.mMoveAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.7 */
    class C03517 extends VpaListenerAdapter {
        final /* synthetic */ ChangeInfo val$changeInfo;
        final /* synthetic */ ViewPropertyAnimatorCompat val$oldViewAnim;

        C03517(ChangeInfo changeInfo, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.val$changeInfo = changeInfo;
            this.val$oldViewAnim = viewPropertyAnimatorCompat;
            super();
        }

        public void onAnimationStart(View view) {
            DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.oldHolder, true);
        }

        public void onAnimationEnd(View view) {
            this.val$oldViewAnim.setListener(null);
            ViewCompat.setAlpha(view, 1.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
            DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.oldHolder, true);
            DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.oldHolder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator.8 */
    class C03528 extends VpaListenerAdapter {
        final /* synthetic */ ChangeInfo val$changeInfo;
        final /* synthetic */ View val$newView;
        final /* synthetic */ ViewPropertyAnimatorCompat val$newViewAnimation;

        C03528(ChangeInfo changeInfo, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            this.val$changeInfo = changeInfo;
            this.val$newViewAnimation = viewPropertyAnimatorCompat;
            this.val$newView = view;
            super();
        }

        public void onAnimationStart(View view) {
            DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.newHolder, false);
        }

        public void onAnimationEnd(View view) {
            this.val$newViewAnimation.setListener(null);
            ViewCompat.setAlpha(this.val$newView, 1.0f);
            ViewCompat.setTranslationX(this.val$newView, 0.0f);
            ViewCompat.setTranslationY(this.val$newView, 0.0f);
            DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.newHolder, false);
            DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.newHolder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    public DefaultItemAnimator() {
        this.mPendingRemovals = new ArrayList();
        this.mPendingAdditions = new ArrayList();
        this.mPendingMoves = new ArrayList();
        this.mPendingChanges = new ArrayList();
        this.mAdditionsList = new ArrayList();
        this.mMovesList = new ArrayList();
        this.mChangesList = new ArrayList();
        this.mAddAnimations = new ArrayList();
        this.mMoveAnimations = new ArrayList();
        this.mRemoveAnimations = new ArrayList();
        this.mChangeAnimations = new ArrayList();
    }

    public void runPendingAnimations() {
        boolean removalsPending = !this.mPendingRemovals.isEmpty();
        boolean movesPending = !this.mPendingMoves.isEmpty();
        boolean changesPending = !this.mPendingChanges.isEmpty();
        boolean additionsPending = !this.mPendingAdditions.isEmpty();
        if (removalsPending || movesPending || additionsPending || changesPending) {
            Iterator i$ = this.mPendingRemovals.iterator();
            while (i$.hasNext()) {
                animateRemoveImpl((ViewHolder) i$.next());
            }
            this.mPendingRemovals.clear();
            if (movesPending) {
                ArrayList<MoveInfo> moves = new ArrayList();
                moves.addAll(this.mPendingMoves);
                this.mMovesList.add(moves);
                this.mPendingMoves.clear();
                Runnable c01771 = new C01771(moves);
                if (removalsPending) {
                    ViewCompat.postOnAnimationDelayed(((MoveInfo) moves.get(0)).holder.itemView, c01771, getRemoveDuration());
                } else {
                    c01771.run();
                }
            }
            if (changesPending) {
                ArrayList<ChangeInfo> changes = new ArrayList();
                changes.addAll(this.mPendingChanges);
                this.mChangesList.add(changes);
                this.mPendingChanges.clear();
                Runnable changer = new C01782(changes);
                if (removalsPending) {
                    ViewCompat.postOnAnimationDelayed(((ChangeInfo) changes.get(0)).oldHolder.itemView, changer, getRemoveDuration());
                } else {
                    changer.run();
                }
            }
            if (additionsPending) {
                ArrayList<ViewHolder> additions = new ArrayList();
                additions.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(additions);
                this.mPendingAdditions.clear();
                Runnable adder = new C01793(additions);
                if (removalsPending || movesPending || changesPending) {
                    ViewCompat.postOnAnimationDelayed(((ViewHolder) additions.get(0)).itemView, adder, (removalsPending ? getRemoveDuration() : 0) + Math.max(movesPending ? getMoveDuration() : 0, changesPending ? getChangeDuration() : 0));
                } else {
                    adder.run();
                }
            }
        }
    }

    public boolean animateRemove(ViewHolder holder) {
        resetAnimation(holder);
        this.mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(ViewHolder holder) {
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(holder.itemView);
        this.mRemoveAnimations.add(holder);
        animation.setDuration(getRemoveDuration()).alpha(0.0f).setListener(new C03484(holder, animation)).start();
    }

    public boolean animateAdd(ViewHolder holder) {
        resetAnimation(holder);
        ViewCompat.setAlpha(holder.itemView, 0.0f);
        this.mPendingAdditions.add(holder);
        return true;
    }

    private void animateAddImpl(ViewHolder holder) {
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(holder.itemView);
        this.mAddAnimations.add(holder);
        animation.alpha(1.0f).setDuration(getAddDuration()).setListener(new C03495(holder, animation)).start();
    }

    public boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        fromX = (int) (((float) fromX) + ViewCompat.getTranslationX(holder.itemView));
        fromY = (int) (((float) fromY) + ViewCompat.getTranslationY(holder.itemView));
        resetAnimation(holder);
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            ViewCompat.setTranslationX(view, (float) (-deltaX));
        }
        if (deltaY != 0) {
            ViewCompat.setTranslationY(view, (float) (-deltaY));
        }
        this.mPendingMoves.add(new MoveInfo(fromX, fromY, toX, toY, null));
        return true;
    }

    private void animateMoveImpl(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX != 0) {
            ViewCompat.animate(view).translationX(0.0f);
        }
        if (deltaY != 0) {
            ViewCompat.animate(view).translationY(0.0f);
        }
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        this.mMoveAnimations.add(holder);
        animation.setDuration(getMoveDuration()).setListener(new C03506(holder, deltaX, deltaY, animation)).start();
    }

    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        if (oldHolder == newHolder) {
            return animateMove(oldHolder, fromX, fromY, toX, toY);
        }
        float prevTranslationX = ViewCompat.getTranslationX(oldHolder.itemView);
        float prevTranslationY = ViewCompat.getTranslationY(oldHolder.itemView);
        float prevAlpha = ViewCompat.getAlpha(oldHolder.itemView);
        resetAnimation(oldHolder);
        int deltaX = (int) (((float) (toX - fromX)) - prevTranslationX);
        int deltaY = (int) (((float) (toY - fromY)) - prevTranslationY);
        ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX);
        ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY);
        ViewCompat.setAlpha(oldHolder.itemView, prevAlpha);
        if (newHolder != null) {
            resetAnimation(newHolder);
            ViewCompat.setTranslationX(newHolder.itemView, (float) (-deltaX));
            ViewCompat.setTranslationY(newHolder.itemView, (float) (-deltaY));
            ViewCompat.setAlpha(newHolder.itemView, 0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo(newHolder, fromX, fromY, toX, toY, null));
        return true;
    }

    private void animateChangeImpl(ChangeInfo changeInfo) {
        View newView;
        ViewHolder holder = changeInfo.oldHolder;
        View view = holder == null ? null : holder.itemView;
        ViewHolder newHolder = changeInfo.newHolder;
        if (newHolder != null) {
            newView = newHolder.itemView;
        } else {
            newView = null;
        }
        if (view != null) {
            ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(view).setDuration(getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            oldViewAnim.translationX((float) (changeInfo.toX - changeInfo.fromX));
            oldViewAnim.translationY((float) (changeInfo.toY - changeInfo.fromY));
            oldViewAnim.alpha(0.0f).setListener(new C03517(changeInfo, oldViewAnim)).start();
        }
        if (newView != null) {
            ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
            this.mChangeAnimations.add(changeInfo.newHolder);
            newViewAnimation.translationX(0.0f).translationY(0.0f).setDuration(getChangeDuration()).alpha(1.0f).setListener(new C03528(changeInfo, newViewAnimation, newView)).start();
        }
    }

    private void endChangeAnimation(List<ChangeInfo> infoList, ViewHolder item) {
        for (int i = infoList.size() - 1; i >= 0; i--) {
            ChangeInfo changeInfo = (ChangeInfo) infoList.get(i);
            if (endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                infoList.remove(changeInfo);
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, ViewHolder item) {
        boolean oldItem = false;
        if (changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder != item) {
            return false;
        } else {
            changeInfo.oldHolder = null;
            oldItem = true;
        }
        ViewCompat.setAlpha(item.itemView, 1.0f);
        ViewCompat.setTranslationX(item.itemView, 0.0f);
        ViewCompat.setTranslationY(item.itemView, 0.0f);
        dispatchChangeFinished(item, oldItem);
        return true;
    }

    public void endAnimation(ViewHolder item) {
        int i;
        View view = item.itemView;
        ViewCompat.animate(view).cancel();
        for (i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            if (((MoveInfo) this.mPendingMoves.get(i)).holder == item) {
                ViewCompat.setTranslationY(view, 0.0f);
                ViewCompat.setTranslationX(view, 0.0f);
                dispatchMoveFinished(item);
                this.mPendingMoves.remove(i);
            }
        }
        endChangeAnimation(this.mPendingChanges, item);
        if (this.mPendingRemovals.remove(item)) {
            ViewCompat.setAlpha(view, 1.0f);
            dispatchRemoveFinished(item);
        }
        if (this.mPendingAdditions.remove(item)) {
            ViewCompat.setAlpha(view, 1.0f);
            dispatchAddFinished(item);
        }
        for (i = this.mChangesList.size() - 1; i >= 0; i--) {
            ArrayList<ChangeInfo> changes = (ArrayList) this.mChangesList.get(i);
            endChangeAnimation(changes, item);
            if (changes.isEmpty()) {
                this.mChangesList.remove(i);
            }
        }
        for (i = this.mMovesList.size() - 1; i >= 0; i--) {
            ArrayList<MoveInfo> moves = (ArrayList) this.mMovesList.get(i);
            int j = moves.size() - 1;
            while (j >= 0) {
                if (((MoveInfo) moves.get(j)).holder == item) {
                    ViewCompat.setTranslationY(view, 0.0f);
                    ViewCompat.setTranslationX(view, 0.0f);
                    dispatchMoveFinished(item);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(i);
                    }
                } else {
                    j--;
                }
            }
        }
        for (i = this.mAdditionsList.size() - 1; i >= 0; i--) {
            ArrayList<ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i);
            if (additions.remove(item)) {
                ViewCompat.setAlpha(view, 1.0f);
                dispatchAddFinished(item);
                if (additions.isEmpty()) {
                    this.mAdditionsList.remove(i);
                }
            }
        }
        if (this.mRemoveAnimations.remove(item)) {
        }
        if (this.mAddAnimations.remove(item)) {
        }
        if (this.mChangeAnimations.remove(item)) {
        }
        if (this.mMoveAnimations.remove(item)) {
            dispatchFinishedWhenDone();
        } else {
            dispatchFinishedWhenDone();
        }
    }

    private void resetAnimation(ViewHolder holder) {
        AnimatorCompatHelper.clearInterpolator(holder.itemView);
        endAnimation(holder);
    }

    public boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimations() {
        int i;
        for (i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            MoveInfo item = (MoveInfo) this.mPendingMoves.get(i);
            View view = item.holder.itemView;
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            dispatchMoveFinished(item.holder);
            this.mPendingMoves.remove(i);
        }
        for (i = this.mPendingRemovals.size() - 1; i >= 0; i--) {
            dispatchRemoveFinished((ViewHolder) this.mPendingRemovals.get(i));
            this.mPendingRemovals.remove(i);
        }
        for (i = this.mPendingAdditions.size() - 1; i >= 0; i--) {
            ViewHolder item2 = (ViewHolder) this.mPendingAdditions.get(i);
            ViewCompat.setAlpha(item2.itemView, 1.0f);
            dispatchAddFinished(item2);
            this.mPendingAdditions.remove(i);
        }
        for (i = this.mPendingChanges.size() - 1; i >= 0; i--) {
            endChangeAnimationIfNecessary((ChangeInfo) this.mPendingChanges.get(i));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int j;
            for (i = this.mMovesList.size() - 1; i >= 0; i--) {
                ArrayList<MoveInfo> moves = (ArrayList) this.mMovesList.get(i);
                for (j = moves.size() - 1; j >= 0; j--) {
                    MoveInfo moveInfo = (MoveInfo) moves.get(j);
                    view = moveInfo.holder.itemView;
                    ViewCompat.setTranslationY(view, 0.0f);
                    ViewCompat.setTranslationX(view, 0.0f);
                    dispatchMoveFinished(moveInfo.holder);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(moves);
                    }
                }
            }
            for (i = this.mAdditionsList.size() - 1; i >= 0; i--) {
                ArrayList<ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i);
                for (j = additions.size() - 1; j >= 0; j--) {
                    item2 = (ViewHolder) additions.get(j);
                    ViewCompat.setAlpha(item2.itemView, 1.0f);
                    dispatchAddFinished(item2);
                    additions.remove(j);
                    if (additions.isEmpty()) {
                        this.mAdditionsList.remove(additions);
                    }
                }
            }
            for (i = this.mChangesList.size() - 1; i >= 0; i--) {
                ArrayList<ChangeInfo> changes = (ArrayList) this.mChangesList.get(i);
                for (j = changes.size() - 1; j >= 0; j--) {
                    endChangeAnimationIfNecessary((ChangeInfo) changes.get(j));
                    if (changes.isEmpty()) {
                        this.mChangesList.remove(changes);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    void cancelAll(List<ViewHolder> viewHolders) {
        for (int i = viewHolders.size() - 1; i >= 0; i--) {
            ViewCompat.animate(((ViewHolder) viewHolders.get(i)).itemView).cancel();
        }
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> payloads) {
        return !payloads.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, payloads);
    }
}
