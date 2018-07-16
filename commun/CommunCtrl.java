package dcSGDB.controles.commun;

import java.util.List;

import dcSGDB.controles.OutilsControle;
import dcSGDB.controles.AbstractControles;



/**
 * La classe de controles communs aux requetes SQL
 * @author xcampano
 *
 */
public class CommunCtrl {

	/**
	 * Retourne la liste des contrôles a executer pour une requete SQL
	 *
	 * @return the controles
	 */
	@SuppressWarnings("unchecked")
	public static List<Class<? extends AbstractControles>> getControles() {
		return OutilsControle.creerDefinition(
				SeparatorCtrlChain.class,
				SelectCtrlChain.class,
				ExpressionCtrlChain.class
				);
	}
}
