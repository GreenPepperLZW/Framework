package com.lzw.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/20 23:02
 * @Description : es客户端
 */
public class EsClient {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                // 构建者模式创建对象
                RestClient.builder(new HttpHost("39.104.201.23",9200))
        );

        // 关闭连接，释放资源
        esClient.close();
    }
}
