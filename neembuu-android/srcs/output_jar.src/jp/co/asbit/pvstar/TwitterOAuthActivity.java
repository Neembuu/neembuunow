package jp.co.asbit.pvstar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterOAuthActivity
  extends Activity
{
  public static final String CALLBACK = "http://www.asbit.co.jp/pvstar_plus/blank.html";
  public static final String CONSUMER_KEY = "TuSlIQOIqG8eM4zDXUPg";
  public static final String CONSUMER_SECRET = "4RGsoG6wXXwcWdeN550kLm1um8Dl0BWYbjdIbubGo";
  private static final String OAUTH_VERIFIER = "oauth_verifier";
  public static final String TOKEN = "token";
  public static final String TOKEN_SECRET = "token_secret";
  private Twitter mTwitter;
  private WebChromeClient mWebChromeClient = new WebChromeClient()
  {
    public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
    {
      super.onProgressChanged(paramAnonymousWebView, paramAnonymousInt);
      TwitterOAuthActivity.this.setProgress(paramAnonymousInt * 100);
    }
  };
  private WebView mWebView;
  private WebViewClient mWebViewClient = new WebViewClient()
  {
    public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
    {
      if ((paramAnonymousString != null) && (paramAnonymousString.startsWith("http://www.asbit.co.jp/pvstar_plus/blank.html?oauth_token")))
      {
        String str = Uri.parse(paramAnonymousString).getQueryParameter("oauth_verifier");
        TwitterOAuthActivity.PostTask localPostTask = new TwitterOAuthActivity.PostTask(TwitterOAuthActivity.this);
        String[] arrayOfString = new String[1];
        arrayOfString[0] = str;
        localPostTask.execute(arrayOfString);
      }
      for (;;)
      {
        return;
        super.onPageStarted(paramAnonymousWebView, paramAnonymousString, paramAnonymousBitmap);
      }
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      if ((paramAnonymousString != null) && (paramAnonymousString.startsWith("http://www.asbit.co.jp/pvstar_plus/blank.html?denied")))
      {
        Intent localIntent = new Intent();
        TwitterOAuthActivity.this.setResult(0, localIntent);
        TwitterOAuthActivity.this.finish();
      }
      return super.shouldOverrideUrlLoading(paramAnonymousWebView, paramAnonymousString);
    }
  };
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setResult(0);
    requestWindowFeature(2);
    requestWindowFeature(5);
    this.mWebView = new WebView(this);
    this.mWebView.getSettings().setSavePassword(false);
    this.mWebView.setWebChromeClient(this.mWebChromeClient);
    this.mWebView.setWebViewClient(this.mWebViewClient);
    setContentView(this.mWebView);
    CookieManager.getInstance().setAcceptCookie(false);
    this.mTwitter = new TwitterFactory().getInstance();
    this.mTwitter.setOAuthConsumer("TuSlIQOIqG8eM4zDXUPg", "4RGsoG6wXXwcWdeN550kLm1um8Dl0BWYbjdIbubGo");
    new PreTask().execute(new Void[0]);
  }
  
  class PostTask
    extends AsyncTask<String, Void, AccessToken>
  {
    PostTask() {}
    
    protected AccessToken doInBackground(String... paramVarArgs)
    {
      Object localObject = null;
      if (paramVarArgs != null) {}
      try
      {
        AccessToken localAccessToken = TwitterOAuthActivity.this.mTwitter.getOAuthAccessToken(paramVarArgs[0]);
        localObject = localAccessToken;
      }
      catch (TwitterException localTwitterException)
      {
        for (;;)
        {
          localTwitterException.printStackTrace();
        }
      }
      return localObject;
    }
    
    protected void onPostExecute(AccessToken paramAccessToken)
    {
      super.onPostExecute(paramAccessToken);
      TwitterOAuthActivity.this.setProgressBarIndeterminateVisibility(false);
      if (paramAccessToken != null)
      {
        String str1 = paramAccessToken.getToken();
        String str2 = paramAccessToken.getTokenSecret();
        Intent localIntent = new Intent();
        localIntent.putExtra("token", str1);
        localIntent.putExtra("token_secret", str2);
        TwitterOAuthActivity.this.setResult(-1, localIntent);
      }
      TwitterOAuthActivity.this.finish();
    }
    
    protected void onPreExecute()
    {
      super.onPreExecute();
      TwitterOAuthActivity.this.setProgressBarIndeterminateVisibility(true);
    }
  }
  
  class PreTask
    extends AsyncTask<Void, Void, String>
  {
    PreTask() {}
    
    protected String doInBackground(Void... paramVarArgs)
    {
      Object localObject = null;
      try
      {
        RequestToken localRequestToken = TwitterOAuthActivity.this.mTwitter.getOAuthRequestToken();
        if (localRequestToken != null)
        {
          String str = localRequestToken.getAuthorizationURL();
          localObject = str;
        }
      }
      catch (TwitterException localTwitterException)
      {
        for (;;)
        {
          localTwitterException.printStackTrace();
        }
      }
      return localObject;
    }
    
    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      TwitterOAuthActivity.this.setProgressBarIndeterminateVisibility(false);
      if (paramString != null) {
        TwitterOAuthActivity.this.mWebView.loadUrl(paramString);
      }
      for (;;)
      {
        return;
        TwitterOAuthActivity.this.finish();
      }
    }
    
    protected void onPreExecute()
    {
      super.onPreExecute();
      TwitterOAuthActivity.this.setProgressBarIndeterminateVisibility(true);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.TwitterOAuthActivity
 * JD-Core Version:    0.7.0.1
 */