package client.proxy;

import common.rmi.IngressoProxyInterface;
import server.entity.Assegnazione;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Proxy con cui interagisce la business logic del client in ingresso.
 */
public class IngressoProxy {

	public Assegnazione richiediTavolo(int numPosti){
		try{
			
			Registry registry = LocateRegistry.getRegistry("localhost");
			IngressoProxyInterface proxy = (IngressoProxyInterface) registry.lookup("ingresso_proxy");
			return proxy.richiediTavolo(numPosti);
			
		}catch(RemoteException e){
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	public boolean inserisciPrenotazione(String cognome, String telefono, int numPosti){
		try{
			
			Registry registry = LocateRegistry.getRegistry("localhost");
			IngressoProxyInterface proxy = (IngressoProxyInterface) registry.lookup("ingresso_proxy");
			return proxy.inserisciPrenotazione(cognome, telefono, numPosti);
			
		}catch(RemoteException e){
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
