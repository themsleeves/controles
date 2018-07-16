package dcSGDB.controles.sqlserver;

import java.util.Arrays;
import java.util.List;

import dcSGDB.controles.AbstractCtrlSQLTables;
import dcSGDB.controles.InfosControle;

/**
 * La Class TablesViewsCGChain SqlServer.
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
				"SELECT TABLE_NAME tblvw_name FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW'",
				"SELECT TABLE_NAME tblvw_name FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'");
	}

}