package UserInterface;

import Cineplex.*;
import Movie.*;
import Service.DateTime;
import Service.GetNumberInput;
import Service.Settings;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * This is the Payment Interface Class. Imported to call interface for the making the payment
 *
 * @author GAN HAO YI, CHEW ZHI QI
 */
public class PaymentUI {
    /**
     * This method is the Payment Interface where customer's ticket price is calculated and the payment process is completed.
     *
     * @param cineplexes     - To get default price charges to calculate price.
     * @param allMovieTicket - To count number of tickets.
     * @param chosenMovie    - To check Movie Genre and Movie Dimension (2D / 3D)
     * @param sSTnC          - Stores the Cinema and the specific ShowTime of Movie Customer is buying.
     */
    public static void PaymentInterface(AllCineplex cineplexes, ArrayList<MovieTicket> allMovieTicket,
                                        Movie chosenMovie, ArrayList<Object> sSTnC) {
        ShowTime specificST = (ShowTime) sSTnC.get(0);
        Cinema cinema = (Cinema) sSTnC.get(1);
        int student = 0, adult = 0, senior = 0;
        cineplexes.clearScreen();
        System.out.println(Settings.ANSI_RED);
        System.out.println("*************************************************");
        System.out.println("*               Payment Interface               *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        if (checkGenre(chosenMovie)) {
            System.out.println("How many student tickets (Enter 0 if no students):");
            student = GetNumberInput.getInt(0, allMovieTicket.size(), -1);
            if (student == -1) student = 0;
            if (allMovieTicket.size() - student != 0) {
                System.out.println("How many senior citizen tickets (Enter 0 if no senior citizens):");
                senior = GetNumberInput.getInt(0, allMovieTicket.size() - student, -1);
                if (senior == -1) senior = 0;
                adult = allMovieTicket.size() - student - senior;
            }
        } else {
            System.out.println("How many senior citizen tickets (Enter 0 if no senior citizens):");
            senior = GetNumberInput.getInt(0, allMovieTicket.size(), -1);
            if (senior == -1) senior = 0;
            adult = allMovieTicket.size() - student - senior;
        }
        System.out.println("You are buying:");
        System.out.printf("\t%d Student tickets\n", student);
        System.out.printf("\t%d Adult tickets\n", adult);
        System.out.printf("\t%d Senior Citizen tickets\n", senior);
        // holiday / weekend / weekday price
        double holidayprice = checkHoliday(cineplexes.getHoliday(), specificST.getTime()) ?
                cineplexes.getTicketCharges().getPriceByDay(8) : checkWeekend(cineplexes, specificST.getTime());
        // Cinema Type price
        double cintypeprice = cineplexes.getTicketCharges().getPriceByType(cinema.getCinemaType().ordinal());
        // Movie Dimension price
        double moviedimprice = cineplexes.getTicketCharges().getPriceByDim(specificST.getDimension().ordinal());
        // Calculating Student Price
        double studentprice = (cineplexes.getTicketCharges().getPriceByAge(1)
                + holidayprice + cintypeprice + moviedimprice) * student;
        // Calculating Adult Price
        double adultprice = (cineplexes.getTicketCharges().getPriceByAge(2)
                + holidayprice + cintypeprice + moviedimprice) * adult;
        // Calculating Senior Citizen Price
        double seniorprice = (cineplexes.getTicketCharges().getPriceByAge(3)
                + holidayprice + cintypeprice + moviedimprice) * senior;
        System.out.printf("holiday: %.1f\tcinema type: %.1f\tmovie dim: %.1f\n", holidayprice, cintypeprice, moviedimprice);
        System.out.printf("student: %.1f\tadult: %.1f\tsenior: %.1f\n", studentprice, adultprice, seniorprice);
        double ticketprice = studentprice + adultprice + seniorprice;
        // Check if blockbuster, add blockbuster charge
        if (chosenMovie.getBlockBuster() == MovieType.Blockbuster.BLOCKBUSTER) {
            ticketprice = ticketprice + allMovieTicket.size();
        }

        System.out.printf("Your Ticket Price is: $%.2f\n", ticketprice);
        System.out.println("Ticket cost will automatically be deducted from your registered PayLah Account.");
        System.out.println("Please do not exit this window or enter any values.\n");
        System.out.println("...Paying...\n...Paying...\n...Paying...\n");
        System.out.printf("Payment is done! Successfully deducted $%.2f from your PayLah Account.", ticketprice);
    }

    /**
     * this method checks if the selected date is a weekend or weekday.
     *
     * @param cineplexes - get the price of when it is a weekday / weekend
     * @param showtime   - check date of showtime if it is a weekday / weekend
     * @return - price of a weekday / weekend
     */
    public static double checkWeekend(AllCineplex cineplexes, Date showtime) {
        int day = DateTime.getDayNumberOld(showtime) - 1;
        double price = cineplexes.getTicketCharges().getPriceByDay(day);
        if (price == -1) return 0.0;
        return price;
    }

    /**
     * This method checks if selected date is a holiday.
     *
     * @param holidaylist - get a list of holiday dates
     * @param showtime    - check date of showtime if it is a holiday
     * @return - if ShowTime date is a holiday, return true, else return false.
     */
    public static boolean checkHoliday(ArrayList<String> holidaylist, Date showtime) {
        for (String holiday : holidaylist) {
            if (Objects.equals(holiday, DateTime.convertDate(showtime.getTime()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks the genre of the movie.
     * If it is rated below NC16, it will not charge student price, only adult / senior citizen.
     *
     * @param movie - To get the rating of the movie.
     * @return returns true if it is below NC16, else false
     */
    public static boolean checkGenre(Movie movie) {
        MovieType.Class rating = movie.getMovieClass();
        switch (rating) {
            case G, PG, PG13 -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}