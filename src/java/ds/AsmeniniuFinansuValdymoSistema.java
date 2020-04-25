package ds;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/*
Pgr funkcijos 

    web serviso veikimas (galima kreiptis ir gauti atsakymą). [1] +
    duomenų pateikimas json formatu. [1] +
    parametrų perdavimas į serverį. [1] +
    vieno įrašo grąžinimas. [1] +
    įrašų sąrašo grąžinimas. [1] +
    4LD panaudojimas sistemos funkcionalumo perkėlimui į web. [5] + // jeigu reikalingas full tai -

*/
public class AsmeniniuFinansuValdymoSistema implements Serializable {

    private ArrayList<Kategorija> kategorijos = new ArrayList();
  //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); per daug parse date erroru ;'(
    Connection conn;
    Statement stmt;

    public boolean pridetiKategorija(String pav, String aprasymas) {
        try {
            this.prisijungtiPrieDB();
            String uzklausa = "INSERT INTO test.ps (KATEGORIJOS, DESCR) VALUES ('" + pav + "', '" + aprasymas + "')";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addPajamos(double suma, String dat, String pav, String kat) {
        Kategorija k = rastiKategorijaPagalPavadinima(kat);
        if (k == null) {
            return false;
        } else {
            try {
                this.prisijungtiPrieDB();
                String uzklausa = "INSERT INTO test.ps (KATEGORIJOS, KOMENTARAS, PAJAMOS, DATAP) \n"
                        + "VALUES ('" + kat + "', '" + pav + "', " + suma + ", '" + dat + "', '-')";
                stmt.execute(uzklausa);
                Pajamos p = new Pajamos(suma, k, pav, dat);
                k.getJudejimas().add(p);

                this.atsijungtiNuoDB();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public Kategorija rastiKategorijaPagalPavadinima(String pav) {
        gautiKategSarasa();
        for (Kategorija k : kategorijos) {
            if (k.getPavadinimas().equals(pav)) {
                return k;
            }
        }
        return null;
    }

    public boolean salintiKategorija(String kategorijosPavadinimas) {
        try {
            this.prisijungtiPrieDB();
            String uzklausa = "DELETE FROM test.ps  WHERE KATEGORIJOS = '" + kategorijosPavadinimas + "'";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean keistiKategPavad(String name, String newname) {
        try {
            this.prisijungtiPrieDB();
            String uzklausa = "UPDATE test.ps SET KATEGORIJOS = '" + newname + "'  WHERE KATEGORIJOS = '" + name + "'";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addIslaidos(double suma, String data, String pav, String kat, String cekis) {
        Kategorija k = rastiKategorijaPagalPavadinima(kat);
        if (k == null) {
            return false;
        } else {
            try {
                this.prisijungtiPrieDB();                    //String dat = simpleDateFormat.format(data);
                String uzklausa = "INSERT INTO test.ps (KATEGORIJOS, DESCR, ISLAIDOS , DATA, CEKIS) \n"
                        + "VALUES ('" + kat + "', '" + pav + "', " + suma + ", '" + data + "', '" + cekis + "')";
                stmt.execute(uzklausa);
                Islaidos p = new Islaidos(suma, k, pav, data, cekis);
                k.getJudejimas().add(p);
                this.atsijungtiNuoDB();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public Kategorija gautita(String kat) { // gauti 1 irasa pagal
        Kategorija cat = null;
        AsmeniniuFinansuValdymoSistema a = new AsmeniniuFinansuValdymoSistema();
        try {
            prisijungtiPrieDB();
            String sql = "SELECT * FROM test.ps WHERE KATEGORIJOS =" + kat;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String pav = rs.getString("KATEGORIJOS");
                String apr = rs.getString("DESCR");
                Kategorija naujas = new Kategorija(pav, apr);
                cat = naujas;
                break;
            }
            rs.close();
            atsijungtiNuoDB();
        } catch (Exception e) {
            System.out.println("klaida gaunant kategorijas: ");
            e.printStackTrace();
        }
        return cat;
    }

    public ArrayList<Kategorija> gautiKategSarasa() { // gauti sarasa
        this.kategorijos = new ArrayList();
        try {
            prisijungtiPrieDB();
            String sql = "SELECT * FROM test.ps";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String pav = rs.getString("KATEGORIJOS");
                String apr = rs.getString("DESCR");
                // TIK SARASA KATEGORIJA NE VISA! ?
                kategorijos.add(new Kategorija(pav, apr));
            }
            rs.close();
            atsijungtiNuoDB();
        } catch (Exception e) {
            System.out.println("klaida gaunant kategorijas: ");
            e.printStackTrace();
        }
        return kategorijos;
    }

    public void prisijungtiPrieDB() { //
        try {
            Class.forName("com.mysql.jdbc.Driver"); // myslq+/derby-
            String DB_URL = "jdbc:mysql://localhost:3306/test";
            String USER = "root";
            String PASS = "";
            kategorijos.clear();
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("prisijungta");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void atsijungtiNuoDB() {
        if (stmt != null && conn != null) {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
