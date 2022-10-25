

public class MovieStatus{
	
	public enum CurrentMovieStatus{
	    COMING_SOON("Coming soon"),
	    PREVIEW("Preview"),
	    NOW_SHOWING("Now showing"),
	    END_OF_SHOWING("End of showing");
	
	    private final String status;
	
	    private CurrentMovieStatus(String status){
	        this.status = status;
	    }
	
	    public String toString(){
	        return status;
	    }
}
}