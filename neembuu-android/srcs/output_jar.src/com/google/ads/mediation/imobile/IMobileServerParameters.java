package com.google.ads.mediation.imobile;

import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.MediationServerParameters.Parameter;

public final class IMobileServerParameters
  extends MediationServerParameters
{
  @MediationServerParameters.Parameter(name="mid")
  public String mediaId;
  @MediationServerParameters.Parameter(name="publisherId")
  public String publisherId;
  @MediationServerParameters.Parameter(name="asid")
  public String spotId;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.mediation.imobile.IMobileServerParameters
 * JD-Core Version:    0.7.0.1
 */