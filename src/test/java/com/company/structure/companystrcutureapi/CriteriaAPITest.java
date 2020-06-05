package com.company.structure.companystrcutureapi;

import com.company.structure.companystrcutureapi.domain.*;
import com.company.structure.companystrcutureapi.domain.dto.EmpDTO;
import com.company.structure.companystrcutureapi.domain.dto.SearchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CriteriaAPITest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void test(){

        Employee e = Employee.builder()
            .name("Darshana")
            .salary(72000L)
            .build();

        em.persist(e);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        ParameterExpression<String> para = cb.parameter(String.class,"name");
        CriteriaQuery<Employee> criteriaQuery = cb.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery
            .select(root)
            .where(cb.like(root.get("name"),para));
        Employee employee = em.createQuery(criteriaQuery)
            .setParameter("name","Darshana")
            .getSingleResult();

        assertNotNull(employee);

    }

    @Test
    public void test_departments_with_employees(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> dept = cq.from(Department.class);
        Root<Employee> emp = cq.from(Employee.class);

        cq.select(dept)
            .distinct(true)
            .where(cb.equal(dept,emp.get("department")));

        List<Department> departments = em.createQuery(cq).getResultList();

        assertNotNull(departments);
    }

    @Test
    void test_carteesian(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> root1 = cq.from(Department.class);
        Root<Department> root2 = cq.from(Department.class);

        cq.select(root1);

        List<Department> departments = em.createQuery(cq).getResultList();

        assertNotNull(departments);
        assertEquals(4,departments.size());
    }

    @Test
    void test_Employee_with_city_newyork(){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        ParameterExpression<String> para = cb.parameter(String.class,"city");

        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.select(root)
            .where(cb.equal(root.get("address").get("city"),para));

        List<Employee> result = em.createQuery(cq)
            .setParameter("city","New York")
            .getResultList();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void test_all_employee_names(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.select(root.<String>get("department"));

        List<String> names = em.createQuery(cq).getResultList();

        assertNotNull(names);
        assertEquals(99,names.size());

    }

    @Test
    void test_multi_select(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> c = cb.createTupleQuery();
        Root<Employee> root = c.from(Employee.class);
        c.select(cb.tuple(root.get("id"),root.get("name")));

        List<Tuple> result = em.createQuery(c).getResultList();

        assertNotNull(result);

        List<Object> names = result.stream()
            .map(t -> t.get(1)).collect(Collectors.toList());

        assertEquals(99,names.size());
    }

    @Test
    void test_2_multi_select(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmpDTO> c = cb.createQuery(EmpDTO.class);
        Root<Employee> root = c.from(Employee.class);

        c.multiselect(root,root.get("department"));

        List<EmpDTO> result = em.createQuery(c).getResultList();

        assertNotNull(result);
    }

    @Test
    void test_3_multi_select(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmpDTO> c = cb.createQuery(EmpDTO.class);
        Root<Employee> root = c.from(Employee.class);
        c.select(cb.construct(EmpDTO.class,root.get("id"),root.get("name")));

        List<EmpDTO> result = em.createQuery(c).getResultList();
        assertNotNull(result);
    }

    @Test
    void test_Employee_Search(){

        Employee employee = Employee.builder()
            .name("Darshana")
            .salary(75000L)
            .build();
        em.persist(employee);

        SearchDTO search = SearchDTO.builder()
            .empName("Darshana")
            .salary(75000L)
            //.projectName("Dev")
            //.departmentName("Eng")
            //.cityName("Cambridge")
            .build();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
        Root<Employee> emp = c.from(Employee.class);
        c.select(emp)
            .distinct(true);
        Join<Employee, Project> project = emp.join("projects",JoinType.LEFT);

        List<Predicate> criteria = new ArrayList<>();

        if(!StringUtils.isEmpty(search.getEmpName())){
            ParameterExpression<String> p = cb.parameter(String.class,"name");
            criteria.add(cb.equal(emp.get("name"),p));
        }

        if(!StringUtils.isEmpty(search.getSalary())){
            ParameterExpression<Long> p = cb.parameter(Long.class,"salary");
            criteria.add(cb.equal(emp.get("salary"),p));
        }

        if(!StringUtils.isEmpty(search.getCityName())){
            ParameterExpression<String> p = cb.parameter(String.class,"city");
            criteria.add(cb.equal(emp.get("address").get("city"),p));
        }

        if(!StringUtils.isEmpty(search.getDepartmentName())){
            ParameterExpression<String> p = cb.parameter(String.class,"dept");
            criteria.add(cb.equal(emp.get("department").get("name"),p));
        }

        if(!StringUtils.isEmpty(search.getProjectName())){
            ParameterExpression<String> p = cb.parameter(String.class,"project");
            criteria.add(cb.equal(project.get("name"),p));
        }

        if(criteria.size() > 0){
            c.where(cb.or(criteria.toArray(Predicate[]::new)));
        }

        TypedQuery<Employee> query = em.createQuery(c);
        if(!StringUtils.isEmpty(search.getEmpName())){
            query.setParameter("name",search.getEmpName());
        }
        if(!StringUtils.isEmpty(search.getSalary())){
            query.setParameter("salary",search.getSalary());
        }
        if(!StringUtils.isEmpty(search.getCityName())){
            query.setParameter("city",search.getCityName());
        }
        if(!StringUtils.isEmpty(search.getDepartmentName())){
            query.setParameter("dept",search.getDepartmentName());
        }
        if(!StringUtils.isEmpty(search.getProjectName())){
            query.setParameter("project",search.getProjectName());
        }

        List<Employee> result = query.getResultList();

        assertNotNull(result);

    }

    @Test
    void test_serach_emp(){

        Employee employee = Employee.builder()
            .name("Darshana")
            .salary(75000L)
            .build();
        em.persist(employee);

        SearchDTO search = SearchDTO.builder()
            .empName("Darshana")
            .salary(75000L)
            .projectName("Dev")
            .departmentName("Eng")
            .cityName("Cambridge")
            .build();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
        Root<Employee> emp = c.from(Employee.class);
        c.select(emp)
            .distinct(true);
        Join<Employee,Project> projects = emp.join("projects",JoinType.LEFT);

        Predicate criteria = cb.conjunction();

        if(search.getEmpName() != null){
            ParameterExpression<String> p = cb.parameter(String.class,"name");
            criteria = cb.and(criteria,cb.equal(emp.get("name"),p));
        }
        if(search.getSalary() != null){
            ParameterExpression<Long> p = cb.parameter(Long.class,"salary");
            criteria = cb.and(criteria,cb.equal(emp.get("salary"),p));
        }
        if(search.getCityName() != null){
            ParameterExpression<String> p = cb.parameter(String.class,"city");
            criteria = cb.and(criteria,cb.equal(emp.get("address").get("city"),p));
        }

        if(search.getDepartmentName() != null){
            ParameterExpression<String> p = cb.parameter(String.class,"dept");
            criteria = cb.and(criteria,cb.equal(emp.get("department").get("name"),p));
        }
        if(search.getProjectName() != null){
            ParameterExpression<String> p = cb.parameter(String.class,"projectName");
            criteria = cb.and(criteria,cb.equal(projects.get("name"),p));
        }

        if(criteria.getExpressions().size() > 0){
            c.where(criteria);
        }

        TypedQuery<Employee> query = em.createQuery(c);

        if(search.getEmpName()!=null) {
            query.setParameter("name",search.getEmpName());
        }
        if(search.getProjectName()!=null) {
            query.setParameter("city",search.getCityName());
        }
        if(search.getDepartmentName()!=null) {
            query.setParameter("dept",search.getDepartmentName());
        }
        if (search.getSalary()!=null) {
            query.setParameter("salary",search.getSalary());
        }
        if(search.getProjectName()!=null) {
            query.setParameter("projectName", search.getProjectName());
        }

        List<Employee> result = query.getResultList();

        assertNotNull(result);
    }

    @Test
    void test_in_query(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
        Root<Employee> emp = c.from(Employee.class);
        c.select(emp);

        Subquery<Employee> sq = c.subquery(Employee.class);
        Root<Project> project = sq.from(Project.class);
        Join<Project,Employee> sqEmp = project.join("employees");
        sq.select(sqEmp)
            .where(cb.equal(project.get("name"),cb.parameter(String.class,"projectName")));

        c.where( cb.in(emp).value(sq) );

        List<Employee> result = em.createQuery(c)
            .setParameter("projectName","test")
            .getResultList();

        assertNotNull(result);
    }

    @Test
    void test_subquery_with_exist(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
        Root<Employee> emp = c.from(Employee.class);

        Subquery<Project> sq = c.subquery(Project.class);
        Root<Project> project = sq.from(Project.class);
        Join<Project,Employee> sqEmp = project.join("employees");
        sq.select(project)
            .where(cb.equal(emp,sqEmp),cb.equal(project.get("name"),
                cb.parameter(String.class,"pname")));

        c.where(cb.exists(sq));

        List<Employee> result = em.createQuery(c)
            .setParameter("pname","Project name")
            .getResultList();

        assertNotNull(result);
    }

    @Test
    void test_query_test(){

        String s = "SELECT e " +
            "FROM Employee e " +
            "WHERE e.department IN " +
            "(SELECT DISTINCT d " +
            "FROM Department d JOIN d.employees de JOIN de.project p " +
            "WHERE p.name LIKE 'QA%')";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
        Root<Employee> emp = c.from(Employee.class);

        Subquery<Integer> sqC = c.subquery(Integer.class);
        Root<Department> sqDept = sqC.from(Department.class);
        Join<Employee,Project> employeeJoin = sqDept.join("employees").join("projects");

        sqC.select(sqDept.<Integer>get("id"))
            .distinct(true)
            .where(cb.like(employeeJoin.get("name"),"%QA"));

        c.where(cb.in(emp.get("department").get("id")).value(sqC));

        List<Employee> result = em.createQuery(c).getResultList();

        assertNotNull(result);

    }

    @Test
    void test_case_query_1(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> c = cb.createTupleQuery();
        Root<Project> project = c.from(Project.class);

        c.multiselect(project.get("name"),
            cb.selectCase()
                    .when(cb.equal(project.type(), DesignProject.class),"Dev")
                    .when(cb.equal(project.type(), QualityProject.class),"QA")
                .otherwise("Unknown"))
            .where(cb.isNotEmpty(project.<List<Employee>>get("employees")));

        c.multiselect(project.get("id"),
                        cb.selectCase(project.type())
                            .when(DesignProject.class,"Design")
                            .when(QualityProject.class,"QA")
                        .otherwise("Unknown"))
            .where(cb.isNotEmpty(project.<List<Employee>>get("employees")));

        List<Tuple> result = em.createQuery(c).getResultList();

        assertNotNull(result);

    }

    @Test
    void test_test(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Project> c = cb.createQuery(Project.class);
        Root<Project> project = c.from(Project.class);

        c.select(project).where(cb.equal(cb.treat(project,DesignProject.class)
            .get("developmentTechnology"),"Java"));

        List<Project> list = em.createQuery(c).getResultList();

        assertNotNull(list);

    }

}
