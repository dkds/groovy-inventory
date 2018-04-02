package com.test.common.repo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.NoRepositoryBean

import com.test.common.model.BaseModel

@NoRepositoryBean
interface BaseRepository<ENTITY extends BaseModel> extends MongoRepository<ENTITY, String> {

	@Query("{ 'id' : ?0 }")
	Optional<ENTITY> findByCustomId(int id)
}
