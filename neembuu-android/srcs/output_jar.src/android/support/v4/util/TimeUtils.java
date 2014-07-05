package android.support.v4.util;

import java.io.PrintWriter;

public class TimeUtils
{
  public static final int HUNDRED_DAY_FIELD_LEN = 19;
  private static final int SECONDS_PER_DAY = 86400;
  private static final int SECONDS_PER_HOUR = 3600;
  private static final int SECONDS_PER_MINUTE = 60;
  private static char[] sFormatStr = new char[24];
  private static final Object sFormatSync = new Object();
  
  private static int accumField(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    int i;
    if ((paramInt1 > 99) || ((paramBoolean) && (paramInt3 >= 3))) {
      i = paramInt2 + 3;
    }
    for (;;)
    {
      return i;
      if ((paramInt1 > 9) || ((paramBoolean) && (paramInt3 >= 2))) {
        i = paramInt2 + 2;
      } else if ((paramBoolean) || (paramInt1 > 0)) {
        i = paramInt2 + 1;
      } else {
        i = 0;
      }
    }
  }
  
  public static void formatDuration(long paramLong1, long paramLong2, PrintWriter paramPrintWriter)
  {
    if (paramLong1 == 0L) {
      paramPrintWriter.print("--");
    }
    for (;;)
    {
      return;
      formatDuration(paramLong1 - paramLong2, paramPrintWriter, 0);
    }
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter)
  {
    formatDuration(paramLong, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter, int paramInt)
  {
    synchronized (sFormatSync)
    {
      int i = formatDurationLocked(paramLong, paramInt);
      paramPrintWriter.print(new String(sFormatStr, 0, i));
      return;
    }
  }
  
  public static void formatDuration(long paramLong, StringBuilder paramStringBuilder)
  {
    synchronized (sFormatSync)
    {
      int i = formatDurationLocked(paramLong, 0);
      paramStringBuilder.append(sFormatStr, 0, i);
      return;
    }
  }
  
  private static int formatDurationLocked(long paramLong, int paramInt)
  {
    if (sFormatStr.length < paramInt) {
      sFormatStr = new char[paramInt];
    }
    char[] arrayOfChar = sFormatStr;
    int i14;
    if (paramLong == 0L)
    {
      int i21 = paramInt - 1;
      while (i21 < 0) {
        arrayOfChar[0] = ' ';
      }
      arrayOfChar[0] = '0';
      i14 = 1;
      return i14;
    }
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    boolean bool4;
    label192:
    boolean bool5;
    label214:
    boolean bool6;
    label236:
    int i18;
    if (paramLong > 0L)
    {
      i = 43;
      j = (int)(paramLong % 1000L);
      k = (int)Math.floor(paramLong / 1000L);
      m = 0;
      n = 0;
      i1 = 0;
      if (k > 86400)
      {
        m = k / 86400;
        k -= 86400 * m;
      }
      if (k > 3600)
      {
        n = k / 3600;
        k -= n * 3600;
      }
      if (k > 60)
      {
        i1 = k / 60;
        k -= i1 * 60;
      }
      i2 = 0;
      if (paramInt == 0) {
        break label329;
      }
      int i15 = accumField(m, 1, false, 0);
      if (i15 <= 0) {
        break label305;
      }
      bool4 = true;
      int i16 = i15 + accumField(n, 1, bool4, 2);
      if (i16 <= 0) {
        break label311;
      }
      bool5 = true;
      int i17 = i16 + accumField(i1, 1, bool5, 2);
      if (i17 <= 0) {
        break label317;
      }
      bool6 = true;
      i18 = i17 + accumField(k, 1, bool6, 2);
      if (i18 <= 0) {
        break label323;
      }
    }
    label305:
    label311:
    label317:
    label323:
    for (int i19 = 3;; i19 = 0)
    {
      for (int i20 = i18 + (1 + accumField(j, 2, true, i19)); i20 < paramInt; i20++)
      {
        arrayOfChar[i2] = ' ';
        i2++;
      }
      i = 45;
      paramLong = -paramLong;
      break;
      bool4 = false;
      break label192;
      bool5 = false;
      break label214;
      bool6 = false;
      break label236;
    }
    label329:
    arrayOfChar[i2] = i;
    int i3 = i2 + 1;
    int i4;
    label348:
    boolean bool1;
    label372:
    int i6;
    label380:
    boolean bool2;
    label406:
    int i8;
    label414:
    boolean bool3;
    label440:
    int i10;
    label448:
    int i11;
    if (paramInt != 0)
    {
      i4 = 1;
      int i5 = printField(arrayOfChar, m, 'd', i3, false, 0);
      if (i5 == i3) {
        break label515;
      }
      bool1 = true;
      if (i4 == 0) {
        break label521;
      }
      i6 = 2;
      int i7 = printField(arrayOfChar, n, 'h', i5, bool1, i6);
      if (i7 == i3) {
        break label527;
      }
      bool2 = true;
      if (i4 == 0) {
        break label533;
      }
      i8 = 2;
      int i9 = printField(arrayOfChar, i1, 'm', i7, bool2, i8);
      if (i9 == i3) {
        break label539;
      }
      bool3 = true;
      if (i4 == 0) {
        break label545;
      }
      i10 = 2;
      i11 = printField(arrayOfChar, k, 's', i9, bool3, i10);
      if ((i4 == 0) || (i11 == i3)) {
        break label551;
      }
    }
    label515:
    label521:
    label527:
    label533:
    label539:
    label545:
    label551:
    for (int i12 = 3;; i12 = 0)
    {
      int i13 = printField(arrayOfChar, j, 'm', i11, true, i12);
      arrayOfChar[i13] = 's';
      i14 = i13 + 1;
      break;
      i4 = 0;
      break label348;
      bool1 = false;
      break label372;
      i6 = 0;
      break label380;
      bool2 = false;
      break label406;
      i8 = 0;
      break label414;
      bool3 = false;
      break label440;
      i10 = 0;
      break label448;
    }
  }
  
  private static int printField(char[] paramArrayOfChar, int paramInt1, char paramChar, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if ((paramBoolean) || (paramInt1 > 0))
    {
      int i = paramInt2;
      if (((paramBoolean) && (paramInt3 >= 3)) || (paramInt1 > 99))
      {
        int m = paramInt1 / 100;
        paramArrayOfChar[paramInt2] = ((char)(m + 48));
        paramInt2++;
        paramInt1 -= m * 100;
      }
      if (((paramBoolean) && (paramInt3 >= 2)) || (paramInt1 > 9) || (i != paramInt2))
      {
        int j = paramInt1 / 10;
        paramArrayOfChar[paramInt2] = ((char)(j + 48));
        paramInt2++;
        paramInt1 -= j * 10;
      }
      paramArrayOfChar[paramInt2] = ((char)(paramInt1 + 48));
      int k = paramInt2 + 1;
      paramArrayOfChar[k] = paramChar;
      paramInt2 = k + 1;
    }
    return paramInt2;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     android.support.v4.util.TimeUtils
 * JD-Core Version:    0.7.0.1
 */