package com.amoad;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AMoAdView
  extends RelativeLayout
{
  public static final int AlphaAnimation = 1;
  public static final int AnimationNone = 0;
  public static final int ContentSizeIdentifierAuto = 2;
  public static final int ContentSizeIdentifierLandscape = 1;
  public static final int ContentSizeIdentifierPortrait = 0;
  private static final String ID_SERVICE_HTML = "<html><head></head><body><script type='text/javascript'>var amoad_enable_ids=true;var amoad_uid='%s';var amoad_enable_android=true;</script><script src='%s' ></script></body></html>";
  private static final String JSON_FQ = "fq";
  private static final String JSON_ROTATION = "rotation";
  private static final String MARKING_JS_URL = "http://j.amoad.com/js/r.js";
  public static final int RotateAnimation = 2;
  public static final int ScaleAnimation = 3;
  public static final int TranslateAnimation = 4;
  private static Object lock = new Object();
  private static ConfigFile mConfigFile = null;
  private int ROTATE_DISABLE = 0;
  private int ROTATE_ENABLE = 1;
  private final String TYPE_GIFTEXT = "giftext";
  private final String TYPE_HTML = "html";
  private final String TYPE_IMG = "img";
  private Handler handler = new Handler();
  private boolean hasDspName = false;
  private Boolean isPostedtoHandler = Boolean.valueOf(false);
  private AdBaseView mAdBaseView = null;
  private String mAdUrl = "http://d.amoad.com/ad/json/";
  private String mAid = "";
  private String mAppdomain = null;
  private String mAppname = null;
  private BannerView mBannerView = null;
  private AdCallback mCallback = null;
  private int mCallbackCnt = 0;
  private boolean mClickAnimation = false;
  private final int mConTimeout = 1000;
  private LinkedHashMap<String, String> mConfig = null;
  private Context mContext = null;
  final Boolean mDebugging = Boolean.valueOf(false);
  private String mDomainName = "amoad";
  private int mEnableRotate = this.ROTATE_ENABLE;
  private String mEncoding = "UTF-8";
  private ErrorReport mErrorReport = null;
  private String mFq = "";
  private GiftextView mGiftextView = null;
  private HtmlView mHtmlView = null;
  private Thread mIdTread = null;
  private String mImpUrl = "";
  private long mInterval = 0L;
  private final long mInterval_DEFAULT = 10000L;
  private String mJsonp = "";
  private Runnable mLooper = null;
  final Boolean mMessage = Boolean.valueOf(false);
  private String mModelName = "";
  private String mOrientation = "portrait";
  private int mRotationAnimation = 0;
  private RtbView mRtbView = null;
  private final String mSdkVersion = "2.5.0";
  private Settings mSettings;
  private String mSid = "";
  private String mType = "";
  private String mUid = "";
  private String mUrl = "";
  private String mUserAgent = "";
  private String mUserAgentRaw = "";
  private int mVisibility = 8;
  
  public AMoAdView(Context paramContext)
  {
    super(paramContext);
    initilize(paramContext);
  }
  
  public AMoAdView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initilize(paramContext);
  }
  
  public AMoAdView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initilize(paramContext);
  }
  
  private void doCrash()
  {
    null.toString();
  }
  
  private void doUrlLoad()
  {
    try
    {
      if (this.mIdTread != null)
      {
        this.mIdTread.join();
        this.mIdTread = null;
      }
      Object[] arrayOfObject = new Object[6];
      arrayOfObject[0] = URLEncoder.encode(this.mSid, "UTF-8");
      arrayOfObject[1] = URLEncoder.encode(this.mUid, "UTF-8");
      arrayOfObject[2] = URLEncoder.encode(this.mOrientation, "UTF-8");
      arrayOfObject[3] = URLEncoder.encode("2.5.0", "UTF-8");
      arrayOfObject[4] = URLEncoder.encode(this.mAppdomain, "UTF-8");
      arrayOfObject[5] = URLEncoder.encode(this.mAppname, "UTF-8");
      String str1 = String.format("sid=%s&uid=%s&orientation=%s&app=1&version=%s&appdomain=%s&appname=%s", arrayOfObject);
      String str2 = str1 + "&aid=" + URLEncoder.encode(this.mAid, "UTF-8");
      if (!this.mFq.equals("")) {
        str2 = str2 + "&fq=" + URLEncoder.encode(this.mFq, "UTF-8");
      }
      this.mUrl = (this.mAdUrl + '?' + str2);
      logCat("mUrl :" + this.mUrl);
      GetAdTask localGetAdTask = new GetAdTask(null);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = this.mUrl;
      localGetAdTask.execute(arrayOfString);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        logCat(localUnsupportedEncodingException.getMessage());
        setVisibility(8);
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        logCat(localNullPointerException.getMessage());
        outputMessage(localNullPointerException);
        setVisibility(8);
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        logCat(localInterruptedException.getMessage());
        outputMessage(localInterruptedException);
        setVisibility(8);
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        logCat(localException.getMessage());
        outputMessage(localException);
        setVisibility(8);
      }
    }
  }
  
  private boolean executeHttpGet(String paramString)
  {
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    boolean bool = false;
    try
    {
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(new HttpGet(paramString));
      if (localHttpResponse.getStatusLine().getStatusCode() == 200) {
        bool = true;
      } else {
        logCat("[I] status is " + localHttpResponse.getStatusLine().getStatusCode());
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      logCat(localClientProtocolException.getMessage());
    }
    catch (ParseException localParseException)
    {
      logCat(localParseException.getMessage());
    }
    catch (IOException localIOException)
    {
      logCat(localIOException.getMessage());
    }
    catch (Exception localException)
    {
      logCat(localException.getMessage());
    }
    return bool;
  }
  
  private boolean getAdContent(String paramString)
  {
    boolean bool = false;
    int i = -1;
    try
    {
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpParams localHttpParams = localDefaultHttpClient.getParams();
      HttpConnectionParams.setConnectionTimeout(localHttpParams, 1000);
      localHttpParams.setParameter("http.useragent", this.mUserAgentRaw);
      outputMessage("sid = " + this.mSid);
      outputMessage("IMEI = " + this.mUid);
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(new HttpGet(paramString));
      i = localHttpResponse.getStatusLine().getStatusCode();
      if (i == 200)
      {
        String str = localHttpResponse.getEntity().getContentType().toString();
        Matcher localMatcher = Pattern.compile("charset=\\s*+([a-zA-Z0-9_-]*)").matcher(str);
        if (localMatcher.find()) {
          this.mEncoding = localMatcher.group(1);
        }
        this.mJsonp = EntityUtils.toString(localHttpResponse.getEntity());
        bool = parseJsonData(this.mJsonp);
      }
      for (;;)
      {
        localDefaultHttpClient.getConnectionManager().shutdown();
        outputMessage("http status code = " + String.valueOf(i));
        return bool;
        logCat("status is " + i);
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      for (;;)
      {
        logCat(localClientProtocolException.getMessage());
      }
    }
    catch (ParseException localParseException)
    {
      for (;;)
      {
        logCat(localParseException.getMessage());
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        logCat(localIOException.getMessage());
      }
    }
  }
  
  private float getDensity()
  {
    WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.density;
  }
  
  private String getUuid()
  {
    synchronized (lock)
    {
      if (mConfigFile == null)
      {
        mConfigFile = new ConfigFile(this.mContext);
        mConfigFile.generateFileName(this.mDomainName, this.mModelName);
        mConfigFile.generateConfigFile();
      }
      this.mConfig = mConfigFile.getConfig();
      return (String)this.mConfig.get("id");
    }
  }
  
  private void initilize(Context paramContext)
  {
    this.mContext = paramContext;
    WebSettings localWebSettings = new WebView(this.mContext).getSettings();
    String str;
    if (localWebSettings.getUserAgentString() != null) {
      str = localWebSettings.getUserAgentString();
    }
    for (;;)
    {
      this.mUserAgent = str;
      this.mUserAgentRaw = str;
      try
      {
        this.mUserAgent = URLEncoder.encode(this.mUserAgent, "UTF-8");
        Matcher localMatcher = Pattern.compile(".*;(.*?)Build").matcher(this.mUserAgentRaw);
        if (localMatcher.find()) {
          this.mModelName = localMatcher.group(1).trim();
        }
        this.mIdTread = new Thread(new Runnable()
        {
          public void run()
          {
            AMoAdView.access$002(AMoAdView.this, AMoAdView.this.getUuid());
            AMoAdView.access$202(AMoAdView.this, new Settings(AMoAdView.this.mContext, AMoAdView.this.mDomainName, AMoAdView.this.mModelName));
            AMoAdView.access$602(AMoAdView.this, AMoAdView.this.mSettings.getFrequency());
          }
        });
        if (this.mIdTread != null) {
          this.mIdTread.start();
        }
        float f = getDensity();
        this.mBannerView = new BannerView(this.mContext);
        this.mBannerView.setDensity(f);
        this.mHtmlView = new HtmlView(this.mContext);
        this.mHtmlView.setDensity(f);
        this.mGiftextView = new GiftextView(this.mContext);
        this.mGiftextView.setDensity(f);
        this.mRtbView = new RtbView(this.mContext);
        this.mRtbView.setDensity(f);
        this.mAppdomain = this.mContext.getPackageName();
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        try
        {
          PackageManager localPackageManager = this.mContext.getPackageManager();
          this.mAppname = this.mContext.getApplicationInfo().loadLabel(localPackageManager).toString();
          logCat("appdomain:" + this.mAppdomain + " appname:" + this.mAppname);
          this.mLooper = new Runnable()
          {
            public void run()
            {
              AMoAdView.access$702(AMoAdView.this, Boolean.valueOf(false));
              AMoAdView.this.doUrlLoad();
              AMoAdView.this.postHandlerForUrlLoad(Boolean.valueOf(true), this);
            }
          };
          sendPayload();
          return;
          str = "";
          continue;
          localUnsupportedEncodingException = localUnsupportedEncodingException;
          logCat(localUnsupportedEncodingException.getMessage());
        }
        catch (Exception localException)
        {
          for (;;)
          {
            logCat(localException.getMessage());
          }
        }
      }
    }
  }
  
  private boolean isEmptyAd()
  {
    if ((this.mJsonp.length() == 0) || (this.mJsonp.equals("{}"))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private void logCat(String paramString)
  {
    if (this.mDebugging.booleanValue())
    {
      if (paramString == null) {
        break label23;
      }
      Log.e("AMoAdView", paramString);
    }
    for (;;)
    {
      return;
      label23:
      Log.e("AMoAdView", "NO MESSAGE");
    }
  }
  
  private void outputMessage(Exception paramException)
  {
    if (this.mMessage.booleanValue()) {
      for (StackTraceElement localStackTraceElement : paramException.getStackTrace()) {
        Log.i("AMoAdView", localStackTraceElement.getClassName() + "#" + localStackTraceElement.getMethodName() + ":" + localStackTraceElement.getLineNumber());
      }
    }
  }
  
  private void outputMessage(String paramString)
  {
    if (this.mMessage.booleanValue())
    {
      if (paramString == null) {
        break label23;
      }
      Log.i("AMoAdView", paramString);
    }
    for (;;)
    {
      return;
      label23:
      Log.i("AMoAdView", "NO MESSAGE");
    }
  }
  
  private boolean parseJsonData(String paramString)
  {
    boolean bool1 = true;
    logCat(paramString);
    if (isEmptyAd()) {}
    for (;;)
    {
      return bool1;
      try
      {
        this.hasDspName = false;
        JSONObject localJSONObject = new JSONObject(paramString);
        this.mType = localJSONObject.getString("type");
        if (localJSONObject.has("imp")) {
          this.mImpUrl = localJSONObject.getString("imp");
        }
        if (localJSONObject.has("rotation"))
        {
          long l = 1000L * localJSONObject.getLong("rotation");
          if (this.mInterval != l)
          {
            this.mInterval = l;
            postHandlerForUrlLoad(Boolean.valueOf(false), this.mLooper);
            postHandlerForUrlLoad(Boolean.valueOf(true), this.mLooper);
          }
        }
        if (localJSONObject.has("fq"))
        {
          String str = localJSONObject.getString("fq");
          if ((!str.equals("")) && (!str.equals(this.mFq)))
          {
            this.mFq = str;
            if (this.mSettings != null) {
              this.mSettings.setFrequency(this.mFq);
            }
          }
        }
        this.hasDspName = localJSONObject.has("dsp_name");
        if (this.mType.equals("img")) {
          this.mAdBaseView = this.mBannerView;
        }
        for (;;)
        {
          this.mAdBaseView.setOrientation(this.mOrientation);
          this.mAdBaseView.setModleName(this.mModelName);
          this.mAdBaseView.parseJson(paramString);
          this.mAdBaseView.loadAdData();
          this.mAid = localJSONObject.getString("aid");
          bool2 = true;
          break label422;
          if (!this.mType.equals("html")) {
            break label388;
          }
          if (!this.hasDspName) {
            break;
          }
          this.mAdBaseView = this.mRtbView;
          this.mRtbView.setEncode(this.mEncoding);
        }
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          if (this.mDebugging.booleanValue()) {
            localJSONException.printStackTrace();
          }
          bool2 = false;
          break;
          this.mAdBaseView = this.mHtmlView;
          this.mHtmlView.setEncode(this.mEncoding);
        }
      }
      catch (Exception localException)
      {
        boolean bool2;
        for (;;)
        {
          if (this.mDebugging.booleanValue()) {
            localException.printStackTrace();
          }
          bool2 = false;
          break;
          label388:
          if (this.mType.equals("giftext"))
          {
            this.mAdBaseView = this.mGiftextView;
            this.mGiftextView.setEncode(this.mEncoding);
          }
        }
        label422:
        bool1 = bool2;
      }
    }
  }
  
  private void postHandlerForUrlLoad(Boolean paramBoolean, Runnable paramRunnable)
  {
    long l;
    if (paramBoolean.booleanValue()) {
      if ((this.mEnableRotate == this.ROTATE_ENABLE) && (!this.isPostedtoHandler.booleanValue()) && (this.mVisibility == 0))
      {
        Handler localHandler = this.handler;
        if (this.mInterval != 0L) {
          break label71;
        }
        l = 10000L;
        localHandler.postDelayed(paramRunnable, l);
      }
    }
    for (this.isPostedtoHandler = Boolean.valueOf(true);; this.isPostedtoHandler = Boolean.valueOf(false))
    {
      return;
      label71:
      l = this.mInterval;
      break;
      this.handler.removeCallbacks(paramRunnable);
    }
  }
  
  private void refresh()
  {
    try
    {
      removeAllViews();
      if (!isEmptyAd())
      {
        this.mAdBaseView.loadView();
        addView(this.mAdBaseView);
        sendImpApi();
        this.mAdBaseView.doAnimation(this.mRotationAnimation);
        this.mAdBaseView.setClickAnimation(this.mClickAnimation);
      }
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        if (this.mDebugging.booleanValue()) {
          localException.printStackTrace();
        }
      }
    }
  }
  
  private void sendImpApi()
  {
    AsyncTask local3 = new AsyncTask()
    {
      protected Boolean doInBackground(String... paramAnonymousVarArgs)
      {
        boolean bool = false;
        if ((paramAnonymousVarArgs[0] != null) && (paramAnonymousVarArgs[0].length() != 0) && (AMoAdView.this.executeHttpGet(paramAnonymousVarArgs[0]))) {
          bool = true;
        }
        return Boolean.valueOf(bool);
      }
      
      protected void onPostExecute(Boolean paramAnonymousBoolean)
      {
        if (!paramAnonymousBoolean.booleanValue()) {
          AMoAdView.this.logCat("Failed calling imp api.");
        }
      }
    };
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mImpUrl;
    local3.execute(arrayOfString);
  }
  
  private void sendPayload()
  {
    if ((this.mUid == null) || (this.mUid.length() == 0)) {}
    for (;;)
    {
      return;
      WebView localWebView = new WebView(this.mContext);
      localWebView.getSettings().setJavaScriptEnabled(true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.mUid;
      arrayOfObject[1] = "http://j.amoad.com/js/r.js";
      localWebView.loadData(String.format("<html><head></head><body><script type='text/javascript'>var amoad_enable_ids=true;var amoad_uid='%s';var amoad_enable_android=true;</script><script src='%s' ></script></body></html>", arrayOfObject), "text/html", "utf-8");
    }
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    logCat("onWindowVisibilityChanged :" + String.valueOf(paramInt));
    this.mVisibility = paramInt;
    if (paramInt == 0) {
      postHandlerForUrlLoad(Boolean.valueOf(true), this.mLooper);
    }
    for (;;)
    {
      return;
      postHandlerForUrlLoad(Boolean.valueOf(false), this.mLooper);
    }
  }
  
  public void requestFreshAd()
  {
    doUrlLoad();
  }
  
  public void setCallback(AdCallback paramAdCallback)
  {
    this.mCallback = paramAdCallback;
  }
  
  public void setClickAanimation(boolean paramBoolean)
  {
    this.mClickAnimation = paramBoolean;
  }
  
  public void setContentSizeIdentifier(int paramInt) {}
  
  public void setRotationAnimation(int paramInt)
  {
    this.mRotationAnimation = paramInt;
  }
  
  public void setSid(String paramString)
  {
    this.mSid = paramString;
  }
  
  public void startRotation()
  {
    this.mEnableRotate = this.ROTATE_ENABLE;
    postHandlerForUrlLoad(Boolean.valueOf(true), this.mLooper);
  }
  
  public void stopRotation()
  {
    this.mEnableRotate = this.ROTATE_DISABLE;
    postHandlerForUrlLoad(Boolean.valueOf(false), this.mLooper);
  }
  
  private class GetAdTask
    extends AsyncTask<String, Void, Boolean>
  {
    private GetAdTask() {}
    
    protected Boolean doInBackground(String... paramVarArgs)
    {
      AMoAdView.access$1002(AMoAdView.this, 0);
      return Boolean.valueOf(AMoAdView.this.getAdContent(AMoAdView.this.mUrl));
    }
    
    protected void onPostExecute(Boolean paramBoolean)
    {
      if (paramBoolean.booleanValue())
      {
        AMoAdView.this.refresh();
        if ((AMoAdView.this.mCallback != null) && (AMoAdView.this.mCallbackCnt == 0))
        {
          if (!AMoAdView.this.isEmptyAd()) {
            break label65;
          }
          AMoAdView.this.mCallback.didReceiveEmptyAd();
          AMoAdView.access$1008(AMoAdView.this);
        }
      }
      for (;;)
      {
        return;
        label65:
        AMoAdView.this.mCallback.didReceiveAd();
        break;
        if ((AMoAdView.this.mCallback != null) && (AMoAdView.this.mCallbackCnt == 0))
        {
          AMoAdView.this.mCallback.didFailToReceiveAdWithError();
          AMoAdView.access$1008(AMoAdView.this);
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.AMoAdView
 * JD-Core Version:    0.7.0.1
 */