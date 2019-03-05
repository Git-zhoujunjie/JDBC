package jdbcStudy;

import java.io.*;
import java.sql.*;

/**
 * 测试封装代码
 */
public class Demo11 {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BufferedReader reader = null;
        BufferedInputStream bis =null;
        OutputStream os =null;

        try {
            //加载驱动类
            conn = JDBCUtil.getMysqlConn();

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
                os = new BufferedOutputStream(new FileOutputStream("copy2.jpg"));
                int len = -1;
                byte[] bytes = new byte[1024];
                while((len = bis.read(bytes))!=-1){
                    os.write(bytes,0,len);
                }
                os.flush();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(os,bis,rs,ps,conn);

        }
    }
}
