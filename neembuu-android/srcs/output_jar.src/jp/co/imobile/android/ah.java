package jp.co.imobile.android;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

final class ah
{
  static final Animation a;
  static final Animation b;
  static final Animation c;
  static final Animation d;
  static final Animation e;
  static final Animation f;
  
  static
  {
    TranslateAnimation localTranslateAnimation1 = new TranslateAnimation(2, -1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation1.setDuration(250L);
    localTranslateAnimation1.setInterpolator(new AccelerateInterpolator());
    a = localTranslateAnimation1;
    TranslateAnimation localTranslateAnimation2 = new TranslateAnimation(2, 0.0F, 2, -1.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation2.setDuration(250L);
    localTranslateAnimation2.setInterpolator(new AccelerateInterpolator());
    b = localTranslateAnimation2;
    AlphaAnimation localAlphaAnimation1 = new AlphaAnimation(0.1F, 1.0F);
    localAlphaAnimation1.setDuration(250L);
    localAlphaAnimation1.setInterpolator(new AccelerateInterpolator());
    c = localAlphaAnimation1;
    TranslateAnimation localTranslateAnimation3 = new TranslateAnimation(2, 0.0F, 2, 1.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation3.setDuration(250L);
    localTranslateAnimation3.setInterpolator(new AccelerateInterpolator());
    d = localTranslateAnimation3;
    TranslateAnimation localTranslateAnimation4 = new TranslateAnimation(2, 1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation4.setDuration(250L);
    localTranslateAnimation4.setInterpolator(new AccelerateInterpolator());
    e = localTranslateAnimation4;
    AlphaAnimation localAlphaAnimation2 = new AlphaAnimation(1.0F, 0.1F);
    localAlphaAnimation2.setDuration(250L);
    localAlphaAnimation2.setInterpolator(new AccelerateInterpolator());
    f = localAlphaAnimation2;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ah
 * JD-Core Version:    0.7.0.1
 */