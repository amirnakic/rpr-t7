package ba.unsa.etf.rpr.tutorijal7;

import java.io.FileNotFoundException;
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

        return rezultat;
    }

    public static void main(String[] args) {
        
    }
}
