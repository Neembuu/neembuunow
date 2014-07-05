package jp.co.asbit.pvstar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract interface MyBindService
  extends IInterface
{
  public abstract void cancelLoading()
    throws RemoteException;
  
  public abstract void fprev()
    throws RemoteException;
  
  public abstract int getCurrentPosition()
    throws RemoteException;
  
  public abstract int getDuration()
    throws RemoteException;
  
  public abstract EqualizerConstants getEqualizerConstants()
    throws RemoteException;
  
  public abstract int[] getEqualizerCustomBandLevels()
    throws RemoteException;
  
  public abstract int getEqualizerPreset()
    throws RemoteException;
  
  public abstract int getRepeatState()
    throws RemoteException;
  
  public abstract boolean getShuffle()
    throws RemoteException;
  
  public abstract long getSleepTimer()
    throws RemoteException;
  
  public abstract int getVIndex()
    throws RemoteException;
  
  public abstract List getVideoRowItems()
    throws RemoteException;
  
  public abstract boolean isEqualizerEnabled()
    throws RemoteException;
  
  public abstract boolean isPlaying()
    throws RemoteException;
  
  public abstract void killSleepTimer()
    throws RemoteException;
  
  public abstract void moveTrack(int paramInt)
    throws RemoteException;
  
  public abstract void next()
    throws RemoteException;
  
  public abstract void pause()
    throws RemoteException;
  
  public abstract void play()
    throws RemoteException;
  
  public abstract void prev()
    throws RemoteException;
  
  public abstract void saveEqualizerCustomBandLevels()
    throws RemoteException;
  
  public abstract void seekTo(int paramInt)
    throws RemoteException;
  
  public abstract void setBindFlag(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setEqualizer(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void setEqualizerEnabled(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setEqualizerPreset(int paramInt)
    throws RemoteException;
  
  public abstract void setRepeatState(int paramInt)
    throws RemoteException;
  
  public abstract void setShuffle(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setSleepTimer(long paramLong)
    throws RemoteException;
  
  public abstract void videoSizeChange()
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements MyBindService
  {
    private static final String DESCRIPTOR = "jp.co.asbit.pvstar.MyBindService";
    static final int TRANSACTION_cancelLoading = 22;
    static final int TRANSACTION_fprev = 6;
    static final int TRANSACTION_getCurrentPosition = 1;
    static final int TRANSACTION_getDuration = 2;
    static final int TRANSACTION_getEqualizerConstants = 30;
    static final int TRANSACTION_getEqualizerCustomBandLevels = 26;
    static final int TRANSACTION_getEqualizerPreset = 25;
    static final int TRANSACTION_getRepeatState = 11;
    static final int TRANSACTION_getShuffle = 21;
    static final int TRANSACTION_getSleepTimer = 15;
    static final int TRANSACTION_getVIndex = 14;
    static final int TRANSACTION_getVideoRowItems = 13;
    static final int TRANSACTION_isEqualizerEnabled = 29;
    static final int TRANSACTION_isPlaying = 12;
    static final int TRANSACTION_killSleepTimer = 17;
    static final int TRANSACTION_moveTrack = 9;
    static final int TRANSACTION_next = 7;
    static final int TRANSACTION_pause = 4;
    static final int TRANSACTION_play = 3;
    static final int TRANSACTION_prev = 5;
    static final int TRANSACTION_saveEqualizerCustomBandLevels = 27;
    static final int TRANSACTION_seekTo = 8;
    static final int TRANSACTION_setBindFlag = 18;
    static final int TRANSACTION_setEqualizer = 23;
    static final int TRANSACTION_setEqualizerEnabled = 28;
    static final int TRANSACTION_setEqualizerPreset = 24;
    static final int TRANSACTION_setRepeatState = 10;
    static final int TRANSACTION_setShuffle = 20;
    static final int TRANSACTION_setSleepTimer = 16;
    static final int TRANSACTION_videoSizeChange = 19;
    
    public Stub()
    {
      attachInterface(this, "jp.co.asbit.pvstar.MyBindService");
    }
    
    public static MyBindService asInterface(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null) {
        localObject = null;
      }
      for (;;)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("jp.co.asbit.pvstar.MyBindService");
        if ((localIInterface != null) && ((localIInterface instanceof MyBindService))) {
          localObject = (MyBindService)localIInterface;
        } else {
          localObject = new Proxy(paramIBinder);
        }
      }
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      int i = 0;
      int j = 1;
      switch (paramInt1)
      {
      default: 
        j = super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      }
      for (;;)
      {
        return j;
        paramParcel2.writeString("jp.co.asbit.pvstar.MyBindService");
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        int i8 = getCurrentPosition();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i8);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        int i7 = getDuration();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i7);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        play();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        pause();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        prev();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        fprev();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        next();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        seekTo(paramParcel1.readInt());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        moveTrack(paramParcel1.readInt());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        setRepeatState(paramParcel1.readInt());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        int i6 = getRepeatState();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i6);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        boolean bool3 = isPlaying();
        paramParcel2.writeNoException();
        if (bool3) {
          i = j;
        }
        paramParcel2.writeInt(i);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        List localList = getVideoRowItems();
        paramParcel2.writeNoException();
        paramParcel2.writeList(localList);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        int i5 = getVIndex();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i5);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        long l = getSleepTimer();
        paramParcel2.writeNoException();
        paramParcel2.writeLong(l);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        setSleepTimer(paramParcel1.readLong());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        killSleepTimer();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        if (paramParcel1.readInt() != 0) {}
        int i4;
        for (int i3 = j;; i4 = 0)
        {
          setBindFlag(i3);
          paramParcel2.writeNoException();
          break;
        }
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        videoSizeChange();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        if (paramParcel1.readInt() != 0) {}
        int i2;
        for (int i1 = j;; i2 = 0)
        {
          setShuffle(i1);
          paramParcel2.writeNoException();
          break;
        }
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        boolean bool2 = getShuffle();
        paramParcel2.writeNoException();
        if (bool2) {
          i = j;
        }
        paramParcel2.writeInt(i);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        cancelLoading();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        setEqualizer(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        setEqualizerPreset(paramParcel1.readInt());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        int n = getEqualizerPreset();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(n);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        int[] arrayOfInt = getEqualizerCustomBandLevels();
        paramParcel2.writeNoException();
        paramParcel2.writeIntArray(arrayOfInt);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        saveEqualizerCustomBandLevels();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        if (paramParcel1.readInt() != 0) {}
        int m;
        for (int k = j;; m = 0)
        {
          setEqualizerEnabled(k);
          paramParcel2.writeNoException();
          break;
        }
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        boolean bool1 = isEqualizerEnabled();
        paramParcel2.writeNoException();
        if (bool1) {
          i = j;
        }
        paramParcel2.writeInt(i);
        continue;
        paramParcel1.enforceInterface("jp.co.asbit.pvstar.MyBindService");
        EqualizerConstants localEqualizerConstants = getEqualizerConstants();
        paramParcel2.writeNoException();
        if (localEqualizerConstants != null)
        {
          paramParcel2.writeInt(j);
          localEqualizerConstants.writeToParcel(paramParcel2, j);
        }
        else
        {
          paramParcel2.writeInt(0);
        }
      }
    }
    
    private static class Proxy
      implements MyBindService
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public void cancelLoading()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(22, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void fprev()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getCurrentPosition()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getDuration()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public EqualizerConstants getEqualizerConstants()
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 29	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_1
        //   4: invokestatic 29	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_2
        //   8: aload_1
        //   9: ldc 31
        //   11: invokevirtual 35	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_0
        //   15: getfield 18	jp/co/asbit/pvstar/MyBindService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   18: bipush 30
        //   20: aload_1
        //   21: aload_2
        //   22: iconst_0
        //   23: invokeinterface 41 5 0
        //   28: pop
        //   29: aload_2
        //   30: invokevirtual 44	android/os/Parcel:readException	()V
        //   33: aload_2
        //   34: invokevirtual 53	android/os/Parcel:readInt	()I
        //   37: ifeq +28 -> 65
        //   40: getstatic 62	jp/co/asbit/pvstar/EqualizerConstants:CREATOR	Landroid/os/Parcelable$Creator;
        //   43: aload_2
        //   44: invokeinterface 68 2 0
        //   49: checkcast 58	jp/co/asbit/pvstar/EqualizerConstants
        //   52: astore 5
        //   54: aload_2
        //   55: invokevirtual 47	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: invokevirtual 47	android/os/Parcel:recycle	()V
        //   62: aload 5
        //   64: areturn
        //   65: aconst_null
        //   66: astore 5
        //   68: goto -14 -> 54
        //   71: astore_3
        //   72: aload_2
        //   73: invokevirtual 47	android/os/Parcel:recycle	()V
        //   76: aload_1
        //   77: invokevirtual 47	android/os/Parcel:recycle	()V
        //   80: aload_3
        //   81: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	82	0	this	Proxy
        //   3	74	1	localParcel1	Parcel
        //   7	66	2	localParcel2	Parcel
        //   71	10	3	localObject	Object
        //   52	15	5	localEqualizerConstants	EqualizerConstants
        // Exception table:
        //   from	to	target	type
        //   8	54	71	finally
      }
      
      public int[] getEqualizerCustomBandLevels()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(26, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int[] arrayOfInt = localParcel2.createIntArray();
          return arrayOfInt;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getEqualizerPreset()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(25, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "jp.co.asbit.pvstar.MyBindService";
      }
      
      public int getRepeatState()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean getShuffle()
        throws RemoteException
      {
        boolean bool = false;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(21, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public long getSleepTimer()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(15, localParcel1, localParcel2, 0);
          localParcel2.readException();
          long l = localParcel2.readLong();
          return l;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getVIndex()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(14, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public List getVideoRowItems()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(13, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.readArrayList(getClass().getClassLoader());
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean isEqualizerEnabled()
        throws RemoteException
      {
        boolean bool = false;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(29, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean isPlaying()
        throws RemoteException
      {
        boolean bool = false;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(12, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void killSleepTimer()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void moveTrack(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void next()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void pause()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void play()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void prev()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void saveEqualizerCustomBandLevels()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(27, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void seekTo(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setBindFlag(boolean paramBoolean)
        throws RemoteException
      {
        int i = 0;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.mRemote.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setEqualizer(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(23, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setEqualizerEnabled(boolean paramBoolean)
        throws RemoteException
      {
        int i = 0;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.mRemote.transact(28, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setEqualizerPreset(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(24, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setRepeatState(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setShuffle(boolean paramBoolean)
        throws RemoteException
      {
        int i = 0;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.mRemote.transact(20, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setSleepTimer(long paramLong)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          localParcel1.writeLong(paramLong);
          this.mRemote.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void videoSizeChange()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("jp.co.asbit.pvstar.MyBindService");
          this.mRemote.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.MyBindService
 * JD-Core Version:    0.7.0.1
 */