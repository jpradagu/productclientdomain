package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "corporateCreditCards")
public class CorporateCreditCard {
	@Id
	private String id;
	private String code;
	private String creditCardNumber;
	private BigDecimal limitAmount;
	private BigDecimal usedAmount;
	private Integer fees;
	private Integer feesPaid;
	private Integer cutoffDate;
	private Integer limitDate;
	private Date openingDate;
	private Date deliveryDate;
	private CommercialCustomer customer;
	private TypeCreditCard typeCreditCard;
	private Boolean state;
}
