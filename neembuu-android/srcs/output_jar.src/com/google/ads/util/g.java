package com.google.ads.util;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.ads.AdActivity;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.c;
import com.google.ads.internal.d;
import com.google.ads.internal.i;
import com.google.ads.m;
import com.google.ads.m.a;
import com.google.ads.n;
import com.google.ads.o;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@TargetApi(11)
public class g
{
  public static void a(View paramView)
  {
    paramView.setLayerType(1, null);
  }
  
  public static void a(Window paramWindow)
  {
    paramWindow.setFlags(16777216, 16777216);
  }
  
  public static void a(WebSettings paramWebSettings, n paramn)
  {
    Context localContext = (Context)paramn.f.a();
    m.a locala = (m.a)((m)paramn.d.a()).b.a();
    paramWebSettings.setAppCacheEnabled(true);
    paramWebSettings.setAppCacheMaxSize(((Long)locala.i.a()).longValue());
    paramWebSettings.setAppCachePath(new File(localContext.getCacheDir(), "admob").getAbsolutePath());
    paramWebSettings.setDatabaseEnabled(true);
    paramWebSettings.setDatabasePath(localContext.getDatabasePath("admob").getAbsolutePath());
    paramWebSettings.setDomStorageEnabled(true);
    paramWebSettings.setSupportZoom(true);
    paramWebSettings.setBuiltInZoomControls(true);
    paramWebSettings.setDisplayZoomControls(false);
  }
  
  public static void b(View paramView)
  {
    paramView.setLayerType(0, null);
  }
  
