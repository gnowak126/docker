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
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String sql;
      
      sql = "DROP TABLE IF EXISTS Persons";
      stmt.executeQuery(sql);
      sql = "CREATE TABLE IF NOT EXISTS Persons (id int, name varchar(255), surname varchar(255))";
      stmt.executeQuery(sql);
      sql = "INSERT INTO Persons (id, name, surname) VALUES (1, 'Grzegorz', 'Nowak')";
      stmt.executeQuery(sql);   
      sql = "INSERT INTO Persons (id, name, surname) VALUES (2, 'Jan', 'Kowalski')";
      stmt.executeQuery(sql); 
      sql = "INSERT INTO Persons (id, name, surname) VALUES (3, 'Katarzyna', 'Kowalska')";
      stmt.executeQuery(sql); 
      
      sql = "SELECT id, surname, name FROM Persons";
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){
         int id  = rs.getInt("id");
         String name = rs.getString("surname");
         String surname = rs.getString("name");

         System.out.println("ID: " + id +", Name: " + name+ ", LasSurnamet: " + surname);
      }
      rs.close();
	   System.out.println("Type Q to exit, or input values in one row in a form '<name>', '<surname>'");

      String input = System.console().readLine();
      int count=4;
      while(input!="Q")
      {
        try {
	      sql="INSERT INTO Persons (id, name, surname) VALUES ("+count+", "+input+");";
	      stmt.executeQuery(sql);
        } catch (SQLException se) {
            System.out.println(se);
        }

         
	      sql = "SELECT id, surname, name FROM Persons";
          rs = stmt.executeQuery(sql);
	    while(rs.next()){
		 int id  = rs.getInt("id");
		 String first = rs.getString("surname");
		 String last = rs.getString("name");
			 String address = rs.getString("Address");
			 String city = rs.getString("City");

		 System.out.println("ID: " + id);
		 System.out.println(", First: " + first);
		 System.out.println(", Last: " + last);
			 System.out.println(", Address: " + address);
			 System.out.println(", City: " + city);
	      }
	      rs.close();
		   System.out.println("Type Q to exit, or input values in one row in \' \' and separeted by comma");
	      count++;
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
