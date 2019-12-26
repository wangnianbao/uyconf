package com.broada.uyconf.client.test.fetcher.inner.restful;

import java.io.File;

import com.broada.uyconf.client.test.support.utils.DirUtils;
import org.apache.commons.io.FileUtils;

import com.broada.uyconf.core.common.constants.Constants;
import com.broada.uyconf.core.common.json.ValueVo;
import com.broada.uyconf.core.common.restful.RestfulMgr;
import com.broada.uyconf.core.common.restful.core.RemoteUrl;
import com.broada.uyconf.core.common.utils.http.HttpClientUtil;

import mockit.Mock;
import mockit.MockUp;

/**
 * Jmockit法测试的MOck
 *
 * @author wnb
 *
 */
public class RestfulMgrMock extends MockUp<RestfulMgr> {

    public static final String defaultValue = "10000";
    public static final String defaultFileName = "confA.properties";
    public static final String defaultFileContent = "varA=400000\r\nvarA2=500000";

    /**
     * 获取JSON数据
     *
     * @param clazz
     * @param remoteUrl
     *
     * @return
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Mock
    public <T> T getJsonData(Class<T> clazz, RemoteUrl remoteUrl, int retryTimes, int retrySleepSeconds)
            throws Exception {

        ValueVo valueVo = new ValueVo();
        valueVo.setMessage("");
        valueVo.setStatus(Constants.OK);
        valueVo.setValue(defaultValue);

        return (T) valueVo;
    }

    /**
     * @param remoteUrl    远程地址
     * @param fileName     文件名
     * @param localFileDir 本地文件地址
     *
     * @return 如果是放到Classpath目录下，则返回相对Classpath的路径，如果不是，则返回全路径
     *
     * @throws Exception
     */
    @Mock
    public String downloadFromServer(RemoteUrl remoteUrl, String fileName, String localFileDir, String localFileDirTemp,
                                     String targetDirPath, boolean download2Classpath, int retryTimes,
                                     int retrySleepSeconds)
            throws Exception {

        File tempFile = DirUtils.createTempDirectory();
        File tempFile2 = DirUtils.createTempDirectory();

        String tempFilePath = tempFile.getAbsolutePath() + "/" + defaultFileName;
        String tempFile2Path = tempFile2.getAbsolutePath() + "/" + defaultFileName;

        FileUtils.writeStringToFile(new File(tempFilePath), defaultFileContent);
        FileUtils.writeStringToFile(new File(tempFile2Path), defaultFileContent);

        return fileName;
    }

    @Mock
    public void close() {
        HttpClientUtil.close();
    }

}
