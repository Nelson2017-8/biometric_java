/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import FIngerUtils.CapturarHuella;
import FIngerUtils.GetCapturarHuella;
import FIngerUtils.GetLecturaHuella;
import FIngerUtils.LecturaHuella;
import FIngerUtils.MyTrustManager;
import Helper.ErrorHandler;
import Helper.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 *
 * @author Mauricio Herrera
 */
public class HabilitarLector {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static String SERVER_PATH;
    private static CapturarHuella frmCapturaHuella;
    private static LecturaHuella frmLecturaHuella;
    public static StackTraceElement[] stackTraceElements = null;

    public HabilitarLector() throws KeyManagementException, NoSuchAlgorithmException {
        try {
            SERVER_PATH = Utils.getKeyConfig("urlHabSensor");
            TrustManager[] trustAllCerts = new TrustManager[]{new MyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (IOException e) {
            System.out.println("error " + e);
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "error " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));
        }

    }

    public long sendGet(long d, String srn) throws UnsupportedEncodingException, MalformedURLException, IOException, AWTException {
        //String opc = "reintentar";
        long timestamp = d;
        System.out.println("d = " + d);
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", "d = " + d, ErrorHandler.lineCode(stackTraceElements));
        StringBuilder stringBuilder = new StringBuilder(SERVER_PATH);
        stringBuilder.append("?timestamp=");
        stringBuilder.append(URLEncoder.encode(""  + d, "UTF-8"));
        stringBuilder.append("&token=").append(srn);
        stringBuilder.append("&_=").append(System.currentTimeMillis());

        System.out.println(stringBuilder.toString());
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", stringBuilder.toString(), ErrorHandler.lineCode(stackTraceElements));

        URL obj = new URL(stringBuilder.toString());

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Charset", "UTF-8");
        System.out.println("Response Message Maurcio : " + con.getResponseMessage());
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", "Response : " + con.getResponseMessage(), ErrorHandler.lineCode(stackTraceElements));
        int responseCode = con.getResponseCode();

        StringBuilder respuesta;

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        respuesta = new StringBuilder();
        while ((line = in.readLine()) != null) {
            respuesta.append(line);
        }
        con.disconnect();

        if (responseCode == 200) {
            System.err.println("Date = " + new Date());
            JsonParser parser = new JsonParser();
            JsonObject objJson = parser.parse(respuesta.toString()).getAsJsonObject();
            timestamp = objJson.get("fecha_creacion").getAsLong();
            //opc = objJson.get("opc").getAsString();

            switch (objJson.get("opc").getAsString()) {
                case "capturar":
                    
                    stackTraceElements = Thread.currentThread().getStackTrace();
                    ErrorHandler.log("info", "Capturando huella", ErrorHandler.lineCode(stackTraceElements));
                    frmLecturaHuella = GetLecturaHuella.getLecturarHuella();
                    frmLecturaHuella.stop();
                    frmLecturaHuella.dispose();
                    frmLecturaHuella = null;
                    //                if (frmCapturaHuella == null) {
                    frmCapturaHuella = GetCapturarHuella.getCapturarHuella();
                    frmCapturaHuella.stop();
                    frmCapturaHuella.Iniciar();
                    frmCapturaHuella.start();
//                }
                    break;
                case "leer":
                    System.out.println("leyendo huella");
                    stackTraceElements = Thread.currentThread().getStackTrace();
                    ErrorHandler.log("info", "leyendo huella", ErrorHandler.lineCode(stackTraceElements));
                    frmCapturaHuella = GetCapturarHuella.getCapturarHuella();
                    frmCapturaHuella.stop();
                    frmCapturaHuella.dispose();
                    frmCapturaHuella = null;
                    //                if (frmLecturaHuella == null) {
                    frmLecturaHuella = GetLecturaHuella.getLecturarHuella();
                    frmLecturaHuella.stop();
                    frmLecturaHuella.Iniciar();
                    frmLecturaHuella.start();
//                }
                    break;
                default:
                    try {
                        frmCapturaHuella.stop();
                        frmLecturaHuella.stop();
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("info", "Se detuvo el lector", ErrorHandler.lineCode(stackTraceElements));
                    } catch (Exception e) {
                        stackTraceElements = Thread.currentThread().getStackTrace();
                        ErrorHandler.log("error", "Error al detener el lector: " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));
                    }   
                    break;
            }
            
        }else{
            try {
                frmCapturaHuella.stop();
                frmLecturaHuella.stop();
                stackTraceElements = Thread.currentThread().getStackTrace();
                ErrorHandler.log("info", "Se detuvo el lector", ErrorHandler.lineCode(stackTraceElements));
            } catch (Exception e) {
                stackTraceElements = Thread.currentThread().getStackTrace();
                ErrorHandler.log("error", "Error al detener el lector: " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));                    
            }
        }
        return timestamp;

    }

}
