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
package sleep.bridges;

import sleep.engine.*;
import sleep.runtime.*;
import sleep.interfaces.*;
import sleep.bridges.BridgeUtilities;

import java.text.*;
import java.util.*;
import shadowstrike.ShadowStrike;

public class TabControl implements Loadable
{
    public ShadowStrike main;
   public TabControl(ShadowStrike main) {
       this.main = main;
   }
   public void scriptLoaded(ScriptInstance script)
   {
      // time date functions 
      script.getScriptEnvironment().getEnvironment().put("&openScriptConsole", new openScriptConsole(main));
      script.getScriptEnvironment().getEnvironment().put("&openListenerTable", new openListenerTable(main));

   }

   public void scriptUnloaded(ScriptInstance script)
   {
   }

   private static class openScriptConsole implements Function
   {
       public ShadowStrike main;
      public openScriptConsole(ShadowStrike main) {
          this.main = main;
          
      }
      public Scalar evaluate(String f, ScriptInstance si, Stack locals)
      {
         main.openScriptConosle();
         return SleepUtils.getEmptyScalar();
      }
   }
   private static class openListenerTable implements Function
   {
       public ShadowStrike main;
      public openListenerTable(ShadowStrike main) {
          this.main = main;
          
      }
      public Scalar evaluate(String f, ScriptInstance si, Stack locals)
      {
         main.openListenerTable();
         return SleepUtils.getEmptyScalar();
      }
   }

}
