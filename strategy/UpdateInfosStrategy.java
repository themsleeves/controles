package dcSGDB.controles.strategy;

import java.util.ListIterator;

import dcSGDB.controles.bean.BeanRequete;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

public class UpdateInfosStrategy extends AbstractInfosStrategy {

	protected Update update = null;
	
	@Override
	public Statement createStatementObj(Statement stmt) {
		update = (Update) stmt;
		parseStatement();
		return update;
	}


	@Override
	protected void parseStatement() {
		
		// Recherche Sous requete dans l'update
		// UPDATE mytable SET (col1, col2, col3) = (SELECT a, b, c FROM mytable2)
		if (update.getSelect()!=null) {
        	try {
        		//System.out.println("subSelect dans UPDATE : " + update.getSelect().getSelectBody().toString());
        		beanReq.addSubStatements(new BeanRequete(update.getSelect().getSelectBody().toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Recherche sous requete dans les valeurs 
		// (ex: UPDATE mytable SET id = (SELECT 1 FROM mytable2))
		final ListIterator<Expression> it = update.getExpressions().listIterator();
		while (it.hasNext()) {
			createExpressionList(it.next());
		}
		
    	if (update.getWhere()!=null) {
    		createExpressionList(update.getWhere());
    	}
				
	}

	
}
