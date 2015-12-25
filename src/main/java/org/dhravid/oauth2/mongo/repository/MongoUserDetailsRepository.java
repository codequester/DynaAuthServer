package org.dhravid.oauth2.mongo.repository;

import org.dhravid.oauth2.mongo.domain.MongoUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserDetailsRepository extends MongoRepository<MongoUserDetails, String> 
{
	public MongoUserDetails findByUserId(String userId);
}
