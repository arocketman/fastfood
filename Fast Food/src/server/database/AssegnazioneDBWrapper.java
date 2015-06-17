package server.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import server.database.util.HibernateUtil;
import server.entity.Posto;

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
		AssegnazioneDBWrapper wrapper = this.findByPrimaryKey(codice);
		if (wrapper != null) {
			this.codiceAssegnazionePosti = codice;
			this.Posti = wrapper.getPosti();
			this.prenotazione = wrapper.getPrenotazione();
		} else {
			throw new RuntimeException("Posto non trovato!");
		}
	}
	
	public AssegnazioneDBWrapper() {
		// TODO Auto-generated constructor stub
	}

	public AssegnazioneDBWrapper findByPrimaryKey(String codice) {
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
	
	public String getCodiceAssegnazionePosti() {
		return codiceAssegnazionePosti;
	}

	public void setCodiceAssegnazionePosti(String codiceAssegnazionePosti) {
		this.codiceAssegnazionePosti = codiceAssegnazionePosti;
	}

	public List<PostoDBWrapper> getPosti() {
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
