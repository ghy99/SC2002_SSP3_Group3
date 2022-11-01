package Movie;

import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

public class TicketCharges {
    private ArrayList<ArrayList<String>> priceByAge = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> priceByDay = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> priceByMovieDim = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> priceByCinemaType = new ArrayList<ArrayList<String>>();

    private Boolean blockbuster;

    public ArrayList<ArrayList<String>> returnAge() {
        return priceByAge;
    }
    public ArrayList<ArrayList<String>> returnDay() {
        return priceByDay;
    }
    public ArrayList<ArrayList<String>> returnDim() {
        return priceByMovieDim;
    }
    public ArrayList<ArrayList<String>> returnType() {
        return  priceByCinemaType;
    }
    public TicketCharges() throws IOException {
        ArrayList<String[][]> ticketPrices = new ArrayList<>();
        String filename = "TicketPrice.txt";
        ticketPrices = TextDB.readFromFile(filename, (TicketCharges) null);
        for (int i = 0; i < ticketPrices.size(); i++) {
            if (i == 0) {
                String[][] temp = ticketPrices.get(i);
                for (int j = 0; j < temp.length; j++) {
                    ArrayList<String> age = new ArrayList<String>();
                    age.add(temp[j][0]);
                    age.add(temp[j][1]);
                    setPriceByAge(age);
                }
            }
            else if (i == 1) {
                String[][] temp = ticketPrices.get(i);
                for (int j = 0; j < temp.length; j++) {
                    ArrayList<String> day = new ArrayList<String>();
                    day.add(temp[j][0]);
                    day.add(temp[j][1]);
                    setPriceByDay(day);
                }
            }
            else if (i == 2) {
                String[][] temp = ticketPrices.get(i);
                for (int j = 0; j < temp.length; j++) {
                    ArrayList<String> Dim = new ArrayList<String>();
                    Dim.add(temp[j][0]);
                    Dim.add(temp[j][1]);
                    setPriceByMovieDim(Dim);
                }
            }
            else if (i == 3) {
                String[][] temp = ticketPrices.get(i);
                for (int j = 0; j < temp.length; j++) {
                    ArrayList<String> Type = new ArrayList<String>();
                    Type.add(temp[j][0]);
                    Type.add(temp[j][1]);
                    setPriceByCinemaType(Type);
                }
            }
        }
    }
    public void printTicketCharges() {
        System.out.println("\n\tPrice category by age:");
        for (int i = 0; i < this.priceByAge.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\tBelow 21: $%.2f\n", Double.parseDouble(priceByAge.get(i).get(1)));
            }
            else if (i == 1) {
                System.out.printf("\t\tBelow 65: $%.2f\n", Double.parseDouble(priceByAge.get(i).get(1)));
            }
            else {
                System.out.printf("\t\tAbove 65: $%.2f\n", Double.parseDouble(priceByAge.get(i).get(1)));
            }
        }
        System.out.println("\n\tPrice category by day of the week:");
        for (int i = 0; i < this.priceByDay.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\tMonday - Friday: $%.2f\n", Double.parseDouble(priceByDay.get(i).get(1)));
            }
            else if (i == 1) {
                System.out.printf("\t\tSaturday - Sunday: $%.2f\n", Double.parseDouble(priceByDay.get(i).get(1)));
            }
            else if (i == 2) {
                System.out.printf("\t\tPublic Holiday: $%.2f\n", Double.parseDouble(priceByDay.get(i).get(1)));
            }
        }
        System.out.println("\n\tPrice category by Movie Dimension:");
        for (int i = 0; i < this.priceByMovieDim.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\t%s: $%.2f\n", priceByMovieDim.get(i).get(0), Double.parseDouble(priceByMovieDim.get(i).get(1)));
            }
            else {
                System.out.printf("\t\t%s: $%.2f\n", priceByMovieDim.get(i).get(0), Double.parseDouble(priceByMovieDim.get(i).get(1)));
            }
        }
        System.out.println("\n\tPrice category by Type of Cinema:");
        for (int i = 0; i < this.priceByCinemaType.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\t%s Cinema: $%.2f\n", priceByCinemaType.get(i).get(0), Double.parseDouble(priceByCinemaType.get(i).get(1)));
            }
            else {
                System.out.printf("\t\t%s Cinema: $%.2f\n", priceByCinemaType.get(i).get(0), Double.parseDouble(priceByCinemaType.get(i).get(1)));
            }
        }
    }
    public void setPriceByAge(ArrayList<String> age) {
        this.priceByAge.add(age);
    }
    public void setPriceByDay(ArrayList<String> day) {
        this.priceByDay.add(day);
    }
    public void setPriceByMovieDim(ArrayList<String> moviedim) {
        this.priceByMovieDim.add(moviedim);
    }
    public void setPriceByCinemaType(ArrayList<String> cinematype) {
        this.priceByCinemaType.add(cinematype);
    }
    public double getPriceByAge(int userage) {
        for (int i = 0; i < priceByAge.size(); i++) {
            if (userage < Integer.parseInt(priceByAge.get(i).get(0))) {
                return Double.parseDouble(priceByAge.get(i).get(1));
            }
        }
        return -1;
    }
    public double getPriceByDay(int day) {
        for (int i = 0; i < priceByDay.size(); i++) {
            if (day <= Integer.parseInt(priceByDay.get(i).get(0))) {
                return Double.parseDouble(priceByDay.get(i).get(1));
            }
        }
        return -1;
    }
    public double getPriceByDim(int dim) {
        for (int i = 0; i < priceByMovieDim.size(); i++) {
            if (dim == 2) {
                return Double.parseDouble(priceByMovieDim.get(0).get(1));
            }
            else if (dim == 3) {
                return Double.parseDouble(priceByMovieDim.get(1).get(1));
            }
        }
        return -1;
    }
    public double getPriceByType(int type) {
        for (int i = 1; i <= priceByCinemaType.size(); i++) {
            if (type == i) {
                return Double.parseDouble(priceByCinemaType.get(i - 1).get(1));
            }
        }
        return -1;
    }
}
