package uz.pdp.lesson4vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.pdp.lesson4vazifa1.entity.Outcome;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

    List<Outcome> findAllByFromCardId(Integer fromCardId);

}
