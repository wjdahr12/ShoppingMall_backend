package wjdahr12.shoppingmall.gallery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wjdahr12.shoppingmall.gallery.backend.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

  List<Item> findByIdIn(List<Integer> ids);
}
