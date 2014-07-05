package com.google.ads.mediation;

import android.content.Context;
import android.location.Location;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.Gender;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class MediationAdRequest
{
  private AdRequest a;
  private boolean b;
  private boolean c;
  
  public MediationAdRequest(AdRequest paramAdRequest, Context paramContext, boolean paramBoolean)
  {
    this.a = paramAdRequest;
    this.c = paramBoolean;
    if (paramContext == null) {}
    for (this.b = true;; this.b = paramAdRequest.isTestDevice(paramContext)) {
      return;
    }
  }
  
  public Integer getAgeInYears()
  {
    if (this.a.getBirthday() != null)
    {
      Calendar localCalendar1 = Calendar.getInstance();
      Calendar localCalendar2 = Calendar.getInstance();
      localCalendar1.setTime(this.a.getBirthday());
      localInteger = Integer.valueOf(localCalendar2.get(1) - localCalendar1.get(1));
      if (localCalendar2.get(6) >= localCalendar1.get(6)) {}
    }
    for (Integer localInteger = Integer.valueOf(-1 + localInteger.intValue());; localInteger = null) {
      return localInteger;
    }
  }
  
  public Date getBirthday()
  {
    return this.a.getBirthday();
  }
  
  public AdRequest.Gender getGender()
  {
    return this.a.getGender();
  }
  
  public Set<String> getKeywords()
  {
    if (this.a.getKeywords() == null) {}
    for (Object localObject = null;; localObject = Collections.unmodifiableSet(this.a.getKeywords())) {
      return localObject;
    }
  }
  
  public Location getLocation()
  {
    if ((this.a.getLocation() == null) || (!this.c)) {}
    for (Location localLocation = null;; localLocation = new Location(this.a.getLocation())) {
      return localLocation;
    }
  }
  
  public boolean isTesting()
  {
    return this.b;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.mediation.MediationAdRequest
 * JD-Core Version:    0.7.0.1
 */