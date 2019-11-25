package bookManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import objects.User;
import objects.Book;

public interface BookManager extends Remote {
    public default void loanBook(Hashtable<Integer, Integer> availableBooks, int choice, String fname, String lname, int duration) throws RemoteException {}
    public ArrayList<Book> getBooks()throws RemoteException;
    public ArrayList<User> getUsers() throws RemoteException;
    public void registerUsers(String fname, String lname, String address, String email, String username, String password) throws RemoteException;
    public void updateLoanDuration(int duration, int id) throws RemoteException;
    public void deleteUser(int id) throws RemoteException;
    public void displayLoginUsers(String username, String password) throws RemoteException;
}