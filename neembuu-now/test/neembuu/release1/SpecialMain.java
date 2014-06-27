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

package neembuu.release1;

import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.externalImpl.linkhandler.YoutubeLinkHandlerProvider;

/**
 *
 * @author davidepastore
 */
public class SpecialMain {
    public static void main(String[] args) {
        Main.main(args, new Main.CallbackFromTestCode() {
            @Override public void callback() {
                LinkHandlerProviders.registerProvider(new YoutubeLinkHandlerProvider());
            }
        }); 
        // yes it is a bit of functional programming style, it is difficult to write in java, but it is the way to modular code
        //anyway stuff should work now
    }
}
