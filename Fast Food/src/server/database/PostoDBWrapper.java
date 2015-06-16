package server.database;

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
@Table(name="Posto")
public class PostoDBWrapper {
		
	@Id
	private String codice;
	
	@ManyToOne
	@JoinColumn(name = "numero_tavolo")
	private TavoloDBWrapper tavolo;
	
	@ManyToOne
	@JoinColumn(name = "codice_assegnazione")
	private AssegnazioneDBWrapper assegnazione;
	
	@Column(name = "stato")
	private String stato;
	

	public PostoDBWrapper(String codice) {
		PostoDBWrapper wrapper = this.findByPrimaryKey(codice);
		if (wrapper != null) {
			this.codice = codice;
			this.tavolo = wrapper.getTavolo();
			this.assegnazione = wrapper.getAssegnazione();
			this.stato = wrapper.getStato();
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

		//salvo il cliente
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

	public TavoloDBWrapper getTavolo() {
		return tavolo;
	}

	public void setTavolo(TavoloDBWrapper tavolo) {
		this.tavolo = tavolo;
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
		
	
}
