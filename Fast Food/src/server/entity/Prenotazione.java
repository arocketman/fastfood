package server.entity;

import server.database.AssegnazioneDBWrapper;
import server.database.PrenotazioneDBWrapper;

public class Prenotazione  {
	String codice;
	String telefono;
	String cognome;
	int numeroPosti;
	Assegnazione assegnazione;

	/**
	 * Crea una nuova prenotazione. Non interagisce con il DB.
	 * @param codice codice prenotazione.
	 * @param cognome il cognome del cliente.
	 * @param telefono numero di telefono del cliente.
	 * @param numeroPosti il numero dei posti richiesti.
	 */
	public Prenotazione(String codice, String cognome, String telefono,int numeroPosti) {
		this.codice = codice;
		this.telefono = telefono;
		this.cognome = cognome;
		this.numeroPosti = numeroPosti;
	}

	/**
	 * Crea un oggetto di tipo prenotazione andandolo a recuperare dal database.
	 * @param codice
	 */
	public Prenotazione(String codice) {
		//TODO: RIVEDERE.
		PrenotazioneDBWrapper dbWrapper = new PrenotazioneDBWrapper(codice);
		this.codice = dbWrapper.getCodice();
		this.telefono = dbWrapper.getTelefono();
		this.cognome = dbWrapper.getCognome();
		this.numeroPosti = dbWrapper.getNumeroPosti();
		this.assegnazione = new Assegnazione(dbWrapper.getAssegnazione().getCodiceAssegnazionePosti());
	}

	/**
	 * Crea un oggetto di tipo prenotazione andandolo a recuperare dal database.
	 * @param codice
	 */
	public Prenotazione(String codice,Assegnazione assegnazione) {
		//TODO: RIVEDERE.
		PrenotazioneDBWrapper dbWrapper = new PrenotazioneDBWrapper(codice);
		this.codice = dbWrapper.getCodice();
		this.telefono = dbWrapper.getTelefono();
		this.cognome = dbWrapper.getCognome();
		this.numeroPosti = dbWrapper.getNumeroPosti();
		this.assegnazione = assegnazione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getNumeroPosti() {
		return numeroPosti;
	}

	public void setNumeroPosti(int numeroPosti) {
		this.numeroPosti = numeroPosti;
	}

	/**
	 * Salva la prenotazione su DB. Interagisce don PrenotazioneDBWrapper.
	 * @return
	 */
	public Prenotazione salva(){
		PrenotazioneDBWrapper dbWrapper = new PrenotazioneDBWrapper();
		dbWrapper.setCodice(this.codice);
		if(this.assegnazione != null)
			dbWrapper.setAssegnazione(new AssegnazioneDBWrapper(assegnazione.getCodiceAssegnazionePosti()));
		dbWrapper.setTelefono(telefono);
		dbWrapper.setNumeroPosti(numeroPosti);
		dbWrapper.setCognome(cognome);
		dbWrapper.salva();
		return this;
	}
	
	
}
