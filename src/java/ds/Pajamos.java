package ds;

import java.io.Serializable;
import java.util.Date;


public class Pajamos extends PiniguJudejimas implements Serializable{
    
    public Pajamos(double suma, Kategorija kategorija, String aprasymas, String data) {
        super(suma, kategorija,aprasymas,data);
    }

    public double getSuma() {
        return suma;
    }
    @Override
    public String toString() {
        return "Pajamos";
    }
}
