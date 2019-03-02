package jdbcStudy;

import java.sql.*;

/**
 * 测试事务的特性
 * 原子性
 */
public class Demo06 {
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
            conn = DriverManager.getConnection(DB_URL, USER, PWD);

            conn.setAutoCommit(false); //设置手动提交事务，JDBC中默认为true
            //一个事务的起始
            String sql = "insert into t_user (username,pwd) values (?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,"王一");
            ps.setObject(2,"444444");
            ps.execute();
            System.out.println("成功插入一条王一的记录");

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //出错的sql语句
            //String sql2 = "insert into t_user (username,pwd) values (?,?,?)";

            String sql2 = "insert into t_user (username,pwd) values (?,?)";
            ps = conn.prepareStatement(sql2);
            ps.setObject(1,"刘二");
            ps.setObject(2,"555555");
            ps.execute();
            System.out.println("成功插入一条刘二的记录");

            //在一个事务提交前，如果有多条sql语句，只要有一条语句出错，则所有的执行记录都会回滚
            conn.commit(); //提交事务

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != ps) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
