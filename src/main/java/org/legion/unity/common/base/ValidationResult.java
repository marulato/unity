package org.legion.unity.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.legion.unity.common.utils.MessageFormatter;
import org.legion.unity.common.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel
public class ValidationResult implements Serializable {

    @ApiModelProperty(value = "验证状态码", example = "1")
    private int validationCode;
    @ApiModelProperty(value = "验证失败的字段个数", example = "2")
    private int failedCounts;
    @ApiModelProperty(value = "接口验证返回值")
    private List<Violation> violations;
    @ApiModelProperty(value = "全局信息", example = "false")
    private boolean passed;

    public ValidationResult() {
        violations = new ArrayList<>();
    }

    public void addFailedField(String field, String errorMsg) {
        Violation violation = new Violation(field, errorMsg);
        violations.add(violation);
    }

    public void addFailedFields(List<ConstraintViolation> cvs) {
        if (violations != null && !violations.isEmpty()) {
            for (ConstraintViolation violation : cvs) {
                violations.add(new Violation(violation.getValidatedFieldName(), violation.getMessage()));
            }
        }
    }

    @ApiModel
    private static class Violation {
        @ApiModelProperty(value = "字段名", example = "username")
        private String name;
        @ApiModelProperty(value = "错误信息", example = "不能为空")
        private String msg;

        Violation(String name, String msg) {
            this.name = name;
            this.msg = msg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    public int getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getFailedCounts() {
        return failedCounts;
    }

    public void setFailedCounts(int failedCounts) {
        this.failedCounts = failedCounts;
    }
}
