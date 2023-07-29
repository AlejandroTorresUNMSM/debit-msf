package com.atorres.nttdata.debitmsf.model;

import lombok.Data;

@Data
public class RequestAddDebit {
	private String client;
	private String product;
	private String debit;
}
