package dcSGDB.controles.oracle;

import java.util.Arrays;
import java.util.List;

import dcSGDB.controles.AbstractCtrlSQLTables;
import dcSGDB.controles.InfosControle;

/**
 * La Class TablesViewsCGChain Oracle.
 */
public class TablesViewsCGChain extends AbstractCtrlSQLTables {

	/**
	 * Instanciation d'un nouveau TablesViewsCGChain.
	 *
	 * @param infos Caracteristiques du controle
	 */
	public TablesViewsCGChain(InfosControle infos) {
		super(infos);
	}
	
	@Override
	protected List<String> getListeTablesViewsCGReq() throws Exception {
		return Arrays.asList(
				"SELECT view_name tblvw_name FROM user_views",
				"SELECT table_name tblvw_name FROM user_tables");
	}

}