/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FIngerUtils;

import Helper.ErrorHandler;
import Helper.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 *
 * @author Mauricio Herrera
 */
public class finger_temp {

    private String serial;
    private String huella;
    private String imageHuella;
    private String texto;
    private String statusPlantilla;
    private String documento;
    private String nombre;
    private String dedo;
    private String option;
    private int endFingerprint;

    private static final String USER_AGENT = "Mozilla/5.0";
    private static String SERVER_PATH;
    public static StackTraceElement[] stackTraceElements = null;
    
    public finger_temp() throws KeyManagementException, NoSuchAlgorithmException {
        try {
            SERVER_PATH = Utils.getKeyConfig("urlRestApi");
            
            TrustManager[] trustAllCerts = new TrustManager[]{new MyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (IOException e) {
            System.out.println("error " + e);
        }
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getHuella() {
        return huella;
    }

    public void setHuella(String huella) {
        this.huella = huella;
    }

    public String getImageHuella() {
        return imageHuella;
    }

    public void setImageHuella(String imageHuella) {
        this.imageHuella = imageHuella;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getStatusPlantilla() {
        return statusPlantilla;
    }

    public void setStatusPlantilla(String statusPlantilla) {
        this.statusPlantilla = statusPlantilla;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDedo() {
        return dedo;
    }

    public void setDedo(String dedo) {
        this.dedo = dedo;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean asociarHuella(String data) {
        boolean r = false;
        try {
                       
            
            URL url = new URL(SERVER_PATH + "?_=" + System.currentTimeMillis());
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("User-Agent", USER_AGENT);
            httpCon.setRequestProperty("Acept", "*/*");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");
            
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("info", "Asociando huella. \n url: "+ url +"\n data: " + data, ErrorHandler.lineCode(stackTraceElements));

            try (OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream())) {
                out.write(data);
            }

            BufferedReader response = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            String str = response.readLine();
            while (str != null) {
                System.err.println(str);
                str = response.readLine();
            }

            httpCon.disconnect();

            r = true;
        } catch (IOException e) {
            System.out.println("Error guardando Huella " + e.getMessage());
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "Error guardando Huella: " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));
        }
        return r;
    }

    public boolean actualizarHuella(String data) {
        boolean respuesta = false;
        try {
            URL url = new URL(SERVER_PATH + "?_=" + System.currentTimeMillis());
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("User-Agent", USER_AGENT);
            httpCon.setRequestProperty("Acept", "*/*");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("info", "Actualizando huella. \n url: "+ url +"\n data: " + data, ErrorHandler.lineCode(stackTraceElements));

            try (OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream())) {
                out.write(data);
            }

//            System.out.println("Mauricio");
            BufferedReader response = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

            String r = response.readLine();
            while (r != null) {
//                System.out.println(r);
                r = response.readLine();
            }

            httpCon.disconnect();

            respuesta = true;
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            stackTraceElements = Thread.currentThread().getStackTrace();
            ErrorHandler.log("error", "Error: " + e.getMessage(), ErrorHandler.lineCode(stackTraceElements));
        }
        return respuesta;
    }

    public String listaHuellas(String serial, int desde, int hasta) throws UnsupportedEncodingException, MalformedURLException, IOException {
        StringBuilder stb = new StringBuilder(SERVER_PATH);
        stb.append("?token=");
        stb.append(URLEncoder.encode(serial, "UTF-8"));
        stb.append("&desde=");
        stb.append(desde);
        stb.append("&hasta=");
        stb.append(hasta);
        stb.append("&_=");
        stb.append(System.currentTimeMillis());

        System.out.println("stb.toString() " + stb.toString());

        URL url = new URL(stb.toString());

        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

        httpCon.setRequestProperty("User-Agent", USER_AGENT);
        httpCon.setRequestProperty("Acept-Charset", "UTF-8");
        httpCon.setRequestProperty("Cache-Control", "no-cache");
        httpCon.setRequestMethod("GET");
        
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", "Listando huellas. \n url: "+ url +"\n serial: " + serial + "\n desde: " + desde + " \n hasta: "+hasta, ErrorHandler.lineCode(stackTraceElements));

        System.out.println("Response Code: " + httpCon.getResponseCode());
        stackTraceElements = Thread.currentThread().getStackTrace();
        ErrorHandler.log("info", "Response Code: " + httpCon.getResponseCode(), ErrorHandler.lineCode(stackTraceElements));
//        System.out.print("Response Code: " + httpCon.getResponseMessage());

        StringBuilder respuesta;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()))) {
            String linea;
            respuesta = new StringBuilder();

            while ((linea = in.readLine()) != null) {
                respuesta.append(linea);
            }
        }

        httpCon.disconnect();
//        System.exit(0);
        return respuesta.toString();

    }

    void setEndFingerprint(int i) {
        this.endFingerprint = i;
    }
    
    public int getEndFingerprint() {
        return this.endFingerprint;
    }

}
