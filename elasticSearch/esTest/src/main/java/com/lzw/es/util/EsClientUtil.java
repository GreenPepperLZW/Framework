package com.lzw.es.util;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/20 23:20
 * @Description : 获取连接，关闭连接工具类
 */
public class EsClientUtil {

    private static RestHighLevelClient esClient;
    static {
        // 创建ES客户端
            esClient = new RestHighLevelClient(
                // 构建者模式创建对象
                RestClient.builder(new HttpHost("39.104.201.23",9200))
        );
    }

    public static RestHighLevelClient getEsClient(){
        return esClient;
    }

    public static void closeEsConnection(RestHighLevelClient esClient){
        try {
            if (null != esClient){
                esClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(SearchHits hits,SearchResponse response){
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
    }
}
