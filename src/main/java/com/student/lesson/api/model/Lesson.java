package com.student.lesson.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "lesson")
public class Lesson {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String code;
	private int credit;
		
	@ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    },
    mappedBy = "lesson")
	@JsonBackReference
	private Set<Student> students = new HashSet<>();
	
    public Lesson(){
    }
    
    public Lesson(String name){
    	this.name = name;
    }
    
    public Lesson(String name, String code, int credit,Set<Student> students){
    	this.name = name;
    	this.code = code;
    	this.credit = credit;
    	this.students = students;
    }
	
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Set<Student> getStudents() {
		return students;
	}
	
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        return id != 0 && id ==((Lesson) o).id;
    }
	@Override
    public int hashCode() {
        return this.id * 31;
    }	
	@Override
	public String toString(){
		String info = "";
		JSONObject jsonInfo = new JSONObject();
		try {
		jsonInfo.put("name",this.name);
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		JSONArray studentArray = new JSONArray();
		if(this.students != null && students.size() > 0){
			this.students.forEach(student->{
				JSONObject subJson = new JSONObject();
				try {
				subJson.put("name", student.getName());
				subJson.put("age",student.getAge());
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
				studentArray.put(subJson);
			});
		}
		try {
		jsonInfo.put("students", studentArray);
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		info = jsonInfo.toString();
		return info;
	}
}