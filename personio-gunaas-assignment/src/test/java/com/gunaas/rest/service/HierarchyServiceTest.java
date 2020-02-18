package com.gunaas.rest.service;

import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.entity.RelationEntity;
import com.gunaas.rest.exceptions.CustomException;
import com.gunaas.rest.models.TreeNode;
import com.gunaas.rest.repository.EmployeeRepository;
import com.gunaas.rest.repository.RelationRepository;
import com.gunaas.rest.service.impl.HierarchyServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class HierarchyServiceTest {
    @InjectMocks
    private HierarchyService hierarchyService = new HierarchyServiceImpl();
    @Mock
    private RelationRepository relationRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void givenHashHierarchy_whenBuildRelationship_thenReplaceCurrentHierarchy() {
        Mockito.when(relationRepository.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        hierarchyService.buildRelationship(createSimpleHierarchy());
        Mockito.verify(relationRepository).saveAll(ArgumentMatchers.anyList());
        Mockito.verify(employeeRepository).saveAll(ArgumentMatchers.anyList());
    }

    @Test
    public void givenReallyBigHierarchy_whenBuildRelationship_thenProcessEfficient() {
        TreeNode tree = hierarchyService.buildRelationship(createBigHierarchy());
        Assertions.assertThat(tree.toJsonString()).isNotBlank();
    }

    @Test(expected = CustomException.class)
    public void givenEmptyHierarchy_whenBuildHierarchy_thenReturnEmpty() {
        Mockito.when(relationRepository.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        hierarchyService.buildHierarchy();
    }

    @Test
    public void givenValidHierarchy_whenBuildHierarchy_thenReturnRightTree() {
        Mockito.when(relationRepository.findAll()).thenReturn(Arrays.asList(new RelationEntity("a", "b")));
        Mockito.when(employeeRepository.findAll()).thenReturn(Arrays.asList(new EmployeeEntity("a"), new EmployeeEntity("b")));
        assertEquals(hierarchyService.buildHierarchy().toJsonString(), "{ \"a\": { \"b\": {  } } }".trim());
    }

    private Map<String, String> createSimpleHierarchy() {
        Map<String, String> relation = new HashMap<>();
        relation.put("Pete", "Nick");
        relation.put("Barbara", "Nick");
        relation.put("Nick", "Sophie");
        relation.put("Sophie", "Jonas");
        return relation;
    }

    private Map<String, String> createBigHierarchy() {
        Map<String, String> hierarchy = new HashMap<>();
        for (int i = 0; i < 500; i++) {
            hierarchy.put(String.valueOf(i), String.valueOf(i + 1));
        }
        return hierarchy;
    }

}
