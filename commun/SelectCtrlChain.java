package dcSGDB.controles.commun;

import dcSGDB.controles.AbstractCtrlSQL;
import dcSGDB.controles.InfosControle;
import dcSGDB.controles.enumeration.TypeStatement;

public class SelectCtrlChain extends AbstractCtrlSQL {

	/**
	 * Instanciation d'un nouveau SelectAllCtrlChain.
	 *
	 * @param infos Caracteristiques du controle
	 */
	public SelectCtrlChain(InfosControle infos) {
		super(infos);
		
	}

	public void execute() throws  NoSuchMethodException,Exception{
		super.execute();
		if (isOk()) {
			if (beanReq.hasParent()){
				TypeStatement sstype = beanReq.getTypeStmt(); 
				switch (sstype) {
				case INSERT:
				case UPDATE:
				case DELETE:
					StringBuilder strb = new StringBuilder();
					strb.append(sstype.name()).append(" interdit !");
					infos.getControle().setMessage(strb.toString());
					break;
				default:
					break;
				}
			}
			if (beanReq.isSelectAll()) {
				infos.getControle().setMessage("SELECT * interdit !"); 
			}else 
			if (beanReq.isUnionAll()) {
				infos.getControle().setMessage("UNION interdit !");
			}else
			if (beanReq.isEXISTS()) {
				infos.getControle().setMessage("EXISTS interdit !");
			}else
			if (beanReq.isCASEWHEN()) {
				infos.getControle().setMessage("CASE WHEN interdit !");
			}
		}
	}

}
