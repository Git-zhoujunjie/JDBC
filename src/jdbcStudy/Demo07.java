package jdbcStudy;

import java.sql.*;
import java.util.Random;

/**
 * 测试时间操作(java.sql.date)
 */
public class Demo07 {
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

            for(int i=0;i<1000;i++){
                String sql = "insert into t_user (username,pwd,regTime,lastlogin) values (?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setObject(1,"王一");
                ps.setObject(2,"444444");

                int random = 1000000000 + new Random().nextInt(1000000000); //随机设定一个时间
                ps.setDate(3,new Date(System.currentTimeMillis()-random*5));
                ps.setTimestamp(4,new Timestamp(System.currentTimeMillis()+random*5));//如果需要插入指定时间，需要使用Calendar、DateFormat类
                ps.execute();
            }
            //System.out.println("成功插入一条王一的记录");


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
