package org.dhravid.oauth2.mongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

/*
 * @author Shankar Govindarajan
*/

@Document(collection = "users")
public @Data class MongoUserDetails
{
	@Id
	private String id;
	private String userId;
	private String password;
	private String companyId;
}
