package com.elasticsearch.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
@Data
public class EsConfig extends AbstractElasticsearchConfiguration {

    private String host;
    private Integer port;


    @Override
    public RestHighLevelClient elasticsearchClient() {

        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port))
        );
    }
}
