package jdbcStudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 测试与数据库的连接
 */
public class Demo01 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            //加载驱动类
            Class.forName(JDBC_DRIVER);
            long start = System.currentTimeMillis();
            //建立连接（连接对象内部其实包含了Socket对象，是一个远程的连接。比较耗时，这是Connection对象管理的一个要点！）
            //真正开发中，为了提高效率，都会使用连接池来管理对象！
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            long end = System.currentTimeMillis();
            System.out.println(conn);
            System.out.println("连接耗时："+(end-start)+"ms");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //使用完后必须关闭连接
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
