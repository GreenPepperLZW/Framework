package com.lzw.es.query;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 16:37
 * @Description : 查询所有索引
 */
public class QueryAllIndex {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 构建查询的请求体
        SearchSourceBuilder requestBuilder = new SearchSourceBuilder();
        // 查询所有数据
        requestBuilder.query(QueryBuilders.matchAllQuery());
        request.source(requestBuilder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        EsClientUtil.print(hits, response);
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

        EsClientUtil.closeEsConnection(esClient);
    }
}
