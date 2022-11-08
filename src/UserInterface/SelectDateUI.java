package UserInterface;

import Cineplex.Cinema;
import Cineplex.Cineplex;
import Cineplex.ShowTime;
import Movie.Movie;
import Service.DateTime;
import Service.GetNumberInput;
import Service.Settings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class SelectDateUI {
    public static ArrayList<Object> SelectDateInterFace(Cineplex cineplex , Movie movie) {
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*              Select Date of Movie             *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        String selectedDate = "";
        int selectDate = -1;
        int dateChecking = -1;
        ArrayList<Object> res = new ArrayList<>();
        ArrayList<ShowTime> allST = new ArrayList<>();
        ArrayList<Cinema> cinemas = new ArrayList<>();

        //Add all showtime and cinema for the specific movie into list
        ArrayList<Cinema> listOfCinemas = cineplex.getListOfCinemas();
        //filter out showtime
        for (Cinema c : listOfCinemas) {
            ArrayList<ShowTime> listOfShowtime = c.getShowTime();
            if (listOfShowtime.size() > 0) {
                for (ShowTime st : listOfShowtime) {
                    if (Objects.equals(st.getMovie().getMovieTitle(), movie.getMovieTitle())) {
                        allST.add(st);
                        cinemas.add(c);
                    }
                }
            }
        }

        while (dateChecking == -1) {
            ArrayList<String> STDates = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            LocalDate STDate = java.time.LocalDate.now();
            for (int d = 0; d < 7; d++) {
                System.out.printf("%s) %s\n", d + 1, formatter.format(STDate));
                STDates.add(formatter.format(STDate));
                STDate = STDate.plusDays(1);
            }
            System.out.println("Select your date from the list above: ");
            selectDate = GetNumberInput.getInt(1, 7, -1) - 1;
            if (selectDate < 0) {
                return null;
            }
            selectedDate = STDates.get(selectDate);

            for (ShowTime showTime : allST) {
                if (Objects.equals(selectedDate, DateTime.convertDate(showTime.getTime().getTime()))) {
                    dateChecking++;
                    break;
                }
            }
            if (dateChecking == -1) {
                System.out.printf("%s no showtime for this movies on %s. Please select another Date.\n\n", cineplex.getCineplexName(), selectedDate);
                return null;
            }
        }
        res.add(allST);
        res.add(cinemas);
        res.add(selectedDate);

        return res;
    }
}