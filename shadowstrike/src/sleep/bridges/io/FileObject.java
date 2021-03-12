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
package sleep.bridges.io;

import java.io.*;
import sleep.bridges.BridgeUtilities;
import sleep.runtime.ScriptEnvironment;

public class FileObject extends IOObject
{
   protected File file;

   /** returns the file referenced by this IOObject */
   public Object getSource()
   {
      return file;
   }

   /** opens a file and references it to this file object.  the descriptor parameter is a filename */
   public void open(String descriptor, ScriptEnvironment env)
   {
      try
      {
         if (descriptor.charAt(0) == '>' && descriptor.charAt(1) == '>')
         {
            file = BridgeUtilities.toSleepFile(descriptor.substring(2, descriptor.length()).trim(), env.getScriptInstance());
            openWrite(new FileOutputStream(file, true));
         }
         else if (descriptor.charAt(0) == '>')
         {
            file = BridgeUtilities.toSleepFile(descriptor.substring(1, descriptor.length()).trim(), env.getScriptInstance());
            openWrite(new FileOutputStream(file, false));
         }
         else
         {
            file = BridgeUtilities.toSleepFile(descriptor, env.getScriptInstance());
            openRead(new FileInputStream(file));
         }
      }
      catch (Exception ex)
      {
         env.flagError(ex);
      }
   }
}
