package com.gunaas.rest.repository;

import com.gunaas.rest.entity.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RelationRepository extends JpaRepository<RelationEntity, Long> {
    public RelationEntity findRelationEntityBySubordinate(String subordinate);

    public int deleteRelationEntityBySubordinate(String subordinate);

}