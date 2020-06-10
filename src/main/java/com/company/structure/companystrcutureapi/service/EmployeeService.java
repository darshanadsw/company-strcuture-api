package com.company.structure.companystrcutureapi.service;

import com.company.structure.companystrcutureapi.domain.Department;
import com.company.structure.companystrcutureapi.domain.Employee;
import com.company.structure.companystrcutureapi.domain.dto.EmpDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @PersistenceContext
    private EntityManager em;

    public List<EmpDTO> getAllEmployees(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmpDTO> c = cb.createQuery(EmpDTO.class);
        Root<Employee> emp = c.from(Employee.class);
        c.multiselect(emp.get("id"),
            emp.get("name"),
            emp.get("salary"),
            emp.get("department").get("name"))
            .where(cb.isNotEmpty(emp.<List<Employee>>get("directs")));

        return em.createQuery(c).getResultList();
    }

    public void saveEmployee(Employee employee){
        em.persist(employee);
    }

    public Employee findById(Integer id){
        return em.find(Employee.class,id);
    }

    public EmpDTO findEmpById(Integer id){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmpDTO> c = cb.createQuery(EmpDTO.class);
        Root<Employee> emp = c.from(Employee.class);
        Join<Employee, Department> department = emp.join("department", JoinType.LEFT);

        c.select(cb.construct(EmpDTO.class,emp.get("id"),
            emp.get("name"),
            emp.get("salary"),
            department.get("name")))
            .where(cb.equal(emp.get("id"),cb.parameter(Integer.class,"id")));

        return em.createQuery(c).setParameter("id",id).getSingleResult();

    }

    public void deleteEmployee(Integer id){
        em.createNamedQuery(Employee.DELETE_QUERY)
            .setParameter("id",id).executeUpdate();
    }

}
