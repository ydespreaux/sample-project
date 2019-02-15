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

package com.github.ydespreaux.sample.elasticsearch.model;

import com.github.ydespreaux.spring.data.elasticsearch.annotations.*;
import com.github.ydespreaux.spring.data.elasticsearch.core.completion.Completion;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RolloverDocument(
        alias = @Alias(name = "read-albums"),
        index = @Index(name = "albums", type = "album", settingsAndMappingPath = "classpath:settings/indices/albums.index"),
        rollover = @Rollover(
                alias = @Alias(name = "write-albums"),
                maxSize = "50kb",
                trigger = @Trigger(true)
        )
)
public class Album {

    @Id
    private String id;

    private String artistId;

    private String name;
    private LocalDate publication;
    private List<Track> tracks;

    private List<Completion> suggests;
}
