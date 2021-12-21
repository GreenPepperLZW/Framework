package com.lzw.es.query;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.IOException;
import java.util.Map;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 17:18
 * @Description : 高亮查询
 */
public class HighLightQuery {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //构建查询方式：高亮查询
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name", "zhangsan");
        //设置查询方式
        sourceBuilder.query(termsQueryBuilder);

        // 构建高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 设置标签前缀
        highlightBuilder.preTags("<font color='red'>");
        // 设置标签后缀
        highlightBuilder.postTags("</font>");
        // 设置高亮字段
        highlightBuilder.field("name");
        // 设置高亮构建对象
        sourceBuilder.highlighter(highlightBuilder);

        request.source(sourceBuilder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        EsClientUtil.print(hits, response);
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            //打印高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
        }

        EsClientUtil.closeEsConnection(esClient);
    }
}
