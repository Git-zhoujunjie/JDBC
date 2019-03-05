package testORM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 封装代码
 * 1、连接数据库
 * 2、关闭连接流
 * 3、读写配置文件
 */

public class JDBCUtil {
    private JDBCUtil(){}  //工具类的构造器应当私有化
    private static Properties pros = null; //帮组读取和处理资源文件的信息

    static{ //静态块，只有在加载JDBCUtil类时调用
        pros = new Properties();
        try {
            pros.load(Thread.currentThread().
                    getContextClassLoader().
                    getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getMysqlConn(){
        //加载驱动类
        try {
            Class.forName(pros.getProperty("JDBC_DRIVER"));

            return DriverManager.getConnection(pros.getProperty("DB_URL"),
                    pros.getProperty("USER"), pros.getProperty("PWD"));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void close(AutoCloseable... stream) {
        for (AutoCloseable c : stream) {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
