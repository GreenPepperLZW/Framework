package com.lzw.es.index;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/20 23:25
 * @Description : java客户端根据索引名称查询索引
 */
public class EsIndexSearch {
    public static void main(String[] args) throws IOException {
        // 获取es客户段
        RestHighLevelClient esClient = EsClientUtil.getEsClient();

        // 构建查询索引对象
        GetIndexRequest getIndexRequest = new GetIndexRequest("user");
        GetIndexResponse getIndexResponse = esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);

        // 响应结果，与postman测试时返回的结果一样
        // 别名
        System.out.println(getIndexResponse.getAliases());
        // 索引结构
        System.out.println(getIndexResponse.getMappings());
        // 配置
        System.out.println(getIndexResponse.getSettings());

        // 关闭客户端
        EsClientUtil.closeEsConnection(esClient);
    }
}
