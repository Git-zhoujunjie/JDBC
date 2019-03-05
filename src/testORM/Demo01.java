package testORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试用Objext[]存取表中的一条记录
 * 并用list存取多条记录
 */
public class Demo01 {
    public static void main(String[] args) {
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> listObjs = null;

        try{
            String sql = "select empname,salary,age from emp where id>=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            listObjs = new ArrayList<>();

            while(rs.next()){
                Object[] objs = new Object[3];
                objs[0] = rs.getObject("empname");
                objs[1] = rs.getObject("salary");
                objs[2] = rs.getObject("age");

                listObjs.add(objs);
            }

            //System.out.println(objs[0] + "--" + objs[1] + "--" +objs[2]);

            for(Object[] objs : listObjs){
                System.out.println(objs[0] + "--" + objs[1] + "--" +objs[2]);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn);
        }
    }
}
