package dcSGDB.controles;

import java.util.HashMap;
import java.util.Map;

import dcSGDB.controles.bean.Controle;

/**
 * Classe regroupant toutes les informations qui sont passees aux controles.
 */
public class InfosControle {

	/** L'objet principal. */
	protected Object obj;
	
	/** Les objets complementaires. */
	protected Map<String,Object> objetsComplementaires = new HashMap<String, Object>();
	
	/** Le controle. */
	protected Controle controle;
	
	/** Le numero de controle. */
	protected Integer numeroCtrl=0;
	
	/** Le numero de depart. */
	protected Integer numeroCtrlDepart=0;
	
	/**
	 * Instanciation d'un nouvel objet InfosControle.
	 *
	 * @param obj L'objet principal
	 * @throws Exception Exception exception
	 */
	public InfosControle(Object obj) throws Exception {
		
		this.obj=obj;
		this.controle=new Controle(0);

	}
	
	/**
	 * Obtenir le controle.
	 *
	 * @return Le controle
	 */
	public Controle getControle() {
		return controle;
	}
	

	/**
	 * Obtenir le numero de contrôle.
	 *
	 * @return Le numero de contrôle
	 */
	public Integer getNumeroCtrl() {
		return numeroCtrl;
	}
	
	/**
	 * Modifier le numero de ctrl.
	 *
	 * @param numeroCtrl Le nouveau numero de ctrl
	 */
	public void setNumeroCtrl(Integer numeroCtrl) {
		this.numeroCtrl = numeroCtrl;
	}

	/**
	 * Obtenir le numero de départ.
	 *
	 * @return Le numero de départ
	 */
	public Integer getNumeroCtrlDepart() {
		return numeroCtrlDepart;
	}

	/**
	 * Obtenir l'objet principal.
	 *
	 * @return L'objet principal
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * Modifier le numero ctrl depart.
	 *
	 * @param numeroDepart Le nouveau numero ctrl depart
	 */
	public void setNumeroCtrlDepart(Integer numeroDepart) {
		this.numeroCtrlDepart = numeroDepart;
	}

	
	/**
	 *   
	 * Ajout un objet complementaire.
	 *
	 * @param nom Le nom de l'object compl�mentaire
	 * @param objetComplementaire L'objet complementaire
	 */
	public void putObjetComplementaire(String nom,Object objetComplementaire){
		objetsComplementaires.put(nom,objetComplementaire);
	}

	/**
	 * Obtenir l'objet secondaire.
	 *
	 * @param nom Le nom
	 * @return L'objet secondaire
	 */
	public Object getObjetComplementaire(String nom) {
		return objetsComplementaires.get(nom);
	}

}
