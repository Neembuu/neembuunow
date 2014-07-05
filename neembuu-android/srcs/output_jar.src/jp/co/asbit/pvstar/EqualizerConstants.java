package jp.co.asbit.pvstar;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EqualizerConstants
  implements Parcelable
{
  public static final Parcelable.Creator<EqualizerConstants> CREATOR = new Parcelable.Creator()
  {
    public EqualizerConstants createFromParcel(Parcel paramAnonymousParcel)
    {
      return new EqualizerConstants(paramAnonymousParcel, null);
    }
    
    public EqualizerConstants[] newArray(int paramAnonymousInt)
    {
      return new EqualizerConstants[paramAnonymousInt];
    }
  };
  private int[] centerFreq;
  private short maxLevel;
  private short minLevel;
  private short numBands;
  private short numPresets;
  private String[] presetsNames;
  
  public EqualizerConstants() {}
  
  private EqualizerConstants(Parcel paramParcel)
  {
    this.maxLevel = ((short)paramParcel.readInt());
    this.minLevel = ((short)paramParcel.readInt());
    this.numBands = ((short)paramParcel.readInt());
    this.centerFreq = paramParcel.createIntArray();
    this.numPresets = ((short)paramParcel.readInt());
    this.presetsNames = paramParcel.createStringArray();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int[] getCenterFreq()
  {
    return this.centerFreq;
  }
  
  public short getMaxLevel()
  {
    return this.maxLevel;
  }
  
  public short getMinLevel()
  {
    return this.minLevel;
  }
  
  public short getNumBands()
  {
    return this.numBands;
  }
  
  public short getNumPresets()
  {
    return this.numPresets;
  }
  
  public String[] getPresetsNames()
  {
    return this.presetsNames;
  }
  
  public void setCenterFreq(int[] paramArrayOfInt)
  {
    this.centerFreq = paramArrayOfInt;
  }
  
  public void setMaxLevel(short paramShort)
  {
    this.maxLevel = paramShort;
  }
  
  public void setMinLevel(short paramShort)
  {
    this.minLevel = paramShort;
  }
  
  public void setNumBands(short paramShort)
  {
    this.numBands = paramShort;
  }
  
  public void setNumPresets(short paramShort)
  {
    this.numPresets = paramShort;
  }
  
  public void setPresetsNames(String[] paramArrayOfString)
  {
    this.presetsNames = paramArrayOfString;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.maxLevel);
    paramParcel.writeInt(this.minLevel);
    paramParcel.writeInt(this.numBands);
    paramParcel.writeIntArray(this.centerFreq);
    paramParcel.writeInt(this.numPresets);
    paramParcel.writeStringArray(this.presetsNames);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.EqualizerConstants
 * JD-Core Version:    0.7.0.1
 */