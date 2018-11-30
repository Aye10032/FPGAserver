import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class historyWrite {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/fpgahistory?useSSL=false&serverTimezone=GMT";

    static final String USER = "root";
    static final String PASS = "Aye10032";

    public static void historyWrite(String msg) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            //2、建立连接
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //3、建立对象
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            String sql = "insert into fpgahistory.ayetable values (null ,'" + msg + "',now())";
            stmt.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
