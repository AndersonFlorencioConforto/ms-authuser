package com.ead.authuser.application.port.out;

import com.ead.authuser.domain.models.RoleModel;
import com.ead.authuser.domain.models.enums.RoleType;
import java.util.Optional;

public interface RoleRepositoryPortOut {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
