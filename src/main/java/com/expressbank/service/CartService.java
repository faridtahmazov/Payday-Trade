package com.expressbank.service;

import com.expressbank.dto.AddToCartDTO;
import com.expressbank.dto.CartDTO;
import com.expressbank.dto.CartItemDTO;
import com.expressbank.dto.ResponseDTO;
import com.expressbank.model.Cart;
import com.expressbank.model.StockData;
import com.expressbank.model.User;
import com.expressbank.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private StockDataService stockDataService;


    //Create&Update
    public void save(Cart cart){
        cartRepository.save(cart);
    }
    //Read
    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public Optional<Cart> findById(Integer id){
        return cartRepository.findById(id);
    }

    public List<Cart> findByUser(User user){
        return cartRepository.findAllByUserOrderByCreatedDateDesc(user);
    }
    //Delete
    public void deleteById(Integer id){
        cartRepository.deleteById(id);
    }

    public void addToCart(AddToCartDTO addToCartDTO, User user) {
        Optional<StockData> stockData = stockDataService.findById(addToCartDTO.getStockId());

        Cart cart = new Cart();
        cart.setStockData(stockData.get());
        cart.setCreatedDate(new Date());
        cart.setQuantity(addToCartDTO.getQuantity());
        cart.setUser(user);

        cartRepository.save(cart);
    }


    public CartDTO listCartItems(User user) {
        List<Cart> cartList = findByUser(user);

        double totalCost = 0;
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        for (Cart cart: cartList){
            CartItemDTO cartItemDTO = new CartItemDTO(cart);
            totalCost+=cartItemDTO.getQuantity() * cart.getStockData().getLastPrice();

            cartItemDTOS.add(cartItemDTO);
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalCost(totalCost);

        cartDTO.setCartItemDTOList(cartItemDTOS);

        return cartDTO;
    }
}
