package org.legion.unity.admin.pk;

import com.google.common.base.Objects;
import javax.persistence.Embeddable;
import java.io.Serializable;

public class ConfigPK implements Serializable {

    private String configKey;
    private String configValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigPK configPK = (ConfigPK) o;
        return Objects.equal(configKey, configPK.configKey) &&
                Objects.equal(configValue, configPK.configValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(configKey, configValue);
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
