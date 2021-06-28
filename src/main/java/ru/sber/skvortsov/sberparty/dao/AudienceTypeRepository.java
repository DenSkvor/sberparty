package ru.sber.skvortsov.sberparty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.skvortsov.sberparty.entities.AudienceType;

@Repository
public interface AudienceTypeRepository extends JpaRepository<AudienceType, Long> {
}
