package com.example.TestiranjeProjekat.InegrationTesting;

import com.example.TestiranjeProjekat.Controller.ListItemController;
import com.example.TestiranjeProjekat.dao.ListItemRepository;
import com.example.TestiranjeProjekat.Model.ListItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(ListItemController.class)
public class ListItemControllerIntegrationTesting {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListItemRepository listItemRepository;

    @InjectMocks
    private ListItemController listItemController;

    @Test
    public void testInitListItems() throws Exception {
        // Perform POST request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/InitListItems"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertEquals("Inicijalizacija izvršena", responseContent);
    }
    
    @Test
    public void testGetAllItems() throws Exception {
        // Mock data
        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem(1, "Task 1", false));
        items.add(new ListItem(2, "Task 2", true));

        // Mock repository method
        when(listItemRepository.findAll()).thenReturn(items);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/getAllItems"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"task\":\"Task 1\",\"done\":false},{\"id\":2,\"task\":\"Task 2\",\"done\":true}]"));
    }

    @Test
    public void testAddListItem() throws Exception {
        // Mock data
        ListItem item = new ListItem(1, "New Task", false);

        // Mock repository method
        when(listItemRepository.save(any(ListItem.class))).thenReturn(item);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"task\":\"New Task\",\"done\":false}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Item saved"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteItem/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Item deleted"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        // Mock data
        ListItem item = new ListItem(1, "Updated Task", false);
        Optional<ListItem> optionalItem = Optional.of(item);

        // Mock repository methods
        when(listItemRepository.findById(1)).thenReturn(optionalItem);
        when(listItemRepository.save(any(ListItem.class))).thenReturn(item);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/updateTask/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"Updated Task\""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Task updated"));
    }

    @Test
    public void testToggleDone() throws Exception {
        // Mock data
        ListItem item = new ListItem(1, "Task", false);
        Optional<ListItem> optionalItem = Optional.of(item);

        // Mock repository methods
        when(listItemRepository.findById(1)).thenReturn(optionalItem);
        when(listItemRepository.save(any(ListItem.class))).thenReturn(item);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/toggleDone/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Done status toggled"));
    }
}