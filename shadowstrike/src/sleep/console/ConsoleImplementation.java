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
package sleep.console;

import java.io.*;
import java.util.*;
import shadowstrike.ShadowStrike;
import shadowstrike.utils.HtmlColors;

import sleep.interfaces.*;
import sleep.engine.*;
import sleep.error.*;
import sleep.parser.*;
import sleep.runtime.*;

import sleep.bridges.*;

/**
 * <p>The ConsoleImplementation is the "engine" behind the sleep console.  To use the sleep console in your application use 
 * the following steps:</p>
 * 1. Instantiate the console implementation
 * <br>
 * <br><code>ConsoleImplementation console;</code>
 * <br><code>console = new ConsoleImplementation(environment, variables, loader);</code>
 * <br>
 * <br>2. Install your implementation of sleep.console.ConsoleProxy into the console
 * <br>
 * <br><code>console.setProxy(new MyConsoleProxy());</code>
 * <br>
 * <br>3. Start the Read, Parse, Print Loop in the console
 * <br>
 * <br><code>console.rppl(); // starts the console</code>
 * 
 * <p>When embedding the console reusing the object of an already quitted console is not
 * only allowed but it is also recommended.  When a user quits the console with the quit command
 * the console proxy is set to a dummy console that does not output anything.  To restart
 * a quitted console just set the appropriate proxy again and call the <code>rppl()</code> method.</P>
 *
 * @see sleep.console.ConsoleProxy
 * @see sleep.runtime.ScriptLoader
 * @see sleep.interfaces.Variable
 */
public class ConsoleImplementation implements RuntimeWarningWatcher, Loadable, ConsoleProxy
{
   /** the *active* script... */
   private ScriptInstance script; 

   /** the user installed console proxy, defining all input/output for the console */
   private ConsoleProxy myProxy; 

   /** the script environment with all of the installed functions, predicates, and environments */
   private Hashtable        sharedEnvironment; 

   /** the shared variable container for all scripts, assuming variables are being shared */
   private Variable         sharedVariables; 

   /** the script loader */
   private ScriptLoader     loader; 

   /** our import manager */
   private ImportManager    imports;

   /** Creates an implementation of the sleep console.  The implementation created by this constructor is isolated from your 
       applications environment.  Any scripts loaded via this console will have only the default bridges.  */
   public ConsoleImplementation(ShadowStrike main)
   {
      this(new Hashtable(), new DefaultVariable(), new ScriptLoader(main));
   }

   /** Creates an implementation of the sleep console that shares what your application is already using.  Any of the 
     * parameters can be null. 
     *
     * <p><font color="red"><b>Warning!</b></font> If you choose to use the Sleep console in your application with this constructor,
     * be aware that even if you don't specify a set of variables or an environment for scripts to share that they will all end up 
     * sharing something as the sleep console will create and install its own environment or variables if you don't specify 
     * something.</p>
     *
     * @param _sharedEnvironment the environment contains all of the bridges (functions, predicates, and environments)
     * @param _sharedVariables the Variable class is a container for Scalar variables with global, local, and script specific scope
     * @param _loader the Script Loader is a container for managing all of the currently loaded scripts
     */
   public ConsoleImplementation(Hashtable _sharedEnvironment, Variable _sharedVariables, ScriptLoader _loader)
   {
      if (_sharedEnvironment == null)
         _sharedEnvironment = new Hashtable();

      if (_sharedVariables == null)
         _sharedVariables = new DefaultVariable();

      

      sharedEnvironment = _sharedEnvironment;
      sharedVariables   = _sharedVariables;
      loader            = _loader;
      loader.addSpecificBridge(this);

      setProxy(this);
   }

   /** Returns the current console proxy being used */
   public ConsoleProxy getProxy()
   {
      return myProxy;
   }

   /** Sets up the implementation of the consoles input/output facilities */
   public void setProxy(ConsoleProxy p)
   {
      myProxy = p;
   }

   /** Dummy implementation, does nothing really. */
   public void consolePrint(String m) { }

   /** Dummy implementation, always returns null. */
   public String consoleReadln() { return null; }

   /** Dummy implementation, does nothing. */
   public void consolePrintln(Object m) { }
   
   public void consoleClear() {}


