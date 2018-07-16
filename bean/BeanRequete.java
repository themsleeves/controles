package dcSGDB.controles.bean;

import java.util.ArrayList;
import java.util.List;

import dcSGDB.controles.enumeration.TypeStatement;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

/**
 * Classe de gestion d'un objet requete SQL
 * @author xcampano
 *
 */
public class BeanRequete {

	
	/**
	 * Chaine correspondante à la requete
	 */
	private String requete; 
	
	/**
	 * Memorisation du parser JSQLParser
	 */
	private CCJSqlParser SQLParser = null;
	
	/**
	 * Instruction principale
	 */
	private Statement mainStatement = null;
	
	/**
	 * Enumeration du type d'instruction
	 */
	private TypeStatement typeStmt = null;
	
	/**
	 * Liste des instruction brutes
	 */
	private List<Statement> stmtFromList = null;
	
	/**
	 * Liste des expressions (ex: 1=2 dans un WHERE)
	 */
	private List<Expression> expressionList = null;
	
	/**
	 * Liste des elements dans un SELECT
	 */
	private List<SelectExpressionItem> selectItemList = null;
	
	/**
	 * Presence ou non d'un SELECT *
	 */
	private boolean selectAll = false;

	/**
	 * Presence ou non d'un UNION
	 */
	private boolean unionAll = false;
	
	/**
	 * Presence ou non d'un EXISTS
	 */
	private boolean isEXISTS = false;
	
	/**
	 * Presence ou non d'un CASE WHEN
	 */
	private boolean isCASEWHEN = false;
	
	/**
	 * Presence ou non d'une requete mere
	 */
	private boolean hasParent = false;
	
	/**
	 * Liste des requetes imbriquees
	 */
	private List<BeanRequete> subStatements = null;
	
	
	public BeanRequete(String req) {
		requete = (String) req;
	}
	
	/**
	 * @return the sQLParser
	 */
	public CCJSqlParser getSQLParser() {
		return SQLParser;
	}

	/**
	 * @param sQLParser the sQLParser to set
	 */
	public void setSQLParser(CCJSqlParser sQLParser) {
		SQLParser = sQLParser;
	}
	
		/**
	 * @return the mainStatement
	 */
	public Statement getMainStatement() {
		return mainStatement;
	}

	/**
	 * @param mainStatement the mainStatement to set
	 */
	public void setMainStatement(Statement mainStatement) {
		this.mainStatement = mainStatement;
	}

	/**
	 * @return the stmtFromList
	 */
	public List<Statement> getStmtFromList() {
		return stmtFromList;
	}

	/**
	 * @param stmtFromList the stmtFromList to set
	 */
	public void setStmtFromList(List<Statement> stmtFromList) {
		this.stmtFromList = stmtFromList;
	}

	/**
	 * @return the requete
	 */
	public String getRequete() {
		return requete;
	}

	/**
	 * @param requete the requete to set
	 */
	public void setRequete(String requete) {
		this.requete = requete;
	}

	/**
	 * @return the leftExprList
	 */
	public List<Expression> getExpressionList() {
		return expressionList;
	}

	/**
	 * @param leftExprList the leftExprList to set
	 */
	public void setExpressionList(List<Expression> expressionList) {
		this.expressionList = expressionList;
	}

	/**
	 * @return the selectItemList
	 */
	public List<SelectExpressionItem> getSelectItemList() {
		return selectItemList;
	}

	/**
	 * @param selectItemList the selectItemList to set
	 */
	public void setSelectItemList(List<SelectExpressionItem> selectItemList) {
		this.selectItemList = selectItemList;
	}
	
	/**
	 * @return boolean : vrai si presence d'un SELECT *
	 */
	public boolean isSelectAll() {
		return selectAll;
	}

	/**
	 * Positionner la presence du SELECT *
	 * @param selectAll the selectAll to set
	 */
	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	/**
	 * @return the subStatements
	 */
	public List<BeanRequete> getSubStatements() {
		return subStatements;
	}

	public void addSubStatements(BeanRequete subStatement) {
		if (subStatements == null) {
			subStatements = new ArrayList<BeanRequete>();
		}
		subStatements.add(subStatement);
	}
	
	public void clearSubStatements() {
		subStatements.clear();
	}

	/**
	 * @return boolean : vrai si la requete est fille
	 */
	public boolean hasParent() {
		return hasParent;
	}

	public void setParent() {
		this.hasParent = true;
	}

	/**
	 * @return the type
	 */
	public TypeStatement getTypeStmt() {
		return typeStmt;
	}

	/**
	 * @param type the type to set
	 */
	public void setTypeStmt(TypeStatement typeStmt) {
		this.typeStmt = typeStmt;
	}

	/**
	 * @return the unionAll
	 */
	public boolean isUnionAll() {
		return unionAll;
	}

	public void setUnionAll() {
		this.unionAll = true;
	}

	/**
	 * @return the isEXISTS
	 */
	public boolean isEXISTS() {
		return isEXISTS;
	}

	public void setEXISTS() {
		this.isEXISTS = true;
	}

	/**
	 * @return the isCASEWHEN
	 */
	public boolean isCASEWHEN() {
		return isCASEWHEN;
	}

	public void setCASEWHEN() {
		this.isCASEWHEN = true;
	}
}
