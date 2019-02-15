/*
 * Copyright (C) 2018 Yoann Despréaux
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING . If not, write to the
 * Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * Please send bugreports with examples or suggestions to yoann.despreaux@believeit.fr
 */

package com.github.ydespreaux.sample.elasticsearch.web.rest;

import com.github.ydespreaux.sample.elasticsearch.model.Artist;
import com.github.ydespreaux.sample.elasticsearch.repositories.ArtistRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistRestController {

    @Autowired
    private ArtistRepository repository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Artist> postArtist(@RequestBody Artist artist) {
        Artist indexedArtist = this.repository.save(artist);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(indexedArtist.getId()).toUri();
        return ResponseEntity.created(location).body(indexedArtist);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Artist> putArtist(@RequestBody Artist artist) {
        if (artist.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Artist indexedArtist = this.repository.save(artist);
        return ResponseEntity.ok(indexedArtist);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> deleteArtist(@PathVariable(name = "id") String id) {
        this.repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Artist> findArtistById(@PathVariable(name = "id") String artistId) {
        return this.repository.findById(artistId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Artist>> findArtistByQuery(@RequestParam(required = false, value = "query") String query) {
        QueryBuilder queryBuilder = null;
        if (StringUtils.hasText(query)) {
            queryBuilder = QueryBuilders.matchPhraseQuery("search_fields", query);
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        return ResponseEntity.ok(this.repository.findByQuery(queryBuilder, Sort.unsorted()/*, Sort.by(Sort.Direction.ASC, "name")*/));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Page size"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sort")
    })
    public ResponseEntity<Page<Artist>> search(@RequestParam(required = false, value = "query") String query, @ApiIgnore Pageable pageable) {
        QueryBuilder queryBuilder = null;
        if (StringUtils.hasText(query)) {
            queryBuilder = QueryBuilders.matchPhraseQuery("search_fields", query);
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        return ResponseEntity.ok(this.repository.findByQuery(queryBuilder, pageable));
    }

    @GetMapping(value = "/search/{scrollId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<Artist>> continueSearch(@PathVariable(value = "scrollId") String scrollId) {
        return ResponseEntity.ok(this.repository.continueScroll(scrollId, Duration.ofMinutes(1L)));
    }
}
