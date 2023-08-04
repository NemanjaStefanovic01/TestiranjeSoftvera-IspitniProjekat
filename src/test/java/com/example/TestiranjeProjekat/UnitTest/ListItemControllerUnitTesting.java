package com.example.TestiranjeProjekat.UnitTest;

import com.example.TestiranjeProjekat.dao.ListItemRepository;
import com.example.TestiranjeProjekat.Controller.ListItemController;
import com.example.TestiranjeProjekat.Model.ListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ListItemControllerUnitTesting {

    private MockMvc mockMvc;

    @Mock
    private ListItemRepository listItemRepository;

    @InjectMocks
    private ListItemController listItemController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(listItemController).build();
    }
    
    @Test
    public void testGetAllItems() throws Exception {
        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem(1, "Task 1", false));
        items.add(new ListItem(2, "Task 2", true));

        when(listItemRepository.findAll()).thenReturn(items);

        mockMvc.perform(get("/getAllItems"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"task\":\"Task 1\",\"done\":false},{\"id\":2,\"task\":\"Task 2\",\"done\":true}]")); 
    }

    @Test
    public void testAddListItem() throws Exception {
        ListItem item = new ListItem(1, "New Task", false);

        when(listItemRepository.save(any(ListItem.class))).thenReturn(item);

        mockMvc.perform(post("/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"task\":\"New Task\",\"done\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item saved"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        mockMvc.perform(delete("/deleteItem/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted"));

        verify(listItemRepository).deleteById(1);
    }

    @Test
    public void testUpdateTask() throws Exception {
        ListItem item = new ListItem(1, "Updated Task", false);
        Optional<ListItem> optionalItem = Optional.of(item);

        when(listItemRepository.findById(1)).thenReturn(optionalItem);
        when(listItemRepository.save(any(ListItem.class))).thenReturn(item);

        mockMvc.perform(post("/updateTask/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"Updated Task\""))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated"));

        verify(listItemRepository).save(item);
    }

    @Test
    public void testToggleDone() throws Exception {
        ListItem item = new ListItem(1, "Task", false);
        Optional<ListItem> optionalItem = Optional.of(item);

        when(listItemRepository.findById(1)).thenReturn(optionalItem);
        when(listItemRepository.save(any(ListItem.class))).thenReturn(item);

        mockMvc.perform(post("/toggleDone/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Done status toggled"));

        verify(listItemRepository).save(item);
    }
}