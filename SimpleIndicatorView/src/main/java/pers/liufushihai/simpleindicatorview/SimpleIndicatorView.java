package pers.liufushihai.simpleindicatorview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;

/**
 * Date        : 2018/5/25
 * Author      : liufushihai
 * Description : 自定义指示器点类
 */

public class SimpleIndicatorView extends View {

    private static final String TAG = "SimpleIndicatorView";

    /* 相关属性默认值 */
    private final int DEFAULT_TYPE = Type.CIRCLE.ordinal();
    private final int DEFAULT_MODE = Mode.SINGLE.ordinal();
    private final int DEFAULT_ORIENTATION = Orientation.HORIZONTAL.ordinal();
    private final int DEFAULT_COUNT = 3;
    private final int DEFAULT_SIZE = 15;
    private final int DEFAULT_MARGIN = 10;
    private final int DEFAULT_UNSELECTED_COLOR = Color.GRAY;
    private final int DEFAULT_SELECTED_COLOR = Color.BLACK;
    private Orientation mOrientation = Orientation.values()[DEFAULT_ORIENTATION];
    private Mode mMode = Mode.values()[DEFAULT_MODE];
    private int mCount = DEFAULT_COUNT;
    private int mSize = DEFAULT_SIZE;
    private float mMargin = DEFAULT_MARGIN;

    @ColorInt
    private int mUnselectedColor = DEFAULT_UNSELECTED_COLOR;
    @ColorInt
    private int mSelectedColor = DEFAULT_SELECTED_COLOR;

    public enum Mode{
        BOTTOM,
        TOP,
        SINGLE
    }

    public enum Orientation{
        HORIZONTAL,
        VERTICAL
    }

    public enum Type{
        CIRCLE,
        SQUARE
    }

    private int mViewWidth;
    private int mViewHeight;
    private Paint mPaint;
    private Path mPath;
    private float mSelectX;
    private float mSelectY;
    private float mCurrentSelectedPosition;

    /**
     * 指示器点构造函数
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SimpleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    /**
     * 属性的获取
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();

        if(attrs == null){
            return;
        }

        /* 获取属性 */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleIndicatorView);
        int mode = typedArray.getInt(R.styleable.P)
    }
}
