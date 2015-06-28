package server.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.collection.internal.PersistentBag;
import server.database.util.HibernateUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Assegnazioni")
public class AssegnazioneDBWrapper {
	
	@Id
	String codiceAssegnazionePosti;
	
	@OneToMany(mappedBy="assegnazione",fetch = FetchType.EAGER)
	List<PostoDBWrapper> Posti;
	
	@OneToOne
	PrenotazioneDBWrapper prenotazione;

	public AssegnazioneDBWrapper(String codice) {
		AssegnazioneDBWrapper wrapper = findByPrimaryKey(codice);
		if (wrapper != null) {
			this.codiceAssegnazionePosti = codice;
			this.Posti = wrapper.getPosti();
			this.prenotazione = wrapper.getPrenotazione();
		} else {
			throw new RuntimeException("Posto non trovato!");
		}
	}

	public AssegnazioneDBWrapper() {
	}

	public static AssegnazioneDBWrapper findByPrimaryKey(String codice) {
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		AssegnazioneDBWrapper b = (AssegnazioneDBWrapper)session.get(AssegnazioneDBWrapper.class, codice);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return b;
	}
	
	public AssegnazioneDBWrapper salva()
	{
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		//salvo il cliente
		String codice = (String) session.save(this);
		this.setCodiceAssegnazionePosti(codice);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return this;
	}

	public AssegnazioneDBWrapper update() {
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		session.update(this);

		//chiudo la transazione e la sessione
		session.getTransaction().commit();
		session.close();

		return this;
	}

	public String getCodiceAssegnazionePosti() {
		return codiceAssegnazionePosti;
	}

	public void setCodiceAssegnazionePosti(String codiceAssegnazionePosti) {
		this.codiceAssegnazionePosti = codiceAssegnazionePosti;
	}

	public List<PostoDBWrapper> getPosti() {
		if(Posti instanceof PersistentBag)
			return new ArrayList<PostoDBWrapper>(Posti);
		return Posti;
	}

	public void setPosti(ArrayList<PostoDBWrapper> posti) {
		Posti = posti;
	}

	public PrenotazioneDBWrapper getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(PrenotazioneDBWrapper prenotazione) {
		this.prenotazione = prenotazione;
	}


	
	
	
	
	

}
