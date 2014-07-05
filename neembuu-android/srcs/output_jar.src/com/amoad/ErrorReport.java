package com.amoad;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

class ErrorReport
  implements Thread.UncaughtExceptionHandler
{
  private Context mContext;
  private SharedPref mPref;
  private final String mUploadUrl = "";
  
  ErrorReport(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void writeLog(Throwable paramThrowable)
  {
    StringBuilder localStringBuilder = new StringBuilder("");
    localStringBuilder.append(paramThrowable.toString() + "\n");
    for (StackTraceElement localStackTraceElement : paramThrowable.getStackTrace())
    {
      localStringBuilder.append(localStackTraceElement.getClassName()).append("#");
      localStringBuilder.append(localStackTraceElement.getMethodName()).append(":");
      localStringBuilder.append(localStackTraceElement.getLineNumber()).append("\n");
    }
    this.mPref.setLog(localStringBuilder.toString());
  }
  
  public String getBuildInfo()
  {
    Object[] arrayOfObject = new Object[9];
    arrayOfObject[0] = Build.BRAND;
    arrayOfObject[1] = Build.DEVICE;
    arrayOfObject[2] = Build.DISPLAY;
    arrayOfObject[3] = Build.ID;
    arrayOfObject[4] = Build.MODEL;
    arrayOfObject[5] = Build.PRODUCT;
    arrayOfObject[6] = Build.VERSION.CODENAME;
    arrayOfObject[7] = Build.VERSION.RELEASE;
    arrayOfObject[8] = Integer.valueOf(Build.VERSION.SDK_INT);
    return String.format("BRAND:%s\nDEVICE:%s\nDISPLAY:%s\nID:%s\nMODEL:%s\nPRODUCT:%s\nCODENANE:%s\nRELEASE:%s\nSDK:%d", arrayOfObject);
  }
  
  protected String getLog()
  {
    return this.mPref.getLog();
  }
  
  protected boolean getLogEnable()
  {
    return this.mPref.getLogEnable().booleanValue();
  }
  
  protected void postLogReport(String paramString)
  {
    String str = this.mPref.getLog();
    if ((getLogEnable() == true) && (str.length() > 0)) {}
    try
    {
      HttpPost localHttpPost = new HttpPost("");
      localHttpPost.addHeader(new BasicHeader("Content-type", "application/x-www-form-urlencoded"));
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("BRAND", Build.BRAND));
      localArrayList.add(new BasicNameValuePair("PRODUCT", Build.PRODUCT));
      localArrayList.add(new BasicNameValuePair("RELEASE", Build.VERSION.RELEASE));
      localArrayList.add(new BasicNameValuePair("SDK", String.valueOf(Build.VERSION.SDK_INT)));
      localArrayList.add(new BasicNameValuePair("BUG", str));
      localArrayList.add(new BasicNameValuePair("ua", paramString));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "UTF-8"));
      new DefaultHttpClient().execute(localHttpPost);
      this.mPref.clearLog();
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException.printStackTrace();
      }
    }
  }
  
  protected boolean setLogEnable(boolean paramBoolean)
  {
    return this.mPref.setLogEnable(Boolean.valueOf(paramBoolean));
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable) {}
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.ErrorReport
 * JD-Core Version:    0.7.0.1
 */