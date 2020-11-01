package org.legion.unity.common.utils;


import org.legion.unity.admin.dao.DistrictDAO;
import org.legion.unity.admin.entity.District;

import java.util.Date;

public class IDNoUtils {

    private static final int[] ratioArr = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] checkCodeList = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    public static boolean isValidIDNo(String idNo) {
        if (StringUtils.isNotBlank(idNo) && idNo.length() == 18) {
            String areaCode = idNo.substring(0, 6);
            String birthYear = idNo.substring(6, 10);
            String birthMonth = idNo.substring(10, 12);
            String birthDay = idNo.substring(12, 14);
            char verifyCode = idNo.substring(17).charAt(0);

            if (!StringUtils.isInteger(areaCode) || !StringUtils.isInteger(birthYear)
                    || !StringUtils.isInteger(birthMonth) || !StringUtils.isInteger(birthDay)) {
                return false;
            }
            DistrictDAO districtDAO = SpringUtils.getBean(DistrictDAO.class);
            District district = districtDAO.findById(Integer.parseInt(areaCode)).orElse(null);
            if (district == null) {
                return false;
            }
            Date date = DateUtils.parseDatetime(birthYear + birthMonth + birthDay, "yyyyMMdd");
            if (date == null || date.after(new Date())) {
                return false;
            }
            char[] idNoCharArr = idNo.toCharArray();
            int idSum = 0;
            int[] idNoNumberArr = new int[idNoCharArr.length];
            for (int i = 0; i < 17; i++) {
                idNoNumberArr[i] = idNoCharArr[i] - '0';
                idSum += idNoNumberArr[i] * ratioArr[i];
            }
            int residue = idSum % 11;
            return checkCodeList[residue] == Character.toUpperCase(verifyCode);
        }
        return false;
    }

    public static String getDistrictCode(String idNo) {
        if (StringUtils.isNotBlank(idNo)) {
            return idNo.substring(0, 6);
        }
        return null;
    }

    public static Date getBirthday(String idNo) {
        if (StringUtils.isNotBlank(idNo)) {
            return DateUtils.parseDatetime(idNo.substring(6, 14), "yyyyMMdd");
        }
        return null;
    }

    public static String getGender(String idNo) {
        if (StringUtils.isNotBlank(idNo)) {
            return Integer.parseInt(idNo.substring(16, 17)) % 2 == 0 ? "F" : "M";
        }
        return null;
    }
}
