package com.example.TestiranjeProjekat.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.TestiranjeProjekat.Model.ListItem;
import com.example.TestiranjeProjekat.dao.ListItemRepository;

@RestController
public class ListItemController {
	@Autowired
	private ListItemRepository repo;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/InitListItems")
    public String initListItems() {
        ListItem item1 = new ListItem(1, "Prva stvar za uraditi", false);
        ListItem item2 = new ListItem(2, "Druga stvar za uraditi", false);
        ListItem item3 = new ListItem(3, "Treca stvar za uraditi", false);

        repo.save(item1);
        repo.save(item2);
        repo.save(item3);

        return "Inicijalizacija izvršena";
    }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addItem")
	public String addListItem(@RequestBody ListItem item) {
		repo.save(item);
		return "Item saved";
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAllItems")
	public List<ListItem> getAllItems(){
		return repo.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteItem/{id}")
    public String deleteItem(@PathVariable int id) {
        repo.deleteById(id);
        return "Item deleted";
    }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/updateTask/{id}")
	public String updateTask(@PathVariable int id, @RequestBody String newTask) {
	    Optional<ListItem> optionalItem = repo.findById(id);
	    if (optionalItem.isPresent()) {
	        ListItem item = optionalItem.get();
	        item.setTask(newTask);
	        repo.save(item);
	        return "Task updated";
	    } else {
	        return "Item not found";
	    }
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/toggleDone/{id}")
    public String toggleDone(@PathVariable int id) {
        Optional<ListItem> optionalItem = repo.findById(id);
        if (optionalItem.isPresent()) {
            ListItem item = optionalItem.get();
            boolean currentDoneStatus = item.isDone();
            item.setDone(!currentDoneStatus);
            repo.save(item);
            return "Done status toggled";
        } else {
            return "Item not found";
        }
    }
}
