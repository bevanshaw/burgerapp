package PostgresJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresJDBC {

	public PostgresJDBC() {
		// TODO Auto-generated constructor stub
	}
	
	public String logIn(String email, String pass){
		String res = null;
		ResultSet rs = null;
		try
		{
			String databaseUser = "postgres";
			String databaseUserPass = "stackoverbuger";           
			Class.forName("org.postgresql.Driver");
			
			Connection connection = DriverManager
			            .getConnection("jdbc:postgresql://23.239.21.155:5432/burger",
			            databaseUser, databaseUserPass);
			 
		
			Statement statement = connection.createStatement();
			rs = statement.executeQuery("select * from staff where id='"+email+"' and password='"+pass+"'");
			if(!rs.next())
				res = null;            
			rs.close();
			//connection.close();
		}
		catch(Exception e){
			System.out.println("LogIn Error: "+e.toString());
			res = null;
		}
		
		return res;
	}
	
	public static void main(String[] args) {

      PostgresJDBC postgresJDBC = new PostgresJDBC();
      postgresJDBC.logIn("manager1@gmail.com", "managerpass1");
	 System.out.println("main");
    }
}