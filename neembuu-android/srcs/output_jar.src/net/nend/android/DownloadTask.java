package net.nend.android;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import java.io.IOException;
import java.lang.ref.WeakReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

final class DownloadTask<T>
  extends AsyncTask<Void, Void, T>
{
  private final WeakReference<Downloadable<T>> mReference;
  
  DownloadTask(Downloadable<T> paramDownloadable)
  {
    this.mReference = new WeakReference(paramDownloadable);
  }
  
  protected T doInBackground(Void... paramVarArgs)
  {
    Object localObject1 = null;
    Thread.currentThread().setPriority(10);
    if (isCancelled()) {}
    for (;;)
    {
      return localObject1;
      Downloadable localDownloadable = (Downloadable)this.mReference.get();
      if ((localDownloadable != null) && (localDownloadable.getRequestUrl() != null) && (localDownloadable.getRequestUrl().length() > 0))
      {
        final String str = localDownloadable.getRequestUrl();
        NendLog.v("Download from " + str);
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        try
        {
          HttpParams localHttpParams = localDefaultHttpClient.getParams();
          HttpConnectionParams.setConnectionTimeout(localHttpParams, 10000);
          HttpConnectionParams.setSoTimeout(localHttpParams, 10000);
          NendLog.d("start request!");
          Object localObject3 = localDefaultHttpClient.execute(new HttpGet(str), new ResponseHandler()
          {
            public T handleResponse(HttpResponse paramAnonymousHttpResponse)
              throws ClientProtocolException, IOException
            {
              NendLog.d("get response!");
              DownloadTask.Downloadable localDownloadable;
              if ((!DownloadTask.this.isCancelled()) && (paramAnonymousHttpResponse.getStatusLine().getStatusCode() == 200))
              {
                localDownloadable = (DownloadTask.Downloadable)DownloadTask.this.mReference.get();
                if (localDownloadable == null) {}
              }
              for (Object localObject = localDownloadable.makeResponse(paramAnonymousHttpResponse.getEntity());; localObject = null)
              {
                return localObject;
                if (!DownloadTask.this.isCancelled()) {
                  NendLog.w(NendStatus.ERR_HTTP_REQUEST, "http status : " + paramAnonymousHttpResponse.getStatusLine().getStatusCode());
                }
              }
            }
          });
          localObject1 = localObject3;
          localDefaultHttpClient.getConnectionManager().shutdown();
          continue;
        }
        catch (ClientProtocolException localClientProtocolException)
        {
          NendLog.w(NendStatus.ERR_HTTP_REQUEST, localClientProtocolException);
          localDefaultHttpClient.getConnectionManager().shutdown();
          continue;
        }
        catch (IOException localIOException)
        {
          NendLog.w(NendStatus.ERR_HTTP_REQUEST, localIOException);
          localDefaultHttpClient.getConnectionManager().shutdown();
          continue;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          NendLog.w(NendStatus.ERR_HTTP_REQUEST, localIllegalStateException);
          localDefaultHttpClient.getConnectionManager().shutdown();
          continue;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          NendLog.w(NendStatus.ERR_HTTP_REQUEST, localIllegalArgumentException);
          localDefaultHttpClient.getConnectionManager().shutdown();
          continue;
        }
        finally
        {
          localDefaultHttpClient.getConnectionManager().shutdown();
        }
      }
      else
      {
        NendLog.w(NendStatus.ERR_INVALID_URL);
      }
    }
  }
  
  boolean isFinished()
  {
    if (getStatus() == AsyncTask.Status.FINISHED) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  protected void onPostExecute(T paramT)
  {
    Downloadable localDownloadable = (Downloadable)this.mReference.get();
    if ((!isCancelled()) && (localDownloadable != null)) {
      localDownloadable.onDownload(paramT);
    }
  }
  
  static abstract interface Downloadable<T>
  {
    public abstract String getRequestUrl();
    
    public abstract T makeResponse(HttpEntity paramHttpEntity);
    
    public abstract void onDownload(T paramT);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.DownloadTask
 * JD-Core Version:    0.7.0.1
 */