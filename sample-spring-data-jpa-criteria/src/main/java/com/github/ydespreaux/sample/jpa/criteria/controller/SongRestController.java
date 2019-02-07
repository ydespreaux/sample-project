/*
 * Copyright (C) 2018 Yoann Despréaux
 *
 * This program eq free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program eq distributed in the hope that it will be useful,
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

package com.github.ydespreaux.sample.jpa.criteria.controller;

import com.github.ydespreaux.sample.jpa.criteria.domain.Song;
import com.github.ydespreaux.sample.jpa.criteria.repository.SongRepository;
import com.github.ydespreaux.spring.data.jpa.query.Criteria;
import com.github.ydespreaux.spring.data.jpa.query.QueryOptions;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/songs")
public class SongRestController {

    @Autowired
    private SongRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<SongRestController.SongDetail>> findSongByQuery(
            @RequestParam(required = false, value = "title") String title,
            @RequestParam(required = false, value = "artist") String artist,
            @RequestParam(required = false, value = "album") String album,
            @RequestParam(required = false, value = "year") Integer year,
            Pageable pageable) {

        Criteria criteria = new Criteria();
        if (StringUtils.hasText(title)) {
            criteria = criteria.and("title").contains(title);
        }
        if (StringUtils.hasText(artist)) {
            criteria = criteria.and("album.artist.displayName").contains(artist);
        }
        if (StringUtils.hasText(album)) {
            criteria = criteria.and("album.title").contains(album);
        }
        if (year != null) {
            criteria = criteria.and("album.year").eq(year);
        }
        // Add the default sort if the pageable is unsorted
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "title"));
        }
        return ResponseEntity.ok(this.repository.findAll(criteria, pageable, new QueryOptions().withAssociation("album", "album.artist")).map(this::mapDetail));
    }

    /**
     * @param song
     * @return
     */
    private SongDetail mapDetail(Song song) {
        return SongDetail.builder()
                .id(song.getId())
                .artist(song.getAlbum().getArtist().getDisplayName())
                .album(song.getAlbum().getTitle())
                .year(song.getAlbum().getYear())
                .track(song.getTrack())
                .title(song.getTitle())
                .build();
    }

    @Data
    @Builder
    private static class SongDetail {
        private Integer id;
        private String artist;
        private String album;
        private Integer year;
        private Integer track;
        private String title;
    }
}
