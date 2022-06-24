package com.expressbank.controller;

import com.expressbank.dto.ResponseDTO;
import com.expressbank.model.StockData;
import com.expressbank.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock-market")
public class StockDataRestController {

    @Autowired
    private StockDataService stockDataService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getStockData(){
        List<StockData> stockDataList = stockDataService.readJson();

        if (stockDataList.isEmpty() || stockDataList == null){
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.BAD_REQUEST, "Json Array is empty or null!"), HttpStatus.BAD_REQUEST);
        }else{
            System.out.println("Stock Data List: " + stockDataList);
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.OK, stockDataList, "Getting JSon data is successfully!"), HttpStatus.BAD_REQUEST);
        }
    }
}
