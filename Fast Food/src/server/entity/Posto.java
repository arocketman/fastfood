package server.entity;

import java.io.Serializable;
import java.util.Date;

import server.database.AssegnazioneDBWrapper;
import server.database.PostoDBWrapper;
import server.database.TavoloDBWrapper;

public class Posto implements Serializable {
	
	private static final long serialVersionUID = 5254230672604941026L;
	
	/* Ogni posto è identificato da un codice alfanumerico di 4 caratteri */
	private String codice;
	private Tavolo tavolo;
	
	//Può anche essere null se non assegnato.
	private Assegnazione assegnazione;
	
	//Stato occupazione
	Date occupazione;
	
	//Implementazione pattern state
	private State stato;
	State poass;
	State polib;
	State poocc;
	
	public Posto(String codice, Tavolo tavolo) {
		//Il codice deve essere di 4 cifre.
		if(codice.length() != 4) 
			return;
		
		this.codice = codice;
		this.tavolo = tavolo;
		
		//Pattern state
		poass = new PostoAssegnato(this);
		polib = new PostoLibero(this);
		poocc = new PostoOccupato(this);
		
		setStato(polib);
	}
	
	public Posto(String codice){
		PostoDBWrapper postoDBWrapper = new PostoDBWrapper(codice);
		this.assegnazione = new Assegnazione(postoDBWrapper.getAssegnazione().getCodiceAssegnazionePosti());
		this.tavolo = new Tavolo(postoDBWrapper.getTavolo().getNumero());
		this.codice = postoDBWrapper.getCodice();
		
		//Recupero dello stato
		setStato(getStato(postoDBWrapper.getStato()));
	}
	
	//Implementazione del pattern state.
	
	private State getStato(String stato) {
		poass = new PostoAssegnato(this);
		polib = new PostoLibero(this);
		poocc = new PostoOccupato(this);
		
		if(stato.equalsIgnoreCase("occupato"))
			return poocc;
		else if(stato.equalsIgnoreCase("assegnato"))
			return poass;
		else 
			return polib;
	}

	public void assegnaPosto(Assegnazione assegnazione){
		stato.assegnaPosto(assegnazione);
	}
	
	public void rilasciaPosto(){
		stato.rilasciaPosto();
	}
	
	public void confermaOccupazione(){
		stato.confermaOccupazione();
	}
	
	//Getters&setters.

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}

	public Assegnazione getAssegnazione() {
		return assegnazione;
	}

	public void setAssegnazione(Assegnazione assegnazione) {
		this.assegnazione = assegnazione;
	}

	public State getStato() {
		return stato;
	}

	public void setStato(State stato) {
		this.stato = stato;
	}

	public void rimuoviAssegnazione() {
		this.assegnazione = null;
		
	}
	
	public boolean isLibero(){
		return this.stato instanceof PostoLibero;
	}
	
	public boolean isOccupato(){
		return this.stato instanceof PostoOccupato;
	}
	
	public boolean isAssegnato(){
		return this.stato instanceof PostoAssegnato;
	}
	
	public Posto salva(){
		PostoDBWrapper dbWrapper = new PostoDBWrapper();
		if(this.assegnazione != null)
			dbWrapper.setAssegnazione(new AssegnazioneDBWrapper(this.assegnazione.getCodiceAssegnazionePosti()));
		
		dbWrapper.setCodice(this.codice);
		dbWrapper.setStato(getStatoString());
		dbWrapper.setTavolo(new TavoloDBWrapper(this.getTavolo().getNumero()));
		dbWrapper.setOccupazione(occupazione);
		dbWrapper.salva();
		return this;
		
	}
	
	public Posto update() {
		PostoDBWrapper PostoDB = new PostoDBWrapper();

		PostoDB.setCodice(codice);
		if(assegnazione != null)
			PostoDB.setAssegnazione(new AssegnazioneDBWrapper(this.assegnazione.codiceAssegnazionePosti));
		PostoDB.setStato(getStatoString());
		PostoDB.setTavolo(new TavoloDBWrapper(this.tavolo.getNumero()));
		if(occupazione != null)
			PostoDB.setOccupazione(occupazione);
		PostoDB.update();
		
		return this;
	}
	
	public String getStatoString(){
		if(isLibero()) return "libero";
		else if(isOccupato()) return "occupato";
		else return "assegnato";
	}

	public Date getOccupazione() {
		return occupazione;
	}

	public void setOccupazione(Date occupazione) {
		this.occupazione = occupazione;
	}
	
	
	
}
