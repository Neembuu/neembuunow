package neembuu.diskmanager.impl;

import neembuu.diskmanager.Nomenclature;

/**
 *
 * @author Shashank Tulsyan
 */
public class DefaultNomenclature implements Nomenclature{

    @Override public String sessionMetaDataFileName() { return "session.metadata";}

    @Override public String sessionFileName(SessionName si) {
        if(si.getVariant()!=null){
            return si.getMetaHash()+"_"+si.getVariant()+ "."+si.getType();
        }else {
            return si.getMetaHash()+"."+si.getType();
        }
    }

    @Override public SessionName getSessionName(String sfn) {
        String name="", type="", variant = null;
        try{ 
            name = sfn.substring(0,sfn.lastIndexOf("."));
            if(name.contains("_")){
                variant = name.substring(name.indexOf("_")+1);
                name = name.substring(0,name.indexOf("_"));
            }
        }catch(Exception a){}
        try{ 
            type = sfn.substring(sfn.lastIndexOf(".")+1);
        }catch(Exception a){}
        return SessionNameImmutable.make(name, type,variant);
    }
    
    @Override public RegionStorage getRegionStorage() { return regionStorageImpl; }
    @Override public FileStorage getFileStorage() { return fileStorageImpl; }
    @Override public LogStorage getLogStorage() { return logStorageImpl; }
    
    private final RegionStorageImpl regionStorageImpl = new RegionStorageImpl();
    private final FileStorageImpl fileStorageImpl = new FileStorageImpl();
    private final LogStorageImpl logStorageImpl = new LogStorageImpl();
    
    
    private static class RegionStorageImpl implements RegionStorage{
        @Override public String makeName(long offset, long max) {
            return preFix() + offsetIndicator(offset, max)
                    + postFix();
        }
        
        private String preFix() { return ""; }
        
        private static String offsetIndicator(long offset, long max) {
            int d10 = numberOfDigits(max, 10);
            int d16 = numberOfDigits(max, 16);
            String n10 = longToString(d10,Long.toString(offset));
            String n16 = longToString(d16,Long.toString(offset,16));
            return "("+n10+")_0x"+n16.toUpperCase();
        }
        
        private String postFix() { return ".neembuu_file_region"; }
        
        public static String longToString(int digits,String output) {
            while (output.length() < digits) output = "0" + output;
            return output;
        }
        
        private static int numberOfDigits(long temp,final int radix){
            int decimalPlaces = 0;
            while(temp >= 1){
                temp/=radix;
                decimalPlaces++;
            }return decimalPlaces;
        }
        
        @Override public long getStartingOffset(String fn) {
            long offset;
            try{
                String o = fn.substring(fn.indexOf("_0x")+3,fn.lastIndexOf("."));
                long hexOffset = Long.parseLong(o, 16);
                o = fn.substring(fn.indexOf("(")+1,fn.lastIndexOf(")"));
                offset = Long.parseLong(o);

                if(offset!=hexOffset){ return INVALID_REGION; }
            }catch(Exception ignore){ return INVALID_REGION; }
            return offset;
        }
    }
    
    
    private static class LogStorageImpl implements LogStorage {
        
        @Override public String makeForRegion(String logName, long offset, long max) {
            return RegionStorageImpl.offsetIndicator(offset, max)+"."+logName+".log.html";
        }
        
        @Override public String make(String logName) {
            return logName+".log.html";
        }
    }
    
    private static class FileStorageImpl implements FileStorage {
        @Override public String makeName(String referenceConstant) {
            return referenceConstant+".neembuu_file";
        }
    }
    
}
