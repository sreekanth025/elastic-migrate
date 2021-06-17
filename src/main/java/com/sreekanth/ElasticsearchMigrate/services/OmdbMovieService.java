package com.sreekanth.ElasticsearchMigrate.services;

import com.sreekanth.ElasticsearchMigrate.repositories.dataFromSource.OmdbMovieSource;
import com.sreekanth.ElasticsearchMigrate.repositories.dataToTarget.OmdbMovieTarget;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OmdbMovieService {

    @Autowired
    @Qualifier("sourceClient")
    private RestHighLevelClient sourceClient;

    @Autowired
    private OmdbMovieSource omdbMovieSource;

    @Autowired
    private OmdbMovieTarget omdbMovieTarget;

    public void migrateAllMovies() throws IOException {

        SearchResponse searchResponse = omdbMovieSource.getAllMovies();
//        SearchResponse searchResponse = omdbMovieSource.getAllMoviesInRange();
        System.out.println("Started migration");
        SearchHit[] searchHits = searchResponse.getHits().getHits();


        while(searchHits != null && searchHits.length > 0) {

            List<Map<String, Object>> hits = new ArrayList<Map<String, Object>>();
            for(SearchHit searchHit: searchHits) {
                hits.add(searchHit.getSourceAsMap());
            }

            putAllMovies(hits);

            String scrollId = searchResponse.getScrollId();
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(TimeValue.timeValueMinutes(1L));

            searchResponse = sourceClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            searchHits = searchResponse.getHits().getHits();
        }
    }

    public void putAllMovies(List<Map<String, Object>> hits) throws IOException {
        System.out.println("Number of hits: " + hits.size());

        BulkRequest bulkRequest = new BulkRequest();
        hits.forEach( hit -> {
            IndexRequest indexRequest = new IndexRequest("omdb_new_index").source(hit);
            bulkRequest.add(indexRequest);
        });

        omdbMovieTarget.putAllMovies(bulkRequest);
    }
}
