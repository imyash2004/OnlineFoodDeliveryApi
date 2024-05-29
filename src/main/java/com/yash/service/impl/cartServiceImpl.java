package com.yash.service.impl;

import com.yash.model.Cart;
import com.yash.model.CartItem;
import com.yash.model.Food;
import com.yash.model.User;
import com.yash.repository.CartItemRepo;
import com.yash.repository.CartRepo;
import com.yash.request.AddCartItemRequest;
import com.yash.service.CartService;
import com.yash.service.FoodService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class cartServiceImpl implements CartService {




    @Autowired
    private CartRepo cartRepo;


    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;




    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Food food=foodService.findFoodById(req.getFoodId());
        Cart cart=cartRepo.findByCustomerId(user.getId());
        for(CartItem cartItem: cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+ req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem=new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()* food.getPrice());

        CartItem savedCartItem=cartItemRepo.save(newCartItem);

        cart.getItems().add(savedCartItem);
        //cartRepo.save()


        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional=cartItemRepo.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("cart item not found");
        }

        CartItem cartItem=cartItemOptional.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice()*quantity);

        return cartItemRepo.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Cart cart=cartRepo.findByCustomerId(user.getId());
        Optional<CartItem> cartItemOptional=cartItemRepo.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("cart item not found");
        }

        CartItem cartItem=cartItemOptional.get();
        cart.getItems().remove(cartItem);



        return cartRepo.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total=0L;
        for(CartItem cartItem:cart.getItems()){
            total+=cartItem.getFood().getPrice()* cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart>opt=cartRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("cart now found");
        }

        return opt.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user=userService.findUserByJwt(jwt);

        Cart cart=cartRepo.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));

        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        User user=userService.findUserByJwt(jwt);
        Cart cart=findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepo.save(cart);
    }
}
