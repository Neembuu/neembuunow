package jp.co.asbit.pvstar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.widget.RemoteViews;

public class AppWidgetPlayerProvider
  extends AppWidgetProvider
{
  public static final String APPWIDGET_ACTION = "jp.co.asbit.pvstar.APPWIDGET_ACTION";
  public static final String APPWIDGET_ACTION_NEXT = "jp.co.asbit.pvstar.APPWIDGET_ACTION_NEXT";
  public static final String APPWIDGET_ACTION_PAUSE = "jp.co.asbit.pvstar.APPWIDGET_ACTION_PAUSE";
  public static final String APPWIDGET_ACTION_PLAY = "jp.co.asbit.pvstar.APPWIDGET_ACTION_PLAY";
  public static final String APPWIDGET_ACTION_PREV = "jp.co.asbit.pvstar.APPWIDGET_ACTION_PREV";
  private static int progressVisibility = 8;
  private static String videoTitle = null;
  
  private RemoteViews removeViews(Context paramContext)
  {
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130903040);
    Intent localIntent1 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_PLAY");
    Intent localIntent2 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_NEXT");
    Intent localIntent3 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_PREV");
    Intent localIntent4 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_PAUSE");
    PendingIntent localPendingIntent1 = PendingIntent.getBroadcast(paramContext, 0, localIntent1, 0);
    PendingIntent localPendingIntent2 = PendingIntent.getBroadcast(paramContext, 0, localIntent2, 0);
    PendingIntent localPendingIntent3 = PendingIntent.getBroadcast(paramContext, 0, localIntent3, 0);
    PendingIntent localPendingIntent4 = PendingIntent.getBroadcast(paramContext, 0, localIntent4, 0);
    localRemoteViews.setOnClickPendingIntent(2131492866, localPendingIntent1);
    localRemoteViews.setOnClickPendingIntent(2131492868, localPendingIntent2);
    localRemoteViews.setOnClickPendingIntent(2131492865, localPendingIntent3);
    localRemoteViews.setOnClickPendingIntent(2131492867, localPendingIntent4);
    localRemoteViews.setOnClickPendingIntent(2131492864, PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, PvstarActivity.class), 134217728));
    localRemoteViews.setOnClickPendingIntent(2131492869, PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, VideoActivity.class), 134217728));
    if (Build.VERSION.SDK_INT >= 10) {
      localRemoteViews.setViewVisibility(2131492870, progressVisibility);
    }
    if (videoTitle == null)
    {
      localRemoteViews.setViewVisibility(2131492869, 8);
      localRemoteViews.setTextViewText(2131492869, "");
      localRemoteViews.setViewVisibility(2131492871, 0);
    }
    for (;;)
    {
      return localRemoteViews;
      localRemoteViews.setViewVisibility(2131492869, 0);
      localRemoteViews.setViewVisibility(2131492871, 8);
      localRemoteViews.setTextViewText(2131492869, videoTitle);
    }
  }
  
  private void updateRemoteView(Context paramContext, AppWidgetManager paramAppWidgetManager, int paramInt)
  {
    paramAppWidgetManager.updateAppWidget(paramInt, removeViews(paramContext));
  }
  
  private void updateRemoteView(Context paramContext, AppWidgetManager paramAppWidgetManager, ComponentName paramComponentName)
  {
    paramAppWidgetManager.updateAppWidget(paramComponentName, removeViews(paramContext));
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    int i = 0;
    super.onReceive(paramContext, paramIntent);
    String str = paramIntent.getAction();
    if (str.equals("jp.co.asbit.pvstar.PROGRESS_DIALOG")) {
      if (paramIntent.getBooleanExtra("PROGRESS_SHOW", false))
      {
        progressVisibility = i;
        updateRemoteView(paramContext, AppWidgetManager.getInstance(paramContext), new ComponentName(paramContext, AppWidgetPlayerProvider.class));
      }
    }
    for (;;)
    {
      return;
      i = 8;
      break;
      if (str.equals("jp.co.asbit.pvstar.START_VIDEO"))
      {
        videoTitle = ((Video)paramIntent.getSerializableExtra("VIDEO")).getTitle();
        updateRemoteView(paramContext, AppWidgetManager.getInstance(paramContext), new ComponentName(paramContext, AppWidgetPlayerProvider.class));
      }
      else if (str.equals("jp.co.asbit.pvstar.STOP_VIDEO"))
      {
        videoTitle = null;
        updateRemoteView(paramContext, AppWidgetManager.getInstance(paramContext), new ComponentName(paramContext, AppWidgetPlayerProvider.class));
      }
      else if (str.equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_PLAY"))
      {
        Intent localIntent1 = new Intent(paramContext, AppWidgetPlayerService.class);
        localIntent1.putExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION", "jp.co.asbit.pvstar.APPWIDGET_ACTION_PLAY");
        paramContext.startService(localIntent1);
      }
      else if (str.equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_PAUSE"))
      {
        Intent localIntent2 = new Intent(paramContext, AppWidgetPlayerService.class);
        localIntent2.putExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION", "jp.co.asbit.pvstar.APPWIDGET_ACTION_PAUSE");
        paramContext.startService(localIntent2);
      }
      else if (str.equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_NEXT"))
      {
        Intent localIntent3 = new Intent(paramContext, AppWidgetPlayerService.class);
        localIntent3.putExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION", "jp.co.asbit.pvstar.APPWIDGET_ACTION_NEXT");
        paramContext.startService(localIntent3);
      }
      else if (str.equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_PREV"))
      {
        Intent localIntent4 = new Intent(paramContext, AppWidgetPlayerService.class);
        localIntent4.putExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION", "jp.co.asbit.pvstar.APPWIDGET_ACTION_PREV");
        paramContext.startService(localIntent4);
      }
    }
  }
  
  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    progressVisibility = 8;
    videoTitle = null;
    int i = paramArrayOfInt.length;
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfInt);
        return;
      }
      updateRemoteView(paramContext, paramAppWidgetManager, paramArrayOfInt[j]);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.AppWidgetPlayerProvider
 * JD-Core Version:    0.7.0.1
 */