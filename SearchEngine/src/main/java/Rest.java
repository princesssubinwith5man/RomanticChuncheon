import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class Rest{
    RestHighLevelClient client;

    public Rest(){
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.30.15", 9200, "http"),
                        new HttpHost("192.168.30.15", 9201, "http")));
    }
    public List<String> start(String name){
        List<Map<String, Object>> arrList = new ArrayList<>();
        List<String> myList = new ArrayList<>();
        String queryStr = "*" + name + "*";

        QueryStringQueryBuilder query = queryStringQuery(queryStr).defaultField("name");

        SearchRequest searchRequest = new SearchRequest("shops");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchRequest.source(searchSourceBuilder);

        try{
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            for(SearchHit s:searchResponse.getHits().getHits())
            {
                Map<String, Object> sourceMap = s.getSourceAsMap();
                System.out.println(s.getIndex());
                arrList.add(sourceMap);
                myList.add(sourceMap.get("name").toString());
            }
            return myList;
        }catch (IOException e){
            System.out.printf("쿼리 실행중 비정상 종료 발생");
            e.printStackTrace();
        }
        return null;
    }

}
