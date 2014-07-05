package jp.co.asbit.pvstar;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RejectedExecutionException;
import jp.co.asbit.pvstar.api.VideoViewCount;
import jp.co.asbit.pvstar.security.ObscuredSharedPreferences;
import jp.co.asbit.pvstar.video.VideoUrl;

public class VideoService
  extends Service
  implements MediaPlayer.OnCompletionListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnBufferingUpdateListener
{
  private static final int HELLO_ID = 1;
  private static final String TAG = "VideoService";
  private int audioSessionId = 0;
  private Equalizer eq = null;
  private IntentFilter filter;
  private Handler handler = new Handler();
  private boolean mBind = false;
  private Context mContext;
  private final MyBindService.Stub mMyBindService = new MyBindService.Stub()
  {
    public void cancelLoading()
    {
      if (VideoService.this.prepareMediaPlayer != null) {
        VideoService.this.prepareMediaPlayer.cancel(false);
      }
    }
    
    public void fprev()
    {
      try
      {
        Video localVideo = VideoService.this.prevVideo();
        if (localVideo == null)
        {
          if (VideoService.this.mp != null)
          {
            VideoService.this.broadcastOpenDialog();
            VideoService.this.mp.seekTo(0);
          }
        }
        else {
          VideoService.this.play(localVideo, 0);
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        localIllegalStateException.printStackTrace();
      }
    }
    
    public int getCurrentPosition()
    {
      try
      {
        if (VideoService.this.mp == null) {
          break label32;
        }
        int j = VideoService.this.mp.getCurrentPosition();
        i = j;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          localIllegalStateException.printStackTrace();
          int i = 0;
        }
      }
      catch (NullPointerException localNullPointerException)
      {
        for (;;)
        {
          label32:
          localNullPointerException.printStackTrace();
        }
      }
      return i;
    }
    
    public int getDuration()
    {
      try
      {
        if (VideoService.this.mp == null) {
          break label30;
        }
        int j = VideoService.this.mp.getDuration();
        i = j;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          localIllegalStateException.printStackTrace();
          label30:
          int i = 0;
        }
      }
      return i;
    }
    
    @SuppressLint({"NewApi"})
    public EqualizerConstants getEqualizerConstants()
      throws RemoteException
    {
      try
      {
        if (VideoService.this.eq == null)
        {
          VideoService.this.audioSessionId = VideoService.this.mp.getAudioSessionId();
          VideoService.this.eq = new Equalizer(2147483647, VideoService.this.audioSessionId);
        }
        EqualizerConstants localEqualizerConstants = new EqualizerConstants();
        localEqualizerConstants.setMaxLevel(VideoService.this.eq.getBandLevelRange()[1]);
        localEqualizerConstants.setMinLevel(VideoService.this.eq.getBandLevelRange()[0]);
        localEqualizerConstants.setNumBands(VideoService.this.eq.getNumberOfBands());
        int[] arrayOfInt = new int[localEqualizerConstants.getNumBands()];
        short s1 = 0;
        String[] arrayOfString;
        short s2;
        if (s1 >= VideoService.this.eq.getNumberOfBands())
        {
          localEqualizerConstants.setCenterFreq(arrayOfInt);
          localEqualizerConstants.setNumPresets(VideoService.this.eq.getNumberOfPresets());
          arrayOfString = new String[localEqualizerConstants.getNumPresets()];
          s2 = 0;
        }
        for (;;)
        {
          if (s2 >= VideoService.this.eq.getNumberOfPresets())
          {
            localEqualizerConstants.setPresetsNames(arrayOfString);
            return localEqualizerConstants;
            arrayOfInt[s1] = VideoService.this.eq.getCenterFreq(s1);
            int i;
            s1 += 1;
            break;
          }
          arrayOfString[s2] = VideoService.this.eq.getPresetName(s2);
          int j;
          s2 += 1;
        }
        return localEqualizerConstants;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localEqualizerConstants = null;
      }
    }
    
    @SuppressLint({"NewApi"})
    public int[] getEqualizerCustomBandLevels()
      throws RemoteException
    {
      return Util.loadEqualizerBandsLevel(VideoService.this.mContext);
    }
    
    @SuppressLint({"NewApi"})
    public int getEqualizerPreset()
      throws RemoteException
    {
      return Util.loadEqualizerCurrentPreset(VideoService.this.mContext);
    }
    
    public int getRepeatState()
      throws RemoteException
    {
      return VideoService.this.repeat;
    }
    
    public boolean getShuffle()
    {
      return VideoService.this.shuffle;
    }
    
    public long getSleepTimer()
    {
      return VideoService.this.getSleepTimer();
    }
    
    public int getVIndex()
    {
      return VideoService.this.vIndex;
    }
    
    public ArrayList<Video> getVideoRowItems()
    {
      return VideoService.this.videoList;
    }
    
    public boolean isEqualizerEnabled()
      throws RemoteException
    {
      return Util.isEqualizerEnabled(VideoService.this.mContext);
    }
    
    public boolean isPlaying()
    {
      try
      {
        if (VideoService.this.mp == null) {
          break label30;
        }
        boolean bool2 = VideoService.this.mp.isPlaying();
        bool1 = bool2;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          localIllegalStateException.printStackTrace();
          label30:
          boolean bool1 = false;
        }
      }
      return bool1;
    }
    
    public void killSleepTimer()
    {
      VideoService.this.killSleepTimer();
    }
    
    public void moveTrack(int paramAnonymousInt)
    {
      if (VideoService.this.videoExists(paramAnonymousInt))
      {
        VideoService.this.vIndex = paramAnonymousInt;
        Video localVideo = VideoService.this.currentVideo();
        VideoService.this.play(localVideo, 0);
      }
    }
    
    public void next()
    {
      Video localVideo = VideoService.this.nextVideo();
      if (localVideo == null) {}
      for (;;)
      {
        return;
        VideoService.this.play(localVideo, 0);
      }
    }
    
    public void pause()
    {
      try
      {
        if ((VideoService.this.mp != null) && (VideoService.this.mp.isPlaying())) {
          VideoService.this.mp.pause();
        }
        return;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          localIllegalStateException.printStackTrace();
        }
      }
    }
    
    public void play()
    {
      try
      {
        if (VideoService.this.mp != null)
        {
          if (!VideoService.this.mp.isPlaying()) {
            VideoService.this.mp.start();
          }
        }
        else {
          VideoService.this.play(VideoService.access$1(VideoService.this), 0, true);
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        localIllegalStateException.printStackTrace();
      }
    }
    
    public void prev()
    {
      try
      {
        if ((VideoService.this.mp != null) && (VideoService.this.mp.getCurrentPosition() > 4000))
        {
          VideoService.this.broadcastOpenDialog();
          VideoService.this.mp.seekTo(0);
        }
        else
        {
          fprev();
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        localIllegalStateException.printStackTrace();
      }
    }
    
    @SuppressLint({"NewApi"})
    public void saveEqualizerCustomBandLevels()
      throws RemoteException
    {
      try
      {
        short[] arrayOfShort = new short[VideoService.this.eq.getNumberOfBands()];
        short s = 0;
        for (;;)
        {
          if (s >= VideoService.this.eq.getNumberOfBands())
          {
            Util.saveEqualizerBandsLevel(arrayOfShort, VideoService.this.mContext);
            break;
          }
          arrayOfShort[s] = VideoService.this.eq.getBandLevel(s);
          int i;
          s += 1;
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    
    public void seekTo(final int paramAnonymousInt)
    {
      new AsyncTask()
      {
        protected String doInBackground(Integer... paramAnonymous2VarArgs)
        {
          try
          {
            if (VideoService.this.mp != null)
            {
              float f = VideoService.this.mp.getDuration() * (paramAnonymousInt / 100.0F);
              VideoService.this.mp.seekTo((int)f);
            }
            return null;
          }
          catch (IllegalStateException localIllegalStateException)
          {
            for (;;)
            {
              localIllegalStateException.printStackTrace();
            }
          }
        }
      }.execute(new Integer[0]);
    }
    
    public void setBindFlag(boolean paramAnonymousBoolean)
    {
      try
      {
        VideoService.this.mBind = paramAnonymousBoolean;
        if ((!VideoService.this.mBind) && (VideoService.this.mp != null)) {
          if (VideoService.this.pauseOnBackground) {
            VideoService.this.play(VideoService.access$1(VideoService.this), VideoService.this.mp.getCurrentPosition(), VideoService.this.mp.isPlaying(), false);
          } else {
            VideoService.this.mp.setDisplay(null);
          }
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        localIllegalStateException.printStackTrace();
      }
    }
    
    /**
     * @deprecated
     */
    @SuppressLint({"NewApi"})
    public void setEqualizer(int paramAnonymousInt1, int paramAnonymousInt2)
      throws RemoteException
    {
      try
      {
        VideoService.this.eq.setBandLevel((short)paramAnonymousInt1, (short)paramAnonymousInt2);
        return;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          localException.printStackTrace();
        }
      }
      finally {}
    }
    
    @SuppressLint({"NewApi"})
    public void setEqualizerEnabled(boolean paramAnonymousBoolean)
      throws RemoteException
    {
      Util.setEqualizerEnabled(VideoService.this.mContext, paramAnonymousBoolean);
      if (paramAnonymousBoolean) {}
      try
      {
        if (VideoService.this.eq == null)
        {
          VideoService.this.audioSessionId = VideoService.this.mp.getAudioSessionId();
          VideoService.this.eq = new Equalizer(2147483647, VideoService.this.audioSessionId);
        }
        while (paramAnonymousBoolean != VideoService.this.eq.getEnabled())
        {
          Log.d("VideoService", "イコライザー設定が変更されました。");
          VideoService.this.eq.setEnabled(paramAnonymousBoolean);
          break;
          if (VideoService.this.audioSessionId != VideoService.this.mp.getAudioSessionId())
          {
            VideoService.this.eq.release();
            VideoService.this.eq = null;
            VideoService.this.audioSessionId = VideoService.this.mp.getAudioSessionId();
            VideoService.this.eq = new Equalizer(2147483647, VideoService.this.audioSessionId);
          }
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    
    @SuppressLint({"NewApi"})
    public void setEqualizerPreset(int paramAnonymousInt)
      throws RemoteException
    {
      try
      {
        VideoService.this.eq.usePreset((short)paramAnonymousInt);
        Util.saveEqualizerCurrentPreset(paramAnonymousInt, VideoService.this.mContext);
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        for (;;)
        {
          Util.saveEqualizerCurrentPreset(paramAnonymousInt, VideoService.this.mContext);
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          localException.printStackTrace();
        }
      }
    }
    
    public void setRepeatState(int paramAnonymousInt)
      throws RemoteException
    {
      VideoService.this.repeat = paramAnonymousInt;
      VideoService.this.setRepeatButton(VideoService.this.repeat);
    }
    
    public void setShuffle(boolean paramAnonymousBoolean)
    {
      VideoService.this.shuffle = paramAnonymousBoolean;
      if (VideoService.this.shuffle) {
        VideoService.this.shuffleVideoList(true);
      }
      VideoService.this.setShuffleButton(VideoService.this.shuffle);
    }
    
    public void setSleepTimer(long paramAnonymousLong)
    {
      killSleepTimer();
      VideoService.this.setSleepTimer(paramAnonymousLong);
    }
    
    public void videoSizeChange()
    {
      try
      {
        VideoService.this.videoSizeChange();
        return;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          localIllegalStateException.printStackTrace();
        }
      }
    }
  };
  private MediaPlayer mp = null;
  private boolean musicMode = false;
  private boolean musicModeNoLoad = false;
  private boolean newPlayer = false;
  private BroadcastReceiver onBecomingNoisy;
  private boolean onStart = false;
  private boolean pauseOnBackground = false;
  private PrepareMediaPlayer prepareMediaPlayer;
  private boolean pseudoSurface = false;
  private int repeat = 0;
  private boolean shuffle = false;
  private long startTime;
  private long startTimestamp;
  private View surfaceLayout;
  private Timer timer;
  private int vIndex = 0;
  private ArrayList<Video> videoList;
  private WifiManager.WifiLock wifilock;
  
  private SurfaceHolder addSurfaceHolderLayer()
  {
    if (this.surfaceLayout == null)
    {
      this.surfaceLayout = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903089, null);
      WindowManager localWindowManager = (WindowManager)getSystemService("window");
      WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(1, 1, 2005, 16416, -2);
      localLayoutParams.gravity = 83;
      localWindowManager.addView(this.surfaceLayout, localLayoutParams);
    }
    SurfaceHolder localSurfaceHolder = ((SurfaceView)this.surfaceLayout.findViewById(2131492928)).getHolder();
    localSurfaceHolder.setType(3);
    return localSurfaceHolder;
  }
  
  private boolean areVideosAllError()
  {
    boolean bool1;
    try
    {
      Iterator localIterator = this.videoList.iterator();
      boolean bool2;
      do
      {
        if (!localIterator.hasNext())
        {
          bool1 = true;
          break;
        }
        bool2 = ((Video)localIterator.next()).isError();
      } while (bool2);
      bool1 = false;
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
      bool1 = true;
    }
    return bool1;
  }
  
  private void broadcastCloseDialog()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("PROGRESS_SHOW", false);
    localIntent.setAction("jp.co.asbit.pvstar.PROGRESS_DIALOG");
    sendBroadcast(localIntent);
  }
  
  private void broadcastOpenDialog()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("PROGRESS_SHOW", true);
    localIntent.setAction("jp.co.asbit.pvstar.PROGRESS_DIALOG");
    sendBroadcast(localIntent);
  }
  
  private void broadcastStartVideo(Video paramVideo)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("VIDEO", paramVideo);
    localIntent.setAction("jp.co.asbit.pvstar.START_VIDEO");
    sendBroadcast(localIntent);
    notificationCtrl(paramVideo);
  }
  
  private Video currentVideo()
  {
    if (videoExists(this.vIndex)) {}
    for (Video localVideo = (Video)this.videoList.get(this.vIndex);; localVideo = null) {
      return localVideo;
    }
  }
  
  private Uri getVideoUrl(Video paramVideo)
  {
    VideoUrl localVideoUrl = new VideoUrl(this.mContext);
    String str1 = paramVideo.getSearchEngine();
    String str2 = paramVideo.getId();
    String str3 = str1 + "_id";
    String str4 = str1 + "_passwd";
    ObscuredSharedPreferences localObscuredSharedPreferences = new ObscuredSharedPreferences(this.mContext, PreferenceManager.getDefaultSharedPreferences(this.mContext));
    String str5;
    if ((localObscuredSharedPreferences.contains(str3)) && (localObscuredSharedPreferences.contains(str4)))
    {
      str5 = localVideoUrl.get(str1, str2, localObscuredSharedPreferences.getString(str3, Util.empty()), localObscuredSharedPreferences.getString(str4, Util.empty()));
      if (str5 == null) {
        break label163;
      }
    }
    label163:
    for (Uri localUri = Uri.parse(str5);; localUri = null)
    {
      return localUri;
      str5 = localVideoUrl.get(str1, str2);
      break;
    }
  }
  
  private void loadLastVideoList()
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    this.videoList = localVideoDbHelper.getLastPlaylist();
    localVideoDbHelper.close();
  }
  
  private Video nextVideo()
  {
    if (videoExists(1 + this.vIndex)) {
      this.vIndex = (1 + this.vIndex);
    }
    for (Video localVideo = currentVideo();; localVideo = null) {
      return localVideo;
    }
  }
  
  private void notificationCtrl(Video paramVideo)
  {
    if (((PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean("status_icon", true)) && (Build.VERSION.SDK_INT >= 16)) || (Build.VERSION.SDK_INT >= 18))
    {
      RemoteViews localRemoteViews = new RemoteViews(getPackageName(), 2130903072);
      Intent localIntent1 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_PLAY");
      Intent localIntent2 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_NEXT");
      Intent localIntent3 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_PREV");
      Intent localIntent4 = new Intent("jp.co.asbit.pvstar.APPWIDGET_ACTION_PAUSE");
      PendingIntent localPendingIntent1 = PendingIntent.getBroadcast(this.mContext, 0, localIntent1, 0);
      PendingIntent localPendingIntent2 = PendingIntent.getBroadcast(this.mContext, 0, localIntent2, 0);
      PendingIntent localPendingIntent3 = PendingIntent.getBroadcast(this.mContext, 0, localIntent3, 0);
      PendingIntent localPendingIntent4 = PendingIntent.getBroadcast(this.mContext, 0, localIntent4, 0);
      localRemoteViews.setOnClickPendingIntent(2131492866, localPendingIntent1);
      localRemoteViews.setOnClickPendingIntent(2131492868, localPendingIntent2);
      localRemoteViews.setOnClickPendingIntent(2131492865, localPendingIntent3);
      localRemoteViews.setOnClickPendingIntent(2131492867, localPendingIntent4);
      localRemoteViews.setTextViewText(2131492869, paramVideo.getTitle());
      Intent localIntent5 = new Intent(this.mContext, VideoActivity.class);
      localRemoteViews.setOnClickPendingIntent(2131492869, PendingIntent.getActivity(this.mContext, 0, localIntent5, 134217728));
      NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(this);
      Notification localNotification = localBuilder.setOngoing(true).setAutoCancel(false).setSmallIcon(2130837575).setWhen(System.currentTimeMillis()).build();
      localNotification.contentView = localRemoteViews;
      startForeground(1, localNotification);
    }
  }
  
  private void play(Video paramVideo, int paramInt)
  {
    if (this.musicMode) {}
    for (boolean bool = false;; bool = true)
    {
      play(paramVideo, paramInt, true, bool);
      return;
    }
  }
  
  private void play(Video paramVideo, int paramInt, boolean paramBoolean)
  {
    if (this.musicMode) {}
    for (boolean bool = false;; bool = true)
    {
      play(paramVideo, paramInt, paramBoolean, bool);
      return;
    }
  }
  
  private void play(Video paramVideo, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = true;
    Object localObject;
    if (paramVideo != null) {
      localObject = null;
    }
    for (;;)
    {
      try
      {
        if (this.mBind)
        {
          setRepeatButton(this.repeat);
          setShuffleButton(this.shuffle);
          if (this.timer == null) {
            break label200;
          }
          setSleepButton(bool);
          if (paramBoolean2) {
            localObject = VideoActivity.holder;
          }
          Intent localIntent = new Intent();
          localIntent.putExtra("VIDEO", paramVideo);
          localIntent.putExtra("music_mode", this.musicMode);
          localIntent.setAction("jp.co.asbit.pvstar.PREPARE_VIDEO");
          this.mContext.sendBroadcast(localIntent);
          if (this.musicModeNoLoad)
          {
            this.musicModeNoLoad = false;
            break label199;
          }
        }
        else if (this.pseudoSurface)
        {
          SurfaceHolder localSurfaceHolder = addSurfaceHolderLayer();
          localObject = localSurfaceHolder;
        }
        try
        {
          this.prepareMediaPlayer = new PrepareMediaPlayer((SurfaceHolder)localObject, paramInt, paramBoolean1);
          PrepareMediaPlayer localPrepareMediaPlayer = this.prepareMediaPlayer;
          Video[] arrayOfVideo = new Video[1];
          arrayOfVideo[0] = paramVideo;
          localPrepareMediaPlayer.execute(arrayOfVideo);
        }
        catch (RejectedExecutionException localRejectedExecutionException)
        {
          localRejectedExecutionException.printStackTrace();
        }
        return;
      }
      catch (NullPointerException localNullPointerException)
      {
        localNullPointerException.printStackTrace();
      }
      label199:
      label200:
      bool = false;
    }
  }
  
  private Video prevVideo()
  {
    if (videoExists(-1 + this.vIndex)) {
      this.vIndex = (-1 + this.vIndex);
    }
    for (Video localVideo = currentVideo();; localVideo = null) {
      return localVideo;
    }
  }
  
  private void setRepeatButton(int paramInt)
  {
    if (this.mBind) {
      switch (paramInt)
      {
      }
    }
    for (;;)
    {
      return;
      VideoActivity.repeatButton.setImageResource(2130837555);
      continue;
      VideoActivity.repeatButton.setImageResource(2130837557);
      continue;
      VideoActivity.repeatButton.setImageResource(2130837556);
    }
  }
  
  private void setShuffleButton(boolean paramBoolean)
  {
    if (this.mBind)
    {
      if (!paramBoolean) {
        break label21;
      }
      VideoActivity.shuffleButton.setImageResource(2130837559);
    }
    for (;;)
    {
      return;
      label21:
      VideoActivity.shuffleButton.setImageResource(2130837558);
    }
  }
  
  private void setSleepButton(boolean paramBoolean)
  {
    if (this.mBind)
    {
      if (!paramBoolean) {
        break label21;
      }
      VideoActivity.sleepButton.setImageResource(2130837561);
    }
    for (;;)
    {
      return;
      label21:
      VideoActivity.sleepButton.setImageResource(2130837560);
    }
  }
  
  private void shuffleVideoList(boolean paramBoolean)
  {
    if (this.videoList != null)
    {
      ArrayList localArrayList = new ArrayList(this.videoList);
      Collections.shuffle(localArrayList);
      if (paramBoolean) {
        Collections.swap(localArrayList, 0, localArrayList.indexOf(currentVideo()));
      }
      this.vIndex = 0;
      this.videoList = localArrayList;
    }
  }
  
  private void start()
  {
    if (this.newPlayer)
    {
      if (this.shuffle) {
        shuffleVideoList(false);
      }
      this.vIndex = 0;
      play(currentVideo(), 0);
    }
    for (;;)
    {
      return;
      if (this.mp != null) {
        try
        {
          if ((this.musicMode) && (!this.newPlayer)) {
            this.musicModeNoLoad = true;
          }
          play(currentVideo(), this.mp.getCurrentPosition(), this.mp.isPlaying());
        }
        catch (IllegalStateException localIllegalStateException)
        {
          localIllegalStateException.printStackTrace();
        }
      } else {
        play(currentVideo(), 0, false);
      }
    }
  }
  
  private void stop()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("jp.co.asbit.pvstar.STOP_VIDEO");
    this.mContext.sendBroadcast(localIntent);
    if (this.mp != null)
    {
      this.mp.release();
      this.mp = null;
    }
  }
  
  private boolean videoExists(int paramInt)
  {
    boolean bool = false;
    try
    {
      int i = this.videoList.size();
      if ((i > paramInt) && (paramInt >= 0)) {
        bool = true;
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      label23:
      break label23;
    }
    return bool;
  }
  
  private boolean videoListEquals(ArrayList<Video> paramArrayList1, ArrayList<Video> paramArrayList2)
  {
    boolean bool1;
    if ((paramArrayList1 == null) || (paramArrayList2 == null) || (paramArrayList1.size() != paramArrayList2.size())) {
      bool1 = false;
    }
    for (;;)
    {
      return bool1;
      int i = 0;
      for (;;)
      {
        if (i >= paramArrayList1.size())
        {
          bool1 = true;
          break;
        }
        String str1 = ((Video)paramArrayList1.get(i)).getId();
        String str2 = ((Video)paramArrayList2.get(i)).getId();
        String str3 = ((Video)paramArrayList1.get(i)).getSearchEngine();
        String str4 = ((Video)paramArrayList2.get(i)).getSearchEngine();
        try
        {
          if (str1.equals(str2))
          {
            boolean bool2 = str3.equals(str4);
            if (bool2) {
              i++;
            }
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          localNullPointerException.printStackTrace();
          bool1 = false;
        }
      }
    }
  }
  
  private boolean videoListExists()
  {
    if ((this.videoList != null) && (this.videoList.size() > 0)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private void videoSizeChange()
  {
    if ((this.mBind) && (this.mp != null)) {
      try
      {
        int i = this.mp.getVideoHeight();
        int j = this.mp.getVideoWidth();
        Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
        int k = localDisplay.getWidth();
        int m = localDisplay.getHeight();
        ViewGroup.LayoutParams localLayoutParams = VideoActivity.preview.getLayoutParams();
        float f = i / j;
        if (f < m / k) {
          localLayoutParams.width = k;
        }
        for (localLayoutParams.height = ((int)(f * k));; localLayoutParams.height = m)
        {
          VideoActivity.preview.setLayoutParams(localLayoutParams);
          break;
          localLayoutParams.width = ((int)(m / f));
        }
        return;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        localIllegalStateException.printStackTrace();
      }
    }
  }
  
  public long getSleepTimer()
  {
    if (this.timer != null) {}
    for (long l = this.startTime - (System.currentTimeMillis() - this.startTimestamp);; l = 0L) {
      return l;
    }
  }
  
  public ArrayList<Video> getVideoList()
  {
    return this.videoList;
  }
  
  public void killSleepTimer()
  {
    if (this.timer != null)
    {
      this.timer.cancel();
      this.timer = null;
      this.startTimestamp = 0L;
      this.startTime = 0L;
      setSleepButton(false);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    this.mBind = false;
    if (this.onStart)
    {
      this.mBind = true;
      this.onStart = false;
      start();
    }
    return this.mMyBindService;
  }
  
  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    if (this.mBind) {
      VideoActivity.seekBar.setSecondaryProgress(paramInt);
    }
  }
  
  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    if (this.repeat == 1) {
      play(currentVideo(), 0);
    }
    for (;;)
    {
      return;
      Video localVideo = nextVideo();
      if (localVideo == null)
      {
        if (this.repeat > 0)
        {
          if (areVideosAllError())
          {
            Toast.makeText(this.mContext, 2131296440, 1).show();
          }
          else
          {
            this.newPlayer = true;
            start();
          }
        }
        else {
          stop();
        }
      }
      else {
        play(localVideo, 0);
      }
    }
  }
  
  @SuppressLint({"InlinedApi"})
  public void onCreate()
  {
    super.onCreate();
    this.mContext = getApplicationContext();
    WifiManager localWifiManager = (WifiManager)getSystemService("wifi");
    int i;
    if (Build.VERSION.SDK_INT >= 12)
    {
      i = 3;
      this.wifilock = localWifiManager.createWifiLock(i, "wifilock");
      if (!this.wifilock.isHeld())
      {
        this.wifilock.acquire();
        Log.d("VideoService", "Wifilock acquired.");
      }
      if ((!PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean("status_icon", true)) && (Build.VERSION.SDK_INT < 18)) {
        break label288;
      }
      Notification localNotification = new Notification(2130837575, getText(2131296256), System.currentTimeMillis());
      Intent localIntent = new Intent(this.mContext, VideoActivity.class);
      localIntent.setFlags(67108864);
      PendingIntent localPendingIntent = PendingIntent.getActivity(this.mContext, 0, localIntent, 0);
      localNotification.setLatestEventInfo(this.mContext, getText(2131296256), getString(2131296424), localPendingIntent);
      localNotification.flags = 2;
      startForeground(1, localNotification);
    }
    for (;;)
    {
      ((AudioManager)getSystemService("audio")).requestAudioFocus(new AudioManager.OnAudioFocusChangeListener()
      {
        boolean resume = false;
        
        public void onAudioFocusChange(int paramAnonymousInt)
        {
          switch (paramAnonymousInt)
          {
          }
          for (;;)
          {
            return;
            try
            {
              if ((VideoService.this.mp != null) && (this.resume))
              {
                if (!VideoService.this.mp.isPlaying()) {
                  VideoService.this.mp.start();
                }
                VideoService.this.mp.setVolume(1.0F, 1.0F);
              }
              this.resume = false;
            }
            catch (IllegalStateException localIllegalStateException)
            {
              localIllegalStateException.printStackTrace();
            }
            continue;
            if ((VideoService.this.mp != null) && (VideoService.this.mp.isPlaying()))
            {
              this.resume = true;
              VideoService.this.mp.pause();
              continue;
              if ((VideoService.this.mp != null) && (VideoService.this.mp.isPlaying()))
              {
                this.resume = true;
                VideoService.this.mp.setVolume(0.1F, 0.1F);
              }
            }
          }
        }
      }, 3, 1);
      this.onBecomingNoisy = new BroadcastReceiver()
      {
        private long currentTime;
        
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
          int i = 0;
          if (paramAnonymousIntent.getAction().equals("android.media.AUDIO_BECOMING_NOISY")) {}
          try
          {
            if ((VideoService.this.mp != null) && (VideoService.this.mp.isPlaying()) && (PreferenceManager.getDefaultSharedPreferences(VideoService.this.mContext).getBoolean("headset_unplug_pause", false))) {
              VideoService.this.mp.pause();
            }
            if (!paramAnonymousIntent.getAction().equals("android.intent.action.HEADSET_PLUG")) {}
          }
          catch (Exception localException3)
          {
            try
            {
              if ((VideoService.this.mp != null) && (!VideoService.this.mp.isPlaying()))
              {
                if (paramAnonymousIntent.getIntExtra("state", 0) > 0) {
                  i = 1;
                }
                boolean bool = PreferenceManager.getDefaultSharedPreferences(VideoService.this.mContext).getBoolean("headset_plug_resume", false);
                if ((i != 0) && (bool)) {
                  VideoService.this.mp.start();
                }
              }
              if (paramAnonymousIntent.getAction().equals("android.intent.action.MEDIA_BUTTON")) {
                localKeyEvent = (KeyEvent)paramAnonymousIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
              }
              try
              {
                long l1 = System.currentTimeMillis();
                long l2 = this.currentTime;
                if (l1 - l2 >= 1000L) {
                  break label221;
                }
                return;
              }
              catch (Exception localException1)
              {
                for (;;)
                {
                  localException1.printStackTrace();
                  continue;
                  VideoService.this.mp.start();
                  continue;
                  Video localVideo2 = VideoService.this.nextVideo();
                  if (localVideo2 != null)
                  {
                    VideoService.this.play(localVideo2, 0);
                    continue;
                    if (VideoService.this.mp.getCurrentPosition() > 4000)
                    {
                      VideoService.this.broadcastOpenDialog();
                      VideoService.this.mp.seekTo(0);
                    }
                    else
                    {
                      Video localVideo1 = VideoService.this.prevVideo();
                      if (localVideo1 == null)
                      {
                        VideoService.this.broadcastOpenDialog();
                        VideoService.this.mp.seekTo(0);
                      }
                      VideoService.this.play(localVideo1, 0);
                    }
                  }
                }
              }
              localException3 = localException3;
              localException3.printStackTrace();
            }
            catch (Exception localException2)
            {
              for (;;)
              {
                KeyEvent localKeyEvent;
                localException2.printStackTrace();
                continue;
                label221:
                this.currentTime = System.currentTimeMillis();
                Log.d("VideoService", "keycode" + localKeyEvent.getKeyCode());
                switch (localKeyEvent.getKeyCode())
                {
                case 85: 
                case 126: 
                case 127: 
                  if (!VideoService.this.mp.isPlaying()) {
                    break label344;
                  }
                  VideoService.this.mp.pause();
                }
              }
            }
          }
        }
      };
      this.filter = new IntentFilter();
      this.filter.addAction("android.media.AUDIO_BECOMING_NOISY");
      this.filter.addAction("android.intent.action.HEADSET_PLUG");
      this.filter.addAction("android.intent.action.MEDIA_BUTTON");
      registerReceiver(this.onBecomingNoisy, this.filter);
      return;
      i = 1;
      break;
      label288:
      startForeground(1, new Notification(0, "pvstar", System.currentTimeMillis()));
    }
  }
  
  public void onDestroy()
  {
    try
    {
      new AsyncTask()
      {
        protected String doInBackground(String... paramAnonymousVarArgs)
        {
          VideoService.this.stop();
          return null;
        }
      }.execute(new String[0]);
      this.videoList = null;
      this.vIndex = 0;
      stopForeground(true);
      if (this.wifilock.isHeld())
      {
        this.wifilock.release();
        Log.d("VideoService", "Wifilock released.");
      }
      unregisterReceiver(this.onBecomingNoisy);
      removeSurfaceHolderLayer();
      super.onDestroy();
      return;
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      for (;;)
      {
        stop();
      }
    }
  }
  
  public void onRebind(Intent paramIntent)
  {
    super.onRebind(paramIntent);
    this.mBind = false;
    if (this.onStart)
    {
      this.mBind = true;
      this.onStart = false;
      start();
    }
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.newPlayer = false;
    ArrayList localArrayList;
    if ((paramIntent != null) && (paramIntent.hasExtra("VIDEO_LIST")))
    {
      localArrayList = (ArrayList)paramIntent.getSerializableExtra("VIDEO_LIST");
      if (localArrayList.size() > 0) {
        if (!videoListEquals(localArrayList, this.videoList)) {
          break label180;
        }
      }
    }
    label180:
    for (boolean bool = false;; bool = true)
    {
      this.newPlayer = bool;
      paramIntent.removeExtra("VIDEO_LIST");
      setVideoList(localArrayList);
      if (!videoListExists()) {
        loadLastVideoList();
      }
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
      this.repeat = localSharedPreferences.getInt("repeat2", 0);
      this.shuffle = localSharedPreferences.getBoolean("shuffle", false);
      this.pseudoSurface = localSharedPreferences.getBoolean("pseudo_surface", false);
      this.pauseOnBackground = localSharedPreferences.getBoolean("pause_on_background", false);
      this.musicMode = localSharedPreferences.getBoolean("music_mode", false);
      this.onStart = true;
      removeSurfaceHolderLayer();
      return 1;
    }
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    this.mBind = false;
    return true;
  }
  
  public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    videoSizeChange();
  }
  
  protected void removeSurfaceHolderLayer()
  {
    if (this.surfaceLayout != null) {
      Log.d("VideoService", "removeSurfaceHolderLayer");
    }
    try
    {
      if (this.mp != null) {
        this.mp.setDisplay(null);
      }
      ((WindowManager)getSystemService("window")).removeView(this.surfaceLayout);
      this.surfaceLayout = null;
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      for (;;)
      {
        localIllegalStateException.printStackTrace();
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        localIllegalArgumentException.printStackTrace();
        Log.d("VideoService", "Seems to removeSurfaceHolderLayer isn't exists.");
      }
    }
  }
  
  public void setSleepTimer(long paramLong)
  {
    this.timer = new Timer();
    TimerTask local5 = new TimerTask()
    {
      public void run()
      {
        VideoService.this.handler.post(new Runnable()
        {
          public void run()
          {
            VideoService.this.killSleepTimer();
            if (VideoService.this.mp != null) {
              VideoService.this.mp.pause();
            }
          }
        });
      }
    };
    this.timer.schedule(local5, paramLong);
    this.startTimestamp = System.currentTimeMillis();
    this.startTime = paramLong;
    setSleepButton(true);
  }
  
  public void setVideoList(ArrayList<Video> paramArrayList)
  {
    this.videoList = paramArrayList;
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    localVideoDbHelper.insertLastPlaylist(paramArrayList);
    localVideoDbHelper.close();
  }
  
  public class PrepareMediaPlayer
    extends AsyncTask<Video, Long, Uri>
  {
    private SurfaceHolder holder = null;
    private int mCount = 0;
    private boolean playStart;
    private int position;
    private Uri uri;
    
    public PrepareMediaPlayer(SurfaceHolder paramSurfaceHolder, int paramInt, boolean paramBoolean)
    {
      this.holder = paramSurfaceHolder;
      this.position = paramInt;
      this.playStart = paramBoolean;
    }
    
    private void prepare()
      throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
    {
      VideoService.this.mp.setOnCompletionListener(VideoService.this);
      VideoService.this.mp.setOnVideoSizeChangedListener(VideoService.this);
      VideoService.this.mp.setOnBufferingUpdateListener(VideoService.this);
      VideoService.this.mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener()
      {
        public void onSeekComplete(MediaPlayer paramAnonymousMediaPlayer)
        {
          try
          {
            Thread.sleep(500L);
            VideoService.this.broadcastCloseDialog();
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            for (;;)
            {
              localInterruptedException.printStackTrace();
            }
          }
        }
      });
      VideoService.this.mp.setOnInfoListener(new MediaPlayer.OnInfoListener()
      {
        public boolean onInfo(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          if (paramAnonymousInt1 == 701) {
            VideoService.this.broadcastOpenDialog();
          }
          for (;;)
          {
            return false;
            if (paramAnonymousInt1 == 702) {
              VideoService.this.broadcastCloseDialog();
            }
          }
        }
      });
      VideoService.this.mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
      {
        /* Error */
        @SuppressLint({"NewApi"})
        public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   4: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   7: invokestatic 44	jp/co/asbit/pvstar/VideoService:access$32	(Ljp/co/asbit/pvstar/VideoService;)V
          //   10: aload_0
          //   11: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   14: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   17: invokestatic 47	jp/co/asbit/pvstar/VideoService:access$0	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/MediaPlayer;
          //   20: ifnonnull +4 -> 24
          //   23: return
          //   24: aload_0
          //   25: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   28: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   31: invokestatic 51	jp/co/asbit/pvstar/VideoService:access$1	(Ljp/co/asbit/pvstar/VideoService;)Ljp/co/asbit/pvstar/Video;
          //   34: astore_2
          //   35: aload_2
          //   36: ifnull -13 -> 23
          //   39: aload_2
          //   40: iconst_0
          //   41: invokevirtual 57	jp/co/asbit/pvstar/Video:setError	(Z)V
          //   44: aload_0
          //   45: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   48: invokestatic 61	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$2	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)I
          //   51: ifle +23 -> 74
          //   54: aload_0
          //   55: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   58: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   61: invokestatic 47	jp/co/asbit/pvstar/VideoService:access$0	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/MediaPlayer;
          //   64: aload_0
          //   65: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   68: invokestatic 61	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$2	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)I
          //   71: invokevirtual 67	android/media/MediaPlayer:seekTo	(I)V
          //   74: aload_0
          //   75: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   78: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   81: aload_2
          //   82: invokestatic 71	jp/co/asbit/pvstar/VideoService:access$33	(Ljp/co/asbit/pvstar/VideoService;Ljp/co/asbit/pvstar/Video;)V
          //   85: aload_0
          //   86: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   89: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   92: invokestatic 75	jp/co/asbit/pvstar/VideoService:access$24	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/audiofx/Equalizer;
          //   95: ifnull +16 -> 111
          //   98: aload_0
          //   99: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   102: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   105: invokestatic 75	jp/co/asbit/pvstar/VideoService:access$24	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/audiofx/Equalizer;
          //   108: invokevirtual 80	android/media/audiofx/Equalizer:release	()V
          //   111: aload_0
          //   112: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   115: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   118: aconst_null
          //   119: invokestatic 84	jp/co/asbit/pvstar/VideoService:access$27	(Ljp/co/asbit/pvstar/VideoService;Landroid/media/audiofx/Equalizer;)V
          //   122: aload_0
          //   123: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   126: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   129: invokestatic 88	jp/co/asbit/pvstar/VideoService:access$28	(Ljp/co/asbit/pvstar/VideoService;)Landroid/content/Context;
          //   132: invokestatic 94	jp/co/asbit/pvstar/Util:isEqualizerEnabled	(Landroid/content/Context;)Z
          //   135: ifeq +144 -> 279
          //   138: getstatic 100	android/os/Build$VERSION:SDK_INT	I
          //   141: bipush 9
          //   143: if_icmplt +136 -> 279
          //   146: aload_0
          //   147: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   150: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   153: aload_0
          //   154: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   157: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   160: invokestatic 47	jp/co/asbit/pvstar/VideoService:access$0	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/MediaPlayer;
          //   163: invokevirtual 104	android/media/MediaPlayer:getAudioSessionId	()I
          //   166: invokestatic 108	jp/co/asbit/pvstar/VideoService:access$25	(Ljp/co/asbit/pvstar/VideoService;I)V
          //   169: aload_0
          //   170: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   173: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   176: new 77	android/media/audiofx/Equalizer
          //   179: dup
          //   180: ldc 109
          //   182: aload_0
          //   183: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   186: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   189: invokestatic 113	jp/co/asbit/pvstar/VideoService:access$26	(Ljp/co/asbit/pvstar/VideoService;)I
          //   192: invokespecial 116	android/media/audiofx/Equalizer:<init>	(II)V
          //   195: invokestatic 84	jp/co/asbit/pvstar/VideoService:access$27	(Ljp/co/asbit/pvstar/VideoService;Landroid/media/audiofx/Equalizer;)V
          //   198: aload_0
          //   199: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   202: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   205: invokestatic 75	jp/co/asbit/pvstar/VideoService:access$24	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/audiofx/Equalizer;
          //   208: iconst_1
          //   209: invokevirtual 120	android/media/audiofx/Equalizer:setEnabled	(Z)I
          //   212: pop
          //   213: aload_0
          //   214: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   217: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   220: invokestatic 88	jp/co/asbit/pvstar/VideoService:access$28	(Ljp/co/asbit/pvstar/VideoService;)Landroid/content/Context;
          //   223: invokestatic 124	jp/co/asbit/pvstar/Util:loadEqualizerCurrentPreset	(Landroid/content/Context;)I
          //   226: istore 10
          //   228: iload 10
          //   230: bipush 255
          //   232: aload_0
          //   233: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   236: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   239: invokestatic 75	jp/co/asbit/pvstar/VideoService:access$24	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/audiofx/Equalizer;
          //   242: invokevirtual 128	android/media/audiofx/Equalizer:getNumberOfPresets	()S
          //   245: iadd
          //   246: if_icmple +211 -> 457
          //   249: aload_0
          //   250: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   253: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   256: invokestatic 88	jp/co/asbit/pvstar/VideoService:access$28	(Ljp/co/asbit/pvstar/VideoService;)Landroid/content/Context;
          //   259: invokestatic 132	jp/co/asbit/pvstar/Util:loadEqualizerBandsLevel	(Landroid/content/Context;)[I
          //   262: astore 11
          //   264: iconst_0
          //   265: istore 12
          //   267: aload 11
          //   269: arraylength
          //   270: istore 13
          //   272: iload 12
          //   274: iload 13
          //   276: if_icmplt +107 -> 383
          //   279: aload_0
          //   280: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   283: invokestatic 136	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$3	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Z
          //   286: ifeq +71 -> 357
          //   289: aload_0
          //   290: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   293: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   296: invokestatic 139	jp/co/asbit/pvstar/VideoService:access$15	(Ljp/co/asbit/pvstar/VideoService;)V
          //   299: aload_0
          //   300: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   303: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   306: invokestatic 47	jp/co/asbit/pvstar/VideoService:access$0	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/MediaPlayer;
          //   309: invokevirtual 143	android/media/MediaPlayer:isPlaying	()Z
          //   312: ifne +16 -> 328
          //   315: aload_0
          //   316: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   319: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   322: invokestatic 47	jp/co/asbit/pvstar/VideoService:access$0	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/MediaPlayer;
          //   325: invokevirtual 146	android/media/MediaPlayer:start	()V
          //   328: new 8	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3$1
          //   331: dup
          //   332: aload_0
          //   333: invokespecial 149	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3$1:<init>	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3;)V
          //   336: astore 5
          //   338: iconst_1
          //   339: anewarray 53	jp/co/asbit/pvstar/Video
          //   342: astore 6
          //   344: aload 6
          //   346: iconst_0
          //   347: aload_2
          //   348: aastore
          //   349: aload 5
          //   351: aload 6
          //   353: invokevirtual 153	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3$1:execute	([Ljava/lang/Object;)Ljp/co/asbit/pvstar/AsyncTask;
          //   356: pop
          //   357: aload_0
          //   358: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   361: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   364: invokestatic 88	jp/co/asbit/pvstar/VideoService:access$28	(Ljp/co/asbit/pvstar/VideoService;)Landroid/content/Context;
          //   367: invokestatic 157	jp/co/asbit/pvstar/Util:incPlayCount	(Landroid/content/Context;)V
          //   370: goto -347 -> 23
          //   373: astore 16
          //   375: aload 16
          //   377: invokevirtual 160	java/lang/IllegalStateException:printStackTrace	()V
          //   380: goto -306 -> 74
          //   383: aload_0
          //   384: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   387: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   390: invokestatic 75	jp/co/asbit/pvstar/VideoService:access$24	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/audiofx/Equalizer;
          //   393: iload 12
          //   395: aload 11
          //   397: iload 12
          //   399: iaload
          //   400: i2s
          //   401: invokevirtual 164	android/media/audiofx/Equalizer:setBandLevel	(SS)V
          //   404: iload 12
          //   406: iconst_1
          //   407: iadd
          //   408: i2s
          //   409: istore 12
          //   411: goto -144 -> 267
          //   414: astore 14
          //   416: ldc 166
          //   418: new 168	java/lang/StringBuilder
          //   421: dup
          //   422: ldc 170
          //   424: invokespecial 173	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   427: iload 12
          //   429: invokevirtual 177	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   432: ldc 179
          //   434: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   437: invokevirtual 186	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   440: invokestatic 192	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
          //   443: pop
          //   444: goto -40 -> 404
          //   447: astore 8
          //   449: aload 8
          //   451: invokevirtual 193	java/lang/Exception:printStackTrace	()V
          //   454: goto -175 -> 279
          //   457: aload_0
          //   458: getfield 19	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer$3:this$1	Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;
          //   461: invokestatic 38	jp/co/asbit/pvstar/VideoService$PrepareMediaPlayer:access$8	(Ljp/co/asbit/pvstar/VideoService$PrepareMediaPlayer;)Ljp/co/asbit/pvstar/VideoService;
          //   464: invokestatic 75	jp/co/asbit/pvstar/VideoService:access$24	(Ljp/co/asbit/pvstar/VideoService;)Landroid/media/audiofx/Equalizer;
          //   467: iload 10
          //   469: i2s
          //   470: invokevirtual 197	android/media/audiofx/Equalizer:usePreset	(S)V
          //   473: goto -194 -> 279
          //   476: astore 4
          //   478: aload 4
          //   480: invokevirtual 160	java/lang/IllegalStateException:printStackTrace	()V
          //   483: goto -460 -> 23
          //   486: astore_3
          //   487: aload_3
          //   488: invokevirtual 198	java/lang/NullPointerException:printStackTrace	()V
          //   491: goto -468 -> 23
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	494	0	this	3
          //   0	494	1	paramAnonymousMediaPlayer	MediaPlayer
          //   34	314	2	localVideo	Video
          //   486	2	3	localNullPointerException1	NullPointerException
          //   476	3	4	localIllegalStateException1	IllegalStateException
          //   336	14	5	local1	1
          //   342	10	6	arrayOfVideo	Video[]
          //   447	3	8	localException	Exception
          //   226	242	10	i	int
          //   262	134	11	arrayOfInt	int[]
          //   265	163	12	j	int
          //   270	7	13	k	int
          //   414	1	14	localNullPointerException2	NullPointerException
          //   373	3	16	localIllegalStateException2	IllegalStateException
          // Exception table:
          //   from	to	target	type
          //   54	74	373	java/lang/IllegalStateException
          //   383	404	414	java/lang/NullPointerException
          //   146	272	447	java/lang/Exception
          //   383	404	447	java/lang/Exception
          //   416	444	447	java/lang/Exception
          //   457	473	447	java/lang/Exception
          //   299	328	476	java/lang/IllegalStateException
          //   299	328	486	java/lang/NullPointerException
        }
      });
      VideoService.this.mp.setOnErrorListener(new MediaPlayer.OnErrorListener()
      {
        public boolean onError(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          boolean bool = true;
          if ((paramAnonymousInt1 == -38) && (paramAnonymousInt2 == 0)) {}
          for (;;)
          {
            return bool;
            VideoService.PrepareMediaPlayer localPrepareMediaPlayer = VideoService.PrepareMediaPlayer.this;
            int i = localPrepareMediaPlayer.mCount;
            localPrepareMediaPlayer.mCount = (i + 1);
            if (i < 3)
            {
              try
              {
                VideoService.this.stop();
                VideoService.PrepareMediaPlayer.this.prepare();
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
              }
            }
            else
            {
              VideoService.this.stop();
              VideoService.this.broadcastCloseDialog();
              Video localVideo = VideoService.this.currentVideo();
              if (localVideo != null) {
                localVideo.setError(bool);
              }
              int j = 2131296427;
              if (VideoService.PrepareMediaPlayer.this.uri.toString().indexOf("rtmpe=yes") != -1) {
                j = 2131296449;
              }
              Util.setVideoError(VideoService.this.mContext, bool);
              Toast.makeText(VideoService.this.mContext, j, 0).show();
              VideoService.this.onCompletion(VideoService.this.mp);
              bool = false;
            }
          }
        }
      });
      VideoService.this.mp.prepareAsync();
    }
    
    protected Uri doInBackground(Video... paramVarArgs)
    {
      Uri localUri = null;
      for (;;)
      {
        boolean bool;
        try
        {
          VideoService.this.stop();
          localVideo = paramVarArgs[0];
        }
        finally {}
        try
        {
          this.uri = VideoService.this.getVideoUrl(localVideo);
          bool = isCancelled();
          if (!bool) {
            continue;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          localVideo.setError(true);
          continue;
        }
        return localUri;
        VideoService.this.mp = new MediaPlayer();
        VideoService.this.mp.setDataSource(VideoService.this.mContext, this.uri);
        VideoService.this.mp.setDisplay(this.holder);
        VideoService.this.mp.setScreenOnWhilePlaying(true);
        VideoService.this.mp.setWakeMode(VideoService.this.mContext, 1);
        localUri = this.uri;
      }
    }
    
    protected void onCancelled()
    {
      super.onCancelled();
      VideoService.this.broadcastCloseDialog();
      VideoService.this.stop();
    }
    
    protected void onPostExecute(Uri paramUri)
    {
      try
      {
        super.onPostExecute(paramUri);
        try
        {
          prepare();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          VideoService.this.broadcastCloseDialog();
          Toast.makeText(VideoService.this.mContext, 2131296427, 0).show();
          VideoService.this.onCompletion(VideoService.this.mp);
        }
        return;
      }
      finally {}
    }
    
    protected void onPreExecute()
    {
      super.onPreExecute();
      VideoService.this.broadcastOpenDialog();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoService
 * JD-Core Version:    0.7.0.1
 */