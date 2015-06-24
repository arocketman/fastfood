package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IngressoProxyInterface extends Remote {

	String richiediTavolo(int numPosti) throws RemoteException;
	boolean inserisciPrenotazione(String cognome, String telefono, int numPosti) throws RemoteException;
	
}
