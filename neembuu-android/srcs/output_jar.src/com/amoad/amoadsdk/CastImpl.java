package com.amoad.amoadsdk;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

abstract class CastImpl<T>
{
  BigDecimal castBigDecimal(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(BigDecimal.class, paramT);
  }
  
  protected BigDecimal castBigDecimal(T paramT, boolean paramBoolean, BigDecimal paramBigDecimal)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramBigDecimal;
        paramBigDecimal = castBigDecimal(paramT);
      }
    }
    throw new TypeCastException(BigDecimal.class, null);
  }
  
  Boolean castBoolean(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Boolean.class, paramT);
  }
  
  protected Boolean castBoolean(T paramT, boolean paramBoolean, Boolean paramBoolean1)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramBoolean1;
        paramBoolean1 = castBoolean(paramT);
      }
    }
    throw new TypeCastException(Boolean.class, null);
  }
  
  Byte castByte(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Byte.class, paramT);
  }
  
  protected Byte castByte(T paramT, boolean paramBoolean, Byte paramByte)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramByte;
        paramByte = castByte(paramT);
      }
    }
    throw new TypeCastException(Byte.class, null);
  }
  
  byte[] castBytes(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(String.class, paramT);
  }
  
  protected byte[] castBytes(T paramT, boolean paramBoolean, byte[] paramArrayOfByte)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramArrayOfByte;
        paramArrayOfByte = castBytes(paramT);
      }
    }
    throw new TypeCastException(String.class, null);
  }
  
  Date castDate(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Date.class, paramT);
  }
  
  protected Date castDate(T paramT, boolean paramBoolean, Date paramDate)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramDate;
        paramDate = castDate(paramT);
      }
    }
    throw new TypeCastException(Date.class, null);
  }
  
  Double castDouble(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Double.class, paramT);
  }
  
  protected Double castDouble(T paramT, boolean paramBoolean, Double paramDouble)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramDouble;
        paramDouble = castDouble(paramT);
      }
    }
    throw new TypeCastException(Double.class, null);
  }
  
  Float castFloat(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Float.class, paramT);
  }
  
  protected Float castFloat(T paramT, boolean paramBoolean, Float paramFloat)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramFloat;
        paramFloat = castFloat(paramT);
      }
    }
    throw new TypeCastException(Float.class, null);
  }
  
  Integer castInteger(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Integer.class, paramT);
  }
  
  protected Integer castInteger(T paramT, boolean paramBoolean, Integer paramInteger)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramInteger;
        paramInteger = castInteger(paramT);
      }
    }
    throw new TypeCastException(Integer.class, null);
  }
  
  Long castLong(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Long.class, paramT);
  }
  
  protected Long castLong(T paramT, boolean paramBoolean, Long paramLong)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramLong;
        paramLong = castLong(paramT);
      }
    }
    throw new TypeCastException(Long.class, null);
  }
  
  Object castObject(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Object.class, paramT);
  }
  
  protected Object castObject(T paramT, boolean paramBoolean, Object paramObject)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramObject;
        paramObject = castObject(paramT);
      }
    }
    throw new TypeCastException(Object.class, null);
  }
  
  Short castShort(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(Short.class, paramT);
  }
  
  protected Short castShort(T paramT, boolean paramBoolean, Short paramShort)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramShort;
        paramShort = castShort(paramT);
      }
    }
    throw new TypeCastException(Short.class, null);
  }
  
  String castString(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException(String.class, paramT);
  }
  
  protected String castString(T paramT, boolean paramBoolean, String paramString)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramString;
        paramString = castString(paramT);
      }
    }
    throw new TypeCastException(String.class, null);
  }
  
  String[] castStrings(T paramT)
    throws TypeCastException
  {
    throw new TypeCastException([Ljava.lang.String.class, paramT);
  }
  
  protected String[] castStrings(T paramT, boolean paramBoolean, String[] paramArrayOfString)
    throws TypeCastException
  {
    if (paramT == null)
    {
      if (!paramBoolean) {}
    }
    else {
      for (;;)
      {
        return paramArrayOfString;
        paramArrayOfString = castStrings(paramT);
      }
    }
    throw new TypeCastException([Ljava.lang.String.class, null);
  }
  
  public BigDecimal forceBigDecimal(T paramT, BigDecimal paramBigDecimal)
  {
    try
    {
      BigDecimal localBigDecimal = castBigDecimal(paramT, true, null);
      paramBigDecimal = localBigDecimal;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramBigDecimal;
  }
  
  public BigDecimal forceBigDecimal(T paramT, boolean paramBoolean, BigDecimal paramBigDecimal)
  {
    try
    {
      BigDecimal localBigDecimal = castBigDecimal(paramT, paramBoolean, null);
      paramBigDecimal = localBigDecimal;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramBigDecimal;
  }
  
  public Boolean forceBoolean(T paramT, Boolean paramBoolean)
  {
    try
    {
      Boolean localBoolean = castBoolean(paramT, true, null);
      paramBoolean = localBoolean;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramBoolean;
  }
  
  public Boolean forceBoolean(T paramT, boolean paramBoolean, Boolean paramBoolean1)
  {
    try
    {
      Boolean localBoolean = castBoolean(paramT, paramBoolean, null);
      paramBoolean1 = localBoolean;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramBoolean1;
  }
  
  public Byte forceByte(T paramT, Byte paramByte)
  {
    try
    {
      Byte localByte = castByte(paramT, true, null);
      paramByte = localByte;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramByte;
  }
  
  public Byte forceByte(T paramT, boolean paramBoolean, Byte paramByte)
  {
    try
    {
      Byte localByte = castByte(paramT, paramBoolean, null);
      paramByte = localByte;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramByte;
  }
  
  public byte[] forceBytes(T paramT, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    try
    {
      byte[] arrayOfByte = castBytes(paramT, paramBoolean, null);
      paramArrayOfByte = arrayOfByte;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramArrayOfByte;
  }
  
  public byte[] forceBytes(T paramT, byte[] paramArrayOfByte)
  {
    try
    {
      byte[] arrayOfByte = castBytes(paramT, true, null);
      paramArrayOfByte = arrayOfByte;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramArrayOfByte;
  }
  
  public Date forceDate(T paramT, Date paramDate)
  {
    try
    {
      Date localDate = castDate(paramT, true, null);
      paramDate = localDate;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramDate;
  }
  
  public Date forceDate(T paramT, boolean paramBoolean, Date paramDate)
  {
    try
    {
      Date localDate = castDate(paramT, paramBoolean, null);
      paramDate = localDate;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramDate;
  }
  
  public Double forceDouble(T paramT, Double paramDouble)
  {
    try
    {
      Double localDouble = castDouble(paramT, true, null);
      paramDouble = localDouble;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramDouble;
  }
  
  public Double forceDouble(T paramT, boolean paramBoolean, Double paramDouble)
  {
    try
    {
      Double localDouble = castDouble(paramT, paramBoolean, null);
      paramDouble = localDouble;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramDouble;
  }
  
  public Float forceFloat(T paramT, Float paramFloat)
  {
    try
    {
      Float localFloat = castFloat(paramT, true, null);
      paramFloat = localFloat;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramFloat;
  }
  
  public Float forceFloat(T paramT, boolean paramBoolean, Float paramFloat)
  {
    try
    {
      Float localFloat = castFloat(paramT, paramBoolean, null);
      paramFloat = localFloat;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramFloat;
  }
  
  public Integer forceInteger(T paramT, Integer paramInteger)
  {
    try
    {
      Integer localInteger = castInteger(paramT, true, null);
      paramInteger = localInteger;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramInteger;
  }
  
  public Integer forceInteger(T paramT, boolean paramBoolean, Integer paramInteger)
  {
    try
    {
      Integer localInteger = castInteger(paramT, paramBoolean, null);
      paramInteger = localInteger;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramInteger;
  }
  
  public Long forceLong(T paramT, Long paramLong)
  {
    try
    {
      Long localLong = castLong(paramT, true, null);
      paramLong = localLong;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramLong;
  }
  
  public Long forceLong(T paramT, boolean paramBoolean, Long paramLong)
  {
    try
    {
      Long localLong = castLong(paramT, paramBoolean, null);
      paramLong = localLong;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramLong;
  }
  
  public Short forceShort(T paramT, Short paramShort)
  {
    try
    {
      Short localShort = castShort(paramT, true, null);
      paramShort = localShort;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramShort;
  }
  
  public Short forceShort(T paramT, boolean paramBoolean, Short paramShort)
  {
    try
    {
      Short localShort = castShort(paramT, paramBoolean, null);
      paramShort = localShort;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramShort;
  }
  
  public String forceString(T paramT, String paramString)
  {
    try
    {
      String str = castString(paramT, true, null);
      paramString = str;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramString;
  }
  
  public String forceString(T paramT, boolean paramBoolean, String paramString)
  {
    try
    {
      String str = castString(paramT, paramBoolean, null);
      paramString = str;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramString;
  }
  
  public String[] forceStrings(T paramT, boolean paramBoolean, String[] paramArrayOfString)
  {
    try
    {
      String[] arrayOfString = castStrings(paramT, paramBoolean, null);
      paramArrayOfString = arrayOfString;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramArrayOfString;
  }
  
  public String[] forceStrings(T paramT, String[] paramArrayOfString)
  {
    try
    {
      String[] arrayOfString = castStrings(paramT, true, null);
      paramArrayOfString = arrayOfString;
    }
    catch (TypeCastException localTypeCastException)
    {
      label12:
      break label12;
    }
    return paramArrayOfString;
  }
  
  public BigDecimal toBigDecimal(T paramT)
    throws TypeCastException
  {
    return castBigDecimal(paramT, true, null);
  }
  
  public BigDecimal toBigDecimal(T paramT, BigDecimal paramBigDecimal)
    throws TypeCastException
  {
    return castBigDecimal(paramT, true, paramBigDecimal);
  }
  
  public BigDecimal toBigDecimal(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castBigDecimal(paramT, paramBoolean, null);
  }
  
  public Boolean toBoolean(T paramT)
    throws TypeCastException
  {
    return castBoolean(paramT, true, null);
  }
  
  public Boolean toBoolean(T paramT, Boolean paramBoolean)
    throws TypeCastException
  {
    return castBoolean(paramT, true, paramBoolean);
  }
  
  public Boolean toBoolean(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castBoolean(paramT, paramBoolean, null);
  }
  
  public Byte toByte(T paramT)
    throws TypeCastException
  {
    return castByte(paramT, true, null);
  }
  
  public Byte toByte(T paramT, Byte paramByte)
    throws TypeCastException
  {
    return castByte(paramT, true, paramByte);
  }
  
  public Byte toByte(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castByte(paramT, paramBoolean, null);
  }
  
  public byte[] toBytes(T paramT)
    throws TypeCastException
  {
    return castBytes(paramT, true, null);
  }
  
  public byte[] toBytes(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castBytes(paramT, paramBoolean, null);
  }
  
  public byte[] toBytes(T paramT, byte[] paramArrayOfByte)
    throws TypeCastException
  {
    return castBytes(paramT, true, paramArrayOfByte);
  }
  
  public Date toDate(T paramT)
    throws TypeCastException
  {
    return castDate(paramT, true, null);
  }
  
  public Date toDate(T paramT, Date paramDate)
    throws TypeCastException
  {
    return castDate(paramT, true, paramDate);
  }
  
  public Date toDate(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castDate(paramT, paramBoolean, null);
  }
  
  public Double toDouble(T paramT)
    throws TypeCastException
  {
    return castDouble(paramT, true, null);
  }
  
  public Double toDouble(T paramT, Double paramDouble)
    throws TypeCastException
  {
    return castDouble(paramT, true, paramDouble);
  }
  
  public Double toDouble(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castDouble(paramT, paramBoolean, null);
  }
  
  public Float toFloat(T paramT)
    throws TypeCastException
  {
    return castFloat(paramT, true, null);
  }
  
  public Float toFloat(T paramT, Float paramFloat)
    throws TypeCastException
  {
    return castFloat(paramT, true, paramFloat);
  }
  
  public Float toFloat(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castFloat(paramT, paramBoolean, null);
  }
  
  public Integer toInteger(T paramT)
    throws TypeCastException
  {
    return castInteger(paramT, true, null);
  }
  
  public Integer toInteger(T paramT, Integer paramInteger)
    throws TypeCastException
  {
    return castInteger(paramT, true, paramInteger);
  }
  
  public Integer toInteger(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castInteger(paramT, paramBoolean, null);
  }
  
  public Long toLong(T paramT)
    throws TypeCastException
  {
    return castLong(paramT, true, null);
  }
  
  public Long toLong(T paramT, Long paramLong)
    throws TypeCastException
  {
    return castLong(paramT, true, paramLong);
  }
  
  public Long toLong(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castLong(paramT, paramBoolean, null);
  }
  
  public Short toShort(T paramT)
    throws TypeCastException
  {
    return castShort(paramT, true, null);
  }
  
  public Short toShort(T paramT, Short paramShort)
    throws TypeCastException
  {
    return castShort(paramT, true, paramShort);
  }
  
  public Short toShort(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castShort(paramT, paramBoolean, null);
  }
  
  public String toString(T paramT)
    throws TypeCastException
  {
    return castString(paramT, true, null);
  }
  
  public String toString(T paramT, String paramString)
    throws TypeCastException
  {
    return castString(paramT, true, paramString);
  }
  
  public String toString(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castString(paramT, paramBoolean, null);
  }
  
  public String[] toStrings(T paramT)
    throws TypeCastException
  {
    return castStrings(paramT, true, null);
  }
  
  public String[] toStrings(T paramT, boolean paramBoolean)
    throws TypeCastException
  {
    return castStrings(paramT, paramBoolean, null);
  }
  
  public String[] toStrings(T paramT, String[] paramArrayOfString)
    throws TypeCastException
  {
    return castStrings(paramT, true, paramArrayOfString);
  }
  
  public static class CastBigDecimal
    extends CastImpl<BigDecimal>
  {
    protected BigDecimal castBigDecimal(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      return paramBigDecimal;
    }
    
    protected Boolean castBoolean(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      if (paramBigDecimal.toBigInteger().toString().equals("0")) {}
      for (boolean bool = false;; bool = true) {
        return Boolean.valueOf(bool);
      }
    }
    
    protected Byte castByte(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      try
      {
        Byte localByte = Byte.valueOf(paramBigDecimal.toBigInteger().toString());
        return localByte;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, Byte.class, paramBigDecimal);
      }
    }
    
    protected Double castDouble(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      return Double.valueOf(paramBigDecimal.toString());
    }
    
    protected Float castFloat(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      return Float.valueOf(paramBigDecimal.toString());
    }
    
    protected Integer castInteger(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      return Integer.valueOf(paramBigDecimal.toBigInteger().toString());
    }
    
    protected Long castLong(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      return Long.valueOf(paramBigDecimal.toBigInteger().toString());
    }
    
    protected Short castShort(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      return Short.valueOf(paramBigDecimal.toBigInteger().toString());
    }
    
    protected String castString(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      return paramBigDecimal.toString();
    }
    
    protected String[] castStrings(BigDecimal paramBigDecimal)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramBigDecimal.toString();
      return arrayOfString;
    }
  }
  
  public static class CastBoolean
    extends CastImpl<Boolean>
  {
    protected BigDecimal castBigDecimal(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (BigDecimal localBigDecimal = BigDecimal.ONE;; localBigDecimal = BigDecimal.ZERO) {
        return localBigDecimal;
      }
    }
    
    protected Boolean castBoolean(Boolean paramBoolean)
      throws TypeCastException
    {
      return paramBoolean;
    }
    
    protected Byte castByte(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (int i = 1;; i = 0) {
        return Byte.valueOf((byte)i);
      }
    }
    
    protected Double castDouble(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (double d = 1.0D;; d = 0.0D) {
        return Double.valueOf(d);
      }
    }
    
    protected Float castFloat(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (float f = 1.0F;; f = 0.0F) {
        return Float.valueOf(f);
      }
    }
    
    protected Integer castInteger(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (int i = 1;; i = 0) {
        return Integer.valueOf(i);
      }
    }
    
    protected Long castLong(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (long l = 1L;; l = 0L) {
        return Long.valueOf(l);
      }
    }
    
    protected Short castShort(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (int i = 1;; i = 0) {
        return Short.valueOf((short)i);
      }
    }
    
    protected String castString(Boolean paramBoolean)
      throws TypeCastException
    {
      if (paramBoolean.booleanValue()) {}
      for (String str = "true";; str = "false") {
        return str;
      }
    }
    
    protected String[] castStrings(Boolean paramBoolean)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      if (paramBoolean.booleanValue()) {}
      for (String str = "true";; str = "false")
      {
        arrayOfString[0] = str;
        return arrayOfString;
      }
    }
  }
  
  public static class CastByte
    extends CastImpl<Byte>
  {
    protected BigDecimal castBigDecimal(Byte paramByte)
      throws TypeCastException
    {
      return BigDecimal.valueOf(paramByte.byteValue());
    }
    
    protected Boolean castBoolean(Byte paramByte)
      throws TypeCastException
    {
      if (paramByte.byteValue() != 0) {}
      for (Boolean localBoolean = Boolean.valueOf(true);; localBoolean = Boolean.valueOf(false)) {
        return localBoolean;
      }
    }
    
    protected Byte castByte(Byte paramByte)
      throws TypeCastException
    {
      return Byte.valueOf(paramByte.byteValue());
    }
    
    protected Double castDouble(Byte paramByte)
      throws TypeCastException
    {
      return Double.valueOf(paramByte.doubleValue());
    }
    
    protected Float castFloat(Byte paramByte)
      throws TypeCastException
    {
      return Float.valueOf(paramByte.floatValue());
    }
    
    protected Integer castInteger(Byte paramByte)
      throws TypeCastException
    {
      return Integer.valueOf(paramByte.intValue());
    }
    
    protected Long castLong(Byte paramByte)
      throws TypeCastException
    {
      return Long.valueOf(paramByte.longValue());
    }
    
    protected Short castShort(Byte paramByte)
      throws TypeCastException
    {
      return Short.valueOf(paramByte.shortValue());
    }
    
    protected String castString(Byte paramByte)
      throws TypeCastException
    {
      return paramByte.toString();
    }
    
    protected String[] castStrings(Byte paramByte)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramByte.toString();
      return arrayOfString;
    }
  }
  
  public static class CastBytes
    extends CastImpl<byte[]>
  {
    protected BigDecimal castBigDecimal(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      throw new TypeCastException(BigDecimal.class, paramArrayOfByte);
    }
    
    protected Boolean castBoolean(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      throw new TypeCastException(Boolean.class, paramArrayOfByte);
    }
    
    protected Byte castByte(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      throw new TypeCastException(Byte.class, paramArrayOfByte);
    }
    
    protected Date castDate(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      throw new TypeCastException(Date.class, paramArrayOfByte);
    }
    
    protected Double castDouble(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      if (paramArrayOfByte != null) {
        return Cast.String.toDouble(Cast.Bytes.toString(paramArrayOfByte));
      }
      throw new TypeCastException(Double.class, paramArrayOfByte);
    }
    
    protected Float castFloat(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      if (paramArrayOfByte != null) {
        return Cast.String.toFloat(Cast.Bytes.toString(paramArrayOfByte));
      }
      throw new TypeCastException(Float.class, paramArrayOfByte);
    }
    
    protected Integer castInteger(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      if (paramArrayOfByte != null) {
        return Cast.String.toInteger(Cast.Bytes.toString(paramArrayOfByte));
      }
      throw new TypeCastException(Integer.class, paramArrayOfByte);
    }
    
    protected Long castLong(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      if (paramArrayOfByte != null) {
        return Cast.String.toLong(Cast.Bytes.toString(paramArrayOfByte));
      }
      throw new TypeCastException(Long.class, paramArrayOfByte);
    }
    
    protected Short castShort(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      if (paramArrayOfByte != null) {
        return Cast.String.toShort(Cast.Bytes.toString(paramArrayOfByte));
      }
      throw new TypeCastException(Short.class, paramArrayOfByte);
    }
    
    protected String castString(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      try
      {
        String str = new String(paramArrayOfByte, "UTF-8");
        return str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        throw new TypeCastException(localUnsupportedEncodingException, String.class, paramArrayOfByte);
      }
    }
    
    protected String[] castStrings(byte[] paramArrayOfByte)
      throws TypeCastException
    {
      throw new TypeCastException([Ljava.lang.String.class, paramArrayOfByte);
    }
  }
  
  public static class CastDate
    extends CastImpl<Date>
  {
    protected Date castDate(Date paramDate)
      throws TypeCastException
    {
      return paramDate;
    }
    
    protected Long castLong(Date paramDate)
      throws TypeCastException
    {
      return Long.valueOf(paramDate.getTime());
    }
    
    protected String castString(Date paramDate)
      throws TypeCastException
    {
      return paramDate.toString();
    }
    
    protected String[] castStrings(Date paramDate)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = toString(paramDate);
      return arrayOfString;
    }
  }
  
  public static class CastDouble
    extends CastImpl<Double>
  {
    protected BigDecimal castBigDecimal(Double paramDouble)
      throws TypeCastException
    {
      return BigDecimal.valueOf(paramDouble.doubleValue());
    }
    
    protected Boolean castBoolean(Double paramDouble)
      throws TypeCastException
    {
      if (paramDouble.doubleValue() != 0.0D) {}
      for (Boolean localBoolean = Boolean.valueOf(true);; localBoolean = Boolean.valueOf(false)) {
        return localBoolean;
      }
    }
    
    protected Byte castByte(Double paramDouble)
      throws TypeCastException
    {
      return Byte.valueOf(paramDouble.byteValue());
    }
    
    protected Double castDouble(Double paramDouble)
      throws TypeCastException
    {
      return Double.valueOf(paramDouble.doubleValue());
    }
    
    protected Float castFloat(Double paramDouble)
      throws TypeCastException
    {
      return Float.valueOf(paramDouble.floatValue());
    }
    
    protected Integer castInteger(Double paramDouble)
      throws TypeCastException
    {
      return Integer.valueOf(paramDouble.intValue());
    }
    
    protected Long castLong(Double paramDouble)
      throws TypeCastException
    {
      return Long.valueOf(paramDouble.longValue());
    }
    
    protected Short castShort(Double paramDouble)
      throws TypeCastException
    {
      return Short.valueOf(paramDouble.shortValue());
    }
    
    protected String castString(Double paramDouble)
      throws TypeCastException
    {
      return paramDouble.toString();
    }
    
    protected String[] castStrings(Double paramDouble)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramDouble.toString();
      return arrayOfString;
    }
  }
  
  public static class CastFloat
    extends CastImpl<Float>
  {
    protected BigDecimal castBigDecimal(Float paramFloat)
      throws TypeCastException
    {
      return BigDecimal.valueOf(paramFloat.floatValue());
    }
    
    protected Boolean castBoolean(Float paramFloat)
      throws TypeCastException
    {
      if (paramFloat.floatValue() != 0.0F) {}
      for (Boolean localBoolean = Boolean.valueOf(true);; localBoolean = Boolean.valueOf(false)) {
        return localBoolean;
      }
    }
    
    protected Byte castByte(Float paramFloat)
      throws TypeCastException
    {
      return Byte.valueOf(paramFloat.byteValue());
    }
    
    protected Double castDouble(Float paramFloat)
      throws TypeCastException
    {
      return Double.valueOf(paramFloat.doubleValue());
    }
    
    protected Float castFloat(Float paramFloat)
      throws TypeCastException
    {
      return Float.valueOf(paramFloat.floatValue());
    }
    
    protected Integer castInteger(Float paramFloat)
      throws TypeCastException
    {
      return Integer.valueOf(paramFloat.intValue());
    }
    
    protected Long castLong(Float paramFloat)
      throws TypeCastException
    {
      return Long.valueOf(paramFloat.longValue());
    }
    
    protected Short castShort(Float paramFloat)
      throws TypeCastException
    {
      return Short.valueOf(paramFloat.shortValue());
    }
    
    protected String castString(Float paramFloat)
      throws TypeCastException
    {
      return paramFloat.toString();
    }
    
    protected String[] castStrings(Float paramFloat)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramFloat.toString();
      return arrayOfString;
    }
  }
  
  public static class CastInteger
    extends CastImpl<Integer>
  {
    protected BigDecimal castBigDecimal(Integer paramInteger)
      throws TypeCastException
    {
      return BigDecimal.valueOf(paramInteger.intValue());
    }
    
    protected Boolean castBoolean(Integer paramInteger)
      throws TypeCastException
    {
      if (paramInteger.intValue() != 0) {}
      for (Boolean localBoolean = Boolean.valueOf(true);; localBoolean = Boolean.valueOf(false)) {
        return localBoolean;
      }
    }
    
    protected Byte castByte(Integer paramInteger)
      throws TypeCastException
    {
      return Byte.valueOf(paramInteger.byteValue());
    }
    
    protected Double castDouble(Integer paramInteger)
      throws TypeCastException
    {
      return Double.valueOf(paramInteger.doubleValue());
    }
    
    protected Float castFloat(Integer paramInteger)
      throws TypeCastException
    {
      return Float.valueOf(paramInteger.floatValue());
    }
    
    protected Integer castInteger(Integer paramInteger)
      throws TypeCastException
    {
      return paramInteger;
    }
    
    protected Long castLong(Integer paramInteger)
      throws TypeCastException
    {
      return Long.valueOf(paramInteger.longValue());
    }
    
    protected Short castShort(Integer paramInteger)
      throws TypeCastException
    {
      return Short.valueOf(paramInteger.shortValue());
    }
    
    protected String castString(Integer paramInteger)
      throws TypeCastException
    {
      return paramInteger.toString();
    }
    
    protected String[] castStrings(Integer paramInteger)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramInteger.toString();
      return arrayOfString;
    }
  }
  
  public static class CastLong
    extends CastImpl<Long>
  {
    protected BigDecimal castBigDecimal(Long paramLong)
      throws TypeCastException
    {
      return BigDecimal.valueOf(paramLong.longValue());
    }
    
    protected Boolean castBoolean(Long paramLong)
      throws TypeCastException
    {
      if (paramLong.longValue() != 0L) {}
      for (Boolean localBoolean = Boolean.valueOf(true);; localBoolean = Boolean.valueOf(false)) {
        return localBoolean;
      }
    }
    
    protected Byte castByte(Long paramLong)
      throws TypeCastException
    {
      return Byte.valueOf(paramLong.byteValue());
    }
    
    protected Double castDouble(Long paramLong)
      throws TypeCastException
    {
      return Double.valueOf(paramLong.doubleValue());
    }
    
    protected Float castFloat(Long paramLong)
      throws TypeCastException
    {
      return Float.valueOf(paramLong.floatValue());
    }
    
    protected Integer castInteger(Long paramLong)
      throws TypeCastException
    {
      return Integer.valueOf(paramLong.intValue());
    }
    
    protected Long castLong(Long paramLong)
      throws TypeCastException
    {
      return Long.valueOf(paramLong.longValue());
    }
    
    protected Short castShort(Long paramLong)
      throws TypeCastException
    {
      return Short.valueOf(paramLong.shortValue());
    }
    
    protected String castString(Long paramLong)
      throws TypeCastException
    {
      return paramLong.toString();
    }
    
    protected String[] castStrings(Long paramLong)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramLong.toString();
      return arrayOfString;
    }
  }
  
  public static class CastObject
    extends CastImpl<Object>
  {
    protected BigDecimal castBigDecimal(Object paramObject)
      throws TypeCastException
    {
      BigDecimal localBigDecimal;
      if ((paramObject instanceof String)) {
        localBigDecimal = Cast.String.castBigDecimal((String)paramObject);
      }
      for (;;)
      {
        return localBigDecimal;
        if ((paramObject instanceof Integer))
        {
          localBigDecimal = Cast.Integer.castBigDecimal((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localBigDecimal = Cast.Long.castBigDecimal((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localBigDecimal = Cast.Byte.castBigDecimal((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localBigDecimal = Cast.Bytes.castBigDecimal((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localBigDecimal = Cast.Boolean.castBigDecimal((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localBigDecimal = Cast.Date.castBigDecimal((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localBigDecimal = Cast.Float.castBigDecimal((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localBigDecimal = Cast.Double.castBigDecimal((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localBigDecimal = Cast.BigDecimal.castBigDecimal((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localBigDecimal = Cast.Short.castBigDecimal((Short)paramObject);
        }
      }
      throw new TypeCastException([Ljava.lang.String.class, paramObject);
    }
    
    protected Boolean castBoolean(Object paramObject)
      throws TypeCastException
    {
      Boolean localBoolean;
      if ((paramObject instanceof String)) {
        localBoolean = Cast.String.castBoolean((String)paramObject);
      }
      for (;;)
      {
        return localBoolean;
        if ((paramObject instanceof Integer))
        {
          localBoolean = Cast.Integer.castBoolean((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localBoolean = Cast.Long.castBoolean((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localBoolean = Cast.Byte.castBoolean((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localBoolean = Cast.Bytes.castBoolean((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localBoolean = Cast.Boolean.castBoolean((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localBoolean = Cast.Date.castBoolean((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localBoolean = Cast.Float.castBoolean((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localBoolean = Cast.Double.castBoolean((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localBoolean = Cast.BigDecimal.castBoolean((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localBoolean = Cast.Short.castBoolean((Short)paramObject);
        }
      }
      throw new TypeCastException(Boolean.class, paramObject);
    }
    
    protected Byte castByte(Object paramObject)
      throws TypeCastException
    {
      Byte localByte;
      if ((paramObject instanceof String)) {
        localByte = Cast.String.castByte((String)paramObject);
      }
      for (;;)
      {
        return localByte;
        if ((paramObject instanceof Integer))
        {
          localByte = Cast.Integer.castByte((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localByte = Cast.Long.castByte((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localByte = Cast.Byte.castByte((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localByte = Cast.Bytes.castByte((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localByte = Cast.Boolean.castByte((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localByte = Cast.Date.castByte((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localByte = Cast.Float.castByte((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localByte = Cast.Double.castByte((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localByte = Cast.BigDecimal.castByte((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localByte = Cast.Short.castByte((Short)paramObject);
        }
      }
      throw new TypeCastException(Byte.class, paramObject);
    }
    
    protected Date castDate(Object paramObject)
      throws TypeCastException
    {
      Date localDate;
      if ((paramObject instanceof String)) {
        localDate = Cast.String.castDate((String)paramObject);
      }
      for (;;)
      {
        return localDate;
        if ((paramObject instanceof Integer))
        {
          localDate = Cast.Integer.castDate((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localDate = Cast.Long.castDate((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localDate = Cast.Byte.castDate((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localDate = Cast.Bytes.castDate((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localDate = Cast.Boolean.castDate((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localDate = Cast.Date.castDate((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localDate = Cast.Float.castDate((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localDate = Cast.Double.castDate((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localDate = Cast.BigDecimal.castDate((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localDate = Cast.Short.castDate((Short)paramObject);
        }
      }
      throw new TypeCastException(Date.class, paramObject);
    }
    
    protected Double castDouble(Object paramObject)
      throws TypeCastException
    {
      Double localDouble;
      if ((paramObject instanceof String)) {
        localDouble = Cast.String.castDouble((String)paramObject);
      }
      for (;;)
      {
        return localDouble;
        if ((paramObject instanceof Integer))
        {
          localDouble = Cast.Integer.castDouble((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localDouble = Cast.Long.castDouble((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localDouble = Cast.Byte.castDouble((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localDouble = Cast.Bytes.castDouble((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localDouble = Cast.Boolean.castDouble((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localDouble = Cast.Date.castDouble((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localDouble = Cast.Float.castDouble((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localDouble = Cast.Double.castDouble((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localDouble = Cast.BigDecimal.castDouble((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localDouble = Cast.Short.castDouble((Short)paramObject);
        }
      }
      throw new TypeCastException(Double.class, paramObject);
    }
    
    protected Float castFloat(Object paramObject)
      throws TypeCastException
    {
      Float localFloat;
      if ((paramObject instanceof String)) {
        localFloat = Cast.String.castFloat((String)paramObject);
      }
      for (;;)
      {
        return localFloat;
        if ((paramObject instanceof Integer))
        {
          localFloat = Cast.Integer.castFloat((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localFloat = Cast.Long.castFloat((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localFloat = Cast.Byte.castFloat((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localFloat = Cast.Bytes.castFloat((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localFloat = Cast.Boolean.castFloat((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localFloat = Cast.Date.castFloat((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localFloat = Cast.Float.castFloat((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localFloat = Cast.Double.castFloat((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localFloat = Cast.BigDecimal.castFloat((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localFloat = Cast.Short.castFloat((Short)paramObject);
        }
      }
      throw new TypeCastException(Float.class, paramObject);
    }
    
    protected Integer castInteger(Object paramObject)
      throws TypeCastException
    {
      Integer localInteger;
      if ((paramObject instanceof String)) {
        localInteger = Cast.String.castInteger((String)paramObject);
      }
      for (;;)
      {
        return localInteger;
        if ((paramObject instanceof Integer))
        {
          localInteger = Cast.Integer.castInteger((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localInteger = Cast.Long.castInteger((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localInteger = Cast.Byte.castInteger((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localInteger = Cast.Bytes.castInteger((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localInteger = Cast.Boolean.castInteger((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localInteger = Cast.Date.castInteger((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localInteger = Cast.Float.castInteger((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localInteger = Cast.Double.castInteger((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localInteger = Cast.BigDecimal.castInteger((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localInteger = Cast.Short.castInteger((Short)paramObject);
        }
      }
      throw new TypeCastException(Integer.class, paramObject);
    }
    
    protected Long castLong(Object paramObject)
      throws TypeCastException
    {
      Long localLong;
      if ((paramObject instanceof String)) {
        localLong = Cast.String.castLong((String)paramObject);
      }
      for (;;)
      {
        return localLong;
        if ((paramObject instanceof Integer))
        {
          localLong = Cast.Integer.castLong((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localLong = Cast.Long.castLong((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localLong = Cast.Byte.castLong((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localLong = Cast.Bytes.castLong((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localLong = Cast.Boolean.castLong((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localLong = Cast.Date.castLong((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localLong = Cast.Float.castLong((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localLong = Cast.Double.castLong((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localLong = Cast.BigDecimal.castLong((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localLong = Cast.Short.castLong((Short)paramObject);
        }
      }
      throw new TypeCastException(Long.class, paramObject);
    }
    
    protected Short castShort(Object paramObject)
      throws TypeCastException
    {
      Short localShort;
      if ((paramObject instanceof String)) {
        localShort = Cast.String.castShort((String)paramObject);
      }
      for (;;)
      {
        return localShort;
        if ((paramObject instanceof Short))
        {
          localShort = Cast.Short.castShort((Short)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          localShort = Cast.Long.castShort((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          localShort = Cast.Byte.castShort((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          localShort = Cast.Bytes.castShort((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          localShort = Cast.Boolean.castShort((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          localShort = Cast.Date.castShort((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          localShort = Cast.Float.castShort((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          localShort = Cast.Double.castShort((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          localShort = Cast.BigDecimal.castShort((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          localShort = Cast.Short.castShort((Short)paramObject);
        }
      }
      throw new TypeCastException(Short.class, paramObject);
    }
    
    protected String castString(Object paramObject)
      throws TypeCastException
    {
      String str;
      if ((paramObject instanceof String)) {
        str = Cast.String.castString((String)paramObject);
      }
      for (;;)
      {
        return str;
        if ((paramObject instanceof Integer))
        {
          str = Cast.Integer.castString((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          str = Cast.Long.castString((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          str = Cast.Byte.castString((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          str = Cast.Bytes.castString((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          str = Cast.Boolean.castString((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          str = Cast.Date.castString((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          str = Cast.Float.castString((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          str = Cast.Double.castString((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          str = Cast.BigDecimal.castString((BigDecimal)paramObject);
        }
        else
        {
          if (!(paramObject instanceof Short)) {
            break;
          }
          str = Cast.Short.castString((Short)paramObject);
        }
      }
      throw new TypeCastException(String.class, paramObject);
    }
    
    protected String[] castStrings(Object paramObject)
      throws TypeCastException
    {
      String[] arrayOfString;
      if ((paramObject instanceof String)) {
        arrayOfString = Cast.String.castStrings((String)paramObject);
      }
      for (;;)
      {
        return arrayOfString;
        if ((paramObject instanceof Integer))
        {
          arrayOfString = Cast.Integer.castStrings((Integer)paramObject);
        }
        else if ((paramObject instanceof Long))
        {
          arrayOfString = Cast.Long.castStrings((Long)paramObject);
        }
        else if ((paramObject instanceof Byte))
        {
          arrayOfString = Cast.Byte.castStrings((Byte)paramObject);
        }
        else if ((paramObject instanceof byte[]))
        {
          arrayOfString = Cast.Bytes.castStrings((byte[])paramObject);
        }
        else if ((paramObject instanceof Boolean))
        {
          arrayOfString = Cast.Boolean.castStrings((Boolean)paramObject);
        }
        else if ((paramObject instanceof Date))
        {
          arrayOfString = Cast.Date.castStrings((Date)paramObject);
        }
        else if ((paramObject instanceof Float))
        {
          arrayOfString = Cast.Float.castStrings((Float)paramObject);
        }
        else if ((paramObject instanceof Double))
        {
          arrayOfString = Cast.Double.castStrings((Double)paramObject);
        }
        else if ((paramObject instanceof BigDecimal))
        {
          arrayOfString = Cast.BigDecimal.castStrings((BigDecimal)paramObject);
        }
        else if ((paramObject instanceof Short))
        {
          arrayOfString = Cast.Short.castStrings((Short)paramObject);
        }
        else
        {
          if (!paramObject.getClass().isArray()) {
            break;
          }
          Object[] arrayOfObject = (Object[])paramObject;
          arrayOfString = new String[arrayOfObject.length];
          for (int i = 0; i < arrayOfObject.length; i++) {
            arrayOfString[i] = arrayOfObject[i].toString();
          }
        }
      }
      throw new TypeCastException([Ljava.lang.String.class, paramObject);
    }
  }
  
  public static class CastShort
    extends CastImpl<Short>
  {
    protected BigDecimal castBigDecimal(Short paramShort)
      throws TypeCastException
    {
      return BigDecimal.valueOf(paramShort.shortValue());
    }
    
    protected Boolean castBoolean(Short paramShort)
      throws TypeCastException
    {
      if (paramShort.shortValue() != 0) {}
      for (Boolean localBoolean = Boolean.valueOf(true);; localBoolean = Boolean.valueOf(false)) {
        return localBoolean;
      }
    }
    
    protected Byte castByte(Short paramShort)
      throws TypeCastException
    {
      return Byte.valueOf(paramShort.byteValue());
    }
    
    protected Double castDouble(Short paramShort)
      throws TypeCastException
    {
      return Double.valueOf(paramShort.doubleValue());
    }
    
    protected Float castFloat(Short paramShort)
      throws TypeCastException
    {
      return Float.valueOf(paramShort.floatValue());
    }
    
    protected Integer castInteger(Short paramShort)
      throws TypeCastException
    {
      return Integer.valueOf(paramShort.intValue());
    }
    
    protected Long castLong(Short paramShort)
      throws TypeCastException
    {
      return Long.valueOf(paramShort.longValue());
    }
    
    protected Short castShort(Short paramShort)
      throws TypeCastException
    {
      return paramShort;
    }
    
    protected String castString(Short paramShort)
      throws TypeCastException
    {
      return paramShort.toString();
    }
    
    protected String[] castStrings(Short paramShort)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramShort.toString();
      return arrayOfString;
    }
  }
  
  public static class CastString
    extends CastImpl<String>
  {
    protected BigDecimal castBigDecimal(String paramString)
      throws TypeCastException
    {
      try
      {
        BigDecimal localBigDecimal = new BigDecimal(paramString);
        return localBigDecimal;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, BigDecimal.class, paramString);
      }
    }
    
    protected Boolean castBoolean(String paramString)
      throws TypeCastException
    {
      return Boolean.valueOf(paramString);
    }
    
    protected Byte castByte(String paramString)
      throws TypeCastException
    {
      try
      {
        Byte localByte = Byte.valueOf(new Double(paramString).byteValue());
        return localByte;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, Byte.class, paramString);
      }
    }
    
    protected Double castDouble(String paramString)
      throws TypeCastException
    {
      try
      {
        Double localDouble = Double.valueOf(paramString);
        return localDouble;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, Double.class, paramString);
      }
    }
    
    protected Float castFloat(String paramString)
      throws TypeCastException
    {
      try
      {
        Float localFloat = Float.valueOf(paramString);
        return localFloat;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, Float.class, paramString);
      }
    }
    
    protected Integer castInteger(String paramString)
      throws TypeCastException
    {
      try
      {
        Integer localInteger = Integer.valueOf(new Double(paramString).intValue());
        return localInteger;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, Integer.class, paramString);
      }
    }
    
    protected Long castLong(String paramString)
      throws TypeCastException
    {
      try
      {
        Long localLong = Long.valueOf(new Double(paramString).longValue());
        return localLong;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, Double.class, paramString);
      }
    }
    
    protected Short castShort(String paramString)
      throws TypeCastException
    {
      try
      {
        Short localShort = Short.valueOf(new Double(paramString).shortValue());
        return localShort;
      }
      catch (Exception localException)
      {
        throw new TypeCastException(localException, Short.class, paramString);
      }
    }
    
    protected String castString(String paramString)
      throws TypeCastException
    {
      return paramString;
    }
    
    protected String[] castStrings(String paramString)
      throws TypeCastException
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramString;
      return arrayOfString;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.CastImpl
 * JD-Core Version:    0.7.0.1
 */