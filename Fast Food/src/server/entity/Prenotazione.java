package server.entity;

public class Prenotazione {
	String codice;
	String telefono;
	String cognome;
	int numeroPosti;
	
	public Prenotazione(String codice, String telefono, String cognome,int numeroPosti) {
		this.codice = codice;
		this.telefono = telefono;
		this.cognome = cognome;
		this.numeroPosti = numeroPosti;
	}
	
	
}
