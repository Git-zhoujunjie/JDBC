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
 * 使用JavaBean对象封装表记录
 *
 * 测试用Map存取表中的一条记录
 * 并分别用list、Map存取多条记录
 */
public class Demo03 {

    public static void main(String[] args) {

        //test1();
        //test2();

        test3();
    }

    /**
     * 用Map存取一条记录
     */
    public static void test1(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select empname,salary,age from emp where id=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            Emp emp = null;

            while(rs.next()){

                emp = new Emp(rs.getString("empname"),
                        rs.getDouble("salary"),
                        rs.getInt("age"));

            }
            System.out.println(emp.getEmpname()+"--"+emp.getSalary()+"--"+emp.getAge());

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
        List<Emp> listEmp = null;

        try{
            String sql = "select empname,salary,age from emp where id>=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            listEmp = new ArrayList<>();

            while(rs.next()){
                Emp emp = new Emp(rs.getString("empname"),
                        rs.getDouble("salary"),
                        rs.getInt("age"));

                listEmp.add(emp);

            }
            for (Emp emp:listEmp){
                System.out.println(emp.getEmpname()+"--"+emp.getSalary()+"--"+emp.getAge());
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
        Map<String,Emp> mapEmp = null;

        try{
            String sql = "select empname,salary,age from emp where id>=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            mapEmp = new HashMap<>();

            while(rs.next()){
                Emp emp = new Emp(rs.getString("empname"),
                        rs.getDouble("salary"),
                        rs.getInt("age"));

                mapEmp.put(rs.getObject("empname").toString(),emp);

            }

            for(String empname:mapEmp.keySet()) {
                Emp emp = mapEmp.get(empname);

                System.out.println(emp.getEmpname()+"--"+emp.getSalary()+"--"+emp.getAge());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn);
        }
    }

}
