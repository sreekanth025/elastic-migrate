package com.sreekanth.ElasticsearchMigrate.repositories.dataToTarget;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class OmdbMovieTarget {

    @Autowired
    @Qualifier("targetClient")
    private RestHighLevelClient client;

    public void putAllMovies(BulkRequest bulkRequest) throws IOException {
        client.bulk(bulkRequest, RequestOptions.DEFAULT);

        try {
            Thread.sleep(1000*15);
        } catch(InterruptedException e) {
            System.out.println("Some error occurred during sleep");
            e.printStackTrace();
        }

    }
}
