package com.jay.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by jay on 2017/6/28 下午1:48
 *
 * @author jay
 * @version 1.0.1
 */
public class RatioViewGroup extends FrameLayout {

    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;

    private final float mWidthRatio;
    private final float mHeightRatio;
    private final int mFixed;

    public RatioViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioViewGroup);
        mWidthRatio = typedArray.getFloat(R.styleable.RatioViewGroup_width_ratio, -1);
        mHeightRatio = typedArray.getFloat(R.styleable.RatioViewGroup_height_ratio, -1);
        mFixed = typedArray.getInt(R.styleable.RatioViewGroup_fixed, WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mWidthRatio != -1 && mHeightRatio != -1) {
            // width/height = mWidthRatio/mHeightRatio
            final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            switch (mFixed) {
                case WIDTH:
                    if (widthMode != MeasureSpec.EXACTLY) {
                        throw new IllegalStateException("layout_width value is not exactly");
                    }
                    final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.round(mHeightRatio / mWidthRatio * widthSize), widthMode);
                    break;
                case HEIGHT:

                    if (heightMode != MeasureSpec.EXACTLY) {
                        throw new IllegalStateException("layout_height value is not exactly");
                    }
                    final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.round(mWidthRatio / mHeightRatio * heightSize), heightMode);

                    break;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}