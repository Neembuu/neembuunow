/*
 * Copyright (C) 2014 davidepastore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package neembuu.release1.externalImpl.linkhandler;

import davidepastore.StringUtils;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.defaultImpl.external.ELHProvider;
import neembuu.release1.defaultImpl.file.BasicOnlineFile;
import neembuu.release1.defaultImpl.file.BasicPropertyProvider;
import neembuu.release1.defaultImpl.linkhandler.BasicLinkHandler;
import neembuu.release1.defaultImpl.linkhandler.Utils;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Other method.
 * @author davidepastore
 */
@ELHProvider(checkingRegex = YoutubeLinkHandlerProvider.REG_EXP)
public class OtherYoutubeLinkHandlerProvider implements LinkHandlerProvider {

    private static final Logger LOGGER = LoggerUtil.getLogger(YoutubeLinkHandlerProvider.class.getName()); // all logs go into an html file

    @Override
    public TrialLinkHandler tryHandling(final String url) {
        return new YoutubeLinkHandlerProvider.YT_TLH(url);
    }

    @Override
    public LinkHandler getLinkHandler(TrialLinkHandler tlh) throws Exception {
        if (!(tlh instanceof YoutubeLinkHandlerProvider.YT_TLH) || !tlh.canHandle()) {
            return null;
        }
        BasicLinkHandler.Builder linkHandlerBuilder = otherExtraction(tlh);
        return linkHandlerBuilder.build();
    }

    private BasicLinkHandler.Builder otherExtraction(TrialLinkHandler tlh) throws Exception {
        String url = tlh.getReferenceLinkString();
        BasicLinkHandler.Builder linkHandlerBuilder = BasicLinkHandler.Builder.create();
        

        try {
            DefaultHttpClient httpClient = NHttpClient.getNewInstance();
            HttpPost httpPost = new HttpPost("http://www.clipconverter.cc/check.php");

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("mediaurl", url));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            final String responseString = EntityUtils.toString(httpResponse.getEntity());

            JSONObject jSonObject = new JSONObject(responseString);
            //LOGGER.log(Level.INFO,jSonObject);

            JSONArray jSonArray = jSonObject.getJSONArray("url");

            LOGGER.log(Level.INFO, "urls: " + jSonArray);

            //Set the group name as the name of the video
            String nameOfVideo = jSonObject.getString("filename");
            //normalize name of video

            //nameOfVideo = jpfm.util.UniversallyValidFileName.makeUniversallyValidFileName(nameOfVideo);
            linkHandlerBuilder.setGroupName(nameOfVideo);

            // Davide you cannot create a this.fileName field
            // this.filename = jSonObject.getString("filename") + ".mp4";
            // The same YoutubeLinkHandler object will be used for hanlding
            // all Youtube links. We "do" it in different threads in 
            // neembuu.release1.ui.actions.LinkActionsImpl line 128
            // void reAddAction(boolean anotherThread) 
            long c_duration = -1;

            for (int i = 0; i < jSonArray.length(); i++) {
                jSonObject = (JSONObject) jSonArray.get(i);
                String fileName = jSonObject.getString("text");
                LOGGER.log(Level.INFO, "Filename: {0}", fileName);

                final String extension = jSonObject.getString("filetype").toLowerCase();
                fileName = StringUtils.stringBetweenTwoStrings(fileName, ">", "<");
                fileName = fileName + "." + extension;

                String singleUrl = jSonObject.getString("url");
                //singleUrl = singleUrl.substring(0, singleUrl.indexOf("#"));
                //did some changes, but this doesn't help :(
                LOGGER.log(Level.INFO, "Before normalization URL: {0}", singleUrl);
                long length = tryFindingSize(singleUrl);
                singleUrl = Utils.normalize(singleUrl);
                LOGGER.log(Level.INFO, "Normalized URL: {0}", singleUrl);

                if (length == 0) {
                    length = NHttpClientUtils.calculateLength(singleUrl, httpClient);
                }
                //LOGGER.log(Level.INFO,"Length: " + length);

                if (length <= 0) {
                    continue; /*skip this url*/ }

                BasicOnlineFile.Builder fileBuilder = linkHandlerBuilder
                        .createFile();

                try { // finding video/audio length
                    String dur = StringUtils.stringBetweenTwoStrings(singleUrl, "dur=", "&");
                    long duration = (int) (Double.parseDouble(dur) * 1000);
                    if (c_duration < 0) {
                        c_duration = duration;
                    }
                    fileBuilder.putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, duration);
                    LOGGER.log(Level.INFO, "dur={0}", dur);
                } catch (NumberFormatException a) {
                    // ignore
                }

                try { // finding the quality short name
                    String type = fileName.substring(fileName.indexOf("(") + 1);
                    type = type.substring(0, type.indexOf(")"));
                    fileBuilder.putStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION, type);
                    if (type.contains("480") || type.contains("1080")) {
                        fileBuilder.putBooleanPropertyValue(PropertyProvider.BooleanProperty.UNSTABLE_VARIANT, true);
                    }
                    LOGGER.log(Level.INFO, "type={0}", type);
                } catch (Exception a) {
                    a.printStackTrace();
                }

