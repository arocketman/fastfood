package client.proxy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.boundary.PostoBoundary;
import common.rmi.IngressoProxyInterface;
import common.rmi.PostoProxyInterface;

public class PostoProxy {
	public int occupaPosto(String codicePosto, int numeroTavolo , String codiceAssegnazione){
		try{
			
			Registry registry = LocateRegistry.getRegistry("localhost");
			PostoProxyInterface proxy = (PostoProxyInterface) registry.lookup("posto_proxy");
			return proxy.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);
			
		}catch(RemoteException e){
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return PostoBoundary.ERRORE;
	}

}
