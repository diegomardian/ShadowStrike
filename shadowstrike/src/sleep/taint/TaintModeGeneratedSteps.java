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
package sleep.taint;

import sleep.engine.*;
import sleep.engine.types.*;
import sleep.engine.atoms.*;

import sleep.runtime.*;
import sleep.interfaces.*;

import java.util.*;

/** A replacement factory that generates Sleep interpreter instructions that honor and spread the taint mode. */
public class TaintModeGeneratedSteps extends GeneratedSteps
{
   public Step Call(String function)
   {
      return new TaintCall(function, super.Call(function));
   }

   public Step PLiteral(List doit)
   {
      return new PermeableStep(super.PLiteral(doit));
   }

   public Step Operate(String oper)
   {
      return new TaintOperate(oper, super.Operate(oper));
   }

   public Step ObjectNew(Class name)
   {
      return new PermeableStep(super.ObjectNew(name));
   }

   public Step ObjectAccess(String name)     
   {
      return new TaintObjectAccess(super.ObjectAccess(name), name, null);
   }

   public Step ObjectAccessStatic(Class aClass, String name)
   {
      return new TaintObjectAccess(super.ObjectAccessStatic(aClass, name), name, aClass);
   }
}
