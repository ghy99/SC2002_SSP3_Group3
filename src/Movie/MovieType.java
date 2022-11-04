package Movie;

/**
 * @author Eddy
 * This class stores the different types of movies.
 */
public class MovieType {

    public enum Genre{
        Action, Comedy, Drama, Fantasy, Horror, Mystery, Romance, Thriller, Western;
    }

    public enum Blockbuster {
        BLOCKBUSTER("BLOCKBUSTER"),
        NOTBLOCKBUSTER("NOTBLOCKBUSTER");

        public final String Blockbuster;

        Blockbuster(String blockbuster) {
            this.Blockbuster = blockbuster;
        }

        public String ToString() {
            return Blockbuster;
        }
    }

    public enum Dimension {
        TWO_D("TWO_D"),
        THREE_D("THREE_D");

        public final String Dimension;

        Dimension(String Dimension) {
            this.Dimension = Dimension;
        }

        public String ToString() {
            return Dimension;
        }
    }

    public enum Class{
        G, PG, PG13, NC16, M18, R21;
    }

    private final String movieGenre, movieClass, movieType;


    public MovieType(String movieGenre, String movieClass, String movieType){
        this.movieGenre = movieGenre;
        this.movieClass = movieClass;
        this.movieType = movieType;

    }


    
}
