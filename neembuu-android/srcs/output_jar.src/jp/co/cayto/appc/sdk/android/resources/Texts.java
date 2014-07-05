package jp.co.cayto.appc.sdk.android.resources;

import android.content.Context;
import jp.co.cayto.appc.sdk.android.common.AppPreference;
import jp.co.cayto.appc.sdk.android.resources.texts.TextsJA;

public class Texts
{
  public ITexts get;
  
  public Texts(Context paramContext)
  {
    if (AppPreference.getLocale(paramContext).equals("ja_JP")) {}
    for (this.get = new TextsJA();; this.get = new TextsJA()) {
      return;
    }
  }
  
  public static abstract interface ITexts
  {
    public abstract String テキスト_おすすめアプリ();
    
    public abstract String トースト_appC_media_key_なし();
    
    public abstract String 利用規約_オプトアウト_本文();
    
    public abstract String 利用規約_タイトル();
    
    public abstract String 利用規約_プライバシーポリシー_本文();
    
    public abstract String 利用規約_ラベル_エラー();
    
    public abstract String 利用規約_ラベル_オプトアウト();
    
    public abstract String 利用規約_ラベル_プライバシーポリシー();
    
    public abstract String 利用規約_ラベル_同意しない();
    
    public abstract String 利用規約_ラベル_同意しないを選択();
    
    public abstract String 利用規約_ラベル_同意する();
    
    public abstract String 利用規約_ラベル_同意するを選択();
    
    public abstract String 利用規約_ラベル_戻る();
    
    public abstract String 利用規約_ラベル_設定();
    
    public abstract String 利用規約_ラベル_設定完了();
    
    public abstract String 利用規約_ラベル_閉じる();
    
    public abstract String 利用規約_ラベル_項目未選択();
    
    public abstract String 利用規約_本文();
    
    public abstract String 広告_タイトル();
    
    public abstract String 広告_読み込み中();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.resources.Texts
 * JD-Core Version:    0.7.0.1
 */