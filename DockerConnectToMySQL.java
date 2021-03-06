import java.sql.*;

public class DockerConnectToMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/database";

   static final String USER = "gnowak";
   static final String PASS = "passwd";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   System.console().readLine();
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to database...");
        for (;;) {
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                break;
            } catch (SQLException se) {
                System.out.println(se);
		System.out.println("Database is not ready to connect. Application will try to connect again in 5 seconds...");
            }
            Thread.sleep(5000);
        }

      stmt = conn.createStatement();
      String sql;
      
      sql = "DROP TABLE IF EXISTS Persons"; //drop table, so that every run looks the same
      stmt.executeUpdate(sql);
      sql = "CREATE TABLE IF NOT EXISTS Persons (id int, name varchar(255), surname varchar(255))"; //create table
      //3 colums: id, name and surname
      stmt.executeUpdate(sql);
      sql = "INSERT INTO Persons (id, name, surname) VALUES (1, 'Grzegorz', 'Nowak')"; //3 first inserts
      stmt.executeUpdate(sql);   
      sql = "INSERT INTO Persons (id, name, surname) VALUES (2, 'Jan', 'Kowalski')";
      stmt.executeUpdate(sql); 
      sql = "INSERT INTO Persons (id, name, surname) VALUES (3, 'Katarzyna', 'Kowalska')";
      stmt.executeUpdate(sql); 
      
      sql = "SELECT id, surname, name FROM Persons"; //select on db
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){ //select display loop
         System.out.println("ID: " + rs.getInt("id") +", Name: " + rs.getString("name")+ ", Surname: " + rs.getString("surname"));
      }
      rs.close();
	 System.out.println("Type 'Q' or 'q' to exit, or input values in one row in a form: '<name>', '<surname>'");

      String input = System.console().readLine(); //readeing input
      int count=4;
      while(!input.toUpperCase().equals("Q")) //insert loop, out on q or Q
      {
        try {
	      sql="INSERT INTO Persons (id, name, surname) VALUES ("+count+", "+input+");"; //insert values from input
	      stmt.executeUpdate(sql);
        } catch (SQLException se) {
            System.out.println(se);
        }         
	      sql = "SELECT id, surname, name FROM Persons"; //select on db
          rs = stmt.executeQuery(sql);
	    while(rs.next()){ //select display loop
		 System.out.println("ID: " + rs.getInt("id") +", Name: " + rs.getString("name")+ ", Surname: " + rs.getString("surname"));
	    }
	      rs.close();
	 System.out.println("Type 'Q' or 'q' to exit, or input values in one row in a form: '<name>', '<surname>'");
	      count++;
	  input = System.console().readLine();
      }
	      
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
 }
}
