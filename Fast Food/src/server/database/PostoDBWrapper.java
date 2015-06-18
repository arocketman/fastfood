package server.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import server.database.util.HibernateUtil;

@Entity
@Table(name="Posti")
public class PostoDBWrapper {
		
	@Id
	private String codice;
	
	@ManyToOne
	@JoinColumn(name = "numero_tavolo")
	private TavoloDBWrapper tavoloPosti;
	
	@ManyToOne
	@JoinColumn(name = "codice_assegnazione")
	private AssegnazioneDBWrapper assegnazione;
	
	@Column(name = "stato")
	private String stato;
	
	@Column
	Date occupazione;
	

	public PostoDBWrapper(String codice) {
		PostoDBWrapper wrapper = this.findByPrimaryKey(codice);
		if (wrapper != null) {
			this.codice = codice;
			this.tavoloPosti = wrapper.getTavolo();
			this.assegnazione = wrapper.getAssegnazione();
			this.stato = wrapper.getStato();
			this.occupazione = wrapper.getOccupazione();
		} else {
			throw new RuntimeException("Posto non trovato!");
		}
	}
	
	public PostoDBWrapper() {
		// TODO Auto-generated constructor stub
	}

	public PostoDBWrapper findByPrimaryKey(String codice) {
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		PostoDBWrapper b = (PostoDBWrapper)session.get(PostoDBWrapper.class, codice);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return b;
	}
	
	public PostoDBWrapper salva()
	{
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		//salvo il posto
		String codice = (String) session.save(this);
		this.setCodice(codice);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return this;
	}
	
	public PostoDBWrapper update() {
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

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public TavoloDBWrapper getTavolo() {
		return tavoloPosti;
	}

	public void setTavolo(TavoloDBWrapper tavolo) {
		this.tavoloPosti = tavolo;
	}

	public AssegnazioneDBWrapper getAssegnazione() {
		return assegnazione;
	}

	public void setAssegnazione(AssegnazioneDBWrapper assegnazione) {
		this.assegnazione = assegnazione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getOccupazione() {
		return occupazione;
	}

	public void setOccupazione(Date occupazione) {
		this.occupazione = occupazione;
	}
		
	
	
}
