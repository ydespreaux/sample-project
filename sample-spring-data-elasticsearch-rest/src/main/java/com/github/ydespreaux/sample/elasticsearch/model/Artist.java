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
 *
 */

package com.github.ydespreaux.sample.elasticsearch.model;

import com.github.ydespreaux.sample.elasticsearch.model.enums.ArtistTypeEnum;
import com.github.ydespreaux.sample.elasticsearch.model.enums.GenderEnum;
import com.github.ydespreaux.spring.data.elasticsearch.annotations.Alias;
import com.github.ydespreaux.spring.data.elasticsearch.annotations.Index;
import com.github.ydespreaux.spring.data.elasticsearch.annotations.IndexedDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@IndexedDocument(
        alias = @Alias(name = "read-artists"),
        index = @Index(name = "artists", type = "artist", settingsAndMappingPath = "classpath:settings/indices/artist.index"))
public class Artist {

    @Id
    private String id;

    private String name;
    private String shortName;
    private ArtistTypeEnum type;
    private GenderEnum gender;


}
