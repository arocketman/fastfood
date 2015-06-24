package server.proxy;

import common.rmi.IngressoProxyInterface;
import server.Server;
import server.coordinator.Controller;

import java.rmi.RemoteException;

public class IngressoProxy implements IngressoProxyInterface {

	@Override
	public String richiediTavolo(int numPosti) throws RemoteException {
		Controller controller = Server.ic;
		return controller.richiediTavolo(numPosti).impacchettaPerClient().toString();
	}

	@Override
	public boolean inserisciPrenotazione(String cognome, String telefono ,int numPosti) throws RemoteException {
		Controller controller = Server.ic;
		return controller.inserisciPrenotazione(cognome,telefono,numPosti);
	}

}
