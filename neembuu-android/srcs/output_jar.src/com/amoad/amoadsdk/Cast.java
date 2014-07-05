package com.amoad.amoadsdk;

import java.math.BigDecimal;
import java.util.Date;

abstract class Cast
{
  protected static final CastImpl.CastBigDecimal BigDecimal = new CastImpl.CastBigDecimal();
  protected static final CastImpl.CastBoolean Boolean;
  protected static final CastImpl.CastByte Byte;
  protected static final CastImpl.CastBytes Bytes;
  protected static final CastImpl.CastDate Date;
  protected static final CastImpl.CastDouble Double;
  protected static final CastImpl.CastFloat Float;
  protected static final CastImpl.CastInteger Integer;
  protected static final CastImpl.CastLong Long;
  protected static final CastImpl.CastObject Object = new CastImpl.CastObject();
  protected static final CastImpl.CastShort Short;
  protected static final CastImpl.CastString String;
  
  static
  {
    Integer = new CastImpl.CastInteger();
    Short = new CastImpl.CastShort();
    Long = new CastImpl.CastLong();
    Float = new CastImpl.CastFloat();
    Double = new CastImpl.CastDouble();
    Byte = new CastImpl.CastByte();
    Boolean = new CastImpl.CastBoolean();
    Date = new CastImpl.CastDate();
    String = new CastImpl.CastString();
    Bytes = new CastImpl.CastBytes();
  }
  
  public static BigDecimal forceBigDecimal(Object paramObject, BigDecimal paramBigDecimal)
  {
    return Object.forceBigDecimal(paramObject, paramBigDecimal);
  }
  
  public static BigDecimal forceBigDecimal(Object paramObject, boolean paramBoolean, BigDecimal paramBigDecimal)
  {
    return Object.forceBigDecimal(paramObject, paramBoolean, paramBigDecimal);
  }
  
  public static Boolean forceBoolean(Object paramObject, Boolean paramBoolean)
  {
    return Object.forceBoolean(paramObject, paramBoolean);
  }
  
  public static Boolean forceBoolean(Object paramObject, boolean paramBoolean, Boolean paramBoolean1)
  {
    return Object.forceBoolean(paramObject, paramBoolean, paramBoolean1);
  }
  
  public static Byte forceByte(Object paramObject, Byte paramByte)
  {
    return Object.forceByte(paramObject, paramByte);
  }
  
  public static Byte forceByte(Object paramObject, boolean paramBoolean, Byte paramByte)
  {
    return Object.forceByte(paramObject, paramBoolean, paramByte);
  }
  
  public static byte[] forceBytes(Object paramObject, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    return Object.forceBytes(paramObject, paramBoolean, paramArrayOfByte);
  }
  
  public static byte[] forceBytes(Object paramObject, byte[] paramArrayOfByte)
  {
    return Object.forceBytes(paramObject, paramArrayOfByte);
  }
  
  public static Date forceDate(Object paramObject, Date paramDate)
  {
    return Object.forceDate(paramObject, paramDate);
  }
  
  public static Date forceDate(Object paramObject, boolean paramBoolean, Date paramDate)
  {
    return Object.forceDate(paramObject, paramBoolean, paramDate);
  }
  
  public static Double forceDouble(Object paramObject, Double paramDouble)
  {
    return Object.forceDouble(paramObject, paramDouble);
  }
  
  public static Double forceDouble(Object paramObject, boolean paramBoolean, Double paramDouble)
  {
    return Object.forceDouble(paramObject, paramBoolean, paramDouble);
  }
  
  public static Float forceFloat(Object paramObject, Float paramFloat)
  {
    return Object.forceFloat(paramObject, paramFloat);
  }
  
  public static Float forceFloat(Object paramObject, boolean paramBoolean, Float paramFloat)
  {
    return Object.forceFloat(paramObject, paramBoolean, paramFloat);
  }
  
  public static Integer forceInteger(Object paramObject, Integer paramInteger)
  {
    return Object.forceInteger(paramObject, paramInteger);
  }
  
  public static Integer forceInteger(Object paramObject, boolean paramBoolean, Integer paramInteger)
  {
    return Object.forceInteger(paramObject, paramBoolean, paramInteger);
  }
  
  public static Long forceLong(Object paramObject, Long paramLong)
  {
    return Object.forceLong(paramObject, paramLong);
  }
  
  public static Long forceLong(Object paramObject, boolean paramBoolean, Long paramLong)
  {
    return Object.forceLong(paramObject, paramBoolean, paramLong);
  }
  
  public static Short forceShort(Object paramObject, Short paramShort)
  {
    return Object.forceShort(paramObject, paramShort);
  }
  
  public static Short forceShort(Object paramObject, boolean paramBoolean, Short paramShort)
  {
    return Object.forceShort(paramObject, paramBoolean, paramShort);
  }
  
  public static String forceString(Object paramObject, String paramString)
  {
    return Object.forceString(paramObject, paramString);
  }
  
  public static String forceString(Object paramObject, boolean paramBoolean, String paramString)
  {
    return Object.forceString(paramObject, paramBoolean, paramString);
  }
  
