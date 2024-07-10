package cn.iocoder.yudao.module.system.service.module;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.module.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.module.ModuleDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.system.dal.mysql.module.ModuleMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 业务模块 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public Long createModule(ModuleSaveReqVO createReqVO) {
        // 插入
        ModuleDO module = BeanUtils.toBean(createReqVO, ModuleDO.class);
        moduleMapper.insert(module);
        // 返回
        return module.getId();
    }

    @Override
    public void updateModule(ModuleSaveReqVO updateReqVO) {
        // 校验存在
        validateModuleExists(updateReqVO.getId());
        // 更新
        ModuleDO updateObj = BeanUtils.toBean(updateReqVO, ModuleDO.class);
        moduleMapper.updateById(updateObj);
    }

    @Override
    public void deleteModule(Long id) {
        // 校验存在
        validateModuleExists(id);
        // 删除
        moduleMapper.deleteById(id);
    }

    private void validateModuleExists(Long id) {
        if (moduleMapper.selectById(id) == null) {
            throw exception(MODULE_NOT_EXISTS);
        }
    }

    @Override
    public ModuleDO getModule(Long id) {
        return moduleMapper.selectById(id);
    }

    @Override
    public PageResult<ModuleDO> getModulePage(ModulePageReqVO pageReqVO) {
        return moduleMapper.selectPage(pageReqVO);
    }

}