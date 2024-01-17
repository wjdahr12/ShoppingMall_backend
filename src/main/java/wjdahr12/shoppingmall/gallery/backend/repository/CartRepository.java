package wjdahr12.shoppingmall.gallery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wjdahr12.shoppingmall.gallery.backend.entity.Cart;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer> {
  List<Cart> findByMemberId(int memberId);

  Cart findByMemberIdAndItemId(int memberId, int itemId);

  void deleteByMemberId(int memberId);
}
