package server.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import server.database.util.HibernateUtil;

@Entity
@Table(name = "Prenotazioni")
public class PrenotazioneDBWrapper {

	@Id
	String codice;
	
	@Column
	String telefono;
	
	@Column
	String cognome;
	
	@Column
	int numeroPosti;
	
	@OneToOne
	AssegnazioneDBWrapper assegnazione;
	
	public PrenotazioneDBWrapper(String codice) {
		PrenotazioneDBWrapper wrapper = this.findByPrimaryKey(codice);
		if (wrapper != null) {
			this.codice = codice;
			this.telefono = wrapper.getTelefono();
			this.cognome = wrapper.getCognome();
			this.numeroPosti = wrapper.getNumeroPosti();
			this.assegnazione = wrapper.getAssegnazione();
		} else {
			throw new RuntimeException("Posto non trovato!");
		}
	}
	
	public PrenotazioneDBWrapper() {
		// TODO Auto-generated constructor stub
	}

	public PrenotazioneDBWrapper findByPrimaryKey(String codice) {
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		PrenotazioneDBWrapper b = (PrenotazioneDBWrapper)session.get(PrenotazioneDBWrapper.class, codice);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return b;
	}
	
	public PrenotazioneDBWrapper salva()
	{
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		//salvo la prenotazione
		String codice = (String) session.save(this);
		this.setCodice(codice);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return this;
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

	public AssegnazioneDBWrapper getAssegnazione() {
		return assegnazione;
	}

	public void setAssegnazione(AssegnazioneDBWrapper assegnazione) {
		this.assegnazione = assegnazione;
	}
	
	
}