                fileName = nameOfVideo + " " + fileName;

                fileBuilder.setName(fileName)
                        .setUrl(singleUrl)
                        .setSize(length).next();
            }

            for (OnlineFile of : linkHandlerBuilder.getFiles()) {
                long dur = of.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS);
                if (dur < 0 && c_duration > 0
                        && of.getPropertyProvider() instanceof BasicPropertyProvider) {
                    ((BasicPropertyProvider) of.getPropertyProvider())
                            .putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, c_duration);
                }
            }
        } catch (Exception ex) {
//            int retryLimit = ((YoutubeLinkHandlerProvider.YT_TLH) tlh).retryLimit;
            ex.printStackTrace();
//            LOGGER.log(Level.INFO, "retry no. = " + retryCount);
//            if (retryCount > retryLimit) {
//                throw ex;
//            }
//            return clipConverterExtraction(tlh, retryCount + 1);
        }

        return linkHandlerBuilder;
    }

    private long tryFindingSize(String rawURL) {
        try {
            String s = "size=";
            String sz = rawURL.substring(rawURL.indexOf(s) + s.length());
            if (sz.contains("#")) {
                sz = sz.substring(0, sz.indexOf("#"));
            }
            long size = Long.parseLong(sz);
            return size;
        } catch (Exception a) {
            /*size not found ignore*/
            a.printStackTrace();
        }
        return 0;
    }

    /**
     * Neembuu get Video Url.
     *
     * @param url The url of the video.
     * @return
     */
    public static String nGetVideoUrl(String url) throws Exception {
        String str1 = null;
        DefaultHttpClient httpClient = NHttpClient.getNewInstance();
        String str2 = NHttpClientUtils.getData(String.format(url), httpClient);
        return "";
    }

    /*
    public static String getVideoUrl2(String url, String paramString2, String paramString3, Constants.Quality paramQuality)
            throws IOException, JSONException, Exception {
        String str1 = null;
        DefaultHttpClient httpClient = NHttpClient.getNewInstance();
        String str2 = NHttpClientUtils.getData(String.format(url), httpClient);
//        localHttpClient.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0");

        HashMap localHashMap;
        String[] arrayOfString;
//        if (localHttpClient.request()) {
//            String str2 = localHttpClient.getResponseBody();
        localHashMap = new HashMap();
        Matcher localMatcher = Pattern.compile("\"url_encoded_fmt_stream_map\": ?(\".*?\")").matcher(str2);
        if (localMatcher.find()) {
            arrayOfString = new JSONArray("[" + localMatcher.group(1) + "]").getString(0).split(",");
        }

        for (int i = 0;; i++) {
            int[] arrayOfInt;
            if (i >= arrayOfString.length) {
                arrayOfInt = getQuality(paramQuality);
            }
            for (int k = 0;; k++) {
                if (k >= arrayOfInt.length) {
                }
                for (;;) {
                    //localHttpClient.shutdown();
                    return str1;
                    try {
                        Map localMap = getQueryMap(arrayOfString[i]);
                        String str3 = URLDecoder.decode((String) localMap.get("url"), "UTF-8");
                        String str4 = (String) localMap.get("sig");
                        String str5 = (String) localMap.get("s");
                        int j = Integer.parseInt((String) localMap.get("itag"));
                        Object localObject;
                        if (str4 != null) {
                            localObject = str3 + "&signature=" + str4;
                        }
                        for (;;) {
                            localHashMap.put(Integer.valueOf(j), localObject);
                            break;
                            if (str5 != null) {
                                String str6 = getSigForPC(str5);
                                String str7 = str3 + "&signature=" + str6;
                                localObject = str7;
                            } else {
                                localObject = str3;
                            }
                        }
                        if (!localHashMap.containsKey(Integer.valueOf(arrayOfInt[k]))) {
                            break;
                        }
                    } catch (Exception localException) {
                        localException.printStackTrace();
                    }
                    str1 = (String) localHashMap.get(Integer.valueOf(arrayOfInt[k]));
                }
            }
        }
    }

    private static String getSig(String paramString) {
        for (;;) {
            try {
                String str1 = (String) getSigPerms().get(String.valueOf(paramString.length()));
                try {
                    JSONArray localJSONArray1 = new JSONArray(str1);
                    Object localObject = "";
                    int i = 0;
                    int j = localJSONArray1.length();
                    if (i >= j) {
                        return (String) localObject;
                    }
                    try {
                        JSONArray localJSONArray2 = localJSONArray1.getJSONArray(i);
                        String str3 = localObject + paramString.substring(localJSONArray2.getInt(0), localJSONArray2.getInt(1));
                        localObject = str3;
                        i++;
                    } catch (JSONException localJSONException2) {
                        if (!localJSONArray1.getString(i).equals("REV")) {
                            continue;
                        }
                        String str2 = getReverse(paramString);
                        paramString = str2;
                        continue;
                    }
                    continue;
//                    localObject = null;
                } catch (JSONException localJSONException1) {
                    localJSONException1.printStackTrace();
                }
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
    }

    private static String getSigForPC(String paramString) {
        return getSig(paramString);
    }

    public static Map<String, String> getQueryMap(String paramString) {
        String[] arrayOfString1 = paramString.split("&");
        HashMap localHashMap = new HashMap();
        int i = arrayOfString1.length;
        for (int j = 0;; j++) {
            if (j >= i) {
                return localHashMap;
            }
            String[] arrayOfString2 = arrayOfString1[j].split("=");
            localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
        }
    }

    private static String getReverse(String paramString) {
        return new StringBuffer(paramString).reverse().toString();
    }

    private static int[] getQuality(Constants.Quality paramQuality) {
        int[] arrayOfInt1 = new int[3];
        arrayOfInt1[0] = 18;
        arrayOfInt1[1] = 36;
        arrayOfInt1[2] = 22;
        int[] arrayOfInt2 = new int[3];
        arrayOfInt2[0] = 36;
        arrayOfInt2[1] = 18;
        arrayOfInt2[2] = 22;
        int[] arrayOfInt3 = new int[3];
        arrayOfInt3[0] = 22;
        arrayOfInt3[1] = 18;
        arrayOfInt3[2] = 36;
        switch (paramQuality) {
        }
        for (arrayOfInt3 = arrayOfInt1;; arrayOfInt3 = arrayOfInt2) {
            return arrayOfInt3;
        }
    }

    private static HashMap<String, String> getSigPerms() throws Exception {
        HashMap localHashMap = new HashMap();
        long l1 = 0L;
        long l2 = 60000L;
        String str1 = null;
        String str3;
        if ((str1 == null) || (System.currentTimeMillis() - l1 > l2)) {
            str3 = NHttpClientUtils.getData("http://pvstar.dooga.org/api2/youtube_sig_perms", NHttpClient.getNewInstance());
        }
        try {
            JSONObject localJSONObject2 = new JSONObject(str3);
            long l3 = 1000L * localJSONObject2.getLong("ttl");
            if (l3 > 3600000L) {
                l3 = 3600000L;
            }
            str1 = localJSONObject2.getJSONObject("sig_perms").toString();
            SharedPreferences.Editor localEditor = localSharedPreferences.edit();
            localEditor.putLong("SIG_PERM_TIMESTAMP", System.currentTimeMillis());
            localEditor.putLong("SIG_PERM_TTL", l3);
            localEditor.putString("SIG_PERM_PARAMS", str1);
            localEditor.commit();
            try {
                localJSONObject1 = new JSONObject(str1);
                localIterator = localJSONObject1.keys();
                boolean bool = localIterator.hasNext();
                if (bool) {
                    break label236;
                }
            } catch (JSONException localJSONException1) {
                for (;;) {
                    JSONObject localJSONObject1;
                    Iterator localIterator;
                    String str2;
                    localJSONException1.printStackTrace();
                }
            }
            return localHashMap;
        } catch (JSONException localJSONException2) {
            for (;;) {
                localJSONException2.printStackTrace();
                continue;
                label236:
                str2 = (String) localIterator.next();
                localHashMap.put(str2, localJSONObject1.getJSONArray(str2).toString());
            }
        }
    }
    */
}
