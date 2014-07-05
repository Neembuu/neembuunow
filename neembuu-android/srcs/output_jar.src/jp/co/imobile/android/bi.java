package jp.co.imobile.android;

import android.graphics.drawable.Drawable;
import java.lang.ref.SoftReference;

final class bi
  implements bp
{
  private final bo a;
  private final int b;
  private final int c;
  private final String d;
  private final int e;
  private SoftReference f;
  
  bi(int paramInt1, bo parambo, int paramInt2, int paramInt3, String paramString)
  {
    this.a = parambo;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramString;
    this.e = paramInt1;
  }
  
  private Drawable b(bl parambl, n paramn)
  {
    Drawable localDrawable = parambl.a(paramn);
    this.f = new SoftReference(localDrawable);
    return localDrawable;
  }
  
  /* Error */
  /**
   * @deprecated
   */
  final Drawable a(bl parambl, n paramn)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 46
    //   4: astore_3
    //   5: aload_0
    //   6: getfield 44	jp/co/imobile/android/bi:f	Ljava/lang/ref/SoftReference;
    //   9: ifnonnull +32 -> 41
    //   12: ldc 48
    //   14: astore_3
    //   15: aload_0
    //   16: aload_1
    //   17: aload_2
    //   18: invokespecial 50	jp/co/imobile/android/bi:b	(Ljp/co/imobile/android/bl;Ljp/co/imobile/android/n;)Landroid/graphics/drawable/Drawable;
    //   21: astore 8
    //   23: aload 8
    //   25: astore 6
    //   27: aload_3
    //   28: aload_0
    //   29: iconst_0
    //   30: anewarray 52	java/lang/String
    //   33: invokestatic 57	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   36: aload_0
    //   37: monitorexit
    //   38: aload 6
    //   40: areturn
    //   41: aload_0
    //   42: getfield 44	jp/co/imobile/android/bi:f	Ljava/lang/ref/SoftReference;
    //   45: invokevirtual 61	java/lang/ref/SoftReference:get	()Ljava/lang/Object;
    //   48: checkcast 63	android/graphics/drawable/Drawable
    //   51: astore 6
    //   53: aload 6
    //   55: ifnonnull +37 -> 92
    //   58: ldc 65
    //   60: astore_3
    //   61: aload_0
    //   62: aload_1
    //   63: aload_2
    //   64: invokespecial 50	jp/co/imobile/android/bi:b	(Ljp/co/imobile/android/bl;Ljp/co/imobile/android/n;)Landroid/graphics/drawable/Drawable;
    //   67: astore 7
    //   69: aload 7
    //   71: astore 6
    //   73: aload_3
    //   74: aload_0
    //   75: iconst_0
    //   76: anewarray 52	java/lang/String
    //   79: invokestatic 57	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   82: goto -46 -> 36
    //   85: astore 5
    //   87: aload_0
    //   88: monitorexit
    //   89: aload 5
    //   91: athrow
    //   92: aload_3
    //   93: aload_0
    //   94: iconst_0
    //   95: anewarray 52	java/lang/String
    //   98: invokestatic 57	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   101: goto -65 -> 36
    //   104: astore 4
    //   106: aload_3
    //   107: aload_0
    //   108: iconst_0
    //   109: anewarray 52	java/lang/String
    //   112: invokestatic 57	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   115: aload 4
    //   117: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	this	bi
    //   0	118	1	parambl	bl
    //   0	118	2	paramn	n
    //   4	103	3	str	String
    //   104	12	4	localObject1	Object
    //   85	5	5	localObject2	Object
    //   25	47	6	localObject3	Object
    //   67	3	7	localDrawable1	Drawable
    //   21	3	8	localDrawable2	Drawable
    // Exception table:
    //   from	to	target	type
    //   2	5	85	finally
    //   27	36	85	finally
    //   73	82	85	finally
    //   92	118	85	finally
    //   5	23	104	finally
    //   41	69	104	finally
  }
  
  final bo a()
  {
    return this.a;
  }
  
  final int b()
  {
    return this.b;
  }
  
  final int c()
  {
    return this.c;
  }
  
  final String d()
  {
    return this.d;
  }
  
  public final String getLogContents()
  {
    return ", advertisementId:" + String.valueOf(this.e) + ", imageType:" + this.a;
  }
  
  public final String getLogTag()
  {
    return "(IM)ImageAdContent:";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bi
 * JD-Core Version:    0.7.0.1
 */