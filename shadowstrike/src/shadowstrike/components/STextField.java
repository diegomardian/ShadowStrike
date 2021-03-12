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


