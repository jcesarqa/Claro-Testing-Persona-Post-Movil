package pe.com.admequipo.stepdefinition.util.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DBUtil {
	
	protected abstract String getConnectionString();
	protected abstract String getUser();
	protected abstract String getPassword();
	
	private Connection cx;
	private Statement st;
	private PreparedStatement pst;
	
	public int executeUpdate(String sql){
		initConnection();
		int resp=0;
		try {
			resp=st.executeUpdate(sql);
			cx.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return resp;
	}
	
	public List<Object[]> executeQuery(String sql){
		ResultSet rs=null;
		List<Object[]> result=new ArrayList<>();
		initConnection();
		try {
			rs=st.executeQuery(sql);
			int columns=rs.getMetaData().getColumnCount();		
			int i=0;
			while(rs.next()){
				Object[] row=new Object[columns];
				for(i=0;i<columns;i++){
					row[i]=rs.getObject(i+1);
				}
				result.add(row);			
			}
			
		} catch (Exception e) {
			
		}
		return result;
	}
	
	public int executePrepared(String sql,Object ...objects){
		initConnectionPrepared(sql);
		int resp=0;
		int idx=1;
		try {
			for(Object o:objects){
				pst.setObject(idx++, o);
			}
			resp=pst.executeUpdate();
			pst.close();
			cx.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return resp;
	}
	
	private void initConnection(){
		
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			cx=DriverManager.getConnection(getConnectionString(),getUser(),getPassword());
			st=cx.createStatement();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private void initConnectionPrepared(String sql){
		
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			cx=DriverManager.getConnection(getConnectionString(),getUser(),getPassword());
			pst=cx.prepareStatement(sql);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	

}
