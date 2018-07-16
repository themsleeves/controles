/*
 * 
 */
package dcSGDB.controles.bean;

/**
 * La Class Controle.
 */
public class Controle extends AbstractRestitutionControle {
	
	/** Le message. */
	private String message = null;
	private Boolean hasNext;


	/**
	 * Instanciation d'un nouveau controle.
	 *
	 * @param numeroCtrl Le numero ctrl
	 */
	public Controle(Integer numeroCtrl) {
		this.numeroCtrl = numeroCtrl;
	}
	
	/**
	 * Modifier le message erreur.
	 *
	 * @param message Le nouveau message erreur
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Obtenir le message.
	 *
	 * @return Le message
	 */
	public String getMessage() {
		return message; 
	}

	/**
	 * Est ok.
	 *
	 * @return True, si c'est correct
	 */
	public boolean estOk() {
		return (message == null);
	}
	
	/**
	 * Modifier le checks for next.
	 *
	 * @param hasNext Le nouveau checks for next
	 */
	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Boolean getHasNext(){
		return hasNext;
	}
}