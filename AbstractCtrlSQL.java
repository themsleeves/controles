package dcSGDB.controles;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import dcSGDB.controles.bean.BeanRequete;
import dcSGDB.controles.enumeration.TypeStatement;
import dcSGDB.controles.strategy.AbstractInfosStrategy;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.statement.Statement;

/**
 * Classe abstraite qui doit etre etendue dans les classes de controle type SQL
 * Elle contient un certain nombre de comportements, de preparations utiles lors de controles sur requetes SQL
 * Entre autres elle fourni : un parseur de requete (JSQLParser), les informations disponibles avec l'objet BeanRequete
 * et effectue egalement un controle de premier niveau sur l'instruction principale (voir dcSGDB.controles.enumeration.TypeStatement)
 * @author xcampano
 *
 */
public abstract class AbstractCtrlSQL extends AbstractControles {
	
	private StringBuilder strBldr = new StringBuilder();
	
	// Instance du parseur de requetes
	protected CCJSqlParser SQLParser = null;
	// Objet contenant les information de la requete
	protected BeanRequete beanReq;
	
	/**
	 * Constructeur pour tout controle de type SQL
	 * @param infos Objet InfoControle passant de classe en classe
	 */
	public AbstractCtrlSQL(InfosControle infos) {
		super(infos);
		beanReq = (BeanRequete)infos.getObj();
		SQLParser = beanReq.getSQLParser();
		if (SQLParser==null) {
			SQLParser = new CCJSqlParser(new StringReader(beanReq.getRequete()));
			beanReq.setSQLParser(SQLParser);
		}
	}

	/**
	 * Methode d'initialisation de l'objet requete principal via des strategies (voir SelectInfosStrategy pour ex.)
	 * Elle effectue en meme temps un controle sur l'instruction principale 
	 * @param stmt : instruction non typee qui doit etre interpretee
	 * @return Statement le type d'instruction du parseur (SELECT, INSERT, ...)
	 */
	protected Statement getStatementObj(Statement stmt) {
		strBldr.setLength(0);
		
		AbstractInfosStrategy tmpStmt = null;
		// Enumeration contenant la liste des instructions et leurs eventuelles strategies
		TypeStatement type = TypeStatement.getEnum(stmt);
		beanReq.setTypeStmt(type);
		
		String mess = type.getMess();
		if (mess.length()>0) {// message present si pas de strategie pour l'instruction
			strBldr.append(type.getMess());
			return null;
		}else {
			// Recuperation strategie correspondante à l'instruction
			tmpStmt = type.getInfosStrat();
			// Execution et initialisation via la strategie
			return tmpStmt.createStatementObj(stmt, beanReq);
		}
	}
	
	public void execute() throws  NoSuchMethodException, Exception {
		if (isOk()) {
			Statement mainStatement = beanReq.getMainStatement();
			if (mainStatement == null) {
				List<Statement> stmtFromList = new ArrayList<Statement>();
				stmtFromList.addAll(SQLParser.Statements().getStatements());
				// Memorisation liste d'instructions brute
				beanReq.setStmtFromList(stmtFromList);
				if (stmtFromList.size()==1) {
					mainStatement = getStatementObj(stmtFromList.get(0));
					if (mainStatement == null) {
						// Instruction non autorisée
						infos.getControle().setMessage(strBldr.toString());
					}else{
						beanReq.setMainStatement(mainStatement);
					}
				}
			}
		}
	}
	
	
}
