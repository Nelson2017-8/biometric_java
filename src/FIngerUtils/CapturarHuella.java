/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FIngerUtils;

import Helper.ErrorHandler;
import Helper.Utils;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.google.gson.Gson;
import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mauricio Herrera
 */
public final class CapturarHuella extends javax.swing.JFrame {

    int longitudBytes;
    // objetos propios de las librerias del lector
    private final DPFPCapture lector = DPFPGlobal.getCaptureFactory().createCapture();
    private final DPFPEnrollment reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPTemplate template;
    private static final String TEMPLATE_PROPERTY = "template";
    public DPFPFeatureSet featuresInscription;
    private finger_temp fingerTemp;
    private Image imageHuella;
    private Utils u;
    private String texto;
    private String statusCapture;
    public static StackTraceElement[] stackTraceElements = null;

    public CapturarHuella() throws AWTException {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Fingerprint.png")).getImage());
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        Robot r = new Robot();
        int tamX = getWidth();
        int tamY = getHeight();
        int maxX = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int maxY = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        r.mouseMove(maxX + 250 - tamX, maxY - tamY + 10);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Notifiacion = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(256, 115));

        jPanel1.setBackground(new java.awt.Color(34, 41, 50));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(34, 41, 50), new java.awt.Color(51, 51, 51)));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("X");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(34, 41, 50), new java.awt.Color(51, 51, 51)));

        Notifiacion.setBackground(new java.awt.Color(34, 41, 50));
        Notifiacion.setColumns(20);
        Notifiacion.setForeground(new java.awt.Color(255, 255, 255));
        Notifiacion.setLineWrap(true);
        Notifiacion.setRows(3);
        jScrollPane1.setViewportView(Notifiacion);

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Sensor en modo captura.");
        jLabel1.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new CapturarHuella().setVisible(true);
                } catch (AWTException ex) {
                    System.out.println("Erro " + ex.getMessage());
                }
            }
        });
    }

    public void stop() {
        lector.stopCapture();
    }

    public void Iniciar() {
        lector.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        setTexto("Huella dactilar capturada.!");
                        ProcesarCaptura(e.getSample());
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "Huella dactilar capturada.!", ErrorHandler.lineCode(stackTraceElements));
                    } catch (IOException ex) {
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("error", "Error: " + ex.getMessage(), ErrorHandler.lineCode(stackTraceElements));
                    
                    } catch (Exception ex) {
                        //Logger.getLogger(CapturarHuella.class.getName()).log(Level.SEVERE, null, ex);
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("error", "Error:  " + ex.getMessage(), ErrorHandler.lineCode(stackTraceElements));
                    }
                });
            }
        });

        lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("Sensor activado o conectado.!");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "Sensor activado o conectado.!", ErrorHandler.lineCode(stackTraceElements));
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("Sensor desactivado o no conectado.!");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "Sensor desactivado o no conectado.!", ErrorHandler.lineCode(stackTraceElements));
                    }
                });
            }
        });

        lector.addSensorListener(new DPFPSensorAdapter() {

            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("Dedo colocado sobre el lector.!");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "Dedo colocado sobre el lector.!", ErrorHandler.lineCode(stackTraceElements));
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("Dedo retirado del lector.!");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "Dedo retirado del lector.!", ErrorHandler.lineCode(stackTraceElements));
                    }
                });
            }

        });

        lector.addErrorListener(new DPFPErrorAdapter() {

            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("Ocurrio un error con el lector.!");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "Ocurrio un error con el lector.!", ErrorHandler.lineCode(stackTraceElements));
                    }
                });
            }
        });

    }

    private void setTexto(String texto) {
        this.texto = texto;
        if (Notifiacion.getLineCount() > 3) {
            Notifiacion.setText("");
            Notifiacion.append(texto + "\n");
        } else {
            Notifiacion.append(texto + "\n");
        }
    }
    
    //public static ErrorHandler log = new ErrorHandler();

    private void ProcesarCaptura(DPFPSample sample) throws IOException, Exception {
        featuresInscription = extraerCaracteristicasHuella(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        if (featuresInscription != null) {
            try {
                System.out.println("estoy en TRY");
                stackTraceElements = Thread.currentThread().getStackTrace();
                ErrorHandler.log("warning", "estoy en TRY", ErrorHandler.lineCode(stackTraceElements));
                reclutador.addFeatures(featuresInscription);
                Image image = CrearImagenHuella(sample);
                setImageHuella(image);
                setStatusCapture();
                updateFingerWS();
            } catch (DPFPImageQualityException | IOException e) {
                System.out.println("Error: " + e.getMessage());
                stackTraceElements = Thread.currentThread().getStackTrace();
                ErrorHandler.log("error", "Error: " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));
            } finally {
                System.out.println("estoy en finally");
                stackTraceElements = Thread.currentThread().getStackTrace();
                ErrorHandler.log("info", "estoy en finally ", ErrorHandler.lineCode(stackTraceElements));
                setStatusCapture();
                System.out.println("estado = " + getStatusCapture());
                stackTraceElements = Thread.currentThread().getStackTrace();
                ErrorHandler.log("info", "estado = " + getStatusCapture(), ErrorHandler.lineCode(stackTraceElements));
                switch (reclutador.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:
                        System.out.println("TEMPLATE_STATUS_READY");
                        stop();
                        setTemplate(reclutador.getTemplate());
                        setTexto("La plantilla ha sido creada ya puede identificarla");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "La plantilla ha sido creada ya puede identificarla", ErrorHandler.lineCode(stackTraceElements));
                        setStatusCapture();
                        guardarHuella();
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        System.out.println("TEMPLATE_STATUS_FAILED");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("warning", "TEMPLATE_STATUS_FAILED", ErrorHandler.lineCode(stackTraceElements));
                        reclutador.clear();
                        stop();
                        setStatusCapture();
                        setTemplate(null);
                        System.out.println("La plantilla no pudo ser creada");
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("warning", "La plantilla no pudo ser creada", ErrorHandler.lineCode(stackTraceElements));
                        start();
                        break;
                }
            }
        }
    }

    private DPFPFeatureSet extraerCaracteristicasHuella(DPFPSample sample, DPFPDataPurpose dpfpDataPurpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, dpfpDataPurpose);
        } catch (DPFPImageQualityException e) {
            System.out.println("error generando caracteristicas: " + e.getMessage());
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "error generando caracteristicas: " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));
            return null;
        }
    }

    private Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    private void setImageHuella(Image image) {
        this.imageHuella = image;
    }

    private void updateFingerWS() throws IOException, Exception {
//        System.out.println("por aqui actualizando "+Base64.getEncoder().encodeToString(getU().getUniqueId().getBytes()));

        getFingerTemp();
        fingerTemp.setSerial(Utils.getKeyConfig("uniqueId"));
        fingerTemp.setImageHuella(getEncodeImage(getImageHuella()));
        fingerTemp.setTexto(getTexto());
        fingerTemp.setStatusPlantilla(getStatusCapture());
        fingerTemp.setOption("actualizar");
        int endFingerprint = reclutador.getFeaturesNeeded() == 0 ? 1 : 0;
        fingerTemp.setEndFingerprint(endFingerprint);
        String object = new Gson().toJson(fingerTemp);
//        System.out.println(object);
        fingerTemp.actualizarHuella(object);
        fingerTemp = null;
    }

    private finger_temp getFingerTemp() throws KeyManagementException, NoSuchAlgorithmException {
        if (fingerTemp == null) {
            fingerTemp = new finger_temp();
        }
        return fingerTemp;
    }

    private String getEncodeImage(Image imageHuella) {
        ImageIcon icon = new ImageIcon(imageHuella);
        BufferedImage image = new BufferedImage(450, 500, BufferedImage.TYPE_INT_RGB);
        byte[] imageInByte = null;
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(imageHuella, 0, 0, icon.getImageObserver());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        } catch (IOException e) {
            System.out.println("error al crear la imagen " + e.getMessage());
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "error al crear la imagen " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements) );
        }
        return Base64.getEncoder().encodeToString(imageInByte);
    }

    private Image getImageHuella() {
        return imageHuella;
    }

    private String getTexto() {
        return texto;
    }

    private String getStatusCapture() {
        return statusCapture;
    }

    private void setStatusCapture() {
        this.statusCapture = "Muestras Restantes: " + reclutador.getFeaturesNeeded();
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", "Muestras Restantes: " + reclutador.getFeaturesNeeded(), ErrorHandler.lineCode(stackTraceElements) );
    }

    private void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    private void guardarHuella() throws IOException, Exception {
//        System.out.println("guardando");
        getFingerTemp();
        String encodeString = Base64.getEncoder().encodeToString(template.serialize());
        fingerTemp.setSerial(Utils.getKeyConfig("uniqueId"));
        fingerTemp.setHuella(encodeString);
        fingerTemp.setImageHuella(getEncodeImage(getImageHuella()));
        fingerTemp.setTexto(getTexto());
        fingerTemp.setStatusPlantilla(getStatusCapture());
        String object = new Gson().toJson(fingerTemp);
        fingerTemp.asociarHuella(object);
        fingerTemp = null;
        stop();     
        GetCapturarHuella.setCapturarHuella();
//        this.dispose();
        System.out.println("cerrando form");
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", "cerrando form", ErrorHandler.lineCode(stackTraceElements) );
    }

    public void start() {
        lector.startCapture();
        setTexto("Utilizando el lector de huella dactilar");
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", "Utilizando el lector de huella dactilar", ErrorHandler.lineCode(stackTraceElements) );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Notifiacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
