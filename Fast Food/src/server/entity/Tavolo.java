package server.entity;

import java.io.Serializable;
import java.util.ArrayList;

import server.database.TavoloDBWrapper;

public class Tavolo implements Serializable{
	
	private int numero;
	private ArrayList<Posto> posti;
	
	
	public Tavolo(int numero, ArrayList<Posto> posti) {
		this.numero = numero;
		this.posti = posti;
	}
	
	public Tavolo(int numero){
		TavoloDBWrapper dbWrapper = new TavoloDBWrapper(numero);
		this.numero = dbWrapper.getNumero();
		posti = dbWrapper.getPosti();
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
	
	
	
	
}
