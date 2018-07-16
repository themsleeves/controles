package dcSGDB.controles;

import java.util.ArrayList;
import java.util.List;

import dcSGDB.controles.bean.Controle;
import dcSGDB.controles.AbstractControles;

/**
 * Façade de contrôle.
 * Tous les contrôles doivent passer par cette facade.
 * @author mro
 *
 */
public class CtrlFacade {


	/**
	 * Executer les contrôles.
	 *
	 * @param controles La liste controles à exécuter
	 * @param asso Association utilisée par le CRUD
	 * @param obj L'objet à tester
	 * @param oldObj L'ancien objet
	 * @param parametres Paramètres récupérés dans l’appel du service
	 * @param cles Valeur de chaque identifiant
	 * @param action Type d'action AJOUT/MODIFICATION/SUPPRESSION
	 * @param moment Le moment
	 * @return Le résultat du controle
	 * @throws Exception Exception exception
	 */
	public static Controle execute(List<Class<? extends AbstractControles>> controles,Object obj) throws Exception {
		InfosControle infos=new InfosControle(obj);
		
		Controle controle = executeControles(infos, controles);
		return controle;
	}
	
	
	/**
	 * Executer les controles.
	 *
	 * @param infos Caractéristiques du contrôle
	 * @param controles Les maillons du controle
	 * @return Le controle
	 * @throws Exception Exception générique
	 * @throws NoSuchMethodException 
	 */
	protected static Controle executeControles(InfosControle infos, List<Class<? extends AbstractControles>>  controles) throws NoSuchMethodException, Exception{
		int numeroDepart = infos.getNumeroCtrlDepart();
		int indiceDepart = numeroDepart==0?0:numeroDepart-1;
		int numeroFin = controles.size();
		
		List<Class<? extends AbstractControles>> controlesAEffectuer;
		if(indiceDepart > controles.size()){
			controlesAEffectuer = new ArrayList<Class<? extends AbstractControles>>();
		}else{
			controlesAEffectuer = controles.subList(indiceDepart, controles.size());
		}
		
		infos.setNumeroCtrl(indiceDepart); /* indiceDepart au lieu de numeroDepart car le numéro du contrôle sera incrémentaer lors de sa création */
		
		// Itération du controle numeroCtrl au dernier controle
		// sauf si un controle est donné positif
		for(Class<? extends AbstractControles> classControle:controlesAEffectuer){
			// On instancie le controle
			AbstractControles controle = classControle.getDeclaredConstructor(InfosControle.class).newInstance(infos);
			boolean hasNext = infos.getNumeroCtrl()!=numeroFin;
			infos.getControle().setHasNext(hasNext);
			// On lance le controle
			controle.execute();
			if(!controle.isOk()){
				break;
			}
		}
		
		return infos.getControle();
	}
	
}
