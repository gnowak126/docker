import java.sql.*;

public class DockerConnectToMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/database";

   static final String USER = "gnowak";
   static final String PASS = "passwd";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   System.out.println("Java application is ready tu use. Press ENTER to start.");
   System.console().readLine();
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
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
         String name = rs.getString("name");
         String surname = rs.getString("surname");

         System.out.println("ID: " + id +", Name: " + name+ ", Surname: " + surname);
      }
      rs.close();
	 System.out.println("Type Q to exit, or input values in one row in a form: '<name>', '<surname>'");

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
		 id  = rs.getInt("id");
		 name = rs.getString("name");
		 surname = rs.getString("surname");

		 System.out.println("ID: " + id +", Name: " + name+ ", Surname: " + surname);
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
