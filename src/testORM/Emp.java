package testORM;

import java.util.Date;

/**
 * 表结构和类对应
 */
public class Emp {
    private Integer id;
    private String empname;
    private Double salary;
    private Integer age;
    private Date birthday;
    private Integer deptId;

    public Emp(String empname, Double salary, Integer age) {
        this.empname = empname;
        this.salary = salary;
        this.age = age;
    }

    public Emp() {
    }

    public Emp(String empname, Double salary, Integer age, Date birthday, Integer deptId) {
        this.empname = empname;
        this.salary = salary;
        this.age = age;
        this.birthday = birthday;
        this.deptId = deptId;
    }

    public Emp(Integer id, String empname, Double salary, Integer age, Date birthday, Integer deptId) {
        this.id = id;
        this.empname = empname;
        this.salary = salary;
        this.age = age;
        this.birthday = birthday;
        this.deptId = deptId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
