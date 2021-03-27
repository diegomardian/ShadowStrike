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
package shadowstrike;
import shadowstrike.ui.ListenersTable;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import shadowstrike.utils.Printer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.openide.awt.TabbedPaneFactory;
import org.openide.util.Exceptions;
import shadowstrike.ui.ScriptConsole;
import shadowstrike.ui.SessionTable;
import sleep.runtime.ScriptLoader;
import shadowstrike.database.Database;
/**
 *
 * @author root
 */
public class ShadowStrike extends javax.swing.JFrame {

    /**
     * Creates new form ShadowStrike
     */
    public ScriptLoader scriptLoader;
    public String scriptData;
    public String path;
    public ShadowStrike() {
        initComponents();
        this.scriptData = "";
        this.scriptLoader = new ScriptLoader(this);
        this.jSplitPane1.setTopComponent(new SessionTable());
        openScriptConosle();
        openListenerTable();
        path = "";
        try {
            path = new File(ShadowStrike.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParentFile().getAbsolutePath();
        } catch (URISyntaxException ex) {
            Printer.plain(Printer.error("An error ocurred while getting the current directory\nError:\n"+ex.getStackTrace()));
            System.exit(0);
        }
        if (path.isEmpty()) {
            Printer.plain(Printer.error("Could not get current directory"));
            System.exit(0);
        }
        if (!Paths.get(path, "data.bin").toFile().exists()) {
            try {
                Paths.get(path, "data.bin").toFile().createNewFile();
            } catch (IOException ex) {
                Printer.plain(Printer.error("Could not create data file "+Paths.get(path, "data.bin").toString()));
                ex.printStackTrace();
                System.exit(0);
            }
            Globals.data = new Data();
        }
        else {
            try
            {   
                if (!(Paths.get(path, "data.bin").toFile().length() == 0)) {
                    FileInputStream file = new FileInputStream(Paths.get(path, "data.bin").toString());
                    ObjectInputStream in = new ObjectInputStream(file);
                    Globals.data = (Data)in.readObject();
                    in.close();
                    file.close();
                }
                else {
                    Globals.data = new Data();
                }
                
            }
            catch(ClassNotFoundException ex)
            {
                Printer.plain(Printer.error("Could not read file Paths.get(path, \"data.bin\").toString()"));
                ex.printStackTrace();
                Globals.data = new Data();
            }
            catch(IOException ex)
            {
                Printer.plain(Printer.error("Could not import data: "+Paths.get(path, "data.bin").toString()+" does not have valid data"));
                ex.printStackTrace();
                Globals.data = new Data();
            }
            
        }
        
        
    }
    public void close() {
        FileOutputStream file;
        ObjectOutputStream out;
        try {
            file = new FileOutputStream(Paths.get(this.path, "data.bin").toString());
            out = new ObjectOutputStream(file);
            out.writeObject(Globals.data);
            out.close();
            file.close();
        } catch (FileNotFoundException ex) {
            Printer.plain(Printer.error("Error saving data: Could not find file "+Paths.get(path, "data.bin").toString()));
            ex.printStackTrace();
            System.exit(0);
        }
        catch (IOException ex) {
            Printer.plain(Printer.error("Error saving data: Could not write file "+Paths.get(path, "data.bin").toString()));
            ex.printStackTrace();
            System.exit(0);
        }
        System.exit(0);
    }
    public void openListenerTable() {
        int index = Globals.listenerTableTabCount;
        String number = "";
        if (Globals.listenerTables.size() > 0) {
            number = " - "+String.valueOf(Globals.listenerTables.get(Globals.listenerTables.size()-1).id+1);
            
        }
        
        
        ListenersTable listenerTable = new ListenersTable(this, "Listeners"+number, index);
        
        this.MainTabbedPane.addTab("Listeners"+number, listenerTable);
        JPanel pnlTab = new JPanel(new GridBagLayout());
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel("Listeners"+number);
        lblTitle.setFont(new Font("DejaVu Sans", 0, 15));
        JButton btnClose = new JButton("x");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        pnlTab.add(lblTitle, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        pnlTab.add(btnClose, gbc);

        this.MainTabbedPane.setTabComponentAt(this.MainTabbedPane.getTabCount()-1, pnlTab);

        btnClose.addActionListener(new onListenersTableTabClose("Listeners"+number, this.MainTabbedPane));
        this.MainTabbedPane.setSelectedIndex(this.MainTabbedPane.getTabCount()-1);
        Globals.listenerTables.add(listenerTable);
        Globals.listenerTableTabCount += 1;
    }
    public void openScriptConosle() {
        int index = Globals.consoleTabCount;
        String number = "";
        if (Globals.consoles.size() > 0) {
            number = " - "+String.valueOf(Globals.consoles.get(Globals.consoles.size()-1).id+1);
            
        }
        ScriptConsole scriptConsole = new ScriptConsole(this, "Script Console"+number, index);
        
        this.MainTabbedPane.addTab("Script Console"+number, scriptConsole);
        JPanel pnlTab = new JPanel(new GridBagLayout());
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel("Script Console"+number);
        lblTitle.setFont(new Font("DejaVu Sans", 0, 15));
        JButton btnClose = new JButton("x");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        pnlTab.add(lblTitle, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        pnlTab.add(btnClose, gbc);

        this.MainTabbedPane.setTabComponentAt(this.MainTabbedPane.getTabCount()-1, pnlTab);

        btnClose.addActionListener(new onConsoleTabClose("Script Console"+number, this.MainTabbedPane));
        this.MainTabbedPane.setSelectedIndex(this.MainTabbedPane.getTabCount()-1);
        Globals.consoles.add(scriptConsole);
        Globals.consoleTabCount += 1;
        
    }
    private static class onConsoleTabClose implements ActionListener {
        private String name;
        private JTabbedPane tabbedPane;
        

        public onConsoleTabClose(String name, JTabbedPane tabbedPane) {
            this.name = name;
            this.tabbedPane = tabbedPane;
        }


        public void actionPerformed(ActionEvent evt) {

            if (tabbedPane.indexOfTab(name) >= 0) {

                tabbedPane.removeTabAt(tabbedPane.indexOfTab(name));
                Globals.consoleTabCount -= 1;

                for (int i = 0; i < Globals.consoles.size(); i++) {
                    if (Globals.consoles.get(i).name.equals(name)) {
                        Globals.consoles.remove(i);
                    }
                }

            }

        }
    }
    private static class onListenersTableTabClose implements ActionListener {
        private String name;
        private JTabbedPane tabbedPane;
        

        public onListenersTableTabClose(String name, JTabbedPane tabbedPane) {
            this.name = name;
            this.tabbedPane = tabbedPane;
        }


        public void actionPerformed(ActionEvent evt) {

            if (tabbedPane.indexOfTab(name) >= 0) {

                tabbedPane.removeTabAt(tabbedPane.indexOfTab(name));
                Globals.listenerTableTabCount -= 1;
                for (int i = 0; i < Globals.listenerTables.size(); i++) {
                    if (Globals.listenerTables.get(i).name.equals(name)) {
                        Globals.listenerTables.remove(i);
                    }
                }

            }

        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        MainTabbedPane = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        PreferencesMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        ScriptManagerMenuItem = new javax.swing.JMenuItem();
        ListenersMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        CloseMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0};
        layout.rowHeights = new int[] {0};
        layout.columnWeights = new double[] {0.5};
        layout.rowWeights = new double[] {0.5};
        getContentPane().setLayout(layout);

        jSplitPane1.setDividerLocation(110);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        //MainTabbedPane = TabbedPaneFactory.createCloseButtonTabbedPane();
        //        MainTabbedPane.addPropertyChangeListener( TabbedPaneFactory.PROP_CLOSE, new PropertyChangeListener() {
            //
            //        public void propertyChange(PropertyChangeEvent evt) {
                //                javax.swing.JTabbedPane pane = (javax.swing.JTabbedPane)evt.getSource();
                //                int sel = pane.getSelectedIndex();
                //                if (pane.getTitleAt(sel).equals("Script Console")) {
                    //                    Globals.consoles.remove(sel);
                    //                }
                //                pane.removeTabAt(sel);
                //
                //            }
            //        });
    MainTabbedPane.setToolTipText("");
    MainTabbedPane.setFont(new java.awt.Font("DejaVu Sans Mono", 3, 16)); // NOI18N

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(MainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1119, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(MainTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
    );

    jSplitPane1.setRightComponent(jPanel2);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1)
                .addGap(0, 0, 0)))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addGap(0, 0, 0)))
    );

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
    gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    getContentPane().add(jPanel1, gridBagConstraints);

    jMenuBar1.setFont(new java.awt.Font("DejaVu Sans", 0, 15)); // NOI18N

    jMenu1.setText("Shadow Strike");
    jMenu1.setFont(new java.awt.Font("DejaVu Sans", 0, 15)); // NOI18N

    PreferencesMenuItem.setText("Preferences");
    jMenu1.add(PreferencesMenuItem);
    jMenu1.add(jSeparator1);

    ScriptManagerMenuItem.setText("Script Manager");
    jMenu1.add(ScriptManagerMenuItem);

    ListenersMenuItem.setText("Listeners");
    ListenersMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ListenersMenuItemActionPerformed(evt);
        }
    });
    jMenu1.add(ListenersMenuItem);
    jMenu1.add(jSeparator2);

    CloseMenuItem.setText("Close");
    jMenu1.add(CloseMenuItem);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("View");
    jMenu2.setFont(new java.awt.Font("DejaVu Sans", 0, 15)); // NOI18N

    jMenuItem1.setText("Script Console");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    jMenu2.add(jMenuItem1);

    jMenuBar1.add(jMenu2);

    jMenu3.setText("Attacks");
    jMenu3.setFont(new java.awt.Font("DejaVu Sans", 0, 15)); // NOI18N
    jMenuBar1.add(jMenu3);

    setJMenuBar(jMenuBar1);

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ListenersMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListenersMenuItemActionPerformed
        // TODO add your handling code here:
        openListenerTable();
    }//GEN-LAST:event_ListenersMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        openScriptConosle();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        Printer.plain("H");
        this.close();
        
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ShadowStrike.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShadowStrike.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShadowStrike.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShadowStrike.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        
        //</editor-fold>
        //</editor-fold>
                
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShadowStrike().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CloseMenuItem;
    private javax.swing.JMenuItem ListenersMenuItem;
    private javax.swing.JTabbedPane MainTabbedPane;
    private javax.swing.JMenuItem PreferencesMenuItem;
    private javax.swing.JMenuItem ScriptManagerMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
