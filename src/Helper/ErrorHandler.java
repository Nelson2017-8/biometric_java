/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;


import UI.OpenLog;
import java.awt.Window;
import java.io.ByteArrayOutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
/**
 *
 * @author Usuario
 */
public class ErrorHandler {
    public static String lineCode(StackTraceElement[] stackTraceElements){
        String lineOfCode = stackTraceElements[1].toString();
        
        return lineOfCode;
    }
    
    public static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());
    public static ByteArrayOutputStream baos = new ByteArrayOutputStream(); // Crear un ByteArrayOutputStream para almacenar los registros del log

    public static void log (String status, String msg, String lineOfCode){
        // Configurar el Logger
        LOGGER.setLevel(Level.ALL);
        

        // Crear un Handler que escriba los registros en el ByteArrayOutputStream
        Handler handler = new StreamHandler(baos, new SimpleFormatter());
        Handler consoleHandler = new ConsoleHandler(); //Estableceremos un manejador de errores
        LOGGER.addHandler(handler);
        LOGGER.addHandler(consoleHandler);


        if(null == status){
            LOGGER.info(msg);
        }else{
            // Registrar mensajes de registro
            String head = "\n File " + lineOfCode + "\n" + msg;
            switch (status) {
                case "warining":
                    LOGGER.warning(head);
                    break;
                case "severe":
                case "error":
                case "errors":
                    LOGGER.severe(head);
                    break;
                default:
                    LOGGER.info(head);
                    break;
            }
            
            handler.close();
            consoleHandler.close();
        }
    }
    
    public static String read(){

        return baos.toString();
    }
    

}
