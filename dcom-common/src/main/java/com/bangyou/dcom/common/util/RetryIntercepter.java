package com.bangyou.dcom.common.util;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 超时重试
 *
 * @author radiance
 */
public class RetryIntercepter implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(RetryIntercepter.class);

    private int maxRetry = 5;//最大重试次数

    private int retryNum = 0;//重试次数

    private int retryDelayMills = 1;//延迟执行时间

    public RetryIntercepter(int maxRetry, int retryDelayMills) {

        this.maxRetry = maxRetry;

        this.retryDelayMills = retryDelayMills;


    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        RetryModel retryModel = retry(chain);

        while (retryModel.isRetry() && retryNum < maxRetry) {

            retryNum++;

            retryModel = retry(chain);

            if (retryModel.isRetry()) {

                try {

                    Thread.sleep(retryDelayMills);

                } catch (InterruptedException e) {

                    logger.error(e.getMessage(), e);

                }

            }

            logger.info(retryModel.getRequest().url() + "-重试次数-" + retryNum);


        }


        if (retryNum == maxRetry && maxRetry > 0) {

            if (retryModel.getException() != null){

                throw retryModel.getException();

            }

        }

        return retryModel.getResponse();

    }

    /**
     * @param chain
     * @return
     */
    private RetryModel retry(Chain chain) {

        RetryModel retryModel = new RetryModel();

        Request request = chain.request();

        retryModel.setRequest(request);

        Response response;

        try {

            response = chain.proceed(request);

            if (!response.isSuccessful()) {

                retryModel.setRetry(true);

            }

            retryModel.setResponse(response);

        } catch (IOException ex) {

            retryModel.setRetry(true);

            retryModel.setException(ex);

            logger.error(ex.getMessage(), ex);

        }

        return retryModel;


    }

}
