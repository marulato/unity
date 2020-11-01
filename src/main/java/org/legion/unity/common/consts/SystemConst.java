package org.legion.unity.common.consts;

public class SystemConst {

    public static final String CLASSPATH    = SystemConst.class.getResource("/").
                                            getPath().replace("%20", " ");

    public static final String MODE_DEV = "DEV";
    public static final String MODE_PRD = "PRD";
    public static final String MODE_UAT = "UAT";

    public static final String ROOT_STORAGE_PATH = CLASSPATH.replace("classes", "data");
    public static final String ROOT_EMAIL_PATH = CLASSPATH.replace("classes", "email");
    public static final String ROOT_TEMP_PATH = CLASSPATH.replace("classes", "temp");
}
