package br.uefs.ecomp.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManipularArquivo {
    Map<String, Integer> map = new HashMap<>();
    Map<Integer, String> pam = new HashMap<>();
    
    public Map<String, Integer> lerDicSerializado() throws IOException, ClassNotFoundException{
        try {
            FileInputStream fis = new FileInputStream("dictionary.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (Map<String, Integer>) ois.readObject();
            ois.close();
            System.out.println("Dicionario serializado lido com sucesso!");
            return map;
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo serializado não encontrado, tentando ler dic...");
            return lerDic();
        }
    }
    
    public Map<Integer, String> lerPamSerializado() throws IOException, ClassNotFoundException{
        try {
            FileInputStream fis = new FileInputStream("pam.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            pam = (Map<Integer, String>) ois.readObject();
            ois.close();
            System.out.println("HashMap para comparar palavras de outros jogadores lido com sucesso!");
            return pam;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManipularArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private Map<String, Integer> lerDic() throws IOException{
        Map<String, Integer> map = new HashMap<>();
        Map<Integer, String> pam = new HashMap<>();
        
        BufferedReader read = null;
        int cont = 0;
        
        try {
            read = new BufferedReader(new FileReader("dictionary.dic"));
            String l;
            
            while ((l = read.readLine()) != null) {
                map.put(l, cont);
                pam.put(cont, l);
                cont++;
                System.out.println(l);
            }
            read.close();
            
            serializaDic(map);
            serializaPam(pam);
            
            return map;
            
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo txt não encontrado!");
            return null;
        }
    }
    
    private void serializaDic(Map<String, Integer> dic) throws IOException{
        try {
            FileOutputStream fos = new FileOutputStream("dictionary.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dic);
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManipularArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void serializaPam(Map<Integer, String> pam) throws IOException{
        try {
            FileOutputStream fos = new FileOutputStream("pam.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pam);
            oos.close();
        } catch (FileNotFoundException ex){
            Logger.getLogger(ManipularArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}