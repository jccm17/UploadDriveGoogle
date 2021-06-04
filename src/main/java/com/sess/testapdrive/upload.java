/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sess.testapdrive;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import java.awt.Component;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import static org.mortbay.log.Log.debug;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author jccm1
 */
public class upload extends javax.swing.JFrame {

    CreateGoogleFile f = new CreateGoogleFile();
    FileInputStream fis;
    int longitudBytes;
    java.io.File file;
    private ImageIcon icon;//Imagen que mostraremos 
    private ImageIcon icono;//Imagen redimensionada 
    String namefile;
    private String ex;//Para mostrar extensión de archivo 
    private URL url;//Tomara el valor del textField 
    private Component c = this;//Para referenciar al frame 
    private URLConnection urlCon;//Para obtener datos de la conexión 
    private File googleFile;
    static FileInputStream fileInputStreamReader;

    /**
     * Creates new form upload
     */
    public upload() {
        initComponents();
        btn_post.setEnabled(false);
        btnupload.setEnabled(false);
        btn_view.setEnabled(false);
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    public void uploadPost(java.io.File file) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("https://api.imgbb.com/1/upload?expiration=600&key=71de2823969a5817bc07f2616ae39315");

            //FileBody bin = new FileBody(file);
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);
            
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addBinaryBody(namefile, file)
                    //.addPart("bin", bin)
                    .addPart("comment", comment)
                    .build();
            System.out.println("file data " + reqEntity.getContent());
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(upload.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpclient.close();
        }
    }

    private static File _createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException {

        File fileMetadata = new File();
        fileMetadata.setName(customFileName);

        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);
        //
        Drive driveService = GoogleDriveUtils.getDriveService();

        File file = driveService.files().create(fileMetadata, uploadStreamContent)
                .setFields("id, webContentLink, webViewLink, parents").execute();

        return file;
    }

    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, java.io.File uploadFile) throws IOException {

        //
        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnselect = new javax.swing.JButton();
        txt_nombre_img = new javax.swing.JLabel();
        btnupload = new javax.swing.JButton();
        LbImagen = new javax.swing.JLabel();
        btn_view = new javax.swing.JButton();
        btn_post = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Upload Images");

        jLabel1.setText("Test API DRIVE GOOGLE");

        btnselect.setBackground(new java.awt.Color(0, 255, 51));
        btnselect.setText("Seleccionar");
        btnselect.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 51), new java.awt.Color(0, 204, 102), new java.awt.Color(0, 255, 102), new java.awt.Color(0, 255, 51)));
        btnselect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnselectActionPerformed(evt);
            }
        });

        txt_nombre_img.setText("...................");

        btnupload.setBackground(new java.awt.Color(255, 255, 255));
        btnupload.setForeground(new java.awt.Color(153, 153, 153));
        btnupload.setText("Upload Drive Google");
        btnupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnuploadActionPerformed(evt);
            }
        });

        LbImagen.setText("IMAGE");
        LbImagen.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        LbImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.yellow, new java.awt.Color(0, 0, 255)));
        LbImagen.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        LbImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_view.setForeground(new java.awt.Color(153, 153, 153));
        btn_view.setText("Vista Previa link");
        btn_view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewActionPerformed(evt);
            }
        });

        btn_post.setText("Upload Post");
        btn_post.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_postActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre_img, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnselect, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_view, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnupload, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(57, 57, 57)
                        .addComponent(btn_post)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(LbImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(btnselect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nombre_img, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_post, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnupload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_view)
                .addGap(65, 65, 65))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnuploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuploadActionPerformed
        try {
            googleFile = createGoogleFile("1HGiRIKqCEAhzOuB5UFo3QNpmw8eD6Uh4", "image/jpeg", namefile, file);
            System.out.println("Created Google file!");
            JOptionPane.showMessageDialog(this, "Mensaje", "Archivo subido exitosamente", JOptionPane.INFORMATION_MESSAGE);
            LbImagen.setIcon(null);
            System.out.println("WebContentLink: " + googleFile.getWebContentLink());
            System.out.println("WebViewLink: " + googleFile.getWebViewLink());
            System.out.println("WebIconLink: " + googleFile.getThumbnailLink());
            System.out.println("Done!");
        } catch (IOException ex) {
            Logger.getLogger(upload.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnuploadActionPerformed

    private void btnselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnselectActionPerformed
        //Creamos nuestra variable archivo en la cual podremos usar todos los metodos de la clase jFileChooser

        JFileChooser archivo = new JFileChooser();
        //Si deseamos crear filtros para la selecion de archivos
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG)", "jpg", "jpeg");
        //Si deseas que se muestre primero los filtros usa la linea q esta abajo de esta.
        //archivo.setFileFilter(filtro);
        // Agregamos el Filtro pero cuidado se mostrara despues de todos los archivos
        archivo.addChoosableFileFilter(filtro);
        // Colocamos titulo a nuestra ventana de Seleccion
        archivo.setDialogTitle("Abrir Archivo");
        //Si deseamos que muestre una carpeta predetermina usa la siguiente linea
        java.io.File ruta = new java.io.File(System.getProperty("user.home"));
        //Le implementamos a nuestro ventana de seleccion
        archivo.setCurrentDirectory(ruta);
        //Abrimos nuestra Ventana de Selccion
        int ventana = archivo.showOpenDialog(null);
        //hacemos comparacion en caso de aprete el boton abrir
        if (ventana == JFileChooser.APPROVE_OPTION) {
            //Obtenemos la ruta de nuestra imagen seleccionada
            file = archivo.getSelectedFile();
            //Lo imprimimos en una caja de texto para ver su ruta
            txt_nombre_img.setText(String.valueOf(file));
            namefile = file.getName();
            //System.out.println(file);
            //de cierto modo necesitamos tener la imagen para ello debemos conocer la ruta de dicha imagen
            Image foto = getToolkit().getImage(txt_nombre_img.getText());
            //Le damos dimension a nuestro label que tendra la imagen
            foto = foto.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
            //Imprimimos la imagen en el label
            LbImagen.setIcon(new ImageIcon(foto));
            this.btnupload.setEnabled(true);
            this.btn_post.setEnabled(true);
            this.btn_view.setEnabled(true);
        }
    }//GEN-LAST:event_btnselectActionPerformed

    private void btn_viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewActionPerformed

        try {
            url = new URL(googleFile.getWebContentLink()); //Inicio de la variable url 
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));//Como demora ponemos cursor de espera
            try {
                urlCon = url.openConnection(); //Inicio de la conexión 

                if (urlCon.getContentType() != null) {//Si hay formato de imagen (No estoy seguro...)
                    ex = urlCon.getContentType();//Obtiene el tipo de imgen  por Ej.: image/jpg
                    ex = "." + ex.substring(ex.length() - 3, ex.length());//Cambiamos lo anterior por Ej.: por ".jpg"

                    if (ex.equals(".peg")) {//A lo gaucho nomás 
                        ex = ".jpg";
                    }
                    txt_nombre_img.setText("<html>"
                            + "Tamaño:  " + (urlCon.getContentLength() / 1024) + " Kb<br>"//Para saber el tamaño en KB
                            + "Tipo: imagen" + ex);
                    icon = new ImageIcon(url);//Iniciamos imagen en un ImageIcon y luego la
                    //redimensionamos al tamaño de la etiqueta 
                    icono = new ImageIcon(icon.getImage().getScaledInstance(LbImagen.getWidth(), LbImagen.getHeight(), Image.SCALE_DEFAULT));
                    LbImagen.setIcon(icono);;
                    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));//Una vez cargada ponemos cursor x defecto

                } else {//Si urlCon.getContentType()==null 
                    JOptionPane.showMessageDialog(c, "No hay Conexión!", "Error", 0);
                    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));//Ponemos cursor x defecto
                }

            } catch (IOException e) {
                debug(e.getMessage());
            }

        } catch (MalformedURLException e) {//Si la URL ingresada no corresponde con el formato URL
            if ("" != null) {
                JOptionPane.showMessageDialog(c, "Puede que la URL:n "
                        + "" + "n"
                        + "no sea correcta", "", 2);
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_btn_viewActionPerformed

    private void btn_postActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_postActionPerformed
        try {
            uploadPost(file);
        } catch (Exception ex) {
            Logger.getLogger(upload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_postActionPerformed

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
            java.util.logging.Logger.getLogger(upload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(upload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(upload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(upload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new upload().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LbImagen;
    private javax.swing.JButton btn_post;
    private javax.swing.JButton btn_view;
    private javax.swing.JButton btnselect;
    private javax.swing.JButton btnupload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel txt_nombre_img;
    // End of variables declaration//GEN-END:variables
}
