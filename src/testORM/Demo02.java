package testORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试用Map存取表中的一条记录
 * 并分别用list、Map存取多条记录
 */
public class Demo02 {

    /**
     * 用Map存取一条记录
     */
    public static void test1(){
        List a = new ArrayList();
        Map m = new HashMap();
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> listObjs = null;

        try{
            String sql = "select empname,salary,age from emp where id=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            //key为列名，value为值
            Map<String,Object> objs = new HashMap<>();

            while(rs.next()){

                objs.put("empname",rs.getObject("empname"));
                objs.put("salary",rs.getObject("salary"));
                objs.put("age",rs.getObject("age"));

            }

            for(String row:objs.keySet()){
                System.out.print(row+":" + objs.get(row)+"\t");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn);
        }
    }

    /**
     * 用list存取多条记录
     */
    public static void test2(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String,Object>> listObjs = null;

        try{
            String sql = "select empname,salary,age from emp where id>=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            listObjs = new ArrayList<>();

            while(rs.next()){
                //key为列名，value为值
                Map<String,Object> objs = new HashMap<>();

                objs.put("empname",rs.getObject("empname"));
                objs.put("salary",rs.getObject("salary"));
                objs.put("age",rs.getObject("age"));
                listObjs.add(objs);

            }

            for(Map<String,Object> objs:listObjs) {
                for (String row : objs.keySet()) {
                    System.out.print(row + ":" + objs.get(row) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn);
        }
    }

    /**
     * 用Map存取多条记录
     */
    public static void test3(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //key为empname，value为每条记录
        Map<String,Map<String,Object>> mapObjs = null;

        try{
            String sql = "select empname,salary,age from emp where id>=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            mapObjs = new HashMap<>();

            while(rs.next()){
                //key为列名，value为值
                Map<String,Object> objs = new HashMap<>();

                objs.put("empname",rs.getObject("empname"));
                objs.put("salary",rs.getObject("salary"));
                objs.put("age",rs.getObject("age"));

                mapObjs.put(rs.getObject("empname").toString(),objs);

            }

            for(String empname:mapObjs.keySet()) {
                Map<String,Object> objs = mapObjs.get(empname);

                for (String row : objs.keySet()) {
                    System.out.print(row + ":" + objs.get(row) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn);
        }
    }
    public static void main(String[] args) {

        //test1();
        //test2();

        test3();
    }
}
