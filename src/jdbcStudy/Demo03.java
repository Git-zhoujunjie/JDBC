package jdbcStudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 测试PreparedStatement
 */
public class Demo03 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //加载驱动类
            Class.forName(JDBC_DRIVER);
            //建立连接（连接对象内部其实包含了Socket对象，是一个远程的连接。比较耗时，这是Connection对象管理的一个要点！）
            //真正开发中，为了提高效率，都会使用连接池来管理对象！
            conn = DriverManager.getConnection(DB_URL,USER,PWD);

            //防止sql注入
            String sql = "insert into t_user (username,pwd,regTime) values (?,?,?)";  //？表示占位符
            ps = conn.prepareStatement(sql);
            //参数索引是从1开始计算
//            ps.setString(1,"周俊杰");  //指第一个问号的位置
//            ps.setString(2,"654321");
//            ps.execute();

//            ps.setObject(1,"周俊杰2");
//            ps.setObject(2,"98789876");
//            ps.execute();

            //使用setObject设置参数可以不用管数据类型，系统会自动匹配
            ps.setObject(1,"周俊杰3");  //指第一个问号的位置
            ps.setObject(2,"7777777");
            ps.setObject(3,new java.sql.Date(System.currentTimeMillis()));
            //ps.execute();

            int count = ps.executeUpdate();
            System.out.println(count);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //注意这里两个try块要分开，不能写在一起
            try {
                if(ps!=null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
