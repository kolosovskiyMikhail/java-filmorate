package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/films")
                                .content("{\"id\":1,\"name\":\"Run\",\"description\":\"About Run\",\"releaseDate\":\"1980-05-11\",\"duration\":\"100\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void emptyName() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/films")
                                .content("{\"id\":1,\"name\":\"\",\"description\":\"About Run\",\"releaseDate\":\"1980-05-11\",\"duration\":\"100\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void emptyDescription() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/films")
                                .content("{\"id\":1,\"name\":\"Run\",\"description\":\"\",\"releaseDate\":\"1980-05-11\",\"duration\":\"100\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void descriptionMoreThan200() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/films")
                                .content("{\"id\":1,\"name\":\"Run\",\"description\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                        "aaaaaaaaaaaaaaaaaaaaaaa\",\"releaseDate\":\"1980-05-11\",\"duration\":\"100\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void negativeDuration() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/films")
                                .content("{\"id\":1,\"name\":\"Run\",\"description\":\"About Run\",\"releaseDate\":\"1980-05-11\",\"duration\":\"-100\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void oldReleaseDate() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/films")
                                .content("{\"id\":1,\"name\":\"Run\",\"description\":\"About Run\",\"releaseDate\":\"1894-12-28\",\"duration\":\"100\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}