package com.agileboot.infrastructure.web.domain.permission.checker;

import com.agileboot.infrastructure.web.domain.login.LoginUser;
import com.agileboot.infrastructure.web.domain.permission.DataCondition;
import com.agileboot.infrastructure.web.domain.permission.DataPermissionChecker;
import com.agileboot.orm.service.ISysDeptService;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据权限测试接口
 * @author valarchie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleDeptDataPermissionChecker extends DataPermissionChecker {

    private ISysDeptService deptService;

    @Override
    public boolean check(LoginUser loginUser, DataCondition condition) {
        if (condition == null || loginUser == null) {
            return false;
        }
        Long currentDeptId = loginUser.getDeptId();
        Long targetDeptId = condition.getTargetDeptId();

        return targetDeptId != null && Objects.equals(currentDeptId, targetDeptId);
    }


}