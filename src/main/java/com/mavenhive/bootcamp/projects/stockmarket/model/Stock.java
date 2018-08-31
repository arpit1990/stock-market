package com.mavenhive.bootcamp.projects.stockmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

  @JsonProperty("dataset")
  private DataSet dataset;

  public DataSet getDataset() {
    return dataset;
  }

  public void setDataset(DataSet dataset) {
    this.dataset = dataset;
  }

  @Override
  public String toString() {
    return "Stock{" +
      "dataset=" + dataset +
      '}';
  }
}