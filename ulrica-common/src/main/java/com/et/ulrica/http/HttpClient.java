package com.et.ulrica.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:ulrica
 * @date: 2022/7/25 下午2:41
 * @description: generate send http request.
 * @version:1.0
 **/
public class HttpClient {

  private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

  /**
   * The method provide function is that send a http request to hostName:port/path  input params,and
   * default http method is get.
   *
   * @param hostName    this param is supply function instance ip
   * @param port        this param is supply function instance listen port
   * @param path        this param is that supply function instance url
   * @param inputParams this param send http request params
   * @return result is string with json string
   * @throws URISyntaxException
   * @throws HttpException
   * @throws IOException
   */
  public static String executeHttpRequest(String hostName, int port, String path,
      Map<String, String> inputParams) throws URISyntaxException, HttpException, IOException {
    return executeHttpRequest(hostName, port, path, HttpMethodEnum.GET.getMethodName(), inputParams,
        Optional.empty());
  }

  /**
   * The method provide function is that send a http request to hostName:port/path
   *
   * @param hostName    this param is supply function instance ip
   * @param port        this param is supply function instance listen port
   * @param path        this param is that supply function instance url
   * @param methodName  this param is that http request method
   * @param inputParams this param send http request params
   * @param bodyInput   this param send http request body when http method is post or put
   * @return
   * @throws URISyntaxException
   * @throws HttpException
   * @throws IOException
   */
  private static String executeHttpRequest(String hostName, int port, String path,
      String methodName, Map<String, String> inputParams, Optional<String> bodyInput)
      throws URISyntaxException, HttpException, IOException {
    List<NameValuePair> params = inputParamsChange(inputParams);
    URI uri = getUri(hostName, port, path, params);
    HttpUriRequest httpUriRequest = null;
    if (StringUtils.equalsIgnoreCase(methodName, HttpMethodEnum.DELETE.getMethodName())) {
      httpUriRequest = new HttpDelete(uri);
    } else if (StringUtils.equalsIgnoreCase(methodName, HttpMethodEnum.PUT.getMethodName())) {
      HttpPut httpInnerPut = new HttpPut(uri);
      if (bodyInput.isPresent()) {
        httpInnerPut.setEntity(new StringEntity(bodyInput.get(), ContentType.APPLICATION_JSON));
      }
      httpUriRequest = httpInnerPut;
    } else if (StringUtils.equalsIgnoreCase(methodName, HttpMethodEnum.POST.getMethodName())) {
      HttpPost httpPost = new HttpPost(uri);
      if (bodyInput.isPresent()) {
        httpPost.setEntity(new StringEntity(bodyInput.get(), ContentType.APPLICATION_JSON));
      }
      httpUriRequest = httpPost;
    } else {
      httpUriRequest = new HttpGet(uri);
    }
    // TODO: 2022/7/25 add authorization
//    httpUriRequest.addHeader("Authorization", "Basic " + Base64
//        .getUrlEncoder()
//        .encodeToString((HttpConstants.AUTH_USER + ":" + HttpConstants.AUTH_PASSWORD).getBytes()));
    return httpExecute(httpUriRequest);
  }

  /**
   * execute http request
   *
   * @param httpUriRequest request builder
   * @return String
   * @throws IOException   IOException
   * @throws HttpException HttpException, when execute status is not 200
   */
  private static String httpExecute(HttpUriRequest httpUriRequest)
      throws IOException, HttpException {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      CloseableHttpResponse response = httpClient.execute(httpUriRequest);
      if (response.getStatusLine().getStatusCode() != 200) {
        logger.error("execute http request error , response :{}", response.getStatusLine());
        throw new HttpException();
      }
      String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
      return resultString;
    } finally {
      httpClient.close();
    }

  }

  /**
   * change input params to http params
   *
   * @param inputParams input data
   * @return List<NameValuePair>
   */
  private static List<NameValuePair> inputParamsChange(Map<String, String> inputParams) {

    List<NameValuePair> params = new ArrayList<>();
    if (Objects.isNull(inputParams) || inputParams.isEmpty()) {
      return params;
    }
    for (Map.Entry<String, String> entry : inputParams.entrySet()) {
      params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
    return params;

  }

  /**
   * get URI
   *
   * @param hostName instance name
   * @param port     instance port
   * @param path     ulr path
   * @param params   params
   * @return URI
   * @throws URISyntaxException URISyntaxException
   */
  private static URI getUri(String hostName, int port, String path, List<NameValuePair> params)
      throws URISyntaxException {
    if (port <= 0) {
      logger.error("http request error, port is  invalid， port : {}", port);
      throw new URISyntaxException(String.valueOf(port),"http request error, port is  invalid");
    }
    return new URIBuilder().setScheme("http").setHost(hostName).setPort(port).setPath(path)
        .setParameters(params).build();
  }
}
