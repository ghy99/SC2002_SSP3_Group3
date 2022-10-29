package Admin;

public class Admin {
	private String name;
	  private String password;

	  public Admin(String name, String password){
	    this.name = name;
	    this.password =  password;
	  }

	  public String getName(){
	    return name;
	  }

	  public String getPassword(){
	    return password;
	  }
}
