package pe.com.admequipo.agileqa.util;


import java.util.Calendar;
import java.util.List;

import javax.crypto.spec.DESedeKeySpec;

import pe.com.admequipo.stepdefinition.util.dao.CCLDBUtil;

public class Utilitario {
	
	public static String desencriptar(String CodigoEncriptado, byte [] llaveDescencriptar){
	  	 //  logger.info("[---------------- Inicio de desencriptamiento   ----------------]");
	  	   String codigoDesencriptado = null;   
	   	  try {
	   		  	   	  	  
	       	  DESedeKeySpec keySpe = new DESedeKeySpec(llaveDescencriptar); 
	       	  codigoDesencriptado = TripleDESEncryption.decrypt(CodigoEncriptado, keySpe);
	       //	  logger.info("Codigo encriptado: "+codigoDesencriptado);
	       	    	  
	   	  }catch (Exception e) {   		  
	   		  System.out.println("No se pudo desencriptar"+e);	   		
	   	  }
		  	System.out.println("LLAVE DE DESENCRIPTACION................."+llaveDescencriptar+"...................\n");
		  	System.out.println("CODIGO A  DESENCRIPTAR .................."+CodigoEncriptado+".........\n");
		  	System.out.println("CODIGO DESENCRIPTADO....................."+codigoDesencriptado+"......\n");
		  	System.out.println("[---------------- Fin de desencriptamiento   ----------------]");
		  	
	     return codigoDesencriptado;
		  	
	}	
	
	public static void main(String[] args)  {
		
		System.out.println("_____");
		System.out.println(obtenerUltimoUserToken("01", "66666666"));
		System.out.println("__FIN___");
		
		//desencriptar("15857315B3944F2613163791F47F29A431EC9D4968A4E961", "SqWCSslUzadNrt+g/LW9sA==");
	}
	
	
	public static String obtenerUltimoUserToken(String tipoDoc,String numDoc) {

		String sql = "select * from " + "(SELECT  B.USTKV_CLAVE, B.USTKR_KEY,B.USTKN_TIPO_CEL_MAIL  FROM CUNCT_USUARIO_TOKEN B "
				+ "where B.USTKN_IDEN_USU=(SELECT B.USUPN_USU_PRE_ID "+
   " FROM CUNCT_USUARIO_PRE B   WHERE  B.USUPV_TIP_DOC = '"+tipoDoc+"' AND B.USUPV_NUM_DOC = '"+numDoc+"') order by ustkd_fec_proc desc) where rownum=1";
		CCLDBUtil ccldbUtil = new CCLDBUtil();
		List<Object[]> result = ccldbUtil.executeQuery(sql);

		String token = "";
		if (result.size() > 0) {
			String valorClave = result.get(0)[0].toString();
			byte[] key = (byte[]) result.get(0)[1];
			int tipoCelMail=Integer.valueOf(result.get(0)[2].toString());
			if(tipoCelMail==1){
			token= valorClave;
			}else{
			token = desencriptar(valorClave, key);
			}

		}
		System.out.println("retornando token " + token);

		return token;

	}
	
	public static int vencerUltimoUserToken(String tipoDoc,String numDoc) {
		int resp=0;
	String sql = "(select USTKV_CLAVE from " + "(SELECT  B.USTKV_CLAVE, B.USTKR_KEY,B.USTKN_TIPO_CEL_MAIL  FROM CUNCT_USUARIO_TOKEN B "
				+ "where B.USTKN_IDEN_USU=(SELECT B.USUPN_USU_PRE_ID "+
   " FROM CUNCT_USUARIO_PRE B   WHERE  B.USUPV_TIP_DOC = '"+tipoDoc+"' AND B.USUPV_NUM_DOC = '"+numDoc+"') order by ustkd_fec_proc desc) where rownum=1)";
		CCLDBUtil ccldbUtil = new CCLDBUtil();
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		java.sql.Date fechaPasada=new java.sql.Date(c.getTime().getTime());
		String sqlUpdate="UPDATE CUNCT_USUARIO_TOKEN set USTKD_FEC_CADUCA=? where USTKV_CLAVE="+sql;
		resp=ccldbUtil.executePrepared(sqlUpdate, fechaPasada);
		System.out.println("se actualizo "+resp+" registro(s) con el token fecha  " + fechaPasada);
		//}
	
		
		return resp;

	}


}
