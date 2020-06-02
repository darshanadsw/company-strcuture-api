package com.company.structure.companystrcutureapi;

import com.company.structure.companystrcutureapi.domain.Department;
import com.company.structure.companystrcutureapi.domain.Employee;
import com.company.structure.companystrcutureapi.domain.PhoneType;
import com.company.structure.companystrcutureapi.domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class CompanyStrcutureApiApplicationTests {

    @PersistenceContext
    private EntityManager em;

    @Test
    void contextLoads() {

        List<Employee> employeeList = em.createQuery("select e from Employee e join e.phones p where p.type =:type")
                .setParameter("type", PhoneType.CELL_PHONE).getResultList();

        assertNotNull(employeeList);

    }

    @Test
    public void test_1(){
        String q = "select distinct(d) from Department d, Employee e where d = e.department";
        String query = "select distinct(e.department) from Employee e";
        List<Department> departments = em.createQuery(query).getResultList();

        assertNotNull(departments);
    }

    @Test
    public void test_getManager(){
        String q = "select d from Employee m, Department d where d = m.department and m.directs is not empty";
        String qq = "select e.department from Employee e where e.directs is not empty";
        Department managerDepartment = em.createQuery(qq,Department.class).getSingleResult();
        assertNotNull(managerDepartment);
    }

    @Test
    public void test_projectEmployeesBelong(){
        String q = "select distinct p from Department d join d.employees e join e.projects p";
        List<Project> projects = em.createQuery(q).getResultList();
        assertNotNull(projects);
    }

    @Test
    public void test_all_employees_with_QA(){
        String q = "select new com.company.structure.companystrcutureapi.domain.dto.EmpDTO(e, d)" +
                " from Employee e left join e.department d on d.name like '%1'";
        List<Object> result = em.createQuery(q).getResultList();

        assertNotNull(result);
    }

    @Test
    public void test_max_salary(){
        String q = "select e from Employee e where e.salary = ( select max(emp.salary) from Employee emp)";
        Employee employee = em.createQuery(q,Employee.class).getSingleResult();

        assertNotNull(employee);
    }

    @Test
    public void test_test2(){
        String q = "select e from Employee e where exists" +
                " (select 1 from Phone p where p.employee = e and p.type = 'CELL_PHONE')";

        List<Employee> employees = em.createQuery(q).getResultList();

        assertNotNull(employees);
    }

    @Test
    public void test_3(){
        String q = "select e from Employee e where e.department in " +
                "(select distinct d from Department d join d.employees de join de.projects p where p.name like 'QA%')";
        List<Employee> employees = em.createQuery(q).getResultList();

        assertNotNull(employees);
    }

    @Test
    public void test_load_all_emps_with_no_cellphone(){
        String q = "select e from Employee e where not exists " +
                "(select p from e.phones p where p.type = 'CELL_PHONE')";
        List<Employee> emps = em.createQuery(q).getResultList();

        assertNotNull(emps);
    }

    @Test
    public void test_employee_earn_heighest_salary(){
        String q = "select e from Employee  e where e.salary = (select max (emp.salary) from Employee emp )";
        Employee e = em.createQuery(q,Employee.class).getSingleResult();

        assertNotNull(e);
    }

    @Test
    public void test_employee_project(){
        String q = "select p from Project p where p.employees is not empty and type(p) = DesignProject ";
        List<Project> projects = em.createQuery(q).getResultList();

        assertNotNull(projects);
    }

    @Test
    public void test_project_casting(){
        String  q = "select p from Project p where type(p) = DesignProject " +
            "and treat(p as DesignProject).developmentTechnology =:tech";
        List<Project> p = em.createQuery(q)
            .setParameter("tech","Java")
            .getResultList();

        assertNotNull(p);
    }

    @Test
    public void test_project_name(){
        String q = "SELECT NEW com.company.structure.companystrcutureapi.domain.dto.ProjectDTO (p.name, " +
            "CASE TYPE(p)" +
            "  WHEN DesignProject THEN 'ENG'" +
            "  WHEN QualityProject THEN 'QA' " +
            "  ELSE 'unknown' " +
            "END ) " +
            "FROM Project p WHERE TYPE(p) = QualityProject ";
        List<Project> projects = em.createQuery(q).getResultList();

        assertNotNull(projects);
    }

}
