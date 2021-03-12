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
import java.net.*;
import sleep.runtime.*;
import sleep.bridges.SleepClosure;

import java.util.*;

public class SocketObject extends IOObject
{
   protected Socket socket;

   /** returns the socket used for this connection */
   public Object getSource()
   {
      return socket;
   }

   public void open(SocketHandler params, ScriptEnvironment env)
   {
      try
      {
         socket = new Socket();
         
         if (params.laddr != null)
         {
            socket.bind(new InetSocketAddress(params.laddr, params.lport));
         }

         socket.connect(new InetSocketAddress(params.host, params.port), params.timeout);

         socket.setSoLinger(true, params.linger);

         openRead(socket.getInputStream());
         openWrite(socket.getOutputStream());
      }
      catch (Exception ex)
      {
         env.flagError(ex);
      }
   }

   /** releases the socket binding for the specified port */
   public static void release(int port)
   {
      String key = port + "";
      
      ServerSocket temp = null;
      if (servers != null && servers.containsKey(key))
      {
         temp = (ServerSocket)servers.get(key);
         servers.remove(key);
 
         try
         {
            temp.close();
         }
         catch (Exception ex)
         {
            ex.printStackTrace();
         }
      }
   }

   private static Map servers;

   private static ServerSocket getServerSocket(int port, SocketHandler params) throws Exception
   {
      String key = port + "";

      if (servers == null)
      {
         servers = Collections.synchronizedMap(new HashMap());
      }

      ServerSocket server = null;

      if (servers.containsKey(key))
      {
         server = (ServerSocket)servers.get(key);
      }
      else
      {
         server = new ServerSocket(port, params.backlog, params.laddr != null ? InetAddress.getByName(params.laddr) : null);
         servers.put(key, server);
      }

      return server;
   }
 
   public void listen(SocketHandler params, ScriptEnvironment env)
   {
      ServerSocket server = null;

      try
      {
         server = getServerSocket(params.port, params);
         server.setSoTimeout(params.timeout);
        
         socket = server.accept();
         socket.setSoLinger(true, params.linger);

         params.callback.setValue(SleepUtils.getScalar(socket.getInetAddress().getHostAddress()));

         openRead(socket.getInputStream());
         openWrite(socket.getOutputStream());

         return;
      }
      catch (Exception ex)
      {
         env.flagError(ex);
      }
   }

   public void close()
   {
      try
      {
         socket.close();
      }
      catch (Exception ex) { }

      super.close();
   }

    public static final int LISTEN_FUNCTION  = 1;
    public static final int CONNECT_FUNCTION = 2;

    public static class SocketHandler implements Runnable
    {
       public ScriptInstance script;
       public SleepClosure   function;
       public SocketObject   socket;

       public int            port;
       public int            timeout;
       public String         host;
       public Scalar         callback;

       public int            type;
       public String         laddr;
       public int            lport;
       public int            linger;
       public int            backlog;

       public void start()
       {
          if (function != null)
          {
             socket.setThread(new Thread(this));
             socket.getThread().start();
          }
          else
          {
             run();
          }
       }

       public void run()
       {
          if (type == LISTEN_FUNCTION)
          {
             socket.listen(this, script.getScriptEnvironment());
          }
          else
          {
             socket.open(this, script.getScriptEnvironment());
          }

          if (function != null)
          {
             Stack  args  = new Stack();
             args.push(SleepUtils.getScalar(socket));
             function.callClosure("&callback", script, args);
          }
       }
    }
}
