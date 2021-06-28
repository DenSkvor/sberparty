package ru.sber.skvortsov.sberparty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.skvortsov.sberparty.entities.DishType;

@Repository
public interface DishTypeRepository extends JpaRepository<DishType, Long> {
}
