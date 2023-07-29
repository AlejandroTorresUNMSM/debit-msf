package com.atorres.nttdata.debitmsf.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("debits")
public class DebitDao {
	@Id
	private String id;
	private String client;
	private String mainProduct;
	private List<String> productList;
}
