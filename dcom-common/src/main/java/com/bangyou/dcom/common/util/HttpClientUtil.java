package com.bangyou.dcom.common.util;

import com.bangyou.dcom.common.model.MDataMap;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * http client
 * @author radiance
 */
public class HttpClientUtil {

    final static String CONST_BASE_ENCODING = "UTF-8";

    public static HttpClientUtil create() {

        return new HttpClientUtil();

    }

    /**
     * 获取请求链接
     *
     * @param sUrl
     * @param sPost
     * @return
     */
    public String upRequest(String sUrl, String sPost) throws Exception {

        HttpEntity httpEntity = null;

        try {
            httpEntity = new StringEntity(sPost);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return doRequest(sUrl, httpEntity);

    }

    /**
     * 根据链接获取post数据
     *
     * @param sUrl
     * @param mDataMap
     * @return
     * @throws Exception
     */
    public String upPost(String sUrl, MDataMap mDataMap) throws Exception {

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        // nvps.add(new BasicNameValuePair("charset",));

        for (String sKey : mDataMap.keySet()) {
            nvps.add(new BasicNameValuePair(sKey, mDataMap.get(sKey)));
        }

        HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, CONST_BASE_ENCODING);

        // return poolRequest(sUrl, httpEntity);
        return HttpClientUtil.create().doRequest(sUrl, httpEntity);
    }

    /**
     * 根据链接获取post数据---传递head参数
     *
     * @param sUrl
     * @param mDataMap
     * @param headerDataMap
     *            请求头参数
     * @return
     * @throws Exception
     */
    public String upPost(String sUrl, MDataMap mDataMap, MDataMap headerDataMap) throws Exception {

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        // nvps.add(new BasicNameValuePair("charset",));

        for (String sKey : mDataMap.keySet()) {
            nvps.add(new BasicNameValuePair(sKey, mDataMap.get(sKey)));
        }

        HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, CONST_BASE_ENCODING);

        return poolRequest(sUrl, httpEntity, headerDataMap);

    }

    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;

    /**
     * post请求 该请求调用的是连接池功能
     *
     * @param sUrl
     * @param httpEntity
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws java.io.IOException
     */
    public String poolRequest(String sUrl, HttpEntity httpEntity) throws ClientProtocolException, IOException {

        String sReturnString = "";

        if (poolingHttpClientConnectionManager == null) {

            poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

            // Increase max total connection to 200
            poolingHttpClientConnectionManager.setMaxTotal(200);
            // Increase default max connection per route to 20
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);

        }

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
                .build();

        HttpPost httppost = new HttpPost(sUrl);
        httppost.setEntity(httpEntity);

        CloseableHttpResponse response = httpClient.execute(httppost);

        try {
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                sReturnString = EntityUtils.toString(resEntity, CONST_BASE_ENCODING);

            }
        } finally {
            response.close();
        }

        return sReturnString;

    }

    /**
     * post请求 该请求调用的是连接池功能 ---参数添加到head里面
     *
     * @param sUrl
     * @param httpEntity
     * @param headerDataMap
     *            请求头参数
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String poolRequest(String sUrl, HttpEntity httpEntity, MDataMap headerDataMap)
            throws ClientProtocolException, IOException {

        String sReturnString = "";

        if (poolingHttpClientConnectionManager == null) {

            poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

            // Increase max total connection to 200
            poolingHttpClientConnectionManager.setMaxTotal(200);
            // Increase default max connection per route to 20
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);

        }

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
                .build();

        HttpPost httppost = new HttpPost(sUrl);

        for (String sKey : headerDataMap.keySet()) {
            httppost.setHeader(sKey, headerDataMap.get(sKey));
        }

        httppost.setEntity(httpEntity);

        CloseableHttpResponse response = httpClient.execute(httppost);

        try {
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                sReturnString = EntityUtils.toString(resEntity, CONST_BASE_ENCODING);

            }
        } finally {
            response.close();
        }

        return sReturnString;

    }

    public String doRequest(String sUrl, HttpEntity httpEntity)throws Exception
    {
        return doRequest( sUrl,  httpEntity,new MDataMap());
    }

    /**
     * 获取请求
     *
     * @param sUrl
     * @param httpEntity
     * @return
     * @throws Exception
     */
    public String doRequest(String sUrl, HttpEntity httpEntity, MDataMap headerDataMap) throws Exception {
        String sReturnString = null;
        HttpClientBuilder hClientBuilder = HttpClientBuilder.create();

        CloseableHttpClient httpclient = hClientBuilder.build();

        HttpPost httppost = new HttpPost(sUrl);
        // 设置成短链接模式 关闭keep-alve
        httppost.setHeader("Connection", "close");

        for(String sKey:headerDataMap.keySet())
        {
            httppost.setHeader(sKey, headerDataMap.get(sKey));
        }

        CloseableHttpResponse response = null;

        try {

            httppost.setEntity(httpEntity);

            response = httpclient.execute(httppost);

            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                sReturnString = EntityUtils.toString(resEntity);

            }
            if (resEntity != null) {

                EntityUtils.consume(resEntity);

            }

        } catch (Exception e) {
            httppost.reset();
            httpclient = null;
            e.printStackTrace();
            throw e;

        } finally {
            response.close();

            httppost.reset();
            httpclient.close();
            httpclient = null;

        }

        return sReturnString;
    }

    public String doGet(String sUrl) throws Exception {
        String sReturnString = null;
        HttpClientBuilder hClientBuilder = HttpClientBuilder.create();

        CloseableHttpClient httpclient = hClientBuilder.build();

        // HttpPost httppost = new HttpPost(sUrl);

        HttpGet httpGet = new HttpGet(sUrl);
        // 设置成短链接模式 关闭keep-alve
        httpGet.setHeader("Connection", "close");
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpGet);

            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                sReturnString = EntityUtils.toString(resEntity);

            }
            if (resEntity != null) {

                EntityUtils.consume(resEntity);

            }

        } catch (Exception e) {
            httpGet.reset();
            httpclient = null;
            e.printStackTrace();
            throw e;

        } finally {
            response.close();

            httpGet.reset();
            httpclient.close();
            httpclient = null;

        }

        return sReturnString;
    }

    /**
     * 获取Stream的调用方法 一般尽量不能使用该方法操作调用完后需要关闭流
     *
     * @param sUrl
     * @return
     * @throws Exception
     */
    public HttpEntity upEntity(String sUrl) throws Exception {

        String sReturnString = null;
        HttpClientBuilder hClientBuilder = HttpClientBuilder.create();

        HttpEntity resEntity = null;

        CloseableHttpClient httpclient = hClientBuilder.build();

        // HttpPost httppost = new HttpPost(sUrl);

        HttpGet httpGet = new HttpGet(sUrl);
        // 设置成短链接模式 关闭keep-alve
        httpGet.setHeader("Connection", "close");

        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpGet);

            resEntity = response.getEntity();

        } catch (Exception e) {
            httpGet.reset();
            httpclient = null;
            e.printStackTrace();
            throw e;

        } finally {

            httpclient = null;

        }

        return resEntity;

    }

}
