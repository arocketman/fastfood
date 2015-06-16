package server.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.collection.internal.PersistentBag;

import server.database.util.HibernateUtil;
import server.entity.Posto;

@Entity
@Table(name = "tavolo")
public class TavoloDBWrapper {
	
	@Id
	@GeneratedValue
	private int numero;
	
	@OneToMany(mappedBy = "tavolo",fetch = FetchType.EAGER)
	private List<PostoDBWrapper> posti;

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

	public List<PostoDBWrapper> getPosti() {
		if(posti instanceof PersistentBag)
			return new ArrayList<PostoDBWrapper>(posti);
		return posti;
	}

	public void setPosti(ArrayList<PostoDBWrapper> posti) {
		this.posti = posti;
	}
	
	public static ArrayList<TavoloDBWrapper> findAll(){
		ArrayList<TavoloDBWrapper>ret = new ArrayList<TavoloDBWrapper>();
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from TavoloDBWrapper");
		ret = (ArrayList<TavoloDBWrapper>)query.list();
		
		//chiudo la transazione e la sessione
		session.getTransaction().commit();		
		session.close();
		
		return ret;
	}
	
	

}
