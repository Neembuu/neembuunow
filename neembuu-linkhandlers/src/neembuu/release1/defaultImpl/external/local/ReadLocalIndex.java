/*
 * Copyright (C) 2014 Shashank Tulsyan
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

package neembuu.release1.defaultImpl.external.local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntry;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntryImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Shashank Tulsyan
 */
public class ReadLocalIndex {    
    private static JSONObject getContents(Path pth)throws IOException,JSONException{
        return new JSONObject(new String(Files.readAllBytes(pth)));
    }
    
    private static LocalExternalLinkHandlers make(JSONObject jsono)throws JSONException{
        LocalExternalLinkHandlers lelh = new LocalExternalLinkHandlers();
        //private ExternalLinkHandlerEntry[] handlers;
        //private long creationTime;
        //private String createdBy;
        String message = "Corrupt json file, lack field";
        String append = null;
        if(!jsono.has("handlers")){append = " handlers";}
        if(!jsono.has("creationTime")){append = " creationTime";}
        if(!jsono.has("createdBy")){append = " createdBy";}
        if(!jsono.has("hashingAlgorithm")){append = " hashingAlgorithm";}
        if(append!=null)
            throw new IllegalStateException(message + append);
        
        JSONArray array = jsono.getJSONArray("handlers");
        ExternalLinkHandlerEntry[]handlers=new ExternalLinkHandlerEntry[array.length()];
        for (int i = 0; i < array.length(); i++) {
            handlers[i] = makeELHE(array.getJSONObject(i));
        }lelh.setHandlers(handlers);
        
        lelh.setCreationTime(jsono.getLong("creationTime"));
        lelh.setCreatedBy(jsono.getString("createdBy"));
        lelh.setHashingAlgorithm(jsono.getString("hashingAlgorithm"));
        return lelh;
    }
    
    private static ExternalLinkHandlerEntry makeELHE(JSONObject jsono)throws JSONException{
        //String getCheckingRegex();
        //String getCheckingJavaCode();
        //String getClassName();
        //String[]getResourcesHash();
        //String[]getDependenciesURL();
        ExternalLinkHandlerEntryImpl elhei = new ExternalLinkHandlerEntryImpl();
        elhei.setCheckingRegex(jsono.getString("checkingRegex"));
        elhei.setCheckingJavaCode(jsono.getString("checkingJavaCode"));
        elhei.setClassName(jsono.getString("className"));
        
        JSONArray resourcesHashJsonArray = jsono.getJSONArray("resourcesHash");
        String[]resourcesHash=new String[resourcesHashJsonArray.length()];
        for (int i = 0; i < resourcesHashJsonArray.length(); i++) {
            resourcesHash[i] = resourcesHashJsonArray.getString(i);
        }elhei.setResourcesHash(resourcesHash);
        
        JSONArray dependenciesURLArray = jsono.getJSONArray("dependenciesURL");
        String[]dependenciesURL=new String[dependenciesURLArray.length()];
        for (int i = 0; i < dependenciesURLArray.length(); i++) {
            dependenciesURL[i] = dependenciesURLArray.getString(i);
        }elhei.setDependenciesURL(dependenciesURL);
        
        return elhei;
    }
}
