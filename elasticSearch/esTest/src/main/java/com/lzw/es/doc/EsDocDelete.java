package com.lzw.es.doc;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 15:34
 * @Description : 删除文档
 */
public class EsDocDelete {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        DeleteRequest deleteRequest = new DeleteRequest().index("user").id("001");
        DeleteResponse response = esClient.delete(deleteRequest, RequestOptions.DEFAULT);

        System.out.println("删除结果："+response.toString());

        EsClientUtil.closeEsConnection(esClient);
    }
}