   /** starts the console */
    public void rppl() throws IOException
    {

        String input;
        StringBuffer code = new StringBuffer();
        String repeat = ""; 
        while (true)
        {
            input = getProxy().consoleReadln();
            getProxy().consolePrint("shadowstrike> "+input);
            if (input != null)
            {
                List<String> args = Collections.emptyList();
                ArrayList<String> splitCommands; 
                String command, filter;
                command = "";
                input = input.trim();
                splitCommands = shadowstrike.utils.Strings.shellSplit(input);
                if (splitCommands.size() == 0) {
                    continue;
                }
                else if (splitCommands.size() == 1) {
                    command = splitCommands.get(0);
                    args = Collections.emptyList();
                }
                else {
                    command = splitCommands.get(0);
                    args = splitCommands.subList(1, splitCommands.size()-1);
                }
                
                System.out.println(args);
                System.out.println(command);



                if (command.equals("version"))
                {
                   getProxy().consolePrintln("ShadowScript 1.0 BETA");
                }
                else if (command.equals("help"))
                {
                    if (args.size() >= 1) {
                        help(args.get(0));
                    }
                    else {
                        help();
                    }
                }
                else if (command.equals("ls"))
                {
                   list();
                }
                else if (command.equals("clear")) {
                    getProxy().consoleClear();
                }
                else if (command.equals("load"))
                {
                    if (args.size() >= 1) {
                        load(args.get(0));
                    }
                    else {
                        getProxy().consolePrintln(HtmlColors.error+" Please specify a script to load");
                    }
                }
                else if (command.equals("unload") && args != null)
                {
                    if (args.size() >= 1) {
                        unload(args.get(0));
                    }
                    else {
                        getProxy().consolePrintln(HtmlColors.error+" Please specify a script to load");
                    }
                   
                }
                else if (command.equals("e")) {
                   Scalar value = eval(args + ";", String.join(" ", args));

                   if (value != null) { getProxy().consolePrintln(value + ""); }
                }
                else if (command.equals("x") && args != null)
                {
                   Scalar value = eval("return " + args + ";", String.join(" ", args));
                   if (value != null) { getProxy().consolePrintln(value + ""); }
                }
                else if (command.equals("?") && args != null)
                {
                   Scalar value = eval("return iff(" + args + ", 'true', 'false');", String.join(" ", args));
                   if (value != null) { getProxy().consolePrintln(value + ""); }
                }
                else 
                {
                   getProxy().consolePrintln(HtmlColors.error+"Command '"+command+"' not found. Type 'help' if you need it");
                } 
            }


        }

      
    }

   private void help()
   {
       getProxy().consolePrintln("Commandsts<br>" +
       HtmlColors.makeGray("-------"));
       getProxy().consolePrintln("debug [script] <level>");
       getProxy().consolePrintln("env [functions/other] [regex filter]");
       getProxy().consolePrintln("help [command]");
       getProxy().consolePrintln("list");
       getProxy().consolePrintln("load <file>");
       getProxy().consolePrintln("unload <file>");
       getProxy().consolePrintln("tree [key]");
       getProxy().consolePrintln("version");
       getProxy().consolePrintln("x <expression>");
       getProxy().consolePrintln("? <predicate expression>");
   }

   private void help(String command)
   {
       if (command.equals("ls"))
       {
          getProxy().consolePrintln("ls");
          getProxy().consolePrintln("   lists all of the currently loaded scripts");
       }
       else if (command.equals("load"))
       {
          getProxy().consolePrintln("load <file>");
          getProxy().consolePrintln("   loads a script file into the script loader");
       }
       else if (command.equals("unload"))
       {
          getProxy().consolePrintln("unload <file>");
          getProxy().consolePrintln("   unloads a script file from the script loader");
       }
       else if (command.equals("x"))
       {
          getProxy().consolePrintln("x <expression>");
          getProxy().consolePrintln("   evaluates a sleep expression and displays the value");
       }
       else if (command.equals("?"))
       {
          getProxy().consolePrintln("? <predicate expression>");
          getProxy().consolePrintln("   evaluates a sleep predicate expression and displays the truth value");
       }
       else if (command.equals("e"))
       {
          getProxy().consolePrintln("e <statement>");
          getProxy().consolePrintln("   evaluate a sleep statement");
       }
       else
       {
          getProxy().consolePrintln("help [command]");
          getProxy().consolePrintln("   displays a help message for the specified command");
       }
   }

   private void load(String file)
   {
       try
       {
          if (loader.getScriptsByKey().containsKey(file)) {
              getProxy().consolePrintln(HtmlColors.error+"Could not load script : " + file + " is already loaded");
          }
          else {
            ScriptInstance script = loader.loadScript(file, sharedEnvironment);

            if (System.getProperty("sleep.debug") != null)
            {
               script.setDebugFlags(Integer.parseInt(System.getProperty("sleep.debug")));
            }
            script.runScript();
          }
       }
       catch (YourCodeSucksException yex)
       {
          processScriptErrors(yex);
       }
       catch (Exception ex)
       {
          getProxy().consolePrintln(HtmlColors.error + "Could not load script " + file + ": "+ex.getMessage());
       }
   }
   
   private void reload(String file){
       
   }

   private String getFullScript(String name)
   {
       if (loader.getScriptsByKey().containsKey(name))
       {
          return name;
       }

       Iterator i = loader.getScripts().iterator();
       while (i.hasNext())
       {
          ScriptInstance script = (ScriptInstance)i.next();
          File temp = new File(script.getName());
 
          if (temp.getName().equals(name))
          {
             return temp.getAbsolutePath();
          }
       }

       return name;
   }

