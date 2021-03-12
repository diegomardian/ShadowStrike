/* 
 * BSD 3-Clause License
 * 
 * Copyright (c) 2021, Diego Mardian
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package shadowstrike.utils;

/**
 *
 * @author root
 */
public class Printer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public Printer() {
        
    }
    
    public static String success(String message) {
        return ANSI_BLUE+"[+]"+ANSI_RESET+" "+message;
    }
    
    public static String error(String message) {
        return ANSI_RED+"[-]"+ANSI_RESET+" "+message;
    }
    
    public static String info(String message) {
        return ANSI_BLUE+"[*]"+ANSI_RESET+" "+message;
    }
    
    public static String warning(String message) {
        return ANSI_YELLOW+"[!]"+ANSI_RESET+" "+message;
    }
    
    public static String yellow(String message) {
        return ANSI_YELLOW+message+ANSI_RESET;
    }
    
    public static String red(String message) {
        return ANSI_RED+message+ANSI_RESET;
    }
    
    public static String green(String message) {
        return ANSI_GREEN+message+ANSI_RESET;
    }
    
    public static String black(String message) {
        return ANSI_BLACK+message+ANSI_RESET;
    }
    
    public static String cyan(String message) {
        return ANSI_CYAN+message+ANSI_RESET;
    }
    
    public static String blue(String message) {
        return ANSI_BLUE+message+ANSI_RESET;
    }
    
    public static String white(String message) {
        return ANSI_WHITE+message+ANSI_RESET;
    }
    
    public static String purple(String message) {
        return ANSI_PURPLE+message+ANSI_RESET;
    }
    
    public static void plain(String message) {
        System.out.println(message);
    }
}
