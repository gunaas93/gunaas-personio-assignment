package com.gunaas.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Table(name = "relations", indexes = {@Index(columnList = "subordinate", unique = true, name = "subordinate")})
public class RelationEntity extends AbstractEntity<Long> {

    @Size(max = 1000, min = 1, message = "employee name must be less than or equal to '{max}'")
    @Column(name = "superior", nullable = false)
    private String superior;

    @Size(max = 255, min = 1, message = "employee name must be less than or equal to '{max}'")
    @Column(name = "subordinate", unique = true, nullable = false)
    private String subordinate;

    public RelationEntity(String superior, String subordinate) {
        this.superior = superior;
        this.subordinate = subordinate;
    }

    public RelationEntity() {
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(@NotNull String manager) {
        this.superior = manager;
    }

    public String getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(@NotNull String subordinate) {
        this.subordinate = subordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationEntity that = (RelationEntity) o;
        return subordinate.equals(that.subordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subordinate);
    }
}
