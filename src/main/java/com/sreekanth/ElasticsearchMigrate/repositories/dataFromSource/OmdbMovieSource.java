package com.sreekanth.ElasticsearchMigrate.repositories.dataFromSource;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Repository
public class OmdbMovieSource {

    @Autowired
    @Qualifier("sourceClient")
    private RestHighLevelClient client;

    private int num_records = 100;

    public SearchResponse getAllMovies() throws IOException {

        SearchRequest searchRequest = new SearchRequest("omdb");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(this.num_records);

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        long num_hits = searchResponse.getHits().getTotalHits().value;
        System.out.println("Total number of hits: " + num_hits);

        return searchResponse;
    }

/*
    public SearchResponse getAllMoviesInRange() throws IOException {

        SearchRequest searchRequest = new SearchRequest("omdb");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(this.num_records);

        searchSourceBuilder.query(QueryBuilders.rangeQuery("_timestamp")
                .gte(new GregorianCalendar(2021, Calendar.JUNE, 5).getTime())
                .lte(new GregorianCalendar(2021, Calendar.JUNE, 15).getTime())
        );
        searchRequest.source(searchSourceBuilder);

        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse;
    }
*/

}
