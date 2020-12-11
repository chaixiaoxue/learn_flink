package com.cxx.flink.wc;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author chaixiaoxue
 * @version 1.0
 * @date 2020/12/11 16:40
 */
public class Testw {

    public static void main(String[] args) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("cancel-job",true);
        requestBody.put("target-directory","hdfs://192.168.3.101:8020/user/flink/flink-savepoints/plink");
        //先进行savePoint
        String requestBodyString = JsonUtil.toJSONString(requestBody);
        HttpPost httpPost = new HttpPost("http://yx-dc-3-100:8081/v1/jobs/a67d08175326f7ad2aa1eeb7f8f95585/savepoints");
        StringEntity stringEntity = new StringEntity(requestBodyString, StandardCharsets.UTF_8);
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        //httpPatch = new HttpPatch(String.format(FlinkConfigUtil.getRestAddress() + JOBS_JOBId, jobId));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        String resJson = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
        boolean errors1 = JsonUtil.parseObject(resJson).has("errors");

        String errors = JsonUtil.parseObject(resJson).has("errors") ? null : JsonUtil.parseObject(resJson).get("errors").textValue();
    }

}
