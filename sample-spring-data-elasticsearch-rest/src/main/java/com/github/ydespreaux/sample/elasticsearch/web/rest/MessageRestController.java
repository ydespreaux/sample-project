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

import com.github.ydespreaux.sample.elasticsearch.model.Message;
import com.github.ydespreaux.sample.elasticsearch.model.Topic;
import com.github.ydespreaux.sample.elasticsearch.repositories.MessageRepository;
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

import static com.github.ydespreaux.sample.elasticsearch.web.config.SwaggerConfig.TAG_TOPICS;

@RestController
@RequestMapping("/api/messages")
@Api(tags = TAG_TOPICS)
public class MessageRestController {

    @Autowired
    private MessageRepository repository;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Message> findTopicById(@PathVariable(name = "id") String id) {
        return this.repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<? extends Topic>> findByQuery(@RequestParam(required = false, value = "query") String query) {
        QueryBuilder queryBuilder = null;
        if (StringUtils.hasText(query)) {
            queryBuilder = QueryBuilders.matchPhraseQuery("search_fields", query);
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        return ResponseEntity.ok(this.repository.findByQuery(queryBuilder, Sort.unsorted()/*, Sort.by(Sort.Direction.ASC, "name")*/));
    }

    @GetMapping(value = "/question/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Message>> findMessagesByQuestionId(@PathVariable(name = "id") String questionId, @RequestParam(required = false, value = "query") String query) {
        QueryBuilder queryBuilder = null;
        if (StringUtils.hasText(query)) {
            queryBuilder = QueryBuilders.matchPhraseQuery("search_fields", query);
        }
        return ResponseEntity.ok(this.repository.hasParentId(questionId, queryBuilder));
    }

    @GetMapping(value = "/topics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Topic>> findTopicsByQuery(@RequestParam(required = false, value = "query") String query) {
        QueryBuilder queryBuilder = null;
        if (StringUtils.hasText(query)) {
            queryBuilder = QueryBuilders.matchPhraseQuery("search_fields", query);
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        return ResponseEntity.ok(this.repository.hasChildByQuery(queryBuilder));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message document = this.repository.save(message);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(document.getId()).toUri();
        return ResponseEntity.created(location).body(document);
    }
}
