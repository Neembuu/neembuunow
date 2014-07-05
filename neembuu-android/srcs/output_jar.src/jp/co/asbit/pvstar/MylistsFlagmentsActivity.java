package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

public class MylistsFlagmentsActivity
  extends BaseActivity
{
  public static final String CURRENT_ITEM = "CURRENT_ITEM";
  private static final int FIRST_PAGE;
  private ProgressDialog progressDialog;
  
  protected void dismissProgressDialog()
  {
    if ((this.progressDialog != null) && (this.progressDialog.isShowing()))
    {
      this.progressDialog.dismiss();
      this.progressDialog = null;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903075, 2130903086);
    setTitle(getString(2131296388));
    ViewPager localViewPager = (ViewPager)findViewById(2131492962);
    PagerTabStrip localPagerTabStrip = (PagerTabStrip)findViewById(2131492963);
    localViewPager.setAdapter(new MylistsFragmentPagerAdapter(getSupportFragmentManager(), this.mContext));
    localPagerTabStrip.setDrawFullUnderline(true);
    localPagerTabStrip.setTabIndicatorColor(getResources().getColor(2131165186));
    Intent localIntent = getIntent();
    int i = localIntent.getIntExtra("CURRENT_ITEM", 0);
    localIntent.removeExtra("CURRENT_ITEM");
    localViewPager.setCurrentItem(i);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.progressDialog = null;
  }
  
  protected void showProgressDialog()
  {
    if (this.progressDialog == null)
    {
      this.progressDialog = new ProgressDialog(this);
      this.progressDialog.setMessage(getString(2131296481));
      this.progressDialog.setProgressStyle(0);
      this.progressDialog.show();
    }
  }
  
  private static class MylistsFragmentPagerAdapter
    extends FragmentPagerAdapter
  {
    private Context mContext;
    private int[] pageTitle;
    
    public MylistsFragmentPagerAdapter(FragmentManager paramFragmentManager, Context paramContext)
    {
      super();
      int[] arrayOfInt = new int[2];
      arrayOfInt[0] = 2131296388;
      arrayOfInt[1] = 2131296393;
      this.pageTitle = arrayOfInt;
      this.mContext = paramContext;
    }
    
    public int getCount()
    {
      return this.pageTitle.length;
    }
    
    public Fragment getItem(int paramInt)
    {
      switch (this.pageTitle[paramInt])
      {
      }
      for (Object localObject = new MylistsFlagment();; localObject = new BookmarksFlagment())
      {
        ((Fragment)localObject).setArguments(new Bundle());
        return localObject;
      }
    }
    
    public CharSequence getPageTitle(int paramInt)
    {
      return this.mContext.getString(this.pageTitle[paramInt]);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.MylistsFlagmentsActivity
 * JD-Core Version:    0.7.0.1
 */