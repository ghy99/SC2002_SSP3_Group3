package Cineplex;
import Movie.Movie;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Cineplex {
    private static final TextDB db = new TextDB();
    //Create dir and movie.txt if entered cineplex name doesn't exist in DataStorage dir
    private final File cineplexDir;

    // This will be for Cineplexes
    private String cineplexName;
    private ArrayList<Cinema> listOfCinemas = new ArrayList<Cinema>();
    private ArrayList<Movie> listOfMovies = new ArrayList<Movie>();

    public Cineplex(String name) {
        this.cineplexName = name;
        cineplexDir = new File(TextDB.getCurrentDirectory() + "\\" + this.getCineplexName().replace(' ','_'));
    }

    public String getCineplexName() {
        return this.cineplexName;
    }

    public int getNoOfCinemas() {
        return this.listOfCinemas.size();
    }
    public int getNoOfRegularCinemas() {
        int count = 0;
        for (int i = 0; i < listOfCinemas.size(); i++)
        {
            if(listOfCinemas.get(i).getCinemaType() == Cinema.CinemaType.Regular)
            {
                count ++;
            }
        }
        return count;
    }
    public int getNoOfPremiumCinemas() {
        int count = 0;
        for (int i = 0; i < listOfCinemas.size(); i++)
        {
            if(listOfCinemas.get(i).getCinemaType() == Cinema.CinemaType.Premium)
            {
                count ++;
            }
        }
        return count;
    }

    public void addCinema(Cinema cinema)
    {
        this.listOfCinemas.add(cinema);

        //write to file
    }

    public ArrayList<Cinema> getListOfCinemas() {
        return this.listOfCinemas;
    }

    public void updateListOfCinema(int index)
    {
        //write to file
    }

    public void addMovies(Movie movie)
    {
        this.listOfMovies.add(movie);

        //write to file
    }

    public ArrayList<Movie> getListOfMovies() {
        return this.listOfMovies;
    }

    public void updateListOfMovies(int index)
    {
        //write to file
    }

    public void InitializeMovies() throws IOException {
        System.out.println("Initializing list of movies in cinemas\n...\n...");

        File movieFile = new File(cineplexDir+"\\" + TextDB.Files.ShowTime.ToString());
        File showTimeFile = new File(cineplexDir+"\\" + TextDB.Files.Movies.ToString());

        if (!cineplexDir.exists()){
            cineplexDir.mkdirs();

            movieFile.createNewFile();
            showTimeFile.createNewFile();

            System.out.println("Created directory, Movies.txt and Showtime.txt. Path :" + cineplexDir+"\\" );
        }
        else
        {
            if(!movieFile.exists())movieFile.createNewFile();
            if(!showTimeFile.exists()) showTimeFile.createNewFile();

            System.out.println("Created Movies.txt and Showtime.txt. Path :" + cineplexDir+"\\" );
        }

        try {
            this.listOfMovies = db.readFromFile(getCineplexName().replace(' ','_') + "\\" +TextDB.Files.Movies.ToString(), this.listOfMovies);
            for(Cinema c : listOfCinemas)
            {
                File cinema = new File(cineplexDir+"\\" + c.getCinemaName()+".txt");
                if(!cinema.exists())cinema.createNewFile();
                c.setShowTime(db.readFromFile( this.listOfMovies, "\\" + this.getCineplexName().replace(' ','_')+ "\\"+c.getCinemaName()+".txt" ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Movies are initialized.\n");
    }

    //Class Test
    public static void main(String[] args) throws IOException {
    }


    private void moveTextTest() throws IOException {
        Cineplex myC = new Cineplex("Cineplex3" );

        System.out.println("Initializing list of movies in cinemas\n...\n...");

        File cineplexDir = new File(TextDB.getCurrentDirectory() + "\\" + myC.getCineplexName().replace(' ','_'));
        if (!cineplexDir.exists()){
            cineplexDir.mkdirs();
            File movieFile = new File(cineplexDir+"\\" + TextDB.Files.Movies.ToString());
            movieFile.createNewFile();

            System.out.println("Created directory and Movies.txt. Path :" + cineplexDir+"\\" + TextDB.Files.Movies.ToString());
        }

        try {
            myC.listOfMovies = db.readFromFile(  myC.getCineplexName().replace(' ','_') + "\\" +TextDB.Files.Movies.ToString(), myC.listOfMovies);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Movie data : myC.getListOfMovies())
        {
            System.out.println(data.getMovieTitle());
        }

        System.out.println("Movies are initialized.\n");
    }

    public String replace(char c, char d) {
        return null;
    }
}
