package clientPosto.proxy;

import clientPosto.boundary.PostoBoundary;
import common.rmi.PostoProxyInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Proxy con cui interagisce la business logic del clientPosto del posto.
 */
public class PostoProxy {
	public String occupaPosto(String codicePosto, int numeroTavolo, String codiceAssegnazione) {
		try {

			Registry registry = LocateRegistry.getRegistry("localhost");
			PostoProxyInterface proxy = (PostoProxyInterface) registry.lookup("posto_proxy");
			return proxy.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
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
			e.printStackTrace();
			return false;
		}
	}

	public boolean occupaPostoCodPrenotazione(String codicePrenotazione, String codiceAssegnazione, String codicePosto){
		try {

			Registry registry = LocateRegistry.getRegistry("localhost");
			PostoProxyInterface proxy = (PostoProxyInterface) registry.lookup("posto_proxy");
			return proxy.occupaPostoCodPrenotazione(codicePrenotazione, codiceAssegnazione, codicePosto);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		return false;
	}
}
