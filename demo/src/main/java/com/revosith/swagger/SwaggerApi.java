package com.revosith.swagger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.revosith.util.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Revosith
 * @description
 * @date 2022/3/8
 **/
public class SwaggerApi {

    public static void main(String[] args) throws Exception {

        List<String> systems = Arrays.asList( "xxxxxxx");
        for (String str:systems) {
            HttpGet get = new HttpGet(String.format("http://xxxxxxx/api/%s/v2/api-docs",str));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpEntity httpEntity = httpClient.execute(get).getEntity();
            String json = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            JSONObject temp = JSON.parseObject(json);
            JSONObject tt = (JSONObject) temp.get("paths");

            if(tt==null || CollectionUtils.isEmpty(tt.keySet())){
                continue;
            }
            List<String> keyList = tt.keySet().stream().sorted().collect(Collectors.toList());
            System.out.println("##################");
            System.out.println(str);
            for (String key : keyList) {
                JSONObject detail = (JSONObject) tt.get(key);
                detail.values().forEach(e -> {
                    JSONObject item = (JSONObject) e;
                    System.out.println(key + "\t " + item.get("summary"));
                });
            }
        }
    }
}
