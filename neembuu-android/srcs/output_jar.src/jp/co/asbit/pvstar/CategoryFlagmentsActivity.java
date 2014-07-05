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

public class CategoryFlagmentsActivity
  extends BaseActivity
{
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
    Category localCategory = (Category)getIntent().getSerializableExtra("CATEGORY");
    setTitle(localCategory.getName());
    ViewPager localViewPager = (ViewPager)findViewById(2131492962);
    PagerTabStrip localPagerTabStrip = (PagerTabStrip)findViewById(2131492963);
    localViewPager.setAdapter(new CategoryFragmentPagerAdapter(getSupportFragmentManager(), localCategory, this.mContext));
    localPagerTabStrip.setDrawFullUnderline(true);
    localPagerTabStrip.setTabIndicatorColor(getResources().getColor(2131165186));
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
  
  private static class CategoryFragmentPagerAdapter
    extends FragmentPagerAdapter
  {
    private Category mCategory;
    private Context mContext;
    private int[] pageTitle;
    
    public CategoryFragmentPagerAdapter(FragmentManager paramFragmentManager, Category paramCategory, Context paramContext)
    {
      super();
      int[] arrayOfInt = new int[2];
      arrayOfInt[0] = 2131296482;
      arrayOfInt[1] = 2131296483;
      this.pageTitle = arrayOfInt;
      this.mContext = paramContext;
      this.mCategory = paramCategory;
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
      for (Object localObject = new CategoryIndexFlagment();; localObject = new CategoryRankingFlagment())
      {
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("CATEGORY", this.mCategory);
        ((Fragment)localObject).setArguments(localBundle);
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
 * Qualified Name:     jp.co.asbit.pvstar.CategoryFlagmentsActivity
 * JD-Core Version:    0.7.0.1
 */