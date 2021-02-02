/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadowstrike.Dialogs;

/**
 *
 * @author root
 */
public class HostFile extends javax.swing.JPanel {

    /**
     * Creates new form HostFile
     */
    public HostFile() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        fileLabel = new javax.swing.JLabel();
        LocalUriTextField = new javax.swing.JTextField();
        LocalUriLabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        LocalHostLabel = new javax.swing.JLabel();
        LocalPortLabel = new javax.swing.JLabel();
        LoaclPortTextField = new javax.swing.JTextField();
        MimeComboBox = new javax.swing.JComboBox<>();
        MimeLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        FileTextField = new javax.swing.JTextField();
        LocalHostTextField = new javax.swing.JTextField();

        jTextPane1.setEditable(false);
        jTextPane1.setText("Host a file with Shadow Strike's web server");
        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextPane1.setFocusCycleRoot(false);
        jScrollPane1.setViewportView(jTextPane1);

        fileLabel.setText("File:");
        fileLabel.setMaximumSize(new java.awt.Dimension(76, 20));
        fileLabel.setMinimumSize(new java.awt.Dimension(76, 20));
        fileLabel.setOpaque(true);
        fileLabel.setPreferredSize(new java.awt.Dimension(76, 20));

        LocalUriTextField.setText("/downloads/file.ext");
        LocalUriTextField.setPreferredSize(new java.awt.Dimension(72, 27));

        LocalUriLabel.setText("Local URI:");
        LocalUriLabel.setMaximumSize(new java.awt.Dimension(66, 20));
        LocalUriLabel.setMinimumSize(new java.awt.Dimension(66, 20));
        LocalUriLabel.setName(""); // NOI18N
        LocalUriLabel.setPreferredSize(new java.awt.Dimension(66, 20));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folderClosedIcon.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        LocalHostLabel.setText("Local Host:");
        LocalHostLabel.setMaximumSize(new java.awt.Dimension(76, 20));
        LocalHostLabel.setMinimumSize(new java.awt.Dimension(76, 20));
        LocalHostLabel.setName(""); // NOI18N
        LocalHostLabel.setPreferredSize(new java.awt.Dimension(76, 20));

        LocalPortLabel.setText("Local Port:");
        LocalPortLabel.setMaximumSize(new java.awt.Dimension(76, 20));
        LocalPortLabel.setMinimumSize(new java.awt.Dimension(76, 20));
        LocalPortLabel.setPreferredSize(new java.awt.Dimension(76, 20));
        LocalPortLabel.setRequestFocusEnabled(false);

