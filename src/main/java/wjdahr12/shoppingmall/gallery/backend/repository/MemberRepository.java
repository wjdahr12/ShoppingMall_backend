package wjdahr12.shoppingmall.gallery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wjdahr12.shoppingmall.gallery.backend.entity.Item;
import wjdahr12.shoppingmall.gallery.backend.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

  Member findByEmailAndPassword(String email, String password);
}
