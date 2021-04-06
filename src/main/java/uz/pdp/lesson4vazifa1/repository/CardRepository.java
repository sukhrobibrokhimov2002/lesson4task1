package uz.pdp.lesson4vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lesson4vazifa1.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query(value = "select * from cards where cards.user_name=:username",nativeQuery = true)
    Card findingByUsername(String username);

    boolean existsByNumber(String number);

    boolean existsByNumberAndIdNot(String number, Integer id);
}
