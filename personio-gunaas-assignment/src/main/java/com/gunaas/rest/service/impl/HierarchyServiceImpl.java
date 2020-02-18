package com.gunaas.rest.service.impl;

import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.entity.RelationEntity;
import com.gunaas.rest.models.TreeNode;
import com.gunaas.rest.repository.EmployeeRepository;
import com.gunaas.rest.repository.RelationRepository;
import com.gunaas.rest.service.HierarchyService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@Scope("prototype")
public class HierarchyServiceImpl implements HierarchyService {
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public TreeNode buildRelationship(@NotNull Map<String, String> relationships) {
        /* fetch old relationships */
        List<EmployeeEntity> alreadyExistingEmployees = employeeRepository.findAll();
        List<RelationEntity> alreadyExistingRelations = relationRepository.findAll();
        /* process new relationships */
        Pair<List<EmployeeEntity>, List<RelationEntity>> pair = buildEntity(relationships);
        List<EmployeeEntity> employeeEntities = pair.getKey();
        List<RelationEntity> relationEntities = pair.getValue();

        /* remove the old relations which are likely to get modified now */
        List<RelationEntity> relationsToBeRemoved = new ArrayList<>();
        for(RelationEntity relationEntity : alreadyExistingRelations) {
            if(relationEntities.contains(relationEntity)) {
                relationsToBeRemoved.add(relationEntity);
            }
        }
        alreadyExistingRelations.removeAll(relationsToBeRemoved);

        /* aggregate the lists */
        alreadyExistingEmployees.addAll(employeeEntities);
        alreadyExistingRelations.addAll(relationEntities);

        List<EmployeeEntity> employeeEntitiesList = new LinkedList<>( new LinkedHashSet<>(alreadyExistingEmployees));
        List<RelationEntity> relationEntityList = new LinkedList<>(new LinkedHashSet<>(alreadyExistingRelations));
        TreeNode<String> treeNode = new TreeBuilderService(employeeEntitiesList, relationEntityList).build();
        updateHierarchy(employeeEntitiesList, relationEntityList);
        return treeNode;
    }

    @Override
    public TreeNode buildHierarchy() {
        return new TreeBuilderService(employeeRepository.findAll(), relationRepository.findAll()).build();
    }

    @Override
    public void updateHierarchy(@NotNull List<EmployeeEntity> employees, @NotNull List<RelationEntity> relations) {
        for (EmployeeEntity entity : employees) {
            if (employeeRepository.findEmployeeEntityByEmployeeName(entity.getEmployeeName()) != null) {
                employeeRepository.deleteEmployeeEntityByEmployeeName(entity.getEmployeeName());
            }
        }
        for (RelationEntity entity : relations) {
            if (relationRepository.findRelationEntityBySubordinate(entity.getSubordinate()) != null) {
                relationRepository.deleteRelationEntityBySubordinate(entity.getSubordinate());
            }
        }
        employeeRepository.saveAll(employees);
        relationRepository.saveAll(relations);
    }

    private final Pair buildEntity(Map<String, String> relationships) {
        List<EmployeeEntity> employees = new LinkedList<>();
        List<RelationEntity> relationEntities = new LinkedList<>();

        for (Map.Entry relation : relationships.entrySet()) {
            EmployeeEntity employeeEntityOne = new EmployeeEntity(String.valueOf(relation.getKey()));
            EmployeeEntity employeeEntityTwo = new EmployeeEntity(String.valueOf(relation.getValue()));
            employees.add(employeeEntityOne);
            employees.add(employeeEntityTwo);
            RelationEntity relationEntity = new RelationEntity(String.valueOf(relation.getValue()),
                    String.valueOf(relation.getKey()));
            relationEntities.add(relationEntity);
        }
        return new Pair(employees, relationEntities);
    }
}
