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

import com.github.ydespreaux.spring.data.elasticsearch.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RolloverDocument(
        alias = @Alias(name = "read-tracks"),
        index = @Index(name = "tracks", type = "track"),
        rollover = @Rollover(
                alias = @Alias(name = "write-tracks"),
                maxSize = "10gb",
                trigger = @Trigger(enabled = true)
        )
)
public class Track {

    @Id
    private String id;

    private String name;
    private Integer number;
    private Integer length;

}
