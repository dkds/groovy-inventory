package com.test.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import com.test.common.service.CommonService
import com.test.model.StockEntry

@RestController
@RequestMapping("stock")
class StockController {

	@Autowired
	CommonService commonService

	@GetMapping
	StockEntry[] list() {
		commonService.listStock()
	}

	@GetMapping("{id}")
	def get(@PathVariable int id) {
		def optional = commonService.getStock(id)
		optional.isPresent() ? optional.get() : ResponseEntity.notFound().build()
	}

	@PostMapping
	def save(@RequestBody StockEntry stock) {
		commonService.saveStock(stock)
	}

	@PostMapping("{id}")
	def save(@PathVariable int id, @RequestBody StockEntry stock) {
		commonService.saveStock(id, stock)
	}

	@DeleteMapping("{id}")
	def delete(@PathVariable int id) {
		commonService.deleteStock(id)
	}
}
