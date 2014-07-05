package jp.adlantis.android.utils;

public class Base64Coder
{
  private static char[] map1;
  private static byte[] map2;
  private static final String systemLineSeparator;
  
  static
  {
    int i = 0;
    systemLineSeparator = System.getProperty("line.separator");
    map1 = new char[64];
    int j = 65;
    int i5;
    for (int k = 0; j <= 90; k = i5)
    {
      char[] arrayOfChar5 = map1;
      i5 = k + 1;
      arrayOfChar5[k] = j;
      j = (char)(j + 1);
    }
    int m = 97;
    while (m <= 122)
    {
      char[] arrayOfChar4 = map1;
      int i4 = k + 1;
      arrayOfChar4[k] = m;
      m = (char)(m + 1);
      k = i4;
    }
    int n = 48;
    while (n <= 57)
    {
      char[] arrayOfChar3 = map1;
      int i3 = k + 1;
      arrayOfChar3[k] = n;
      n = (char)(n + 1);
      k = i3;
    }
    char[] arrayOfChar1 = map1;
    int i1 = k + 1;
    arrayOfChar1[k] = '+';
    char[] arrayOfChar2 = map1;
    (i1 + 1);
    arrayOfChar2[i1] = '/';
    map2 = new byte[''];
    for (int i2 = 0; i2 < map2.length; i2++) {
      map2[i2] = -1;
    }
    while (i < 64)
    {
      map2[map1[i]] = ((byte)i);
      i++;
    }
  }
  
  public static byte[] decode(String paramString)
  {
    return decode(paramString.toCharArray());
  }
  
  public static byte[] decode(char[] paramArrayOfChar)
  {
    return decode(paramArrayOfChar, 0, paramArrayOfChar.length);
  }
  
  public static byte[] decode(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramInt2 % 4 != 0) {
      throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    }
    while ((paramInt2 > 0) && (paramArrayOfChar[(-1 + (paramInt1 + paramInt2))] == '=')) {
      paramInt2--;
    }
    int i = paramInt2 * 3 / 4;
    byte[] arrayOfByte = new byte[i];
    int j = paramInt1 + paramInt2;
    int k = 0;
    int i4;
    label177:
    label188:
    int i12;
    int i13;
    int i14;
    if (paramInt1 < j)
    {
      int m = paramInt1 + 1;
      int n = paramArrayOfChar[paramInt1];
      int i1 = m + 1;
      int i2 = paramArrayOfChar[m];
      int i3;
      int i5;
      if (i1 < j)
      {
        int i17 = i1 + 1;
        i3 = paramArrayOfChar[i1];
        i1 = i17;
        if (i1 >= j) {
          break label177;
        }
        int i16 = i1 + 1;
        i5 = paramArrayOfChar[i1];
        i4 = i16;
      }
      for (;;)
      {
        if ((n <= 127) && (i2 <= 127) && (i3 <= 127) && (i5 <= 127)) {
          break label188;
        }
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
        i3 = 65;
        break;
        i4 = i1;
        i5 = 65;
      }
      int i6 = map2[n];
      int i7 = map2[i2];
      int i8 = map2[i3];
      int i9 = map2[i5];
      if ((i6 < 0) || (i7 < 0) || (i8 < 0) || (i9 < 0)) {
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      }
      int i10 = i6 << 2 | i7 >>> 4;
      int i11 = (i7 & 0xF) << 4 | i8 >>> 2;
      i12 = i9 | (i8 & 0x3) << 6;
      i13 = k + 1;
      arrayOfByte[k] = ((byte)i10);
      if (i13 >= i) {
        break label361;
      }
      i14 = i13 + 1;
      arrayOfByte[i13] = ((byte)i11);
    }
    for (;;)
    {
      int i15;
      if (i14 < i)
      {
        i15 = i14 + 1;
        arrayOfByte[i14] = ((byte)i12);
      }
      for (;;)
      {
        k = i15;
        paramInt1 = i4;
        break;
        return arrayOfByte;
        i15 = i14;
      }
      label361:
      i14 = i13;
    }
  }
  
  public static byte[] decodeLines(String paramString)
  {
    char[] arrayOfChar = new char[paramString.length()];
    int i = 0;
    int j = 0;
    while (i < paramString.length())
    {
      int k = paramString.charAt(i);
      if ((k != 32) && (k != 13) && (k != 10) && (k != 9))
      {
        int m = j + 1;
        arrayOfChar[j] = k;
        j = m;
      }
      i++;
    }
    return decode(arrayOfChar, 0, j);
  }
  
  public static String decodeString(String paramString)
  {
    return new String(decode(paramString));
  }
  
  public static char[] encode(byte[] paramArrayOfByte)
  {
    return encode(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static char[] encode(byte[] paramArrayOfByte, int paramInt)
  {
    return encode(paramArrayOfByte, 0, paramInt);
  }
  
  public static char[] encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = (2 + paramInt2 * 4) / 3;
    char[] arrayOfChar = new char[4 * ((paramInt2 + 2) / 3)];
    int j = paramInt1 + paramInt2;
    int k = 0;
    if (paramInt1 < j)
    {
      int m = paramInt1 + 1;
      int n = 0xFF & paramArrayOfByte[paramInt1];
      int i1;
      label74:
      int i2;
      int i3;
      label97:
      int i7;
      int i10;
      label186:
      int i11;
      if (m < j)
      {
        int i13 = m + 1;
        i1 = 0xFF & paramArrayOfByte[m];
        m = i13;
        if (m >= j) {
          break label238;
        }
        i2 = m + 1;
        i3 = 0xFF & paramArrayOfByte[m];
        int i4 = n >>> 2;
        int i5 = (n & 0x3) << 4 | i1 >>> 4;
        int i6 = (i1 & 0xF) << 2 | i3 >>> 6;
        i7 = i3 & 0x3F;
        int i8 = k + 1;
        arrayOfChar[k] = map1[i4];
        int i9 = i8 + 1;
        arrayOfChar[i8] = map1[i5];
        if (i9 >= i) {
          break label248;
        }
        i10 = map1[i6];
        arrayOfChar[i9] = i10;
        i11 = i9 + 1;
        if (i11 >= i) {
          break label255;
        }
      }
      label238:
      label248:
      label255:
      for (int i12 = map1[i7];; i12 = 61)
      {
        arrayOfChar[i11] = i12;
        k = i11 + 1;
        paramInt1 = i2;
        break;
        i1 = 0;
        break label74;
        i2 = m;
        i3 = 0;
        break label97;
        i10 = 61;
        break label186;
      }
    }
    return arrayOfChar;
  }
  
  public static String encodeLines(byte[] paramArrayOfByte)
  {
    return encodeLines(paramArrayOfByte, 0, paramArrayOfByte.length, 76, systemLineSeparator);
  }
  
  public static String encodeLines(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    int i = paramInt3 * 3 / 4;
    if (i <= 0) {
      throw new IllegalArgumentException();
    }
    int j = (-1 + (paramInt2 + i)) / i;
    StringBuilder localStringBuilder = new StringBuilder(4 * ((paramInt2 + 2) / 3) + j * paramString.length());
    int k = 0;
    while (k < paramInt2)
    {
      int m = Math.min(paramInt2 - k, i);
      localStringBuilder.append(encode(paramArrayOfByte, paramInt1 + k, m));
      localStringBuilder.append(paramString);
      k += m;
    }
    return localStringBuilder.toString();
  }
  
  public static String encodeString(String paramString)
  {
    return new String(encode(paramString.getBytes()));
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.utils.Base64Coder
 * JD-Core Version:    0.7.0.1
 */