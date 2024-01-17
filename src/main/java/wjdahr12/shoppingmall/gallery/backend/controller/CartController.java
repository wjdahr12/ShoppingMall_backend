package wjdahr12.shoppingmall.gallery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wjdahr12.shoppingmall.gallery.backend.entity.Cart;
import wjdahr12.shoppingmall.gallery.backend.entity.Item;
import wjdahr12.shoppingmall.gallery.backend.repository.CartRepository;
import wjdahr12.shoppingmall.gallery.backend.repository.ItemRepository;
import wjdahr12.shoppingmall.gallery.backend.service.JwtService;

import java.util.List;

@RestController
public class CartController {

  @Autowired
  JwtService jwtService;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  ItemRepository itemRepository;

  @GetMapping("/api/cart/items")
  public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token) {

    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    int memberId = jwtService.getId(token);
    List<Cart> carts = cartRepository.findByMemberId(memberId);
    List<Integer> itemids = carts.stream().map(Cart ::getItemId).toList();
    List<Item> items = itemRepository.findByIdIn(itemids);

    return new ResponseEntity<>(items, HttpStatus.OK);
  }

  @PostMapping("/api/cart/items/{itemId}")
  public ResponseEntity pushCartItem(
      @PathVariable("itemId") int itemId,
      @CookieValue(value = "token", required = false) String token
  ) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    int memberId = jwtService.getId(token);
    Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

    if (cart == null) {
      Cart newCart = new Cart();
      newCart.setMemberId(memberId);
      newCart.setItemId(itemId);
      cartRepository.save(newCart);

    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/api/cart/items/{itemId}")
  public ResponseEntity removeCartItem(
      @PathVariable("itemId") int itemId,
      @CookieValue(value = "token", required = false) String token
  ) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    int memberId = jwtService.getId(token);
    Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

    cartRepository.delete(cart);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
