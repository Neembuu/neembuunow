package jp.adlantis.android.mediation.adapters;

import android.util.Log;
import jp.adlantis.android.mediation.AdMediationAdapter;
import jp.adlantis.android.mediation.AdMediationNetworkParameters;
import jp.adlantis.android.utils.AdlantisUtils;

public class AdMediationAdapterFactory
{
  private static final String LOG_TAG = "AdMediationAdapterFactory";
  
  public static AdMediationAdapter create(AdMediationNetworkParameters paramAdMediationNetworkParameters)
  {
    AdMediationAdapter localAdMediationAdapter;
    if ("admob".equalsIgnoreCase(paramAdMediationNetworkParameters.getNetworkName()))
    {
      String[] arrayOfString9 = new String[5];
      arrayOfString9[0] = "com.google.ads.Ad";
      arrayOfString9[1] = "com.google.ads.AdListener";
      arrayOfString9[2] = "com.google.ads.AdRequest";
      arrayOfString9[3] = "com.google.ads.AdSize";
      arrayOfString9[4] = "com.google.ads.AdView";
      String[] arrayOfString10 = new String[1];
      arrayOfString10[0] = "ad_unit_id";
      localAdMediationAdapter = createAdapter("jp.adlantis.android.mediation.adapters.AdMobAdapter", arrayOfString9, paramAdMediationNetworkParameters, arrayOfString10);
    }
    for (;;)
    {
      return localAdMediationAdapter;
      if ("amoad".equalsIgnoreCase(paramAdMediationNetworkParameters.getNetworkName()))
      {
        String[] arrayOfString7 = new String[2];
        arrayOfString7[0] = "jp.co.cyberagent.AMoAdView";
        arrayOfString7[1] = "jp.co.cyberagent.AdCallback";
        String[] arrayOfString8 = new String[1];
        arrayOfString8[0] = "ad_sid";
        localAdMediationAdapter = createAdapter("jp.adlantis.android.mediation.adapters.AMoAdAdapter", arrayOfString7, paramAdMediationNetworkParameters, arrayOfString8);
      }
      else if ("imobile".equalsIgnoreCase(paramAdMediationNetworkParameters.getNetworkName()))
      {
        String[] arrayOfString5 = new String[4];
        arrayOfString5[0] = "jp.co.imobile.android.AdView";
        arrayOfString5[1] = "jp.co.imobile.android.AdViewRequestListener";
        arrayOfString5[2] = "jp.co.imobile.android.AdRequestResult";
        arrayOfString5[3] = "jp.co.imobile.android.AdRequestResultType";
        String[] arrayOfString6 = new String[2];
        arrayOfString6[0] = "media_id";
        arrayOfString6[1] = "spot_id";
        localAdMediationAdapter = createAdapter("jp.adlantis.android.mediation.adapters.IMobileAdapter", arrayOfString5, paramAdMediationNetworkParameters, arrayOfString6);
      }
      else if ("mediba".equalsIgnoreCase(paramAdMediationNetworkParameters.getNetworkName()))
      {
        String[] arrayOfString3 = new String[3];
        arrayOfString3[0] = "mediba.ad.sdk.android.openx.MasAdListener";
        arrayOfString3[1] = "mediba.ad.sdk.android.openx.MasAdView";
        arrayOfString3[2] = "com.mediba.jp.KSL";
        String[] arrayOfString4 = new String[1];
        arrayOfString4[0] = "sid";
        localAdMediationAdapter = createAdapter("jp.adlantis.android.mediation.adapters.MedibaAdAdapter", arrayOfString3, paramAdMediationNetworkParameters, arrayOfString4);
      }
      else if ("nend".equalsIgnoreCase(paramAdMediationNetworkParameters.getNetworkName()))
      {
        String[] arrayOfString1 = new String[1];
        arrayOfString1[0] = "net.nend.android.NendAdView";
        String[] arrayOfString2 = new String[2];
        arrayOfString2[0] = "api_key";
        arrayOfString2[1] = "spot_id";
        localAdMediationAdapter = createAdapter("jp.adlantis.android.mediation.adapters.NendAdapter", arrayOfString1, paramAdMediationNetworkParameters, arrayOfString2);
      }
      else
      {
        localAdMediationAdapter = null;
      }
    }
  }
  
  private static AdMediationAdapter createAdapter(String paramString, String[] paramArrayOfString1, AdMediationNetworkParameters paramAdMediationNetworkParameters, String[] paramArrayOfString2)
  {
    int i = paramArrayOfString2.length;
    int j = 0;
    if (j < i)
    {
      String str = paramAdMediationNetworkParameters.getParameter(paramArrayOfString2[j]);
      if ((str == null) || ("".equals(str)))
      {
        Log.d("AdMediationAdapterFactory", "can not find required parameters: " + paramAdMediationNetworkParameters.getNetworkName() + "[" + str + "]");
        localAdMediationAdapter = null;
      }
    }
    for (;;)
    {
      return localAdMediationAdapter;
      j++;
      break;
      if (!AdlantisUtils.findClass(paramArrayOfString1))
      {
        Log.d("AdMediationAdapterFactory", "can not load required classes: " + paramAdMediationNetworkParameters.getNetworkName());
        localAdMediationAdapter = null;
      }
      try
      {
        localAdMediationAdapter = (AdMediationAdapter)Class.forName(paramString).newInstance();
        try
        {
          Log.d("AdMediationAdapterFactory", "created AdMediationAdapter: " + paramAdMediationNetworkParameters.getNetworkName());
        }
        catch (Exception localException2) {}
      }
      catch (Exception localException1)
      {
        for (;;)
        {
          localAdMediationAdapter = null;
          Object localObject = localException1;
        }
      }
      Log.d("AdMediationAdapterFactory", "exception on creating AdMediationAdapter: " + paramAdMediationNetworkParameters.getNetworkName());
      Log.d("AdMediationAdapterFactory", "exception = " + localException2.toString());
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.adapters.AdMediationAdapterFactory
 * JD-Core Version:    0.7.0.1
 */