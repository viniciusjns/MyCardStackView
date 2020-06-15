package com.example.mycardstackview.cardStackView;

import android.animation.ObjectAnimator;
import android.view.View;

import com.example.mycardstackview.R;

public class AllMoveDownAnimatorAdapter extends AnimatorAdapter {

    public AllMoveDownAnimatorAdapter(CardStackView cardStackView) {
        super(cardStackView);
    }

    private int dp2px(int value) {
        float density = mCardStackView.getResources().getDisplayMetrics().density;
        return (int) (value * density + 0.5f);
    }

    private float getCardFinalY() {
        float paddingTop = dp2px(40);
        float cardHeight = mCardStackView.getResources().getDimension(R.dimen.card_height);
        float showHeight = mCardStackView.getShowHeight();
        float scrollY = mCardStackView.getScrollY();

        return (scrollY + ((showHeight/2) - (cardHeight/2))) - paddingTop;
    }

    protected void itemExpandAnimatorSet(final CardStackView.ViewHolder viewHolder, int position) {
        final View itemView = viewHolder.itemView;
        itemView.clearAnimation();
//        ObjectAnimator oa = ObjectAnimator.ofFloat(itemView, View.Y, itemView.getY(), mCardStackView.getScrollY() + mCardStackView.getPaddingTop());
//        ObjectAnimator oa = ObjectAnimator.ofFloat(itemView, View.Y, itemView.getY(), mCardStackView.getScrollY());

        ObjectAnimator oa = ObjectAnimator.ofFloat(itemView, View.Y, itemView.getY(), getCardFinalY());
        mSet.play(oa);
        int collapseShowItemCount = 0;
        for (int i = 0; i < mCardStackView.getChildCount(); i++) {
            int childTop;
            if (i == mCardStackView.getSelectPosition()) continue;
            final View child = mCardStackView.getChildAt(i);
            child.clearAnimation();
            if (i > mCardStackView.getSelectPosition() && collapseShowItemCount < mCardStackView.getNumBottomShow()) {
                childTop = mCardStackView.getShowHeight() - getCollapseStartTop(collapseShowItemCount) + mCardStackView.getScrollY();
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
                collapseShowItemCount++;
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), mCardStackView.getShowHeight() + mCardStackView.getScrollY());
                mSet.play(oAnim);
            }
        }
    }

    @Override
    protected void itemCollapseAnimatorSet(CardStackView.ViewHolder viewHolder) {
        int childTop = mCardStackView.getPaddingTop();
        for (int i = 0; i < mCardStackView.getChildCount(); i++) {
            View child = mCardStackView.getChildAt(i);
            child.clearAnimation();
            final CardStackView.LayoutParams lp =
                    (CardStackView.LayoutParams) child.getLayoutParams();
            childTop += lp.topMargin;
            if (i != 0) {
                childTop -= mCardStackView.getOverlapGaps() * 2;
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
            }
            childTop += lp.mHeaderHeight;
        }
    }

}
