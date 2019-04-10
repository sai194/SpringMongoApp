package com.sye.microservices.mongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
public class Country {
	@Id
	private String id;

	private String name;
	private Integer area;
	private Long population;
	private Continent continent;
	public Country(String name, Integer area, Long population, Continent continent) {
		super();
		
		this.name = name;
		this.area = area;
		this.population = population;
		this.continent = continent;
	}
	public Country() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Long getPopulation() {
		return population;
	}
	public void setPopulation(Long population) {
		this.population = population;
	}
	public Continent getContinent() {
		return continent;
	}
	public void setContinent(Continent continent) {
		this.continent = continent;
	}
	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", area=" + area + ", population=" + population + ", continent="
				+ continent + "]";
	}
	
	
}
