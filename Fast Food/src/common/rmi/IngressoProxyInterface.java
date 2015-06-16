package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.entity.Assegnazione;

public interface IngressoProxyInterface extends Remote {

	public Assegnazione richiediTavolo(int numPosti) throws RemoteException;
	public boolean inserisciPrenotazione(String cognome, String telefono, int numPosti) throws RemoteException;
	
}
