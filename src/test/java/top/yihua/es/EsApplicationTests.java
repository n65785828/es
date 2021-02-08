package top.yihua.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yihua.es.product.Goods;
import top.yihua.es.utils.HtmlUtil;

import java.io.IOException;

@SpringBootTest
class EsApplicationTests {


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("my_index");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    void existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("my_index");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("my_index");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    void addDocument() throws IOException {
        IndexRequest request = new IndexRequest("my_index");
        request.timeout("1s");
        Goods cup = HtmlUtil.getData("显卡").get(0);
        request.source(mapper.writeValueAsString(cup), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(index.status());
        System.out.println(index.toString());
    }


}
