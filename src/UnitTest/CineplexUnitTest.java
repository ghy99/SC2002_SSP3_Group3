package UnitTest;

import Cineplex.*;
import Service.DateTime;
import Service.TextDB;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class CineplexUnitTest {
    private static final TextDB db = new TextDB();

    private static void cineplexTextTest() throws IOException {
        ArrayList<Cineplex> cineplexes = new ArrayList<Cineplex>();
        String filename = "Cineplexes.txt";

        try {
            cineplexes = db.readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Cineplex c : cineplexes)
        {
            System.out.println(c.getCineplexName());

            if(Objects.equals(c.getCineplexName(), "Shaw Theatre"))
            {
                c.InitializeMovies();
                for (Cinema z : c.getListOfCinemas())
                {
                    System.out.println(z.getCinemaName() + " " + z.getCinemaType());
                    {
                        for (ShowTime st : z.getShowTime())
                        {
                            System.out.println((DateTime.convertTime(st.time.getTime()) ));
                            st.printSeats();
                        }
                    }
                }
            }

        }
    }


    public static void main(String[] args) throws IOException {
        cineplexTextTest();
    }
}

