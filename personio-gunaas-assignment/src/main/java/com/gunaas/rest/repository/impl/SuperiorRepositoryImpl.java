package com.gunaas.rest.repository.impl;

import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.repository.SuperiorRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SuperiorRepositoryImpl implements SuperiorRepository {
    private EntityManager entityManager;
    private final String superiorsSQL =
            "\n    SELECT " +
                    "\n        employee.*" +
                    "\n    FROM" +
                    "\n        employee" +
                    "\n    WHERE" +
                    "\n        (emp_name IN (SELECT " +
                    "\n            superior" +
                    "\n            FROM" +
                    "\n                employee" +
                    "\n                    INNER JOIN" +
                    "\n                relations ON relations.subordinate = employee.emp_name" +
                    "\n            WHERE" +
                    "\n                employee.emp_name = :name UNION SELECT " +
                    "\n                B.superior" +
                    "\n            FROM" +
                    "\n                (SELECT " +
                    "\n                    superior" +
                    "\n                FROM" +
                    "\n                    employee" +
                    "\n                INNER JOIN relations ON relations.subordinate = employee.emp_name" +
                    "\n                WHERE" +
                    "\n                    employee.emp_name = :name) AS A" +
                    "\n                    LEFT JOIN" +
                    "\n                relations AS B ON A.superior = B.subordinate))" +
                    "\n";

    private final Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<EmployeeEntity> getSuperiors(String employeeName) {
        Session session = getCurrentSession();
        Query query = session.createNativeQuery(superiorsSQL, EmployeeEntity.class);
        query.setParameter("name", employeeName);
        return query.getResultList();
    }
}
