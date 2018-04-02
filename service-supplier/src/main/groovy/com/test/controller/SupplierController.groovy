package com.test.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import com.test.common.service.CommonService
import com.test.model.Supplier

@RestController
@RequestMapping("supplier")
class SupplierController {

	@Autowired
	CommonService commonService

	@GetMapping
	Supplier[] list() {
		commonService.listSupplier()
	}

	@GetMapping("{id}")
	def get(@PathVariable int id) {
		def optional = commonService.getSupplier(id)
		optional.isPresent() ? optional.get() : ResponseEntity.notFound().build()
	}

	@PostMapping
	def save(@RequestBody Supplier supplier) {
		commonService.saveSupplier(supplier)
	}

	@PostMapping("{id}")
	def save(@PathVariable int id, @RequestBody Supplier supplier) {
		commonService.saveSupplier(id, supplier)
	}

	@DeleteMapping("{id}")
	def delete(@PathVariable int id) {
		commonService.deleteSupplier(id)
	}
}
