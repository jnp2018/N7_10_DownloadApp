/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptinhmang;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dangn
 */
public class ControllDownload {
    private String link;
    private ArrayList<Download> listDownloads;
    private long durationThread;
    private long current;
    private long before;
    private long size;
    private String fileName;
    private int quantityThread;
    private int speed;
    private boolean DownloadStatus;
    public static boolean DownladStart = true;
    public static boolean DownloadPause = false;
    public ControllDownload(String link) {
        this.link = link;
        listDownloads = new ArrayList<>();
        DownloadStatus = DownloadPause;

    }

    public ControllDownload() {
        listDownloads = new ArrayList<>();
        this.before = 0;
        this.current = 0;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        try {
            this.before = 0;
            this.current = 0;
            this.link = link;
            setSize();
            setFileName();
            setQuantityThread();
            setDurationThread();
            setThreadDownload();
        } catch (IOException ex) {
            Logger.getLogger(ControllDownload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getDurationThread() {
        return durationThread;
    }

    public void setDurationThread() {
        this.durationThread = durationThread = size / quantityThread;
    }
    
    public void setThreadDownload(){
        listDownloads.clear();
        try {
            long start = 0, end = 0;
            for (int i = 0; i < quantityThread; i++) {
                start = i * durationThread;
                end = (i + 1) * durationThread;
                if (i == quantityThread - 1) {
                    end = size;
                }
                Download download = new Download(start, end, new URL(link), fileName);
                listDownloads.add(download);
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllDownload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public long getCurrent() {
        current = 0;
        for (int i = 0; i < listDownloads.size(); i++) {
               current = current + listDownloads.get(i).getCurrent();
        }
        return current;
    }
    
    public String infoDownload(){
        String info = "";
        for (int i = 0; i < listDownloads.size(); i++) {
            info = info + String.format("Thread%d : %d / %d kb \n",(i+1),(listDownloads.get(i).getCurrent())/1024,durationThread/1024);  
        }
        return info;
    }
    public void start() {
        if(!listDownloads.isEmpty()){
            DownloadStatus = DownladStart;
            for (Download download : listDownloads) {
                download.start();
            }
            
        }
    }

    public void pause() {
        if(!listDownloads.isEmpty()){
            DownloadStatus = DownloadPause;
            for (Download download : listDownloads) {
                download.pause();
            }
        }
    }

    public String getFileName() {
        return fileName;
    }
    
    public void setFileName() throws IOException {
        String raw = "";
        fileName = "";
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            raw = connection.getHeaderField("Content-Disposition");
            System.out.println(raw);
            int i = 0;
            boolean kt = false;
            if (raw != null) {

                fileName = raw.split("filename=")[1].trim();
                if (fileName.indexOf(';') != -1) {
                    fileName = fileName.split(";")[0].trim();
                }
                if (fileName.indexOf('"') != -1) {
                    fileName = fileName.replace('"', ' ').trim();
                }
            } else {

                fileName = link.substring(link.lastIndexOf('/') + 1);
                System.out.println(fileName);
                if(fileName.indexOf(".")==-1){
                    fileName = "abc.xyz";
                }
                if(fileName.indexOf("mp4")!=-1){
                    fileName = fileName.substring(0,fileName.indexOf("mp4")+3);
                }
                
            }
            connection.disconnect();
            System.out.println(fileName);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setSize() throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        if (connection.getResponseCode() != 200) {
            System.out.println("Error return code != 200");
        }
        size = connection.getContentLength();
        connection.disconnect();
    }

    public void setQuantityThread(int quantityThread) {
        this.quantityThread = quantityThread;
    }
    public void setQuantityThread() {
        quantityThread = (int) (size/(10280*1028));
        quantityThread = (quantityThread>16)?16:quantityThread;
        quantityThread = (quantityThread<1)?1:quantityThread;
    }

    public long getSize() {
        return size;
    }

    public int getQuantityThread() {
        return quantityThread;
    }
    public int getSpeed(int sleep){
        speed=0;
        getCurrent();
        speed = (int)((current - before)*(1000/sleep));
        before = current;
        return speed/1028;
    }
    public String getTIme(){
        String time="";
        if(speed==0) return "0"; 
        int seconds = (int)(size - current) / speed;
        int h = seconds/3600;
        int m = (seconds%3600)/60;
        int s = (seconds%3600)%60;
        if(h==0){
            if(s < 10) time = time+m+" : " + "0"+s;
            else time = time + m +" : " + s;
        }
        else{
            if(m<0){
                if(s < 10) time = time + h +" : 0" + m +" : " + "0"+s;
                else time = time  +h +" : 0" + m +" : " + s;
            }
            else{
                if(s < 10) time = time + h +" : " + m +" : " + "0"+s;
                else time = time  +h +" : " + m +" : " + s;
            }
        }
   
        
        return time;
    }

    public boolean getDownloadStatus() {
        return DownloadStatus;
    }
    
}
