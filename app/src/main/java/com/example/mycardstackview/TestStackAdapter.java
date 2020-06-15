package com.example.mycardstackview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mycardstackview.cardStackView.CardStackView;
import com.example.mycardstackview.cardStackView.StackAdapter;


public class TestStackAdapter extends StackAdapter<Integer> {

    private OnAnimationEnd onAnimationEnd;

    public TestStackAdapter(Context context, OnAnimationEnd onAnimationEnd) {
        super(context);
        this.onAnimationEnd = onAnimationEnd;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        ColorItemViewHolder h = (ColorItemViewHolder) holder;
        h.onBind(data, position);
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
        return new ColorItemViewHolder(view, onAnimationEnd);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_card_item;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        private View mLayout;
        private TextView mTextTitle;
        private OnAnimationEnd onAnimationEnd;

        public ColorItemViewHolder(View view, OnAnimationEnd onAnimationEnd) {
            super(view);
            this.onAnimationEnd = onAnimationEnd;
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mTextTitle = view.findViewById(R.id.text_list_card_title);
        }

        @Override
        public void onItemExpand(boolean b) { }

        @Override
        public void onAnimationStateChange(int state, boolean willBeSelect, int position) {
            if (state == CardStackView.ANIMATION_STATE_END && willBeSelect)
                this.onAnimationEnd.endAnimation(itemView, position);
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));
        }

    }

    interface OnAnimationEnd {
        void endAnimation(View view, int position);
    }

}
