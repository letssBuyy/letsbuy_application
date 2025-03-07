package com.application.letsbuy.internal.controllers;

import com.application.letsbuy.internal.dto.AdversimentFilterDto;
import com.application.letsbuy.internal.dto.AllAdversimentsAndLikeDtoResponse;
import com.application.letsbuy.internal.services.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/searches")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Page<AllAdversimentsAndLikeDtoResponse>> searchAdversiments(@RequestParam Optional<Long> idUser, @RequestParam Optional<String> title, Pageable pageable) {

        List<AllAdversimentsAndLikeDtoResponse> results = searchService.searchAdversiments(idUser, title, pageable);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Page<AllAdversimentsAndLikeDtoResponse> pages = new PageImpl<>(results, pageable, results.size());
        return ResponseEntity.ok(pages);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<AllAdversimentsAndLikeDtoResponse>> searchAdversimentsFilter(@RequestParam Optional<Long> idUser, @RequestParam Optional<String> title, @RequestBody AdversimentFilterDto filter, Pageable pageable) {

        List<AllAdversimentsAndLikeDtoResponse> results = searchService.searchAdversiments(idUser, title, pageable);
        if(results.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<AllAdversimentsAndLikeDtoResponse> filteredResults = searchService.searchAdversimentsFilter(results, filter);

        Page<AllAdversimentsAndLikeDtoResponse> pages = new PageImpl<>(filteredResults, pageable, filteredResults.size());
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/android")
    public ResponseEntity<List<AllAdversimentsAndLikeDtoResponse>> searchAdversiments(@RequestParam Optional<Long> idUser, @RequestParam Optional<String> title) {

        List<AllAdversimentsAndLikeDtoResponse> results = searchService.searchAdversiments(idUser, title);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(results);
    }

    @PostMapping("/filter/android")
    public ResponseEntity<List<AllAdversimentsAndLikeDtoResponse>> searchAdversimentsFilter(@RequestParam Optional<Long> idUser, @RequestParam Optional<String> title, @RequestBody AdversimentFilterDto filter) {
        System.out.println(filter);
        List<AllAdversimentsAndLikeDtoResponse> results = searchService.searchAdversiments(idUser, title);
        if(results.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<AllAdversimentsAndLikeDtoResponse> filteredResults = searchService.searchAdversimentsFilter(results, filter);
        return ResponseEntity.ok(filteredResults);
    }
}
