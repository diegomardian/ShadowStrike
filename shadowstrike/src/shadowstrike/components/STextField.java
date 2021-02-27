/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shadowstrike.components;

/**
 *
 * @author root
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class STextField extends JTextField {
    protected JPopupMenu menu = null;

    public STextField(int var1) {
        super(var1);
        this.createMenu();
    }

    public STextField(Document var1, String var2, int var3) {
        super(var1, var2, var3);
        this.createMenu();
    }

    public STextField(String var1, int var2) {
        super(var1, var2);
        this.createMenu();
    }

    public STextField() {
        this.createMenu();
    }

    public void createMenu() {
        if (this.menu == null) {
            this.menu = new JPopupMenu();
            JMenuItem var1 = new JMenuItem("Cut", 67);
            JMenuItem var2 = new JMenuItem("Copy", 111);
            JMenuItem var3 = new JMenuItem("Paste", 80);
            JMenuItem var4 = new JMenuItem("Clear", 108);
            var1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent var1) {
                    STextField.this.cut();
                }
            });
            var2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent var1) {
                    STextField.this.copy();
                }
            });
            var3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent var1) {
                    STextField.this.paste();
                }
            });
            var4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent var1) {
                    STextField.this.setText("");
                }
            });
            this.menu.add(var1);
            this.menu.add(var2);
            this.menu.add(var3);
            this.menu.add(var4);
            this.addMouseListener(new MouseAdapter() {
                public void handle(MouseEvent var1) {
                    if (var1.isPopupTrigger()) {
                        STextField.this.menu.show((JComponent)var1.getSource(), var1.getX(), var1.getY());
                    }

                }

                public void mousePressed(MouseEvent var1) {
                    this.handle(var1);
                }

                public void mouseClicked(MouseEvent var1) {
                    this.handle(var1);
                }

                public void mouseReleased(MouseEvent var1) {
                    this.handle(var1);
                }
            });
        }
    }
}


