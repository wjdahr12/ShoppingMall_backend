package wjdahr12.shoppingmall.gallery.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private int memberId;

  @Column(length = 50 ,nullable = false)
  private String name;

  @Column(length = 500 ,nullable = false)
  private String address;

  // 결제 수단
  @Column(length = 10 ,nullable = false)
  private String payment;

  @Column(length = 16)
  private String cardNumber;

  // 구입했던 아이템 정보
  @Column(length = 500, nullable = false)
  private String items;

}
