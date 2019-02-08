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

package com.github.ydespreaux.sample.jpa.criteria.repository;

import com.github.ydespreaux.sample.jpa.criteria.SampleJpaCriteriaApplication;
import com.github.ydespreaux.sample.jpa.criteria.utils.GenerateDataBuilder;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SampleJpaCriteriaApplication.class})
@AutoConfigureMockMvc
public class TrackRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GenerateDataBuilder generateDataBuilder;

    @Before
    public void onSetup() {
        generateDataBuilder.cleanData();
        generateDataBuilder.insertData();
    }

    @Test
    public void findSongByQuery_withArtist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/tracks")
                .param("artist", "Marley")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("totalElements", Matchers.is(Matchers.equalTo(33))))
                .andExpect(jsonPath("sort.sorted", Matchers.is(Matchers.equalTo(true))));
    }

    @Test
    public void findSongByQuery_withAlbum() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/tracks")
                .param("album", "voodoo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("totalElements", Matchers.is(Matchers.equalTo(15))))
                .andExpect(jsonPath("sort.sorted", Matchers.is(Matchers.equalTo(true))));
    }

    @Test
    public void findSongByQuery_withYear() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/tracks")
                .param("year", "1970")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("totalElements", Matchers.is(Matchers.equalTo(12))))
                .andExpect(jsonPath("sort.sorted", Matchers.is(Matchers.equalTo(true))));
    }

    @Test
    public void findSongByQuery_withTitle() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/tracks")
                .param("title", "400 years")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("totalElements", Matchers.is(Matchers.equalTo(2))))
                .andExpect(jsonPath("sort.sorted", Matchers.is(Matchers.equalTo(true))));
    }

    @Test
    public void findSongByQuery_withAllCriteria() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/tracks")
                .param("title", "400 years")
                .param("artist", "Marley")
                .param("year", "1973")
                .param("album", "catch a fire")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("totalElements", Matchers.is(Matchers.equalTo(1))))
                .andExpect(jsonPath("sort.sorted", Matchers.is(Matchers.equalTo(true))));
    }
}
