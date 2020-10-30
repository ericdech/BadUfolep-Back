package org.ufolep.bad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.ufolep.bad.domain.Championnat;

public interface ChampionnatRepository
	extends CrudRepository<Championnat, Integer> {

	/**
	 * Retourne les championnats auxquel un adhérent a participé en tant que joueur
	 * @param idadherent
	 * @return
	 */
	@Query(
		value =
			"SELECT * "
			+ "FROM CHAMPIONNAT C "
			+ "INNER JOIN JOUEUR J ON (J.IDCHAMPIONNAT = C.IDCHAMPIONNAT) "
			+ "WHERE J.IDADHERENT = :idAdherent",
		nativeQuery = true)
	List<Championnat> findChampionnatByIdAdherent(final @Param("idAdherent") Integer idadherent);
}