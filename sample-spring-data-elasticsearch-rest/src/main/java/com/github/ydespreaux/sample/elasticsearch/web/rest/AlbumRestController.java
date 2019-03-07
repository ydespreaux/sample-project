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

import com.github.ydespreaux.sample.elasticsearch.model.Album;
import com.github.ydespreaux.sample.elasticsearch.repositories.AlbumRepository;
import io.swagger.annotations.Api;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.github.ydespreaux.sample.elasticsearch.web.config.SwaggerConfig.TAG_ARTISTS;

@RestController
@RequestMapping("/api/albums")
@Api(tags = TAG_ARTISTS)
public class AlbumRestController {

    @Autowired
    private AlbumRepository repository;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Album> findAlbumById(@PathVariable(name = "id") String albumId) {
        return this.repository.findById(albumId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Album>> findAlbumByQuery(@RequestParam(required = false, value = "query") String query) {
        QueryBuilder queryBuilder = null;
        if (StringUtils.hasText(query)) {
            queryBuilder = QueryBuilders.matchPhraseQuery("search_fields", query);
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        return ResponseEntity.ok(this.repository.findByQuery(queryBuilder, Sort.unsorted()/*, Sort.by(Sort.Direction.ASC, "name")*/));
    }

    @GetMapping(value = "/suggest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Album>> suggest(@RequestParam(required = false, value = "query") String query) {
        return ResponseEntity.ok(this.repository.suggest(query));
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Album> postAlbum(@PathVariable(name = "id") String artistId, @RequestBody Album album) {

        album.setArtistId(artistId);
        Album indexedAlbum = this.repository.save(album);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(indexedAlbum.getId()).toUri();
        return ResponseEntity.created(location).body(indexedAlbum);
    }
}
