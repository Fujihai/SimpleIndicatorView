package pers.liufushihai.sample;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pers.liufushihai.customview.SimpleIndicatorView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;

    private SimpleIndicatorView mSivBottom;
    private SimpleIndicatorView mSivTop;
    private SimpleIndicatorView mSivSingle;

    private SimpleIndicatorView mSivBottomS;
    private SimpleIndicatorView mSivTopS;
    private SimpleIndicatorView mSivSingleS;

    private List<View> colorLayoutList = new ArrayList<>();
    private ViewPagerAdapter mViewPagerAdapter;
    private int colorValueList[] = {
            Color.parseColor("#296bae"),
            Color.parseColor("#39ae29"),
            Color.parseColor("#aeac29"),
            Color.parseColor("#29a7ae")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPagerDatas();
        mViewPagerAdapter = new ViewPagerAdapter(colorLayoutList);

        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mSivBottom.setSelectedColor(colorValueList[position]);
                mSivBottom.setSelectedIndicatorIndex(position,positionOffset);

                mSivTop.setSelectedColor(colorValueList[position]);
                mSivTop.setSelectedIndicatorIndex(position,positionOffset);

                mSivSingle.setSelectedColor(colorValueList[position]);
                mSivSingle.setSelectedIndicatorIndex(position,positionOffset);

                mSivBottomS.setSelectedColor(colorValueList[position]);
                mSivBottomS.setSelectedIndicatorIndex(position,positionOffset);

                mSivTopS.setSelectedColor(colorValueList[position]);
                mSivTopS.setSelectedIndicatorIndex(position,positionOffset);

                mSivSingleS.setSelectedColor(colorValueList[position]);
                mSivSingleS.setSelectedIndicatorIndex(position,positionOffset);

                Log.d(TAG, "onPageScrolled: " + "position = " + position);
            }

            @Override
            public void onPageSelected(int position) {
                mSivBottom.setSelectedIndicatorIndex(position);
                mSivTop.setSelectedIndicatorIndex(position);
                mSivSingle.setSelectedIndicatorIndex(position);

                mSivBottomS.setSelectedIndicatorIndex(position);
                mSivTopS.setSelectedIndicatorIndex(position);
                mSivSingleS.setSelectedIndicatorIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mSivBottom = findViewById(R.id.siv_bottom);
        mSivTop = findViewById(R.id.siv_top);
        mSivSingle = findViewById(R.id.siv_single);
        mSivBottomS = findViewById(R.id.siv_bottom_s);
        mSivTopS = findViewById(R.id.siv_top_s);
        mSivSingleS = findViewById(R.id.siv_single_s);
    }

    /**
     * 初始化ViewPager适配器数据集
     */
    private void initViewPagerDatas(){
        View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.color_layout1,null);
        View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.color_layout2,null);
        View view3 = LayoutInflater.from(MainActivity.this).inflate(R.layout.color_layout3,null);
        View view4 = LayoutInflater.from(MainActivity.this).inflate(R.layout.color_layout4,null);
        colorLayoutList.add(view1);
        colorLayoutList.add(view2);
        colorLayoutList.add(view3);
        colorLayoutList.add(view4);
    }
}
