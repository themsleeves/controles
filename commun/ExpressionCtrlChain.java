package dcSGDB.controles.commun;

import java.util.ListIterator;

import dcSGDB.controles.AbstractCtrlSQL;
import dcSGDB.controles.InfosControle;
import dcSGDB.controles.OutilsControle;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.schema.Column;

public class ExpressionCtrlChain extends AbstractCtrlSQL {

	/**
	 * Instanciation d'un nouveau ExpressionCtrlChain
	 *
	 * @param infos Caracteristiques du controle
	 */
	public ExpressionCtrlChain(InfosControle infos) {
		super(infos);
		
	}

	public void execute() throws  NoSuchMethodException,Exception{
		super.execute();
		if (isOk()) {
			if (beanReq.getExpressionList()!=null) {
				
				final ListIterator<Expression> iterator = beanReq.getExpressionList().listIterator();
				while (iterator.hasNext()) {
					Expression expr = iterator.next();
					expr.accept(new ExpressionVisitorAdapter() {
						
						@Override
				        protected void visitBinaryExpression(BinaryExpression expr) {
							super.visitBinaryExpression(expr); 
							
							
							boolean ok=true;
							StringBuilder lStrBldr = new StringBuilder();
				            if (expr instanceof ComparisonOperator) {
				            	
				            	String op = expr.getStringExpression().toString().trim();
				            	String left = expr.getLeftExpression().toString();
				            	String right = expr.getRightExpression().toString();
				            	
				            	if (expr.getLeftExpression() instanceof StringValue ||
				            		expr.getLeftExpression() instanceof Column) {
				            		left = left.substring(1, left.length()-1);
				            	}
				            	if (expr.getRightExpression() instanceof StringValue ||
				            		expr.getRightExpression() instanceof Column) {
				            		right = right.substring(1, right.length()-1);
				            	}
				            	
				            	if (left.equals(right) && "=".equals(op)){
									ok=false;
				            	}
				            	else if ((expr.getLeftExpression() instanceof LongValue ||
				            		expr.getLeftExpression() instanceof DoubleValue ||
				            		(OutilsControle.isInteger(left) && OutilsControle.isInteger(right)))
				            			&& ("=".equals(op) || "<".equals(op) || ">".equals(op) 
				            			|| (op.contains("<") && op.contains(">"))
				            			|| (op.contains("!") && op.contains("=")) 
				            			)){
				            		ok=false;
				            	}
				            	else if ((expr.getLeftExpression() instanceof Column &&
				            			expr.getRightExpression() instanceof Column) ||
				            			(expr.getLeftExpression() instanceof StringValue &&
				            			expr.getLeftExpression() instanceof StringValue)) {
				            		if ("<".equals(op)) {
					            		if (left.compareTo(right)<0) {
					            			ok=false;
					            		}	
				            		}else
				            		if (">".equals(op)) {
					            		if (right.compareTo(left)<0) {
					            			ok=false;
					            		}	
				            		}else
				            		if ((op.contains("<") && op.contains(">"))) {
					            		if (!right.equals(left)) {
					            			ok=false;
					            		}	
				            		}else
				            		if ((op.contains("!") && op.contains("="))) {
					            		if (!right.equals(left)) {
					            			ok=false;
					            		}
				            		}
				            	}
				            }

				            
			            	if (!ok) {
			            		lStrBldr.setLength(0);
								lStrBldr.append("Expression ").append(expr.toString()).append(" interdite !");
								infos.getControle().setMessage(lStrBldr.toString());
			            	}
				            
				        }
						
					});
					
					if (!isOk()) {
						break;
					}
				}
			}
		}
	}
}
