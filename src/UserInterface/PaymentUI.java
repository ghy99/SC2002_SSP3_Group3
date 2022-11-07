package UserInterface;
import Cineplex.*;
import Customer.Customer;
import Movie.*;
import Service.DateTime;
import Service.GetNumberInput;
import Service.Settings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * This is the Payment Interface Class. Imported to call interface for the making the payment
 * @author GAN HAO YI, CHEW ZHI QI
 */
public class PaymentUI {
    /**
     * This method is the Payment Interface where customer's ticket price is calculated and the payment process is completed.
     * @param customer - tells me ticket information. (Might have to pass in Arraylist of Ticket.
     * @return change this to void.
     * @throws IOException
     */
    public static void PaymentInterface(AllCineplex cineplexes, Customer customer, ArrayList<MovieTicket> allMovieTicket,
                                        Cineplex choosenCineplex, Movie chosenMovie, ArrayList<Object> sSTnC
    ) throws IOException {
        ShowTime specificST = (ShowTime) sSTnC.get(0);
        Cinema cinema = (Cinema) sSTnC.get(1);
        // Check if movie is a blockbuster. If yes, +$1, else, no charge.
        // Get movie date, check holiday, if not holiday, check weekday / weekend, calc ticket price (Compare to settings.ticketcharges)
        // Check Cinema type (Regular Premium), check movie Dimensions(2D, 3D), calc ticket price (Compare to settings.ticketcharges)
        // Get number of tickets.
        // get movie genre, if movie genre < NC16, ask for how many children / adult / senior citizen price, cap at number of tickets.
        //                  if movie genre >= NC16, ask for adult / senior citizen price, cap at number of tickets.
        // Count number of seats booked, compare to age price, sum up and return price.

        // Number of tickets by age
        int student = 0, adult = 0, senior = 0;
        if (checkGenre(cineplexes, chosenMovie)) {
            System.out.println("How many student tickets (Enter 0 if no students):");
            student = GetNumberInput.getInt(0, allMovieTicket.size(), -1);
            if (student == -1) student = 0;
            if (allMovieTicket.size() - student == 0) {
                System.out.println("How many senior citizen tickets (Enter 0 if no senior citizens):");
                senior = GetNumberInput.getInt(0, allMovieTicket.size() - student, -1);
                if (senior == -1) senior = 0;
                adult = allMovieTicket.size() - student - senior;
            }
        }
        else {
            System.out.println("How many senior citizen tickets (Enter 0 if no senior citizens):");
            senior = GetNumberInput.getInt(0, allMovieTicket.size(), -1);
            if (senior == -1) senior = 0;
            adult = allMovieTicket.size() - student - senior;

        }

        // holiday / weekend / weekday price
        System.out.printf("You are buying:\n");
        System.out.printf("\t%d Student tickets\n", student);
        System.out.printf("\t%d Adult tickets\n", adult);
        System.out.printf("\t%d Senior Citizen tickets\n", senior);
        double holidayprice = checkHoliday(cineplexes.getHoliday(), specificST.getTime()) ?
                cineplexes.getTicketCharges().getPriceByDay(7) : checkWeekend(cineplexes, specificST.getTime());

        double cintypeprice = cineplexes.getTicketCharges().getPriceByType(cinema.getCinemaType().ordinal());

        double moviedimprice = cineplexes.getTicketCharges().getPriceByDim(specificST.getDimension().ordinal());
        double ticketPrice = 0;

        double studentprice = (cineplexes.getTicketCharges().getPriceByAge(1)
                + holidayprice + cintypeprice + moviedimprice) * student;
        double adultprice = (cineplexes.getTicketCharges().getPriceByAge(2)
                + holidayprice + cintypeprice + moviedimprice) * adult;
        double seniorprice = (cineplexes.getTicketCharges().getPriceByAge(3)
                + holidayprice + cintypeprice + moviedimprice) * senior;

        double ticketprice = studentprice + adultprice + seniorprice;

        System.out.printf("Your Ticket Price is: %.2f\n", ticketprice);
        System.out.println("Ticket cost will automatically be deducted from your registered PayLah account.");
        System.out.println("Please do not exit this window or enter any values.\n...Paying...\n...Paying...\n...Paying...\n");
        System.out.print("Payment done! Your transaction ID is: ");
    }

    public static double checkWeekend(AllCineplex cineplexes, Date showtime) {
        int day = DateTime.getDayNumberOld(showtime) - 1;
        double price = cineplexes.getTicketCharges().getPriceByDay(day);
        if (price == -1) return 0.0;
        return price;
    }

    public static boolean checkHoliday(ArrayList<String> holidaylist, Date showtime) {
        for (String holiday : holidaylist) {
            if (Objects.equals(holiday, DateTime.convertDate(showtime.getTime()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkGenre(AllCineplex cineplexes, Movie movie) {
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

    public static double CalculatePrice(AllCineplex cineplexes, Customer customer, ArrayList<MovieTicket> allMovieTicket,
                                        Cineplex choosenCineplex, Movie chosenMovie, ArrayList<Object> sSTnC) throws IOException {
        System.out.println("Calculate Price\n");
        System.out.println("Initializing Prices to compare\n");
        Settings settings = new Settings();
        TicketCharges charges = cineplexes.getTicketCharges();
        int userAge = 20;
        int day = 2;
        int dim = 2;
        int type = 1;
        double ageCost = charges.getPriceByAge(userAge); // get user age
        double dayCost = charges.getPriceByDay(day); // change to get day of week through datetime
        double dimCost = charges.getPriceByDim(dim); // get dimension of movie
        double typeCost = charges.getPriceByType(type); // get cinema type
        if (ageCost == -1) {
            System.out.println("Age is invalid. please enter a new value.");
            return -1;
        }
        if (dayCost == -1) {
            System.out.println("Day of the week is invalid. please enter a new value.");
            return -1;
        }
        if (dimCost == -1) {
            System.out.println("Movie Dimension is invalid. please enter a new value.");
            return -1;
        }
        if (typeCost == -1) {
            System.out.println("Cinema Type is invalid. please enter a new value.");
            return -1;
        }
        return ageCost + dayCost + dimCost + typeCost;
    }
}
