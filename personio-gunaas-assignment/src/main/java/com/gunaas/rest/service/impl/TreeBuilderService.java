package com.gunaas.rest.service.impl;

import com.gunaas.rest.exceptions.CustomException;
import com.gunaas.rest.exceptions.ErrorCode;
import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.entity.RelationEntity;
import com.gunaas.rest.models.TreeNode;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

public class TreeBuilderService {
    private HashMap<String, Integer> empToPositionMap;
    private HashMap<Integer, String> postitionToEmpMap;

    private int[][] matrix;
    private final List<EmployeeEntity> employees;
    private final List<RelationEntity> relations;

    @NotNull
    public final TreeNode<String> build() {
        buildMatrix(relations);
        int root = findRoot();
        return buildTree(root);
    }

    private TreeNode<String> buildTree(Integer root) {
        TreeNode<String> node = new TreeNode<>(employeeName(root));
        List<Integer> subOrdinates = findSubordinateIndexes(root);
        for (Integer subordinateIndex : subOrdinates) {
            node.addChild(buildTree(subordinateIndex));
        }
        return node;
    }

    private final void buildMatrix(List<RelationEntity> relations) {
        for (RelationEntity relationEntity : relations) {
            Integer superiorIndex = empToPositionMap.get(relationEntity.getSuperior());
            List<Integer> superSuperiors = findSuperiorIndexes(superiorIndex);
            fillMatrix(relationEntity, superiorIndex, superSuperiors,
                    new LinkedList<>(Arrays.asList(empToPositionMap.get(relationEntity.getSubordinate()))));
        }
    }

    private final void fillMatrix(RelationEntity relationEntity, int superiorIndex,
                                  List<Integer> superSuperiors, LinkedList<Integer> subordinateIndexes) {
        //Indirect relation with superiors
        for (Integer s : superSuperiors) {
            matrix[s][subordinateIndexes.getLast()] = 2;
        }
        //Ensuring there is a direct relation
        if (empToPositionMap.get(relationEntity.getSuperior()) == superiorIndex
                && empToPositionMap.get(relationEntity.getSubordinate()) == subordinateIndexes.getLast()) {
            matrix[superiorIndex][subordinateIndexes.getLast()] = 1;
        }

        List<Integer> furtherSubordinates = findSubordinateIndexes(subordinateIndexes.getLast());
        //Iterating over children and assigning indirect relation
        for (Integer subordinateIndex : furtherSubordinates) {
            if (subordinateIndex == superiorIndex) {
                subordinateIndexes.add(subordinateIndex);
                raiseConflict(superiorIndex, subordinateIndexes.toArray());
            }
            matrix[superiorIndex][subordinateIndex] = 2;
            subordinateIndexes.add(subordinateIndex);
            fillMatrix(relationEntity, superiorIndex, superSuperiors, subordinateIndexes);
        }
    }

    private final int findRoot() throws CustomException {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            int numberOfZeroes = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    numberOfZeroes++;
                }
            }
            if (numberOfZeroes == 1) {
                result.add(i);
            }
        }
        if (result.size() != 1) throw new CustomException(ErrorCode.HIERARCHY_MULTI_ROOT, "contain multiple root");
        return result.get(0);
    }

    private final void raiseConflict(int superiorIndex, Object[] subordinateIndexes) throws CustomException {
        String list = (postitionToEmpMap.get(superiorIndex) + "->");
        list += Arrays.asList(subordinateIndexes).stream().map(a -> postitionToEmpMap.get(a))
                .collect(Collectors.joining("->"));
        String errorMessage = postitionToEmpMap.get(superiorIndex)
                + " -- cannot be superior for himself/herself || Hierarchy --> " + list;
        throw new CustomException(ErrorCode.HIERARCHY_CONFLICT, errorMessage);
    }

    private final List<Integer> findSubordinateIndexes(Integer superiorIndex) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < matrix[superiorIndex].length; i++) {
            if (matrix[superiorIndex][i] == 1) {
                result.add(i);
            }
        }
        return result;
    }

    private final List<Integer> findSuperiorIndexes(int subordinateIndex) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][subordinateIndex] == 1 || matrix[i][subordinateIndex] == 2) {
                result.add(i);
            }
        }
        return result;
    }

    private String employeeName(int index) {
        return employees.get(index).getEmployeeName();
    }

    public TreeBuilderService(@NotNull List<EmployeeEntity> employees, @NotNull List<RelationEntity> relations) {
        this.employees = employees;
        this.relations = relations;
        this.empToPositionMap = new HashMap<>();
        this.postitionToEmpMap = new HashMap<>();
        this.matrix = new int[employees.size()][employees.size()];
        initialize();
    }

    private void initialize() {
        if (employees.isEmpty() || relations.isEmpty()) {
            throw new CustomException(ErrorCode.HIERARCHY_EMPTY, "Hierarchy is empty");
        }
        int start=0;
        for(EmployeeEntity entity: employees) {
            empToPositionMap.put(entity.getEmployeeName(), start);
            postitionToEmpMap.put(start, entity.getEmployeeName());
            start++;
        }
    }

}
