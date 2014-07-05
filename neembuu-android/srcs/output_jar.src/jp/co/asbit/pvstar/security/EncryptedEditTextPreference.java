package jp.co.asbit.pvstar.security;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import jp.co.asbit.pvstar.Util;

public class EncryptedEditTextPreference
  extends EditTextPreference
{
  public EncryptedEditTextPreference(Context paramContext)
  {
    super(paramContext);
  }
  
  public EncryptedEditTextPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public EncryptedEditTextPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public String getText()
  {
    return AESCipher.decrypt(super.getText());
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    if (paramBoolean) {}
    for (String str = getPersistedString(null);; str = (String)paramObject)
    {
      super.setText(str);
      return;
    }
  }
  
  public void setText(String paramString)
  {
    if (Util.isStringBlank(paramString)) {
      super.setText(null);
    }
    for (;;)
    {
      return;
      super.setText(AESCipher.encrypt(paramString));
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.security.EncryptedEditTextPreference
 * JD-Core Version:    0.7.0.1
 */