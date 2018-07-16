package dcSGDB.controles;

import java.util.List;

import dcSGDB.controles.bean.Controle;
import dcSGDB.controles.AbstractControles;

/**
 * Façade de contrôle.
 * Tous les contrôles doivent passer par cette facade.
 * @author mro
 *
 */
public class CtrlSuiteFacade extends CtrlFacade {

	/**
	 * Executer les controles.
	 *
	 * @param controleSuite Le controle suite
	 * @param infos Caractéristiques du contrôle
	 * @param controles Les maillons du controle
	 * @return Le controle
	 * @throws NoSuchMethodException Exception lors de la recherche dynamique d'une méthode d'un objet
	 * @throws Exception Exception générique
	 */
	private static Controle executeControles(SuiteControle controleSuite, InfosControle infos, List<Class<? extends AbstractControles>> controles)
			throws NoSuchMethodException, Exception {
		/*
		 * Passage en numéro de départ relatif au controle local (en référentiel 1-n !)
		 */
		Integer nombreDeControlesPrecedents = controleSuite.getNumeroCtrlTraite();
//		System.out.println("executeControles : controleSuite.getNumeroCtrlDepart()=" + controleSuite.getNumeroCtrlDepart() + " - controleSuite.getNumeroCtrlTraite()="
//				+ controleSuite.getNumeroCtrlTraite());
		Integer numeroCtrlDepartRelatif = Math.max(1, controleSuite.getNumeroCtrlDepart() - nombreDeControlesPrecedents);
//		System.out.println("executeControles : numeroCtrlDepartRelatif=" + numeroCtrlDepartRelatif);
		//
		Controle controle;
		if (numeroCtrlDepartRelatif > controles.size()) {
			/*
			 * On bypass les controles locaux si inférieur au numéro de départ
			 */
			Integer newNumeroCtrlTraiteGlobal = controles.size()+nombreDeControlesPrecedents;
			controleSuite.setNumeroCtrlTraite(newNumeroCtrlTraiteGlobal);
			//
			controle = new Controle(0);
		} else {
			/*
			 * Controles locaux
			 */
			/*
			 * Controles locaux
			 */
			infos.setNumeroCtrlDepart(numeroCtrlDepartRelatif);
			controle = executeControles(infos, controles);
			/*
			 * Incrementation du numero de controle global
			 */
			Integer newNumeroCtrlTraiteGlobal = controle.getNumeroCtrl() + nombreDeControlesPrecedents;
			controleSuite.setNumeroCtrlTraite(newNumeroCtrlTraiteGlobal);
			if (!controle.estOk()) {
				/*
				 * Si controle KO : Révision des numéros dans le contecxte de la suite globale 
				 */
				controle.setNumeroCtrl(newNumeroCtrlTraiteGlobal);
				Integer newNumeroProchainGlobal = (controle.getProchainCtrl() == null || controle.getProchainCtrl() == 0 ? 0 : controle.getProchainCtrl() + nombreDeControlesPrecedents);
				controle.setProchainCtrl(newNumeroProchainGlobal);
			}
		}
//		System.out.println("executeControles FIN : controleSuite.getNumeroCtrlDepart()=" + controleSuite.getNumeroCtrlDepart() + " - controleSuite.getNumeroCtrlTraite()="
//				+ controleSuite.getNumeroCtrlTraite());
		return controle;
	}

	/**
	 * Executer les contrôles.
	 *
	 * @param controleSuite Le controle suite
	 * @param controles La liste controles à exécuter
	 * @param asso Association utilisée par le CRUD
	 * @param obj L'objet à tester
	 * @param oldObj L'ancien objet
	 * @param cles Valeur de chaque identifiant
	 * @param action Type d'action AJOUT/MODIFICATION/SUPPRESSION
	 * @param moment Le moment
	 * @return Le résultat du controle
	 * @throws Exception Exception exception
	 */
	public static Controle execute(SuiteControle controleSuite, List<Class<? extends AbstractControles>> controles, Object obj) throws Exception {
		InfosControle infos = new InfosControle(obj);

		return executeControles(controleSuite, infos, controles);
	}



}
