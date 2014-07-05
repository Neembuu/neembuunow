package twitter4j.auth;

import twitter4j.conf.Configuration;

public final class AuthorizationFactory
{
  public static Authorization getInstance(Configuration paramConfiguration)
  {
    Object localObject = null;
    String str1 = paramConfiguration.getOAuthConsumerKey();
    String str2 = paramConfiguration.getOAuthConsumerSecret();
    if ((str1 != null) && (str2 != null)) {
      if (paramConfiguration.isApplicationOnlyAuthEnabled())
      {
        OAuth2Authorization localOAuth2Authorization = new OAuth2Authorization(paramConfiguration);
        String str5 = paramConfiguration.getOAuth2TokenType();
        String str6 = paramConfiguration.getOAuth2AccessToken();
        if ((str5 != null) && (str6 != null)) {
          localOAuth2Authorization.setOAuth2Token(new OAuth2Token(str5, str6));
        }
        localObject = localOAuth2Authorization;
      }
    }
    for (;;)
    {
      if (localObject == null) {
        localObject = NullAuthorization.getInstance();
      }
      return localObject;
      OAuthAuthorization localOAuthAuthorization = new OAuthAuthorization(paramConfiguration);
      String str7 = paramConfiguration.getOAuthAccessToken();
      String str8 = paramConfiguration.getOAuthAccessTokenSecret();
      if ((str7 != null) && (str8 != null)) {
        localOAuthAuthorization.setOAuthAccessToken(new AccessToken(str7, str8));
      }
      localObject = localOAuthAuthorization;
      continue;
      String str3 = paramConfiguration.getUser();
      String str4 = paramConfiguration.getPassword();
      if ((str3 != null) && (str4 != null)) {
        localObject = new BasicAuthorization(str3, str4);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.AuthorizationFactory
 * JD-Core Version:    0.7.0.1
 */