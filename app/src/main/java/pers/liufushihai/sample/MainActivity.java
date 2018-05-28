package pers.liufushihai.sample;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pers.liufushihai.customview.SimpleIndicatorView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private SimpleIndicatorView mSimpleIndicatorView;
    private List<View> colorLayoutList = new ArrayList<>();
    private ViewPagerAdapter mViewPagerAdapter;

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
                mSimpleIndicatorView.setSelectedIndicatorIndex(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                mSimpleIndicatorView.setSelectedIndicatorIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mSimpleIndicatorView = findViewById(R.id.siv);
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
