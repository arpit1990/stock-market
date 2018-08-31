package com.mavenhive.bootcamp.projects.stockmarket.services;

import com.mavenhive.bootcamp.projects.stockmarket.model.Stock;
import com.mavenhive.bootcamp.projects.stockmarket.repositories.StockHistoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.ValidationException;

@Service
public class StockHistoryService {

  @Inject
  private StockHistoryRepository stockHistoryRepository;

  public Stock price(String dataSet, String entity) {
    Stock stock = stockHistoryRepository.price(dataSet, entity);

    if(stock == null) {
      throw new ValidationException("No Record found for dataset " + dataSet + " & entity " + entity);
    }
    return stock;
  }
}
