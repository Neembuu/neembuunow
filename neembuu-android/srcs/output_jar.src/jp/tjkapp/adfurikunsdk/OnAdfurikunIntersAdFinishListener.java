package jp.tjkapp.adfurikunsdk;

public abstract interface OnAdfurikunIntersAdFinishListener
{
  public abstract void onAdfurikunIntersAdClose(int paramInt);
  
  public abstract void onAdfurikunIntersAdCustomClose(int paramInt);
  
  public abstract void onAdfurikunIntersAdError(int paramInt1, int paramInt2);
  
  public abstract void onAdfurikunIntersAdMaxEnd(int paramInt);
  
  public abstract void onAdfurikunIntersAdSkip(int paramInt);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.OnAdfurikunIntersAdFinishListener
 * JD-Core Version:    0.7.0.1
 */