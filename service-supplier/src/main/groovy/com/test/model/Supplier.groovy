package com.test.model

import org.springframework.data.mongodb.core.mapping.Document

import com.test.common.model.BaseModel

@Document
class Supplier extends BaseModel {

	String name
}
