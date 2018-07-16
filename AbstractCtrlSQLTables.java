package dcSGDB.controles;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import dcSGDB.facadeServices;
import dcSGDB.facadeServices_i;
import dcUsers.User;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * Classe abstraite qui doit etre etendue dans les classes de controle type SQL
 * pour gestion des tables dans la requete. 
 * Centralise les methodes pour lister les tables de l'application et de la requete
 * Utilise l'instruction principale de la requete pour effectuer la recherche de tables
 * @author xcampano
 *
 */
public abstract class AbstractCtrlSQLTables extends AbstractCtrlSQL {
	
	public AbstractCtrlSQLTables(InfosControle infos) {
		super(infos);
	}
	
	/**
	 * Methode abstraite a implementer avec la requete en fonction du SGBD
	 * @return List<String> : Liste des requetes à utiliser pour obtenir les tables de l'application
	 * @throws Exception
	 */
	protected abstract List<String> getListeTablesViewsCGReq() throws Exception;

	/**
	 * Methode qui recherche la liste des tables de l'application
	 * Elle s'appuie sur la liste de requetes orientees SGBD retournee par getListeTablesViewsCGReq()
	 * @return List<String> : Liste des tables de l'application (tables autorisees)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<String> getListeTablesViewsCG() throws Exception {
		final String MOT_CLE = "LISTE_TABVIEWCG";
		List<String> listeTabViewCG = (List<String>) infos.getObjetComplementaire(MOT_CLE);
		if (listeTabViewCG == null) {
			// ----- Connexion Services RMI -----
			facadeServices_i serviceReq = facadeServices.getInstance();
			listeTabViewCG = serviceReq.getListeTablesViewsCG();
			
			if (listeTabViewCG == null) {
				listeTabViewCG = new ArrayList<String>();
				
				// -- Creation objet utilisateur valide --
				User uSrv = new User("kekaha");
				uSrv.setTrusted(true);
				
				Vector<Hashtable<String, String>> rangees = null;
				// Construction requete conditionnee par le type SGBD...
				List<String> sqlStringList = getListeTablesViewsCGReq();
				for (String req : sqlStringList){
					rangees = serviceReq.getDataList(uSrv, req, 1, 1000, false, false);
					for (Hashtable<String, String> element : rangees) {
						listeTabViewCG.add((String)element.get("tblvw_name").toUpperCase());
					}
				}
				serviceReq.putListeTablesViewsCG(listeTabViewCG);
			}
			infos.putObjetComplementaire(MOT_CLE, listeTabViewCG);
		}
		return listeTabViewCG;
	}
	
	/**
	 * Methode qui effectue un controle sur les tables presentes dans la requete
	 * @return List<String> : liste des tables non autorisees
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<String> getTableNonAutorisee() throws Exception {
		final String MOT_CLE = "LISTE_TABVKO";
		List<String> listeTabViewKO = (List<String>) infos.getObjetComplementaire(MOT_CLE);
		if (listeTabViewKO == null) {
			listeTabViewKO = new ArrayList<String>();
			// Recherche pour chaque table remontee par le parser : si elle n'est pas presente
			// dans la liste des tables de l'application elle est non autorisee !
			List<String> listeTabViewCG = getListeTablesViewsCG();
			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
			List<String> listeTabViewSQL = tablesNamesFinder.getTableList(beanReq.getMainStatement());
			for (Iterator<String> it = listeTabViewSQL.iterator(); it.hasNext();) {
				 String table = (it.next()).toUpperCase();
				 if (!listeTabViewCG.contains(table)) {
					 listeTabViewKO.add(table);
				 }
			}
			
			infos.putObjetComplementaire(MOT_CLE, listeTabViewKO);
		}
		return listeTabViewKO;
	}
	
	/**
	 * Verification qu'il existe au moins une table dans la liste autorisee.
	 * @return Vrai si au moins une table presente
	 * @throws Exception
	 */
	protected boolean hasTableNonAutorisee() throws Exception {
		return !getTableNonAutorisee().isEmpty();
	}
	
	@Override
	public void execute() throws NoSuchMethodException, Exception {
		super.execute();
		if (isOk()) {
			if (hasTableNonAutorisee()) {// Controle si une table non autorisee dans la requete
				StringBuilder strBldr = new StringBuilder();
				strBldr.append("Table(s) non autorisee(s) dans la requete : ")
					.append(getTableNonAutorisee().toString());
				infos.getControle().setMessage(strBldr.toString());	
			}
		}
	}
	
}
