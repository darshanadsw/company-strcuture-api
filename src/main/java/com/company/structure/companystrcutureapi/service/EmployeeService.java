package com.company.structure.companystrcutureapi.service;

import com.company.structure.companystrcutureapi.domain.Employee;
import com.company.structure.companystrcutureapi.domain.dto.EmpDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
            emp.get("name"))
            //emp.get("salary"),
            //emp.get("department"),
            //emp.get("manager"))
            .where(cb.isEmpty(emp.<List<Employee>>get("directs")));

        return em.createQuery(c).getResultList();
    }

}
