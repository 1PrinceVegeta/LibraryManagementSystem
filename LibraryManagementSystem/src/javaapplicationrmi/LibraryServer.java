/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LibraryServer {
	public LibraryServer() throws MalformedURLException {
		try {
			Registry r = LocateRegistry.createRegistry(1099);
			BookManagerImpl bm = new BookManagerImpl();
			Naming.rebind("LibrarySystemService", bm);
		} catch(RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}		
	
	public static void main(String args[])throws Exception{
	   new LibraryServer();	   
	}
}

