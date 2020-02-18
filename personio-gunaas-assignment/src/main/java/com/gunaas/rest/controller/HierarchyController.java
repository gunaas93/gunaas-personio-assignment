package com.gunaas.rest.controller;

import com.gunaas.rest.service.HierarchyService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@Scope("request")
@RequestMapping(
        produces = {"application/json"},
        value = {"/hierarchy"}
)
@Validated
public class HierarchyController extends BaseController {
    @Autowired
    private HierarchyService hierarchyService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid @NotNull HashMap<String, String> relationships) {
        return new ResponseEntity((hierarchyService.buildRelationship(relationships).toJsonString()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getHierarchy() {
        return new ResponseEntity(hierarchyService.buildHierarchy().toJsonString(), HttpStatus.OK);
    }
}

