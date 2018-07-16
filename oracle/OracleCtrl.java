package dcSGDB.controles.oracle;

import java.util.List;

import dcSGDB.controles.OutilsControle;
import dcSGDB.controles.AbstractControles;


/**
 * La classe de controles types ORACLE
 * @author xcampano
 *
 */
public class OracleCtrl {

	@SuppressWarnings("unchecked")
	public static List<Class<? extends AbstractControles>> getControles() {
		return OutilsControle.creerDefinition(
				TablesViewsCGChain.class
				);
	}
}
