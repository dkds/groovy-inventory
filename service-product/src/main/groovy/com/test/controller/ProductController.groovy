package com.test.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import com.test.common.service.CommonService
import com.test.model.Product

@RestController
@RequestMapping("product")
class ProductController {

	@Autowired
	CommonService commonService

	@GetMapping
	Product[] list() {
		commonService.listProduct()
	}

	@GetMapping("{id}")
	def get(@PathVariable int id) {
		def optional = commonService.getProduct(id)
		optional.isPresent() ? optional.get() : ResponseEntity.notFound().build()
	}

	@PostMapping
	def save(@RequestBody Product product) {
		commonService.saveProduct(product)
	}

	@PostMapping("{id}")
	def save(@PathVariable int id, @RequestBody Product product) {
		commonService.saveProduct(id, product)
	}

	@DeleteMapping("{id}")
	def delete(@PathVariable int id) {
		commonService.deleteProduct(id)
	}
}
