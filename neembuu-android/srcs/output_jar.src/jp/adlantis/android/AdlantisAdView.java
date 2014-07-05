package jp.adlantis.android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;
import jp.adlantis.android.utils.AdlantisUtils;
import jp.adlantis.android.utils.AsyncImageLoader;
import jp.adlantis.android.utils.AsyncImageLoader.ImageLoadedCallback;

class AdlantisAdView
  extends ViewSwitcher
{
  private static final float AD_TEXT_SIZE = 20.0F;
  private static final int BANNER_ALTTEXT_VIEW = 1;
  private static final int BANNER_IMAGE_VIEW = 0;
  private static final int BANNER_VIEW = 0;
  private static final float BYLINE_TEXT_SIZE = 12.0F;
  private static final float TEXTAD_ICON_DIMENSION = 32.0F;
  private static final int TEXTAD_VIEW = 1;
  private AdlantisAd _ad;
  private TextView _adAltText;
  private ImageView _adBanner;
  private ViewFlipper _adBannerViewFlipper;
  protected AdlantisAdsModel _adsModel;
  private SizeFitTextView _adtext;
  private ImageView _adtextIconView;
  
  public AdlantisAdView(Context paramContext)
  {
    super(paramContext);
    commonInitLayout();
  }
  
  public AdlantisAdView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    commonInitLayout();
  }
  
  private AdManager adManager()
  {
    return AdManager.getInstance();
  }
  
  private AdlantisAd[] adsForCurrentOrientation()
  {
    return adsModel().adsForOrientation(orientation());
  }
  
  private void commonInitLayout()
  {
    this._adBannerViewFlipper = new AdlantisViewFlipper(getContext());
    addView(this._adBannerViewFlipper, 0, new ViewGroup.LayoutParams(-1, -1));
    this._adBannerViewFlipper.setInAnimation(AdlantisAdViewContainer.fadeInAnimation());
    this._adBannerViewFlipper.setOutAnimation(AdlantisAdViewContainer.fadeOutAnimation());
    this._adBanner = createAdBannerView();
    this._adBannerViewFlipper.addView(this._adBanner, 0, new ViewGroup.LayoutParams(-1, -1));
    this._adAltText = createAdAltText();
    this._adBannerViewFlipper.addView(this._adAltText, 1, new ViewGroup.LayoutParams(-1, -1));
    RelativeLayout localRelativeLayout = new RelativeLayout(getContext());
    addView(localRelativeLayout, 1, new ViewGroup.LayoutParams(-1, -1));
    this._adtextIconView = new ImageView(getContext());
    localRelativeLayout.addView(this._adtextIconView, createAdTextIconViewLayout());
    this._adtext = createAdTextView();
    localRelativeLayout.addView(this._adtext, createAdTextRelativeLayout());
    localRelativeLayout.addView(createBylineTextView(), createBylineTextRelativeLayout());
  }
  
  private float displayDensity()
  {
    return AdlantisUtils.displayDensity(getContext());
  }
  
  private int orientation()
  {
    return AdlantisUtils.orientation(this);
  }
  
  private void setBannerDrawable(Drawable paramDrawable, boolean paramBoolean)
  {
    View localView = this._adBannerViewFlipper.getCurrentView();
    if (this._adBanner != null) {
      this._adBanner.setImageDrawable(paramDrawable);
    }
    if ((this._adBanner != localView) && (paramDrawable != null))
    {
      if (!paramBoolean) {
        break label47;
      }
      this._adBannerViewFlipper.showNext();
    }
    for (;;)
    {
      return;
      label47:
      this._adBannerViewFlipper.setDisplayedChild(0);
    }
  }
  
  private void setIconDrawable(Drawable paramDrawable)
  {
    if (this._adtextIconView != null) {
      this._adtextIconView.setImageDrawable(paramDrawable);
    }
  }
  
  public AdlantisAdsModel adsModel()
  {
    return this._adsModel;
  }
  
  protected TextView createAdAltText()
  {
    TextView localTextView = new TextView(getContext());
    localTextView.setTextSize(20.0F);
    localTextView.setTextColor(-1);
    localTextView.setGravity(17);
    return localTextView;
  }
  
  protected ImageView createAdBannerView()
  {
    ImageView localImageView = new ImageView(getContext());
    localImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    return localImageView;
  }
  
  protected RelativeLayout.LayoutParams createAdTextIconViewLayout()
  {
    float f = displayDensity();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams((int)(32.0F * f), (int)(32.0F * f));
    localLayoutParams.addRule(15, -1);
    localLayoutParams.setMargins((int)(f * 5.0F), 0, 0, 0);
    return localLayoutParams;
  }
  
  protected RelativeLayout.LayoutParams createAdTextRelativeLayout()
  {
    float f = displayDensity();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
    localLayoutParams.addRule(13, -1);
    localLayoutParams.addRule(9, -1);
    localLayoutParams.setMargins((int)(f * 42.0F), 0, 0, 0);
    return localLayoutParams;
  }
  
  protected SizeFitTextView createAdTextView()
  {
    SizeFitTextView localSizeFitTextView = new SizeFitTextView(getContext());
    localSizeFitTextView.setTextSize(20.0F);
    localSizeFitTextView.setTextColor(-1);
    localSizeFitTextView.setLines(1);
    localSizeFitTextView.setMaxLines(1);
    return localSizeFitTextView;
  }
  
  protected RelativeLayout.LayoutParams createBylineTextRelativeLayout()
  {
    float f = displayDensity();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(11, -1);
    localLayoutParams.addRule(12, -1);
    localLayoutParams.setMargins(0, 0, (int)(4.0F * f), (int)(f * 1.0F));
    return localLayoutParams;
  }
  
  protected TextView createBylineTextView()
  {
    TextView localTextView = new TextView(getContext());
    localTextView.setText(adManager().byline());
    localTextView.setTextSize(12.0F);
    localTextView.setTextColor(-1);
    return localTextView;
  }
  
  protected Drawable loadBannerDrawable()
  {
    Drawable localDrawable = loadDrawable(this._ad.bannerURLForCurrentOrientation(this), new AsyncImageLoader.ImageLoadedCallback()
    {
      public void imageLoaded(Drawable paramAnonymousDrawable, String paramAnonymousString)
      {
        if (paramAnonymousDrawable != null) {
          AdlantisAdView.this.setBannerDrawable(paramAnonymousDrawable, true);
        }
      }
    });
    setBannerDrawable(localDrawable, false);
    return localDrawable;
  }
  
  protected Drawable loadDrawable(String paramString, AsyncImageLoader.ImageLoadedCallback paramImageLoadedCallback)
  {
    return adManager().asyncImageLoader().loadDrawable(getContext(), paramString, paramImageLoadedCallback);
  }
  
  protected Drawable loadIconDrawable()
  {
    Drawable localDrawable = loadDrawable(this._ad.iconURL(this), new AsyncImageLoader.ImageLoadedCallback()
    {
      public void imageLoaded(Drawable paramAnonymousDrawable, String paramAnonymousString)
      {
        AdlantisAdView.this.setIconDrawable(paramAnonymousDrawable);
      }
    });
    setIconDrawable(localDrawable);
    return localDrawable;
  }
  
  protected void logD(String paramString)
  {
    Log.d(getClass().getSimpleName(), paramString);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    setupAdDisplay();
  }
  
  public void setAdByIndex(int paramInt)
  {
    AdlantisAd[] arrayOfAdlantisAd = adsForCurrentOrientation();
    if (arrayOfAdlantisAd == null) {}
    for (;;)
    {
      return;
      int i = arrayOfAdlantisAd.length;
      if (i != 0)
      {
        if (paramInt >= i) {
          paramInt = 0;
        }
        AdlantisAd localAdlantisAd1 = arrayOfAdlantisAd[paramInt];
        AdlantisAd localAdlantisAd2 = arrayOfAdlantisAd[((paramInt + 1) % i)];
        if (this._ad != null) {
          this._ad.viewingEnded();
        }
        this._ad = localAdlantisAd1;
        this._ad.viewingStarted();
        setupAdDisplay();
        loadDrawable(localAdlantisAd2.imageURL(this), null);
      }
    }
  }
  
  public void setAdsModel(AdlantisAdsModel paramAdlantisAdsModel)
  {
    this._adsModel = paramAdlantisAdsModel;
  }
  
  protected void setupAdDisplay()
  {
    if (this._ad == null) {}
    for (;;)
    {
      return;
      int i = this._ad.adType();
      if (i == 1)
      {
        setDisplayedChild(0);
        setupBannerAdDisplay();
      }
      else if (i == 2)
      {
        setDisplayedChild(1);
        setupTextAdDisplay();
      }
    }
  }
  
  protected void setupBannerAdDisplay()
  {
    this._adAltText.setText(this._ad.altTextString(this));
    if (loadBannerDrawable() == null)
    {
      View localView = this._adBannerViewFlipper.getCurrentView();
      if (this._adAltText != localView) {
        this._adBannerViewFlipper.setDisplayedChild(1);
      }
    }
  }
  
  protected void setupTextAdDisplay()
  {
    loadIconDrawable();
    this._adtext.setTextAndSize(this._ad.textAdString());
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisAdView
 * JD-Core Version:    0.7.0.1
 */