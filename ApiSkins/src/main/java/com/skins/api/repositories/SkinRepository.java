package com.skins.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.skins.api.model.Skin;

public interface SkinRepository extends CrudRepository<Skin, Long> {
	
	@Modifying
    @Transactional
    @Query(value = "INSERT INTO skinsadquiridas (userId, skinId) VALUES (:userId, :skinId)", nativeQuery = true)
    Integer savePurchasedSkin(Long skinId, Long userId);

	@Transactional
	@Query(value = "SELECT s.* FROM skin s, skinsadquiridas sA  WHERE sA.userId = :userId and sA.skinId = s.id", nativeQuery = true)
	List<Skin> findByUserId(Long userId);

	@Transactional
	@Query(value = "SELECT s.* FROM skin s, skinsadquiridas sA  WHERE sA.userId = :userId and sA.skinId = :skinId and sA.skinId = s.id", nativeQuery = true)
	Optional<Skin> findByPurchasedAndUserId(Long userId, Long skinId);
}
