package com.lzw.es.index;

import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/20 23:37
 * @Description : 根据索引名称删除索引
 */
public class EsIndexDelete {
    public static void main(String[] args) throws IOException {
        // 获取es客户端
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        // 构建删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user");
        // 执行删除操作
        AcknowledgedResponse delete = esClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        // 响应结果
        boolean acknowledged = delete.isAcknowledged();
        System.out.println("删除索应："+acknowledged);
        // 关闭客户端
        EsClientUtil.closeEsConnection(esClient);
    }
}
