package server.entity;

import java.io.Serializable;
import java.util.ArrayList;

import server.database.AssegnazioneDBWrapper;

public class Assegnazione implements Serializable {
	
	private static final long serialVersionUID = 2152899918707217120L;
	
	String codiceAssegnazionePosti;
	ArrayList<Posto> Posti;

	public Assegnazione(String codiceAssegnazionePosti, ArrayList<Posto> posti) {
		this.codiceAssegnazionePosti = codiceAssegnazionePosti;
		Posti = posti;
		for(Posto p : posti){
			p.assegnaPosto(this);
		}
	}

	public Assegnazione(String codiceAssegnazionePosti) {
		AssegnazioneDBWrapper dbWrapper = new AssegnazioneDBWrapper(codiceAssegnazionePosti);
		this.codiceAssegnazionePosti = dbWrapper.getCodiceAssegnazionePosti();
		this.Posti = dbWrapper.getPosti();
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
		dbWrapper.salva();
		return this;
	}


	
	
	

	
}
