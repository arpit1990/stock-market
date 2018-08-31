package com.mavenhive.bootcamp.projects.stockmarket.repositories;

import com.mavenhive.bootcamp.projects.stockmarket.model.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/*
@Repository
public interface UsersRepository extends MongoRepository<UserAuth, String> {
    UserAuth findByUsername(String username);
}*/

@Repository
public class UsersRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public UserAuth findByUsernameAndPassword(UserAuth userAuth) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(userAuth.getUsername()));
        query.addCriteria(Criteria.where("password").is(userAuth.getPassword()));
        return mongoTemplate.findOne(query, UserAuth.class);
    }
}