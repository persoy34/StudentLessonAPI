package com.student.lesson.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String name;

	@NotNull
	@Column(unique = true)
	private Integer age;

	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
	@JoinTable(name = "student_lesson", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"))
	private Set<Lesson> lesson= new HashSet<>();

	public Student() {

	}

	public Student(String name) {
		this.name = name;
	}

	public Student(String name, int age,Set<Lesson> lesson) {
		this.name = name;
		this.age = age;
		this.lesson = lesson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Set<Lesson> getLesson() {
		return lesson;
	}

	public void setLesson(Set<Lesson> subjects) {
		this.lesson = subjects;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return id != 0 && id ==((Student) o).id;
    }
	@Override
    public int hashCode() {
        return (int) (this.getId()/ 16 * 31);
    }	
	@Override
	public String toString() {
		String info = "";
		JSONObject jsonInfo = new JSONObject();
		try {
			jsonInfo.put("name", this.name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray subArray = new JSONArray();
		this.lesson.forEach(sub -> {
			JSONObject subJson = new JSONObject();
			try {
				subJson.put("name", sub.getName());
				subJson.put("code", sub.getCode());
				subJson.put("credit", sub.getCredit());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			subArray.put(subJson);
		});
		try {
			jsonInfo.put("lessons", subArray);
			info = jsonInfo.toString();
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		return info;
	}
}