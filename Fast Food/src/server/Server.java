package server;

import server.coordinator.Controller;
import server.database.PostoDBWrapper;
import server.database.TavoloDBWrapper;
import server.entity.Assegnazione;
import server.entity.Posto;
import server.entity.Tavolo;
import server.proxy.ServerProxyManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {
	
	public static Controller ic = new Controller();
	
	public static void main(String[] args) throws RemoteException {
		Server.inserisciDatiProvaDB();

		//System.setProperty("java.rmi.server.hostname","62.98.100.27");
		//avvia il registry sul porto 1099 (default)
		LocateRegistry.createRegistry(1099);
		//Server.loadFromDB();
		
		//inizializza i servizi offerti
		ServerProxyManager lServerProxyManager = new ServerProxyManager();
		lServerProxyManager.initProxy();
		
		log("Server ready!");

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
	
	public static void loadFromDB(){
		for(Tavolo t : loadTavoli())
			ic.gestoreTavoli.aggiungiTavolo(t);
	}
	
	public static ArrayList<Tavolo> loadTavoli(){
		ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
		
		ArrayList<TavoloDBWrapper> tavoliDBWrapper = TavoloDBWrapper.findAll();
		for(TavoloDBWrapper tavoloDBWrapper : tavoliDBWrapper){
			Tavolo tavolo = new Tavolo();
			tavolo.setNumero(tavoloDBWrapper.getNumero());
			
			//Recupero i posti
			ArrayList<PostoDBWrapper> postiDBWrapper = (ArrayList<PostoDBWrapper>) tavoloDBWrapper.getPosti();
			for(PostoDBWrapper postoDBWrapper : postiDBWrapper){
				Posto posto = new Posto(postoDBWrapper.getCodice(),tavolo);
				if(postoDBWrapper.getAssegnazione() != null)
					posto.setAssegnazione(new Assegnazione(postoDBWrapper.getAssegnazione().getCodiceAssegnazionePosti()));
				posto.setTavolo(new Tavolo(tavolo.getNumero()));
				posto.setCodice(postoDBWrapper.getCodice());

				//Recupero dello stato
				posto.setStato(posto.getStato(postoDBWrapper.getStato()));

				if(postoDBWrapper.getOccupazione() != null)
					posto.setOccupazione(postoDBWrapper.getOccupazione());

				tavolo.aggiungiPosti(posto);
			}
			tavoli.add(tavolo);
		}
		return tavoli;
	}
	
	public static void inserisciDatiProvaDB(){
		Tavolo t1 = new Tavolo();
		Posto p1 = new Posto("AAAA",t1);
		Posto p2 = new Posto("BBBB",t1);
		Posto p3 = new Posto("CCCC",t1);
		Posto p4 = new Posto("DDDD",t1);
		
		ic.gestoreTavoli.aggiungiTavolo(t1);

		t1.aggiungiPosti(p1,p2,p3,p4);
		t1.salva();
		p1.salva();
		p2.salva();
		p3.salva();
		p4.salva();
		
		Tavolo t2 = new Tavolo();
		Posto pp1 = new Posto("EEEE",t2);
		Posto pp2 = new Posto("FFFF",t2);
		Posto pp3 = new Posto("GGGG",t2);
		Posto pp4 = new Posto("HHHH",t2);

		t2.aggiungiPosti(pp1,pp2,pp3,pp4);
		t2.salva();
		pp1.salva();
		pp2.salva();
		pp3.salva();
		pp4.salva();
		

		ic.gestoreTavoli.aggiungiTavolo(t2);

		Tavolo t3 = new Tavolo();
		Posto ppp1 = new Posto("IIII",t3);
		Posto ppp2 = new Posto("LLLL",t3);
		Posto ppp3 = new Posto("MMMM",t3);
		Posto ppp4 = new Posto("NNNN",t3);

		t3.aggiungiPosti(ppp1,ppp2,ppp3,ppp4);
		t3.salva();
		ppp1.salva();
		ppp2.salva();
		ppp3.salva();
		ppp4.salva();


		ic.gestoreTavoli.aggiungiTavolo(t3);

		Tavolo t4 = new Tavolo();
		Posto pppp1 = new Posto("OOOO",t4);
		Posto pppp2 = new Posto("PPPP",t4);

		t4.aggiungiPosti(pppp1,pppp2);
		t4.salva();
		pppp1.salva();
		pppp2.salva();


		ic.gestoreTavoli.aggiungiTavolo(t4);
/*
		Tavolo t5 = new Tavolo();
		Posto ppppp1 = new Posto("QQQQ",t5);
		Posto ppppp2 = new Posto("RRRR",t5);
		Posto ppppp3 = new Posto("SSSS",t5);
		Posto ppppp4 = new Posto("TTTT",t5);
		Posto ppppp5 = new Posto("UUUU",t5);
		Posto ppppp6 = new Posto("VVVV",t5);
		Posto ppppp7 = new Posto("WWWW",t5);
		Posto ppppp8 = new Posto("XXXX",t5);

		t5.aggiungiPosti(ppppp1,ppppp2,ppppp3,ppppp4,ppppp5,ppppp6,ppppp7,ppppp8);
		t5.salva();
		ppppp1.salva();
		ppppp2.salva();


		ic.gestoreTavoli.aggiungiTavolo(t5);*/
		
	}

	public static void log(String s){
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("HH:mm:ss");

		System.out.println("[" + ft.format(dNow) + "] : " + s);

	}
}
