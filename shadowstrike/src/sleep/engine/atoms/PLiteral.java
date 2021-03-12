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
package sleep.engine.atoms;

import java.util.*;
import sleep.interfaces.*;
import sleep.engine.*;
import sleep.runtime.*;

import java.io.Serializable;

public class PLiteral extends Step
{
   public String toString(String prefix)
   {
      StringBuffer temp = new StringBuffer();
      temp.append(prefix);
      temp.append("[Parsed Literal] ");

      Iterator i = fragments.iterator();

      while (i.hasNext())
      {
         Fragment f = (Fragment)i.next();

         switch (f.type)
         {
            case STRING_FRAGMENT:
              temp.append(f.element);
              break;
            case ALIGN_FRAGMENT:
              temp.append("[:align:]");
              break;
            case VAR_FRAGMENT:
              temp.append("[:var:]");
              break;
         }
      }

      temp.append("\n");

      return temp.toString();
   }

   public Scalar evaluate(ScriptEnvironment e)
   {
      Scalar value = SleepUtils.getScalar(buildString(e));
      e.getCurrentFrame().push(value);
      return value;
   }

   public static final int STRING_FRAGMENT = 1;
   public static final int ALIGN_FRAGMENT  = 2;
   public static final int VAR_FRAGMENT    = 3;
  
   private static final class Fragment implements Serializable
   {
      public Object element;
      public int    type;
   }

   private List fragments;

   /** requires a list of parsed literal fragments to use when constructing the final string at runtime */
   public PLiteral(List f)
   {
      fragments = f;
   }

   /** create a fragment for interpretation by this parsed literal step */
   public static Fragment fragment(int type, Object element)
   {
      Fragment f = new Fragment();
      f.element  = element;
      f.type     = type;

      return f;
   }

   private String buildString(ScriptEnvironment e)
   {
      StringBuffer result = new StringBuffer();
      int          align  = 0;

      String       temp;
      Iterator i = fragments.iterator();

      while (i.hasNext())
      {
         Fragment f = (Fragment)i.next();

         switch (f.type)
         {
            case STRING_FRAGMENT:
              result.append(f.element);
              break;
            case ALIGN_FRAGMENT:
              align = ((Scalar)e.getCurrentFrame().remove(0)).getValue().intValue();
              break;
            case VAR_FRAGMENT:
              temp  = ((Scalar)e.getCurrentFrame().remove(0)).getValue().toString();

              for (int z = 0 - temp.length(); z > align; z--)
              {
                 result.append(" ");
              }

              result.append(temp);

              for (int y = temp.length(); y < align; y++)
              {
                 result.append(" ");
              }

              align = 0;              
              break;
         }
      }

      e.KillFrame();
      return result.toString();
   }
}



