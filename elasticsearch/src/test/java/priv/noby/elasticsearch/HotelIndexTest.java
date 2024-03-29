package priv.noby.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static priv.noby.elasticsearch.constants.HotelIndexConstants.MAPPING_TEMPLATE;
import static priv.noby.elasticsearch.constants.HotelIndexConstants.MAPPING_TEMPLATE2;

@SpringBootTest
class HotelIndexTest {

    private RestHighLevelClient client;;
    @Test
    void testCreateIndex() throws IOException {
        // 1.准备Request      PUT /hotel
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        // 2.准备请求参数
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 该索引库区别与hotel用于搜索补全
     * @throws IOException
     */
    @Test
    void testCreateIndex2() throws IOException {
        // 1.准备Request      PUT /hotel
        CreateIndexRequest request = new CreateIndexRequest("hotel2");
        // 2.准备请求参数
        request.source(MAPPING_TEMPLATE2, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testExistsIndex() throws IOException {
        // 1.准备Request
        GetIndexRequest request = new GetIndexRequest("hotel");
        // 3.发送请求
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);

        System.out.println(isExists ? "存在" : "不存在");
    }
    @Test
    void testDeleteIndex() throws IOException {
        // 1.准备Request
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        // 3.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }


    @BeforeEach
    void setUp() {


        RestClient httpClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();



        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://localhost:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }



}
