package com.gunaas.rest.utils;

import com.gunaas.rest.entity.AbstractEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class TestUtils {
    public static void testAuditTime(List<AbstractEntity> entities) {
        for (AbstractEntity e : entities) {
            e.setCreatedTime(LocalDateTime.now());
            e.setUpdatedTime(LocalDateTime.now());
        }
    }

    public static void persist(TestEntityManager em, List<AbstractEntity> entities) {
        for (AbstractEntity e : entities) {
            em.persist(e);
        }
    }

    public static void persist(EntityManager em, List<AbstractEntity> entities) {
        for (AbstractEntity e : entities) {
            em.persist(e);
        }
    }

}
