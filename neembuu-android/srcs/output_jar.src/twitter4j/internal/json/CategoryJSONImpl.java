package twitter4j.internal.json;

import java.io.Serializable;
import twitter4j.Category;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class CategoryJSONImpl
  implements Category, Serializable
{
  private static final long serialVersionUID = -6703617743623288566L;
  private String name;
  private int size;
  private String slug;
  
  CategoryJSONImpl(JSONObject paramJSONObject)
    throws JSONException
  {
    init(paramJSONObject);
  }
  
  static ResponseList<Category> createCategoriesList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    return createCategoriesList(paramHttpResponse.asJSONArray(), paramHttpResponse, paramConfiguration);
  }
  
  static ResponseList<Category> createCategoriesList(JSONArray paramJSONArray, HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    for (;;)
    {
      int i;
      try
      {
        if (paramConfiguration.isJSONStoreEnabled()) {
          DataObjectFactoryUtil.clearThreadLocalMap();
        }
        ResponseListImpl localResponseListImpl = new ResponseListImpl(paramJSONArray.length(), paramHttpResponse);
        i = 0;
        if (i < paramJSONArray.length())
        {
          JSONObject localJSONObject = paramJSONArray.getJSONObject(i);
          CategoryJSONImpl localCategoryJSONImpl = new CategoryJSONImpl(localJSONObject);
          localResponseListImpl.add(localCategoryJSONImpl);
          if (paramConfiguration.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(localCategoryJSONImpl, localJSONObject);
          }
        }
        else
        {
          if (paramConfiguration.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(localResponseListImpl, paramJSONArray);
          }
          return localResponseListImpl;
        }
      }
      catch (JSONException localJSONException)
      {
        throw new TwitterException(localJSONException);
      }
      i++;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    CategoryJSONImpl localCategoryJSONImpl;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject == null) || (getClass() != paramObject.getClass()))
        {
          bool = false;
        }
        else
        {
          localCategoryJSONImpl = (CategoryJSONImpl)paramObject;
          if (this.size == localCategoryJSONImpl.size) {
            break;
          }
          bool = false;
        }
      }
      if (this.name != null)
      {
        if (this.name.equals(localCategoryJSONImpl.name)) {}
      }
      else {
        while (localCategoryJSONImpl.name != null)
        {
          bool = false;
          break;
        }
      }
      if (this.slug == null) {
        break;
      }
    } while (this.slug.equals(localCategoryJSONImpl.slug));
    for (;;)
    {
      bool = false;
      break;
      if (localCategoryJSONImpl.slug == null) {
        break;
      }
    }
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public String getSlug()
  {
    return this.slug;
  }
  
  public int hashCode()
  {
    int i = 0;
    if (this.name != null) {}
    for (int j = this.name.hashCode();; j = 0)
    {
      int k = j * 31;
      if (this.slug != null) {
        i = this.slug.hashCode();
      }
      return 31 * (k + i) + this.size;
    }
  }
  
  void init(JSONObject paramJSONObject)
    throws JSONException
  {
    this.name = paramJSONObject.getString("name");
    this.slug = paramJSONObject.getString("slug");
    this.size = z_T4JInternalParseUtil.getInt("size", paramJSONObject);
  }
  
  public String toString()
  {
    return "CategoryJSONImpl{name='" + this.name + '\'' + ", slug='" + this.slug + '\'' + ", size=" + this.size + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.CategoryJSONImpl
 * JD-Core Version:    0.7.0.1
 */