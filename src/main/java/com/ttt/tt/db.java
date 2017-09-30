package com.ttt.tt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
public class db {
	
	static HashMap<String,Integer> result = new HashMap<String,Integer>();
	
	public static boolean isExistColumn(ResultSet rs, String columnName) {  
	    try {  
	        if (rs.findColumn(columnName) > 0 ) {  
	            return true;  
	        }   
	    }  
	    catch (SQLException e) {  
	        return false;  
	    }  
	      
	    return false;  
	} 
	
	public static HashMap<String,Integer> searchDb(String sql){
		//
		Connection con;
		//
		String driver="com.mysql.jdbc.Driver";
		//
//		String url = "jdbc:mysql://localhost:3306/mydata";
		String url = "jdbc:mysql://172.19.160.71:3306/risk_data_two";
		//
		String user="worker";
		String password = "Geeker4ZolZ";
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			
			Statement statement = con.createStatement();
//			String sql = "select * from risk_field where id <10";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				if(isExistColumn(rs,"count"))
					result.put("count", rs.getInt("count"));
				if(isExistColumn(rs,"id"))
					result.put("id", rs.getInt("id"));
				if(isExistColumn(rs,"is_deleted"))
					result.put("isdeleted", rs.getInt("is_deleted"));
			}			
			rs.close();
			con.close();
		}catch(ClassNotFoundException e){
			System.out.println("Sorry,can't find the Driver!");
			e.printStackTrace();
			result = null;
		} catch(SQLException e){
			e.printStackTrace();
			result = null;
		}catch(Exception e){
			e.printStackTrace();
			result = null;
		}
		return result;
	}
}
