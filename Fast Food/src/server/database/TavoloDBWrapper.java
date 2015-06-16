package server.database;

import java.util.ArrayList;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import server.database.util.HibernateUtil;
import server.entity.Posto;

public class TavoloDBWrapper {
	
	@Id
	@GeneratedValue
	private int numero;
	
	@OneToMany(mappedBy = "tavolo")
	private ArrayList<Posto> posti;

	public TavoloDBWrapper(int numero) {
		TavoloDBWrapper wrapper = this.findByPrimaryKey(numero);
		if (wrapper != null) {
			this.numero = numero;
			this.posti = wrapper.getPosti();
		} else {
			throw new RuntimeException("Posto non trovato!");
		}
	}
	
	public TavoloDBWrapper() {
		// TODO Auto-generated constructor stub
	}

	public TavoloDBWrapper findByPrimaryKey(int numero) {
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		TavoloDBWrapper b = (TavoloDBWrapper)session.get(TavoloDBWrapper.class, numero);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return b;
	}
	
	public TavoloDBWrapper salva()
	{
		//apro la sessione e la transazione
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		//salvo il cliente
		int numero = (int) session.save(this);
		this.setNumero(numero);
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return this;
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
	
	

}
