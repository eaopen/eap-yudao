package cn.iocoder.yudao.module.system.service.module;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.system.controller.admin.module.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.module.ModuleDO;
import cn.iocoder.yudao.module.system.dal.mysql.module.ModuleMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link ModuleServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(ModuleServiceImpl.class)
public class ModuleServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ModuleServiceImpl moduleService;

    @Resource
    private ModuleMapper moduleMapper;

    @Test
    public void testCreateModule_success() {
        // 准备参数
        ModuleSaveReqVO createReqVO = randomPojo(ModuleSaveReqVO.class).setId(null);

        // 调用
        Long moduleId = moduleService.createModule(createReqVO);
        // 断言
        assertNotNull(moduleId);
        // 校验记录的属性是否正确
        ModuleDO module = moduleMapper.selectById(moduleId);
        assertPojoEquals(createReqVO, module, "id");
    }

    @Test
    public void testUpdateModule_success() {
        // mock 数据
        ModuleDO dbModule = randomPojo(ModuleDO.class);
        moduleMapper.insert(dbModule);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ModuleSaveReqVO updateReqVO = randomPojo(ModuleSaveReqVO.class, o -> {
            o.setId(dbModule.getId()); // 设置更新的 ID
        });

        // 调用
        moduleService.updateModule(updateReqVO);
        // 校验是否更新正确
        ModuleDO module = moduleMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, module);
    }

    @Test
    public void testUpdateModule_notExists() {
        // 准备参数
        ModuleSaveReqVO updateReqVO = randomPojo(ModuleSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> moduleService.updateModule(updateReqVO), MODULE_NOT_EXISTS);
    }

    @Test
    public void testDeleteModule_success() {
        // mock 数据
        ModuleDO dbModule = randomPojo(ModuleDO.class);
        moduleMapper.insert(dbModule);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbModule.getId();

        // 调用
        moduleService.deleteModule(id);
       // 校验数据不存在了
       assertNull(moduleMapper.selectById(id));
    }

    @Test
    public void testDeleteModule_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> moduleService.deleteModule(id), MODULE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetModulePage() {
       // mock 数据
       ModuleDO dbModule = randomPojo(ModuleDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setName(null);
           o.setStatus(null);
           o.setRemark(null);
           o.setCreateTime(null);
           o.setDeletedTime(null);
       });
       moduleMapper.insert(dbModule);
       // 测试 code 不匹配
       moduleMapper.insert(cloneIgnoreId(dbModule, o -> o.setCode(null)));
       // 测试 name 不匹配
       moduleMapper.insert(cloneIgnoreId(dbModule, o -> o.setName(null)));
       // 测试 status 不匹配
       moduleMapper.insert(cloneIgnoreId(dbModule, o -> o.setStatus(null)));
       // 测试 remark 不匹配
       moduleMapper.insert(cloneIgnoreId(dbModule, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       moduleMapper.insert(cloneIgnoreId(dbModule, o -> o.setCreateTime(null)));
       // 测试 deletedTime 不匹配
       moduleMapper.insert(cloneIgnoreId(dbModule, o -> o.setDeletedTime(null)));
       // 准备参数
       ModulePageReqVO reqVO = new ModulePageReqVO();
       reqVO.setCode(null);
       reqVO.setName(null);
       reqVO.setStatus(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setDeletedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ModuleDO> pageResult = moduleService.getModulePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbModule, pageResult.getList().get(0));
    }

}