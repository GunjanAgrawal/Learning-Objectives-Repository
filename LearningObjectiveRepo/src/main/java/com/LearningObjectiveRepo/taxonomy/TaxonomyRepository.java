package com.LearningObjectiveRepo.taxonomy;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaxonomyRepository extends CrudRepository<Taxonomy,String>{

	public Taxonomy findByTaxoName(String taxo);
	public Taxonomy findByTaxoId(Long taxoId);
	public Taxonomy findLevelByTaxoId(Long tId);
	
	@Transactional
	@Modifying
	@Query("Delete from Taxonomy where taxo_id= :tId")
	public void deleteFromTaxo(@Param("tId")Long taxoId);
}
