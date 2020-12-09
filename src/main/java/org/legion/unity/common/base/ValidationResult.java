package org.legion.unity.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.legion.unity.common.utils.MessageFormatter;
import org.legion.unity.common.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel
public class ValidationResult implements Serializable {

    @ApiModelProperty(value = "接口验证返回值，key(字段名): value(具体错误信息)", dataType = "map")
    private Map<String, String> map;

    public ValidationResult() {
        map = new HashMap<>();
    }

    public void addFailedField(String field, String errorMsg) {
        map.put(field, MessageFormatter.getErrorMsg(errorMsg));
    }

    public void addFailedFields(List<ConstraintViolation> violations) {
        if (violations != null && !violations.isEmpty()) {
            for (ConstraintViolation violation : violations) {
                map.put(violation.getValidatedFieldName(), violation.getMessage());
            }
        }
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
