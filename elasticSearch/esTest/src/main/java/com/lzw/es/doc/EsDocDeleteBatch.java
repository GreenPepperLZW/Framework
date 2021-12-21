package com.lzw.es.doc;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 15:37
 * @Description : 批量删除
 */
public class EsDocDeleteBatch {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();

        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("002"));
        request.add(new DeleteRequest().index("user").id("003"));
        request.add(new DeleteRequest().index("user").id("004"));

        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        // 打印结果信息
        System.out.println(response.getTook());
        System.out.println(response.getItems());

        EsClientUtil.closeEsConnection(esClient);
    }
}
