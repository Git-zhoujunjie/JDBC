package jdbcStudy;

import java.io.*;
import java.sql.*;

/**
 * 测试BLOB，二进制大对象
 *
 * MySQL中为blob
 */
public class Demo10 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testjdbc?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BufferedReader reader = null;
        BufferedInputStream bis =null;
        OutputStream os =null;

        try {
            //加载驱动类
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);

            //插入记录操作(插入一张图片)
//            ps = conn.prepareStatement("insert into t_user (username,headImg) values (?,?)");
//            ps.setObject(1,"亚丝娜");
//            //直接用字节流
//            ps.setBlob(2,new FileInputStream("d:/xun.jpg"));
//            ps.executeUpdate();

            //从数据库中读取blob
            ps = conn.prepareStatement("select * from t_user where username=?");
            ps.setObject(1,"亚丝娜");
            rs = ps.executeQuery();

            while(rs.next()){
                Blob blob = rs.getBlob("headImg");
                bis = new BufferedInputStream(blob.getBinaryStream());
                os = new BufferedOutputStream(new FileOutputStream("copy.jpg"));
                int len = -1;
                byte[] bytes = new byte[1024];
                while((len = bis.read(bytes))!=-1){
                    os.write(bytes,0,len);
                }
                os.flush();
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(os,bis,rs,ps,conn);

//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (null != rs) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (null != ps) {
//                    ps.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
    }
}
