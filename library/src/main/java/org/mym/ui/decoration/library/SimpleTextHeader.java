package org.mym.ui.decoration.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 这个类可以用于创建简单的 TextView 显示类型的 Header。
 *
 * Created by muyangmin on Dec 29, 2017.
 */
public abstract class SimpleTextHeader implements StickyHeaderAdapter<SimpleHolder> {

    @ColorInt
    private int mThemeBackgroundColor = -1;
    @ColorInt
    private int mThemeTextColor = -1;

    /**
     * 默认会创建一个 TextView, 其水平 padding 为10dp，竖直 padding 为 8dp，
     * 使用 {@code colorPrimary} 来作为背景， {@code textColorPrimary} 为文字颜色。
     */
    @SuppressLint("ResourceType")
    protected TextView createTextView(Context context) {
        TextView textView = new TextView(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int verticalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8F, textView.getResources().getDisplayMetrics());

        int horizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10F, textView.getResources().getDisplayMetrics());

        textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);

        textView.setLayoutParams(lp);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (mThemeBackgroundColor == -1 || mThemeTextColor == -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int[] attrs = {android.R.attr.colorPrimary, android.R.attr.textColorPrimary};
                TypedArray array = context.obtainStyledAttributes(attrs);
                mThemeBackgroundColor = array.getColor(0, Color.GRAY);
                mThemeTextColor = array.getColor(1, Color.BLACK);
                array.recycle();
            } else {
                mThemeBackgroundColor = Color.GRAY;
                mThemeTextColor = Color.BLACK;
            }
        }
        textView.setBackgroundColor(mThemeBackgroundColor);
        textView.setTextColor(mThemeTextColor);
        return textView;
    }

    /**
     * 为 Header 指定要展示的文本。
     *
     * @param childPos item 在数据集中的位置
     */
    protected abstract CharSequence getHeaderContent(int childPos);

    @NonNull
    @Override
    public SimpleHolder onCreateHeaderViewHolder(ViewGroup parent) {
        TextView textView = createTextView(parent.getContext());
        return new SimpleHolder(textView);
    }

    @Override
    public void onBindHeaderViewHolder(@NonNull SimpleHolder holder, int childAdapterPosition) {
        ((TextView) holder.itemView).setText(getHeaderContent(childAdapterPosition));
    }
}

/*package*/ class SimpleHolder extends RecyclerView.ViewHolder {
    SimpleHolder(View itemView) {
        super(itemView);
    }
}