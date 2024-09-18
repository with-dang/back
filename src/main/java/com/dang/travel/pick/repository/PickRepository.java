package com.dang.travel.pick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dang.travel.pick.domain.Pick;

public interface PickRepository extends JpaRepository<Pick, Long> {
	List<Pick> findAllBy();
}
