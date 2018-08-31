package com.mavenhive.bootcamp.projects.stockmarket.repositories;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavenhive.bootcamp.projects.stockmarket.model.Stock;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;

@Repository
public class StockHistoryRepository {

  public Stock price(String dataSet, String entity) throws RuntimeException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    Stock stock = null;
    try {
      URL url = new URL("https://www.quandl.com/api/v3/datasets/" + dataSet + "/" + entity + ".json?api_key=jJuof97y6vWmGxnCMySG");
      stock = mapper.readValue(url, Stock.class);
      System.out.println("Response Data   " + stock);
    } catch (IOException e) {
      System.out.println("Exception   " + stock);
      throw new RuntimeException(e);
    }
    return stock;
  }
}
