package com.expressbank.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.expressbank.model.StockData;
import com.expressbank.repository.StockDataRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockDataService {

    private List<StockData> stockDataList = new ArrayList<>();

    @Autowired
    private StockDataRepository stockDataRepository;

    public List<StockData> readJson() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("D:\\TahmazovFarid\\OneDrive\\Belgeler\\Workspace\\IdeaProjects\\Payday-Trade\\src\\main\\resources\\json-data\\stocks-data.json")) {

            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray stockJsonArray = (JSONArray) obj;
            System.out.println(stockJsonArray);

            //Iterate over employee array

            stockJsonArray.forEach( stock -> parseStockObject( (JSONObject) stock ) );

            System.out.println("Stock Data List: " + stockDataList);
            return stockDataList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<StockData> parseStockObject(JSONObject stock) {
        //Get stock company
        String company = (String) stock.get("company");
        System.out.println(company);

        //Get stock description
        String description = (String) stock.get("description");
        System.out.println(description);

        //Get stock last price
        Double lastPrice = (Double) stock.get("price_2007");
        System.out.println(lastPrice);

        //Get stock symbol
        String symbol = (String) stock.get("symbol");
        System.out.println(symbol);

        StockData stockData = new StockData(company, description, lastPrice, symbol);
        this.stockDataList.add(stockData);

        stockDataRepository.save(stockData);

        return stockDataList;
    }

    public Optional<StockData> findById(Integer id){
        Optional<StockData> stockData = stockDataRepository.findById(id);
        if (!stockData.isPresent()){
            throw new NullPointerException("Not found this id: " + id);
        }else{
            return stockData;
        }
    }
}
