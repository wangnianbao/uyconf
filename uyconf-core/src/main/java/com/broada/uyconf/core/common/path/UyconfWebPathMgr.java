package com.broada.uyconf.core.common.path;

import java.util.LinkedHashMap;
import java.util.Map;

import com.broada.uyconf.core.common.constants.Constants;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;

/**
 * uyconf web path 管理
 *
 * @author wnb
 * 14-6-10
 */
public class UyconfWebPathMgr {

    public UyconfWebPathMgr() {

    }

    /**
     * 获取 配置项 或者 是配置ITEM 的远程URL
     *
     * @return
     */
    public static String getRemoteUrlParameter(String urlPrefix, String app, String version, String env, String key,
                                               UyConfigTypeEnum uyConfigTypeEnum) {

        Map<String, String> parameterMap = getConfServerBasePathMap(app, version, env, key);

        // 配置文件或配置项
        parameterMap.put(Constants.TYPE, String.valueOf(uyConfigTypeEnum.getType()));

        StringBuffer sb = new StringBuffer();
        sb.append(urlPrefix);

        if (uyConfigTypeEnum.getType() == UyConfigTypeEnum.FILE.getType()) {
            sb.append(Constants.SEP_STRING + Constants.STORE_FILE_URL_KEY);
        } else {
            sb.append(Constants.SEP_STRING + Constants.STORE_ITEM_URL_KEY);
        }

        sb.append("?");
        for (String thisKey : parameterMap.keySet()) {

            String cur = thisKey + "=" + parameterMap.get(thisKey);
            cur += "&";
            sb.append(cur);
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * @return String
     *
     * @Description: 获取基本配置路径 的MAP
     * @author wnb
     *  2013-6-16
     */
    private static Map<String, String> getConfServerBasePathMap(String app, String version, String env, String key) {

        Map<String, String> parameterMap = new LinkedHashMap<String, String>();

        parameterMap.put(Constants.VERSION, version);
        parameterMap.put(Constants.APP, app);
        parameterMap.put(Constants.ENV, env);
        parameterMap.put(Constants.KEY, key);

        return parameterMap;
    }

    /**
     * 获取 Uyconf-Web 上的ZOO获取URL地址
     *
     * @return
     */
    public static String getZooHostsUrl(String urlPrefix) {

        return urlPrefix + Constants.SEP_STRING + Constants.ZOO_HOSTS_URL_KEY;
    }

    /**
     * 获取 Uyconf-Web 上的ZOO PrefixURL
     *
     * @return
     */
    public static String getZooPrefixUrl(String urlPrefix) {

        return urlPrefix + Constants.SEP_STRING + Constants.ZOO_HOSTS_URL_PREFIX_KEY;
    }
}
