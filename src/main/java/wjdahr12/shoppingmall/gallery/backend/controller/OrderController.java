package wjdahr12.shoppingmall.gallery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wjdahr12.shoppingmall.gallery.backend.dto.OrderDto;
import wjdahr12.shoppingmall.gallery.backend.entity.Order;
import wjdahr12.shoppingmall.gallery.backend.repository.CartRepository;
import wjdahr12.shoppingmall.gallery.backend.repository.OrderRepository;
import wjdahr12.shoppingmall.gallery.backend.service.JwtService;

import java.util.List;

@RestController
public class OrderController {

  @Autowired
  JwtService jwtService;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  OrderRepository orderRepository;

  @GetMapping("/api/orders")
  public ResponseEntity getOrder(
      @CookieValue(value = "token", required = false) String token
  ) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    int memberId = jwtService.getId(token);
    List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);

    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @Transactional
  @PostMapping("/api/orders")
  public ResponseEntity pushOrder(
      @RequestBody OrderDto dto,
      @CookieValue(value = "token", required = false) String token
  ) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    int memberId = jwtService.getId(token);

    Order newOrder = new Order();
    newOrder.setMemberId(jwtService.getId(token));
    newOrder.setName(dto.getName());
    newOrder.setAddress(dto.getAddress());
    newOrder.setPayment(dto.getPayment());
    newOrder.setCardNumber(dto.getCardNumber());
    newOrder.setItems(dto.getItems());
  
    // 두가지 디비조작을 하기에 @Transactional이 필요함
    orderRepository.save(newOrder);
    cartRepository.deleteByMemberId(memberId);

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
