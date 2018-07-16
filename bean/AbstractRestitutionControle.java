package dcSGDB.controles.bean;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe abstraite contenant la base pour restituer les contrôles ou rapports de contrôles.
 */
public abstract class AbstractRestitutionControle {

	/** Le numero ctrl. */
	protected Integer numeroCtrl;
	
	/** Le prochain ctrl. */
	protected Integer prochainCtrl;
	
	/** Le has next. */
	protected Boolean hasNext;

	
	/**
	 * Instanciation d'un nouveau controle.
	 */
	public AbstractRestitutionControle() {
		numeroCtrl=0;
	}



	/**
	 * Instanciation d'un nouveau controle.
	 *
	 * @param numeroCtrl Le numero ctrl
	 */
	public AbstractRestitutionControle(Integer numeroCtrl) {
		this.numeroCtrl=numeroCtrl;
	}
		
	/**
	 * Modifier le prochain ctrl.
	 *
	 * @param prochainCtrl Le nouveau prochain ctrl
	 */
	public void setProchainCtrl(Integer prochainCtrl) {
		this.prochainCtrl = prochainCtrl;
	}




	/**
	 * Modifier le checks for next.
	 *
	 * @param hasNext Le nouveau checks for next
	 */
	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}


	/**
	 * Obtenir le checks for next.
	 *
	 * @return Le checks for next
	 */
	@XmlTransient
	public Boolean getHasNext() {
		return hasNext;
	}

	/**
	 * Obtenir le prochain ctrl.
	 *
	 * @return Le prochain ctrl
	 */
	@XmlTransient
	public Integer getProchainCtrl() {
		return prochainCtrl;
	}

	/**
	 * Obtenir le numero ctrl.
	 *
	 * @return Le numero ctrl
	 */
	@XmlTransient
	public Integer getNumeroCtrl() {
		return numeroCtrl;
	}

	/**
	 * Modifier le numero.
	 *
	 * @param numeroCtrl Le nouveau numero
	 */
	public void setNumeroCtrl(Integer numeroCtrl) {
		this.numeroCtrl=numeroCtrl;
	}

	
}