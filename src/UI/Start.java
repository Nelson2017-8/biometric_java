/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import CrearServicioWindows.CrearServicio;
import Helper.Utils;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import Helper.ErrorHandler;
import java.awt.AWTException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Mauricio Herrera
 */
public class Start {

    public static long timestamp = (new Date().getTime() / 1000);
    public static String srn;
    public static String indexUrl;
    public static Timer timer = new Timer();
    public static String className = Start.class.getSimpleName();
    public static StackTraceElement[] stackTraceElements = null;
    public static String nameApp = "PluginBiometricoV3.jar";

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        try {
            Properties props = new Properties();
            props.put("logoString", "M-Systems");
            AcrylLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("error confiig form");
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "Error de configuración del formulario", ErrorHandler.lineCode(stackTraceElements) );
        }

        File archivo = new File("src/DB/Config.db");
        if (!archivo.exists()) {
            ConfigForm cf = new ConfigForm();
            cf.setLocationRelativeTo(null);
            cf.setVisible(true);
            return;
        }

        if (!Utils.isServicesAdd()) {
            /**File f = new File("PluginBiometricoV3.exe");
            String ruta = f.getAbsolutePath();**/
            
            Path path = Paths.get(nameApp);
            String ruta = path.toAbsolutePath().toString();
            CrearServicio.addServicesOnWindows(nameApp, "", ruta);
            Utils.addServiceConfig();
        }

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                HabilitarLector hbs = null;
                try {
                    srn = Utils.getKeyConfig("uniqueId");
                    System.out.println("srn " + srn);
                    hbs = new HabilitarLector();
                    timestamp = hbs.sendGet(timestamp, srn);
                } catch (AWTException | IOException e) {
                    System.out.println("Error habilitando el sensor " + e.getMessage());
                    stackTraceElements = Thread.currentThread().getStackTrace();
                    ErrorHandler.log("error", "Error habilitando el sensor " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements) );
                } catch (KeyManagementException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    hbs = null;
                }
            }
        };
        timer.schedule(tarea, 0, 4000);
        TrayClass.show();

    }
}
