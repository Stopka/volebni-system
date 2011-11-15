/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LegalData.java
 *
 * Created on 10.5.2011, 0:24:55
 */

package cz.cvut.fel.mvod.gui;

import cz.cvut.fel.mvod.global.GlobalSettingsAndNotifier;

/**
 *
 * @author Murko
 */
public class LegalData extends javax.swing.JFrame {

    /** Creates new form LegalData */
    public LegalData() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(GlobalSettingsAndNotifier.singleton.messages.getString("legalDataLabel")); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText(" Copyright 2011 Radovan Murin\n\n Licensed under the Apache License, Version 2.0 (the \"License\");\n you may not use this file except in compliance with the License.\n You may obtain a copy of the License at\n\n     http://www.apache.org/licenses/LICENSE-2.0\n\n Unless required by applicable law or agreed to in writing, software\n distributed under the License is distributed on an \"AS IS\" BASIS,\n WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n See the License for the specific language governing permissions and\n limitations under the License.\n\n This software also contains the following licences:\n\n ----\n\n © 2010, Jakub Valenta\n\n Redistribution and use in source and binary forms, with or without modification,\n are permitted provided that the following conditions are met:\n Redistributions of source code must retain the above copyright notice,\n this list of conditions and the following disclaimer.\n Redistributions in binary form must reproduce the above copyright notice,\n this list of conditions and the following disclaimer in the documentation\n and/or other materials provided with the distribution.\n Neither the name of the Jakub Valenta\n nor the names of its contributors may be used to endorse or promote products\n derived from this software without specific prior written permission.\n \n This software is provided by the copyright holders and contributors “as is” and any\n express or implied warranties, including, but not limited to, the implied\n warranties of merchantability and fitness for a particular purpose are disclaimed.\n In no event shall the foundation or contributors be liable for any direct, indirect,\n incidental, special, exemplary, or consequential damages (including, but not limited to,\n procurement of substitute goods or services; loss of use, data, or profits; or business\n interruption) however caused and on any theory of liability, whether in contract, strict\n liability, or tort (including negligence or otherwise) arising in any way out of the use\n of this software, even if advised of the possibility of such damage.\n---------------------------------\n\n\nLEGAL NOTICE: This product includes software developed by the OpenSSL Project for use in the OpenSSL Toolkit. (http://www.openssl.org/)\n\n\n---------------------------------\n\n\nLEGAL NOTICE: This product includes images procured from http://openiconlibrary.sourceforge.net/downloads.html.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LegalData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}