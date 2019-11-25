package objects;

import java.io.Serializable;

public class User implements Serializable{
	int userId;
	String userFirstName;
	String userLastName;
	String userBookId;
	int userLoanDuration;
	
	public User(int userId, String userFirstName, String userLastName, String userBookId, int userLoanDuration) {
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userBookId = userBookId;
		this.userLoanDuration = userLoanDuration;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserBookId() {
		return userBookId;
	}

	public void setUserBookId(String userBookId) {
		this.userBookId = userBookId;
	}

	public int getUserLoanDuration() {
		return userLoanDuration;
	}

	public void setUserLoanDuration(int userLoanDuration) {
		this.userLoanDuration = userLoanDuration;
	}
}
