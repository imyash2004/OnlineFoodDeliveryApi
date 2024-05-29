package com.yash.service;

import com.yash.model.Cart;
import com.yash.model.CartItem;
import com.yash.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req,String jwt)throws Exception;
    public CartItem updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;
    public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;
    public Long calculateCartTotal(Cart cart)throws Exception;
    public Cart findCartById(Long id)throws Exception;
    public Cart findCartByUserId(Long userId)throws Exception;
    public Cart clearCart(Long UserId)throws Exception;


}
