package dcSGDB.controles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import dcSGDB.exceptionKekahaGenerique;
import dcSGDB.facadeServices;
import dcSGDB.controles.bean.BeanRequete;
import dcSGDB.controles.bean.Controle;
import dcSGDB.controles.commun.CommunCtrl;
import dcSGDB.controles.oracle.OracleCtrl;
import dcSGDB.controles.sqlserver.SqlServerCtrl;

/**
 * Classe outils pour les controles.
 * @author xcampano
 *
 */
public class OutilsControle {

	/**
	 * Creer la definition des controles.
	 *
	 * @param classes Le classes
	 * @return the list< class<? extends abstract controles>>
	 */
	public static List<Class<? extends AbstractControles>> creerDefinition(Class<? extends AbstractControles> ... classes) {
		List<Class<? extends AbstractControles>> liste = new ArrayList<Class<? extends AbstractControles>>();
		for(Class<? extends AbstractControles> classe:classes){
			liste.add(classe);
		}
		return liste;
	}
	
	
	/**
	 * Controle des injections : Point d'entree des controles sur le contenu de la requete SQL
	 * @param typeSGBD : chaine type de moteur BDD utilise
	 * @param cursorSQL : chaine requete SQL
	 * @return le message d'erreur s'il existe sinon chaine vide
	 * @throws exceptionKekahaGenerique
	 */
	public static String ctrlInjection(String typeSGBD, String cursorSQL) throws exceptionKekahaGenerique {
		String ret = "";
		try {
			
			SuiteControle ctrlSuite = new SuiteControle();
			BeanRequete mainBeanReq = new BeanRequete(cursorSQL);
			
			// Controles communs
			Controle ctrl = CtrlSuiteFacade.execute(ctrlSuite, CommunCtrl.getControles(), mainBeanReq);

			if (ctrl.estOk()) {
				// Controles specifiques SGBD pour liste des tables...
				if ((typeSGBD).equals(facadeServices.getInstance().SGBD_ORACLE)) { //***** ORACLE *****
					ctrl = CtrlSuiteFacade.execute(ctrlSuite, OracleCtrl.getControles(), mainBeanReq);
					
				}else if ((typeSGBD).equals(facadeServices.getInstance().SGBD_MSSQLSERVER)) { //***** SQL Server *****
					ctrl = CtrlSuiteFacade.execute(ctrlSuite, SqlServerCtrl.getControles(), mainBeanReq);
					
				}
			}
			
			if (!ctrl.estOk()) {// Rejet de la requete principale
				ret = ctrl.getMessage();
			}else {// Suite des controles...
				ret = ctrlInjectionSuite(mainBeanReq);
			}
			
		} catch (Exception e) {
			throw new exceptionKekahaGenerique("Erreur dans ctrlInjection... " + e.getMessage(), exceptionKekahaGenerique.errSGBDdown);
		}
		return ret;
	}
	
	
	/**
	 * Suite des controles injections : traitement recursif de controles communs...
	 * Utilisation pour traitement des requetes imbriquees (detectees et memorisees dans les strategies)
	 * @param beanReq : BeanRequete contenant les infos d'une requete SQL
	 * @return le message d'erreur s'il existe sinon chaine vide
	 * @throws Exception
	 */
	public static String ctrlInjectionSuite(BeanRequete beanReq) throws Exception {
		String ret="";
		if (beanReq.getSubStatements()!=null) {
			ListIterator<BeanRequete> iterator = beanReq.getSubStatements().listIterator();
			while (iterator.hasNext()) {
				BeanRequete subBeanReq = iterator.next(); 
				subBeanReq.setParent();
				Controle subCtrl = CtrlFacade.execute(CommunCtrl.getControles(), subBeanReq);
				if (!subCtrl.estOk()) {// Rejet de la requete
					ret = subCtrl.getMessage();
					break;
				}else {// Relance du controle pour recusivite
					ret = ctrlInjectionSuite(subBeanReq);
				}
			}
			if (ret.length()==0) {
				beanReq.clearSubStatements();
			}
		}
		return ret;
	}	
	
	
	/**
	 * Verification si chaine est de type entier
	 * @param str : chaine a tester
	 * @return vrai si entier
	 */
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
	
}