   private void unload(String file)
   {
       try
       {
            if (loader.getScriptsByKey().containsKey(file))
            {
                loader.unloadScript(getFullScript(file));
                return;
            }
          
            getProxy().consolePrintln(HtmlColors.error+"Could not find script '" + file + "'");
            return;
          
       }
       catch (Exception ex)
       {
          getProxy().consolePrintln(HtmlColors.error+"Could not unloaded script " + file + ": " + ex.getMessage());
       }
   }

   private void list()
   {
       getProxy().consolePrintln("Scripts<br>" +
HtmlColors.makeGray("-------"));
       Iterator i = loader.getScripts().iterator();
       while (i.hasNext())
       {
          ScriptInstance temp = (ScriptInstance)i.next();
          getProxy().consolePrintln(temp.getName());
       }
   }

   private void env(String type, String filter)
   {
       Iterator i = sharedEnvironment.keySet().iterator();
       while (i.hasNext())
       {
          Object temp = i.next();
          
          if ( (type == null) || 
               (type.equals("functions") && temp.toString().charAt(0) == '&') ||
               (type.equals("other") && temp.toString().charAt(0) != '&') 
             )
          {
             if (filter == null || java.util.regex.Pattern.matches(".*?" + filter + ".*", sharedEnvironment.get(temp).toString()))
             {
                getProxy().consolePrintln(align(temp.toString(), 20) + " => " + sharedEnvironment.get(temp));
             }
          }
       }
   }

   private String align(String text, int to)
   {
       StringBuffer temp = new StringBuffer(text);
       while (temp.length() < to)
       {
          temp.append(" ");
       }

       return temp.toString();
   }

   private void tree(String item)
   {
       if (item == null)
       {
          getProxy().consolePrintln(script.getRunnableBlock().toString());
       }
       else if (item.charAt(0) == '&' || item.charAt(0) == '$')
       {
          if (sharedEnvironment != null && sharedEnvironment.get(item) instanceof SleepClosure)
          {
             SleepClosure temp = (SleepClosure)sharedEnvironment.get(item);
             getProxy().consolePrintln(temp.getRunnableCode());
          }
          else
          {
             getProxy().consolePrintln(HtmlColors.error+"Could not find code block "+item+" to print tree of");
          }
       }
       else
       {
          Map temp = loader.getScriptsByKey();

          if (temp.get(getFullScript(item)) != null)
          {
             getProxy().consolePrintln(((ScriptInstance)temp.get(getFullScript(item))).getRunnableBlock());
          }
          else
          {
             getProxy().consolePrintln("Could not find script "+item+" to print tree of");
          }
       }
   }

   private void debug(String item, int level)
   {
       if (item == null)
       {
          System.setProperty("sleep.debug", ""+level);
          getProxy().consolePrintln("Default debug level set");
       }
       else
       {
          Map temp = loader.getScriptsByKey();

          if (temp.get(getFullScript(item)) != null)
          {
             ((ScriptInstance)temp.get(getFullScript(item))).setDebugFlags(level);
             getProxy().consolePrintln("Debug level set for "+item);
          }
          else
          {
             getProxy().consolePrintln("Could not find script "+item+" to set debug level for");
          }
       }
   }

   private Scalar eval (String expression, String original)
   {
       try
       {
          Parser parser = new Parser("eval", expression.toString(), imports);
          imports = parser.getImportManager();
          parser.parse();
          Block parsed = parser.getRunnableBlock();

          script = loader.loadScript("<interact mode>", parsed, sharedEnvironment);

          if (System.getProperty("sleep.debug") != null)
          {
             script.setDebugFlags(Integer.parseInt(System.getProperty("sleep.debug")));
          }

          return script.runScript();
       }
       catch (YourCodeSucksException yex)
       {
          processScriptErrors(yex);
       }
       catch (Exception ex)
       {
          getProxy().consolePrintln(HtmlColors.error + "error with " + original + ": " + ex.toString());
       } 

       return null;
   }

   /** a convienence method that formats and writes each syntax error to the proxy output */
   public void processScriptErrors(YourCodeSucksException ex)
   {
      getProxy().consolePrint(ex.formatErrors());
   }

   public void processScriptWarning(ScriptWarning warning)
   {
      getProxy().consolePrintln(warning.toString());
   }     

   public void scriptLoaded(ScriptInstance script)
   {
      getProxy().consolePrintln(HtmlColors.success + script.getName() + " loaded successfully.");

      script.addWarningWatcher(this);
      script.setScriptVariables(new ScriptVariables(sharedVariables));
   }

   public void scriptUnloaded(ScriptInstance script)
   {
      getProxy().consolePrintln(HtmlColors.success+script.getName() + " has been unloaded");
   }
}
