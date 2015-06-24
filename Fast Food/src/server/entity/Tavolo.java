package server.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import server.database.TavoloDBWrapper;

import java.util.ArrayList;

public class Tavolo{
	
	private int numero;
	private ArrayList<Posto> posti;
	
	
	public Tavolo(int numero, ArrayList<Posto> posti) {
		this.numero = numero;
		this.posti = posti;
	}
	
	public Tavolo(int numero){
		TavoloDBWrapper dbWrapper = new TavoloDBWrapper(numero);
		this.numero = dbWrapper.getNumero();
	}
	
	public Tavolo() {
		posti = new ArrayList<Posto>();
	}

	public void aggiungiPosto(Posto posto){
		posti.add(posto);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public ArrayList<Posto> getPosti() {
		return posti;
	}

	public void setPosti(ArrayList<Posto> posti) {
		this.posti = posti;
	}
	
	public void aggiungiPosti(Posto ... posti){
		for(Posto p : posti){
			this.posti.add(p);
		}
	}
	
	public Tavolo salva(){
		TavoloDBWrapper dbWrapper = new TavoloDBWrapper();
		dbWrapper.salva();
		this.numero = dbWrapper.getNumero();
		return this;
	}

	public JsonObject impacchettaPerClient(){
		JsonObject tavoloJson = new JsonObject();
		tavoloJson.addProperty("numero", String.valueOf(this.numero));
		JsonArray jsonArray = new JsonArray();
		for(Posto p : posti){
			jsonArray.add(p.impacchettaPerClient());
		}
		tavoloJson.add("posti",jsonArray);
		return tavoloJson;
	}
		
}
