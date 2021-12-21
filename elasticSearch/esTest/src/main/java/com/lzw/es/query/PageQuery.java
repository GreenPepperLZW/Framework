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
 * @Date : 2021/12/21 16:52
 * @Description : 分页查询
 */
public class PageQuery {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("user");

        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // 分页查询
        // 当前页其实索引(第一条数据的顺序号)，from
        sourceBuilder.from(0);
        // 每页显示多少条 size
        sourceBuilder.size(2);

        request.source(sourceBuilder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        EsClientUtil.print(hits,response);
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }

        EsClientUtil.closeEsConnection(esClient);
    }
}
