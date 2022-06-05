package com.rpc.framework.register;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import com.rpc.framework.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteRegister {

    static CuratorFramework client;

    static Map<String, List<URL>> UrlCache = new HashMap<>();

    static {
        client = CuratorFrameworkFactory
                .newClient("172.16.2.218:2181", new RetryNTimes(3, 1000));
        client.start();
    }

    // 创建一个容器节点
    static public void register(String interfaceName, URL url) {
        try {
            String result = client
                    .create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(String.format("/dubbo/service/%s/%s", interfaceName, JSON.toJSONString(url)), null);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<URL> get(String interfaceName) {
        if (UrlCache.containsKey(interfaceName)) {
            return UrlCache.get(interfaceName);
        } else {
            List<URL> urlList = new ArrayList<>();
            try {
                List<String> result = client.getChildren().forPath(String.format("/dubbo/service/%s", interfaceName));
                for (String urlstr : result) {
                    urlList.add(JSONObject.parseObject(urlstr, URL.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            UrlCache.put(interfaceName, urlList);
            return urlList;
        }
    }
}
