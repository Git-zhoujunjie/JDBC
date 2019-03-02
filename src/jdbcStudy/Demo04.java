package jdbcStudy;

import java.sql.*;

/**
 * 测试ResultSet结果集的基本用法executeQuery()
 */
public class Demo04 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) {
        //防止sql注入
        String sql = "select * from t_user where id>?";  //？表示占位符
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PWD);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            //加载驱动类
            Class.forName(JDBC_DRIVER);

            ps.setObject(1, 10);



            while (rs.next()) {
                System.out.println(rs.getInt(1) + "---" + rs.getString(2) + "---" + rs.getString(3));
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
