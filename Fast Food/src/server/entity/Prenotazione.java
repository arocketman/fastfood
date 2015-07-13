package server.entity;

import server.database.AssegnazioneDBWrapper;
import server.database.PrenotazioneDBWrapper;

public class Prenotazione  {
	String codice;
	String telefono;
	String cognome;
	int numeroPosti;
	Assegnazione assegnazione;
	Prenotazione prenotazioneSuccessiva;
	Prenotazione prenotazionePrecedente;

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
		PrenotazioneDBWrapper dbWrapper = new PrenotazioneDBWrapper(codice);
		this.codice = dbWrapper.getCodice();
		this.telefono = dbWrapper.getTelefono();
		this.cognome = dbWrapper.getCognome();
		this.numeroPosti = dbWrapper.getNumeroPosti();
		if(this.assegnazione != null)
			this.assegnazione = new Assegnazione(dbWrapper.getAssegnazione().getCodiceAssegnazionePosti());
		if(this.prenotazioneSuccessiva != null)
			this.prenotazioneSuccessiva = new Prenotazione(dbWrapper.getPrenotazioneSuccessiva().getCodice());
		if(this.prenotazionePrecedente != null)
			this.prenotazionePrecedente = new Prenotazione(dbWrapper.getPrenotazionePrecedente().getCodice());
	}

	/**
	 * Crea un oggetto di tipo prenotazione andandolo a recuperare dal database.
	 * @param codice
	 */
	public Prenotazione(String codice,Assegnazione assegnazione) {
		PrenotazioneDBWrapper dbWrapper = new PrenotazioneDBWrapper(codice);
		this.codice = dbWrapper.getCodice();
		this.telefono = dbWrapper.getTelefono();
		this.cognome = dbWrapper.getCognome();
		this.numeroPosti = dbWrapper.getNumeroPosti();
		this.assegnazione = assegnazione;
		if(this.prenotazioneSuccessiva != null)
			this.prenotazioneSuccessiva = new Prenotazione(dbWrapper.getPrenotazioneSuccessiva().getCodice());
		if(this.prenotazionePrecedente != null)
			this.prenotazionePrecedente = new Prenotazione(dbWrapper.getPrenotazionePrecedente().getCodice());
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
		if(this.prenotazioneSuccessiva != null)
			dbWrapper.setPrenotazioneSuccessiva(new PrenotazioneDBWrapper(prenotazioneSuccessiva.getCodice()));
		if(this.prenotazionePrecedente != null)
			dbWrapper.setPrenotazionePrecedente(new PrenotazioneDBWrapper(prenotazionePrecedente.getCodice()));
		dbWrapper.salva();
		return this;
	}

	public Assegnazione getAssegnazione() {
		return assegnazione;
	}

	public void setAssegnazione(Assegnazione assegnazione) {
		this.assegnazione = assegnazione;
	}

	public Prenotazione getPrenotazioneSuccessiva() {
		return prenotazioneSuccessiva;
	}

	public void setPrenotazioneSuccessiva(Prenotazione prenotazioneSuccessiva) {
		this.prenotazioneSuccessiva = prenotazioneSuccessiva;
	}

	public Prenotazione getPrenotazionePrecedente() {
		return prenotazionePrecedente;
	}

	public void setPrenotazionePrecedente(Prenotazione prenotazionePrecedente) {
		this.prenotazionePrecedente = prenotazionePrecedente;
	}
}
