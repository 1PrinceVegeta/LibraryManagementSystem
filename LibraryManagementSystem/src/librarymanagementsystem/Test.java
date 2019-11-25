/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import bookManager.BookManager;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author User
 */
public class Test {
    public static void main(String[] args){
        try {
            Registry r=LocateRegistry.getRegistry("127.0.0.1", 1099);
            BookManager bM=(BookManager) r.lookup("LibrarySystemService");
            System.out.print(bM.getBooks());
            System.out.print(bM.getUsers());
        }catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
