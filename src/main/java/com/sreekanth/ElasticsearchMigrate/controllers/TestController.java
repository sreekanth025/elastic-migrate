package com.sreekanth.ElasticsearchMigrate.controllers;

import com.sreekanth.ElasticsearchMigrate.services.OmdbMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private OmdbMovieService omdbMovieService;

    @RequestMapping("/allMovies")
    public void migrateAllMovies() throws IOException {
        omdbMovieService.migrateAllMovies();
    }
}