  public static class a
    extends WebChromeClient
  {
    private final n a;
    
    public a(n paramn)
    {
      this.a = paramn;
    }
    
    private static void a(AlertDialog.Builder paramBuilder, Context paramContext, String paramString1, String paramString2, JsPromptResult paramJsPromptResult)
    {
      LinearLayout localLinearLayout = new LinearLayout(paramContext);
      localLinearLayout.setOrientation(1);
      TextView localTextView = new TextView(paramContext);
      localTextView.setText(paramString1);
      final EditText localEditText = new EditText(paramContext);
      localEditText.setText(paramString2);
      localLinearLayout.addView(localTextView);
      localLinearLayout.addView(localEditText);
      paramBuilder.setView(localLinearLayout).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          g.a.this.confirm(localEditText.getText().toString());
        }
      }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          g.a.this.cancel();
        }
      }).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          g.a.this.cancel();
        }
      }).create().show();
    }
    
    private static void a(AlertDialog.Builder paramBuilder, String paramString, JsResult paramJsResult)
    {
      paramBuilder.setMessage(paramString).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          g.a.this.confirm();
        }
      }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          g.a.this.cancel();
        }
      }).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          g.a.this.cancel();
        }
      }).create().show();
    }
    
    private static boolean a(WebView paramWebView, String paramString1, String paramString2, String paramString3, JsResult paramJsResult, JsPromptResult paramJsPromptResult, boolean paramBoolean)
    {
      AlertDialog.Builder localBuilder;
      if ((paramWebView instanceof AdWebView))
      {
        AdActivity localAdActivity = ((AdWebView)paramWebView).i();
        if (localAdActivity != null)
        {
          localBuilder = new AlertDialog.Builder(localAdActivity);
          localBuilder.setTitle(paramString1);
          if (paramBoolean) {
            a(localBuilder, localAdActivity, paramString2, paramString3, paramJsPromptResult);
          }
        }
      }
      for (boolean bool = true;; bool = false)
      {
        return bool;
        a(localBuilder, paramString2, paramJsResult);
        break;
      }
    }
    
    public void onCloseWindow(WebView paramWebView)
    {
      if ((paramWebView instanceof AdWebView)) {
        ((AdWebView)paramWebView).f();
      }
    }
    
    public boolean onConsoleMessage(ConsoleMessage paramConsoleMessage)
    {
      String str = "JS: " + paramConsoleMessage.message() + " (" + paramConsoleMessage.sourceId() + ":" + paramConsoleMessage.lineNumber() + ")";
      switch (g.1.a[paramConsoleMessage.messageLevel().ordinal()])
      {
      }
      for (;;)
      {
        return super.onConsoleMessage(paramConsoleMessage);
        b.b(str);
        continue;
        b.e(str);
        continue;
        b.c(str);
        continue;
        b.a(str);
      }
    }
    
    public void onExceededDatabaseQuota(String paramString1, String paramString2, long paramLong1, long paramLong2, long paramLong3, WebStorage.QuotaUpdater paramQuotaUpdater)
    {
      m.a locala = (m.a)((m)this.a.d.a()).b.a();
      long l = ((Long)locala.l.a()).longValue() - paramLong3;
      if (l <= 0L)
      {
        paramQuotaUpdater.updateQuota(paramLong1);
        return;
      }
      if (paramLong1 == 0L) {
        if ((paramLong2 > l) || (paramLong2 > ((Long)locala.m.a()).longValue())) {}
      }
      for (;;)
      {
        paramQuotaUpdater.updateQuota(paramLong2);
        break;
        paramLong2 = 0L;
        continue;
        if (paramLong2 == 0L)
        {
          paramLong2 = Math.min(paramLong1 + Math.min(((Long)locala.n.a()).longValue(), l), ((Long)locala.m.a()).longValue());
        }
        else
        {
          if (paramLong2 <= Math.min(((Long)locala.m.a()).longValue() - paramLong1, l)) {
            paramLong1 += paramLong2;
          }
          paramLong2 = paramLong1;
        }
      }
    }
    
    public boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      return a(paramWebView, paramString1, paramString2, null, paramJsResult, null, false);
    }
    
    public boolean onJsBeforeUnload(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      return a(paramWebView, paramString1, paramString2, null, paramJsResult, null, false);
    }
    
    public boolean onJsConfirm(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      return a(paramWebView, paramString1, paramString2, null, paramJsResult, null, false);
    }
    
    public boolean onJsPrompt(WebView paramWebView, String paramString1, String paramString2, String paramString3, JsPromptResult paramJsPromptResult)
    {
      return a(paramWebView, paramString1, paramString2, paramString3, null, paramJsPromptResult, true);
    }
    
    public void onReachedMaxAppCacheSize(long paramLong1, long paramLong2, WebStorage.QuotaUpdater paramQuotaUpdater)
    {
      m.a locala = (m.a)((m)this.a.d.a()).b.a();
      long l1 = ((Long)locala.k.a()).longValue() - paramLong2;
      long l2 = paramLong1 + ((Long)locala.j.a()).longValue();
      if (l1 < l2) {
        paramQuotaUpdater.updateQuota(0L);
      }
      for (;;)
      {
        return;
        paramQuotaUpdater.updateQuota(l2);
      }
    }
    
    public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
    {
      paramCustomViewCallback.onCustomViewHidden();
    }
  }
  
  public static class b
    extends i
  {
    public b(d paramd, Map<String, o> paramMap, boolean paramBoolean1, boolean paramBoolean2)
    {
      super(paramMap, paramBoolean1, paramBoolean2);
    }
    
    private static WebResourceResponse a(String paramString, Context paramContext)
      throws IOException
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      try
      {
        AdUtil.a(localHttpURLConnection, paramContext.getApplicationContext());
        localHttpURLConnection.connect();
        WebResourceResponse localWebResourceResponse = new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(AdUtil.a(new InputStreamReader(localHttpURLConnection.getInputStream())).getBytes("UTF-8")));
        return localWebResourceResponse;
      }
      finally
      {
        localHttpURLConnection.disconnect();
      }
    }
    
    public WebResourceResponse shouldInterceptRequest(WebView paramWebView, String paramString)
    {
      try
      {
        m.a locala;
        Object localObject;
        if ("mraid.js".equalsIgnoreCase(new File(paramString).getName()))
        {
          c localc = this.a.k();
          if (localc != null) {
            localc.c(true);
          }
          for (;;)
          {
            locala = (m.a)((m)this.a.i().d.a()).b.a();
            if (this.a.i().b()) {
              break label227;
            }
            if (!this.b) {
              break;
            }
            String str3 = (String)locala.f.a();
            b.a("shouldInterceptRequest(" + str3 + ")");
            localObject = a(str3, paramWebView.getContext());
            break label296;
            this.a.a(true);
          }
        }
        String str2;
        label227:
        String str1;
        WebResourceResponse localWebResourceResponse;
        return localObject;
      }
      catch (IOException localIOException)
      {
        b.d("IOException fetching MRAID JS.", localIOException);
        localObject = super.shouldInterceptRequest(paramWebView, paramString);
        break label296;
        str2 = (String)locala.e.a();
        b.a("shouldInterceptRequest(" + str2 + ")");
        localObject = a(str2, paramWebView.getContext());
        break label296;
        str1 = (String)locala.g.a();
        b.a("shouldInterceptRequest(" + str1 + ")");
        localWebResourceResponse = a(str1, paramWebView.getContext());
        localObject = localWebResourceResponse;
      }
      catch (Throwable localThrowable)
      {
        for (;;)
        {
          b.d("An unknown error occurred fetching MRAID JS.", localThrowable);
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.util.g
 * JD-Core Version:    0.7.0.1
 */