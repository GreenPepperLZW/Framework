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
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 17:02
 * @Description : 过滤字段查询
 */
public class FilterFileQuery {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // 查询字段过滤
        String [] excludes = {};
        String [] includes = {"age"};
        sourceBuilder.fetchSource(includes, excludes);

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
