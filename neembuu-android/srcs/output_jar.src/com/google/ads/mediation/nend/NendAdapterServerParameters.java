package com.google.ads.mediation.nend;

import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.MediationServerParameters.Parameter;

public final class NendAdapterServerParameters
  extends MediationServerParameters
{
  @MediationServerParameters.Parameter(name="apiKey")
  public String apiKey;
  @MediationServerParameters.Parameter(name="spotId")
  public String spotIdStr;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.mediation.nend.NendAdapterServerParameters
 * JD-Core Version:    0.7.0.1
 */