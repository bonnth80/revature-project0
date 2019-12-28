package business;

public abstract class User {
	private String fName;
	private String lName;
	private int id;
	
	public User(int id, String fName, String lName) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
	};	
}
