package jdbcStudy;

import java.sql.*;

/**
 * 测试批处理
 * Batch
 * 注意需要使用Statement
 *
 */
public class Demo05 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement ps = null;
        try{
            //加载驱动类
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);

            conn.setAutoCommit(false); //将自动提交事务改成手动提交
            long start = System.currentTimeMillis();
            ps = conn.createStatement();
            //插入20000条记录
            for(int i=0;i<20000;i++){
                String sql = "insert into t_user (username,pwd,regTime) values ('杰"+i+"','666666',now())";
                ps.addBatch(sql);
            }
            ps.executeBatch();

            long end = System.currentTimeMillis();
            System.out.println("插入20000条数据耗时为："+(end-start));
            conn.commit();  //提交事务

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
