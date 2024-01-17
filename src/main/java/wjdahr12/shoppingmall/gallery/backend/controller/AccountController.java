package wjdahr12.shoppingmall.gallery.backend.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wjdahr12.shoppingmall.gallery.backend.entity.Item;
import wjdahr12.shoppingmall.gallery.backend.entity.Member;
import wjdahr12.shoppingmall.gallery.backend.repository.ItemRepository;
import wjdahr12.shoppingmall.gallery.backend.repository.MemberRepository;
import wjdahr12.shoppingmall.gallery.backend.service.JwtService;
import wjdahr12.shoppingmall.gallery.backend.service.JwtServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  JwtService jwtService;

  @PostMapping("/api/account/login")
  public ResponseEntity login(
      @RequestBody Map<String, String> params,
      HttpServletResponse res
  ) {
    Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

    if (member !=null) {

      int id = member.getId();
      String token = jwtService.getToken("id", id);

      Cookie cookie = new Cookie("token", token);
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setPath("/");
      res.addCookie(cookie);

      return new ResponseEntity<>(id, HttpStatus.OK);

    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/api/account/logout")
  public ResponseEntity logout(HttpServletResponse res) {
    // 새로운 쿠키는 토큰 값을 null로  설정
    Cookie cookie = new Cookie("token", null);
    cookie.setPath("/");
    cookie.setMaxAge(0);

    res.addCookie(cookie);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/api/account/check")
  public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
    Claims claims = jwtService.getClaims(token);

    if (claims != null) {
      int id = Integer.parseInt(claims.get("id").toString());

      return new ResponseEntity<>(id, HttpStatus.OK);
    }

    return new ResponseEntity<>(null, HttpStatus.OK);

  }

}
