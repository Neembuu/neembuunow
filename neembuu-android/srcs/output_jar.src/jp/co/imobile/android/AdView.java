package jp.co.imobile.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewFlipper;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class AdView
  extends RelativeLayout
  implements bp
{
  private static final boolean a = true;
  private final Handler b = new Handler(Looper.getMainLooper());
  private final a c;
  private final Context d;
  private final AtomicReference e = new AtomicReference(ap.a);
  private final AtomicBoolean f = new AtomicBoolean(false);
  private final ao g;
  private final int h;
  private AdViewRunMode i = AdViewRunMode.NORMAL;
  private q j;
  private AdViewStateListener k;
  private final o l = new t(this);
  private final BroadcastReceiver m = new u(this);
  private final BroadcastReceiver n = new v(this);
  private final Runnable o = new w(this);
  private ImageView p;
  private ViewFlipper q;
  private TextView r;
  private View s;
  
  public AdView(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public AdView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AdView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode())
    {
      this.c = null;
      this.h = 0;
      this.d = null;
      this.g = null;
      a(paramContext);
    }
    for (;;)
    {
      return;
      this.d = paramContext.getApplicationContext();
      this.g = new ao(this.d, (byte)0);
      this.c = a(this.d, paramAttributeSet, false, null, null, null);
      this.h = ((int)(3.0F * this.g.j()));
    }
  }
  
  private AdView(Context paramContext, Integer paramInteger, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(paramContext, null, 0);
    if (isInEditMode())
    {
      this.c = null;
      this.h = 0;
      this.d = null;
      this.g = null;
      a(paramContext);
    }
    for (;;)
    {
      return;
      this.d = paramContext.getApplicationContext();
      this.g = new ao(this.d, (byte)0);
      this.c = a(this.d, null, paramBoolean, paramInteger, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      this.h = ((int)(3.0F * this.g.j()));
    }
  }
  
  private static AdView a(Context paramContext, Integer paramInteger, int paramInt1, int paramInt2)
  {
    return new AdView(paramContext, paramInteger, paramInt1, paramInt2, false);
  }
  
  /* Error */
  private final a a(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: invokevirtual 163	jp/co/imobile/android/AdView:setClickable	(Z)V
    //   5: aload_0
    //   6: iconst_1
    //   7: invokevirtual 166	jp/co/imobile/android/AdView:setFocusable	(Z)V
    //   10: aload_0
    //   11: iconst_1
    //   12: invokevirtual 169	jp/co/imobile/android/AdView:setFocusableInTouchMode	(Z)V
    //   15: aload_0
    //   16: new 171	android/widget/ImageView
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 173	android/widget/ImageView:<init>	(Landroid/content/Context;)V
    //   24: putfield 175	jp/co/imobile/android/AdView:p	Landroid/widget/ImageView;
    //   27: aload_0
    //   28: new 177	android/widget/TextView
    //   31: dup
    //   32: aload_1
    //   33: invokespecial 178	android/widget/TextView:<init>	(Landroid/content/Context;)V
    //   36: putfield 180	jp/co/imobile/android/AdView:r	Landroid/widget/TextView;
    //   39: aload_0
    //   40: getfield 180	jp/co/imobile/android/AdView:r	Landroid/widget/TextView;
    //   43: ldc 181
    //   45: invokevirtual 185	android/widget/TextView:setTextColor	(I)V
    //   48: aload_0
    //   49: getfield 180	jp/co/imobile/android/AdView:r	Landroid/widget/TextView;
    //   52: ldc 186
    //   54: aload_0
    //   55: getfield 127	jp/co/imobile/android/AdView:g	Ljp/co/imobile/android/ao;
    //   58: invokevirtual 147	jp/co/imobile/android/ao:j	()F
    //   61: fmul
    //   62: invokevirtual 190	android/widget/TextView:setTextSize	(F)V
    //   65: aload_0
    //   66: getfield 175	jp/co/imobile/android/AdView:p	Landroid/widget/ImageView;
    //   69: getstatic 196	android/widget/ImageView$ScaleType:FIT_CENTER	Landroid/widget/ImageView$ScaleType;
    //   72: invokevirtual 200	android/widget/ImageView:setScaleType	(Landroid/widget/ImageView$ScaleType;)V
    //   75: aload_0
    //   76: new 202	jp/co/imobile/android/bh
    //   79: dup
    //   80: aload_1
    //   81: invokespecial 203	jp/co/imobile/android/bh:<init>	(Landroid/content/Context;)V
    //   84: putfield 205	jp/co/imobile/android/AdView:q	Landroid/widget/ViewFlipper;
    //   87: aload_0
    //   88: aload_0
    //   89: getfield 205	jp/co/imobile/android/AdView:q	Landroid/widget/ViewFlipper;
    //   92: iconst_0
    //   93: new 207	android/widget/RelativeLayout$LayoutParams
    //   96: dup
    //   97: bipush 255
    //   99: bipush 255
    //   101: invokespecial 210	android/widget/RelativeLayout$LayoutParams:<init>	(II)V
    //   104: invokevirtual 214	jp/co/imobile/android/AdView:addView	(Landroid/view/View;ILandroid/view/ViewGroup$LayoutParams;)V
    //   107: aload_0
    //   108: getfield 205	jp/co/imobile/android/AdView:q	Landroid/widget/ViewFlipper;
    //   111: aload_0
    //   112: getfield 175	jp/co/imobile/android/AdView:p	Landroid/widget/ImageView;
    //   115: bipush 255
    //   117: bipush 255
    //   119: invokevirtual 219	android/widget/ViewFlipper:addView	(Landroid/view/View;II)V
    //   122: aload_0
    //   123: getfield 205	jp/co/imobile/android/AdView:q	Landroid/widget/ViewFlipper;
    //   126: aload_0
    //   127: getfield 180	jp/co/imobile/android/AdView:r	Landroid/widget/TextView;
    //   130: bipush 254
    //   132: bipush 254
    //   134: invokevirtual 219	android/widget/ViewFlipper:addView	(Landroid/view/View;II)V
    //   137: aload_0
    //   138: new 221	android/view/View
    //   141: dup
    //   142: aload_1
    //   143: invokespecial 222	android/view/View:<init>	(Landroid/content/Context;)V
    //   146: putfield 224	jp/co/imobile/android/AdView:s	Landroid/view/View;
    //   149: aload_0
    //   150: getfield 224	jp/co/imobile/android/AdView:s	Landroid/view/View;
    //   153: new 226	android/view/ViewGroup$LayoutParams
    //   156: dup
    //   157: bipush 255
    //   159: bipush 255
    //   161: invokespecial 227	android/view/ViewGroup$LayoutParams:<init>	(II)V
    //   164: invokevirtual 231	android/view/View:setLayoutParams	(Landroid/view/ViewGroup$LayoutParams;)V
    //   167: invokestatic 234	jp/co/imobile/android/AdView:b	()Landroid/graphics/drawable/GradientDrawable;
    //   170: astore 7
    //   172: aload 7
    //   174: ldc 235
    //   176: invokevirtual 240	android/graphics/drawable/GradientDrawable:setCornerRadius	(F)V
    //   179: aload_0
    //   180: getfield 224	jp/co/imobile/android/AdView:s	Landroid/view/View;
    //   183: aload 7
    //   185: invokevirtual 244	android/view/View:setBackgroundDrawable	(Landroid/graphics/drawable/Drawable;)V
    //   188: aload_0
    //   189: getfield 224	jp/co/imobile/android/AdView:s	Landroid/view/View;
    //   192: iconst_4
    //   193: invokevirtual 247	android/view/View:setVisibility	(I)V
    //   196: aload_0
    //   197: aload_0
    //   198: getfield 224	jp/co/imobile/android/AdView:s	Landroid/view/View;
    //   201: invokevirtual 250	jp/co/imobile/android/AdView:addView	(Landroid/view/View;)V
    //   204: aload 7
    //   206: aconst_null
    //   207: invokevirtual 254	android/graphics/drawable/GradientDrawable:setCallback	(Landroid/graphics/drawable/Drawable$Callback;)V
    //   210: iconst_0
    //   211: istore 8
    //   213: invokestatic 259	jp/co/imobile/android/cj:e	()Ljava/lang/String;
    //   216: astore 14
    //   218: aload 14
    //   220: ifnonnull +32 -> 252
    //   223: new 261	android/webkit/WebView
    //   226: dup
    //   227: aload_0
    //   228: invokevirtual 264	jp/co/imobile/android/AdView:getContext	()Landroid/content/Context;
    //   231: invokespecial 265	android/webkit/WebView:<init>	(Landroid/content/Context;)V
    //   234: astore 35
    //   236: aload 35
    //   238: invokevirtual 269	android/webkit/WebView:getSettings	()Landroid/webkit/WebSettings;
    //   241: invokevirtual 274	android/webkit/WebSettings:getUserAgentString	()Ljava/lang/String;
    //   244: invokestatic 277	jp/co/imobile/android/cj:a	(Ljava/lang/String;)V
    //   247: aload 35
    //   249: invokevirtual 280	android/webkit/WebView:destroy	()V
    //   252: aload_1
    //   253: invokevirtual 284	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   256: aload_1
    //   257: invokevirtual 287	android/content/Context:getPackageName	()Ljava/lang/String;
    //   260: sipush 128
    //   263: invokevirtual 293	android/content/pm/PackageManager:getApplicationInfo	(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    //   266: astore 15
    //   268: aload 15
    //   270: getfield 299	android/content/pm/ApplicationInfo:metaData	Landroid/os/Bundle;
    //   273: ifnull +578 -> 851
    //   276: aload 15
    //   278: getfield 299	android/content/pm/ApplicationInfo:metaData	Landroid/os/Bundle;
    //   281: ldc_w 301
    //   284: bipush 255
    //   286: invokevirtual 307	android/os/Bundle:getInt	(Ljava/lang/String;I)I
    //   289: istore 32
    //   291: aload 15
    //   293: getfield 299	android/content/pm/ApplicationInfo:metaData	Landroid/os/Bundle;
    //   296: ldc_w 309
    //   299: iconst_0
    //   300: invokevirtual 313	android/os/Bundle:getBoolean	(Ljava/lang/String;Z)Z
    //   303: istore 33
    //   305: iload 33
    //   307: invokestatic 315	jp/co/imobile/android/cj:a	(Z)V
    //   310: aload 15
    //   312: getfield 299	android/content/pm/ApplicationInfo:metaData	Landroid/os/Bundle;
    //   315: ldc_w 317
    //   318: iconst_1
    //   319: invokevirtual 313	android/os/Bundle:getBoolean	(Ljava/lang/String;Z)Z
    //   322: istore 34
    //   324: iload 34
    //   326: invokestatic 319	jp/co/imobile/android/cj:b	(Z)V
    //   329: aload 15
    //   331: getfield 299	android/content/pm/ApplicationInfo:metaData	Landroid/os/Bundle;
    //   334: ldc_w 321
    //   337: iconst_0
    //   338: invokevirtual 313	android/os/Bundle:getBoolean	(Ljava/lang/String;Z)Z
    //   341: invokestatic 323	jp/co/imobile/android/cj:c	(Z)V
    //   344: aload 15
    //   346: getfield 299	android/content/pm/ApplicationInfo:metaData	Landroid/os/Bundle;
    //   349: ldc_w 325
    //   352: iconst_1
    //   353: invokevirtual 313	android/os/Bundle:getBoolean	(Ljava/lang/String;Z)Z
    //   356: invokestatic 327	jp/co/imobile/android/cj:d	(Z)V
    //   359: iload 34
    //   361: istore 17
    //   363: iload 33
    //   365: istore 8
    //   367: iload 32
    //   369: istore 16
    //   371: aload_2
    //   372: ifnull +240 -> 612
    //   375: aload_2
    //   376: aconst_null
    //   377: ldc_w 329
    //   380: invokeinterface 335 3 0
    //   385: astore 28
    //   387: aload 28
    //   389: astore 11
    //   391: aload 11
    //   393: invokestatic 339	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   396: istore 29
    //   398: aload_2
    //   399: aconst_null
    //   400: ldc_w 341
    //   403: invokeinterface 335 3 0
    //   408: astore 30
    //   410: aload 30
    //   412: astore 10
    //   414: aload 10
    //   416: invokestatic 339	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   419: istore 31
    //   421: iload 29
    //   423: istore 20
    //   425: iload 31
    //   427: istore 21
    //   429: aload_1
    //   430: ldc_w 343
    //   433: invokestatic 346	jp/co/imobile/android/AdView:a	(Landroid/content/Context;Ljava/lang/String;)Z
    //   436: ifne +207 -> 643
    //   439: invokestatic 349	jp/co/imobile/android/cj:d	()Ljp/co/imobile/android/bq;
    //   442: ldc_w 351
    //   445: ldc_w 353
    //   448: invokevirtual 358	jp/co/imobile/android/bq:c	(Ljava/lang/String;Ljava/lang/String;)I
    //   451: pop
    //   452: aload_1
    //   453: invokestatic 363	jp/co/imobile/android/l:a	(Landroid/content/Context;)Ljp/co/imobile/android/a;
    //   456: astore 27
    //   458: aload 27
    //   460: astore 13
    //   462: aload 13
    //   464: areturn
    //   465: astore 36
    //   467: aconst_null
    //   468: astore 35
    //   470: iconst_2
    //   471: anewarray 365	java/lang/String
    //   474: astore 38
    //   476: aload 38
    //   478: iconst_0
    //   479: ldc_w 367
    //   482: aastore
    //   483: aload 38
    //   485: iconst_1
    //   486: aload 36
    //   488: invokevirtual 370	java/lang/Exception:toString	()Ljava/lang/String;
    //   491: aastore
    //   492: ldc_w 372
    //   495: aload_0
    //   496: aload 38
    //   498: invokestatic 375	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   501: ldc_w 377
    //   504: invokestatic 277	jp/co/imobile/android/cj:a	(Ljava/lang/String;)V
    //   507: aload 35
    //   509: ifnull -257 -> 252
    //   512: goto -265 -> 247
    //   515: astore 37
    //   517: aconst_null
    //   518: astore 35
    //   520: aload 35
    //   522: ifnull +8 -> 530
    //   525: aload 35
    //   527: invokevirtual 280	android/webkit/WebView:destroy	()V
    //   530: aload 37
    //   532: athrow
    //   533: astore 9
    //   535: aconst_null
    //   536: astore 10
    //   538: aconst_null
    //   539: astore 11
    //   541: invokestatic 349	jp/co/imobile/android/cj:d	()Ljp/co/imobile/android/bq;
    //   544: ldc_w 351
    //   547: new 379	java/lang/StringBuilder
    //   550: dup
    //   551: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   554: ldc_w 383
    //   557: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   560: ldc_w 389
    //   563: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   566: ldc_w 391
    //   569: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   572: ldc_w 393
    //   575: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   578: aload 10
    //   580: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   583: ldc_w 395
    //   586: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   589: aload 11
    //   591: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   594: invokevirtual 396	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   597: aload 9
    //   599: invokevirtual 399	jp/co/imobile/android/bq:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   602: pop
    //   603: aload_1
    //   604: invokestatic 363	jp/co/imobile/android/l:a	(Landroid/content/Context;)Ljp/co/imobile/android/a;
    //   607: astore 13
    //   609: goto -147 -> 462
    //   612: aload 5
    //   614: invokevirtual 403	java/lang/Integer:intValue	()I
    //   617: istore 18
    //   619: aload 6
    //   621: invokevirtual 403	java/lang/Integer:intValue	()I
    //   624: istore 19
    //   626: iload 18
    //   628: istore 20
    //   630: aconst_null
    //   631: astore 11
    //   633: iload 19
    //   635: istore 21
    //   637: aconst_null
    //   638: astore 10
    //   640: goto -211 -> 429
    //   643: aload_1
    //   644: ldc_w 405
    //   647: invokestatic 346	jp/co/imobile/android/AdView:a	(Landroid/content/Context;Ljava/lang/String;)Z
    //   650: ifne +25 -> 675
    //   653: invokestatic 349	jp/co/imobile/android/cj:d	()Ljp/co/imobile/android/bq;
    //   656: ldc_w 351
    //   659: ldc_w 407
    //   662: invokevirtual 358	jp/co/imobile/android/bq:c	(Ljava/lang/String;Ljava/lang/String;)I
    //   665: pop
    //   666: aload_1
    //   667: invokestatic 363	jp/co/imobile/android/l:a	(Landroid/content/Context;)Ljp/co/imobile/android/a;
    //   670: astore 13
    //   672: goto -210 -> 462
    //   675: aload 4
    //   677: ifnull +10 -> 687
    //   680: aload 4
    //   682: invokevirtual 403	java/lang/Integer:intValue	()I
    //   685: istore 16
    //   687: iload 16
    //   689: bipush 255
    //   691: if_icmpne +12 -> 703
    //   694: aload_1
    //   695: invokestatic 363	jp/co/imobile/android/l:a	(Landroid/content/Context;)Ljp/co/imobile/android/a;
    //   698: astore 13
    //   700: goto -238 -> 462
    //   703: ldc_w 409
    //   706: astore 22
    //   708: iload_3
    //   709: ifeq +105 -> 814
    //   712: aload_1
    //   713: iload 16
    //   715: iload 20
    //   717: iload 21
    //   719: invokestatic 412	jp/co/imobile/android/l:b	(Landroid/content/Context;III)Ljp/co/imobile/android/a;
    //   722: astore 13
    //   724: ldc_w 414
    //   727: astore 22
    //   729: invokestatic 349	jp/co/imobile/android/cj:d	()Ljp/co/imobile/android/bq;
    //   732: ldc_w 351
    //   735: new 379	java/lang/StringBuilder
    //   738: dup
    //   739: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   742: ldc_w 383
    //   745: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   748: aload 22
    //   750: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   753: ldc_w 391
    //   756: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   759: ldc_w 416
    //   762: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   765: iload 21
    //   767: invokevirtual 419	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   770: ldc_w 421
    //   773: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   776: iload 8
    //   778: invokevirtual 424	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   781: ldc_w 426
    //   784: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   787: iload 17
    //   789: invokevirtual 424	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   792: ldc_w 428
    //   795: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   798: ldc_w 430
    //   801: invokevirtual 387	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   804: invokevirtual 396	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   807: invokevirtual 432	jp/co/imobile/android/bq:b	(Ljava/lang/String;Ljava/lang/String;)I
    //   810: pop
    //   811: goto -349 -> 462
    //   814: aload_1
    //   815: iload 16
    //   817: iload 20
    //   819: iload 21
    //   821: invokestatic 434	jp/co/imobile/android/l:a	(Landroid/content/Context;III)Ljp/co/imobile/android/a;
    //   824: astore 23
    //   826: aload 23
    //   828: astore 13
    //   830: goto -101 -> 729
    //   833: astore 9
    //   835: aconst_null
    //   836: astore 10
    //   838: goto -297 -> 541
    //   841: astore 37
    //   843: goto -323 -> 520
    //   846: astore 36
    //   848: goto -378 -> 470
    //   851: bipush 255
    //   853: istore 16
    //   855: iconst_1
    //   856: istore 17
    //   858: goto -487 -> 371
    //   861: astore 9
    //   863: goto -322 -> 541
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	866	0	this	AdView
    //   0	866	1	paramContext	Context
    //   0	866	2	paramAttributeSet	AttributeSet
    //   0	866	3	paramBoolean	boolean
    //   0	866	4	paramInteger1	Integer
    //   0	866	5	paramInteger2	Integer
    //   0	866	6	paramInteger3	Integer
    //   170	35	7	localGradientDrawable	GradientDrawable
    //   211	566	8	bool1	boolean
    //   533	65	9	localException1	Exception
    //   833	1	9	localException2	Exception
    //   861	1	9	localException3	Exception
    //   412	425	10	str1	String
    //   389	243	11	str2	String
    //   460	369	13	localObject1	Object
    //   216	3	14	str3	String
    //   266	79	15	localApplicationInfo	android.content.pm.ApplicationInfo
    //   369	485	16	i1	int
    //   361	496	17	bool2	boolean
    //   617	10	18	i2	int
    //   624	10	19	i3	int
    //   423	395	20	i4	int
    //   427	393	21	i5	int
    //   706	43	22	str4	String
    //   824	3	23	locala1	a
    //   456	3	27	locala2	a
    //   385	3	28	str5	String
    //   396	26	29	i6	int
    //   408	3	30	str6	String
    //   419	7	31	i7	int
    //   289	79	32	i8	int
    //   303	61	33	bool3	boolean
    //   322	38	34	bool4	boolean
    //   234	292	35	localWebView	android.webkit.WebView
    //   465	22	36	localException4	Exception
    //   846	1	36	localException5	Exception
    //   515	16	37	localObject2	Object
    //   841	1	37	localObject3	Object
    //   474	23	38	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   223	236	465	java/lang/Exception
    //   223	236	515	finally
    //   213	218	533	java/lang/Exception
    //   247	387	533	java/lang/Exception
    //   525	533	533	java/lang/Exception
    //   612	626	533	java/lang/Exception
    //   391	410	833	java/lang/Exception
    //   236	247	841	finally
    //   470	507	841	finally
    //   236	247	846	java/lang/Exception
    //   414	458	861	java/lang/Exception
    //   643	826	861	java/lang/Exception
  }
  
  private void a(Context paramContext)
  {
    TextView localTextView = new TextView(paramContext);
    localTextView.setText("i-mobile AdVeiw ver1.4.0");
    localTextView.setPadding(5, 5, 5, 5);
    localTextView.setTextColor(-1);
    localTextView.setTextSize(14.0F);
    addView(localTextView, new RelativeLayout.LayoutParams(-1, -1));
    GradientDrawable localGradientDrawable = b();
    setBackgroundDrawable(localGradientDrawable);
    localGradientDrawable.setCallback(null);
  }
  
  private boolean a(long paramLong)
  {
    boolean bool = false;
    if (!this.i.a().b(this)) {
      cj.b("cancel adview start for run state", this, new String[0]);
    }
    for (;;)
    {
      return bool;
      b(paramLong);
      bool = true;
    }
  }
  
  private static boolean a(Context paramContext, String paramString)
  {
    if (paramContext.checkCallingOrSelfPermission(paramString) == 0) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private boolean a(MotionEvent paramMotionEvent)
  {
    boolean bool1 = true;
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    float f3 = getHeight();
    float f4 = getWidth();
    boolean bool2;
    if ((f1 > this.h) && (f1 < f4 - 1.0F - this.h) && (f2 > this.h) && (f2 < f3 - 1.0F - this.h))
    {
      bool2 = bool1;
      if ((!bool2) || (paramMotionEvent.getEdgeFlags() != 0)) {
        break label102;
      }
    }
    for (;;)
    {
      return bool1;
      bool2 = false;
      break;
      label102:
      bool1 = false;
    }
  }
  
  private static GradientDrawable b()
  {
    int i1 = Color.argb(128, 255, 255, 255);
    int i2 = Color.argb(32, 255, 255, 255);
    GradientDrawable.Orientation localOrientation = GradientDrawable.Orientation.TL_BR;
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = i1;
    arrayOfInt[1] = i2;
    return new GradientDrawable(localOrientation, arrayOfInt);
  }
  
  private static AdView b(Context paramContext, Integer paramInteger, int paramInt1, int paramInt2)
  {
    AdView localAdView = new AdView(paramContext, paramInteger, paramInt1, paramInt2, true);
    cj.d().b("i-mobile SDK", "adView run state is adwhirl");
    localAdView.setRunState(AdViewRunMode.FULL_MANUAL);
    return localAdView;
  }
  
  private void b(long paramLong)
  {
    this.f.set(false);
    if (!this.i.a().b()) {
      this.c.b.compareAndSet(false, true);
    }
    this.b.removeCallbacks(this.o);
    this.b.postDelayed(this.o, paramLong);
  }
  
  private void c()
  {
    this.f.set(true);
    this.c.c();
    this.b.removeCallbacks(this.o);
    this.c.h();
  }
  
  public static AdView create(Context paramContext, int paramInt1, int paramInt2)
  {
    return a(paramContext, null, paramInt1, paramInt2);
  }
  
  public static AdView create(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    return a(paramContext, Integer.valueOf(paramInt1), paramInt2, paramInt3);
  }
  
  public static AdView create(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      int i1 = Integer.parseInt(paramString1);
      int i2 = Integer.parseInt(paramString2);
      int i3 = Integer.parseInt(paramString3);
      AdView localAdView2 = a(paramContext, Integer.valueOf(i1), i2, i3);
      localAdView1 = localAdView2;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        AdView localAdView1 = new AdView(paramContext, Integer.valueOf(-1), -1, -1, false);
      }
    }
    return localAdView1;
  }
  
  public static AdView createForAdWhirl(Context paramContext, int paramInt1, int paramInt2)
  {
    return b(paramContext, null, paramInt1, paramInt2);
  }
  
  public static AdView createForAdWhirl(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    return b(paramContext, Integer.valueOf(paramInt1), paramInt2, paramInt3);
  }
  
  private void d()
  {
    if (this.j != null) {
      this.c.a(this.j);
    }
  }
  
  final boolean a()
  {
    boolean bool = false;
    if (!this.i.a().a()) {
      cj.b("cancel adview stop for run state", this, new String[0]);
    }
    for (;;)
    {
      return bool;
      c();
      bool = true;
    }
  }
  
  public final String getLogContents()
  {
    return ",partnerId:" + this.c.e() + ",mediaId:" + this.c.f() + ",spotId:" + this.c.g() + ",run state:" + this.i.a().toString();
  }
  
  public final String getLogTag()
  {
    return "(IM)AdView:";
  }
  
  public final AdViewRunMode getRunState()
  {
    return this.i;
  }
  
  public final boolean isTest()
  {
    try
    {
      boolean bool2 = this.c.a();
      bool1 = bool2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
        boolean bool1 = false;
      }
    }
    return bool1;
  }
  
  protected final void onAttachedToWindow()
  {
    try
    {
      super.onAttachedToWindow();
      IntentFilter localIntentFilter1 = new IntentFilter();
      localIntentFilter1.addAction("android.intent.action.SCREEN_ON");
      localIntentFilter1.addAction("android.intent.action.SCREEN_OFF");
      this.d.registerReceiver(this.m, localIntentFilter1);
      IntentFilter localIntentFilter2 = new IntentFilter();
      localIntentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      this.d.registerReceiver(this.n, localIntentFilter2);
      cj.b("prepare view by onAttachedToWindow", this, new String[0]);
      a(750L);
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  protected final void onDetachedFromWindow()
  {
    try
    {
      super.onDetachedFromWindow();
      this.d.unregisterReceiver(this.m);
      this.d.unregisterReceiver(this.n);
      a();
      cj.b("clearn up view by onDetachedFromWindow", this, new String[0]);
      this.c.h();
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool;
    int i1;
    try
    {
      if (((ap)this.e.get()).equals(ap.a))
      {
        bool = true;
        break label246;
      }
      i1 = paramMotionEvent.getAction();
      if (!a) {
        break label248;
      }
      i1 &= 0xFF;
    }
    catch (Exception localException1)
    {
      cj.a("(IM)AdView:", localException1);
      bool = false;
      break label246;
    }
    if (a(paramMotionEvent)) {
      this.s.setVisibility(0);
    }
    a();
    break label284;
    this.s.setVisibility(4);
    if (a(paramMotionEvent))
    {
      ap localap = (ap)this.e.get();
      if (localap != null)
      {
        d();
        a locala = this.c;
        Handler localHandler = new Handler(Looper.getMainLooper());
        try
        {
          localHandler.post(new b(locala, localap));
        }
        catch (Exception localException2)
        {
          localHandler.post(new c(locala, localap));
        }
      }
    }
    else
    {
      a(750L);
      break label284;
      if (a(paramMotionEvent))
      {
        this.s.setVisibility(0);
        a();
      }
      else
      {
        this.s.setVisibility(4);
        break label284;
        this.s.setVisibility(4);
        a(750L);
      }
    }
    for (;;)
    {
      label246:
      return bool;
      label248:
      switch (i1)
      {
      }
      label284:
      bool = true;
    }
  }
  
  public final void onWindowFocusChanged(boolean paramBoolean)
  {
    try
    {
      super.onWindowFocusChanged(paramBoolean);
      if (paramBoolean)
      {
        cj.b("adview start window focus true", this, new String[0]);
        if ((this.k != null) && (this.c.b())) {
          this.k.onDismissAdScreen(this);
        }
        a(750L);
      }
      else
      {
        cj.b("adview stop window focus false", this, new String[0]);
        a();
      }
    }
    catch (Exception localException)
    {
      cj.a("(IM)AdView:", localException);
    }
  }
  
  protected final void onWindowVisibilityChanged(int paramInt)
  {
    switch (paramInt)
    {
    default: 
    case 0: 
      try
      {
        a();
      }
      catch (Exception localException)
      {
        cj.a("(IM)AdView:", localException);
      }
      cj.b("adview start for visible", this, new String[0]);
      a(750L);
      break;
    case 4: 
      cj.b("adview stop for invisible", this, new String[0]);
      a();
      break;
    case 8: 
      cj.b("adview stop for gone", this, new String[0]);
      a();
    }
  }
  
  public final void removeOnRequestListener()
  {
    try
    {
      this.c.h();
      this.j = null;
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final void removeOnViewStateListener()
  {
    try
    {
      this.k = null;
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final void setManualMode(boolean paramBoolean)
  {
    try
    {
      setRunState(AdViewRunMode.EASY_MANUAL);
      cj.c("set manual mode", this, new String[0]);
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final void setOnRequestListener(AdViewRequestListener paramAdViewRequestListener)
  {
    if (paramAdViewRequestListener == null) {}
    for (;;)
    {
      return;
      try
      {
        this.c.h();
        this.j = new x(this, paramAdViewRequestListener);
        this.c.a(this.j);
      }
      catch (Exception localException)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final void setOnViewStateListener(AdViewStateListener paramAdViewStateListener)
  {
    if (paramAdViewStateListener == null) {}
    for (;;)
    {
      return;
      try
      {
        this.k = paramAdViewStateListener;
      }
      catch (Exception localException)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final void setRunState(AdViewRunMode paramAdViewRunMode)
  {
    try
    {
      this.i = paramAdViewRunMode;
      cj.c("set run mode", this, new String[0]);
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final void setTest(boolean paramBoolean)
  {
    try
    {
      this.c.a(paramBoolean);
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
  
  public final boolean start()
  {
    boolean bool = false;
    cj.c("start adview by client called", this, new String[0]);
    try
    {
      if (!this.i.a().a(this))
      {
        cj.b("cancel adview start for run state", this, new String[0]);
      }
      else
      {
        b(0L);
        bool = true;
      }
    }
    catch (Exception localException)
    {
      cj.a("(IM)AdView:", localException);
    }
    return bool;
  }
  
  public final boolean stop()
  {
    boolean bool = false;
    cj.c("stop adview by client called", this, new String[0]);
    try
    {
      this.i.a();
      c();
      bool = true;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
    return bool;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.AdView
 * JD-Core Version:    0.7.0.1
 */