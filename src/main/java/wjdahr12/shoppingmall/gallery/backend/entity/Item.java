package wjdahr12.shoppingmall.gallery.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(length = 50, nullable = false)
  private String name;

  @Column(length = 100 )
  private String imgPath;

  @Column
  private String price;

  @Column
  private String discountPer;

}
