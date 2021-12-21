package com.lzw.es.doc;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 15:30
 * @Description : 文档查询
 */
public class EsDocGet {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        GetRequest getRequest = new GetRequest().index("user").id("001");
        GetResponse response = esClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println("_index："+response.getIndex());
        System.out.println("_type："+response.getType());
        System.out.println("_id："+response.getId());
        System.out.println("_result："+response.getSourceAsString());

        EsClientUtil.closeEsConnection(esClient);
    }
}
