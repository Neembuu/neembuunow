package jp.adlantis.android.mediation;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import jp.adlantis.android.AdManager;
import jp.adlantis.android.AdNetworkConnection;
import jp.adlantis.android.mediation.adapters.AdMediationAdapterFactory;

public class AdMediationNetwork
  implements AdMediationAdapterListener
{
  private static final String LOG_TAG = "AdMediationNetwork";
  private static final int TIMEOUT_SECONDS = 10;
  private AdMediationAdapter adapter = null;
  private Handler handler = new Handler();
  private AdMediationNetworkParameters networkParameters;
  private boolean receivedAd = false;
  private ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
  private ScheduledFuture<?> timerFuture = null;
  
  public AdMediationNetwork(AdMediationNetworkParameters paramAdMediationNetworkParameters)
  {
    this.networkParameters = paramAdMediationNetworkParameters;
  }
  
  private boolean addToParentView(View paramView)
  {
    boolean bool = false;
    ViewGroup localViewGroup = AdMediationManager.getInstance().getParentView();
    if (localViewGroup == null) {
      return bool;
    }
    for (int i = 0;; i++) {
      if (i < localViewGroup.getChildCount())
      {
        if (paramView == localViewGroup.getChildAt(i)) {
          bool = true;
        }
      }
      else
      {
        if (!bool)
        {
          localViewGroup.removeAllViews();
          RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
          localLayoutParams.addRule(13);
          localViewGroup.addView(paramView, localLayoutParams);
        }
        bool = true;
        break;
      }
    }
  }
  
  private void killTimer()
  {
    if (this.timerFuture != null) {
      this.timerFuture.cancel(false);
    }
    Log.d("AdMediationNetwork", "killed timer: " + this.networkParameters.getNetworkName());
  }
  
  private void nextNetwork()
  {
    this.handler.post(new Runnable()
    {
      public void run()
      {
        AdMediationNetwork.this.destroy();
        AdMediationManager.getInstance().nextNetwork();
      }
    });
  }
  
  private void startTimer()
  {
    this.timerFuture = this.timer.schedule(new Runnable()
    {
      public void run()
      {
        Log.d("AdMediationNetwork", "timer timeout: " + AdMediationNetwork.this.networkParameters.getNetworkName());
        AdMediationNetwork.this.nextNetwork();
      }
    }, 10L, TimeUnit.SECONDS);
    Log.d("AdMediationNetwork", "started timer: " + this.networkParameters.getNetworkName());
  }
  
  public void destroy()
  {
    if (this.adapter != null)
    {
      this.adapter.destroy();
      this.adapter = null;
    }
    if (this.timerFuture != null) {
      this.timerFuture.cancel(true);
    }
  }
  
  public AdMediationAdapter getAdapter()
  {
    return this.adapter;
  }
  
  public boolean isReceivedAd()
  {
    return this.receivedAd;
  }
  
  public void onDismissScreen(AdMediationAdapter paramAdMediationAdapter)
  {
    Log.d("AdMediationNetwork", "onDismissScreen: " + this.networkParameters.getNetworkName());
  }
  
  public void onFailedToReceiveAd(AdMediationAdapter paramAdMediationAdapter)
  {
    Log.d("AdMediationNetwork", "onFailedToReceiveAd: " + this.networkParameters.getNetworkName());
    killTimer();
    startCountRequest();
    nextNetwork();
  }
  
  public void onLeaveApplication(AdMediationAdapter paramAdMediationAdapter)
  {
    Log.d("AdMediationNetwork", "onLeaveApplication: " + this.networkParameters.getNetworkName());
  }
  
  public void onPresentScreen(AdMediationAdapter paramAdMediationAdapter)
  {
    Log.d("AdMediationNetwork", "onPresentScreen: " + this.networkParameters.getNetworkName());
  }
  
  public void onReceivedAd(AdMediationAdapter paramAdMediationAdapter, final View paramView)
  {
    Log.d("AdMediationNetwork", "onReceivedAd: " + this.networkParameters.getNetworkName());
    killTimer();
    Activity localActivity = AdMediationManager.getInstance().getActivity();
    if (localActivity == null)
    {
      Log.d("AdMediationNetwork", "can not get Activity: " + this.networkParameters.getNetworkName());
      destroy();
    }
    for (;;)
    {
      return;
      localActivity.runOnUiThread(new Runnable()
      {
        public void run()
        {
          if (AdMediationNetwork.this.addToParentView(paramView)) {
            Log.d("AdMediationNetwork", "show ads in AdlantisView: " + AdMediationNetwork.this.networkParameters.getNetworkName());
          }
          AdMediationNetwork.this.startCountImpression();
          AdMediationNetwork.access$302(AdMediationNetwork.this, true);
        }
      });
    }
  }
  
  public void onTouchAd(AdMediationAdapter paramAdMediationAdapter)
  {
    Log.d("AdMediationNetwork", "onTouchAd: " + this.networkParameters.getNetworkName());
    if (this.receivedAd) {
      startCountTap();
    }
  }
  
  public boolean requestAd()
  {
    boolean bool = false;
    Log.d("AdMediationNetwork", "request network ad: " + this.networkParameters.getNetworkName());
    if (this.adapter == null) {
      this.adapter = AdMediationAdapterFactory.create(this.networkParameters);
    }
    if (this.adapter == null) {}
    for (;;)
    {
      return bool;
      Activity localActivity = AdMediationManager.getInstance().getActivity();
      this.receivedAd = false;
      try
      {
        if ((localActivity.getResources().getConfiguration().orientation != 2) || (!"mediba".equalsIgnoreCase(this.networkParameters.getNetworkName()))) {
          break label203;
        }
        Log.d("AdMediationNetwork", "skip loadscape mode: " + this.networkParameters.getNetworkName());
      }
      catch (Exception localException)
      {
        Log.d("AdMediationNetwork", "exception in requestAd of adapter: " + this.networkParameters.getNetworkName());
        Log.d("AdMediationNetwork", "exception = " + localException.toString());
      }
      continue;
      label203:
      final View localView = this.adapter.requestAd(this, localActivity, this.networkParameters);
      if (localView != null) {
        localActivity.runOnUiThread(new Runnable()
        {
          public void run()
          {
            AdMediationNetwork.this.addToParentView(localView);
          }
        });
      }
      startTimer();
      bool = true;
    }
  }
  
  public void setAdapter(AdMediationAdapter paramAdMediationAdapter)
  {
    this.adapter = paramAdMediationAdapter;
  }
  
  protected void startCountImpression()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        String str1 = AdMediationNetwork.this.networkParameters.getCountRequestUrl();
        if (str1 == null) {
          Log.d("AdMediationNetwork", "cannot get count_imp_url: " + AdMediationNetwork.this.networkParameters.getNetworkName());
        }
        for (;;)
        {
          return;
          String str2 = str1 + "&impFlag=1";
          Activity localActivity = AdMediationManager.getInstance().getActivity();
          String str3 = AdManager.getInstance().getAdNetworkConnection().buildCompleteHttpUri(localActivity, str2);
          Log.d("AdMediationNetwork", "count_imp_url of " + AdMediationNetwork.this.networkParameters.getNetworkName() + ": " + str3);
          AdMediationRequest.sendGetRequest(str3);
        }
      }
    }).start();
  }
  
  protected void startCountRequest()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        String str1 = AdMediationNetwork.this.networkParameters.getCountRequestUrl();
        if (str1 == null) {
          Log.d("AdMediationNetwork", "cannot get count_req_url: " + AdMediationNetwork.this.networkParameters.getNetworkName());
        }
        for (;;)
        {
          return;
          String str2 = str1 + "&impFlag=0";
          Activity localActivity = AdMediationManager.getInstance().getActivity();
          String str3 = AdManager.getInstance().getAdNetworkConnection().buildCompleteHttpUri(localActivity, str2);
          Log.d("AdMediationNetwork", "count_req_url of " + AdMediationNetwork.this.networkParameters.getNetworkName() + ": " + str3);
          AdMediationRequest.sendGetRequest(str3);
        }
      }
    }).start();
  }
  
  protected void startCountTap()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        String str1 = AdMediationNetwork.this.networkParameters.getCountTapUrl();
        if (str1 == null) {
          Log.d("AdMediationNetwork", "cannot get count_tap_url: " + AdMediationNetwork.this.networkParameters.getNetworkName());
        }
        for (;;)
        {
          return;
          Activity localActivity = AdMediationManager.getInstance().getActivity();
          String str2 = AdManager.getInstance().getAdNetworkConnection().buildCompleteHttpUri(localActivity, str1);
          Log.d("AdMediationNetwork", "count_tap_url of " + AdMediationNetwork.this.networkParameters.getNetworkName() + ": " + str2);
          AdMediationRequest.sendGetRequest(str2);
        }
      }
    }).start();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.AdMediationNetwork
 * JD-Core Version:    0.7.0.1
 */