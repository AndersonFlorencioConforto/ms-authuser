package com.ead.authuser.framework.adapters.out.repositories;

import com.ead.authuser.domain.models.enums.RoleType;
import com.ead.authuser.domain.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
