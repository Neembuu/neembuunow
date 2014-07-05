package jp.co.asbit.pvstar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.asbit.pvstar.api.GetChannelDetailsTask;
import jp.co.asbit.pvstar.api.GetVideoDetailTask;

public class VideoDetailActivity
  extends Activity
{
  private Context mContext;
  private Video mVideo;
  private ProgressDialog progressDialog;
  
  private void openUserVideos(Playlist paramPlaylist)
  {
    Intent localIntent = new Intent(this.mContext, UserVideosActivity.class);
    localIntent.putExtra("PLAYLIST", paramPlaylist);
    startActivity(localIntent);
    finish();
  }
  
  public void onBackPressed()
  {
    finish();
    overridePendingTransition(2130968578, 2130968579);
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContext = getApplicationContext();
    setContentView(2130903094);
    this.mVideo = ((Video)getIntent().getSerializableExtra("VIDEO"));
    GetVideoDetailTask local1 = new GetVideoDetailTask()
    {
      protected void onCancelled()
      {
        if ((VideoDetailActivity.this.progressDialog != null) && (VideoDetailActivity.this.progressDialog.isShowing())) {}
        try
        {
          VideoDetailActivity.this.progressDialog.dismiss();
          super.onCancelled();
          return;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          for (;;)
          {
            localIllegalArgumentException.printStackTrace();
          }
        }
      }
      
      protected void onPostExecute(Video paramAnonymousVideo)
      {
        if ((VideoDetailActivity.this.progressDialog != null) && (VideoDetailActivity.this.progressDialog.isShowing())) {}
        try
        {
          VideoDetailActivity.this.progressDialog.dismiss();
          VideoDetailActivity.this.mVideo = paramAnonymousVideo;
          VideoDetailActivity.this.setVideoDetail();
          super.onPostExecute(paramAnonymousVideo);
          return;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          for (;;)
          {
            localIllegalArgumentException.printStackTrace();
          }
        }
      }
      
      protected void onPreExecute()
      {
        VideoDetailActivity.this.progressDialog = new ProgressDialog(VideoDetailActivity.this);
        VideoDetailActivity.this.progressDialog.setMessage(VideoDetailActivity.this.getString(2131296481));
        VideoDetailActivity.this.progressDialog.setCancelable(true);
        VideoDetailActivity.this.progressDialog.setProgressStyle(0);
        VideoDetailActivity.this.progressDialog.show();
        super.onPreExecute();
      }
    };
    Video[] arrayOfVideo = new Video[1];
    arrayOfVideo[0] = this.mVideo;
    local1.execute(arrayOfVideo);
  }
  
  protected void onDestroy()
  {
    this.progressDialog = null;
    super.onDestroy();
  }
  
  public boolean onSearchRequested()
  {
    return false;
  }
  
  protected void setVideoDetail()
  {
    try
    {
      ((TextView)findViewById(2131493002)).setText(this.mVideo.getTitle());
      TextView localTextView1 = (TextView)findViewById(2131493003);
      localTextView1.setText(getString(2131296447));
      Spannable localSpannable = Spannable.Factory.getInstance().newSpannable(localTextView1.getText());
      UnderlineSpan localUnderlineSpan = new UnderlineSpan();
      localSpannable.setSpan(localUnderlineSpan, 0, localTextView1.getText().length(), localSpannable.getSpanFlags(localUnderlineSpan));
      localTextView1.setText(localSpannable, TextView.BufferType.SPANNABLE);
      View.OnTouchListener local2 = new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          TextView localTextView = (TextView)paramAnonymousView;
          switch (paramAnonymousMotionEvent.getAction())
          {
          }
          for (;;)
          {
            return true;
            localTextView.setBackgroundColor(-7829368);
            continue;
            localTextView.setBackgroundColor(0);
            VideoDetailActivity.this.userVideos();
            continue;
            localTextView.setBackgroundColor(0);
          }
        }
      };
      localTextView1.setOnTouchListener(local2);
      TextView localTextView2 = (TextView)findViewById(2131493005);
      StringBuilder localStringBuilder = new StringBuilder();
      int i = 0;
      SpannableString localSpannableString1;
      Matcher localMatcher1;
      if (i >= this.mVideo.getTagCount())
      {
        localSpannableString1 = new SpannableString(localStringBuilder.toString());
        localMatcher1 = Pattern.compile("([^　]+)　?").matcher(localSpannableString1);
      }
      for (;;)
      {
        if (!localMatcher1.find())
        {
          localTextView2.setText(localSpannableString1, TextView.BufferType.SPANNABLE);
          localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
          localTextView2.setFocusable(false);
          localTextView2.setFocusableInTouchMode(false);
          TextView localTextView3 = (TextView)findViewById(2131493007);
          localSpannableString2 = new SpannableString(Html.fromHtml(this.mVideo.getDescription().replace("\n", "<br>")));
          localMatcher2 = Pattern.compile("(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)").matcher(localSpannableString2);
          if (localMatcher2.find()) {
            break label429;
          }
          localMatcher3 = Pattern.compile("(sm|nm|so)[\\d]+").matcher(localSpannableString2);
          if (localMatcher3.find()) {
            break label472;
          }
          localMatcher4 = Pattern.compile("mylist\\/([\\d]+)").matcher(localSpannableString2);
          if (localMatcher4.find()) {
            break label515;
          }
          localTextView3.setText(localSpannableString2, TextView.BufferType.SPANNABLE);
          localTextView3.setMovementMethod(LinkMovementMethod.getInstance());
          localTextView3.setFocusable(false);
          localTextView3.setFocusableInTouchMode(false);
          return;
          localStringBuilder.append(this.mVideo.getTag(i) + "　");
          i++;
          break;
        }
        localSpannableString1.setSpan(new MyUrlSpan(localMatcher1.group(1), 2, localMatcher1.group(1)), localMatcher1.start(1), localMatcher1.end(1), 33);
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        SpannableString localSpannableString2;
        Matcher localMatcher2;
        Matcher localMatcher3;
        Matcher localMatcher4;
        localNullPointerException.printStackTrace();
        break;
        label429:
        localSpannableString2.setSpan(new MyUrlSpan(localMatcher2.group(0), 1, localMatcher2.group(0)), localMatcher2.start(0), localMatcher2.end(0), 33);
        continue;
        label472:
        localSpannableString2.setSpan(new MyUrlSpan(localMatcher3.group(0), 4, localMatcher3.group(0)), localMatcher3.start(0), localMatcher3.end(0), 33);
        continue;
        label515:
        localSpannableString2.setSpan(new MyUrlSpan(localMatcher4.group(0), 3, localMatcher4.group(1)), localMatcher4.start(0), localMatcher4.end(0), 33);
      }
    }
  }
  
  protected void userVideos()
  {
    if ((this.mVideo.getSearchEngine().equals("youtube")) || (this.mVideo.getSearchEngine().equals("dailymotion")) || (this.mVideo.getSearchEngine().equals("vimeo")))
    {
      GetChannelDetailsTask local3 = new GetChannelDetailsTask()
      {
        protected void onPostExecute(Playlist paramAnonymousPlaylist)
        {
          if ((VideoDetailActivity.this.progressDialog != null) && (VideoDetailActivity.this.progressDialog.isShowing())) {
            VideoDetailActivity.this.progressDialog.dismiss();
          }
          VideoDetailActivity.this.openUserVideos(paramAnonymousPlaylist);
        }
        
        protected void onPreExecute()
        {
          VideoDetailActivity.this.progressDialog = new ProgressDialog(VideoDetailActivity.this);
          VideoDetailActivity.this.progressDialog.setMessage(VideoDetailActivity.this.getString(2131296481));
          VideoDetailActivity.this.progressDialog.setProgressStyle(0);
          VideoDetailActivity.this.progressDialog.show();
        }
      };
      String[] arrayOfString = new String[2];
      arrayOfString[0] = this.mVideo.getSearchEngine();
      arrayOfString[1] = this.mVideo.getUserId();
      local3.execute(arrayOfString);
      return;
    }
    Playlist localPlaylist = new Playlist();
    localPlaylist.setId(this.mVideo.getUserId());
    localPlaylist.setSearchEngine(this.mVideo.getSearchEngine());
    localPlaylist.setListType(2);
    if ((this.mVideo.getUserId() != null) && (this.mVideo.getSearchEngine().equals("niconico"))) {}
    for (String str = "user/" + this.mVideo.getUserId();; str = getString(2131296447))
    {
      localPlaylist.setTitle(str);
      openUserVideos(localPlaylist);
      break;
    }
  }
  
  class MyUrlSpan
    extends URLSpan
  {
    public static final int LINK_TYPE_NICOVIDEO_ID = 4;
    public static final int LINK_TYPE_NICOVIDEO_MYLIST = 3;
    public static final int LINK_TYPE_SEARCH = 2;
    public static final int LINK_TYPE_URL = 1;
    String linkTitle;
    int linkType = 0;
    
    public MyUrlSpan(String paramString)
    {
      super();
    }
    
    public MyUrlSpan(String paramString, int paramInt)
    {
      super();
      this.linkType = paramInt;
    }
    
    public MyUrlSpan(String paramString1, int paramInt, String paramString2)
    {
      super();
      this.linkType = paramInt;
      this.linkTitle = paramString2;
    }
    
    public void onClick(View paramView)
    {
      switch (this.linkType)
      {
      }
      for (;;)
      {
        return;
        Intent localIntent4 = new Intent(VideoDetailActivity.this.mContext, SearchActivity.class);
        localIntent4.putExtra("QUERY", this.linkTitle);
        VideoDetailActivity.this.startActivity(localIntent4);
        VideoDetailActivity.this.finish();
        continue;
        Intent localIntent3 = new Intent("android.intent.action.VIEW", Uri.parse(this.linkTitle));
        VideoDetailActivity.this.startActivity(localIntent3);
        VideoDetailActivity.this.finish();
        continue;
        Intent localIntent2 = new Intent(VideoDetailActivity.this.mContext, ReceiveVideoURLActivity.class);
        localIntent2.setData(Uri.parse("http://www.nicovideo.jp/watch/" + this.linkTitle));
        VideoDetailActivity.this.startActivity(localIntent2);
        VideoDetailActivity.this.finish();
        continue;
        Intent localIntent1 = new Intent(VideoDetailActivity.this.mContext, NicoMylistActivity.class);
        localIntent1.putExtra("NICO_MYLIST_ID", this.linkTitle);
        VideoDetailActivity.this.startActivity(localIntent1);
        VideoDetailActivity.this.finish();
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoDetailActivity
 * JD-Core Version:    0.7.0.1
 */