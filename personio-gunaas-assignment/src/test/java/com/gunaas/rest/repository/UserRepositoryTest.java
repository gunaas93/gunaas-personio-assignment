package com.gunaas.rest.repository;

import com.gunaas.rest.entity.UserEntity;
import com.gunaas.rest.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;
    @NotNull
    public static final String rightName = "long";
    @NotNull
    public static final String wrongName = "top";
    @NotNull
    public static final String rightPassword = "$23E5dkk3jf3dlf3333kss32144j32";

    @BeforeEach
    public void createTestUser() {
        UserEntity userEntity = new UserEntity("long", "$23E5dkk3jf3dlf3333kss32144j32");
        TestUtils.testAuditTime(Arrays.asList(userEntity));
        entityManager.persist(userEntity);
    }

    @Test
    public void whenFindByRightName_thenReturnUser() {
        UserEntity user = repository.findUserEntityByUserName(rightName);
        Assertions.assertThat(user.getUserName()).isEqualTo(rightName);
        Assertions.assertThat(user.getEncryptedPassword()).isEqualTo(rightPassword);
    }

    @Test
    public void whenFindByWrongName_thenNotFoundUser() {
        UserEntity userEntity = repository.findUserEntityByUserName(wrongName);
        Assertions.assertThat(userEntity).isNull();
    }

}
