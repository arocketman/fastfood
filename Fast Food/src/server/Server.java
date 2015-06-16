package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import server.coordinator.IngressoController;
import server.entity.Posto;
import server.entity.Tavolo;
import server.proxy.ServerProxyManager;

public class Server {
	
	public static IngressoController ic = new IngressoController();
	
	public static void main(String[] args) throws RemoteException {
		Server.inserisciDatiProva();
		
		//avvia il registry sul porto 1099 (default)
		LocateRegistry.createRegistry(1099);
		
		//inizializza i servizi offerti
		ServerProxyManager lServerProxyManager = new ServerProxyManager();
		lServerProxyManager.initProxy();
		
		System.out.println("Server ready!");

	}
	
	public static void inserisciDatiProva(){
		Tavolo t1 = new Tavolo(1);
		Posto p1 = new Posto("AAAA", t1);
		Posto p2 = new Posto("BBBB", t1);
		Posto p3 = new Posto("CCCC", t1);
		Posto p4 = new Posto("DDDD", t1);
		Posto p5 = new Posto("EEEE", t1);
		Posto p6 = new Posto("FFFF", t1);
		t1.aggiungiPosti(p1,p2,p3,p4,p5,p6);
		
		Tavolo t2 = new Tavolo(2);
		Posto pp1 = new Posto("GGGG", t2);
		Posto pp2 = new Posto("HHHH", t2);
		Posto pp3 = new Posto("IIII", t2);
		Posto pp4 = new Posto("LLLL", t2);
		Posto pp5 = new Posto("MMMM", t2);
		Posto pp6 = new Posto("NNNN", t2);
		t2.aggiungiPosti(pp1,pp2,pp3,pp4,pp5,pp6);
		
		ic.gestoreTavoli.aggiungiTavolo(t1);
		ic.gestoreTavoli.aggiungiTavolo(t2);
	
	}
}
