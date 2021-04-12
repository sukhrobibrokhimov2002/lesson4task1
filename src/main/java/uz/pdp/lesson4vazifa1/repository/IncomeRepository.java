package uz.pdp.lesson4vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.pdp.lesson4vazifa1.entity.Card;
import uz.pdp.lesson4vazifa1.entity.Income;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Integer> {

    List<Income> findAllByReceivingCardId(Card receivingCardId);

}
