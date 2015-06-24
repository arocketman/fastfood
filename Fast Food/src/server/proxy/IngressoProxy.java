package server.proxy;

import common.rmi.IngressoProxyInterface;
import server.Server;
import server.coordinator.Controller;
import server.entity.Assegnazione;

import java.rmi.RemoteException;

public class IngressoProxy implements IngressoProxyInterface {

	@Override
	public String richiediTavolo(int numPosti) throws RemoteException {
		Controller controller = Server.ic;
		Assegnazione assegnazione = controller.richiediTavolo(numPosti);
		if(assegnazione != null)
			return assegnazione.impacchettaPerClient().toString();
		return null;
	}

	@Override
	public boolean inserisciPrenotazione(String cognome, String telefono ,int numPosti) throws RemoteException {
		Controller controller = Server.ic;
		return controller.inserisciPrenotazione(cognome,telefono,numPosti);
	}

}
