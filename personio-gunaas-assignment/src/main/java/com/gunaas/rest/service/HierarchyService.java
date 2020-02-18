package com.gunaas.rest.service;

import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.entity.RelationEntity;
import com.gunaas.rest.models.TreeNode;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface HierarchyService {
    @NotNull
    TreeNode<String> buildRelationship(@NotNull Map<String, String> relationMap);

    @NotNull
    TreeNode<String> buildHierarchy();

    void updateHierarchy(@NotNull List<EmployeeEntity> employeeEntities, @NotNull List<RelationEntity> relationEntities);
}
