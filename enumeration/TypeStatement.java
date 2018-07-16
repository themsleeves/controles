package dcSGDB.controles.enumeration;

import dcSGDB.controles.strategy.AbstractInfosStrategy;
import dcSGDB.controles.strategy.DeleteInfosStrategy;
import dcSGDB.controles.strategy.InsertInfosStrategy;
import dcSGDB.controles.strategy.SelectInfosStrategy;
import dcSGDB.controles.strategy.UpdateInfosStrategy;
import net.sf.jsqlparser.statement.Statement;

/**
 * Enumeration qui permet la gestion et la construction d'une instruction typee JSQLParser
 * Seules les instructions contenant une strategie seront construites
 * Retournera un message pour les instructions non autorisees
 * @author xcampano
 *
 */
public enum TypeStatement {

	
	SELECT(net.sf.jsqlparser.statement.select.Select.class, new SelectInfosStrategy()),
	INSERT(net.sf.jsqlparser.statement.insert.Insert.class, new InsertInfosStrategy()),
	UPDATE(net.sf.jsqlparser.statement.update.Update.class, new UpdateInfosStrategy()),
	DELETE(net.sf.jsqlparser.statement.delete.Delete.class, new DeleteInfosStrategy()),
	MERGE(net.sf.jsqlparser.statement.merge.Merge.class),
	ALTER(net.sf.jsqlparser.statement.alter.Alter.class),
	REPLACE(net.sf.jsqlparser.statement.replace.Replace.class),
	CREATE_INDEX(net.sf.jsqlparser.statement.create.index.CreateIndex.class),
	CREATE_TABLE(net.sf.jsqlparser.statement.create.table.CreateTable.class),
	CREATE_VIEW(net.sf.jsqlparser.statement.create.view.CreateView.class),
	DROP(net.sf.jsqlparser.statement.drop.Drop.class),
	EXECUTE(net.sf.jsqlparser.statement.execute.Execute.class),
	TRUNCATE(net.sf.jsqlparser.statement.truncate.Truncate.class);

	
	/** L'instruction */
	private Class<? extends Statement> statement = null;
	private AbstractInfosStrategy infosStrat = null;
	
	
	/**
	 * Instanciation d'un nouveau type d'instruction.
	 */
	TypeStatement(){
	}
	
	/**
	 * Instanciation d'un nouveau type d'instruction.
	 */
	TypeStatement(Class<? extends Statement> statement){
		this();
		this.statement = statement;
	}
	
	/**
	 * Instanciation d'un nouveau type d'instruction.
	 */
	TypeStatement(Class<? extends Statement> statement, AbstractInfosStrategy infoStrat){
		this(statement);
		this.infosStrat = infoStrat;
	}
	
	/**
	 * Obtenir le type d'instruction grace à son nom.
	 *
	 * @param chaine La chaine
	 * @return Le type de l'événement
	 */
	public static TypeStatement getEnum(Statement stmt) {
		for (TypeStatement typestmt:TypeStatement.values()) {
			if (typestmt.statement.getName().equals(stmt.getClass().getName())) {
				return typestmt;
			}
		}
		return null;
	}
	
	
	/**
	 * Obtenir le decorateur d'un type d'instruction.
	 *
	 * @return Le decorateur d'un type d'instruction.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Statement getStatement() throws InstantiationException, IllegalAccessException{
		return statement.newInstance();
	}

	/**
	 * @return the mess
	 */
	public String getMess() {
		StringBuilder strb = new StringBuilder();
		if (infosStrat==null) {
			strb.append(this.name()).append(" interdit !");
		}
		
		return strb.toString();
	}

	/**
	 * @return the infosStrat
	 */
	public AbstractInfosStrategy getInfosStrat() {
		return infosStrat;
	}


}
