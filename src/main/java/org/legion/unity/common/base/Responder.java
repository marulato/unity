package org.legion.unity.common.base;

import org.legion.unity.common.cache.CachePool;
import org.legion.unity.common.cache.ICache;
import org.legion.unity.common.cache.MasterCodeCache;
import org.legion.unity.common.consts.AppConst;
import org.legion.unity.common.utils.MessageFormatter;
import org.legion.unity.common.utils.StringUtils;
import org.legion.unity.common.validation.ConstraintViolation;
import org.legion.unity.general.entity.MasterCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class Responder {

    private final Response ajaxResponseBody;
    private final Map<String, String> errorCodes;
    private final List<Object> dataObjects;
    private static final Logger log = LoggerFactory.getLogger(Responder.class);

    private Responder(int code){
        ajaxResponseBody = new Response();
        ajaxResponseBody.setStatus(code);
        errorCodes = new HashMap<>();
        dataObjects = new ArrayList<>();
    }

    public static Responder ready() {
        return new Responder(AppConst.RESPONSE_SUCCESS);
    }

    public void addError(String field, String errorCode) {
        errorCodes.put(StringUtils.isBlank(field) ? "default" : field, errorCode);
    }

    public void addErrors(Map<String, String> errorCode) {
        if (errorCode != null) {
            errorCodes.putAll(errorCode);
        }
    }

    public void addValidations(List<ConstraintViolation> violations) {
        if (violations != null && !violations.isEmpty()) {
            ajaxResponseBody.setStatus(AppConst.RESPONSE_INVALID);
            for (ConstraintViolation violation : violations) {
                if (StringUtils.isNotBlank(errorCodes.get(violation.getValidatedFieldName()))) {
                    continue;
                }
                errorCodes.put(violation.getValidatedFieldName(), violation.getMessage());
            }
            log.info("Validation NOT Pass -> " + violations);
        }
    }

    public void addDataObject(Object object) {
        if (object != null && errorCodes.isEmpty()) {
            dataObjects.add(object);
        }
    }

    public void addDataObjects(List<?> objects) {
        if (objects != null && errorCodes.isEmpty()) {
            dataObjects.addAll(objects);
        }
    }

    public Response ok() {
        ajaxResponseBody.setRespondAt(new Date());
        if (!errorCodes.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            Set<String> keySet = errorCodes.keySet();
            for (String field : keySet) {
                data.put(field, MessageFormatter.getErrorMsg(errorCodes.get(field)));
            }
            ajaxResponseBody.setData(data);
        } else {
            ajaxResponseBody.setData(dataObjects);
        }
        return ajaxResponseBody;
    }

    public Response error() {
        ajaxResponseBody.setRespondAt(new Date());
        ajaxResponseBody.setStatus(AppConst.RESPONSE_ERROR);
        return ajaxResponseBody;
    }
}
