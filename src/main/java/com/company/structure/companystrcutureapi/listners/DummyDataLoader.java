package com.company.structure.companystrcutureapi.listners;

import com.company.structure.companystrcutureapi.domain.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@Transactional
public class DummyDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Department department1 = new Department();
        department1.setName("Department 1");
        em.persist(department1);
        Department department2 = new Department();
        department2.setName("Department 2");
        em.persist(department2);

        List<Department> departments = List.of(department1,department2);

        final Employee[] manager = new Employee[1];

        IntStream.range(1,100).forEach(i -> {
            Address address = Address.builder()
                    .city("City " + i)
                    .state("State " + i)
                    .street("Street " + i)
                    .zip("Zip code " + i)
                    .build();
            em.persist(address);
            List<Integer> addressIds = new ArrayList();
            addressIds.add(address.getId());

            Employee employee = Employee.builder()
                    .address(address)
                    .department(departments.get(new Random().nextInt(2)))
                    .name("Name " + i)
                    .salary(new Long(new Random().nextInt(500000)))
                    .build();

            int last = new Random().nextInt(20);
            List<PhoneType> phoneTypes = List.of(PhoneType.CELL_PHONE,PhoneType.HOME_PHONE,PhoneType.WORK_PHONE);
            IntStream.range(1,last).forEach(a -> {
                Phone p = Phone.builder()
                        .number("416-994-34"+new Random().nextInt(100))
                        .type(phoneTypes.get(new Random().nextInt(3)))
                        .build();
                em.persist(p);
                employee.addPhone(p);
            });

            IntStream.range(1,new Random().nextInt(4)).forEach(b -> {

                if(new Random().nextBoolean()){
                    DesignProject designProject1 = DesignProject.builder()
                            .build();
                    designProject1.setName("Development Project");
                    designProject1.setDevelopmentTechnology("Java");
                    em.persist(designProject1);
                    employee.addProject(designProject1);
                }else {
                    QualityProject qualityProject1 = QualityProject.builder()
                            .build();
                    qualityProject1.setName("QA Project");
                    qualityProject1.setAutomationTechnology("Sellinium");
                    em.persist(qualityProject1);
                    employee.addProject(qualityProject1);
                }
            });

            em.persist(employee);
           if(i == 1){
               manager[0] = employee;
           }else {
               employee.addManager(manager[0]);
           }



        });

    }
}
