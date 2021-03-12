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
import sleep.runtime.*;

/** <p>The IOObject is the parent class for all IO Source objects that are compatible with Sleep's
 *  I/O API.</p>
 *
 *  <p>When an IOObject is created, calling openRead() with the appropriate input stream will initialize
 *  this IOObject to work with IO functions that read data.  Calling openWrite() has the same effect for
 *  print functions.  It is the responsibility of the IOObject child class to invoke openRead and openWrite.
 *  This is usually done in the constructor.</p>  
 *
 *  <p>The pipeline for reading data looks like this:</p>
 *
 *  <pre>... <- DataInputStream <- BufferedInputStream <- Original Input Stream</pre>
 *
 *  <p>The pipeline for writing data is:</p>
 *
 *  <pre>... -> DataOutputStream -> Original Output Stream</pre>
 */

public class IOObject
{
   /* input pipeline */ 

   protected InputStreamReader   readeru = null; /* a buffered reader, pHEAR */
   protected DataInputStream     readerb = null; /* used to support the binary read/write stuffz */
   protected BufferedInputStream reader  = null; /* used to support mark and reset functionality y0 */
   protected InputStream         in      = null; /* the original stream, love it, hold it... yeah right */

   /* output pipeline */

   protected OutputStreamWriter  writeru = null;
   protected DataOutputStream    writerb = null; /* high level method for writing stuff out, fun fun fun */
   protected OutputStream        out     = null; /* original output stream */

   /* other fun stuff <3 */  

   protected Thread           thread  = null;
   protected Scalar           token   = null;

   /** return the actual source of this IO for scripters to query using HOES */
   public Object getSource()
   {
      return null;
   }

   /** set the charset to be used for all unicode aware reads/writes from/to this stream */
   public void setEncoding(String name) throws UnsupportedEncodingException
   {
      if (writerb != null)
      {
         writeru = new OutputStreamWriter(writerb, name);
      }

      if (readerb != null)
      {
         readeru = new InputStreamReader(readerb, name);
      }
   }


   /** set the thread used for this IOObject (currently used to allow a script to wait() on the threads completion) */
   public void setThread(Thread t)
   {
      thread = t;
   }

   /** returns the thread associated with this IOObject */
   public Thread getThread()
   {
      return thread;
   }

   public Scalar wait(ScriptEnvironment env, long timeout)
   {
      if (getThread() != null && getThread().isAlive())
      {
         try
         {
            getThread().join(timeout);

            if (getThread().isAlive())
            {
               env.flagError(new IOException("wait on object timed out"));
               return SleepUtils.getEmptyScalar();
            }
         }
         catch (Exception ex)
         {
            env.flagError(ex);
            return SleepUtils.getEmptyScalar();
         }
      }

      return getToken();
   }

   /** returns a scalar token associated with this IOObject.  Will return the empty scalar if the token is null.  The token is essentially the stored return value of an executing thread.  */
   public Scalar getToken()
   {
      if (token == null) return SleepUtils.getEmptyScalar();

      return token;
   }

   /** sets the scalar token associated with this IOObject.  Any ScriptInstance object calls setToken on it's parent IOObject.  This method is called when the script is finished running and has a return value waiting.  This value can be retrieved in Sleep with the <code>&amp;wait</code> function. */
   public void setToken(Scalar t)
   {
      token = t;
   }

   /** sets the stdin/stdout for this script environment. This value is placed into the script metadata with the %console% key */
   public static void setConsole(ScriptEnvironment environment, IOObject object)
   {
      environment.getScriptInstance().getMetadata().put("%console%", object);
   }

   /** returns an IOObject that represents stdin/stdout to Sleep's I/O API. */
   public static IOObject getConsole(ScriptEnvironment environment)
   {
      IOObject console = (IOObject)environment.getScriptInstance().getMetadata().get("%console%");

      if (console == null)
      {
         console = new IOObject();
         console.openRead(System.in);
         console.openWrite(System.out);
         setConsole(environment, console);
      }

      return console;
   }

   /** Returns the latest hooking point into the input stream */
   public InputStream getInputStream()
   {
      return in;
   }

   /** Returns the latest hooking point into the output stream */
   public OutputStream getOutputStream()
   {
      return out;
   }

