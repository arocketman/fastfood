package server.entity;

import com.google.gson.JsonObject;
import server.database.AssegnazioneDBWrapper;
import server.database.PostoDBWrapper;
import server.database.TavoloDBWrapper;

import java.util.Date;

public class Posto {

	/* Ogni posto è identificato da un codice alfanumerico di 4 caratteri */
	private String codice;
	private Tavolo tavolo;
	
	//Può anche essere null se non assegnato.
	private Assegnazione assegnazione;
	
	//Stato occupazione
	Date occupazione;
	
	/* Implementazione pattern state */
	private State stato;
	State poass;
	State polib;
	State poocc;

	/**
	 * Costruttore per Posto, senza accesso al database. Crea un oggetto Posto con codice e tavolo assengati e avente stato iniziale Libero.
	 * @param codice il codice del posto.
	 * @param tavolo il tavolo a cui il posto appartiene.
	 */
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
		if(postoDBWrapper.getAssegnazione() != null)
			this.assegnazione = new Assegnazione(postoDBWrapper.getAssegnazione().getCodiceAssegnazionePosti());
		this.tavolo = new Tavolo(postoDBWrapper.getTavolo().getNumero());
		this.codice = postoDBWrapper.getCodice();
		
		//Recupero dello stato
		setStato(getStato(postoDBWrapper.getStato()));
	}

	/**
	 * Ritorna un oggetto della classe State in base alla stringa in ingresso. Metodo di utilità per corretto funzionamento del pattern State.
	 * @param stato stringa rappresentante lo stato , può essere 'occupato' , 'assegnato' , 'libero'.
	 * @return
	 */
	public State getStato(String stato) {
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

	/**
	 * Salva l'entita' posto su DB. Interagisce con PostoDBWrapper.
	 * @return
	 */
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

	/**
	 * Aggiorna l'entità posto su DB.
	 * @return
	 */
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

	/**
	 * Ritorna lo stato del posto in forma di stringa.
	 * @return
	 */
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


	public JsonObject impacchettaPerClient() {
		JsonObject postoJson = new JsonObject();
		postoJson.addProperty("codice",codice);
		postoJson.addProperty("tavolo", String.valueOf(tavolo.getNumero()));
		postoJson.addProperty("stato",this.getStatoString());
		postoJson.addProperty("occupazione", String.valueOf(occupazione));
		return postoJson;
	}
}
