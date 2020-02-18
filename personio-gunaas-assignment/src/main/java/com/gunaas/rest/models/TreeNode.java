package com.gunaas.rest.models;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TreeNode<T> {
    private String value;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;

    public TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public final void addChild(@NotNull TreeNode<T> node) {
        children.add(node);
        node.parent = this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public final String toJsonString() {
        return "{ " + this.toString() + " }";
    }

    public String toString() {
        String childValue = children.stream().map(TreeNode::toString).collect(Collectors.joining(","));
        return "\"" + this.value + "\": { " + childValue + " }";
    }

}
