package com.revosith.oss;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;

/**
 * @author Revosith
 * @description
 * @date 2022/3/24
 **/
public class OssFileUpload {

    public static void main(String[] args) {
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        String accessKeyId = "xxx";
        String accessKeySecret = "xxxxxxx";
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest("xxx", "xxx/xxx.xlsx"
                , new File("D:\\temp\\xxxxx.xlsx"));
        System.out.println(JSON.toJSONString(ossClient.putObject(putObjectRequest)));
        ossClient.shutdown();
    }
}
