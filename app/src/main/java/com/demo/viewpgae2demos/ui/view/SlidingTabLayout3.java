package com.demo.viewpgae2demos.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sliding TabLayout has a strong dependency on ViewPager2
 */
public class SlidingTabLayout3 extends SlidingTabLayoutBase {

  private ViewPager2 mViewPager;
  /**
   * Used to monitor changes in viewpager2 and make changes accordingly
   */
  ViewPager2PageChangeCallBack vpListener = new ViewPager2PageChangeCallBack();

  public SlidingTabLayout3(Context context) {
    this(context, null, 0);
  }

  public SlidingTabLayout3(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SlidingTabLayout3(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void registerVpListener(ViewPager2 vp) {
    if (this.mViewPager != null) {
      this.mViewPager.unregisterOnPageChangeCallback(vpListener);
    }
    this.mViewPager = vp;
    this.mViewPager.registerOnPageChangeCallback(vpListener);
  }

  /**
   *  Associate with ViewPager for situations where you do not want to set title data in the ViewPager adapter
   */
  public void setViewPager(ViewPager2 vp, List<String> titles) {
    if (vp == null || vp.getAdapter() == null) {
      throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
    }

    if (titles == null || titles.size() == 0) {
      throw new IllegalStateException("Titles can not be EMPTY !");
    }

    if (titles.size() != vp.getAdapter().getItemCount()) {
      throw new IllegalStateException("Titles length must be the same as the page count !");
    }

    this.mViewPager = vp;
    mTitles = new ArrayList<>();
    mTitles.addAll(titles);

    registerVpListener(vp);
    notifyDataSetChanged();
  }

  public void setViewPager(ViewPager2 vp, String[] titles) {
    ArrayList<String> titleList = new ArrayList<>();
    Collections.addAll(titleList, titles);
    setViewPager(vp, titleList);
  }

  /**
   * Associate ViewPager for situations where even the adapter does not want to instantiate it on its own
   */
  public void setViewPager(ViewPager2 vp, List<String> titles, FragmentManager fragmentManager, FragmentActivity fa,
                           List<Fragment> fragments) {

    if (vp == null) {
      throw new IllegalStateException("ViewPager can not be NULL !");
    }

    if (titles == null || titles.size() == 0) {
      throw new IllegalStateException("Titles can not be EMPTY !");
    }

    registerVpListener(vp);
    this.mViewPager.setAdapter(new InnerPagerAdapter(fa, fragmentManager, fa.getLifecycle()));
    mTitles = new ArrayList<>();
    mTitles.addAll(titles);
    notifyDataSetChanged();
  }

  public void setViewPager(ViewPager2 vp, List<String> titles, FragmentActivity fa,
                           List<Fragment> fragments) {

    if (vp == null) {
      throw new IllegalStateException("ViewPager can not be NULL !");
    }

    if (titles == null || titles.size() == 0) {
      throw new IllegalStateException("Titles can not be EMPTY !");
    }

    registerVpListener(vp);
    this.mViewPager.setAdapter(new InnerPagerAdapter(fa, fragments, fa.getLifecycle()));
    mTitles = new ArrayList<>();
    mTitles.addAll(titles);
    notifyDataSetChanged();
  }


  public void setViewPager(ViewPager2 vp, String[] titles, FragmentActivity fa,
                           List<Fragment> fragments) {

    ArrayList<String> arrayList = new ArrayList<>();
    Collections.addAll(arrayList, titles);
    setViewPager(vp, arrayList, fa, fragments);
  }

  //setter and getter
  public void setCurrentTab(int currentTab) {
    this.mCurrentTab = currentTab;
    mViewPager.setCurrentItem(currentTab);
  }

  public void setCurrentTab(int currentTab, boolean smoothScroll) {
    this.mCurrentTab = currentTab;
    mViewPager.setCurrentItem(currentTab, smoothScroll);
  }

  @Override
  int getPageCount() {
    RecyclerView.Adapter adapter = mViewPager.getAdapter();
    if (adapter != null) {
      return adapter.getItemCount();
    }
    return 0;
  }

  @Override
  int getCurrentItem() {

    return mViewPager.getCurrentItem();
  }

  @Override
  void setCurrentItem(int position) {
    mViewPager.setCurrentItem(position);
  }

  @Override
  void setCurrentItem(int position, boolean smooth) {
    mViewPager.setCurrentItem(position, smooth);
  }

  /**
   * Used to monitor changes in viewpager2 and make changes accordingly
   */
  private class ViewPager2PageChangeCallBack extends ViewPager2.OnPageChangeCallback {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      /**
       * position:The current position of the View
       * mCurrentPositionOffset:The offset ratio of the current View [0,1)
       */
      mCurrentTab = position;
      mCurrentPositionOffset = positionOffset;
      tabScaleTransformer.onPageScrolled(position, positionOffset, positionOffsetPixels);
      scrollToCurrentTab();
      invalidate();
    }

    @Override
    public void onPageSelected(int position) {
      updateTabSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
  }

  class InnerPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {

    List<Fragment> fragmentArrayList = new ArrayList<>();

    public InnerPagerAdapter(
            FragmentActivity fragmentManager,
            List<Fragment> fragmentArrayList) {
      super(fragmentManager);
      this.fragmentArrayList = fragmentArrayList;
    }

    public InnerPagerAdapter(
            FragmentActivity fragmentManager,
            List<Fragment> fragmentArrayList, Lifecycle lifecycle) {
      super(fragmentManager.getSupportFragmentManager(), lifecycle);

      this.fragmentArrayList = fragmentArrayList;
    }

    public InnerPagerAdapter(FragmentActivity fa, FragmentManager fragmentManager, Lifecycle lifecycle) {
      super(fragmentManager, lifecycle);
      this.fragmentArrayList = fragmentArrayList;

    }

    public Fragment createFragment(int position) {
      return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
      return fragmentArrayList.size();
    }
  }
}
