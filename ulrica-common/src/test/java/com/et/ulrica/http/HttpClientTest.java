package com.et.ulrica.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpException;
import org.junit.Test;

/**
 * @author:ulrica
 * @date: 2022/7/25 下午3:05
 * @description:
 * @version:1.0
 **/
public class HttpClientTest {

  @Test
  public void sendGetRequestTest() throws HttpException, URISyntaxException, IOException {
    String hostName = "localhost";
    String result = HttpClient.executeHttpRequest(hostName, 8080, "/test", null);
    System.out.println(result);
  }

  @Test
  public void sendGetRequestWithParamsTest() throws HttpException, URISyntaxException, IOException {
    String hostName = "localhost";
    Map<String,String> params = new HashMap<>();
    params.put("id","123456");
    String result = HttpClient.executeHttpRequest(hostName, 8080, "/test1", params);
    System.out.println(result);
  }
}
