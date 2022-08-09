package com.revosith.git;

import com.alibaba.fastjson.JSON;
import com.revosith.util.CollectionUtils;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Revosith
 * @description
 * @date 2021/11/8
 **/
public class GitCommitCount {

    private static String url = "https://xxxxxxxxxx/api/v4";
    private static String token = "xxxxxx";
    static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void main(String[] args) throws Exception {
        //读项目
        HttpGet get = new HttpGet(url + "/projects?per_page=100&private_token=" + token);
        HttpEntity httpEntity = httpClient.execute(get).getEntity();
        String json = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        List<Project> projectList = JSON.parseArray(json, Project.class);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sf.parse("2021-01-01", new ParsePosition(1));

        ThreadPoolExecutor executor = new ThreadPoolExecutor(60, 60, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20), new ThreadPoolExecutor.CallerRunsPolicy());

        Map<String, Integer> total = new HashMap<>();

        for (Project project : projectList) {

            if (project.getId() == 274) {
                continue;
            }
            System.out.println("开始统计项目" + project.getName());

            for (int i = 1; i < 1000; i++) {
                //分页读master commit
                HttpGet getCommit = new HttpGet(url + "/projects/" + project.getId() + "/repository/commits?ref_name=master&since=2021-01-01T00:00:000&page=" + i + "&per_page=100&private_token=" + token);

                HttpEntity commitEntity = httpClient.execute(getCommit).getEntity();
                String commitJson = EntityUtils.toString(commitEntity, StandardCharsets.UTF_8);
                List<Commit> commitList = JSON.parseArray(commitJson, Commit.class);

                if (CollectionUtils.isEmpty(commitList)) {
                    break;
                }
                List<Future<CommitDetail>> futures = new ArrayList<>(commitList.size());

                for (Commit commit : commitList) {
                    //跳过合并
                    if (commit.getCreated_at().before(start) || commit.getTitle().contains("合并分支")
                            || commit.getTitle().contains("merge") || commit.getTitle().contains("Merge")) {
                        continue;
                    }
                    //提取commit明细
                    futures.add(executor.submit(() -> getDetail(project.getId(), commit.getId())));
                }
                //统计
                for (Future<CommitDetail> future : futures) {
                    CommitDetail details = future.get();

                    Integer num = total.get(details.getAuthor_name() + project.getName());
                    if (num == null) {
                        num = 0;
                    }
                    total.put(details.getAuthor_name() + project.getName(), num + details.getStats().getAdditions());
                }
                System.out.println(project.getName() + "进度: 完成项目统计100条");
            }
        }
        for (Map.Entry<String, Integer> entity : total.entrySet()) {
            System.out.println(entity.getKey() + " : " + entity.getValue());
        }
        executor.shutdown();
    }

    static CommitDetail getDetail(Long projectId, String commitId) throws Exception {
        HttpGet commitDetail = new HttpGet(url + "/projects/" + projectId + "/repository/commits/" + commitId + "?ref_name=master&private_token=" + token);
        HttpEntity detailEntity = httpClient.execute(commitDetail).getEntity();
        String detailJson = EntityUtils.toString(detailEntity, StandardCharsets.UTF_8);
        return JSON.parseObject(detailJson, CommitDetail.class);
    }
}

@Data
class Project {
    private Long id;
    private String name;
}

@Data
class Commit {
    private Date created_at;
    private String id;
    private String title;
}

@Data
class CommitDetail {
    private String author_name;
    private String id;
    private CommitStat stats;
}

@Data
class CommitStat {
    private Integer additions;
    private Integer deletions;
}