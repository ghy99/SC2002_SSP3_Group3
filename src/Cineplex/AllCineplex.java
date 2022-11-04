package Cineplex;

import Movie.Movie;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AllCineplex {
    private final String filename = "movies.txt";
    private ArrayList<Cineplex> cineplexes;

    private ArrayList<Movie> listOfMovies = new ArrayList<Movie>();

    public AllCineplex() throws IOException {
        InitializeCineplexes();
    }

    public ArrayList<Cineplex> getCineplexes() {
        return this.cineplexes;
    }

    public void setListOfMovies(ArrayList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }
    public void addMovies(Movie movie) throws IOException {
        this.listOfMovies.add(movie);
        TextDB.UpdateTextDB(filename, listOfMovies);
        //write to file
    }

    public ArrayList<Movie> getListOfMovies() {
        return this.listOfMovies;
    }

    public void updateListOfMovies(int index, Movie movie) throws IOException {
        //write to file
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);

    }

    public void removeMovie(int index, Movie movie) throws IOException {
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);
    }

    public void InitializeCineplexes() throws IOException {
        System.out.println("Initializing Cineplexes...\n...\n...");
        TextDB db = new TextDB();
//        this.cineplexes = new ArrayList<Cineplex>();
        String filename = "Cineplexes.txt";
        try {
            this.cineplexes = db.readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File movieFile = new File(TextDB.getCurrentDirectory() + File.separator + TextDB.Files.Movies.toString());
        if(!movieFile.exists())movieFile.createNewFile();


        ArrayList<Movie> movieList = db.readFromFile(File.separator + TextDB.Files.Movies.toString(), new ArrayList<>());

        for (Cineplex cineplex : this.cineplexes) {
            setListOfMovies(movieList);
            cineplex.InitializeMovies(this.listOfMovies);
        }
        System.out.println("Cineplexes are initialized\n");
    }

    /**
     Done by : Eddy Cheng
     * This Method displays the list of cineplex(Branches) available.
     */
    public void displayCineplexList() {
        for (int i = 0; i < this.cineplexes.size(); i++) {
            System.out.printf("\t%d) %s\n", i + 1, this.cineplexes.get(i).getCineplexName());
        }
        System.out.println();
    }

    /**
     * This Method displays the List of Movies currently available. User will only see Movie Title.
     * After this method, user will be able to select which movie to display more details.
     */
    public void displayMovieList() {
        ArrayList<Movie> movielist = this.listOfMovies;
        for (int j = 0; j < movielist.size(); j++) {
            System.out.printf("%d)\n", j + 1);
            movielist.get(j).printMovieDetails();
        }
        System.out.println();
    }
}
