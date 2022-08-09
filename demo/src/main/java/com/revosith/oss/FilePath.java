package com.revosith.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Revosith
 * @description
 * @date 2022/4/12
 **/
public class FilePath {


    public static void main(String[] args) {

        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        String accessKeyId = "xxxx";
        String accessKeySecret = "xxxxxxxxxxxxxxx";
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\Revosith\\Downloads\\xxx\\xxx\\xxxx"))) {
            paths.filter(Files::isRegularFile)
                    .forEach(e -> {
                        String fileName = UUID.randomUUID() +".pdf";
                        PutObjectRequest putObjectRequest = new PutObjectRequest("xxxxxx", "xxxxxxx/"+fileName
                                , new File(e.toString()));
                        ossClient.putObject(putObjectRequest);
                        System.out.println(fileName+","+e.getFileName());
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
    }
}
