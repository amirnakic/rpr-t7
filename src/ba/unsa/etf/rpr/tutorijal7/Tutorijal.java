package ba.unsa.etf.rpr.tutorijal7;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove() throws ArrayStoreException {
        ArrayList<Grad> rezultat = new ArrayList<>();
        Scanner ulaz;
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne može otvoriti.");
            return rezultat;
        }
        try {
            while (ulaz.hasNextLine()) {
                String tmp = ulaz.nextLine();
                String grad = "";
                int velicina = 0;
                for (int i = 0; i < tmp.length(); i++)
                    if (tmp.charAt(i) == ',') velicina++;
                double[] temperature = new double[velicina];
                int brojacZareza = 0, indeks = 0, pocetakProslog = 0;
                for (int i = 0; i < tmp.length(); i++) {
                    if (tmp.charAt(i) == ',' && brojacZareza == 0) {
                        grad = tmp.substring(0, i);
                        pocetakProslog = i + 1;
                        brojacZareza++;
                    } else if (tmp.charAt(i) == ',') {
                        if (brojacZareza == 1001) throw new ArrayStoreException();
                        temperature[indeks] = Double.parseDouble(tmp.substring(pocetakProslog, i));
                        pocetakProslog = i + 1;
                        brojacZareza++;
                        indeks++;
                    } else if (i == tmp.length() - 1) {
                        if (brojacZareza == 1001) throw new ArrayStoreException();
                        temperature[indeks] = Double.parseDouble(tmp.substring(pocetakProslog, i + 1));
                    }
                }
                Grad g = new Grad(grad, 0, temperature);
                rezultat.add(g);
            }
        } catch (Exception e) {
            System.out.println("Greška pri čitanju datoteke.");
            System.out.println("Greška: " + e);
        } finally {
            ulaz.close();
        }
        return rezultat;
    }

    public static UN ucitajXml(ArrayList<Grad> gradovi) {
        UN un = null;
        try {
            XMLDecoder ulaz = new XMLDecoder(new FileInputStream("drzave.xml"));
            un = (UN) ulaz.readObject();
            ulaz.close();
        } catch (Exception e) {
            System.out.println("Greška: " + e);
        }
        for (Drzava d : un.getDrzave())
            for (int i = 0; i < gradovi.size(); i++)
                if (gradovi.get(i).getNaziv().equals(d.getGlavniGrad().getNaziv()))
                    d.getGlavniGrad().setTemperature(gradovi.get(i).getTemperature());
        return un;
    }

    public static void zapisiXml(UN un) {
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("un.xml"));
            izlaz.writeObject(un);
            izlaz.close();
        }
        catch(Exception e) {
            System.out.println("Greška: " + e);
        }
    }

    public static void main(String[] args) {
    }
}
