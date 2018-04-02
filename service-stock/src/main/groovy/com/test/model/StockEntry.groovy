package com.test.model

import java.time.LocalDateTime

import org.springframework.data.mongodb.core.mapping.Document

import com.test.common.model.BaseModel

@Document
class StockEntry extends BaseModel {

	int productId
	int customerId
	int supplierId
	int quantity
	LocalDateTime createTime
}
