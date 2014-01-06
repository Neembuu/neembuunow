package neembuu.util.logging;

/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */


import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import neembuu.util.logging.LoggerUtil;
/**
 *
 * @author Shashank Tulsyan
 */


final class HtmlFormatter extends Formatter{
    
    private static final String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    
    private int cnt = 0;
    @Override
    public String format(LogRecord record) {
        if(record.getThrown() instanceof FakeThrowable){
            System.out.println(record.getMessage());
            return record.getMessage();
        }
        
        Date dat = new Date();
        dat.setTime(record.getMillis());
        
        String formattedText = getTime(dat)+tab+
                             level(record) +tab+
                            source(record) +tab;
        formattedText = formattedText+
                             message(record,formattedText.length()-3*tab.length()) +tab+
                             throwable(record) +"<br/>";
        
        if(cnt%2==0){
            formattedText = "<span style=\"background-color: F2F2F2;\">"+formattedText+"</span>";
        }
        cnt++;
        return formattedText;
    }
    
    private String level(LogRecord record){
        return "<FONT COLOR=\""
                + c(Color.ORANGE)
                + "\">"
                + record.getLevel().getLocalizedName()
                + "</FONT>";
    }

    @Override
    public String getHead(Handler h) {
        return "<html>\n"
                + "<script type=\"text/javascript\">\n"+
                "function toggle(id) {\n"+
                  "var e = document.getElementById(id);\n"+
                  "if (e.style.display == \'\')\n"+
                    "e.style.display = \'none\';\n"+
                  "else\n"+
                    "e.style.display = \'\';\n}\n"+
            "</script>\n"
                + "<body style=\"width:200%;\">";
    }
    
    private String message(LogRecord record, int length){
        String message = formatMessage(record);
        if(message==null)return "null";
        String space = "";
        
        for (int i = 0; i < length; i++) {
            space = space+ "&nbsp;";
        }
        message = message.replace("\n", "<br/>"+space);
        return "<b>"+message+"</b>";
    }
        
    private String source(LogRecord record){
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
               source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }
        return "<U><FONT COLOR=\""
                + c(Color.blue)
                + "\">["
                + source
                + "]</FONT></U>";
    }
    
    private String throwable(LogRecord record){
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
            
            String id = "throwable"+Math.random();
            
            return 
                "<a href=\"#a"+id+ "\" onclick=\"toggle(\'"+ id+ "\')\">Show exception stacktrace</a> "+
                "<a name=\"a"+id+"\"/>"+
                "<div id=\""+ id+ "\" style=\"display:none\"><pre> "+
                throwable+
                "</pre></div>"
                ;
        }return "";
        
    }
    
    private static String c(Color c){
        return Integer.toHexString(c.getRGB()).substring(2, 8);
    }
    
    private String getTime(Date d){
        return (1900+d.getYear())+":"+_(d.getMonth())+":"+
                _(d.getDate())+","+_(d.getHours())+":<b>"+_(d.getMinutes())+":"+_(d.getSeconds())+"."
                + __(d.getTime())+"</b>";
    }
    
    private String _(int x){
        if(x<10)return "0"+x;
        return String.valueOf(x);
    }
    
    private String __(long t){
        long f = t%1000;
        if(f<10)return "00"+f;
        if(f<100)return "0"+f;
        return String.valueOf(f);
    }
    
    private static final class FakeThrowable extends Throwable{
    
    }
    

    
}
