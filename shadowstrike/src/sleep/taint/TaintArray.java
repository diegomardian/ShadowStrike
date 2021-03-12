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

import sleep.runtime.*;
import java.util.*;
import sleep.engine.ObjectUtilities;

/** Used to wrap read-only arrays so values are only converted on an as-needed basis */
public class TaintArray implements ScalarArray
{
   protected ScalarArray source;

   public ScalarArray sublist(int begin, int end)
   {
      return new TaintArray(source.sublist(begin, end));
   }

   public TaintArray(ScalarArray src)
   {
      source = src;
   }

   public String toString()
   {
      return source.toString();
   }

   public Scalar pop()
   {
      return TaintUtils.taintAll(source.pop());
   }

   public void sort(Comparator compare)
   {
      source.sort(compare);
   }

   public Scalar push(Scalar value)
   {
      return TaintUtils.taintAll(source.push(value));
   }

   public int size()
   {
      return source.size();
   }

   public Scalar remove(int index)
   {
      return TaintUtils.taintAll(source.remove(index));
   }

   public Scalar getAt(int index)
   {
      return TaintUtils.taintAll(source.getAt(index));
   }

   public Iterator scalarIterator()
   {
      return new TaintIterator(source.scalarIterator());
   }

   public Scalar add(Scalar value, int index)
   {
      return TaintUtils.taintAll(source.add(value, index));
   }

   public void remove(Scalar value)
   {
      source.remove(value);
   }

   protected class TaintIterator implements Iterator
   {
      protected Iterator realIterator;

      public TaintIterator(Iterator iter)
      {
         realIterator = iter;
      }

      public boolean hasNext()
      {
         return realIterator.hasNext(); 
      }

      public Object next()
      {
         return TaintUtils.taintAll((Scalar)realIterator.next());
      }

      public void remove()
      {
         realIterator.remove();
      }
   }
}
