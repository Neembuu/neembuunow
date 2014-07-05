package jp.adlantis.android.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

public class ADLAssetUtils
{
  static String ANDROID_ASSET_PREFIX = "/android_asset/";
  
  public static InputStream inputStreamFromAssetUri(Context paramContext, Uri paramUri)
    throws IOException
  {
    if (paramContext == null) {}
    for (InputStream localInputStream = null;; localInputStream = paramContext.getAssets().open(pathFromAssetUri(paramUri))) {
      return localInputStream;
    }
  }
  
  public static boolean isAssetUri(Uri paramUri)
  {
    if ((paramUri.getScheme().equals("file")) && (paramUri.getPath().startsWith(ANDROID_ASSET_PREFIX))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public static boolean isAssetUrl(String paramString)
  {
    if ((paramString != null) && (isAssetUri(Uri.parse(paramString)))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  static String pathFromAssetUri(Uri paramUri)
  {
    if (isAssetUri(paramUri)) {}
    for (String str = paramUri.getPath().substring(ANDROID_ASSET_PREFIX.length());; str = null) {
      return str;
    }
  }
  
  public static String uriForAsset(String paramString)
  {
    return "file://" + ANDROID_ASSET_PREFIX + paramString;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.utils.ADLAssetUtils
 * JD-Core Version:    0.7.0.1
 */