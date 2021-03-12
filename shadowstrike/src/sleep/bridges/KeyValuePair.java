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

import sleep.runtime.Scalar;

/** <p>Arguments passed to functions with the form <code>key =&gt; expression</code> are available via
  * the KeyValuePair object.  The following is the implementation of the built-in function 
  * <code>&hash(key => "value", key2 => 3, ...)</code>:</p>
  *
  * <pre> class hash implements Function
  * {
  *    public Scalar evaluate(String n, ScriptInstance si, Stack arguments)
  *    {
  *       Scalar value = SleepUtils.getHashScalar();
  *
  *       while (!arguments.isEmpty())
  *       {
  *          <b>KeyValuePair kvp = BridgeUtilities.getKeyValuePair(arguments);</b>
  *
  *          Scalar blah = value.getHash().getAt(kvp.getKey());
  *          blah.setValue(kvp.getValue());
  *       }
  *
  *       return value;
  *    }
  * }</pre>
  *
  * @see sleep.bridges.BridgeUtilities
  */
public class KeyValuePair
{
   /** the key scalar */
   protected Scalar key; 

   /** the value scalar */
   protected Scalar value; 

   /** Instantiates a key/value pair */
   public KeyValuePair(Scalar _key, Scalar _value)
   {
      key   = _key;
      value = _value;
   }

   /** Obtain the key portion of this pair */
   public Scalar getKey() { return key; }

   /** Obtain the value portion of this pair */
   public Scalar getValue() { return value; }

   /** Return a string representation of this key/value pair */
   public String toString()
   {
      return key.toString() + "=" + value.toString();
   }
}

