package server.entity;

import server.database.AssegnazioneDBWrapper;
import server.database.PrenotazioneDBWrapper;

public class Prenotazione {
	String codice;
	String telefono;
	String cognome;
	int numeroPosti;
	Assegnazione assegnazione;
	
	public Prenotazione(String codice, String cognome, String telefono,int numeroPosti) {
		this.codice = codice;
		this.telefono = telefono;
		this.cognome = cognome;
		this.numeroPosti = numeroPosti;
	}

	public Prenotazione(String codice) {
		PrenotazioneDBWrapper dbWrapper = new PrenotazioneDBWrapper();
		this.codice = dbWrapper.getCodice();
		this.telefono = dbWrapper.getTelefono();
		this.cognome = dbWrapper.getCognome();
		this.numeroPosti = dbWrapper.getNumeroPosti();
		this.assegnazione = new Assegnazione(dbWrapper.getAssegnazione().getCodiceAssegnazionePosti());
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
