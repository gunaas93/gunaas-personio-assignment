package com.gunaas.rest.repository;

import com.gunaas.rest.entity.AbstractEntity;
import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.entity.RelationEntity;
import com.gunaas.rest.repository.impl.SuperiorRepositoryImpl;
import com.gunaas.rest.utils.TestUtils;
import javafx.util.Pair;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SuperiorRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    private SuperiorRepositoryImpl repository;

    @BeforeEach
    public void beforeEach() {
        repository = new SuperiorRepositoryImpl();
        repository.setEntityManager(entityManager);
    }

    @Test
    public void givenStaffHasNoSuperior_whenGetSuperiors_thenReturnEmpty() {
        EmployeeEntity employeeEntity = fakeAEmployee();
        List<EmployeeEntity> superiors = repository.getSuperiors(employeeEntity.getEmployeeName());
        Assertions.assertThat(superiors.size()).isEqualTo(0);
    }

    @Test
    public void givenStaffHasASuperiorAndASuperSuperior_whenGetSuperiors_thenReturn2Superiors() {
        Pair subordinateAndSuperiors = fakeASubordinateASuperiorAndASuperSuperior();
        List<EmployeeEntity> rightSuperiors = repository.getSuperiors(((EmployeeEntity)subordinateAndSuperiors.getKey()).getEmployeeName());
        Assertions.assertThat(rightSuperiors.get(0).getEmployeeName()).isEqualTo(rightSuperiors.get(0).getEmployeeName());
        Assertions.assertThat(rightSuperiors.get(1).getEmployeeName()).isEqualTo(rightSuperiors.get(1).getEmployeeName());
    }


    @NotNull
    public EmployeeEntity fakeAEmployee() {
        EmployeeEntity sup = new EmployeeEntity("A");
        TestUtils.testAuditTime(Arrays.asList(new AbstractEntity[]{sup}));
        TestUtils.persist(entityManager, Arrays.asList(new AbstractEntity[]{sup}));
        return sup;
    }

    @NotNull
    public Pair fakeASubordinateASuperiorAndASuperSuperior() {
        EmployeeEntity sub = new EmployeeEntity("A");
        EmployeeEntity sup1 = new EmployeeEntity("B");
        EmployeeEntity sup2 = new EmployeeEntity("C");
        RelationEntity rel1 = new RelationEntity("C", "B");
        RelationEntity rel2 = new RelationEntity("B", "A");
        TestUtils.testAuditTime(Arrays.asList(new AbstractEntity[]{sub, sup1, sup2, rel1, rel2}));
        TestUtils.persist(entityManager, Arrays.asList(new AbstractEntity[]{sub, sup1, sup2, rel1, rel2}));
        return new Pair(sub, Arrays.asList(sup1, sup2));
    }
}

