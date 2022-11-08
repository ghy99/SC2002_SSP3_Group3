package Movie;

import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Gan Hao Yi
 * Stores default ticket prices for reference
 */
public class TicketCharges {
    /**
     * price by Age:                Student: $1.50, Adult: $4.50, Senior Citizen: $2.50
     * price by Day:                Weekday: $2.50, Weekend: $4.00, Holiday: $4.50
     * price by Movie Dimension:    2D: $3.00, 3D: $5.00
     * price by Cinema Type:        Regular: $1.50, Premium: $4.50
     */
    private ArrayList<ArrayList<String>> priceByAge = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> priceByDay = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> priceByMovieDim = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> priceByCinemaType = new ArrayList<ArrayList<String>>();

    /**
     * TicketCharges Constructor.
     * This Constructor reads the default price from database and stores it in this TicketCharges Object.
     *
     * @throws IOException - Check TicketPrice database if it exists.
     */
    public TicketCharges() throws IOException {
        ArrayList<String[][]> ticketPrices = new ArrayList<>();
        String filename = "TicketPrice.txt";
        ticketPrices = TextDB.readFromFile(filename, (TicketCharges) null);
        for (int i = 0; i < ticketPrices.size(); i++) {
            switch (i) {
                case 0 -> {
                    String[][] temp = ticketPrices.get(i);
                    for (String[] strings : temp) {
                        ArrayList<String> age = new ArrayList<String>();
                        age.add(strings[0]);
                        age.add(strings[1]);
                        setPriceByAge(age);
                    }
                }
                case 1 -> {
                    String[][] temp = ticketPrices.get(i);
                    for (String[] strings : temp) {
                        ArrayList<String> day = new ArrayList<String>();
                        day.add(strings[0]);
                        day.add(strings[1]);
                        setPriceByDay(day);
                    }
                }
                case 2 -> {
                    String[][] temp = ticketPrices.get(i);
                    for (String[] strings : temp) {
                        ArrayList<String> Dim = new ArrayList<String>();
                        Dim.add(strings[0]);
                        Dim.add(strings[1]);
                        setPriceByMovieDim(Dim);
                    }
                }
                case 3 -> {
                    String[][] temp = ticketPrices.get(i);
                    for (String[] strings : temp) {
                        ArrayList<String> Type = new ArrayList<String>();
                        Type.add(strings[0]);
                        Type.add(strings[1]);
                        setPriceByCinemaType(Type);
                    }
                }
            }
        }
    }

    /**
     * Get Method
     *
     * @return - Returns an ArrayList of price by Age
     */
    public ArrayList<ArrayList<String>> returnAge() {
        return priceByAge;
    }

    /**
     * Get Method
     *
     * @return - Returns an ArrayList of price by Day of the Week
     */
    public ArrayList<ArrayList<String>> returnDay() {
        return priceByDay;
    }

    /**
     * Get Method
     *
     * @return - Returns an ArrayList of price by Dimension of Movie (2D / 3D)
     */
    public ArrayList<ArrayList<String>> returnDim() {
        return priceByMovieDim;
    }

    /**
     * Get Method
     *
     * @return - Returns an ArrayList of price by Type of Cinema (Regular / Premium)
     */
    public ArrayList<ArrayList<String>> returnType() {
        return priceByCinemaType;
    }

    /**
     * Set Method
     *
     * @param age - ArrayList of Price by Age
     */
    public void setPriceByAge(ArrayList<String> age) {
        this.priceByAge.add(age);
    }

    /**
     * Set Method
     *
     * @param day - ArrayList of Price by Day of the Week
     */
    public void setPriceByDay(ArrayList<String> day) {
        this.priceByDay.add(day);
    }

    /**
     * Set Method
     *
     * @param moviedim - ArrayList of Price by Movie Dimension (2D / 3D)
     */
    public void setPriceByMovieDim(ArrayList<String> moviedim) {
        this.priceByMovieDim.add(moviedim);
    }

    /**
     * Set Method
     *
     * @param cinematype - ArrayList of Price by Type of Cinema (Regular Premium)
     */
    public void setPriceByCinemaType(ArrayList<String> cinematype) {
        this.priceByCinemaType.add(cinematype);
    }

