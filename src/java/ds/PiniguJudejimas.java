package ds;

import java.io.Serializable;
import java.util.Date; 

public class PiniguJudejimas implements Serializable, Comparable<PiniguJudejimas>{
     double suma;
     Kategorija kategorija;
     String aprasymas;
     String data;
     String cekioNr="-";

    public PiniguJudejimas(double suma, Kategorija kategorija, String aprasymas, String data) {
        this.suma = suma;
        this.kategorija = kategorija;
        this.aprasymas = aprasymas;
        this.data = data;
    }

    PiniguJudejimas(Double sum, String ks, String apr, String dat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public double getSuma(){
            return suma;
        }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public String getData() {
        return data;
    }
    
    /*@Override
    public String toString() {
        return "Pajamos{" + "suma= " + suma + ", aprasymas= " + aprasymas + ", data= " + data + '}';
    }*/
    
    @Override
    public String toString() {
        return "Pajamos";
    }
    public String getCekioNr() {
        return cekioNr;
    }
    @Override
    public int compareTo(PiniguJudejimas o){
        if(this.suma==o.suma){
            return o.aprasymas.compareTo(aprasymas);
        }else{
            return Double.compare(suma,o. suma);
        }
    }
    
}
