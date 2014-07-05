package jp.adlantis.android.mediation;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;

public class AdMediationManager
{
  private static final String LOG_TAG = "AdMediationManager";
  private WeakReference<Activity> activityRef;
  private LinkedList<AdMediationNetworkParameters> networkParametersList;
  private WeakReference<ViewGroup> parentViewRef;
  
  public static AdMediationManager getInstance()
  {
    return AdMediationManagerHolder.INSTANCE;
  }
  
  public void destroy()
  {
    ViewGroup localViewGroup = getParentView();
    if (localViewGroup != null) {
      localViewGroup.removeAllViews();
    }
    if (this.networkParametersList != null) {}
    synchronized (this.networkParametersList)
    {
      this.networkParametersList.clear();
      this.activityRef = null;
      this.parentViewRef = null;
      Log.d("AdMediationManager", "destroy mediation.");
      return;
    }
  }
  
  public Activity getActivity()
  {
    if (this.activityRef == null) {}
    for (Activity localActivity = null;; localActivity = (Activity)this.activityRef.get()) {
      return localActivity;
    }
  }
  
  public ViewGroup getParentView()
  {
    if (this.parentViewRef == null) {}
    for (ViewGroup localViewGroup = null;; localViewGroup = (ViewGroup)this.parentViewRef.get()) {
      return localViewGroup;
    }
  }
  
  public void nextNetwork()
  {
    Log.d("AdMediationManager", "go to next network.");
    boolean bool = false;
    while ((!bool) && (this.networkParametersList != null)) {
      synchronized (this.networkParametersList)
      {
        AdMediationNetworkParameters localAdMediationNetworkParameters = (AdMediationNetworkParameters)this.networkParametersList.poll();
        if (localAdMediationNetworkParameters != null) {
          bool = new AdMediationNetwork(localAdMediationNetworkParameters).requestAd();
        }
      }
    }
  }
  
  public void requestAd(Activity paramActivity, ViewGroup paramViewGroup, AdMediationNetworkParameters[] paramArrayOfAdMediationNetworkParameters)
  {
    if ((paramActivity == null) || (paramViewGroup == null) || (paramArrayOfAdMediationNetworkParameters == null)) {
      Log.d("AdMediationManager", "mediation requestAd parameter error!");
    }
    for (;;)
    {
      return;
      setActivity(paramActivity);
      setParentView(paramViewGroup);
      this.networkParametersList = new LinkedList(Arrays.asList(paramArrayOfAdMediationNetworkParameters));
      nextNetwork();
    }
  }
  
  public void setActivity(Activity paramActivity)
  {
    this.activityRef = new WeakReference(paramActivity);
  }
  
  public void setParentView(ViewGroup paramViewGroup)
  {
    this.parentViewRef = new WeakReference(paramViewGroup);
  }
  
  protected static class AdMediationManagerHolder
  {
    protected static AdMediationManager INSTANCE = new AdMediationManager();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.AdMediationManager
 * JD-Core Version:    0.7.0.1
 */