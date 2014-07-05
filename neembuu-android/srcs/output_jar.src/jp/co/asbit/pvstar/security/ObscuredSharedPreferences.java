package jp.co.asbit.pvstar.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import java.util.Map;
import java.util.Set;

public class ObscuredSharedPreferences
  implements SharedPreferences
{
  protected Context context;
  protected SharedPreferences delegate;
  
  public ObscuredSharedPreferences(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    this.delegate = paramSharedPreferences;
    this.context = paramContext;
  }
  
  public boolean contains(String paramString)
  {
    return this.delegate.contains(paramString);
  }
  
  public Editor edit()
  {
    return new Editor();
  }
  
  public Map<String, ?> getAll()
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    String str = this.delegate.getString(paramString, null);
    if (str != null) {
      paramBoolean = Boolean.parseBoolean(AESCipher.decrypt(str));
    }
    return paramBoolean;
  }
  
  public float getFloat(String paramString, float paramFloat)
  {
    String str = this.delegate.getString(paramString, null);
    if (str != null) {
      paramFloat = Float.parseFloat(AESCipher.decrypt(str));
    }
    return paramFloat;
  }
  
  public int getInt(String paramString, int paramInt)
  {
    String str = this.delegate.getString(paramString, null);
    if (str != null) {
      paramInt = Integer.parseInt(AESCipher.decrypt(str));
    }
    return paramInt;
  }
  
  public long getLong(String paramString, long paramLong)
  {
    String str = this.delegate.getString(paramString, null);
    if (str != null) {
      paramLong = Long.parseLong(AESCipher.decrypt(str));
    }
    return paramLong;
  }
  
  public String getString(String paramString1, String paramString2)
  {
    String str = this.delegate.getString(paramString1, null);
    if (str != null) {
      paramString2 = AESCipher.decrypt(str);
    }
    return paramString2;
  }
  
  public Set<String> getStringSet(String paramString, Set<String> paramSet)
  {
    return null;
  }
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    this.delegate.registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    this.delegate.unregisterOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
  
  public class Editor
    implements SharedPreferences.Editor
  {
    protected SharedPreferences.Editor delegate = ObscuredSharedPreferences.this.delegate.edit();
    
    public Editor() {}
    
    public void apply()
    {
      this.delegate.apply();
    }
    
    public Editor clear()
    {
      this.delegate.clear();
      return this;
    }
    
    public boolean commit()
    {
      return this.delegate.commit();
    }
    
    public Editor putBoolean(String paramString, boolean paramBoolean)
    {
      this.delegate.putString(paramString, AESCipher.encrypt(Boolean.toString(paramBoolean)));
      return this;
    }
    
    public Editor putFloat(String paramString, float paramFloat)
    {
      this.delegate.putString(paramString, AESCipher.encrypt(Float.toString(paramFloat)));
      return this;
    }
    
    public Editor putInt(String paramString, int paramInt)
    {
      this.delegate.putString(paramString, AESCipher.encrypt(Integer.toString(paramInt)));
      return this;
    }
    
    public Editor putLong(String paramString, long paramLong)
    {
      this.delegate.putString(paramString, AESCipher.encrypt(Long.toString(paramLong)));
      return this;
    }
    
    public Editor putString(String paramString1, String paramString2)
    {
      this.delegate.putString(paramString1, AESCipher.encrypt(paramString2));
      return this;
    }
    
    public SharedPreferences.Editor putStringSet(String paramString, Set<String> paramSet)
    {
      return null;
    }
    
    public Editor remove(String paramString)
    {
      this.delegate.remove(paramString);
      return this;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.security.ObscuredSharedPreferences
 * JD-Core Version:    0.7.0.1
 */