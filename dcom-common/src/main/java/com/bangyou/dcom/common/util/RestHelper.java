package com.bangyou.dcom.common.util;

import com.yixin.common.exception.BzException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 基于okhttp rest接口调用
 *
 * @author radiance
 */
public class RestHelper {

    private final static int CONNECT_OUT = 20;

    private final static int READ_OUT = 30;

    private final static int WRITE_OUT = 20;

    private final static int delayMills = 2000;//毫秒

    private final static MediaType APPLICATION_JSON_UTF8 = MediaType.parse("application/json;charset=UTF-8");

    private final static Logger logger = LoggerFactory.getLogger(RestHelper.class);

    private static OkHttpClient init(int retrys) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .addInterceptor(new RetryIntercepter(retrys, delayMills))
                .connectTimeout(CONNECT_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_OUT, TimeUnit.SECONDS).build();

        return okHttpClient;

    }

    /**
     * get请求处理
     *
     * @param url 请求路径
     * @param retrys 重试次数
     * @return 响应信息
     * @throws Exception
     */
    public static String doGet(String url,int retrys) throws BzException {

        String result;

        try {
            long threadId = Thread.currentThread().getId();

            logger.info("线程号-" + threadId + "-Rest请求路径-" + url);

            Request request = new Request.Builder().url(url).build();

            OkHttpClient okHttpClient = init(retrys);

            Response response = okHttpClient.newCall(request).execute();

            result = response.body().string();

            logger.info("线程号-" + threadId + "-Rest响应参数-" + result);

        } catch (IOException ex) {

            logger.error("Rest请求异常-" + ex.getMessage(), ex);

            throw new BzException(ex.getMessage(),ex);

        }

        return result;

    }

    /**
     * post请求处理
     *
     * @param url 请求路径
     * @param retrys 重试次数
     * @return 响应信息
     * @throws Exception
     */
    public static String doPost(String url, Object data, int retrys) throws BzException {

        String result;

        try {

            String dataStr = GsonHelper.toJson(data);

            long threadId = Thread.currentThread().getId();

            logger.info("线程号-" + threadId + "-Rest请求路径-" + url);

            logger.info("线程号-" + threadId + "-Rest请求参数-" + dataStr);

            RequestBody requestBody = RequestBody.create(APPLICATION_JSON_UTF8, dataStr);

            Request request = new Request.Builder().url(url).post(requestBody).build();

            OkHttpClient okHttpClient = init(retrys);

            Response response = okHttpClient.newCall(request).execute();

            result = response.body().string();

            logger.info("线程号-" + threadId + "-Rest响应参数-" + result);

        } catch (IOException ex) {

            logger.error("Rest请求异常-" + ex.getMessage(), ex);

            throw new BzException(ex.getMessage(),ex);

        }

        return result;

    }

}
