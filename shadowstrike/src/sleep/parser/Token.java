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
package sleep.parser;

/** as much as possible this is a String with a line number associate with it (aka hint) */
public class Token
{
   protected String term;
   protected int    hint;
   protected int    marker;
   protected int    tophint;
 
   public Token(String term, int hint)
   {
      this(term, hint, -1);
   }

   public Token(String _term, int _hint, int _marker)
   { 
      term   = _term;
      hint   = _hint;
      marker = _marker;
      tophint = -1;
   }

   public String toString()
   {
      return term;
   }

   public int getMarkerIndex()
   {
      return marker;
   }

   public Token copy(int _hint)
   {
      return new Token(term, _hint);
   }
 
   public Token copy(String text)
   {
      return new Token(text, getHint());
   }

   public String getMarker()
   {
      if (marker > -1)
      {
         StringBuffer temp = new StringBuffer();
         for (int x = 0; x < (marker - 1); x++)
         {
            temp.append(" ");
         }
         temp.append("^");

         return temp.toString();
      }

      return null;
   }

   public int getTopHint()
   {
      if (tophint >= 0) {
           return tophint;
      }

      tophint = hint;
      int endAt = -1;
      while ((endAt = term.indexOf('\n', endAt + 1)) > -1)
      {
         tophint++;
      }

      return tophint;
   }

   public int getHint()
   {
      return hint;
   }
}
