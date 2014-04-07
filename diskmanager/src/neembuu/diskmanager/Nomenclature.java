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

package neembuu.diskmanager;

/**
 *
 * @author Shashank Tulsyan
 */
public interface Nomenclature {
    String sessionFileName(SessionName si);
    SessionName getSessionName(String sessionFileName);
    
    String sessionMetaDataFileName();
    RegionStorage getRegionStorage();
    FileStorage getFileStorage();
    LogStorage getLogStorage();
    
    interface SessionName {
        String getMetaHash(); String getType(); String getVariant();
    }
    
    public static final class SessionNameMutable implements SessionName{
        private String metaHash=null,type=null,variant=null;
        public SessionNameMutable(){}
        @Override public String getMetaHash() { return metaHash; }
        @Override public String getType() { return type; }
        @Override public String getVariant() { return variant; }
        public SessionNameMutable setMetaHash(String metaHash) {this.metaHash = metaHash;return this;}
        public SessionNameMutable setType(String type) {this.type = type;return this;}
        public SessionNameMutable setVariant(String variant) {this.variant = variant;return this;}
    }
    
    public static final class SessionNameImmutable implements SessionName{
        private final String metaHash,type,variant;
        public static SessionName make(String name, String type){
            return new SessionNameImmutable(name, type, null);}
        public static SessionName make(String name, String type,String variant){
            return new SessionNameImmutable(name, type,variant);}
        public SessionNameImmutable(String metaHash, String type, String variant) {
            this.metaHash = metaHash; this.type = type; this.variant = variant;}
        @Override public String getMetaHash() { return metaHash; }
        @Override public String getType() { return type; }
        @Override public String getVariant() { return variant; }
    }

    
    interface FileStorage {
        String makeName(String referenceConstant);//fileName + n.fileStorage_postFix()
        //Long.parseLong(f1name,16)
        
        //String fileStorage_postFix(); //"_neembuu_download_data"
//            if(!fs[i].getName().endsWith(".partial")){
//                // metadata file ignore
//                /*try{
//                    java.nio.file.Files.delete(fs[i].toPath());
//                }catch(Exception a){
//                    a.printStackTrace(System.err);
//                }*/
//            }
//            else if(fs[i].getName().indexOf("_0x")<0){}
//            else {files.add(fs[i]);}
    }
    
    interface RegionStorage {
        long INVALID_REGION = -1;
        //f1name = f1name.substring(f1name.indexOf("_0x")+3,f1name.lastIndexOf("."));
        long getStartingOffset(String fileName);
        
        // Math.random()+"("+startingOffset+")_0x"+Long.toHexString(startingOffset)+".partial"
        /**
         * 
         * @param offset
         * @param estimatedMaxFileSize of the entire file, and NOT of this region
         * @return 
         */
        String makeName(long offset,long estimatedMaxFileSize);
        
        //String preFix();//Math.random()
        //String offsetIndicator(long offset, long estimatedMaxFileSize);
        //String postFix();//".partial";
    }
    
    //store_path + java.io.File.separator + name+"_log.html",
    interface LogStorage {
        
        //name = "DownloadThread("+startingOffset+")"
        //name = "ReadHandlerThread("+startingOffset+")"
        String makeForRegion(String logName, long offset, long max);
        
        //name = "ReadQueueManagerThread" 
        String make(String logName);
    }
    
}
