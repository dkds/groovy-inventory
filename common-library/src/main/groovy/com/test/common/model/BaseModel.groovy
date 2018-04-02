package com.test.common.model

import org.springframework.data.annotation.Id

import com.fasterxml.jackson.annotation.JsonIgnore

class BaseModel {

	@Id
	@JsonIgnore
	String _id
	int id
}
