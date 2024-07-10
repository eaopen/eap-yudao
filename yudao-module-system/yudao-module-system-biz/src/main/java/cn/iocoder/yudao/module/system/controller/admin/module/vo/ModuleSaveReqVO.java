package cn.iocoder.yudao.module.system.controller.admin.module.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 业务模块新增/修改 Request VO")
@Data
public class ModuleSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "3035")
    private Long id;

    @Schema(description = "模块code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模块code不能为空")
    private String code;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0正常 1停用")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}