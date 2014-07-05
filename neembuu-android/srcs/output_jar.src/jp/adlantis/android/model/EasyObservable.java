package jp.adlantis.android.model;

public abstract interface EasyObservable<T>
{
  public abstract void addListener(OnChangeListener<T> paramOnChangeListener);
  
  public abstract void removeListener(OnChangeListener<T> paramOnChangeListener);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.model.EasyObservable
 * JD-Core Version:    0.7.0.1
 */