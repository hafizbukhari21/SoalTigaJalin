package src;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;  
public class CreateConnection {

     static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
     static final String DB_URL = "jdbc:mysql://localhost/soaltigajalin";
     static final String USER = "root";
     static final String PASS = "";

     static Connection conn;
     static Statement stmt;
     static ResultSet rs;

     

     private void createConnection(){
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        }catch (Exception e) {
            e.printStackTrace();
        }
     }

     private void closedConnection(){
        try{
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
     }

     private ResultSet execute(String query){
        try{
            return stmt.executeQuery(query);
        }catch(Exception e){
             e.printStackTrace();
        }
        return null;

     }

     private List<Bank> insertLooping(ResultSet rs){
        try {
            List<Bank> listBank = new ArrayList<Bank>();

            while(rs.next()){
                    listBank.add(
                        new Bank(
                            rs.getDate("transaction_date"), 
                            rs.getTime("transaction_time"), 
                            rs.getString("transaction_status"), 
                            rs.getInt("transaction_amount"), 
                            rs.getInt("transaction_fee")
                        )
                    );
                }
                return listBank;
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
     }



     public List<Bank> Get(String... whereCondition){
        String query = "select*from transaction";
        try{
            
            if(whereCondition.length>0) query = "select*from transaction where "+whereCondition[0]; 
            this.createConnection();
            List<Bank> listBank = insertLooping(execute(query));
            this.closedConnection();
            return listBank;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
     }
}


