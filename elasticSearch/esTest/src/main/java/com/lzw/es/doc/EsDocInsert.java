package com.lzw.es.doc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzw.es.entity.User;
import com.lzw.es.util.EsClientUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/21 14:30
 * @Description : 新增文档
 */
public class EsDocInsert {
    public static void main(String[] args) throws IOException {
        // 获取Es客户端
        RestHighLevelClient esClient = EsClientUtil.getEsClient();
        // 构建新增文档请求对象
        IndexRequest request = new IndexRequest();
        // 设置索引以及唯一标识,不传唯一标识，会自己生成一个
        request.index("user").id("001");
        // 创建数据对象
        User user = new User("小明",20,"男的");
        // 请求对象中添加json格式文档数据
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUser = objectMapper.writeValueAsString(user);
        XContentType.JSON.ordinal();
        IndexRequest source = request.source(jsonUser, XContentType.JSON);
        // 执行新增操作，获取响应
        IndexResponse index = esClient.index(request, RequestOptions.DEFAULT);
        // 打印结果
        System.out.println("_index:"+index.getIndex());
        System.out.println("_id:"+index.getId());
        // 执行两次以上，_result的结果会从CREATE变成UPDATED
        System.out.println("_result:"+index.getResult());
        // 关闭连接，释放资源
        EsClientUtil.closeEsConnection(esClient);
    }
}
