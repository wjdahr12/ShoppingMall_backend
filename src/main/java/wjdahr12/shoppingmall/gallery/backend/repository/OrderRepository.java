package wjdahr12.shoppingmall.gallery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wjdahr12.shoppingmall.gallery.backend.entity.Order;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

  List<Order> findByMemberIdOrderByIdDesc(int memberId);
}
