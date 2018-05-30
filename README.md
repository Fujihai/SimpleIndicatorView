# SimpleIndicatorView

This is a simple indicator view which you can use in your ViewPager and so on.

Introduction
------------

A simple indicator view bases on custom view.  

Support two types and orientations, three switch modes.

Features
------------

You need to configure attributes as follows in your `layout.xml` file. For example :

```xml
<pers.liufushihai.customview.SimpleIndicatorView
            android:id="@+id/siv_bottom"
            android:layout_width="100dp"
            android:layout_height="45dp"
            android:layout_weight="7"
            app:siv_count="4"
            app:siv_margin="10dp"
            app:siv_orientation="horizontal"
            app:siv_selectedColor="#b3b37c"
            app:siv_size="18dp"
            app:siv_switch_mode="bottom"
            app:siv_type="circle"
            app:siv_unselectedColor="#b0c9c9" />
```

Use it with your ViewPager. Two essential method.

* `setSelectedIndicatorIndex(position,positionOffset)`.
* `setSelectedIndicatorIndex(position)`.

```java
mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
     @Override
     public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
         mSiv.setSelectedIndicatorIndex(position,positionOffset);
     }

     @Override
     public void onPageSelected(int position) {
         mSiv.setSelectedIndicatorIndex(position);
     }

     @Override
     public void onPageScrollStateChanged(int state) {

     }
 });
```

The detail of attributes as follows :  

* `siv_count` : Amounts of indicator layout.
* `siv_margin` : Margin between two indicators.
* `siv_orientation` : Orientation of the whole indicator layout.
* `siv_selectedColor` : Color when a indicator was selected.
* `siv_unselectedColor` : Color when a indicator was unselected.
* `siv_size` : Size of a single indicator .
* `siv_switch_mode` : Effect of a selected indicator switch to other selected indicator.
* `siv_type` : Support circle and square type.

You can change attributes dynamically in your Java code with some methods like these.

* `setType(Type type)`
* `setCount(int count)`
* `setSize(int size)`
* `setMargin(float margin)`
* `setSelectedColor(int selectedColor)`
* `setUnselectedColor(int unselectedColor)`
* `setMode(SwitchMode mode)`
* `setOrientation(Orientation orientation)`

Getting Started
---------------

**Step 1** : Add it in your root `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
            maven { url 'https://jitpack.io' }
    }
}
```

**Step 2** : Add the dependency

```groovy
dependencies {
    implementation 'com.github.liufushihai:SimpleIndicatorView:v1.0.0'
}
```

Screenshot
-----------

![Screenshot](https://github.com/liufushihai/SimpleIndicatorView/blob/master/Sceenshots/SimpleIndicatorView.gif)

Reference
-----------

Thanks to [rhinoSp](https://github.com/rhinoSp), He did a great job in [CircleIndicator](https://github.com/rhinoSp/CircleIndicator).

License
-------

[MIT License](https://github.com/liufushihai/SimpleIndicatorView/blob/master/LICENSE)