package com.test.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import com.test.common.service.CommonService
import com.test.model.Customer

@RestController
@RequestMapping("customer")
class CustomerController {

	@Autowired
	CommonService commonService

	@GetMapping
	Customer[] list() {
		commonService.listCustomer()
	}

	@GetMapping("{id}")
	def get(@PathVariable int id) {
		def optional = commonService.getCustomer(id)
		optional.isPresent() ? optional.get() : ResponseEntity.notFound().build()
	}

	@PostMapping
	def save(@RequestBody Customer customer) {
		commonService.saveCustomer(customer)
	}

	@PostMapping("{id}")
	def save(@PathVariable int id, @RequestBody Customer customer) {
		commonService.saveCustomer(id, customer)
	}

	@DeleteMapping("{id}")
	def delete(@PathVariable int id) {
		commonService.deleteCustomer(id)
	}
}
