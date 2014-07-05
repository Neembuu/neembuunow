package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;

class AdfurikunIntersAdUtil
{
  private ArrayList<AdfurikunIntersAdInfo> mAdInfoList = null;
  private ArrayList<AdfurikunIntersAdLayoutInfo> mAdLayoutInfoList = null;
  
  private void addIntersAdLayout(Context paramContext, String paramString)
  {
    if (getIntersAdLayoutInfo(paramString) == null)
    {
      AdfurikunIntersAdLayoutInfo localAdfurikunIntersAdLayoutInfo = new AdfurikunIntersAdLayoutInfo();
      localAdfurikunIntersAdLayoutInfo.app_id = paramString;
      localAdfurikunIntersAdLayoutInfo.start_time = true;
      localAdfurikunIntersAdLayoutInfo.ad_layout = new AdfurikunIntersAdLayout(paramContext);
      localAdfurikunIntersAdLayoutInfo.ad_layout.setAdfurikunAppKey(paramString);
      localAdfurikunIntersAdLayoutInfo.ad_layout.nextAd();
      this.mAdLayoutInfoList.add(localAdfurikunIntersAdLayoutInfo);
    }
  }
  
  public void addIntersAdSetting(Context paramContext, String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4)
  {
    AdfurikunIntersAdInfo localAdfurikunIntersAdInfo = new AdfurikunIntersAdInfo();
    localAdfurikunIntersAdInfo.index = this.mAdInfoList.size();
    localAdfurikunIntersAdInfo.app_id = paramString1;
    localAdfurikunIntersAdInfo.frequency = paramInt1;
    localAdfurikunIntersAdInfo.max = paramInt2;
    if (paramString2 == null) {
      paramString2 = "";
    }
    localAdfurikunIntersAdInfo.intersad_button_name = paramString2;
    if (paramString3 == null) {
      paramString3 = "";
    }
    localAdfurikunIntersAdInfo.cancel_button_name = paramString3;
    if (paramString4 == null) {
      paramString4 = "";
    }
    localAdfurikunIntersAdInfo.custom_button_name = paramString4;
    this.mAdInfoList.add(localAdfurikunIntersAdInfo);
    String str = localAdfurikunIntersAdInfo.index + "_" + paramString1;
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).edit();
    localEditor.putInt(str + AdfurikunConstants.PREFKEY_INTERS_AD_MAX_CT, 0);
    localEditor.commit();
    if (paramString1.length() > 0) {
      addIntersAdLayout(paramContext, paramString1);
    }
  }
  
  public AdfurikunIntersAdInfo getIntersAdInfo(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mAdInfoList.size())) {}
    for (AdfurikunIntersAdInfo localAdfurikunIntersAdInfo = (AdfurikunIntersAdInfo)this.mAdInfoList.get(paramInt);; localAdfurikunIntersAdInfo = null) {
      return localAdfurikunIntersAdInfo;
    }
  }
  
  public AdfurikunIntersAdLayoutInfo getIntersAdLayoutInfo(String paramString)
  {
    int i = this.mAdLayoutInfoList.size();
    for (int j = 0;; j++)
    {
      AdfurikunIntersAdLayoutInfo localAdfurikunIntersAdLayoutInfo;
      if (j >= i) {
        localAdfurikunIntersAdLayoutInfo = null;
      }
      do
      {
        return localAdfurikunIntersAdLayoutInfo;
        localAdfurikunIntersAdLayoutInfo = (AdfurikunIntersAdLayoutInfo)this.mAdLayoutInfoList.get(j);
      } while (localAdfurikunIntersAdLayoutInfo.app_id.equals(paramString));
    }
  }
  
  public boolean isLoadFinished(String paramString)
  {
    AdfurikunIntersAdLayoutInfo localAdfurikunIntersAdLayoutInfo = getIntersAdLayoutInfo(paramString);
    if ((localAdfurikunIntersAdLayoutInfo != null) && (localAdfurikunIntersAdLayoutInfo.ad_layout != null)) {}
    for (boolean bool = localAdfurikunIntersAdLayoutInfo.ad_layout.isLoadFinished();; bool = false) {
      return bool;
    }
  }
  
  public void removeIntersAdAll()
  {
    int i = this.mAdLayoutInfoList.size();
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        this.mAdLayoutInfoList.removeAll(this.mAdLayoutInfoList);
        return;
      }
      AdfurikunIntersAdLayoutInfo localAdfurikunIntersAdLayoutInfo = (AdfurikunIntersAdLayoutInfo)this.mAdLayoutInfoList.get(j);
      if (localAdfurikunIntersAdLayoutInfo.ad_layout != null)
      {
        localAdfurikunIntersAdLayoutInfo.ad_layout.destroy();
        localAdfurikunIntersAdLayoutInfo.ad_layout = null;
      }
    }
  }
  
  class AdfurikunIntersAdInfo
  {
    public String app_id = "";
    public String cancel_button_name = "";
    public String custom_button_name = "";
    public int frequency = 1;
    public int index = 0;
    public String intersad_button_name = "";
    public int max = 0;
    
    AdfurikunIntersAdInfo() {}
  }
  
  class AdfurikunIntersAdLayoutInfo
  {
    public AdfurikunIntersAdLayout ad_layout = null;
    public String app_id = "";
    public boolean start_time = true;
    
    AdfurikunIntersAdLayoutInfo() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunIntersAdUtil
 * JD-Core Version:    0.7.0.1
 */