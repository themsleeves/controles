package dcSGDB.controles.sqlserver;

import java.util.List;

import dcSGDB.controles.OutilsControle;
import dcSGDB.controles.AbstractControles;


/**
 * La classe de controles types SQL Server
 * @author xcampano
 *
 */
public class SqlServerCtrl {

	@SuppressWarnings("unchecked")
	public static List<Class<? extends AbstractControles>> getControles() {
		return OutilsControle.creerDefinition(
				TablesViewsCGChain.class
				);
	}
}
