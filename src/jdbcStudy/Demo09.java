package jdbcStudy;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 测试CLOB，文本大对象处理
 * 包含：将字符串、文件内容插入数据库中的CLOB字段、将CLOB字段值取出来的操作
 * MySQL中有text类型
 */
public class Demo09 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BufferedReader reader = null;

        try {
            //加载驱动类
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);

            //插入记录操作
            //ps = conn.prepareStatement("insert into t_user (username,myInfo) values (?,?)");
            //ps.setObject(1,"雪之下雪乃");
            //注意CLOB是进行流操作
            //ps.setClob(2,new BufferedReader(new FileReader("D:/a.txt", Charset.forName("utf-8"))));
            //直接将字符串以Clob的方式存入数据库
            String data = "由比滨结衣";
//            ps.setClob(2,new BufferedReader(
//                    new InputStreamReader(
//                            new ByteArrayInputStream(data.getBytes("utf-8")))));
            //ps.executeUpdate();

            //读取记录操作
            ps = conn.prepareStatement("select * from t_user where username=?");
            ps.setString(1, "雪之下雪乃");
            rs = ps.executeQuery();
            while (rs.next()) {
                Clob clob = rs.getClob("myInfo");
                reader = new BufferedReader(clob.getCharacterStream());  //返回字符流
                String temp = "";
                while ((temp = reader.readLine()) != null) {
                    System.out.println(rs.getString("username") + "---" + temp);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != rs) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (null != ps) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
