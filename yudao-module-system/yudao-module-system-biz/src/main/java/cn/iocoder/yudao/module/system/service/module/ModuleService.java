package cn.iocoder.yudao.module.system.service.module;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.module.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.module.ModuleDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 业务模块 Service 接口
 *
 * @author 芋道源码
 */
public interface ModuleService {

    /**
     * 创建业务模块
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createModule(@Valid ModuleSaveReqVO createReqVO);

    /**
     * 更新业务模块
     *
     * @param updateReqVO 更新信息
     */
    void updateModule(@Valid ModuleSaveReqVO updateReqVO);

    /**
     * 删除业务模块
     *
     * @param id 编号
     */
    void deleteModule(Long id);

    /**
     * 获得业务模块
     *
     * @param id 编号
     * @return 业务模块
     */
    ModuleDO getModule(Long id);

    /**
     * 获得业务模块分页
     *
     * @param pageReqVO 分页查询
     * @return 业务模块分页
     */
    PageResult<ModuleDO> getModulePage(ModulePageReqVO pageReqVO);

}