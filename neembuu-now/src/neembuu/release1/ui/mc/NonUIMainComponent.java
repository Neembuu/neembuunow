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

package neembuu.release1.ui.mc;

import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;

/**
 *
 * @author Shashank Tulsyan
 */
public class NonUIMainComponent implements MainComponent{

    @Override
    public javax.swing.JFrame getJFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Message newMessage() {
        return new NonUIMessage();
    }
    
    @Override public boolean allowReplacementWith(MainComponent mc) {
        return true;
    }
    
    
    private static final class NonUIMessage implements Message {
        private String message,title;
        @Override public Message warning() {
            return this;
        }
        @Override public Message error() {
            return this;
        }

        @Override public Message info() {
            return this;
        }

        @Override public Message setTitle(String title) {
            this.title=title;
            return this;
        }

        @Override public Message setMessage(String message) {
            this.message = message;
            return this;
        }

        @Override public Message setTimeout(int timeout) {
            return this;
        }

        @Override
        public Message setEmotion(Emotion e) {
            return this;
        }

        @Override
        public Message setPreferredLocation(PreferredLocation pl) {
            return this;
        }

        @Override
        public Message editable() {
            return this;
        }

        @Override
        public void show() {
            System.out.println("title="+title);
            System.out.println("message="+message);
        }

        @Override
        public Message showNonBlocking() {
            show();
            return this;
        }

        @Override
        public void close() {
            
        }

        @Override
        public boolean ask() {
            show();
            return true;//todo ask in console maybe?
        }

        @Override
        public Object ask(Object[] options) {
            return options!=null?options.length>0?options[0]:null:null;
        }
        
        @Override public Object ask(Object[] options, int i) {
            return options!=null?options.length>i?options[i]:null:null;
        }

        @Override
        public String askPassword() {
            show();
            return "";//
        }
        
    }
}