   /** Initializes a binary reader (a DataInputStream) and a text reader (a BufferedReader) against this input stream.  Calling this effectively makes this IOObject useable with Sleep's IO read* functions. */
   public void openRead(InputStream _in)
   {
      in = _in;
      
      if (in != null)
      {
         reader  = new BufferedInputStream(in, 8192);
         readerb = new DataInputStream(reader);
         readeru = new InputStreamReader(readerb);
      }
   }

   /** Initializes a binary writer (a DataOutputStream) and a text writer (a PrintWriter) against this input stream.  Calling this effectively makes this IOObject useable with Sleep's IO print* functions. */
   public void openWrite(OutputStream _out)
   {
      out = _out;

      if (out != null)
      {
         writerb = new DataOutputStream(out);
         writeru = new OutputStreamWriter(writerb);
      }
   }

   /** Closes all of the reader's / writer's opened by this IOObject.  If the IO Source object opens any streams, this method should be overriden to close those streams when requested.  Calling super.close() is highly recommended as well. */
   public void close()
   {
      try
      {
         if (in != null) { in.notifyAll(); } // done to prevent a deadlock, trust me it works
         if (out != null) { out.notifyAll(); } // done to prevent a deadlock, trust me it works
      }
      catch (Exception ex) { } /* we might get an illegal monitor state type exception if we don't own
                                  the lock from this thread... in that case we move on with our lives */
      try
      {
         if (readeru != null)
           readeru.close();

         if (writeru != null)
           writeru.close();

         if (reader != null)
           reader.close();

         if (readerb != null)
           readerb.close();

         if (writerb != null)
           writerb.close();

         if (in != null)
           in.close();

         if (out != null)
           out.close();
      }
      catch (Exception ex)
      {
      }
      finally
      {
         in      = null;
         out     = null;
         reader  = null;
         readerb = null;
         writerb = null;
         readeru = null;
         writeru = null;
      }
   }

   private boolean stripTheLineSeparator = false;

   /** Reads in a line of text */
   public String readLine()
   {
      try
      {
         if (readeru != null)
         {
            StringBuffer rv = new StringBuffer(8192);
            
            int temp = readeru.read();
         
            /* remember a line can terminate with any of the following: \r, \n, or \r\n */
            if (stripTheLineSeparator && temp == '\n') 
            {
               temp = readeru.read();
            }
   
            stripTheLineSeparator = false;

            while (temp != -1)
            {
               if (temp == '\n')
               {
                  return rv.toString();
               }
               else if (temp == '\r')
               {
                  stripTheLineSeparator = true;
                  return rv.toString();
               }
               else
               { 
                  rv.append((char)temp);
               }
 
               temp = readeru.read();
            }

            close();

            if (rv.length() > 0)
            {
               return rv.toString();
            }
            else
            {
               return null;
            }
         }
      }
      catch (Exception ex) 
      { 
         close();
      }

      return null;
   }

   /** Reads in a character of text and returns as a string. */
   public String readCharacter()
   {
      try
      {
         if (readeru != null)
         {
            int temp = readeru.read();
         
            if (temp == -1)
            {
               close();
            }
            else
            {
               return (char)temp + "";
            }
         }
      }
      catch (Exception ex) 
      { 
         close();
      }

      return null;
   }

   /** Returns true if the reader is closed */
   public boolean isEOF()
   {
      return (reader == null);
   }

   /** Closes down the output streams effectively sending an end of file message to the reading end. */
   public void sendEOF()
   {
      try
      {
         if (writerb != null)
            writerb.close();

         if (out != null)
            out.close();
      }
      catch (Exception ex) { }
   }
 
   /** Returns the ascii data reader */
   public BufferedInputStream getInputBuffer()
   {
       return reader;
   }

   /** Returns the binary data reader */
   public DataInputStream getReader()
   {
       return readerb;
   }
 
   /** Returns the binary data writer */
   public DataOutputStream getWriter()
   {
       return writerb;
   }

   private static final String lineSeparator = System.getProperty("line.separator");

   /** Prints out a line of text with a newline character appended */
   public void printLine(String text)
   {
      print(text + lineSeparator);
   }

   /** Prints out a line of text with no newline character appended */
   public void print(String text)
   {
      try
      {
         if (writeru != null)
         {
            writeru.write(text, 0, text.length());
            writeru.flush();
         }
      }
      catch (Exception ex)
      {
         close();
      }
   }
}

