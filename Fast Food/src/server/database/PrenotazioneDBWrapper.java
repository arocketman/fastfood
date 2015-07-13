package server.database;

import org.hibernate.*;
import server.database.util.HibernateUtil;

import javax.persistence.*;
import java.util.ArrayList;

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
	
	@OneToOne
	PrenotazioneDBWrapper prenotazioneSuccessiva;
	
	@OneToOne
	PrenotazioneDBWrapper prenotazionePrecedente;
	
	public PrenotazioneDBWrapper(String codice) {
		PrenotazioneDBWrapper wrapper = this.findByPrimaryKey(codice);
		if (wrapper != null) {
			this.codice = codice;
			this.telefono = wrapper.getTelefono();
			this.cognome = wrapper.getCognome();
			this.numeroPosti = wrapper.getNumeroPosti();
			this.assegnazione = wrapper.getAssegnazione();
			this.prenotazioneSuccessiva = wrapper.getPrenotazioneSuccessiva();
			this.prenotazionePrecedente = wrapper.getPrenotazionePrecedente();
		} else {
			throw new RuntimeException("Prenotazione non trovato!");
		}
		//System.out.println(wrapper.prenotazioneSuccessiva);
	}
	
	public PrenotazioneDBWrapper() {
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
		this.updatePreview();
		return this;
	}
	
	public Boolean updatePreview(){
		PrenotazioneDBWrapper prenotazionePrecedenteDBWrapper=this.getPrenotazionePrecedente();
		if(prenotazionePrecedenteDBWrapper !=null){
			if(this.getAssegnazione()==null){
				prenotazionePrecedenteDBWrapper.setPrenotazioneSuccessiva(new PrenotazioneDBWrapper(this.codice));
			}else{
				prenotazionePrecedenteDBWrapper.setPrenotazioneSuccessiva(this.getPrenotazioneSuccessiva());
			}
			System.out.println("["+this.getCodice()+"] updatePreview -> ["+prenotazionePrecedenteDBWrapper.getCodice()+"]");
			//apro la sessione e la transazione
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			session.update(prenotazionePrecedenteDBWrapper);

			//chiudo la transazione e la sessione
			session.getTransaction().commit();
			session.close();
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public Boolean updateNext(){
		PrenotazioneDBWrapper prenotazioneSuccessivaDBWrapper=this.getPrenotazioneSuccessiva();
		if(prenotazioneSuccessivaDBWrapper !=null){
			if(this.getAssegnazione()==null){
				prenotazioneSuccessivaDBWrapper.setPrenotazionePrecedente(new PrenotazioneDBWrapper(this.codice));
			}else{
				prenotazioneSuccessivaDBWrapper.setPrenotazionePrecedente(this.getPrenotazionePrecedente());
			}
			System.out.println("["+this.getCodice()+"] updateNext -> ["+prenotazioneSuccessivaDBWrapper.getCodice()+"]");
			//apro la sessione e la transazione
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			session.update(prenotazioneSuccessivaDBWrapper);

			//chiudo la transazione e la sessione
			session.getTransaction().commit();
			session.close();
			return true;
		}else{
			return false;
		}
	}

	public static ArrayList<PrenotazioneDBWrapper> findAll(){
		ArrayList<PrenotazioneDBWrapper>ret = new ArrayList<>();

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		org.hibernate.Query query = session.createQuery("from PrenotazioneDBWrapper");
		ret = (ArrayList<PrenotazioneDBWrapper>)query.list();

		//chiudo la transazione e la sessione
		session.getTransaction().commit();
		session.close();

		return ret;
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

	public PrenotazioneDBWrapper update() {
		System.out.println("["+this.getCodice()+"] update");
		this.updatePreview();
		this.updateNext();
		if(this.assegnazione!=null){
			this.setPrenotazionePrecedente(null);
			this.setPrenotazioneSuccessiva(null);
		}
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

	public PrenotazioneDBWrapper getPrenotazioneSuccessiva() {
		return prenotazioneSuccessiva;
	}

	public void setPrenotazioneSuccessiva(
			PrenotazioneDBWrapper prenotazioneSuccessiva) {
		this.prenotazioneSuccessiva = prenotazioneSuccessiva;
	}

	public PrenotazioneDBWrapper getPrenotazionePrecedente() {
		return prenotazionePrecedente;
	}

	public void setPrenotazionePrecedente(
			PrenotazioneDBWrapper prenotazionePrecedente) {
		this.prenotazionePrecedente = prenotazionePrecedente;
	}
	
}
