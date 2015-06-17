package server.proxy;

import java.rmi.RemoteException;

import server.Server;
import server.coordinator.Controller;
import server.entity.Assegnazione;
import common.rmi.IngressoProxyInterface;

public class IngressoProxy implements IngressoProxyInterface {

	@Override
	public Assegnazione richiediTavolo(int numPosti) throws RemoteException {
		Controller controller = Server.ic;
		return controller.richiediTavolo(numPosti);
	}

	@Override
	public boolean inserisciPrenotazione(String cognome, String telefono ,int numPosti) throws RemoteException {
		Controller controller = Server.ic;
		return controller.inserisciPrenotazione(cognome,telefono,numPosti);
	}

}
