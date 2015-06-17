package server.entity;

import java.io.Serializable;
import java.util.ArrayList;

import server.database.AssegnazioneDBWrapper;
import server.database.PrenotazioneDBWrapper;

public class Assegnazione implements Serializable {
	
	private static final long serialVersionUID = 2152899918707217120L;
	
	String codiceAssegnazionePosti;
	ArrayList<Posto> Posti;
	Prenotazione prenotazione;

	public Assegnazione(String codiceAssegnazionePosti, ArrayList<Posto> posti) {
		this.codiceAssegnazionePosti = codiceAssegnazionePosti;
		Posti = posti;
		this.salva();
		for(Posto p : posti){
			p.assegnaPosto(this);
			p.update();
		}
	}

	public Assegnazione(String codiceAssegnazionePosti) {
		AssegnazioneDBWrapper dbWrapper = new AssegnazioneDBWrapper(codiceAssegnazionePosti);
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
