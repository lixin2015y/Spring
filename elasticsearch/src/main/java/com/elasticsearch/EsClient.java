package com.elasticsearch;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EsClient {

    private RestHighLevelClient esClient;

    @Before
    public void before() {
        esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("172.16.2.56", 9200))
        );


    }

    @After
    public void after() {
        // 关闭Es客户端
        try {
            esClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建索引
     */
    @Test
    public void test() throws IOException {
        CreateIndexRequest indexRequest = new CreateIndexRequest("user");
        CreateIndexResponse createIndexResponse = esClient.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println("createIndexResponse.isAcknowledged() = " + createIndexResponse.isAcknowledged());

    }

    /**
     * get
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("user");
        GetIndexResponse getIndexResponse = esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println("getIndexResponse.getAliases() = " + getIndexResponse.getAliases());
        System.out.println("getIndexResponse.getMappings() = " + getIndexResponse.getMappings());
        System.out.println("getIndexResponse.getSetting() = " + getIndexResponse.getSettings());
    }


    @Test
    public void test2() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user");
        AcknowledgedResponse acknowledgedResponse = esClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println("acknowledgedResponse.isAcknowledged() = " + acknowledgedResponse.isAcknowledged());
    }

    /**
     * 插入数据
     */
    @Test
    public void test3() throws IOException {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").type("_doc").id("1");
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", "li xin");
        map.put("age", 11);
        indexRequest.source(JSON.toJSONString(map), XContentType.JSON);
        IndexResponse response = esClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("response.getResult() = " + response.getResult());
    }

    /**
     * 修改数据
     */
    @Test
    public void test4() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").type("_doc").id("1");
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", "li xin");
        map.put("age", 222);
        updateRequest.doc(map);
        UpdateResponse updateResponse = esClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("updateResponse = " + updateResponse.getResult());
    }

    /**
     * 批量更新
     */
    @Test
    public void test5() throws IOException {
        BulkRequest request = new BulkRequest();

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").type("_doc").id("3");
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", "li xin");
        map.put("age", 222);
        updateRequest.doc(map);
        request.add(updateRequest);

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").type("_doc").id("4");
        Map<String, Object> map2 = new HashMap<>(16);
        map2.put("name", "li xin");
        map2.put("age", 11);
        indexRequest.source(JSON.toJSONString(map2), XContentType.JSON);
        request.add(indexRequest);


        BulkResponse bulk = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println("bulk.getTook() = " + bulk.getTook());
        System.out.println("bulk.getItems() = " + bulk.getItems());
    }


    /**
     * 全量查询
     */
    @Test
    public void test6() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse search = esClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("search.getHits() = " + JSON.toJSONString(search.getHits()));
    }



}
