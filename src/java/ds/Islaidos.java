package ds;

import java.io.Serializable;
import java.util.Date;

public class Islaidos extends PiniguJudejimas implements Serializable{
    private String cekioNr;
    

    public Islaidos(double suma, Kategorija kategorija, String aprasymas, String data, String cekis) {
        super(suma,kategorija,aprasymas,data);
        cekioNr = cekis;
    }
    
    public double getSuma(){
        return -suma;
    }

    public String getCekioNr() {
        return cekioNr;
    }
    
   /*@Override
    public String toString() {
        return "Islaidos{" + "suma= " + -suma + ", aprasymas= " + aprasymas + ", data= " + data + ", cekis= " + cekioNr + '}';
    }*/
    @Override
    public String toString() {
        return "Islaidos";
    }
   
}
