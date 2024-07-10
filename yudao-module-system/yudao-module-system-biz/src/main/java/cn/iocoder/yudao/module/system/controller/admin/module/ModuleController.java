package cn.iocoder.yudao.module.system.controller.admin.module;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.system.controller.admin.module.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.module.ModuleDO;
import cn.iocoder.yudao.module.system.service.module.ModuleService;

@Tag(name = "管理后台 - 业务模块")
@RestController
@RequestMapping("/system/module")
@Validated
public class ModuleController {

    @Resource
    private ModuleService moduleService;

    @PostMapping("/create")
    @Operation(summary = "创建业务模块")
    @PreAuthorize("@ss.hasPermission('system:module:create')")
    public CommonResult<Long> createModule(@Valid @RequestBody ModuleSaveReqVO createReqVO) {
        return success(moduleService.createModule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新业务模块")
    @PreAuthorize("@ss.hasPermission('system:module:update')")
    public CommonResult<Boolean> updateModule(@Valid @RequestBody ModuleSaveReqVO updateReqVO) {
        moduleService.updateModule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除业务模块")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:module:delete')")
    public CommonResult<Boolean> deleteModule(@RequestParam("id") Long id) {
        moduleService.deleteModule(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得业务模块")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:module:query')")
    public CommonResult<ModuleRespVO> getModule(@RequestParam("id") Long id) {
        ModuleDO module = moduleService.getModule(id);
        return success(BeanUtils.toBean(module, ModuleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得业务模块分页")
    @PreAuthorize("@ss.hasPermission('system:module:query')")
    public CommonResult<PageResult<ModuleRespVO>> getModulePage(@Valid ModulePageReqVO pageReqVO) {
        PageResult<ModuleDO> pageResult = moduleService.getModulePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ModuleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出业务模块 Excel")
    @PreAuthorize("@ss.hasPermission('system:module:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportModuleExcel(@Valid ModulePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ModuleDO> list = moduleService.getModulePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "业务模块.xls", "数据", ModuleRespVO.class,
                        BeanUtils.toBean(list, ModuleRespVO.class));
    }

}