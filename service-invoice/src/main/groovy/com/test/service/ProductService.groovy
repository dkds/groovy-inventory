package com.test.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient('service-product')
interface ProductService {

	@RequestMapping('product/{id}')
	def getProduct(int id)
}
