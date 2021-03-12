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
package sleep.runtime;

import java.io.*;

import sleep.taint.*;

public class WatchScalar extends Scalar
{
   protected ScriptEnvironment owner;
   protected String            name;

   public WatchScalar(String _name, ScriptEnvironment _owner)
   {
      name  = _name;
      owner = _owner;
   }

   public void flagChange(Scalar valuez)
   {
      if (owner != null && (value != null || array != null || hash != null))
      {
         owner.showDebugMessage("watch(): " + name + " = " + SleepUtils.describe(valuez));
      }
   }

   /** set the value of this scalar container to a scalar value of some type */
   public void setValue(ScalarType _value)
   {
      /** check if we're merely tainting this scalar... if we are then we can ignore it. */
      if (! (_value.getClass() == TaintedValue.class && ((TaintedValue)_value).untaint() == value) )
      {
         Scalar blah = new Scalar();
         blah.setValue(_value);
         flagChange(blah);
      }
      
      super.setValue(_value);
   }

   /** set the value of this scalar container to a scalar array */
   public void setValue(ScalarArray _array)
   {
      Scalar blah = new Scalar();
      blah.setValue(_array);
      flagChange(blah);

      super.setValue(_array);
   }

   /** set the value of this scalar container to a scalar hash */
   public void setValue(ScalarHash _hash)
   {
      Scalar blah = new Scalar();
      blah.setValue(_hash);
      flagChange(blah);

      super.setValue(_hash);
   }

   private void writeObject(ObjectOutputStream out) throws IOException
   {
       if (SleepUtils.isEmptyScalar(this))
       {
          out.writeObject(null);
       }
       else
       {
          out.writeObject(value);
       }
       out.writeObject(array);
       out.writeObject(hash);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
   {
       value = (ScalarType)in.readObject();
       array = (ScalarArray)in.readObject();
       hash  = (ScalarHash)in.readObject();

       if (value == null && array == null && hash == null)
       {
          setValue(SleepUtils.getEmptyScalar());
       }
   }
}
