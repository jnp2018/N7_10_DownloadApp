/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptinhmang;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dangn
 */
public class Download{
    long star,endl,current;
    private URL url;
    private String fileName;
    private boolean dowload;
    public static int SIZE = 1;
    public static int BUFFER_SIZE = 4096;
    public static boolean START = true;
    public static boolean PAUSE = false;
    private RandomAccessFile file = null;
    private BufferedInputStream in =null;
    private HttpURLConnection connection=null;
    private long i = 0;
    private int id = 1;
    public static int k = 1;
    public Download(long star, long endl, URL url,String fileName) throws FileNotFoundException {
        this.star = star;
        this.endl = endl;
        this.url = url;
        this.fileName = fileName;
        current = star;
        this.file = new RandomAccessFile(fileName,"rw");
        k++;
        id = k;
    }

    public long getCurrent() {
        return (current-star);
    }

    public void startDownload(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(current<endl){
                    Run(current,endl );
                }
               
            }
        }).start();
    }
    public void Run(long start,long endl){
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            String range = start + "-"+endl;
            connection.setRequestProperty("Range","bytes="+ range);
            in= new BufferedInputStream(connection.getInputStream());
            byte data[] = new byte[BUFFER_SIZE];
            int numRead;
            file.seek(start);
            while((numRead = in.read(data,0,BUFFER_SIZE))!=-1&&dowload!=PAUSE){
                file.write(data,0,numRead);
                current = current + numRead;
            }
            
        } catch (IOException ex) {
            startDownload();
        }finally {
            if(current == endl){
                if (file != null) {
                    try {
                        file.close();
                    } catch (IOException ex) {

                    }

                }
            }
            if (in != null) {
              try {
                in.close();
             } catch (IOException e) {}
             }
        }
    }
    public void start(){
        dowload = START;
        startDownload();
    }
    public void pause(){
        dowload = PAUSE;
    }
    
    /////////
    public static int getSIZE() {
        return SIZE;
    }

    public static void setBUFFER_SIZE(int BUFFER_SIZE) {
        Download.BUFFER_SIZE = BUFFER_SIZE;
    }
    
    public static void setSIZE(int SIZE) {
        Download.SIZE = SIZE;
    }
}
