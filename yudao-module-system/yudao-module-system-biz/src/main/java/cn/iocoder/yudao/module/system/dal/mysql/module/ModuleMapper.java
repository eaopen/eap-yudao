package cn.iocoder.yudao.module.system.dal.mysql.module;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.module.ModuleDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.module.vo.*;

/**
 * 业务模块 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ModuleMapper extends BaseMapperX<ModuleDO> {

    default PageResult<ModuleDO> selectPage(ModulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ModuleDO>()
                .likeIfPresent(ModuleDO::getCode, reqVO.getCode())
                .likeIfPresent(ModuleDO::getName, reqVO.getName())
                .eqIfPresent(ModuleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ModuleDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ModuleDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(ModuleDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(ModuleDO::getId));
    }

}