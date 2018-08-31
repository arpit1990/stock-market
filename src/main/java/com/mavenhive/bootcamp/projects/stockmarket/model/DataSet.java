package com.mavenhive.bootcamp.projects.stockmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSet {

  @JsonProperty("dataset")
  private BigDecimal id;

  @JsonProperty("name")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("newest_available_date")
  private String newest_available_date;

  @JsonProperty("oldest_available_date")
  private String oldest_available_date;

  @JsonProperty("column_names")
  private List<String> column_names;

  @JsonProperty("start_date")
  private String start_date;

  @JsonProperty("end_date")
  private String end_date;

  @JsonProperty("data")
  private Object listOfStockParametersData;

  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNewest_available_date() {
    return newest_available_date;
  }

  public void setNewest_available_date(String newest_available_date) {
    this.newest_available_date = newest_available_date;
  }

  public String getOldest_available_date() {
    return oldest_available_date;
  }

  public void setOldest_available_date(String oldest_available_date) {
    this.oldest_available_date = oldest_available_date;
  }

  public List<String> getColumn_names() {
    return column_names;
  }

  public void setColumn_names(List<String> column_names) {
    this.column_names = column_names;
  }

  public String getStart_date() {
    return start_date;
  }

  public void setStart_date(String start_date) {
    this.start_date = start_date;
  }

  public String getEnd_date() {
    return end_date;
  }

  public void setEnd_date(String end_date) {
    this.end_date = end_date;
  }

  public Object getListOfStockParametersData() {
    return listOfStockParametersData;
  }

  public void setListOfStockParametersData(Object listOfStockParametersData) {
    this.listOfStockParametersData = listOfStockParametersData;
  }

  @Override
  public String toString() {
    return "DataSet{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", description='" + description + '\'' +
      ", newest_available_date='" + newest_available_date + '\'' +
      ", oldest_available_date='" + oldest_available_date + '\'' +
      ", column_names=" + column_names +
      ", start_date='" + start_date + '\'' +
      ", end_date='" + end_date + '\'' +
      ", listOfStockParametersData=" + listOfStockParametersData +
      '}';
  }
}