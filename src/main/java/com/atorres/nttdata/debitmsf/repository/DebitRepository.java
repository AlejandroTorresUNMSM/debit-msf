package com.atorres.nttdata.debitmsf.repository;

import com.atorres.nttdata.debitmsf.model.dao.DebitDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DebitRepository extends ReactiveMongoRepository<DebitDao,String> {
	Flux<DebitDao> findByClient(String clientId);
}