        LoaclPortTextField.setText("80");
        LoaclPortTextField.setPreferredSize(new java.awt.Dimension(29, 27));
        LoaclPortTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoaclPortTextFieldActionPerformed(evt);
            }
        });

        String[] items = new String[]{"automatic", "application/octect-stream::any", "application/acad::dwg", "application/arj::arj", "application/astound::asn", "application/clariscad::ccad", "application/drafting::drw", "application/dxf::dxf", "application/hta::hta", "application/i-deas::unv", "application/iges::igs", "application/java-archive::jar", "application/mac-binhex40::hqx", "application/msaccess::mdb", "application/msexcel::xlw", "application/mspowerpoint::ppt", "application/msproject::mpp", "application/msword::w6w", "application/mswrite::wri", "application/octet-stream::bin", "application/oda::oda", "application/pdf::pdf", "application/postscript::ps", "application/pro_eng::prt", "application/rtf::rtf", "application/set::set", "application/sla::stl", "application/solids::sol", "application/STEP::stp", "application/vda::vda", "application/vnd.openxmlformats-officedocument.wordprocessingml.document::docx", "application/vnd.ms-word.document.macroEnabled.12::docm", "application/vnd.openxmlformats-officedocument.wordprocessingml.template::dotx", "application/vnd.ms-word.template.macroEnabled.12::dotm", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet::xlsx", "application/vnd.ms-excel.sheet.macroEnabled.12::xlsm", "application/vnd.openxmlformats-officedocument.spreadsheetml.template::xltx", "application/vnd.ms-excel.template.macroEnabled.12::xltm", "application/vnd.ms-excel.sheet.binary.macroEnabled.12::xlsb", "application/vnd.ms-excel.addin.macroEnabled.12::xlam", "application/vnd.openxmlformats-officedocument.presentationml.presentation::pptx", "application/vnd.ms-powerpoint.presentation.macroEnabled.12::pptm", "application/vnd.openxmlformats-officedocument.presentationml.slideshow::ppsx", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12::ppsm", "application/vnd.openxmlformats-officedocument.presentationml.template::potx", "application/vnd.ms-powerpoint.template.macroEnabled.12::potm", "application/vnd.ms-powerpoint.addin.macroEnabled.12::ppam", "application/vnd.openxmlformats-officedocument.presentationml.slide::sldx", "application/vnd.ms-powerpoint.slide.macroEnabled.12::sldm", "application/msonenote::one", "application/msonenote::onetoc2", "application/msonenote::onetmp", "application/msonenote::onepkg", "application/vnd.ms-officetheme::thmx", "application/x-bcpio::bcpio", "application/x-cpio::cpio", "application/x-csh::csh", "application/x-director::dxr", "application/x-dvi::dvi", "application/x-dwf::dwf", "application/x-gtar::gtar", "application/x-gzip::gzip", "application/x-hdf::hdf", "application/x-javascript::js", "application/x-latex::latex", "application/x-macbinary::bin", "application/x-midi::mid", "application/x-mif::mif", "application/x-netcdf::nc", "application/x-sh::sh", "application/x-shar::shar", "application/x-shockwave-flash::swf", "application/x-stuffit::sit", "application/x-sv4cpio::sv4cpio", "application/x-sv4crc::sv4crc", "application/x-tar::tar", "application/x-tcl::tcl", "application/x-tex::tex", "application/x-texinfo::texinfo", "application/x-troff::tr", "application/x-troff-man::man", "application/x-troff-me::me", "application/x-troff-ms::ms", "application/x-ustar::ustar", "application/x-wais-source::src", "application/x-winhelp::hlp", "application/zip::zip", "audio/basic::snd", "audio/midi::midi", "audio/x-aiff::aiff", "audio/x-mpeg::mp3", "audio/x-pn-realaudio::ram", "audio/x-pn-realaudio-plugin::rpm", "audio/x-voice::voc", "audio/x-wav::wav", "image/bmp::bmp", "image/gif::gif", "image/ief::ief", "image/jpeg::jpg", "image/pict::pict", "image/png::png", "image/tiff::tiff", "image/x-cmu-raster::ras", "image/x-portable-anymap::pnm", "image/x-portable-bitmap::pbm", "image/x-portable-graymap::pgm", "image/x-portable-pixmap::ppm", "image/x-rgb::rgb", "image/x-xbitmap::xbm", "image/x-xpixmap::xpm", "image/x-xwindowdump::xwd", "multipart/x-gzip::gzip", "multipart/x-zip::zip", "text/html::html", "text/plain::txt", "text/richtext::rtx", "text/tab-separated-values::tsv", "text/x-setext::etx", "text/x-sgml::sgml", "video/mpeg::mpg", "video/msvideo::avi", "video/quicktime::qt", "video/vdo::vdo", "video/vivo::vivo", "video/x-sgi-movie::movie", "x-conference/x-cooltalk::ice", "x-world/x-svr::svr", "x-world/x-vrml::wrl", "x-world/x-vrt::vrt", "other"};
        MimeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(items));
        MimeComboBox.setName(""); // NOI18N
        MimeComboBox.setPreferredSize(new java.awt.Dimension(42, 27));
        MimeComboBox.setRequestFocusEnabled(false);
        MimeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MimeComboBoxActionPerformed(evt);
            }
        });

        MimeLabel.setText("Mime  Type:");
        MimeLabel.setMaximumSize(new java.awt.Dimension(76, 20));
        MimeLabel.setMinimumSize(new java.awt.Dimension(76, 20));
        MimeLabel.setName(""); // NOI18N
        MimeLabel.setPreferredSize(new java.awt.Dimension(76, 20));

        jButton1.setText("Launch");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Help");

        FileTextField.setPreferredSize(new java.awt.Dimension(29, 27));
        FileTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileTextFieldActionPerformed(evt);
            }
        });

        LocalHostTextField.setText("127.0.0.1");
        LocalHostTextField.setPreferredSize(new java.awt.Dimension(72, 27));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(fileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(MimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                        .addComponent(LocalUriLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(LocalHostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(LocalPortLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LoaclPortTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LocalUriTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(MimeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LocalHostTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, 0)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(FileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LocalUriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LocalUriTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LocalHostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LocalHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoaclPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LocalPortLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void MimeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MimeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MimeComboBoxActionPerformed

    private void LoaclPortTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoaclPortTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoaclPortTextFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void FileTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FileTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FileTextField;
    private javax.swing.JTextField LoaclPortTextField;
    private javax.swing.JLabel LocalHostLabel;
    private javax.swing.JTextField LocalHostTextField;
    private javax.swing.JLabel LocalPortLabel;
    private javax.swing.JLabel LocalUriLabel;
    private javax.swing.JTextField LocalUriTextField;
    private javax.swing.JComboBox<String> MimeComboBox;
    private javax.swing.JLabel MimeLabel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
