package dcSGDB.controles;

/**
 * Classe abstraite qui doit être étendue par toutes les classes de contrôle.<BR>
 * Elle contient une certain nombre de contrôles qui peuvent être communs aux différentes classes de contrôles.
 * 
 * @author erodriguez
 *
 */
 public abstract class AbstractControles {


	
	/** Les données du contrôle. */
	protected InfosControle infos;
	
	/**
	 * Méthode abstraite à implémenter qui contient le code du ou des contrôles à effectuer.
	 * @throws NoSuchMethodException exception
	 * @throws Exception exception
	 */
	public abstract void execute() throws NoSuchMethodException,Exception;
	
	/**
	 * Contructeur de la méthode qui doit être éxécuté dans tous les constructeurs des classes contrôles.<BR>
	 * => super(objet infoControle)
	 * @param infos Objet InfoControle passant de classe en classe
	 */
	public AbstractControles(InfosControle infos) {
		this.infos=infos;
	}

	/**
	 * Méthode à lancer entre deux contrôle afin d'éviter d'éxécuter n contrôle si le précédent à échoué.
	 * @return true ou false
	 */
	public boolean isOk() {
		return infos.getControle().estOk();
	}

	/** 
	 * Setteur de l'objet infos.
	 * @param infos Classe infosControle
	 */
	public void setInfos(InfosControle infos) {
		this.infos = infos;
	}
	
	
}