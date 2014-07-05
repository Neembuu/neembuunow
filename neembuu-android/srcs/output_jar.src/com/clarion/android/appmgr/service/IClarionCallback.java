package com.clarion.android.appmgr.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IClarionCallback
  extends IInterface
{
  public abstract void onAccessoryNotify(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, String paramString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IClarionCallback
  {
    private static final String DESCRIPTOR = "com.clarion.android.appmgr.service.IClarionCallback";
    static final int TRANSACTION_onAccessoryNotify = 1;
    
    public Stub()
    {
      attachInterface(this, "com.clarion.android.appmgr.service.IClarionCallback");
    }
    
    public static IClarionCallback asInterface(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null) {
        localObject = null;
      }
      for (;;)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.clarion.android.appmgr.service.IClarionCallback");
        if ((localIInterface != null) && ((localIInterface instanceof IClarionCallback))) {
          localObject = (IClarionCallback)localIInterface;
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
      boolean bool;
      switch (paramInt1)
      {
      default: 
        bool = super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      }
      for (;;)
      {
        return bool;
        paramParcel2.writeString("com.clarion.android.appmgr.service.IClarionCallback");
        bool = true;
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionCallback");
        onAccessoryNotify(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.createIntArray(), paramParcel1.readString());
        bool = true;
      }
    }
    
    private static class Proxy
      implements IClarionCallback
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
      
      public String getInterfaceDescriptor()
      {
        return "com.clarion.android.appmgr.service.IClarionCallback";
      }
      
      public void onAccessoryNotify(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          localParcel.writeInt(paramInt3);
          localParcel.writeIntArray(paramArrayOfInt);
          localParcel.writeString(paramString);
          this.mRemote.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.clarion.android.appmgr.service.IClarionCallback
 * JD-Core Version:    0.7.0.1
 */