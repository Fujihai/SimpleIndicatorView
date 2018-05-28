package pers.liufushihai.sample;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Date        : 2018/5/28
 * Author      : liufushihai
 * Description : ViewPager适配器类
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> datas;

    public ViewPagerAdapter(List<View> datas) {
        this.datas = datas;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(datas.get(position));
        return datas.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(datas.get(position));
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
