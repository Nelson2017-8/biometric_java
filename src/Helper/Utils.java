/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import DB.Conexion;
import UI.Start;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Mauricio Herrera
 */
public class Utils {

    public Utils() {
    }
    static String nameApp = Start.nameApp;
    
    public static StackTraceElement[] stackTraceElements = null;

    public static final boolean setKeyConfig(String urlIndexFile, String urlHabSensor, String urlRestApi, String uniqueId, String browser, String action) {
        boolean response = false;
        Conexion con = new Conexion();
        if (action.equals("add")) {
            con.createTable();
            response = con.insert(urlHabSensor, urlRestApi, uniqueId, browser);
        } else {
            response = con.updateConfig(uniqueId, browser);
        }
        con = null;
        return response;
    }

    public static final String getKeyConfig(String key) throws FileNotFoundException, IOException {
        String keyVaue = "";
        Conexion con = new Conexion();
        keyVaue = con.select(key);
        con = null;
        return keyVaue;
    }

    public static final boolean isServicesAdd() throws FileNotFoundException, IOException {
        boolean response = false;
        Conexion con = new Conexion();
        response = con.isServices();
        con = null;
        return response;
    }

    public static final boolean addServiceConfig() throws FileNotFoundException, IOException {
        boolean response = false;
        Conexion con = new Conexion();
        response = con.insertService();
        con = null;
        return response;
    }

    public static void restartApplication() throws IOException {
        try {
            String current = new java.io.File(".").getCanonicalPath();
            String nameapp = nameApp;
            File archivo = new File(current + "\\" + nameapp);
            if (!archivo.exists()) {
                nameapp = "PluginBiometricoV3.jar";
            }
            new ProcessBuilder("cmd", "/c start /min " + current + "\\" + nameapp + " ^& exit").start();
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Error reiniciando " + ex);
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "Error reiniciando: " + ex.getMessage(), ErrorHandler.lineCode(stackTraceElements));
        }
    }

    public static String getToken(String url) throws IOException{
        try {
            Document doc = Jsoup.connect(url).get();
            Element tokenElement = doc.getElementById("Token");
            String token = tokenElement.text();
            return token;
        } catch (IOException e) {
            System.err.println("error" + e.toString());
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "Error: " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));
        }
        
        return "";
    }
}
