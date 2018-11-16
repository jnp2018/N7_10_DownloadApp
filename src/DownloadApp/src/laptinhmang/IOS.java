/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptinhmang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dangn
 */
public class IOS {
    //private ArrayList<LichSu> listLS;
    private String file = "lichsu.txt";
    public IOS() {
        
    }
    public ArrayList<LichSu> readList(){
        ArrayList<LichSu> listLS = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(file));
            ObjectInputStream ois = new ObjectInputStream(fis);
            listLS = (ArrayList<LichSu>) ois.readObject();
            ois.close();
            fis.close();
            
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        } catch (ClassNotFoundException ex) {
        }
        
        return listLS;
    }
    public void writeList(ArrayList<LichSu> listLS){
        try {
            FileOutputStream fos = new FileOutputStream(new File(file));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listLS);
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
        
    }
}
