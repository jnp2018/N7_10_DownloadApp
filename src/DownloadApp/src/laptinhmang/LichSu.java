/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptinhmang;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author dangn
 */
public class LichSu implements Serializable{
    private String DateTime;
    private String fileName;
    private String linkFile;

    public LichSu(String DateTime, String fileName, String linkFile) {
        this.DateTime = DateTime;
        this.fileName = fileName;
        this.linkFile = linkFile;
    }

    public LichSu() {
    }

    public LichSu(String fileName, String linkFile) {
        this.DateTime = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy").format(Calendar.getInstance().getTime());
        this.fileName = fileName;
        this.linkFile = linkFile;
    }
    

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String DateTime) {
        this.DateTime = DateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLinkFile() {
        return linkFile;
    }

    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
    }
    
}
