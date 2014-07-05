package com.clarion.android.appmgr.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IClarionService
  extends IInterface
{
  public abstract void command(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    throws RemoteException;
  
  public abstract int createAppInfo()
    throws RemoteException;
  
  public abstract void debugShowCalibInfo()
    throws RemoteException;
  
  public abstract int deleteAllAppInfo()
    throws RemoteException;
  
  public abstract String getAppCategory(int paramInt)
    throws RemoteException;
  
  public abstract String getAppDLSiteURL(int paramInt)
    throws RemoteException;
  
  public abstract String getAppDescriptionEN(int paramInt)
    throws RemoteException;
  
  public abstract String getAppDescriptionES(int paramInt)
    throws RemoteException;
  
  public abstract String getAppDescriptionFR(int paramInt)
    throws RemoteException;
  
  public abstract String getAppDescriptionJP(int paramInt)
    throws RemoteException;
  
  public abstract int getAppDisplay(int paramInt)
    throws RemoteException;
  
  public abstract int getAppDrivingDispCtrl(int paramInt)
    throws RemoteException;
  
  public abstract int getAppDrivingOpeCtrl(int paramInt)
    throws RemoteException;
  
  public abstract String getAppID(int paramInt)
    throws RemoteException;
  
  public abstract String getAppIconDLURL(int paramInt)
    throws RemoteException;
  
  public abstract String getAppNameEN(int paramInt)
    throws RemoteException;
  
  public abstract String getAppNameES(int paramInt)
    throws RemoteException;
  
  public abstract String getAppNameFR(int paramInt)
    throws RemoteException;
  
  public abstract String getAppNameJP(int paramInt)
    throws RemoteException;
  
  public abstract int getAppOrderOrigin(int paramInt)
    throws RemoteException;
  
  public abstract int getAppParkingDispCtrl(int paramInt)
    throws RemoteException;
  
  public abstract int getAppParkingOpeCtrl(int paramInt)
    throws RemoteException;
  
  public abstract String getAppPkgName(int paramInt)
    throws RemoteException;
  
  public abstract String getAppPriceEN(int paramInt)
    throws RemoteException;
  
  public abstract String getAppPriceES(int paramInt)
    throws RemoteException;
  
  public abstract String getAppPriceFR(int paramInt)
    throws RemoteException;
  
  public abstract String getAppPriceJP(int paramInt)
    throws RemoteException;
  
  public abstract String getAppReleaseDate(int paramInt)
    throws RemoteException;
  
  public abstract int getAppRestriction(int paramInt)
    throws RemoteException;
  
  public abstract int getAppSize()
    throws RemoteException;
  
  public abstract int getAppSoundType(int paramInt)
    throws RemoteException;
  
  public abstract int getAppStoppingDispCtrl(int paramInt)
    throws RemoteException;
  
  public abstract int getAppStoppingOpeCtrl(int paramInt)
    throws RemoteException;
  
  public abstract int getAppSupport(int paramInt)
    throws RemoteException;
  
  public abstract String getAppURLName(int paramInt)
    throws RemoteException;
  
  public abstract String getAppVer(int paramInt)
    throws RemoteException;
  
  public abstract int getBluetooth()
    throws RemoteException;
  
  public abstract String getBrand()
    throws RemoteException;
  
  public abstract int getDoubleSlide()
    throws RemoteException;
  
  public abstract int getDoubleTap()
    throws RemoteException;
  
  public abstract int getDrag()
    throws RemoteException;
  
  public abstract int getDrivingSts()
    throws RemoteException;
  
  public abstract int getFlick()
    throws RemoteException;
  
  public abstract int getHeightAspect()
    throws RemoteException;
  
  public abstract int getHeightResolution()
    throws RemoteException;
  
  public abstract int getHomeButton()
    throws RemoteException;
  
  public abstract String getID()
    throws RemoteException;
  
  public abstract int getIllumination()
    throws RemoteException;
  
  public abstract int getMaxFrameSize()
    throws RemoteException;
  
  public abstract int getMicrophone()
    throws RemoteException;
  
  public abstract String getModel()
    throws RemoteException;
  
  public abstract int getPinchIn()
    throws RemoteException;
  
  public abstract int getPinchOut()
    throws RemoteException;
  
  public abstract String getPolicyDate()
    throws RemoteException;
  
  public abstract String getPolicyID()
    throws RemoteException;
  
  public abstract String getProductName()
    throws RemoteException;
  
  public abstract int getProtocolVersion()
    throws RemoteException;
  
  public abstract String getRegion()
    throws RemoteException;
  
  public abstract String getRegionInfo()
    throws RemoteException;
  
  public abstract int getRule(int paramInt)
    throws RemoteException;
  
  public abstract int getSPDispH()
    throws RemoteException;
  
  public abstract int getSPDispW()
    throws RemoteException;
  
  public abstract String getSamplingRate()
    throws RemoteException;
  
  public abstract int getSpeed()
    throws RemoteException;
  
  public abstract int getState()
    throws RemoteException;
  
  public abstract int getStateHardwareInfo()
    throws RemoteException;
  
  public abstract int getStatePolicyInfo()
    throws RemoteException;
  
  public abstract int getTwist()
    throws RemoteException;
  
  public abstract int getType()
    throws RemoteException;
  
  public abstract String getUnitType()
    throws RemoteException;
  
  public abstract int getVRButton()
    throws RemoteException;
  
  public abstract int getWideAffineOffsetX()
    throws RemoteException;
  
  public abstract int getWideAffineOffsetY()
    throws RemoteException;
  
  public abstract float getWideAffineSizeX()
    throws RemoteException;
  
  public abstract float getWideAffineSizeY()
    throws RemoteException;
  
  public abstract int getWidthAspect()
    throws RemoteException;
  
  public abstract int getWidthResolution()
    throws RemoteException;
  
  public abstract boolean isPaired()
    throws RemoteException;
  
  public abstract void registerCallback(IClarionCallback paramIClarionCallback, String paramString)
    throws RemoteException;
  
  public abstract int setAppCategory(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppDLSiteURL(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppDescriptionEN(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppDescriptionES(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppDescriptionFR(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppDescriptionJP(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppDisplay(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppDrivingDispCtrl(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppDrivingOpeCtrl(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppID(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppIconDLURL(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppNameEN(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppNameES(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppNameFR(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppNameJP(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppOrderOrigin(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppParkingDispCtrl(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppParkingOpeCtrl(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppPkgName(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppPriceEN(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppPriceES(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppPriceFR(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppPriceJP(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppReleaseDate(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppRestriction(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppSoundType(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppStoppingDispCtrl(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppStoppingOpeCtrl(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppSupport(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int setAppURLName(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract int setAppVer(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract void setCalibTouch(float paramFloat1, float paramFloat2, int paramInt)
    throws RemoteException;
  
  public abstract int setInitPolicyInfo()
    throws RemoteException;
  
  public abstract int setLaunchAppName(String paramString)
    throws RemoteException;
  
  public abstract int setPolicyDate(String paramString)
    throws RemoteException;
  
  public abstract int setPolicyID(String paramString)
    throws RemoteException;
  
  public abstract int setProductName(String paramString)
    throws RemoteException;
  
  public abstract int setRegionInfo(String paramString)
    throws RemoteException;
  
  public abstract int setRule(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void setSPTouchMarkPosi(float paramFloat1, float paramFloat2, int paramInt)
    throws RemoteException;
  
  public abstract int setStatePolicyInfo(int paramInt)
    throws RemoteException;
  
  public abstract int setUnitType(String paramString)
    throws RemoteException;
  
  public abstract void start()
    throws RemoteException;
  
  public abstract void stop()
    throws RemoteException;
  
  public abstract void unregisterCallback(IClarionCallback paramIClarionCallback, String paramString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IClarionService
  {
    private static final String DESCRIPTOR = "com.clarion.android.appmgr.service.IClarionService";
    static final int TRANSACTION_command = 3;
    static final int TRANSACTION_createAppInfo = 114;
    static final int TRANSACTION_debugShowCalibInfo = 122;
    static final int TRANSACTION_deleteAllAppInfo = 115;
    static final int TRANSACTION_getAppCategory = 80;
    static final int TRANSACTION_getAppDLSiteURL = 67;
    static final int TRANSACTION_getAppDescriptionEN = 58;
    static final int TRANSACTION_getAppDescriptionES = 60;
    static final int TRANSACTION_getAppDescriptionFR = 59;
    static final int TRANSACTION_getAppDescriptionJP = 57;
    static final int TRANSACTION_getAppDisplay = 81;
    static final int TRANSACTION_getAppDrivingDispCtrl = 72;
    static final int TRANSACTION_getAppDrivingOpeCtrl = 73;
    static final int TRANSACTION_getAppID = 51;
    static final int TRANSACTION_getAppIconDLURL = 69;
    static final int TRANSACTION_getAppNameEN = 54;
    static final int TRANSACTION_getAppNameES = 56;
    static final int TRANSACTION_getAppNameFR = 55;
    static final int TRANSACTION_getAppNameJP = 53;
    static final int TRANSACTION_getAppOrderOrigin = 70;
    static final int TRANSACTION_getAppParkingDispCtrl = 76;
    static final int TRANSACTION_getAppParkingOpeCtrl = 77;
    static final int TRANSACTION_getAppPkgName = 52;
    static final int TRANSACTION_getAppPriceEN = 64;
    static final int TRANSACTION_getAppPriceES = 66;
    static final int TRANSACTION_getAppPriceFR = 65;
    static final int TRANSACTION_getAppPriceJP = 63;
    static final int TRANSACTION_getAppReleaseDate = 61;
    static final int TRANSACTION_getAppRestriction = 71;
    static final int TRANSACTION_getAppSize = 113;
    static final int TRANSACTION_getAppSoundType = 78;
    static final int TRANSACTION_getAppStoppingDispCtrl = 74;
    static final int TRANSACTION_getAppStoppingOpeCtrl = 75;
    static final int TRANSACTION_getAppSupport = 79;
    static final int TRANSACTION_getAppURLName = 68;
    static final int TRANSACTION_getAppVer = 62;
    static final int TRANSACTION_getBluetooth = 21;
    static final int TRANSACTION_getBrand = 11;
    static final int TRANSACTION_getDoubleSlide = 31;
    static final int TRANSACTION_getDoubleTap = 28;
    static final int TRANSACTION_getDrag = 30;
    static final int TRANSACTION_getDrivingSts = 117;
    static final int TRANSACTION_getFlick = 29;
    static final int TRANSACTION_getHeightAspect = 15;
    static final int TRANSACTION_getHeightResolution = 13;
    static final int TRANSACTION_getHomeButton = 23;
    static final int TRANSACTION_getID = 8;
    static final int TRANSACTION_getIllumination = 22;
    static final int TRANSACTION_getMaxFrameSize = 26;
    static final int TRANSACTION_getMicrophone = 24;
    static final int TRANSACTION_getModel = 7;
    static final int TRANSACTION_getPinchIn = 32;
    static final int TRANSACTION_getPinchOut = 33;
    static final int TRANSACTION_getPolicyDate = 41;
    static final int TRANSACTION_getPolicyID = 40;
    static final int TRANSACTION_getProductName = 39;
    static final int TRANSACTION_getProtocolVersion = 9;
    static final int TRANSACTION_getRegion = 10;
    static final int TRANSACTION_getRegionInfo = 37;
    static final int TRANSACTION_getRule = 42;
    static final int TRANSACTION_getSPDispH = 121;
    static final int TRANSACTION_getSPDispW = 120;
    static final int TRANSACTION_getSamplingRate = 27;
    static final int TRANSACTION_getSpeed = 20;
    static final int TRANSACTION_getState = 4;
    static final int TRANSACTION_getStateHardwareInfo = 35;
    static final int TRANSACTION_getStatePolicyInfo = 50;
    static final int TRANSACTION_getTwist = 34;
    static final int TRANSACTION_getType = 5;
    static final int TRANSACTION_getUnitType = 38;
    static final int TRANSACTION_getVRButton = 25;
    static final int TRANSACTION_getWideAffineOffsetX = 18;
    static final int TRANSACTION_getWideAffineOffsetY = 19;
    static final int TRANSACTION_getWideAffineSizeX = 16;
    static final int TRANSACTION_getWideAffineSizeY = 17;
    static final int TRANSACTION_getWidthAspect = 14;
    static final int TRANSACTION_getWidthResolution = 12;
    static final int TRANSACTION_isPaired = 6;
    static final int TRANSACTION_registerCallback = 123;
    static final int TRANSACTION_setAppCategory = 111;
    static final int TRANSACTION_setAppDLSiteURL = 98;
    static final int TRANSACTION_setAppDescriptionEN = 89;
    static final int TRANSACTION_setAppDescriptionES = 91;
    static final int TRANSACTION_setAppDescriptionFR = 90;
    static final int TRANSACTION_setAppDescriptionJP = 88;
    static final int TRANSACTION_setAppDisplay = 112;
    static final int TRANSACTION_setAppDrivingDispCtrl = 103;
    static final int TRANSACTION_setAppDrivingOpeCtrl = 104;
    static final int TRANSACTION_setAppID = 82;
    static final int TRANSACTION_setAppIconDLURL = 100;
    static final int TRANSACTION_setAppNameEN = 85;
    static final int TRANSACTION_setAppNameES = 87;
    static final int TRANSACTION_setAppNameFR = 86;
    static final int TRANSACTION_setAppNameJP = 84;
    static final int TRANSACTION_setAppOrderOrigin = 101;
    static final int TRANSACTION_setAppParkingDispCtrl = 107;
    static final int TRANSACTION_setAppParkingOpeCtrl = 108;
    static final int TRANSACTION_setAppPkgName = 83;
    static final int TRANSACTION_setAppPriceEN = 95;
    static final int TRANSACTION_setAppPriceES = 97;
    static final int TRANSACTION_setAppPriceFR = 96;
    static final int TRANSACTION_setAppPriceJP = 94;
    static final int TRANSACTION_setAppReleaseDate = 92;
    static final int TRANSACTION_setAppRestriction = 102;
    static final int TRANSACTION_setAppSoundType = 109;
    static final int TRANSACTION_setAppStoppingDispCtrl = 105;
    static final int TRANSACTION_setAppStoppingOpeCtrl = 106;
    static final int TRANSACTION_setAppSupport = 110;
    static final int TRANSACTION_setAppURLName = 99;
    static final int TRANSACTION_setAppVer = 93;
    static final int TRANSACTION_setCalibTouch = 119;
    static final int TRANSACTION_setInitPolicyInfo = 36;
    static final int TRANSACTION_setLaunchAppName = 116;
    static final int TRANSACTION_setPolicyDate = 47;
    static final int TRANSACTION_setPolicyID = 46;
    static final int TRANSACTION_setProductName = 45;
    static final int TRANSACTION_setRegionInfo = 43;
    static final int TRANSACTION_setRule = 48;
    static final int TRANSACTION_setSPTouchMarkPosi = 118;
    static final int TRANSACTION_setStatePolicyInfo = 49;
    static final int TRANSACTION_setUnitType = 44;
    static final int TRANSACTION_start = 1;
    static final int TRANSACTION_stop = 2;
    static final int TRANSACTION_unregisterCallback = 124;
    
    public Stub()
    {
      attachInterface(this, "com.clarion.android.appmgr.service.IClarionService");
    }
    
    public static IClarionService asInterface(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null) {
        localObject = null;
      }
      for (;;)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.clarion.android.appmgr.service.IClarionService");
        if ((localIInterface != null) && ((localIInterface instanceof IClarionService))) {
          localObject = (IClarionService)localIInterface;
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
      int i = 1;
      switch (paramInt1)
      {
      default: 
        i = super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      }
      for (;;)
      {
        return i;
        paramParcel2.writeString("com.clarion.android.appmgr.service.IClarionService");
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        start();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        stop();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        command(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.createByteArray());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i81 = getState();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i81);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i80 = getType();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i80);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        boolean bool = isPaired();
        paramParcel2.writeNoException();
        if (bool) {}
        int i79;
        for (int i78 = i;; i79 = 0)
        {
          paramParcel2.writeInt(i78);
          break;
        }
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str30 = getModel();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str30);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str29 = getID();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str29);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i77 = getProtocolVersion();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i77);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str28 = getRegion();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str28);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str27 = getBrand();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str27);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i76 = getWidthResolution();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i76);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i75 = getHeightResolution();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i75);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i74 = getWidthAspect();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i74);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i73 = getHeightAspect();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i73);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        float f2 = getWideAffineSizeX();
        paramParcel2.writeNoException();
        paramParcel2.writeFloat(f2);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        float f1 = getWideAffineSizeY();
        paramParcel2.writeNoException();
        paramParcel2.writeFloat(f1);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i72 = getWideAffineOffsetX();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i72);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i71 = getWideAffineOffsetY();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i71);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i70 = getSpeed();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i70);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i69 = getBluetooth();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i69);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i68 = getIllumination();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i68);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i67 = getHomeButton();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i67);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i66 = getMicrophone();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i66);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i65 = getVRButton();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i65);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i64 = getMaxFrameSize();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i64);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str26 = getSamplingRate();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str26);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i63 = getDoubleTap();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i63);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i62 = getFlick();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i62);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i61 = getDrag();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i61);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i60 = getDoubleSlide();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i60);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i59 = getPinchIn();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i59);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i58 = getPinchOut();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i58);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i57 = getTwist();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i57);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i56 = getStateHardwareInfo();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i56);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i55 = setInitPolicyInfo();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i55);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str25 = getRegionInfo();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str25);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str24 = getUnitType();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str24);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str23 = getProductName();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str23);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str22 = getPolicyID();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str22);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str21 = getPolicyDate();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str21);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i54 = getRule(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i54);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i53 = setRegionInfo(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i53);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i52 = setUnitType(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i52);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i51 = setProductName(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i51);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i50 = setPolicyID(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i50);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i49 = setPolicyDate(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i49);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i48 = setRule(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i48);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i47 = setStatePolicyInfo(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i47);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i46 = getStatePolicyInfo();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i46);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str20 = getAppID(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str20);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str19 = getAppPkgName(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str19);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str18 = getAppNameJP(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str18);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str17 = getAppNameEN(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str17);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str16 = getAppNameFR(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str16);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str15 = getAppNameES(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str15);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str14 = getAppDescriptionJP(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str14);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str13 = getAppDescriptionEN(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str13);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str12 = getAppDescriptionFR(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str12);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str11 = getAppDescriptionES(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str11);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str10 = getAppReleaseDate(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str10);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str9 = getAppVer(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str9);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str8 = getAppPriceJP(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str8);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str7 = getAppPriceEN(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str7);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str6 = getAppPriceFR(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str6);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str5 = getAppPriceES(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str5);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str4 = getAppDLSiteURL(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str4);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str3 = getAppURLName(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str3);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str2 = getAppIconDLURL(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str2);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i45 = getAppOrderOrigin(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i45);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i44 = getAppRestriction(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i44);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i43 = getAppDrivingDispCtrl(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i43);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i42 = getAppDrivingOpeCtrl(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i42);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i41 = getAppStoppingDispCtrl(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i41);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i40 = getAppStoppingOpeCtrl(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i40);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i39 = getAppParkingDispCtrl(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i39);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i38 = getAppParkingOpeCtrl(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i38);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i37 = getAppSoundType(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i37);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i36 = getAppSupport(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i36);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        String str1 = getAppCategory(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str1);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i35 = getAppDisplay(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i35);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i34 = setAppID(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i34);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i33 = setAppPkgName(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i33);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i32 = setAppNameJP(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i32);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i31 = setAppNameEN(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i31);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i30 = setAppNameFR(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i30);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i29 = setAppNameES(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i29);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i28 = setAppDescriptionJP(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i28);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i27 = setAppDescriptionEN(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i27);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i26 = setAppDescriptionFR(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i26);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i25 = setAppDescriptionES(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i25);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i24 = setAppReleaseDate(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i24);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i23 = setAppVer(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i23);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i22 = setAppPriceJP(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i22);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i21 = setAppPriceEN(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i21);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i20 = setAppPriceFR(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i20);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i19 = setAppPriceES(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i19);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i18 = setAppDLSiteURL(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i18);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i17 = setAppURLName(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i17);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i16 = setAppIconDLURL(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i16);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i15 = setAppOrderOrigin(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i15);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i14 = setAppRestriction(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i14);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i13 = setAppDrivingDispCtrl(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i13);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i12 = setAppDrivingOpeCtrl(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i12);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i11 = setAppStoppingDispCtrl(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i11);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i10 = setAppStoppingOpeCtrl(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i10);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i9 = setAppParkingDispCtrl(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i9);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i8 = setAppParkingOpeCtrl(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i8);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i7 = setAppSoundType(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i7);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i6 = setAppSupport(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i6);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i5 = setAppCategory(paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i5);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i4 = setAppDisplay(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i4);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i3 = getAppSize();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i3);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i2 = createAppInfo();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i2);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int i1 = deleteAllAppInfo();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i1);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int n = setLaunchAppName(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(n);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int m = getDrivingSts();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(m);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        setSPTouchMarkPosi(paramParcel1.readFloat(), paramParcel1.readFloat(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        setCalibTouch(paramParcel1.readFloat(), paramParcel1.readFloat(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int k = getSPDispW();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(k);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        int j = getSPDispH();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        debugShowCalibInfo();
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        registerCallback(IClarionCallback.Stub.asInterface(paramParcel1.readStrongBinder()), paramParcel1.readString());
        paramParcel2.writeNoException();
        continue;
        paramParcel1.enforceInterface("com.clarion.android.appmgr.service.IClarionService");
        unregisterCallback(IClarionCallback.Stub.asInterface(paramParcel1.readStrongBinder()), paramParcel1.readString());
        paramParcel2.writeNoException();
      }
    }
    
    private static class Proxy
      implements IClarionService
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
      
      public void command(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeByteArray(paramArrayOfByte);
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
      
      public int createAppInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(114, localParcel1, localParcel2, 0);
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
      
      public void debugShowCalibInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(122, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int deleteAllAppInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(115, localParcel1, localParcel2, 0);
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
      
      public String getAppCategory(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(80, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppDLSiteURL(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(67, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppDescriptionEN(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(58, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppDescriptionES(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(60, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppDescriptionFR(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(59, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppDescriptionJP(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(57, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getAppDisplay(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(81, localParcel1, localParcel2, 0);
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
      
      public int getAppDrivingDispCtrl(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(72, localParcel1, localParcel2, 0);
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
      
      public int getAppDrivingOpeCtrl(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(73, localParcel1, localParcel2, 0);
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
      
      public String getAppID(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(51, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppIconDLURL(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(69, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppNameEN(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(54, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppNameES(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(56, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppNameFR(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(55, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppNameJP(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(53, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getAppOrderOrigin(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(70, localParcel1, localParcel2, 0);
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
      
      public int getAppParkingDispCtrl(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(76, localParcel1, localParcel2, 0);
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
      
      public int getAppParkingOpeCtrl(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(77, localParcel1, localParcel2, 0);
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
      
      public String getAppPkgName(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(52, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppPriceEN(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(64, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppPriceES(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(66, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppPriceFR(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(65, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppPriceJP(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(63, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppReleaseDate(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(61, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getAppRestriction(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(71, localParcel1, localParcel2, 0);
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
      
      public int getAppSize()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(113, localParcel1, localParcel2, 0);
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
      
      public int getAppSoundType(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(78, localParcel1, localParcel2, 0);
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
      
      public int getAppStoppingDispCtrl(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(74, localParcel1, localParcel2, 0);
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
      
      public int getAppStoppingOpeCtrl(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(75, localParcel1, localParcel2, 0);
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
      
      public int getAppSupport(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(79, localParcel1, localParcel2, 0);
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
      
      public String getAppURLName(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(68, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getAppVer(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(62, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getBluetooth()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(21, localParcel1, localParcel2, 0);
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
      
      public String getBrand()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getDoubleSlide()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(31, localParcel1, localParcel2, 0);
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
      
      public int getDoubleTap()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(28, localParcel1, localParcel2, 0);
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
      
      public int getDrag()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(30, localParcel1, localParcel2, 0);
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
      
      public int getDrivingSts()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(117, localParcel1, localParcel2, 0);
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
      
      public int getFlick()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(29, localParcel1, localParcel2, 0);
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
      
      public int getHeightAspect()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(15, localParcel1, localParcel2, 0);
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
      
      public int getHeightResolution()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(13, localParcel1, localParcel2, 0);
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
      
      public int getHomeButton()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(23, localParcel1, localParcel2, 0);
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
      
      public String getID()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getIllumination()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(22, localParcel1, localParcel2, 0);
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
        return "com.clarion.android.appmgr.service.IClarionService";
      }
      
      public int getMaxFrameSize()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(26, localParcel1, localParcel2, 0);
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
      
      public int getMicrophone()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(24, localParcel1, localParcel2, 0);
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
      
      public String getModel()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getPinchIn()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(32, localParcel1, localParcel2, 0);
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
      
      public int getPinchOut()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(33, localParcel1, localParcel2, 0);
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
      
      public String getPolicyDate()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(41, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getPolicyID()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(40, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getProductName()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(39, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getProtocolVersion()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
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
      
      public String getRegion()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getRegionInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(37, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getRule(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(42, localParcel1, localParcel2, 0);
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
      
      public int getSPDispH()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(121, localParcel1, localParcel2, 0);
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
      
      public int getSPDispW()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(120, localParcel1, localParcel2, 0);
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
      
      public String getSamplingRate()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(27, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getSpeed()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(20, localParcel1, localParcel2, 0);
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
      
      public int getState()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
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
      
      public int getStateHardwareInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(35, localParcel1, localParcel2, 0);
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
      
      public int getStatePolicyInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(50, localParcel1, localParcel2, 0);
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
      
      public int getTwist()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(34, localParcel1, localParcel2, 0);
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
      
      public int getType()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
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
      
      public String getUnitType()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(38, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getVRButton()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
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
      
      public int getWideAffineOffsetX()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(18, localParcel1, localParcel2, 0);
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
      
      public int getWideAffineOffsetY()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(19, localParcel1, localParcel2, 0);
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
      
      public float getWideAffineSizeX()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          float f = localParcel2.readFloat();
          return f;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public float getWideAffineSizeY()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          float f = localParcel2.readFloat();
          return f;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getWidthAspect()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
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
      
      public int getWidthResolution()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(12, localParcel1, localParcel2, 0);
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
      
      public boolean isPaired()
        throws RemoteException
      {
        boolean bool = false;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
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
      
      /* Error */
      public void registerCallback(IClarionCallback paramIClarionCallback, String paramString)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_1
        //   16: ifnull +53 -> 69
        //   19: aload_1
        //   20: invokeinterface 155 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 158	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_3
        //   34: aload_2
        //   35: invokevirtual 161	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   38: aload_0
        //   39: getfield 18	com/clarion/android/appmgr/service/IClarionService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   42: bipush 123
        //   44: aload_3
        //   45: aload 4
        //   47: iconst_0
        //   48: invokeinterface 50 5 0
        //   53: pop
        //   54: aload 4
        //   56: invokevirtual 53	android/os/Parcel:readException	()V
        //   59: aload 4
        //   61: invokevirtual 56	android/os/Parcel:recycle	()V
        //   64: aload_3
        //   65: invokevirtual 56	android/os/Parcel:recycle	()V
        //   68: return
        //   69: aconst_null
        //   70: astore 6
        //   72: goto -45 -> 27
        //   75: astore 5
        //   77: aload 4
        //   79: invokevirtual 56	android/os/Parcel:recycle	()V
        //   82: aload_3
        //   83: invokevirtual 56	android/os/Parcel:recycle	()V
        //   86: aload 5
        //   88: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	89	0	this	Proxy
        //   0	89	1	paramIClarionCallback	IClarionCallback
        //   0	89	2	paramString	String
        //   3	80	3	localParcel1	Parcel
        //   7	71	4	localParcel2	Parcel
        //   75	12	5	localObject	Object
        //   25	46	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	59	75	finally
      }
      
      public int setAppCategory(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(111, localParcel1, localParcel2, 0);
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
      
      public int setAppDLSiteURL(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(98, localParcel1, localParcel2, 0);
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
      
      public int setAppDescriptionEN(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(89, localParcel1, localParcel2, 0);
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
      
      public int setAppDescriptionES(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(91, localParcel1, localParcel2, 0);
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
      
      public int setAppDescriptionFR(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(90, localParcel1, localParcel2, 0);
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
      
      public int setAppDescriptionJP(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(88, localParcel1, localParcel2, 0);
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
      
      public int setAppDisplay(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(112, localParcel1, localParcel2, 0);
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
      
      public int setAppDrivingDispCtrl(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(103, localParcel1, localParcel2, 0);
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
      
      public int setAppDrivingOpeCtrl(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(104, localParcel1, localParcel2, 0);
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
      
      public int setAppID(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(82, localParcel1, localParcel2, 0);
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
      
      public int setAppIconDLURL(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(100, localParcel1, localParcel2, 0);
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
      
      public int setAppNameEN(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(85, localParcel1, localParcel2, 0);
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
      
      public int setAppNameES(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(87, localParcel1, localParcel2, 0);
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
      
      public int setAppNameFR(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(86, localParcel1, localParcel2, 0);
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
      
      public int setAppNameJP(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(84, localParcel1, localParcel2, 0);
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
      
      public int setAppOrderOrigin(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(101, localParcel1, localParcel2, 0);
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
      
      public int setAppParkingDispCtrl(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(107, localParcel1, localParcel2, 0);
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
      
      public int setAppParkingOpeCtrl(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(108, localParcel1, localParcel2, 0);
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
      
      public int setAppPkgName(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(83, localParcel1, localParcel2, 0);
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
      
      public int setAppPriceEN(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(95, localParcel1, localParcel2, 0);
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
      
      public int setAppPriceES(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(97, localParcel1, localParcel2, 0);
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
      
      public int setAppPriceFR(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(96, localParcel1, localParcel2, 0);
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
      
      public int setAppPriceJP(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(94, localParcel1, localParcel2, 0);
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
      
      public int setAppReleaseDate(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(92, localParcel1, localParcel2, 0);
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
      
      public int setAppRestriction(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(102, localParcel1, localParcel2, 0);
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
      
      public int setAppSoundType(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(109, localParcel1, localParcel2, 0);
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
      
      public int setAppStoppingDispCtrl(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(105, localParcel1, localParcel2, 0);
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
      
      public int setAppStoppingOpeCtrl(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(106, localParcel1, localParcel2, 0);
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
      
      public int setAppSupport(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(110, localParcel1, localParcel2, 0);
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
      
      public int setAppURLName(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(99, localParcel1, localParcel2, 0);
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
      
      public int setAppVer(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(93, localParcel1, localParcel2, 0);
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
      
      public void setCalibTouch(float paramFloat1, float paramFloat2, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeFloat(paramFloat1);
          localParcel1.writeFloat(paramFloat2);
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(119, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int setInitPolicyInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(36, localParcel1, localParcel2, 0);
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
      
      public int setLaunchAppName(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(116, localParcel1, localParcel2, 0);
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
      
      public int setPolicyDate(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(47, localParcel1, localParcel2, 0);
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
      
      public int setPolicyID(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(46, localParcel1, localParcel2, 0);
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
      
      public int setProductName(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(45, localParcel1, localParcel2, 0);
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
      
      public int setRegionInfo(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(43, localParcel1, localParcel2, 0);
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
      
      public int setRule(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(48, localParcel1, localParcel2, 0);
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
      
      public void setSPTouchMarkPosi(float paramFloat1, float paramFloat2, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeFloat(paramFloat1);
          localParcel1.writeFloat(paramFloat2);
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(118, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int setStatePolicyInfo(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(49, localParcel1, localParcel2, 0);
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
      
      public int setUnitType(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(44, localParcel1, localParcel2, 0);
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
      
      public void start()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void stop()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.clarion.android.appmgr.service.IClarionService");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public void unregisterCallback(IClarionCallback paramIClarionCallback, String paramString)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_1
        //   16: ifnull +53 -> 69
        //   19: aload_1
        //   20: invokeinterface 155 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 158	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_3
        //   34: aload_2
        //   35: invokevirtual 161	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   38: aload_0
        //   39: getfield 18	com/clarion/android/appmgr/service/IClarionService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   42: bipush 124
        //   44: aload_3
        //   45: aload 4
        //   47: iconst_0
        //   48: invokeinterface 50 5 0
        //   53: pop
        //   54: aload 4
        //   56: invokevirtual 53	android/os/Parcel:readException	()V
        //   59: aload 4
        //   61: invokevirtual 56	android/os/Parcel:recycle	()V
        //   64: aload_3
        //   65: invokevirtual 56	android/os/Parcel:recycle	()V
        //   68: return
        //   69: aconst_null
        //   70: astore 6
        //   72: goto -45 -> 27
        //   75: astore 5
        //   77: aload 4
        //   79: invokevirtual 56	android/os/Parcel:recycle	()V
        //   82: aload_3
        //   83: invokevirtual 56	android/os/Parcel:recycle	()V
        //   86: aload 5
        //   88: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	89	0	this	Proxy
        //   0	89	1	paramIClarionCallback	IClarionCallback
        //   0	89	2	paramString	String
        //   3	80	3	localParcel1	Parcel
        //   7	71	4	localParcel2	Parcel
        //   75	12	5	localObject	Object
        //   25	46	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	59	75	finally
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.clarion.android.appmgr.service.IClarionService
 * JD-Core Version:    0.7.0.1
 */