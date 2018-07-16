package dcSGDB.controles;

/**
 * La Class SuiteControle.
 */
public class SuiteControle {

	
	protected Integer numeroCtrlDepart;

	
	protected Integer numeroCtrlTraite;

	/**
	 * Instanciation d'un nouveau suite controle.
	 *
	 * @throws Exception Exception exception
	 */
	public SuiteControle() throws Exception {
		this.numeroCtrlDepart = 0;
		this.numeroCtrlTraite = 0;
	}

	/**
	 * Obtenir le numero ctrl depart.
	 *
	 * @return Le numero ctrl depart
	 */
	public Integer getNumeroCtrlDepart() {
		return numeroCtrlDepart;
	}

	/**
	 * Obtenir le numero ctrl traite.
	 *
	 * @return Le numero ctrl traite
	 */
	public Integer getNumeroCtrlTraite() {
		return numeroCtrlTraite;
	}

	/**
	 *   
	 * Incremente numero ctrl traite.
	 *
	 * @param numeroCtrlTraite Le nouveau numero traite
	 */
	public void setNumeroCtrlTraite(Integer numeroCtrlTraite) {
		this.numeroCtrlTraite = numeroCtrlTraite;
	}
}
