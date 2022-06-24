package com.expressbank.controller;

import com.expressbank.dto.AddToCartDTO;
import com.expressbank.dto.ResponseDTO;
import com.expressbank.model.Cart;
import com.expressbank.model.User;
import com.expressbank.service.CartService;
import com.expressbank.service.RegistrationService;
import com.expressbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    //POST Cart API
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody AddToCartDTO addToCartDTO,
                                                 @RequestParam("id") Integer id){
        Optional<User> user = userService.findById(id);

        cartService.addToCart(addToCartDTO, user.get());

        List<Object> objectList = new ArrayList<>();
        objectList.add(user.get());
        objectList.add(addToCartDTO);
        objectList.add(cartService.listCartItems(user.get()));


        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.CREATED, objectList, "Success!"), HttpStatus.CREATED);
    }

    //GET Cart API
    @GetMapping
    public ResponseEntity<ResponseDTO> getCart(){
        List<Cart> cartList = cartService.findAll();
        if (cartList.isEmpty() || cartList.size()==0){
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.BAD_REQUEST, "Failed"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.OK, cartList, "Success"), HttpStatus.OK);
    }

    //DELETE Cart API
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ResponseDTO> deleteCartItem(@PathVariable("cartItemId") Integer cartItemId,
                                                      @RequestParam("token") String token){
        registrationService.confirmToken(token);

        User user = registrationService.findUserByToken(token);

        cartService.deleteById(cartItemId);
        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.OK, "Deleted is successfully!"), HttpStatus.OK);
    }
}
