package com.example.TestiranjeProjekat.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListItem {
	@Id
	@GeneratedValue
	private int id;
	private String task;
	private boolean done;
	
	public ListItem(){}
	public ListItem(int id, String task, boolean done) {
		this.id = id;
		this.task = task;
		this.done = done;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	
	
}