  public static String[] forceStrings(Object paramObject, boolean paramBoolean, String[] paramArrayOfString)
  {
    return Object.forceStrings(paramObject, paramBoolean, paramArrayOfString);
  }
  
  public static String[] forceStrings(Object paramObject, String[] paramArrayOfString)
  {
    return Object.forceStrings(paramObject, paramArrayOfString);
  }
  
  public static BigDecimal toBigDecimal(Object paramObject)
  {
    return Object.toBigDecimal(paramObject);
  }
  
  public static BigDecimal toBigDecimal(Object paramObject, BigDecimal paramBigDecimal)
  {
    return Object.toBigDecimal(paramObject, paramBigDecimal);
  }
  
  public static BigDecimal toBigDecimal(Object paramObject, boolean paramBoolean)
  {
    return Object.toBigDecimal(paramObject, paramBoolean);
  }
  
  public static Boolean toBoolean(Object paramObject)
  {
    return Object.toBoolean(paramObject);
  }
  
  public static Boolean toBoolean(Object paramObject, Boolean paramBoolean)
  {
    return Object.toBoolean(paramObject, paramBoolean);
  }
  
  public static Boolean toBoolean(Object paramObject, boolean paramBoolean)
  {
    return Object.toBoolean(paramObject, paramBoolean);
  }
  
  public static Byte toByte(Object paramObject)
  {
    return Object.toByte(paramObject);
  }
  
  public static Byte toByte(Object paramObject, Byte paramByte)
  {
    return Object.toByte(paramObject, paramByte);
  }
  
  public static Byte toByte(Object paramObject, boolean paramBoolean)
  {
    return Object.toByte(paramObject, paramBoolean);
  }
  
  public static byte[] toBytes(Object paramObject)
  {
    return Object.toBytes(paramObject);
  }
  
  public static byte[] toBytes(Object paramObject, boolean paramBoolean)
  {
    return Object.toBytes(paramObject, paramBoolean);
  }
  
  public static byte[] toBytes(Object paramObject, byte[] paramArrayOfByte)
  {
    return Object.toBytes(paramObject, paramArrayOfByte);
  }
  
  public static Date toDate(Object paramObject)
  {
    return Object.toDate(paramObject);
  }
  
  public static Date toDate(Object paramObject, Date paramDate)
  {
    return Object.toDate(paramObject, paramDate);
  }
  
  public static Date toDate(Object paramObject, boolean paramBoolean)
  {
    return Object.toDate(paramObject, paramBoolean);
  }
  
  public static Double toDouble(Object paramObject)
  {
    return Object.toDouble(paramObject);
  }
  
  public static Double toDouble(Object paramObject, Double paramDouble)
  {
    return Object.toDouble(paramObject, paramDouble);
  }
  
  public static Double toDouble(Object paramObject, boolean paramBoolean)
  {
    return Object.toDouble(paramObject, paramBoolean);
  }
  
  public static Float toFloat(Object paramObject)
  {
    return Object.toFloat(paramObject);
  }
  
  public static Float toFloat(Object paramObject, Float paramFloat)
  {
    return Object.toFloat(paramObject, paramFloat);
  }
  
  public static Float toFloat(Object paramObject, boolean paramBoolean)
  {
    return Object.toFloat(paramObject, paramBoolean);
  }
  
  public static Integer toInteger(Object paramObject)
  {
    return Object.toInteger(paramObject);
  }
  
  public static Integer toInteger(Object paramObject, Integer paramInteger)
  {
    return Object.toInteger(paramObject, paramInteger);
  }
  
  public static Integer toInteger(Object paramObject, boolean paramBoolean)
  {
    return Object.toInteger(paramObject, paramBoolean);
  }
  
  public static Long toLong(Object paramObject)
  {
    return Object.toLong(paramObject);
  }
  
  public static Long toLong(Object paramObject, Long paramLong)
  {
    return Object.toLong(paramObject, paramLong);
  }
  
  public static Long toLong(Object paramObject, boolean paramBoolean)
  {
    return Object.toLong(paramObject, paramBoolean);
  }
  
  public static Short toShort(Object paramObject)
  {
    return Object.toShort(paramObject);
  }
  
  public static Short toShort(Object paramObject, Short paramShort)
  {
    return Object.toShort(paramObject, paramShort);
  }
  
  public static Short toShort(Object paramObject, boolean paramBoolean)
  {
    return Object.toShort(paramObject, paramBoolean);
  }
  
  public static String toString(Object paramObject)
  {
    return Object.toString(paramObject);
  }
  
  public static String toString(Object paramObject, String paramString)
  {
    return Object.toString(paramObject, paramString);
  }
  
  public static String toString(Object paramObject, boolean paramBoolean)
  {
    return Object.toString(paramObject, paramBoolean);
  }
  
  public static String[] toStrings(Object paramObject)
  {
    return Object.toStrings(paramObject);
  }
  
  public static String[] toStrings(Object paramObject, boolean paramBoolean)
  {
    return Object.toStrings(paramObject, paramBoolean);
  }
  
  public static String[] toStrings(Object paramObject, String[] paramArrayOfString)
  {
    return Object.toStrings(paramObject, paramArrayOfString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.Cast
 * JD-Core Version:    0.7.0.1
 */