package jdbcStudy;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 测试时间操作(java.sql.date)
 * 取出指定时间区间的数据
 */
public class Demo08 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    /**
     * 将字符串代表的日期转换成long型的数字 时间格式（yyyy-MM-dd hh:mm:ss）
     * @param dateStr
     * @return
     */
    public static long str2Date(String dateStr){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return df.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //加载驱动类
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);

//            ps = conn.prepareStatement("select * from t_user where regTime>? and regTime<?");
//            long start = str2Date("2019-02-27");
//            long end = str2Date("2019-03-03");
//            ps.setDate(1,new Date(start));
//            ps.setDate(2,new Date(end));

            ps = conn.prepareStatement("select * from t_user where lastlogin>? and lastlogin<?");
            long start = str2Date("2019-02-27 10:10:56");
            long end = str2Date("2019-02-28 9:9:22");
            ps.setObject(1,new java.sql.Date(start));
            ps.setObject(2,new java.sql.Date(end));

            rs = ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getInt("id")+"---"+rs.getString("username")+"---"+
                        rs.getString("pwd")+"---"+rs.getTimestamp("lastlogin"));
            }


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
