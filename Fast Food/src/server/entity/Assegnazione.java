package server.entity;

import server.database.AssegnazioneDBWrapper;
import server.database.PrenotazioneDBWrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class Assegnazione implements Serializable {
	
	private static final long serialVersionUID = 2152899918707217120L;
	
	String codiceAssegnazionePosti;
	ArrayList<Posto> Posti;
	Prenotazione prenotazione;

	/**
	 * Crea un oggetto di tipo Assegnazione. Non interagisce con il DB.
	 * @param codiceAssegnazionePosti il codice di assegnazione.
	 * @param posti i posti appartenenti alla prenotazione.
	 */
	public Assegnazione(String codiceAssegnazionePosti, ArrayList<Posto> posti) {
		this.codiceAssegnazionePosti = codiceAssegnazionePosti;
		Posti = posti;
		this.salva();
		for(Posto p : posti){
			p.assegnaPosto(this);
			p.update();
		}
	}

	/**
	 * Crea un oggetto di tipo assegnazione andandolo a recuperare dal database.
	 * @param codiceAssegnazionePosti il codice dell'assegnazione che si vuole andare a recuperare.
	 */
	public Assegnazione(String codiceAssegnazionePosti) {
		AssegnazioneDBWrapper dbWrapper = new AssegnazioneDBWrapper(codiceAssegnazionePosti);
		if(prenotazione != null)
			this.prenotazione = new Prenotazione(dbWrapper.getPrenotazione().getCodice());
		this.codiceAssegnazionePosti = dbWrapper.getCodiceAssegnazionePosti();
	}

	public String getCodiceAssegnazionePosti() {
		return codiceAssegnazionePosti;
	}

	public void setCodiceAssegnazionePosti(String codiceAssegnazionePosti) {
		this.codiceAssegnazionePosti = codiceAssegnazionePosti;
	}
	
	public int getNumeroTavolo(){
		return Posti.get(0).getTavolo().getNumero();
	}

	/**
	 * Ritorna i codici dei posti appartenenti alla prenotazione in formato String.
	 * @return
	 */
	public String getCodiciPosti(){
		String str = "";
		for(Posto p : Posti){
			str += p.getCodice() + ",";
		}
		return str;
	}
	
	public Assegnazione salva(){
		AssegnazioneDBWrapper dbWrapper = new AssegnazioneDBWrapper();
		dbWrapper.setCodiceAssegnazionePosti(this.codiceAssegnazionePosti);
		if(this.prenotazione != null)
			dbWrapper.setPrenotazione(new PrenotazioneDBWrapper(prenotazione.getCodice()));
		dbWrapper.salva();
		return this;
	}

	public Prenotazione getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public ArrayList<Posto> getPosti() {
		return Posti;
	}

	public void setPosti(ArrayList<Posto> posti) {
		Posti = posti;
	}
	
	
}
