package jdbcStudy;

import java.sql.*;

/**
 * 执行SQL语句和测试sql注入问题
 */
public class Demo02 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) {

        //使用try...with resource方式释放连接
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PWD);
                //一个statement用于执行sql语句，但是效率太低，用createStatement创建
                Statement statement = conn.createStatement();) {
            //加载驱动类
            Class.forName(JDBC_DRIVER);

//            String name = "赵柳";

//            String sql = "insert into t_user (username,pwd,regTime) values ('"+name+"',12345,now())";
//            statement.execute(sql);

            //测试SQL注入（可以任意修改数据库），这样数据库全删掉了，太过于危险
            String id = "5 or 1=1";
            String sql = "delete from t_user where id=" + id;
            statement.execute(sql);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
