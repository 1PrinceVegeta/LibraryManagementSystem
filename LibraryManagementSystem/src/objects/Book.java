package objects;

import java.io.Serializable;

public class Book implements Serializable {
    int bookId;
    int bookType;
    String bookTitle;
    String bookAuthor;
    int numAvailable;

    public Book(int bookId, int bookType, String bookTitle, String bookAuthor, int numAvailable) {
        this.bookId = bookId;
    	this.bookType = bookType;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.numAvailable = numAvailable;
    }

    public int getNumAvailable() {
		return numAvailable;
	}

	public void setNumAvailable(int numAvailable) {
		this.numAvailable = numAvailable;
	}


	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getBookType() {
        return bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
    }


    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
