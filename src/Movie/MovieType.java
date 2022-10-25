package Movie;

public class MovieType {

    public enum Genre{
        Action, Comedy, Drama, Fantasy, Horror, Myster, Romance, Thriller, Western;
    }

    public enum Class{
        G, PG, PG13, NC16, M18, R21;
    }

    public enum Type{
        TWO_D, THREE_D, BLOCKBUSTER;
    }

    private final String movieGenre, movieClass, movieType;


    public MovieType(String movieGenre, String movieClass, String movieType){
        this.movieGenre = movieGenre;
        this.movieClass = movieClass;
        this.movieType = movieType;

    }


    
}