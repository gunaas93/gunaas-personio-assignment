package com.gunaas.rest.service;

import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.entity.RelationEntity;
import com.gunaas.rest.exceptions.CustomException;
import com.gunaas.rest.models.TreeNode;
import com.gunaas.rest.service.impl.TreeBuilderService;
import javafx.util.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TreeBuilderServiceTest {

    @Test
    public void givenRightHierarchy_whenBuildTree_thenReturnRightTree() {
        Pair<List<EmployeeEntity>, List<RelationEntity>> pair = createRightHierarchy();
        TreeNode treeNode = new TreeBuilderService(pair.getKey(), pair.getValue()).build();
        assertEquals(treeNode.toJsonString(), "{ \"Jonas\": { \"Sophie\": { \"Nick\": { \"Pete\": {  },\"Barbara\": {  } } } } }");
    }

    @Test
    public void givenMultiRootHierarchy_whenBuildTree_thenReturnError() {
        Pair<List<EmployeeEntity>, List<RelationEntity>> pair = createMultiRootHierarchy();
        assertThrows(CustomException.class, () -> new TreeBuilderService(pair.getKey(), pair.getValue()).build());
    }

    @Test
    public void givenConflictHierarchy_whenBuildTree_thenReturnError() {
        Pair<List<EmployeeEntity>, List<RelationEntity>> pair = createConflictHierarchy();
        assertThrows(CustomException.class, () -> new TreeBuilderService(pair.getKey(), pair.getValue()).build());
    }

    @Test
    public void givenSelfEmployedHierarchy_whenBuildTree_thenReturnError() {
        RelationEntity relation = new RelationEntity("Jonas", "Jonas");
        assertThrows(CustomException.class, () -> new TreeBuilderService(Arrays.asList(getJonas()), Arrays.asList(relation)).build());

    }

    @Test
    public void givenEmptyHierarchy_whenBuildTree_thenReturnError() {
        assertThrows(CustomException.class, () -> new TreeBuilderService(Collections.emptyList(), Collections.emptyList()).build());
    }

    private final Pair createRightHierarchy() {
        RelationEntity r1 = new RelationEntity("Nick", "Pete");
        RelationEntity r2 = new RelationEntity("Nick", "Barbara");
        RelationEntity r3 = new RelationEntity("Sophie", "Nick");
        RelationEntity r4 = new RelationEntity("Jonas", "Sophie");
        return new Pair(Arrays.asList(getPete(), getNick(), getBarbara(), getSophie(), getJonas()),
                Arrays.asList(r1, r2, r3, r4));
    }

    private final Pair createMultiRootHierarchy() {
        RelationEntity r1 = new RelationEntity("Nick", "Pete");
        RelationEntity r2 = new RelationEntity("Nick", "Barbara");
        RelationEntity r3 = new RelationEntity("Sophie", "Nick");
        RelationEntity r4 = new RelationEntity("Jonas", "Sophie");
        EmployeeEntity guna = new EmployeeEntity("Guna");
        EmployeeEntity guru = new EmployeeEntity("Guru");
        RelationEntity r5 = new RelationEntity("Guna", "Guru");
        return new Pair(Arrays.asList(getPete(), getNick(), getBarbara(), getSophie(), getJonas(), guna, guru),
                Arrays.asList(r1, r2, r3, r4, r5));
    }

    private final Pair createConflictHierarchy() {
        RelationEntity r1 = new RelationEntity("Nick", "Pete");
        RelationEntity r2 = new RelationEntity("Nick", "Barbara");
        RelationEntity r3 = new RelationEntity("Sophie", "Nick");
        RelationEntity r4 = new RelationEntity("Jonas", "Sophie");
        RelationEntity r5 = new RelationEntity("Sophie", "Jonas");
        return new Pair(Arrays.asList(getPete(), getNick(), getBarbara(), getSophie(), getJonas()),
                Arrays.asList(r1, r2, r3, r4, r5));
    }

    private EmployeeEntity getPete() {
        return new EmployeeEntity("Pete");
    }

    private EmployeeEntity getSophie() {
        return new EmployeeEntity("Sophie");
    }

    private EmployeeEntity getNick() {
        return new EmployeeEntity("Nick");
    }

    private EmployeeEntity getJonas() {
        return new EmployeeEntity("Jonas");
    }

    private EmployeeEntity getBarbara() {
        return new EmployeeEntity("Barbara");
    }
}
