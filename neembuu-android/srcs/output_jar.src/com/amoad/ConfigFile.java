package com.amoad;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

class ConfigFile
{
  static final String KEY_DSPNAME = "dsp_name";
  static final String KEY_DSPUID = "dspuid";
  static final String KEY_ID = "id";
  static final String KEY_OPTOUT = "optout";
  private static final String TAG = "ConfigFile";
  private final String CRYPT_ALGORITHM = "AES/ECB/PKCS7Padding";
  private final String FILEPATH_FILES = "/files";
  private final String HASH_ALGORITHUM = "SHA-1";
  private final int SECRET_KEY_LENGTH = 32;
  private final String SECRET_KEY_SPEC = "AES";
  private LinkedHashMap<String, String> mConfig = null;
  private Context mContext = null;
  private boolean mDebug = false;
  private String mDomainName = null;
  private String mFileName = null;
  private String mModelName = null;
  private byte[] mSecretKey = null;
  private String mUserPath = null;
  
  ConfigFile(Context paramContext)
  {
    this.mContext = paramContext;
    this.mConfig = new LinkedHashMap();
    this.mUserPath = getUserPath();
  }
  
  private String byte2hex(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      String str = Integer.toHexString(0xFF & paramArrayOfByte[j]);
      if (str.length() == 1) {
        localStringBuffer.append("0");
      }
      localStringBuffer.append(str);
    }
    return localStringBuffer.toString();
  }
  
  private String createFileName()
  {
    return getHash(this.mDomainName + this.mModelName, "SHA-1");
  }
  
  private byte[] createSecretKey(String paramString)
  {
    new byte[32];
    StringBuilder localStringBuilder = new StringBuilder(paramString);
    byte[] arrayOfByte;
    if (localStringBuilder.length() < 32)
    {
      int i = 32 - localStringBuilder.length();
      for (int j = 0; j < i; j++) {
        localStringBuilder.append("0");
      }
      arrayOfByte = localStringBuilder.toString().getBytes();
    }
    for (;;)
    {
      return arrayOfByte;
      if (localStringBuilder.length() > 32) {
        arrayOfByte = localStringBuilder.substring(0, 32).getBytes();
      } else {
        arrayOfByte = localStringBuilder.toString().getBytes();
      }
    }
  }
  
  private String createUuid()
  {
    String str = UUID.randomUUID().toString();
    if (this.mDebug) {
      Log.d("ConfigFile", "uuid:" + str);
    }
    return str;
  }
  
  private byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte2, "AES");
    try
    {
      Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
      localCipher.init(2, localSecretKeySpec);
      byte[] arrayOfByte2 = localCipher.doFinal(paramArrayOfByte1);
      arrayOfByte1 = arrayOfByte2;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localNoSuchAlgorithmException.printStackTrace();
        }
        byte[] arrayOfByte1 = new byte[0];
      }
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localNoSuchPaddingException.printStackTrace();
        }
      }
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localInvalidKeyException.printStackTrace();
        }
      }
    }
    catch (IllegalBlockSizeException localIllegalBlockSizeException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localIllegalBlockSizeException.printStackTrace();
        }
      }
    }
    catch (BadPaddingException localBadPaddingException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localBadPaddingException.printStackTrace();
        }
      }
    }
    return arrayOfByte1;
  }
  
  private byte[] encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte2, "AES");
    try
    {
      Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
      localCipher.init(1, localSecretKeySpec);
      byte[] arrayOfByte2 = localCipher.doFinal(paramArrayOfByte1);
      arrayOfByte1 = arrayOfByte2;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localNoSuchAlgorithmException.printStackTrace();
        }
        byte[] arrayOfByte1 = new byte[0];
      }
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localNoSuchPaddingException.printStackTrace();
        }
      }
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localInvalidKeyException.printStackTrace();
        }
      }
    }
    catch (IllegalBlockSizeException localIllegalBlockSizeException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localIllegalBlockSizeException.printStackTrace();
        }
      }
    }
    catch (BadPaddingException localBadPaddingException)
    {
      for (;;)
      {
        if (this.mDebug) {
          localBadPaddingException.printStackTrace();
        }
      }
    }
    return arrayOfByte1;
  }
  
  private String getFilePathFromPackages()
  {
    List localList = this.mContext.getPackageManager().getInstalledApplications(0);
    long l1 = 0L;
    String str = null;
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      ApplicationInfo localApplicationInfo = (ApplicationInfo)localIterator.next();
      if ((!localApplicationInfo.packageName.startsWith("com.android.")) && (!localApplicationInfo.packageName.startsWith("com.google.")))
      {
        File localFile = new File(getFullPath(localApplicationInfo.packageName, this.mFileName));
        if (localFile.exists())
        {
          long l2 = localFile.lastModified();
          if (l1 < l2)
          {
            l1 = l2;
            str = localFile.getPath();
          }
        }
      }
    }
    return str;
  }
  
  private String getFullPath(String paramString1, String paramString2)
  {
    return this.mUserPath + "/" + paramString1 + "/files" + "/" + paramString2;
  }
  
  private String getHash(String paramString1, String paramString2)
  {
    String str = null;
    if ((paramString1 == null) || (paramString2 == null)) {}
    for (;;)
    {
      return str;
      StringBuffer localStringBuffer;
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance(paramString2);
        localMessageDigest.reset();
        localMessageDigest.update(paramString1.getBytes());
        byte[] arrayOfByte = localMessageDigest.digest();
        localStringBuffer = new StringBuffer();
        int i = arrayOfByte.length;
        for (int j = 0; j < i; j++)
        {
          localStringBuffer.append(Integer.toHexString(0xF & arrayOfByte[j] >> 4));
          localStringBuffer.append(Integer.toHexString(0xF & arrayOfByte[j]));
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      str = localStringBuffer.toString();
    }
  }
  
  private String getUserPath()
  {
    String str1 = "";
    int i = 0;
    String str2 = this.mContext.getFilesDir().toString();
    Matcher localMatcher1 = Pattern.compile("/files$").matcher(str2);
    while (localMatcher1.find()) {
      str1 = str2.substring(0, -1 + localMatcher1.start());
    }
    Matcher localMatcher2 = Pattern.compile("/").matcher(str1);
    while (localMatcher2.find()) {
      i = localMatcher2.start();
    }
    return str2.substring(0, i);
  }
  
  private byte[] hex2byte(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    for (int i = 0; i < arrayOfByte.length; i++) {
      arrayOfByte[i] = ((byte)Integer.parseInt(paramString.substring(i * 2, 2 * (i + 1)), 16));
    }
    return arrayOfByte;
  }
  
  /* Error */
  private LinkedHashMap<String, String> readConfigFile(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield 46	com/amoad/ConfigFile:mDebug	Z
    //   6: ifeq +29 -> 35
    //   9: ldc 20
    //   11: new 114	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 115	java/lang/StringBuilder:<init>	()V
    //   18: ldc_w 308
    //   21: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: aload_1
    //   25: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: invokevirtual 119	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: invokestatic 153	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   34: pop
    //   35: new 236	java/io/File
    //   38: dup
    //   39: aload_1
    //   40: invokespecial 240	java/io/File:<init>	(Ljava/lang/String;)V
    //   43: astore 5
    //   45: aload 5
    //   47: invokevirtual 243	java/io/File:exists	()Z
    //   50: ifeq +192 -> 242
    //   53: new 310	java/io/FileReader
    //   56: dup
    //   57: aload 5
    //   59: invokespecial 313	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   62: astore 6
    //   64: new 315	java/io/BufferedReader
    //   67: dup
    //   68: aload 6
    //   70: invokespecial 318	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   73: astore 7
    //   75: new 80	java/util/LinkedHashMap
    //   78: dup
    //   79: invokespecial 81	java/util/LinkedHashMap:<init>	()V
    //   82: astore 8
    //   84: aload 7
    //   86: invokevirtual 321	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   89: astore 9
    //   91: aload 9
    //   93: ifnull +160 -> 253
    //   96: aload 9
    //   98: ldc_w 323
    //   101: iconst_2
    //   102: invokevirtual 327	java/lang/String:split	(Ljava/lang/String;I)[Ljava/lang/String;
    //   105: astore 10
    //   107: aload 10
    //   109: iconst_0
    //   110: aaload
    //   111: ifnull -27 -> 84
    //   114: aload 10
    //   116: iconst_0
    //   117: aaload
    //   118: ldc_w 269
    //   121: invokevirtual 331	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   124: ifne -40 -> 84
    //   127: aload_0
    //   128: getfield 46	com/amoad/ConfigFile:mDebug	Z
    //   131: ifeq +32 -> 163
    //   134: ldc 20
    //   136: new 114	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 115	java/lang/StringBuilder:<init>	()V
    //   143: ldc_w 333
    //   146: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: aload 10
    //   151: iconst_0
    //   152: aaload
    //   153: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: invokevirtual 119	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: invokestatic 153	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   162: pop
    //   163: aload 10
    //   165: iconst_0
    //   166: aaload
    //   167: ldc 14
    //   169: invokevirtual 331	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   172: ifne +15 -> 187
    //   175: aload 10
    //   177: iconst_0
    //   178: aaload
    //   179: ldc 11
    //   181: invokevirtual 331	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   184: ifeq +60 -> 244
    //   187: new 98	java/lang/String
    //   190: dup
    //   191: aload_0
    //   192: aload_0
    //   193: aload 10
    //   195: iconst_1
    //   196: aaload
    //   197: invokespecial 335	com/amoad/ConfigFile:hex2byte	(Ljava/lang/String;)[B
    //   200: aload_0
    //   201: getfield 58	com/amoad/ConfigFile:mSecretKey	[B
    //   204: invokespecial 337	com/amoad/ConfigFile:decrypt	([B[B)[B
    //   207: invokespecial 339	java/lang/String:<init>	([B)V
    //   210: astore 11
    //   212: aload 8
    //   214: aload 10
    //   216: iconst_0
    //   217: aaload
    //   218: aload 11
    //   220: invokevirtual 343	java/util/LinkedHashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   223: pop
    //   224: goto -140 -> 84
    //   227: astore_3
    //   228: aload 8
    //   230: astore_2
    //   231: aload_0
    //   232: getfield 46	com/amoad/ConfigFile:mDebug	Z
    //   235: ifeq +7 -> 242
    //   238: aload_3
    //   239: invokevirtual 344	java/io/FileNotFoundException:printStackTrace	()V
    //   242: aload_2
    //   243: areturn
    //   244: aload 10
    //   246: iconst_1
    //   247: aaload
    //   248: astore 11
    //   250: goto -38 -> 212
    //   253: aload 7
    //   255: invokevirtual 347	java/io/BufferedReader:close	()V
    //   258: aload 6
    //   260: invokevirtual 348	java/io/FileReader:close	()V
    //   263: aload 8
    //   265: astore_2
    //   266: goto -24 -> 242
    //   269: astore 4
    //   271: aload_0
    //   272: getfield 46	com/amoad/ConfigFile:mDebug	Z
    //   275: ifeq -33 -> 242
    //   278: aload 4
    //   280: invokevirtual 349	java/io/IOException:printStackTrace	()V
    //   283: goto -41 -> 242
    //   286: astore 4
    //   288: aload 8
    //   290: astore_2
    //   291: goto -20 -> 271
    //   294: astore_3
    //   295: goto -64 -> 231
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	298	0	this	ConfigFile
    //   0	298	1	paramString	String
    //   1	290	2	localObject	Object
    //   227	12	3	localFileNotFoundException1	FileNotFoundException
    //   294	1	3	localFileNotFoundException2	FileNotFoundException
    //   269	10	4	localIOException1	IOException
    //   286	1	4	localIOException2	IOException
    //   43	15	5	localFile	File
    //   62	197	6	localFileReader	java.io.FileReader
    //   73	181	7	localBufferedReader	java.io.BufferedReader
    //   82	207	8	localLinkedHashMap	LinkedHashMap
    //   89	8	9	str1	String
    //   105	140	10	arrayOfString	String[]
    //   210	39	11	str2	String
    // Exception table:
    //   from	to	target	type
    //   84	224	227	java/io/FileNotFoundException
    //   244	263	227	java/io/FileNotFoundException
    //   2	84	269	java/io/IOException
    //   84	224	286	java/io/IOException
    //   244	263	286	java/io/IOException
    //   2	84	294	java/io/FileNotFoundException
  }
  
  private void writeConfigFile(LinkedHashMap<String, String> paramLinkedHashMap)
  {
    try
    {
      FileOutputStream localFileOutputStream = this.mContext.openFileOutput(this.mFileName, 1);
      if (this.mDebug) {
        Log.d("ConfigFile", "write file:" + this.mFileName);
      }
      Iterator localIterator = paramLinkedHashMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        localEntry = (Map.Entry)localIterator.next();
        String str1 = (String)localEntry.getKey();
        if ((!str1.equals("id")) && (!str1.equals("dspuid"))) {
          break label244;
        }
        str2 = byte2hex(encrypt(((String)localEntry.getValue()).getBytes(), this.mSecretKey));
        String str3 = (String)localEntry.getKey() + "," + str2 + "\n";
        localFileOutputStream.write(str3.getBytes());
        if (this.mDebug) {
          Log.d("ConfigFile", "write data:" + str3);
        }
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      for (;;)
      {
        Map.Entry localEntry;
        if (this.mDebug) {
          localFileNotFoundException.printStackTrace();
        }
        return;
        String str2 = (String)localEntry.getValue();
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        label244:
        if (this.mDebug) {
          localIOException.printStackTrace();
        }
      }
    }
  }
  
  void createNewFile()
  {
    String str = createUuid();
    this.mConfig.put("id", str);
  }
  
  void createNewFileDspUid()
  {
    String str = createUuid();
    this.mConfig.put("dspuid", str);
  }
  
  void generateConfigFile()
  {
    if (this.mDebug) {
      Log.d("ConfigFile", "read localfile");
    }
    if (!readLoacalFile())
    {
      if (this.mDebug) {
        Log.d("ConfigFile", "read other packages");
      }
      if (!readOtherFile())
      {
        if (this.mDebug) {
          Log.d("ConfigFile", "new file");
        }
        createNewFile();
        writeConfigFile(this.mConfig);
      }
    }
    if ((!this.mConfig.containsKey("id")) || (((String)this.mConfig.get("id")).equals("")) || (this.mConfig.get("id") == null))
    {
      createNewFile();
      writeConfigFile(this.mConfig);
    }
  }
  
  void generateFileName(String paramString1, String paramString2)
  {
    this.mDomainName = paramString1;
    this.mModelName = paramString2;
    this.mFileName = createFileName();
    this.mSecretKey = createSecretKey(this.mDomainName + this.mModelName);
  }
  
  LinkedHashMap<String, String> getConfig()
  {
    return this.mConfig;
  }
  
  boolean hasLocalFile()
  {
    return new File(getFullPath(this.mContext.getPackageName(), this.mFileName)).exists();
  }
  
  boolean readLoacalFile()
  {
    boolean bool = false;
    if (hasLocalFile())
    {
      this.mConfig = readConfigFile(getFullPath(this.mContext.getPackageName(), this.mFileName));
      bool = true;
    }
    return bool;
  }
  
  boolean readOtherFile()
  {
    boolean bool = false;
    String str = getFilePathFromPackages();
    if (str != null)
    {
      this.mConfig = readConfigFile(str);
      writeConfigFile(this.mConfig);
      bool = true;
    }
    return bool;
  }
  
  void setConfig(LinkedHashMap<String, String> paramLinkedHashMap)
  {
    writeConfigFile(paramLinkedHashMap);
    this.mConfig = paramLinkedHashMap;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.ConfigFile
 * JD-Core Version:    0.7.0.1
 */