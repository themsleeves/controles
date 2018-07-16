package dcSGDB.controles.commun;

import dcSGDB.controles.AbstractCtrlSQL;
import dcSGDB.controles.InfosControle;

public class SeparatorCtrlChain extends AbstractCtrlSQL {

	/**
	 * Instanciation d'un nouveau SeparatorCtrlChain.
	 *
	 * @param infos Caracteristiques du controle
	 */
	public SeparatorCtrlChain(InfosControle infos) {
		super(infos);
		
	}

	public void execute() throws  NoSuchMethodException,Exception{
		super.execute();
		if (isOk()) {
			// Si ; present alors plusieurs instructions
			if (beanReq.getStmtFromList().size()>1) {
				infos.getControle().setMessage("Separateur ; interdit !");
			}else {

				String req = beanReq.getRequete();
	            // Attaque basee sur l'utilsation de commentaires
				String expr = "";
				int idx = req.indexOf("--");
				if (idx<0) {
					idx = req.indexOf("/*");
				}
				
				if (idx>0) {// Commentaire trouve
					expr = req.substring(idx, idx+2);
				}
	            
				// Pas de recherche d'attaque basee sur un temps d'attente MSS (WAIT FOR DELAY)
				// Deux possibilites : soit avec ";", soit avec commentaires en fin d'instruction...
				// Le controle effectuera donc le rejet en amont.
	            
            	if (expr.length()>0) {
            		StringBuilder lStrBldr = new StringBuilder();
					lStrBldr.append("Expression ").append(expr).append(" interdite !");
					infos.getControle().setMessage(lStrBldr.toString());
            	}
			}
		}
	}
}