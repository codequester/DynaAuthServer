package org.dhravid.oauth2.mongo.service;

import org.dhravid.oauth2.domain.AuthUserDetails;
import org.dhravid.oauth2.mongo.repository.MongoUserDetailsRepository;
import org.dhravid.oauth2.service.AuthUserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author Shankar Govindarajan
*/

@Service("mongoUserStore")
public class MongoUserDetailsService implements AuthUserDetailsService 
{

	@Autowired
	public MongoUserDetailsRepository userDetailsRepository;
	
	@Override
	public AuthUserDetails fetchUserDetails(String userId) 
	{
		AuthUserDetails userDetails = new AuthUserDetails();
		BeanUtils.copyProperties(userDetailsRepository.findByUserId(userId), userDetails);
		return userDetails;
	}
}
