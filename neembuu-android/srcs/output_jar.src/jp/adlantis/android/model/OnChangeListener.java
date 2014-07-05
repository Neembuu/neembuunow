package jp.adlantis.android.model;

import java.util.EventListener;

public abstract interface OnChangeListener<T>
  extends EventListener
{
  public abstract void onChange(T paramT);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.model.OnChangeListener
 * JD-Core Version:    0.7.0.1
 */