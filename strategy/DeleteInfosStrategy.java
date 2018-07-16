package dcSGDB.controles.strategy;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;

/**
 * Classe concrete qui effectue l'initialisation de l'objet BeanRequete (et sous-objets).
 * Implementation ciblee sur la strategie DELETE de JSQLParser
 * @author xcampano
 *
 */
public class DeleteInfosStrategy extends AbstractInfosStrategy  {

	
	protected Delete delete = null;

	@Override
	public Statement createStatementObj(Statement stmt) {
		delete = (Delete) stmt;
		parseStatement();
		return delete;
	}

	protected void parseStatement() {
    	if (delete.getWhere()!=null) {
    		createExpressionList(delete.getWhere());
    	}
	}

}
