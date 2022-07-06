package com.ead.authuser.application.port.in;

import com.ead.authuser.domain.models.enums.RoleType;
import com.ead.authuser.domain.models.RoleModel;

import java.util.Optional;

public interface RoleServicePortIn {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
