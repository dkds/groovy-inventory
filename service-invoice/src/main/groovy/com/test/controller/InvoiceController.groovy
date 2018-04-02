package com.test.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.test.common.service.CommonService
import com.test.model.Invoice
import com.test.service.ProductService

@RestController
@RequestMapping("invoice")
class InvoiceController {

	@Autowired
	CommonService commonService
	@Autowired
	ProductService productService

	private def productRetriever = {invoice ->
		invoice.entries.each { entry ->
			entry.product = productService.getProduct(entry.productId)
		}
	}

	@HystrixCommand(fallbackMethod = 'onFailList')
	@Cacheable('invoice-list')
	@GetMapping
	Invoice[] list() {
		commonService.listInvoice().each productRetriever
	}

	@Cacheable
	@GetMapping("{id}")
	def get(@PathVariable int id) {
		def optional = commonService.getInvoice(id)
		optional.isPresent() ? optional.map(productRetriever).get() : ResponseEntity.notFound().build()
	}

	@PostMapping
	def save(@RequestBody Invoice invoice) {
		commonService.saveInvoice(invoice)
	}

	@PostMapping("{id}")
	def save(@PathVariable int id, @RequestBody Invoice invoice) {
		commonService.saveInvoice(id, invoice)
	}

	@DeleteMapping("{id}")
	def delete(@PathVariable int id) {
		commonService.deleteInvoice(id)
	}

	def onFailList() {
		['message': 'Please try again']
	}
}
