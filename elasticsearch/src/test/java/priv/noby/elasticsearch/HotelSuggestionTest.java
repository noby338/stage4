package priv.noby.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HotelSuggestionTest {

    private RestHighLevelClient client;

    @Test
    void testSuggest() throws IOException {
        // 1.准备请求
        SearchRequest request = new SearchRequest("hotel2");
        // 2.请求参数
        request.source().suggest(new SuggestBuilder()
                .addSuggestion(
                        "hotelSuggest",
                        SuggestBuilders
                                .completionSuggestion("suggestion")
                                .size(10)
                                .skipDuplicates(true)
                                .prefix("s")
                ));
        // 3.发出请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.解析
        Suggest suggest = response.getSuggest();
        // 4.1.根据名称获取结果
        CompletionSuggestion suggestion = suggest.getSuggestion("hotelSuggest");
        // 4.2.获取options
        for (CompletionSuggestion.Entry.Option option : suggestion.getOptions()) {
            // 4.3.获取补全的结果
            String str = option.getText().toString();
            System.out.println(str);
        }
    }

    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://localhost:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }



}
