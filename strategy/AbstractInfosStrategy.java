package dcSGDB.controles.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import dcSGDB.controles.bean.BeanRequete;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;

/**
 * Classe abstraite qui doit etre etendue dans les classes de strategies 
 * Ces classes traversent le contenu des requetes SQL pour initialiser l'objet BeanRequete
 * Les elements determinants tels que la liste des expressions dans un WHERE sont alors memorises
 * afin d'etre restitues lors des controles.
 * Les requetes imbriquees sont detectees et memorisees dans les strategies
 * 
 * @author xcampano
 *
 */
public abstract class AbstractInfosStrategy {
	
	protected BeanRequete beanReq = null;
	protected List<SelectExpressionItem> selectItemList = null;
	protected List<Expression> expressionList = null;
	
	/**
	 * Methode intermediaire pour obliger l'utilisation du synchronized
	 * (pour eviter les acces concurrents car traitement recursif)
	 * @param stmt Statement : instruction brute
	 * @param inbeanReq BeanRequete : objet requete a impacter
	 * @return Statement : une instruction typee JSQLParser (SELECT, UPDATE...)
	 */
	public synchronized Statement createStatementObj(Statement stmt, BeanRequete inbeanReq) {
		this.beanReq = inbeanReq;
		if (beanReq.getMainStatement() == null) {
			return createStatementObj(stmt);
		}
		return null;
	}
	
	/**
	 * Methode abstraite a implementer dans les strategies filles
	 * @param stmt Statement : instruction brute
	 * @return Statement : une instruction typee JSQLParser (SELECT, UPDATE...)
	 */
	protected abstract Statement createStatementObj(Statement stmt);
	
	/**
	 * Methode abstraite à implementer dans les strategies filles
	 * Repartit les differentes operations a effectuer pour obtenir les informations
	 */
	protected abstract void parseStatement();
	
	/**
	 * Methode qui effectue la lecture et l'interpretation d'une expression (ex : WHERE 1=2)
	 * @param expr Expression : element d'expression simple
	 */
	protected void createExpressionList(Expression expr) {
		expressionList = beanReq.getExpressionList();
		if (expressionList==null) {
			expressionList = new ArrayList<Expression>();
		}
		
		expr.accept(new ExpressionVisitorAdapter() {

				@Override
		        protected void visitBinaryExpression(BinaryExpression expr) {
					super.visitBinaryExpression(expr); 
		            if (expr instanceof ComparisonOperator) {
		            	expressionList.add(expr);
		            }
		        }
				
				@Override
				public void visit(SubSelect subSelect) {
					super.visit(subSelect);
					//System.out.println("subSelect dans WHERE : " + subSelect.toString());
					try {
						beanReq.addSubStatements(new BeanRequete(subSelect.getSelectBody().toString()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void visit(ExistsExpression expr) {
					super.visit(expr);
					beanReq.setEXISTS();
				}
				
				@Override
				public void visit(CaseExpression arg0) {
					super.visit(arg0);
					beanReq.setCASEWHEN();
				}
				
				
		});
		
		beanReq.setExpressionList(expressionList);
	}
	
	/**
	 * Methode qui effectue la lecture et l'interpretation du FROM
	 * @param ps PlainSelect : corps de select
	 */
	protected void createFromItemList(PlainSelect ps) {
		ps.getFromItem().accept(new FromItemVisitor() {

	        @Override
	        public void visit(SubJoin subJoin) {
	        }

	        @Override
	        public void visit(SubSelect subSelect) {
	        	try {
	        		//System.out.println("subSelect dans FROM : " + subSelect.toString());
	        		beanReq.addSubStatements(new BeanRequete(subSelect.getSelectBody().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }

	        @Override
	        public void visit(Table table) {
	        }

			@Override
			public void visit(LateralSubSelect lateralSubSelect) {
	        	try {
	        		//System.out.println("LateralSubSelect dans FROM : " + lateralSubSelect.getSubSelect().toString());
	        		beanReq.addSubStatements(new BeanRequete(
							lateralSubSelect.getSubSelect().getSelectBody().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void visit(ValuesList valuesList) {
			}

			@Override
			public void visit(TableFunction tableFunction) {
			}
	    });
	}
	
	/**
	 * Methode qui effectue la lecture et l'interpretation des colonnes de requete
	 * @param ps PlainSelect : corps de select
	 */
	protected void createItemList(PlainSelect ps) {
		selectItemList = beanReq.getSelectItemList();
		if (selectItemList==null) {
			selectItemList = new ArrayList<SelectExpressionItem>();	
		}

		final ListIterator<SelectItem> iterator = ps.getSelectItems().listIterator();
		while (iterator.hasNext()) {
			iterator.next().accept(new SelectItemVisitor() {
				@Override
				public void visit(AllColumns allColumns) {
					beanReq.setSelectAll(true);
				}

				@Override
				public void visit(AllTableColumns allTableColumns) {
					beanReq.setSelectAll(true);
				}

				@Override
				public void visit(SelectExpressionItem selectExpressionItem) {
					selectItemList.add(selectExpressionItem);
					createExpressionList(selectExpressionItem.getExpression());
				}

			});
		}

		beanReq.setSelectItemList(selectItemList);
	}
}
