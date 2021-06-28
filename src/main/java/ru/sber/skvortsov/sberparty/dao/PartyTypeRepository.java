package ru.sber.skvortsov.sberparty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.skvortsov.sberparty.entities.PartyType;

@Repository
public interface PartyTypeRepository extends JpaRepository<PartyType, Long> {
}
