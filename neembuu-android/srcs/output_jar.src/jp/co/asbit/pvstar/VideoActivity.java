package jp.co.asbit.pvstar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.clarion.android.appmgr.service.IClarionCallback;
import com.clarion.android.appmgr.service.IClarionCallback.Stub;
import com.clarion.android.appmgr.service.IClarionService;
import com.clarion.android.appmgr.service.IClarionService.Stub;
import java.util.ArrayList;
import jp.co.asbit.pvstar.security.ObscuredSharedPreferences;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class VideoActivity
  extends Activity
  implements SurfaceHolder.Callback
{
  private static final int NICONICO_CONFIG = 2525;
  protected static final String TAG = "VideoActivity";
  private static final int VIDEO_INFO = 1;
  private static int flagMax = 1;
  public static SurfaceHolder holder;
  public static SurfaceView preview;
  public static ImageView repeatButton;
  public static SeekBar seekBar;
  public static ImageView shuffleButton;
  public static ImageView sleepButton;
  private ImageView backButton;
  private IClarionCallback callback;
  private ServiceConnection clarionServiceConn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      VideoActivity.this.mClarionServiceIf = IClarionService.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        if (VideoActivity.this.mClarion)
        {
          VideoActivity.this.mClarionServiceIf.registerCallback(VideoActivity.this.callback, VideoActivity.this.getPackageName());
          int i = VideoActivity.this.mClarionServiceIf.getDrivingSts();
          VideoActivity.this.onChangeVehicleState(i);
        }
        else if (VideoActivity.this.mClarionServiceIf.getState() == 3)
        {
          new AsyncTask()
          {
            protected Void doInBackground(Void... paramAnonymous2VarArgs)
            {
              for (;;)
              {
                if (VideoActivity.this.mBind) {
                  return null;
                }
                try
                {
                  Thread.sleep(1000L);
                }
                catch (InterruptedException localInterruptedException)
                {
                  localInterruptedException.printStackTrace();
                }
              }
            }
            
            @SuppressLint({"InlinedApi"})
            protected void onPostExecute(Void paramAnonymous2Void)
            {
              Intent localIntent = new Intent(VideoActivity.this.mContext, Drv_PvstarActivity.class);
              localIntent.setFlags(268533760);
              VideoActivity.this.startActivity(localIntent);
              VideoActivity.this.finish();
            }
          }.execute(new Void[0]);
        }
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      VideoActivity.this.mClarionServiceIf = null;
    }
  };
  public TextView currentPosition;
  private LinearLayout driveMode;
  public TextView duration;
  private int flag = 1;
  private Handler handler = new Handler();
  public CustomDialog loadDialog;
  private BroadcastReceiver loadingReceiver;
  private Handler mAccessoryHandler;
  private boolean mBind = false;
  private boolean mClarion;
  private IClarionService mClarionServiceIf = null;
  private ServiceConnection mConnection;
  private Context mContext;
  private float mLastTouchX;
  private float mLastTouchY;
  private MyBindService mService;
  public ImageView musicMode;
  private boolean musicModeSetting;
  private boolean newPlayer = false;
  private ImageView nextButton;
  private ImageView pauseButton;
  private ImageView playButton;
  private boolean popupPlayModel;
  private BroadcastReceiver prepareReceiver;
  private ImageView prevButton;
  private ProgressDialog progressDialog;
  private Runnable progressRunnable;
  public TextView title;
  private FrameLayout videoFrame;
  private ArrayList<Video> videoList;
  private ImageView videoMenu;
  
  private void OnCLStateChange(int paramInt)
  {
    if ((paramInt != 3) && (paramInt == 0))
    {
      Intent localIntent = new Intent(this.mContext, Drv_PvstarActivity.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
      finish();
    }
  }
  
  private void addVideosToMylist()
  {
    this.videoList = getVideoList();
    if ((this.videoList == null) || (this.videoList.size() == 0)) {}
    for (;;)
    {
      return;
      Log.d("VideoActivity", currentVideo().getId());
      Log.d("VideoActivity", currentVideo().getTitle());
      Log.d("VideoActivity", currentVideo().getThumbnailUrl());
      new VideoAddDialog(this).setOnVideoAddedListener(new VideoAddDialog.OnVideoAddedListener()
      {
        public void onVideoAdded(long paramAnonymousLong)
        {
          VideoDbHelper localVideoDbHelper = new VideoDbHelper(VideoActivity.this.mContext);
          try
          {
            localVideoDbHelper.insertVideo(VideoActivity.this.currentVideo(), Long.valueOf(paramAnonymousLong));
            Toast.makeText(VideoActivity.this.mContext, 2131296433, 0).show();
            return;
          }
          catch (VideoDbHelper.MaxVideoCountException localMaxVideoCountException)
          {
            for (;;)
            {
              Toast.makeText(VideoActivity.this.mContext, localMaxVideoCountException.getMessage(), 0).show();
              localVideoDbHelper.close();
            }
          }
          catch (NullPointerException localNullPointerException)
          {
            for (;;)
            {
              localNullPointerException.printStackTrace();
              localVideoDbHelper.close();
            }
          }
          finally
          {
            localVideoDbHelper.close();
          }
        }
      }).show();
    }
  }
  
  private Video currentVideo()
  {
    try
    {
      localVideo = (Video)((ArrayList)this.mService.getVideoRowItems()).get(this.mService.getVIndex());
      return localVideo;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
        Video localVideo = null;
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      for (;;)
      {
        localIndexOutOfBoundsException.printStackTrace();
      }
    }
  }
  
  private String getPlayerUrl(String paramString1, String paramString2)
  {
    String str;
    if (paramString1.equals("youtube"))
    {
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = paramString2;
      str = String.format("http://www.youtube.com/watch?v=%s", arrayOfObject4);
    }
    for (;;)
    {
      return str;
      if (paramString1.equals("niconico"))
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = paramString2;
        str = String.format("http://www.nicovideo.jp/watch/%s", arrayOfObject3);
      }
      else if (paramString1.equals("dailymotion"))
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramString2;
        str = String.format("http://www.dailymotion.com/embed/video/%s?api=postMessage&autoplay=false&fullscreen=auto&html=false&info=false&sc_insite_webapp=true", arrayOfObject2);
      }
      else if (paramString1.equals("vimeo"))
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramString2;
        str = String.format("http://vimeo.com/%s", arrayOfObject1);
      }
      else
      {
        str = null;
      }
    }
  }
  
  private void onChangeVehicleState(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return;
      if (this.popupPlayModel)
      {
        Intent localIntent = new Intent(this.mContext, Drv_PvstarActivity.class);
        localIntent.setFlags(67108864);
        startActivity(localIntent);
        finish();
      }
      else
      {
        Animation localAnimation2 = AnimationUtils.loadAnimation(this.mContext, 2130968576);
        this.driveMode.setAnimation(localAnimation2);
        this.driveMode.setVisibility(0);
        continue;
        Animation localAnimation1 = AnimationUtils.loadAnimation(this.mContext, 2130968577);
        this.driveMode.setAnimation(localAnimation1);
        this.driveMode.setVisibility(8);
      }
    }
  }
  
  private void relatedVideos()
  {
    try
    {
      Intent localIntent = new Intent(this.mContext, RelatedVideosActivity.class);
      localIntent.setFlags(536870912);
      Video localVideo = currentVideo();
      if (localVideo != null)
      {
        localIntent.putExtra("VIDEO", localVideo);
        startActivity(localIntent);
      }
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  private void setSleepTimer()
  {
    View localView = LayoutInflater.from(this.mContext).inflate(2130903050, null);
    final TimePicker localTimePicker = (TimePicker)localView.findViewById(2131492896);
    localTimePicker.setIs24HourView(Boolean.valueOf(true));
    try
    {
      long l1 = this.mService.getSleepTimer();
      long l2 = l1;
      long l3 = 0L;
      long l4 = 0L;
      if (l2 > 0L)
      {
        l2 /= 1000L;
        l3 = l2 / 3600L;
        l4 = (l2 - 3600L * l3) / 60L;
      }
      localTimePicker.setCurrentHour(Integer.valueOf((int)l3));
      localTimePicker.setCurrentMinute(Integer.valueOf((int)l4));
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this).setTitle(2131296463).setView(localView).setPositiveButton(2131296472, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          long l1 = localTimePicker.getCurrentHour().intValue();
          long l2 = localTimePicker.getCurrentMinute().intValue();
          if ((l1 == 0L) && (l2 == 0L)) {}
          for (;;)
          {
            try
            {
              VideoActivity.this.mService.killSleepTimer();
              return;
            }
            catch (RemoteException localRemoteException2)
            {
              localRemoteException2.printStackTrace();
              continue;
            }
            catch (NullPointerException localNullPointerException2)
            {
              localNullPointerException2.printStackTrace();
              continue;
            }
            try
            {
              VideoActivity.this.mService.setSleepTimer(1000L * (60L * (l2 + l1 * 60L)));
            }
            catch (RemoteException localRemoteException1)
            {
              localRemoteException1.printStackTrace();
            }
            catch (NullPointerException localNullPointerException1)
            {
              localNullPointerException1.printStackTrace();
            }
          }
        }
      }).setNeutralButton(2131296382, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
      });
      if (l2 > 0L) {
        localBuilder.setNegativeButton(2131296475, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            try
            {
              VideoActivity.this.mService.killSleepTimer();
              return;
            }
            catch (RemoteException localRemoteException)
            {
              for (;;)
              {
                localRemoteException.printStackTrace();
              }
            }
            catch (NullPointerException localNullPointerException)
            {
              for (;;)
              {
                localNullPointerException.printStackTrace();
              }
            }
          }
        });
      }
      localBuilder.show();
      label192:
      return;
    }
    catch (RemoteException localRemoteException)
    {
      break label192;
    }
    catch (NullPointerException localNullPointerException)
    {
      break label192;
    }
  }
  
  private void setTitle(String paramString)
  {
    if ((this.mClarion) && (paramString.length() > 30)) {
      this.title.setText(paramString.substring(0, 30) + "...");
    }
    for (;;)
    {
      return;
      this.title.setText(paramString);
    }
  }
  
  private void showDetail()
  {
    Video localVideo = currentVideo();
    if (localVideo != null)
    {
      Intent localIntent = new Intent(this.mContext, VideoDetailActivity.class);
      localIntent.putExtra("VIDEO", localVideo);
      startActivity(localIntent);
      overridePendingTransition(2130968584, 2130968585);
    }
  }
  
  private void showEqualizer()
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)getSystemService("layout_inflater");
    View localView1 = localLayoutInflater.inflate(2130903048, null);
    final ViewGroup localViewGroup = (ViewGroup)localView1.findViewById(2131492890);
    final Spinner localSpinner = (Spinner)localView1.findViewById(2131492889);
    CheckBox localCheckBox = (CheckBox)localView1.findViewById(2131492888);
    try
    {
      final EqualizerConstants localEqualizerConstants = this.mService.getEqualizerConstants();
      final int i = localEqualizerConstants.getMinLevel();
      int j = localEqualizerConstants.getMaxLevel() - i;
      int k = j / 2;
      int m = localEqualizerConstants.getNumBands();
      int n = this.mService.getEqualizerPreset();
      ArrayAdapter localArrayAdapter = new ArrayAdapter(this.mContext, 17367048);
      int i1 = 0;
      int i2;
      if (i1 >= localEqualizerConstants.getNumPresets())
      {
        localArrayAdapter.add(getString(2131296454));
        localArrayAdapter.setDropDownViewResource(17367049);
        localSpinner.setAdapter(localArrayAdapter);
        localSpinner.setSelection(n);
        AdapterView.OnItemSelectedListener local21 = new AdapterView.OnItemSelectedListener()
        {
          public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            try
            {
              VideoActivity.this.mService.setEqualizerPreset(paramAnonymousInt);
              if (localEqualizerConstants.getNumPresets() > paramAnonymousInt)
              {
                localViewGroup.setVisibility(8);
              }
              else
              {
                localViewGroup.setVisibility(0);
                int[] arrayOfInt = VideoActivity.this.mService.getEqualizerCustomBandLevels();
                for (int i = 0; i < arrayOfInt.length; i++)
                {
                  ((SeekBar)localViewGroup.findViewWithTag(Integer.valueOf(i))).setProgress(arrayOfInt[i] - i);
                  VideoActivity.this.mService.setEqualizer(i, arrayOfInt[i]);
                }
              }
              return;
            }
            catch (RemoteException localRemoteException)
            {
              localRemoteException.printStackTrace();
            }
            catch (NullPointerException localNullPointerException)
            {
              localNullPointerException.printStackTrace();
            }
          }
          
          public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
        };
        localSpinner.setOnItemSelectedListener(local21);
        i2 = 0;
        if (i2 < m) {
          break label332;
        }
        CompoundButton.OnCheckedChangeListener local23 = new CompoundButton.OnCheckedChangeListener()
        {
          public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
          {
            try
            {
              VideoActivity.this.mService.setEqualizerEnabled(paramAnonymousBoolean);
              if (paramAnonymousBoolean)
              {
                if (VideoActivity.this.mService.getEqualizerPreset() > -1 + localEqualizerConstants.getNumPresets()) {
                  localViewGroup.setVisibility(0);
                }
                localSpinner.setVisibility(0);
              }
              else
              {
                localViewGroup.setVisibility(8);
                localSpinner.setVisibility(8);
              }
            }
            catch (RemoteException localRemoteException)
            {
              localRemoteException.printStackTrace();
            }
            catch (NullPointerException localNullPointerException)
            {
              localNullPointerException.printStackTrace();
            }
          }
        };
        localCheckBox.setOnCheckedChangeListener(local23);
        boolean bool = this.mService.isEqualizerEnabled();
        this.mService.setEqualizerEnabled(bool);
        localCheckBox.setChecked(bool);
        if (!bool) {
          break label575;
        }
        if (n > -1 + localEqualizerConstants.getNumPresets()) {
          localViewGroup.setVisibility(0);
        }
        localSpinner.setVisibility(0);
      }
      for (;;)
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setView(localView1).setNegativeButton(2131296470, null).show();
        return;
        localArrayAdapter.add(localEqualizerConstants.getPresetsNames()[i1]);
        i1 += 1;
        break;
        label332:
        View localView2 = localLayoutInflater.inflate(2130903064, null);
        int i3 = localEqualizerConstants.getCenterFreq()[i2];
        String str = "";
        float f2;
        if (i3 > 1000000)
        {
          f2 = i3 / 1000000.0F;
          if (f2 == (int)f2) {
            str = String.valueOf((int)f2) + "K";
          }
        }
        for (;;)
        {
          ((TextView)localView2.findViewById(2131492953)).setText(str);
          SeekBar localSeekBar = (SeekBar)localView2.findViewById(2131492954);
          localSeekBar.setMax(j);
          localSeekBar.setProgress(k);
          localSeekBar.setTag(Integer.valueOf(i2));
          SeekBar.OnSeekBarChangeListener local22 = new SeekBar.OnSeekBarChangeListener()
          {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
            {
              if (paramAnonymousBoolean) {}
              try
              {
                VideoActivity.this.mService.setEqualizer(Integer.parseInt(paramAnonymousSeekBar.getTag().toString()), paramAnonymousInt + i);
                return;
              }
              catch (RemoteException localRemoteException)
              {
                for (;;)
                {
                  localRemoteException.printStackTrace();
                }
              }
              catch (NullPointerException localNullPointerException)
              {
                for (;;)
                {
                  localNullPointerException.printStackTrace();
                }
              }
            }
            
            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
            
            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
            {
              try
              {
                VideoActivity.this.mService.saveEqualizerCustomBandLevels();
                return;
              }
              catch (RemoteException localRemoteException)
              {
                for (;;)
                {
                  localRemoteException.printStackTrace();
                }
              }
              catch (NullPointerException localNullPointerException)
              {
                for (;;)
                {
                  localNullPointerException.printStackTrace();
                }
              }
            }
          };
          localSeekBar.setOnSeekBarChangeListener(local22);
          localViewGroup.addView(localView2);
          i2 += 1;
          break;
          str = String.valueOf(f2) + "K";
          continue;
          if (i3 > 1000)
          {
            float f1 = i3 / 1000.0F;
            if (f1 == (int)f1) {
              str = String.valueOf((int)f1);
            } else {
              str = String.valueOf(f1);
            }
          }
        }
        label575:
        localViewGroup.setVisibility(8);
        localSpinner.setVisibility(8);
      }
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  private void showMenu()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new OptionMenuItem(2131296429, 2130837534));
    localArrayList.add(new OptionMenuItem(2131296430, 2130837535));
    if (Build.VERSION.SDK_INT >= 9) {
      localArrayList.add(new OptionMenuItem(2131296453, 2130837533));
    }
    localArrayList.add(new OptionMenuItem(2131296446, 2130837532));
    localArrayList.add(new OptionMenuItem(2131296444, 2130837546));
    localArrayList.add(new OptionMenuItem(2131296432, 2130837530));
    localArrayList.add(new OptionMenuItem(2131296438, 2130837545));
    localArrayList.add(new OptionMenuItem(2131296491, 2130837541));
    localArrayList.add(new OptionMenuItem(2131296262, 2130837531));
    localArrayList.add(new OptionMenuItem(2131296475, 2130837544));
    ListView localListView = new ListView(this.mContext);
    localListView.setAdapter(new OptionMenuAdapter(this.mContext, 0, localArrayList));
    localListView.setScrollingCacheEnabled(false);
    localListView.setDividerHeight(0);
    localListView.setSelector(2130837582);
    final CustomDialog localCustomDialog = new CustomDialog(this);
    localCustomDialog.requestWindowFeature(1);
    localCustomDialog.setContentView(localListView);
    localCustomDialog.getWindow().setFlags(0, 2);
    localCustomDialog.setCanceledOnTouchOutside(true);
    float f = getResources().getDisplayMetrics().density;
    localCustomDialog.getWindow().setLayout((int)(300.0F * f), -2);
    localCustomDialog.show();
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        switch (((OptionMenuItem)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt)).getTitle())
        {
        }
        for (;;)
        {
          localCustomDialog.dismiss();
          return;
          VideoActivity.this.backHome();
          continue;
          VideoActivity.this.showPlaylist();
          continue;
          VideoActivity.this.showEqualizer();
          continue;
          VideoActivity.this.showDetail();
          continue;
          VideoActivity.this.addVideosToMylist();
          continue;
          VideoActivity.this.relatedVideos();
          continue;
          VideoActivity.this.stopPlayer();
          continue;
          VideoActivity.this.tweetVideo();
          continue;
          VideoActivity.this.shareVideo();
          continue;
          Intent localIntent = new Intent(VideoActivity.this.mContext, SettingActivity.class);
          VideoActivity.this.startActivity(localIntent);
        }
      }
    });
  }
  
  private void showPlaylist()
  {
    int i;
    label21:
    String[] arrayOfString;
    int j;
    try
    {
      this.videoList = getVideoList(true);
      i = this.videoList.size();
      if (i == 0) {
        return;
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      break label21;
      arrayOfString = new String[i];
      j = 0;
    }
    for (;;)
    {
      if (j >= i)
      {
        ListView localListView = new ListView(this.mContext);
        localListView.setAdapter(new ArrayAdapter(this.mContext, 2130903083, arrayOfString));
        localListView.setScrollingCacheEnabled(false);
        localListView.setSelector(2130837582);
        localListView.setFadingEdgeLength(0);
        final CustomDialog localCustomDialog = new CustomDialog(this);
        localCustomDialog.setTitle(2131296431);
        localCustomDialog.setContentView(localListView);
        localCustomDialog.getWindow().setFlags(0, 2);
        localCustomDialog.setCanceledOnTouchOutside(true);
        localCustomDialog.show();
        localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            try
            {
              VideoActivity.this.mService.moveTrack(paramAnonymousInt);
              if ((localCustomDialog != null) && (localCustomDialog.isShowing())) {
                localCustomDialog.dismiss();
              }
              return;
            }
            catch (RemoteException localRemoteException)
            {
              for (;;)
              {
                localRemoteException.printStackTrace();
              }
            }
            catch (NullPointerException localNullPointerException)
            {
              for (;;)
              {
                localNullPointerException.printStackTrace();
              }
            }
          }
        });
        break;
      }
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = ((Video)this.videoList.get(j)).getTitle();
      arrayOfObject[1] = ((Video)this.videoList.get(j)).getDuration();
      arrayOfString[j] = String.format("%s (%s)", arrayOfObject);
      j++;
    }
  }
  
  private void stopPlayer()
  {
    stopService(new Intent(this.mContext, VideoService.class));
    stopService(new Intent(this.mContext, ProxyService.class));
    finish();
  }
  
  private void tweetVideo()
  {
    this.videoList = getVideoList();
    if ((this.videoList == null) || (this.videoList.size() == 0)) {}
    for (;;)
    {
      return;
      ObscuredSharedPreferences localObscuredSharedPreferences = new ObscuredSharedPreferences(this.mContext, PreferenceManager.getDefaultSharedPreferences(this.mContext));
      String str1 = localObscuredSharedPreferences.getString("twitter_token", null);
      String str2 = localObscuredSharedPreferences.getString("twitter_token_secret", null);
      try
      {
        final AccessToken localAccessToken = new AccessToken(str1, str2);
        final CustomDialog localCustomDialog = new CustomDialog(this);
        localCustomDialog.requestWindowFeature(1);
        localCustomDialog.setContentView(2130903052);
        localCustomDialog.getWindow().setFlags(0, 2);
        final EditText localEditText = (EditText)localCustomDialog.findViewById(2131492900);
        final TextView localTextView = (TextView)localCustomDialog.findViewById(2131492901);
        Video localVideo = currentVideo();
        String str3 = getPlayerUrl(localVideo.getSearchEngine(), localVideo.getId());
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localVideo.getTitle();
        arrayOfObject[1] = str3;
        String str4 = String.format("%s %s #nowplaying #pvstar", arrayOfObject);
        TextWatcher local28 = new TextWatcher()
        {
          public void afterTextChanged(Editable paramAnonymousEditable) {}
          
          public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
          
          public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          {
            TextView localTextView = localTextView;
            VideoActivity localVideoActivity = VideoActivity.this;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(140 - localEditText.getText().length());
            localTextView.setText(localVideoActivity.getString(2131296439, arrayOfObject));
          }
        };
        localEditText.addTextChangedListener(local28);
        localEditText.setText(str4);
        float f = getResources().getDisplayMetrics().density;
        localCustomDialog.getWindow().setLayout((int)(300.0F * f), -2);
        Button localButton = (Button)localCustomDialog.findViewById(2131492902);
        View.OnTouchListener local29 = new View.OnTouchListener()
        {
          public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
          {
            if (paramAnonymousMotionEvent.getAction() == 1)
            {
              ConfigurationBuilder localConfigurationBuilder = new ConfigurationBuilder();
              localConfigurationBuilder.setOAuthConsumerKey("TuSlIQOIqG8eM4zDXUPg");
              localConfigurationBuilder.setOAuthConsumerSecret("4RGsoG6wXXwcWdeN550kLm1um8Dl0BWYbjdIbubGo");
              final Twitter localTwitter = new TwitterFactory(localConfigurationBuilder.build()).getInstance(localAccessToken);
              String str = localEditText.getText().toString();
              AsyncTask local1 = new AsyncTask()
              {
                protected Status doInBackground(String... paramAnonymous2VarArgs)
                {
                  try
                  {
                    Status localStatus2 = localTwitter.updateStatus(paramAnonymous2VarArgs[0]);
                    localStatus1 = localStatus2;
                  }
                  catch (TwitterException localTwitterException)
                  {
                    for (;;)
                    {
                      localTwitterException.printStackTrace();
                      Status localStatus1 = null;
                    }
                  }
                  return localStatus1;
                }
                
                protected void onPostExecute(Status paramAnonymous2Status)
                {
                  super.onPostExecute(paramAnonymous2Status);
                  if ((VideoActivity.this.progressDialog != null) && (VideoActivity.this.progressDialog.isShowing())) {
                    VideoActivity.this.progressDialog.dismiss();
                  }
                  if (paramAnonymous2Status == null) {
                    Toast.makeText(VideoActivity.this.mContext, 2131296466, 1).show();
                  }
                  for (;;)
                  {
                    return;
                    Toast.makeText(VideoActivity.this.mContext, 2131296467, 1).show();
                  }
                }
                
                protected void onPreExecute()
                {
                  VideoActivity.this.progressDialog = new ProgressDialog(VideoActivity.this);
                  VideoActivity.this.progressDialog.setMessage(VideoActivity.this.getString(2131296497));
                  VideoActivity.this.progressDialog.setCancelable(false);
                  VideoActivity.this.progressDialog.setProgressStyle(0);
                  VideoActivity.this.progressDialog.show();
                  super.onPreExecute();
                }
              };
              String[] arrayOfString = new String[1];
              arrayOfString[0] = str;
              local1.execute(arrayOfString);
              localCustomDialog.dismiss();
            }
            return false;
          }
        };
        localButton.setOnTouchListener(local29);
        localCustomDialog.show();
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localIllegalArgumentException.printStackTrace();
        AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
        AlertDialog.Builder localBuilder2 = localBuilder1.setTitle(2131296336).setMessage(2131296465);
        DialogInterface.OnClickListener local30 = new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            Intent localIntent = new Intent(VideoActivity.this.mContext, SettingTwitterActivity.class);
            VideoActivity.this.startActivity(localIntent);
          }
        };
        localBuilder2.setPositiveButton(2131296470, local30).setNegativeButton(2131296382, null).show();
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          localIllegalStateException.printStackTrace();
        }
      }
      catch (NullPointerException localNullPointerException)
      {
        for (;;)
        {
          localNullPointerException.printStackTrace();
        }
      }
    }
  }
  
  protected void OnCLNotifyCommand(int paramInt1, int paramInt2, Object paramObject)
  {
    if ((paramInt1 == 3) && (paramInt2 == 2)) {}
    try
    {
      if (this.mClarionServiceIf != null) {
        onChangeVehicleState(this.mClarionServiceIf.getDrivingSts());
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
  }
  
  protected void backHome()
  {
    Intent localIntent = new Intent(this.mContext, PvstarActivity.class);
    localIntent.setFlags(67108864);
    startActivity(localIntent);
    finish();
    overridePendingTransition(2130968582, 2130968583);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mClarion) && (this.popupPlayModel)) {
      switch (paramMotionEvent.getAction())
      {
      }
    }
    for (boolean bool = false;; bool = super.dispatchTouchEvent(paramMotionEvent))
    {
      for (;;)
      {
        return bool;
        this.mLastTouchX = paramMotionEvent.getX();
        this.mLastTouchY = paramMotionEvent.getY();
        break;
        int i = (int)Math.abs(paramMotionEvent.getY() - this.mLastTouchY);
        int j = (int)(paramMotionEvent.getX() - this.mLastTouchX);
        if ((j > 100) && (i < 100)) {}
        try
        {
          this.mService.fprev();
        }
        catch (RemoteException localRemoteException)
        {
          localRemoteException.printStackTrace();
          break;
          if ((j >= -100) || (i >= 100)) {
            break label159;
          }
          this.mService.next();
        }
        catch (NullPointerException localNullPointerException)
        {
          localNullPointerException.printStackTrace();
        }
      }
      break;
      label159:
      if (this.mService.isPlaying())
      {
        this.mService.pause();
        break;
      }
      this.mService.play();
      break;
    }
  }
  
  public ArrayList<Video> getVideoList()
  {
    return getVideoList(false);
  }
  
  public ArrayList<Video> getVideoList(boolean paramBoolean)
  {
    if ((this.videoList == null) || (paramBoolean)) {}
    try
    {
      this.videoList = ((ArrayList)this.mService.getVideoRowItems());
      return this.videoList;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    try
    {
      this.mService.videoSizeChange();
      super.onConfigurationChanged(paramConfiguration);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  @SuppressLint({"HandlerLeak"})
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mClarion = getIntent().getBooleanExtra("CLARION_MODE", false);
    this.mContext = getApplicationContext();
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
    setRequestedOrientation(Integer.parseInt(localSharedPreferences.getString("screen_orientation", "-1")));
    if ((!localSharedPreferences.getBoolean("status_bar", false)) || (this.mClarion)) {
      getWindow().addFlags(1024);
    }
    getWindow().addFlags(128);
    getWindow().setFormat(-2);
    setContentView(2130903093);
    setVolumeControlStream(3);
    preview = (SurfaceView)findViewById(2131492928);
    holder = preview.getHolder();
    holder.setType(3);
    holder.addCallback(this);
    this.videoFrame = ((FrameLayout)findViewById(2131492927));
    this.videoFrame.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (paramAnonymousMotionEvent.getAction() == 1)
        {
          VideoActivity localVideoActivity = VideoActivity.this;
          int i = 1 + localVideoActivity.flag;
          localVideoActivity.flag = i;
          if (i > VideoActivity.flagMax) {
            VideoActivity.this.flag = 0;
          }
          VideoActivity.this.updateVisibleInfo();
        }
        return false;
      }
    });
    this.videoMenu = ((ImageView)findViewById(2131493001));
    this.videoMenu.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        VideoActivity.this.showMenu();
      }
    });
    this.mConnection = new ServiceConnection()
    {
      public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
      {
        VideoActivity.this.mService = MyBindService.Stub.asInterface(paramAnonymousIBinder);
        VideoActivity.this.mBind = true;
      }
      
      public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
      {
        VideoActivity.this.mService = null;
      }
    };
    this.playButton = ((ImageView)findViewById(2131492939));
    this.playButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          VideoActivity.this.mService.play();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;)
          {
            localRemoteException.printStackTrace();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    });
    this.pauseButton = ((ImageView)findViewById(2131492940));
    this.pauseButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          VideoActivity.this.mService.pause();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;)
          {
            localRemoteException.printStackTrace();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    });
    this.prevButton = ((ImageView)findViewById(2131492938));
    this.prevButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          VideoActivity.this.mService.prev();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;)
          {
            localRemoteException.printStackTrace();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    });
    this.nextButton = ((ImageView)findViewById(2131492941));
    this.nextButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          VideoActivity.this.mService.next();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;)
          {
            localRemoteException.printStackTrace();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    });
    repeatButton = (ImageView)findViewById(2131492935);
    repeatButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          int i = 1 + VideoActivity.this.mService.getRepeatState();
          if (i > 2) {
            i = 0;
          }
          PreferenceManager.getDefaultSharedPreferences(VideoActivity.this.mContext).edit().putInt("repeat2", i).commit();
          VideoActivity.this.mService.setRepeatState(i);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;)
          {
            localRemoteException.printStackTrace();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    });
    shuffleButton = (ImageView)findViewById(2131492936);
    shuffleButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          if (VideoActivity.this.mService.getShuffle()) {}
          for (boolean bool = false;; bool = true)
          {
            PreferenceManager.getDefaultSharedPreferences(VideoActivity.this.mContext).edit().putBoolean("shuffle", bool).commit();
            VideoActivity.this.mService.setShuffle(bool);
            return;
          }
        }
        catch (RemoteException localRemoteException)
        {
          for (;;)
          {
            localRemoteException.printStackTrace();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    });
    sleepButton = (ImageView)findViewById(2131492937);
    sleepButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        VideoActivity.this.setSleepTimer();
      }
    });
    this.backButton = ((ImageView)findViewById(2131492933));
    this.backButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        VideoActivity.this.finish();
      }
    });
    seekBar = (SeekBar)findViewById(2131492943);
    seekBar.setMax(100);
    seekBar.setProgress(0);
    seekBar.setSecondaryProgress(0);
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {}
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
        VideoActivity.this.stopProgressRunnable();
      }
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
        try
        {
          VideoActivity.this.loadDialog.show();
        }
        catch (Exception localException)
        {
          try
          {
            for (;;)
            {
              VideoActivity.this.mService.seekTo(paramAnonymousSeekBar.getProgress());
              VideoActivity.this.mService.play();
              label38:
              VideoActivity.this.startProgressRunnable();
              return;
              localException = localException;
              localException.printStackTrace();
            }
          }
          catch (NullPointerException localNullPointerException)
          {
            break label38;
          }
          catch (RemoteException localRemoteException)
          {
            break label38;
          }
        }
      }
    });
    this.loadDialog = new CustomDialog(this);
    this.loadDialog.requestWindowFeature(1);
    this.loadDialog.setContentView(2130903077);
    this.loadDialog.getWindow().setFlags(0, 2);
    this.loadDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        try
        {
          VideoActivity.this.mService.cancelLoading();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;)
          {
            localRemoteException.printStackTrace();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    });
    this.title = ((TextView)findViewById(2131492932));
    this.duration = ((TextView)findViewById(2131492944));
    this.currentPosition = ((TextView)findViewById(2131492942));
    this.musicMode = ((ImageView)findViewById(2131492930));
    if (this.mClarion)
    {
      this.title.setTextSize(17.0F);
      this.duration.setTextSize(18.0F);
      this.currentPosition.setTextSize(18.0F);
      this.popupPlayModel = Util.isPopUpPlayModel(this.mContext);
      if (this.popupPlayModel)
      {
        this.flag = 0;
        Toast.makeText(this.mContext, 2131296514, 1).show();
      }
      this.musicModeSetting = localSharedPreferences.getBoolean("music_mode", false);
      localSharedPreferences.edit().putBoolean("music_mode", false).commit();
      this.driveMode = ((LinearLayout)findViewById(2131492929));
      sleepButton.setVisibility(8);
      this.videoMenu.setVisibility(8);
      this.backButton.setVisibility(0);
      this.mAccessoryHandler = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default: 
            super.handleMessage(paramAnonymousMessage);
          }
          for (;;)
          {
            return;
            VideoActivity.this.OnCLNotifyCommand(paramAnonymousMessage.arg1, paramAnonymousMessage.arg2, paramAnonymousMessage.obj);
            continue;
            VideoActivity.this.OnCLStateChange(paramAnonymousMessage.arg1);
          }
        }
      };
      this.callback = new IClarionCallback.Stub()
      {
        public void onAccessoryNotify(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int[] paramAnonymousArrayOfInt, String paramAnonymousString)
          throws RemoteException
        {
          VideoActivity.this.mAccessoryHandler.sendMessage(VideoActivity.this.mAccessoryHandler.obtainMessage(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousArrayOfInt));
        }
      };
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    showMenu();
    return false;
  }
  
  protected void onDestroy()
  {
    if (this.mClarion) {
      PreferenceManager.getDefaultSharedPreferences(this.mContext).edit().putBoolean("music_mode", this.musicModeSetting).commit();
    }
    if (this.mBind)
    {
      unbindService(this.mConnection);
      this.mBind = false;
    }
    try
    {
      this.playButton.setOnClickListener(null);
      this.pauseButton.setOnClickListener(null);
      this.prevButton.setOnClickListener(null);
      this.nextButton.setOnClickListener(null);
      repeatButton.setOnClickListener(null);
      shuffleButton.setOnClickListener(null);
      sleepButton.setOnClickListener(null);
      this.backButton.setOnClickListener(null);
      seekBar.setOnSeekBarChangeListener(null);
      this.videoFrame.setOnTouchListener(null);
      this.videoMenu.setOnClickListener(null);
      this.mConnection = null;
      this.progressRunnable = null;
      this.progressDialog = null;
      super.onDestroy();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
  }
  
  public boolean onSearchRequested()
  {
    return false;
  }
  
  protected void onStart()
  {
    super.onStart();
    this.newPlayer = false;
    Intent localIntent = getIntent();
    if (localIntent.hasExtra("VIDEO_LIST"))
    {
      this.newPlayer = true;
      ArrayList localArrayList = (ArrayList)localIntent.getSerializableExtra("VIDEO_LIST");
      localIntent.removeExtra("VIDEO_LIST");
      this.videoList = localArrayList;
    }
    updateVisibleInfo();
    bindService(new Intent(IClarionService.class.getName()), this.clarionServiceConn, 1);
    this.loadingReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        if (paramAnonymousIntent.getBooleanExtra("PROGRESS_SHOW", false)) {}
        try
        {
          if ((VideoActivity.this.loadDialog == null) || (VideoActivity.this.loadDialog.isShowing())) {
            return;
          }
          VideoActivity.this.loadDialog.show();
        }
        catch (WindowManager.BadTokenException localBadTokenException)
        {
          localBadTokenException.printStackTrace();
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          localIllegalArgumentException.printStackTrace();
        }
        if ((VideoActivity.this.loadDialog != null) && (VideoActivity.this.loadDialog.isShowing())) {
          VideoActivity.this.loadDialog.dismiss();
        }
      }
    };
    IntentFilter localIntentFilter1 = new IntentFilter();
    localIntentFilter1.addAction("jp.co.asbit.pvstar.PROGRESS_DIALOG");
    registerReceiver(this.loadingReceiver, localIntentFilter1);
    this.prepareReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        int i = 0;
        Video localVideo = (Video)paramAnonymousIntent.getSerializableExtra("VIDEO");
        VideoActivity.this.setTitle(localVideo.getTitle());
        if (localVideo.getDuration() != null) {
          VideoActivity.this.duration.setText(localVideo.getDuration());
        }
        VideoActivity.this.currentPosition.setText("00:00");
        VideoActivity.seekBar.setSecondaryProgress(0);
        VideoActivity.seekBar.setProgress(0);
        boolean bool = paramAnonymousIntent.getBooleanExtra("music_mode", false);
        ImageView localImageView = VideoActivity.this.musicMode;
        if (bool) {}
        for (;;)
        {
          localImageView.setVisibility(i);
          return;
          i = 8;
        }
      }
    };
    IntentFilter localIntentFilter2 = new IntentFilter();
    localIntentFilter2.addAction("jp.co.asbit.pvstar.PREPARE_VIDEO");
    registerReceiver(this.prepareReceiver, localIntentFilter2);
  }
  
  protected void onStop()
  {
    super.onStop();
    if ((this.mClarionServiceIf != null) && (this.callback != null)) {}
    try
    {
      this.mClarionServiceIf.unregisterCallback(this.callback, getPackageName());
      unbindService(this.clarionServiceConn);
      unregisterReceiver(this.loadingReceiver);
      unregisterReceiver(this.prepareReceiver);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
  }
  
  protected void shareVideo()
  {
    Video localVideo = currentVideo();
    if (localVideo == null) {}
    for (;;)
    {
      return;
      String str = getPlayerUrl(localVideo.getSearchEngine(), localVideo.getId());
      Intent localIntent = new Intent("android.intent.action.SEND");
      localIntent.putExtra("android.intent.extra.TEXT", localVideo.getTitle() + " " + str);
      localIntent.setType("text/plain");
      startActivity(localIntent);
    }
  }
  
  public void startProgressRunnable()
  {
    this.progressRunnable = new Runnable()
    {
      public void run()
      {
        try
        {
          if (VideoActivity.this.mService != null)
          {
            int i = VideoActivity.this.mService.getCurrentPosition();
            int j = VideoActivity.this.mService.getDuration();
            final float f = 100.0F * (i / j);
            int k = i / 1000;
            int m = k / 60;
            int n = k % 60;
            if (m > 2880)
            {
              m = 0;
              n = 0;
            }
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = Integer.valueOf(m);
            arrayOfObject[1] = Integer.valueOf(n);
            final String str = String.format("%02d:%02d", arrayOfObject);
            VideoActivity.seekBar.post(new Runnable()
            {
              public void run()
              {
                VideoActivity.seekBar.setProgress((int)f);
                VideoActivity.this.currentPosition.setText(str);
              }
            });
            label138:
            VideoActivity.this.handler.postDelayed(this, 1000L);
          }
        }
        catch (RemoteException localRemoteException)
        {
          break label138;
        }
      }
    };
    this.handler.postDelayed(this.progressRunnable, 1000L);
  }
  
  protected void startVideoService()
  {
    this.videoList = getVideoList();
    Intent localIntent = new Intent(this.mContext, VideoService.class);
    if ((this.newPlayer) && (this.videoList != null)) {
      localIntent.putExtra("VIDEO_LIST", this.videoList);
    }
    startService(localIntent);
    if (!this.mBind) {
      bindService(localIntent, this.mConnection, 0);
    }
    startService(new Intent(this.mContext, ProxyService.class));
  }
  
  protected void stopProgressRunnable()
  {
    this.handler.removeCallbacks(this.progressRunnable);
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (!this.mClarion) {}
    try
    {
      this.videoList = getVideoList();
      boolean bool = PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean("niconico_logintest", false);
      i = 0;
      int j = this.videoList.size();
      if (i >= j)
      {
        startVideoService();
        startProgressRunnable();
      }
      for (;;)
      {
        return;
        if ((!((Video)this.videoList.get(i)).getSearchEngine().equals("niconico")) || (bool)) {
          break;
        }
        new AlertDialog.Builder(this).setTitle(2131296343).setMessage(2131296344).setPositiveButton(2131296270, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            Intent localIntent = new Intent(VideoActivity.this, SettingNiconicoActivity.class);
            VideoActivity.this.startActivityForResult(localIntent, 2525);
            paramAnonymousDialogInterface.dismiss();
          }
        }).setNeutralButton(2131296507, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://px.moba8.net/svt/ejp?a8mat=1ZVIIF+43NLW2+SPC+60H7N&guid=on"));
            VideoActivity.this.startActivity(localIntent);
          }
        }).show();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        int i;
        localNullPointerException.printStackTrace();
        continue;
        i++;
      }
    }
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    try
    {
      this.mService.setBindFlag(false);
      if (this.mBind)
      {
        unbindService(this.mConnection);
        this.mBind = false;
      }
      stopProgressRunnable();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
  }
  
  protected void updateVisibleInfo()
  {
    Animation localAnimation = AnimationUtils.loadAnimation(this.mContext, 2130968577);
    View localView1 = findViewById(2131492931);
    localView1.clearAnimation();
    int i = localView1.getVisibility();
    View localView2 = findViewById(2131492934);
    localView2.clearAnimation();
    if (((0x1 & this.flag) != 0) && (i == 8))
    {
      localView1.setVisibility(0);
      localView2.setVisibility(0);
    }
    for (;;)
    {
      return;
      if (((0x1 & this.flag) == 0) && (i == 0))
      {
        localView1.startAnimation(localAnimation);
        localView1.setVisibility(8);
        localView2.startAnimation(localAnimation);
        localView2.setVisibility(8);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoActivity
 * JD-Core Version:    0.7.0.1
 */