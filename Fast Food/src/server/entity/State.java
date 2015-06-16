/*
 * Implementazione del pattern state.
 */

package server.entity;

public abstract class State {
	int state;
	Posto posto;
	
	public State(Posto p){
		this.posto = p;
	}
	
	public State(){
		
	}
	
	public abstract void assegnaPosto(Assegnazione assegnazione);
	public abstract void rilasciaPosto();
	public abstract void confermaOccupazione();

}
