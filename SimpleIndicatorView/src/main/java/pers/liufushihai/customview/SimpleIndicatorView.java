package pers.liufushihai.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
    private final int DEFAULT_COUNT = 3;
    private final int DEFAULT_SIZE = 15;
    private final int DEFAULT_MARGIN = 10;
    private final int DEFAULT_UNSELECTED_COLOR = Color.GRAY;
    private final int DEFAULT_SELECTED_COLOR = Color.BLACK;
    private final int DEFAULT_MODE = SwitchMode.SINGLE.ordinal();
    private final int DEFAULT_ORIENTATION = Orientation.HORIZONTAL.ordinal();

    /* 属性成员变量 - 类型、个数、大小、间隔、未选中颜色、选中颜色、切换模式、排列方式 */
    private Type mType = Type.values()[DEFAULT_TYPE];
    private int mCount = DEFAULT_COUNT;
    private int mSize = DEFAULT_SIZE;
    private float mMargin = DEFAULT_MARGIN;
    @ColorInt
    private int mSelectedColor = DEFAULT_SELECTED_COLOR;
    @ColorInt
    private int mUnselectedColor = DEFAULT_UNSELECTED_COLOR;
    private SwitchMode mMode = SwitchMode.values()[DEFAULT_MODE];
    private Orientation mOrientation = Orientation.values()[DEFAULT_ORIENTATION];


    public enum SwitchMode {
        BOTTOM,
        TOP,
        SINGLE
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public enum Type {
        CIRCLE,
        SQUARE
    }

    private int mViewWidth;
    private int mViewHeight;
    private Paint mPaint;
    private Path mPath;
    private float mSelectedIndicatorX;
    private float mSelectedIndicatorY;
    private int mCurrentSelectedIndex;

    /**
     * 指示器点构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SimpleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 获取attrs.xml文件中定义的相关属性
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();

        if (attrs == null) {
            return;
        }

        /* 获取属性 - 类型、个数、大小、间隔、选中颜色、未选中颜色、切换模式、水平 */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleIndicatorView);
        int type = typedArray.getInt(R.styleable.SimpleIndicatorView_siv_type, DEFAULT_TYPE);
        mType = Type.values()[type];
        mCount = typedArray.getInt(R.styleable.SimpleIndicatorView_siv_count, DEFAULT_COUNT);
        mSize = typedArray.getInt(R.styleable.SimpleIndicatorView_siv_size, DEFAULT_SIZE);
        mMargin = typedArray.getInt(R.styleable.SimpleIndicatorView_siv_margin, DEFAULT_MARGIN);
        mUnselectedColor = typedArray.getColor(
                R.styleable.SimpleIndicatorView_siv_unselectedColor, DEFAULT_UNSELECTED_COLOR);
        mSelectedColor = typedArray.getColor(
                R.styleable.SimpleIndicatorView_siv_selectedColor, DEFAULT_SELECTED_COLOR);

        int mode = typedArray.getInt(R.styleable.SimpleIndicatorView_siv_switch_mode, DEFAULT_MODE);
        mMode = SwitchMode.values()[mode];

        int orientation = typedArray.getInt(R.styleable.SimpleIndicatorView_siv_orientation,
                DEFAULT_ORIENTATION);
        mOrientation = Orientation.values()[orientation];
        typedArray.recycle();
    }

    /**
     * 测量当前控件大小，给onLayout()提供数值参考
     * 父类传递过来给当前View的一个建议值，
     * 即想把当前View的尺寸设置为宽为widthMeasureSpec，高为heightMeasureSpec
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        /**
         * 如果自定义View在的宽高xml文件中定义为match_parent或具体数值，则模式为EXCATLY
         * 否则为AT_MOST
         */
        if (widthMode == MeasureSpec.EXACTLY) {
            mViewWidth = widthSize;
        } else {
            mViewWidth = getMeasuredWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = heightSize;
        } else {
            mViewHeight = getMeasuredHeight();
        }
        /* 指定最后测量的宽高，重写onMeasure()的最终目的在于此，及告诉系统当前View要绘制的宽高 */
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* canvas.save()与restore()配套处理，类似于图层的操作 */
        canvas.save();
        drawIndicators(canvas);
        drawSelectedIndicator(canvas);
        canvas.restore();
    }

    /**
     * 绘制指示器点
     * 思路：遍历每一个点，若为圆，则根据圆心坐标绘制圆，
     * 若为正方形，则根据左上角点的x、y、size来绘制正方形
     *
     * @param canvas
     */
    private void drawIndicators(Canvas canvas) {
        canvas.save();
        mPaint.setColor(mUnselectedColor);

        if (Type.CIRCLE == mType) {
            float cx = mViewWidth / 2;
            float cy = mViewHeight / 2;
            for (int i = 0; i < mCount; i++) {
                if (Orientation.HORIZONTAL == mOrientation) {
                    canvas.drawCircle(calculateIndicatorX(i), cy, mSize, mPaint);
                } else {
                    canvas.drawCircle(cx, calculateIndicatorY(i), mSize, mPaint);
                }
            }
        } else if (Type.SQUARE == mType) {
            for (int i = 0; i < mCount; i++) {
                if (Orientation.HORIZONTAL == mOrientation) {
                    canvas.drawRect(calculateIndicatorX(i), mViewHeight / 2 - mSize / 2,
                            calculateIndicatorX(i) + mSize, mViewHeight / 2 - mSize / 2 + mSize,
                            mPaint);
                } else {
                    canvas.drawRect(mViewWidth / 2 - mSize / 2, calculateIndicatorY(i),
                            mViewWidth / 2 + mSize / 2, calculateIndicatorY(i) + mSize,
                            mPaint);
                }
            }
        }
        canvas.restore();
    }

    /**
     * 绘制选中的指示器点，作用于特定的切换效果
     *
     * @param canvas
     */
    private void drawSelectedIndicator(Canvas canvas) {
        canvas.save();
        mPaint.setColor(mSelectedColor);

        if (mType == Type.CIRCLE) {
            float cx = mViewWidth / 2;
            float cy = mViewHeight / 2;
            if (SwitchMode.BOTTOM == mMode) {
                //这里的处理就是定义裁剪区域，这个区域与未选中的指示器点的区域一致
                //当后续内容在上面绘制时只会显示相交的区域
                mPath.reset();
                for (int i = 0; i < mCount; i++) {
                    if (Orientation.HORIZONTAL == mOrientation) {
                        mPath.addCircle(calculateIndicatorX(i), cy, mSize, Path.Direction.CW);
                    } else {
                        mPath.addCircle(cx, calculateIndicatorY(i), mSize, Path.Direction.CW);
                    }
                }
                canvas.clipPath(mPath);
            }

            if (0 == mSelectedIndicatorX || 0 == mSelectedIndicatorY) {
                if (Orientation.HORIZONTAL == mOrientation) {
                    mSelectedIndicatorX = calculateIndicatorX(mCurrentSelectedIndex);
                    mSelectedIndicatorY = cy;
                } else {
                    mSelectedIndicatorX = cx;
                    mSelectedIndicatorY = calculateIndicatorY(mCurrentSelectedIndex);
                }
            }
            canvas.drawCircle(mSelectedIndicatorX, mSelectedIndicatorY, mSize, mPaint);
        } else if (mType == Type.SQUARE) {
            if (SwitchMode.BOTTOM == mMode) {
                mPath.reset();
                for (int i = 0; i < mCount; i++) {
                    if (Orientation.HORIZONTAL == mOrientation) {
                        mPath.addRect(calculateIndicatorX(i), mViewHeight / 2 - mSize / 2,
                                calculateIndicatorX(i) + mSize, mViewHeight / 2 - mSize / 2 + mSize,
                                Path.Direction.CW);
                    } else {
                        mPath.addRect(mViewWidth / 2 - mSize / 2, calculateIndicatorY(i),
                                mViewWidth / 2 + mSize / 2, calculateIndicatorY(i) + mSize,
                                Path.Direction.CW);
                    }
                }
                canvas.clipPath(mPath);
            }

            //其他模式，直接进行相应绘制即可
            if (0 == mSelectedIndicatorX || 0 == mSelectedIndicatorY) {
                if (Orientation.HORIZONTAL == mOrientation) {
                    mSelectedIndicatorX = calculateIndicatorX(mCurrentSelectedIndex);
                    mSelectedIndicatorY = mViewHeight / 2 - mSize / 2;
                } else {
                    mSelectedIndicatorX = mViewWidth / 2 - mSize / 2;
                    mSelectedIndicatorY = calculateIndicatorY(mCurrentSelectedIndex);
                }
            }

            canvas.drawRect(mSelectedIndicatorX, mSelectedIndicatorY,
                    mSelectedIndicatorX + mSize, mSelectedIndicatorY + mSize, mPaint);
        }

        canvas.restore();
    }

    /**
     * 计算横向排列时指示器点的x坐标，x是一直在变的
     *
     * @param position
     * @return
     */
    private float calculateIndicatorX(int position) {
        float resX = 0;
        if (Type.CIRCLE == mType) {
            float indicatorRealWidth = mCount * mSize * 2 + (mCount - 1) * mMargin;
            float margin = position * mMargin + position * mSize * 2;
            resX = mViewWidth / 2 - indicatorRealWidth / 2 + mSize + margin;
        } else if (Type.SQUARE == mType) {
            float indicatorRealWidth = mCount * mSize + (mCount - 1) * mMargin;
            float margin = position * mMargin + position * mSize;
            resX = mViewWidth / 2 - indicatorRealWidth / 2 + margin;
        }
        return resX;
    }

    /**
     * 计算纵向排列时指示器点的y坐标，y是一直在变的
     *
     * @param position
     * @return
     */
    private float calculateIndicatorY(int position) {
        float resY = 0;
        if (Type.CIRCLE == mType) {
            float indicatorRealHeight = mCount * mSize * 2 + (mCount - 1) * mMargin;
            float margin = position * mMargin + position * mSize * 2;
            resY = mViewHeight / 2 - indicatorRealHeight / 2 + mSize + margin;
        } else if (Type.SQUARE == mType) {
            float indicatorHeight = mCount * mSize + (mCount - 1) * mMargin;
            float margin = position * mMargin + position * mSize;
            resY = mViewHeight / 2 - indicatorHeight / 2 + margin;
        }
        return resY;
    }

    /**
     * 设置选中的指示器点的下标
     * 最大值为(mCount - 1)
     *
     * @param idx
     */
    public void setSelectedIndicatorIndex(int idx) {
        this.mCurrentSelectedIndex = idx;
        if (Orientation.HORIZONTAL == mOrientation) {
            float currentIndicatorX = calculateIndicatorX(idx);
            if (currentIndicatorX != mSelectedIndicatorX) {
                mSelectedIndicatorX = currentIndicatorX;
                invalidate();
            }
        } else {
            float currentIndicatorY = calculateIndicatorY(idx);
            if (currentIndicatorY != mSelectedIndicatorY) {
                mSelectedIndicatorY = currentIndicatorY;
                invalidate();
            }
        }
    }

    /**
     * 根据传入的偏移量进行绘制，主要用于BOTTOM、TOP模式
     *
     * @param idx    指示器点的下标
     * @param offset ViewPager滑动时的偏移量
     */
    public void setSelectedIndicatorIndex(int idx, float offset) {
        if (0 >= offset) {
            mCurrentSelectedIndex = idx;
        }

        if (SwitchMode.SINGLE == mMode) {
            setSelectedIndicatorIndex(idx);
        } else {
            if (Type.CIRCLE == mType) {
                if (Orientation.HORIZONTAL == mOrientation) {
                    float destIndicatorX = calculateIndicatorX(idx);
                    float towIndicatorDistance = mSize * 2 + mMargin;
                    mSelectedIndicatorX = destIndicatorX + offset * towIndicatorDistance;
                    invalidate();
                } else {
                    float destIndicatorY = calculateIndicatorY(idx);
                    float towIndicatorDistance = mSize * 2 + mMargin;
                    mSelectedIndicatorY = destIndicatorY + offset * towIndicatorDistance;
                    invalidate();
                }
            } else if (Type.SQUARE == mType) {
                if (Orientation.HORIZONTAL == mOrientation) {
                    float destIndicatorX = calculateIndicatorX(idx);
                    float towIndicatorDistance = mSize + mMargin;
                    mSelectedIndicatorY = destIndicatorX + offset * towIndicatorDistance;
                    invalidate();
                } else {
                    float destIndicatorY = calculateIndicatorY(idx);
                    float towIndicatorDistance = mSize + mMargin;
                    mSelectedIndicatorY = destIndicatorY + offset * towIndicatorDistance;
                    invalidate();
                }
            }
        }
    }
}
