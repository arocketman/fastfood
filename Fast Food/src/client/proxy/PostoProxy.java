package client.proxy;

import client.boundary.PostoBoundary;
import common.rmi.PostoProxyInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PostoProxy {
	public int occupaPosto(String codicePosto, int numeroTavolo, String codiceAssegnazione) {
		try {

			Registry registry = LocateRegistry.getRegistry("localhost");
			PostoProxyInterface proxy = (PostoProxyInterface) registry.lookup("posto_proxy");
			return proxy.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return PostoBoundary.ERRORE;
	}

	public boolean liberaPosto(String codicePosto, int numeroTavolo) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost");
			PostoProxyInterface proxy = (PostoProxyInterface) registry.lookup("posto_proxy");
			return proxy.liberaPosto(codicePosto, numeroTavolo);
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
