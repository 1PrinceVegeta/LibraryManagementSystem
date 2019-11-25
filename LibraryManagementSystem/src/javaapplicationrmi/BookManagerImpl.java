/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationrmi;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

import objects.User;   //User Object RMI
import objects.Book;    //Book Object RMI
import bookManager.BookManager;    //RMI Interface
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class BookManagerImpl extends UnicastRemoteObject implements bookManager.BookManager {
        
    private static final int type0Book = 17;
    private static final int type1Book = 9;
    private static final int type2Book = 13;
    private static final int type3Book = 4;
    private static final int type4Book = 6;

    public BookManagerImpl() throws RemoteException{
    	super();
    }

    public ArrayList<Book> getBooks() throws RemoteException {
    	ArrayList<Book> bookList = new ArrayList<>();
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");  
    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");  
    		Statement stmt = con.createStatement();  
    		ResultSet rs = stmt.executeQuery("select * from book");  
    		while(rs.next()) {
                    Book book = new Book(1, 1, "", "", 1);
                    book.setBookId(rs.getInt("bookId"));
                    book.setBookType(rs.getInt("bookType"));
                    book.setBookTitle(rs.getString("bookTitle"));
                    book.setBookAuthor(rs.getString("bookAuthor")); 
                    book.setNumAvailable(rs.getInt("numAvailable"));
                    bookList.add(book);
    		}
    		con.close();  
    	} catch(Exception e) {
    		e.printStackTrace(); 
    	}
        return bookList;
    }

	public ArrayList<User> getUsers() throws RemoteException {
        ArrayList<User> userList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");  
            Statement stmt = con.createStatement();  
            ResultSet rs = stmt.executeQuery("select * from user");  
            while(rs.next()) {
                User user = new User(1, "", "", "", 1);
                user.setUserFirstName(rs.getString("firstName"));
                user.setUserLastName(rs.getString("lastName"));
                user.setUserBookId(rs.getString("bookId")); 
                user.setUserId(rs.getInt("userId"));
                user.setUserLoanDuration(rs.getInt("loanDuration"));
                userList.add(user);
            }
        con.close();  
        } catch(Exception e) {
            e.printStackTrace(); 
        }
        return userList;
	}

	public synchronized void loanBook(Hashtable<Integer, Integer> availableBooks, int choice, String fname, String lname, int duration) throws RemoteException {
		try {
			Thread.sleep(1); 
		} catch (InterruptedException e) {
			e.printStackTrace();  
		}
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		Connection con = null;
		try{
            initializeBooks();
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");  
	
            con.setAutoCommit(false);
		    stmt1 = con.prepareStatement("UPDATE book SET numAvailable = ? WHERE bookType = ?");
		    
		    String registerUser = "insert into user(firstName, lastName, bookId, loanDuration) values(?, ?, ?, ?)";
		    stmt2 = con.prepareStatement(registerUser);

		    // Set Prepared Statement Parameters [BookType] & prepare for batch update
            int dbBooksAvailableValue=0;
		    switch(choice){
                case 0:
                    dbBooksAvailableValue=type0Book;
                    break;
                case 1:
                    dbBooksAvailableValue=type1Book;
                    break;
                case 2:
                    dbBooksAvailableValue=type2Book;
                    break;
                case 3:
                    dbBooksAvailableValue=type3Book;
                    break;
                case 4:
                    dbBooksAvailableValue=type4Book;
            }
		    stmt1.setInt(1, dbBooksAvailableValue - 1);
		    stmt1.setInt(2, choice);

		    stmt2.setString(1, fname); 
		    stmt2.setString(2, lname);
		    stmt2.setInt(3, choice++);
		    stmt2.setInt(4, duration);
		   
		    stmt1.execute();
                    stmt2.execute();
                    if (stmt2.executeUpdate() > 0) {                        
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);    
                        JOptionPane.showMessageDialog(dialog, "Booking is successfully created!");
                    }                        
                    
		    con.commit();                    
        } catch (ClassNotFoundException | SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
	}
        
    public static void initializeBooks() throws ClassNotFoundException {
        PreparedStatement statement = null;
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");  

            con.setAutoCommit(false);
            statement = con.prepareStatement("UPDATE book SET numAvailable = ? WHERE bookId = ?");

            // Set Prepared Statement Parameters [BookType] & prepare for batch update
            statement.setInt(1, type0Book);
            statement.setInt(2, 1);
            statement.addBatch();

            statement.setInt(1, type1Book);
            statement.setInt(2, 2);
            statement.addBatch();

            statement.setInt(1, type2Book);
            statement.setInt(2, 3);
            statement.addBatch();

            statement.setInt(1, type3Book);
            statement.setInt(2, 4);
            statement.addBatch();

            statement.setInt(1, type4Book);
            statement.setInt(2, 5);
            statement.addBatch();

            statement.executeBatch();
            con.commit();
        } catch (ClassNotFoundException | SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            try{ 
                if(statement != null) statement.close();
                if(con != null) con.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }             
	}
    
    public void registerUsers(String fname, String lname, String address, String email, String username, String password) throws RemoteException {
        
		PreparedStatement stmt1 = null;
		Connection con = null;
		try{              
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");  
	
            con.setAutoCommit(false);
		    
		    String registerUsers = "insert into registration(firstName, lastName, address, email, username, password) values(?, ?, ?, ?, ?, ?)";
		    stmt1 = con.prepareStatement(registerUsers);

		    stmt1.setString(1, fname); 
		    stmt1.setString(2, lname);
		    stmt1.setString(3, address);
		    stmt1.setString(4, email);
                    stmt1.setString(5, username);
                    stmt1.setString(6, password);
		   
		    stmt1.execute();
                    if (stmt1.executeUpdate() > 0) {                        
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);    
                        JOptionPane.showMessageDialog(dialog, "Registration is successfully created!");
                    }                         
                    
		    con.commit();
		    
        } catch (ClassNotFoundException | SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
        
    @Override
    public void displayLoginUsers(String username, String password) {
    	PreparedStatement stmt1 = null;
	    Connection con = null;
	    ResultSet rs;
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");  
	        con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");
	        
	        con.setAutoCommit(false);
	        
	        String loginUsers = "SELECT * FROM registration where username=? and password=?";
	        stmt1 = con.prepareStatement(loginUsers);
	        
	        stmt1.setString(1, username);
	        stmt1.setString(2, password);
	        
	        rs = stmt1.executeQuery();
	        con.commit();                      
	   
        } catch (ClassNotFoundException | SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
            e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
   
    
    public void updateLoanDuration(int duration, int userId) throws RemoteException {
        
        PreparedStatement stmt1 = null;	
        Connection con = null;
        try{
	               
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");  
	
            con.setAutoCommit(false);
            stmt1 = con.prepareStatement("UPDATE user SET loanDuration = ? WHERE userId = ?");

            stmt1.setInt(1, duration);
            stmt1.setInt(2, userId);

            stmt1.execute();
            if (stmt1.executeUpdate() > 0) {                        
                final JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);    
                JOptionPane.showMessageDialog(dialog, "Booking is successfully updated!");
            }
            con.commit();
		    
        } catch (ClassNotFoundException | SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    
    public void deleteUser(int userId) throws RemoteException {
        
        PreparedStatement stmt1 = null;	
        Connection con = null;
        try{
	               
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://localhost/library_system","root","");  
	
            con.setAutoCommit(false);
		    stmt1 = con.prepareStatement("DELETE from user WHERE userId = ?");
	
		    stmt1.setInt(1, userId);
		
		    stmt1.execute();
                    
                    final JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);    
                    JOptionPane.showMessageDialog(dialog, "Booking is successfully deleted!");                     
                    
		    con.commit();
		    
        } catch (ClassNotFoundException | SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}