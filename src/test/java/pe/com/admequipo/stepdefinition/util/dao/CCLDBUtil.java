package pe.com.admequipo.stepdefinition.util.dao;

public class CCLDBUtil extends DBUtil {

	private String host="172.19.91.129";
	private String port="1521";
	private String user="USR_CCL";
	private String password="Miclar0$";
	
	@Override
	protected String getConnectionString() {
		return "jdbc:oracle:thin:@"+host+":"+port+"/MICLARO";
	}

	@Override
	protected String getUser() {
		return user;
	}

	@Override
	protected String getPassword() {
		return password;
	}

	
	
}
