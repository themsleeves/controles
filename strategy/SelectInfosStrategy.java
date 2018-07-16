package dcSGDB.controles.strategy;

import java.util.Iterator;
import java.util.List;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperation;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.UnionOp;
import net.sf.jsqlparser.statement.select.WithItem;

/**
 * Classe concrete qui effectue l'initialisation de l'objet BeanRequete (et sous-objets).
 * Implementation ciblee sur la strategie SELECT de JSQLParser
 * @author xcampano
 *
 */
public class SelectInfosStrategy extends AbstractInfosStrategy {

	protected Select select = null;
	
	@Override
	public Statement createStatementObj(Statement stmt) {
		select = (Select) stmt;
		parseStatement();
		return select;
	}

	@Override
	protected void parseStatement() {

		select.getSelectBody().accept(new SelectVisitor() {
		    @Override
		    public void visit(PlainSelect ps) {
		    	if (ps.getWhere()!=null) {
		    		createExpressionList(ps.getWhere());
		    	}
		    	createItemList(ps);
		    	createFromItemList(ps);
		    }

		    @Override
		    public void visit(SetOperationList soList) {
		    	// Liste des corps de SELECT
				List<SelectBody> selBList = soList.getSelects();
				for (Iterator<SelectBody> it = selBList.iterator(); it.hasNext();) {
					SelectBody selectBody = it.next();
					if (selectBody instanceof PlainSelect) {
						visit((PlainSelect) selectBody);
					}
				}
				// Liste des operations (UNION,INTERSECT,MINUS,EXCEPT)
				List<SetOperation> opList = soList.getOperations();
				for (Iterator<SetOperation> it = opList.iterator(); it.hasNext();) {
					SetOperation op = it.next();
					if (op instanceof UnionOp) {
						beanReq.setUnionAll();
					}
				}
		    }

		    @Override
		    public void visit(WithItem wi) {
		    }
		    
		});
		
	}
	

	
}
