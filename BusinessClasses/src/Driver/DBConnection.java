/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package Driver;

import Entities.DialogMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection = null;
    DialogMessage dm = new DialogMessage();

    public Connection getConnection() {
        try {
            //This class is designed to be flexible to connect 
            //to any Database, using the right Driver
            //just uncomment the Database Driver you want to use
            //and comment the other's

            //To connect to derby Database using Embedded Driver           
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";            
            String url = "jdbc:derby:F:\\TBA.java\\data\\TXDB;create=true";
            //String url = "jdbc:derby:C:\\data\\TXDB.old1;create=true";
            //String url = "jdbc:derby:C:\\data\\TXDB;create=true";            
            String username = "TXDB";
            String password = "000";

            //To connect to derby Database using Using ClientDriver:
//            String driver = "org.apache.derby.jdbc.ClientDriver";
//            String url = "jdbc:derby://localhost:1527/TXDB";
//            String username = "TXDB";
//            String password = "000";
            
            //To connect MS Access Database using JdbcOdbcDriver: 
//            String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
//            String url = "jdbc:odbc:TBA";
//            String username = "";
//            String password = "";
            //To connect to MysqlWorkbench Database:
//            String driver = "com.mysql.jdbc.Driver";
//            String url = "jdbc:mysql://localhost:3306/txdb";
//            String username = "root";
//            String password = "";
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                System.out.println(e.toString());
            }

            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            if (connection != null) {
                System.out.println("Connection Success!");
            } else {
                System.out.println("Connection Failed!");
                dm.Message("Cannot connect to Database, please contact the developer"
                        + "\n <<<<<<<<<<    www.benmouaou.com    >>>>>>>>>>");
            }
        }
        return connection;
    }

}
