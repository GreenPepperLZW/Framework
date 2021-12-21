package com.lzw.es.doc;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 15:38
 * @Description : 文档批量新增
 */
public class EsDocInsertBatch {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        // 创建批量新增请求对象
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest().index("user").id("002").source(XContentType.JSON, "name", "zhangsan"));
        bulkRequest.add(new IndexRequest().index("user").id("003").source(XContentType.JSON, "name", "lisi"));
        bulkRequest.add(new IndexRequest().index("user").id("004").source(XContentType.JSON, "name", "wangwu"));

        // 执行批量新增操作
        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        // 打印结果信息
        System.out.println("耗时" + response.getTook());
        System.out.println("新增条目" + response.getItems());

        EsClientUtil.closeEsConnection(esClient);
    }
}
