package com.atorres.nttdata.debitmsf.utils;

import com.atorres.nttdata.debitmsf.model.DebitDto;
import com.atorres.nttdata.debitmsf.model.RequestDebit;
import com.atorres.nttdata.debitmsf.model.dao.DebitDao;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class RequestMapper {
	public DebitDto toDto(DebitDao debitDao){
		DebitDto debitDto = new DebitDto();
		debitDto.setId(debitDao.getId());
		debitDto.setClient(debitDao.getClient());
		debitDto.setMainProduct(debitDao.getMainProduct());
		debitDto.setProductList(debitDao.getProductList());
		return debitDto;
	}

	public DebitDao toDao(RequestDebit request,String productId){
		DebitDao debitDao = new DebitDao();
		debitDao.setId(generateId());
		debitDao.setClient(request.getClient());
		debitDao.setMainProduct(productId);
		debitDao.setProductList(Collections.singletonList(productId));
		return debitDao;
	}

	private String generateId(){
		return java.util.UUID.randomUUID().toString().replace("-","");
	}
}