    /**
     * Get Specific price based on Age
     *
     * @param userage - User's Age
     * @return - Default Price based on Age
     */
    public double getPriceByAge(int userage) {
        switch (userage) {
            case 1 -> {
                return Double.parseDouble(priceByAge.get(0).get(1));
            }
            case 2 -> {
                return Double.parseDouble(priceByAge.get(1).get(1));
            }
            case 3 -> {
                return Double.parseDouble(priceByAge.get(2).get(1));
            }
        }
        return -1;
    }

    /**
     * Get Specific price based on Age
     *
     * @param day - Day of the Movie ShowTime
     * @return - Default Price based on Day of the Week
     */
    public double getPriceByDay(int day) {
        for (int i = 0; i < priceByDay.size(); i++) {
            if (day <= Integer.parseInt(priceByDay.get(i).get(0))) {
                return Double.parseDouble(priceByDay.get(i).get(1));
            }
        }
        return -1;
    }

    /**
     * Get Specific price based on Age
     *
     * @param dim - Movie's Dimension (2D / 3D)
     * @return - Default Price based on Movie Dimension
     */
    public double getPriceByDim(int dim) {
        for (int i = 0; i < priceByMovieDim.size(); i++) {
            if (dim == 0) {
                return Double.parseDouble(priceByMovieDim.get(0).get(1));
            } else if (dim == 1) {
                return Double.parseDouble(priceByMovieDim.get(1).get(1));
            }
        }
        return -1;
    }

    /**
     * Get Specific price based on Age
     *
     * @param type - Cinema Type (Regular / Premium)
     * @return - Default Price based on Type of Cinema
     */
    public double getPriceByType(int type) {
        for (int i = 1; i <= priceByCinemaType.size(); i++) {
            if (type == i) {
                return Double.parseDouble(priceByCinemaType.get(i - 1).get(1));
            }
        }
        return -1;
    }

    /**
     * This function prints the current default ticket charges. For Admin only
     */
    public void printTicketCharges() {
        System.out.println("\n\tPrice category by age:");
        for (int i = 0; i < this.priceByAge.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\tBelow 21: $%.2f\n", Double.parseDouble(priceByAge.get(i).get(1)));
            } else if (i == 1) {
                System.out.printf("\t\tBelow 65: $%.2f\n", Double.parseDouble(priceByAge.get(i).get(1)));
            } else {
                System.out.printf("\t\tAbove 65: $%.2f\n", Double.parseDouble(priceByAge.get(i).get(1)));
            }
        }
        System.out.println("\n\tPrice category by day of the week:");
        for (int i = 0; i < this.priceByDay.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\tMonday - Friday: $%.2f\n", Double.parseDouble(priceByDay.get(i).get(1)));
            } else if (i == 1) {
                System.out.printf("\t\tSaturday - Sunday: $%.2f\n", Double.parseDouble(priceByDay.get(i).get(1)));
            } else if (i == 2) {
                System.out.printf("\t\tPublic Holiday: $%.2f\n", Double.parseDouble(priceByDay.get(i).get(1)));
            }
        }
        System.out.println("\n\tPrice category by Movie Dimension:");
        for (int i = 0; i < this.priceByMovieDim.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\t%s: $%.2f\n", priceByMovieDim.get(i).get(0), Double.parseDouble(priceByMovieDim.get(i).get(1)));
            } else {
                System.out.printf("\t\t%s: $%.2f\n", priceByMovieDim.get(i).get(0), Double.parseDouble(priceByMovieDim.get(i).get(1)));
            }
        }
        System.out.println("\n\tPrice category by Type of Cinema:");
        for (int i = 0; i < this.priceByCinemaType.size(); i++) {
            if (i == 0) {
                System.out.printf("\t\t%s Cinema: $%.2f\n", priceByCinemaType.get(i).get(0), Double.parseDouble(priceByCinemaType.get(i).get(1)));
            } else {
                System.out.printf("\t\t%s Cinema: $%.2f\n", priceByCinemaType.get(i).get(0), Double.parseDouble(priceByCinemaType.get(i).get(1)));
            }
        }
    }
}