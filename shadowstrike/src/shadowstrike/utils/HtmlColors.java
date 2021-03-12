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

public class HtmlColors {
    public static String red = "<font color='red'>";
    public static String blue = "<font color='blue'>";
    public static String green = "<font color='green'>";
    public static String black = "<font color='black'>";
    public static String white = "<font color='white'>";
    public static String yellow = "<font color='yellow'>";
    public static String gray = "<font color='gray'>";
    public static String error = red + "[-] " + white;
    public static String success = green + "[+] " + white;
    public static String info = blue + "[*] " + white;
    public static String warning = yellow + "[!] " + white;     
    
    public static String makeGray(String msg) {
        return gray+msg+white;
    }
    public static String makeRed(String msg) {
        return red+msg+white;
    }
    public static String makeBlue(String msg) {
        return blue+msg+white;
    }
    public static String makeGreen(String msg) {
        return green+msg+white;
    }
    public static String makeYellow(String msg) {
        return yellow+msg+white;
    }
}