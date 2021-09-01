package br.com.elo7.exploringmars.db;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.elo7.exploringmars.db.entity.MapEntity;

public interface MapRepository extends JpaRepository<MapEntity, Long> {

}
