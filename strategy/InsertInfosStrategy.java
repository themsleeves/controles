package dcSGDB.controles.strategy;

import java.util.ListIterator;

import dcSGDB.controles.bean.BeanRequete;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

public class InsertInfosStrategy extends AbstractInfosStrategy {

	protected Insert insert = null;

	@Override
	public Statement createStatementObj(Statement stmt) {
		insert = (Insert) stmt;
		parseStatement();
		return insert;
	}

	@Override
	protected void parseStatement() {
		
		// Recherche Sous requete dans l'insert
		// (ex: INSERT INTO MyTable (id, name) SELECT id, name FROM SomeView)
		if (insert.getSelect()!=null) {
        	try {
        		//System.out.println("subSelect dans INSERT : " + insert.getSelect().getSelectBody().toString());
        		beanReq.addSubStatements(new BeanRequete(insert.getSelect().getSelectBody().toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Recherche sous requete dans les valeurs 
		// (ex: INSERT INTO mytable (id, name) VALUES (1, SELECT 1 FROM mytable2))
		final ListIterator<Expression> it = ((ExpressionList)insert.getItemsList()).getExpressions().listIterator();
		while (it.hasNext()) {
			createExpressionList(it.next());
		}
		
		// Recherche sous requete dans les sets 
		// (ex: INSERT INTO mytable SET col1 = (SELECT 1 FROM mytable2))
		final ListIterator<Expression> iterator = insert.getSetExpressionList().listIterator();
		while (iterator.hasNext()) {
			createExpressionList(iterator.next());
		}
	}

}
