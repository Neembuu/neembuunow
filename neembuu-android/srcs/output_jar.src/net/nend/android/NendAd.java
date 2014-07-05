package net.nend.android;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.UUID;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

final class NendAd
  implements Ad, DownloadTask.Downloadable<AdParameter>
{
  private static final String NEND_UID_FILE_NAME = "NENDUUID";
  private String mClickUrl = null;
  private final Context mContext;
  private int mHeight = 50;
  private String mImageUrl = null;
  private WeakReference<AdListener> mListenerReference = null;
  private int mReloadIntervalInSeconds = 60;
  private final NendAdRequest mRequest;
  private DownloadTask<AdParameter> mTask = null;
  private final String mUid;
  private AdParameter.ViewType mViewType = AdParameter.ViewType.NONE;
  private String mWebViewUrl = null;
  private int mWidth = 320;
  
  static
  {
    if (!NendAd.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  NendAd(Context paramContext, int paramInt, String paramString)
  {
    if (paramContext == null) {
      throw new NullPointerException("Context is null.");
    }
    if (paramInt <= 0) {
      throw new IllegalArgumentException("Spot id is invalid. spot id : " + paramInt);
    }
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException("Api key is invalid. api key : " + paramString);
    }
    this.mContext = paramContext;
    this.mRequest = new NendAdRequest(paramContext, paramInt, paramString);
    this.mUid = makeUid(paramContext);
  }
  
  private String makeUid(Context paramContext)
  {
    assert (paramContext != null);
    Object localObject = NendHelper.md5String(UUID.randomUUID().toString());
    if (!new File(paramContext.getFilesDir(), "NENDUUID").exists()) {}
    try
    {
      FileOutputStream localFileOutputStream = paramContext.openFileOutput("NENDUUID", 0);
      localFileOutputStream.write(((String)localObject).getBytes());
      localFileOutputStream.close();
      for (;;)
      {
        label70:
        return localObject;
        try
        {
          String str = new BufferedReader(new InputStreamReader(paramContext.openFileInput("NENDUUID"))).readLine();
          localObject = str;
        }
        catch (Exception localException1) {}
      }
    }
    catch (Exception localException2)
    {
      break label70;
    }
  }
  
  private void setAdViewParam(AdParameter paramAdParameter)
  {
    assert (paramAdParameter != null);
    this.mViewType = AdParameter.ViewType.ADVIEW;
    setReloadIntervalInSeconds(paramAdParameter.getReloadIntervalInSeconds());
    this.mImageUrl = paramAdParameter.getImageUrl();
    this.mClickUrl = paramAdParameter.getClickUrl();
    this.mWebViewUrl = null;
  }
  
  private void setReloadIntervalInSeconds(int paramInt)
  {
    if (paramInt > 99999) {
      this.mReloadIntervalInSeconds = 99999;
    }
    for (;;)
    {
      return;
      if ((NendHelper.isDev()) || (paramInt > 30)) {
        this.mReloadIntervalInSeconds = paramInt;
      } else {
        this.mReloadIntervalInSeconds = 30;
      }
    }
  }
  
  private void setWebViewParam(AdParameter paramAdParameter)
  {
    assert (paramAdParameter != null);
    this.mViewType = AdParameter.ViewType.WEBVIEW;
    this.mWebViewUrl = paramAdParameter.getWebViewUrl();
    this.mImageUrl = null;
    this.mClickUrl = null;
  }
  
  public void cancelRequest()
  {
    if (this.mTask != null) {
      this.mTask.cancel(true);
    }
  }
  
  public String getClickUrl()
  {
    return this.mClickUrl;
  }
  
  public int getHeight()
  {
    return this.mHeight;
  }
  
  public String getImageUrl()
  {
    return this.mImageUrl;
  }
  
  public AdListener getListener()
  {
    if (this.mListenerReference != null) {}
    for (AdListener localAdListener = (AdListener)this.mListenerReference.get();; localAdListener = null) {
      return localAdListener;
    }
  }
  
  public int getReloadIntervalInSeconds()
  {
    return this.mReloadIntervalInSeconds;
  }
  
  public String getRequestUrl()
  {
    return this.mRequest.getRequestUrl(this.mUid);
  }
  
  public String getUid()
  {
    return this.mUid;
  }
  
  public AdParameter.ViewType getViewType()
  {
    return this.mViewType;
  }
  
  public String getWebViewUrl()
  {
    return this.mWebViewUrl;
  }
  
  public int getWidth()
  {
    return this.mWidth;
  }
  
  public boolean isRequestable()
  {
    if ((this.mTask == null) || (this.mTask.isFinished())) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public AdParameter makeResponse(HttpEntity paramHttpEntity)
  {
    Object localObject = null;
    if (paramHttpEntity == null) {}
    for (;;)
    {
      return localObject;
      try
      {
        AdParameter localAdParameter = new NendAdResponseParser(this.mContext).parseResponse(EntityUtils.toString(paramHttpEntity));
        localObject = localAdParameter;
      }
      catch (ParseException localParseException)
      {
        if (!$assertionsDisabled) {
          throw new AssertionError();
        }
        NendLog.d(NendStatus.ERR_HTTP_REQUEST, localParseException);
      }
      catch (IOException localIOException)
      {
        if (!$assertionsDisabled) {
          throw new AssertionError();
        }
        NendLog.d(NendStatus.ERR_HTTP_REQUEST, localIOException);
      }
    }
  }
  
  public void onDownload(AdParameter paramAdParameter)
  {
    AdListener localAdListener = getListener();
    if (paramAdParameter != null) {
      switch (1.$SwitchMap$net$nend$android$AdParameter$ViewType[paramAdParameter.getViewType().ordinal()])
      {
      default: 
        if (!$assertionsDisabled) {
          throw new AssertionError();
        }
        break;
      case 1: 
        setAdViewParam(paramAdParameter);
        if (localAdListener != null) {
          localAdListener.onReceiveAd();
        }
        break;
      }
    }
    for (;;)
    {
      return;
      setWebViewParam(paramAdParameter);
      break;
      if (localAdListener != null)
      {
        localAdListener.onFailedToReceiveAd();
        continue;
        if (localAdListener != null) {
          localAdListener.onFailedToReceiveAd();
        }
      }
    }
  }
  
  public void removeListener()
  {
    this.mListenerReference = null;
  }
  
  public boolean requestAd()
  {
    boolean bool = false;
    if (isRequestable())
    {
      this.mTask = new DownloadTask(this);
      this.mTask.execute(new Void[0]);
      bool = true;
    }
    return bool;
  }
  
  public void setListener(AdListener paramAdListener)
  {
    this.mListenerReference = new WeakReference(paramAdListener);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAd
 * JD-Core Version:    0.7.0.1
 */