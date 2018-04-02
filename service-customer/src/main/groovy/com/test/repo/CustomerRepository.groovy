package com.test.repo

import com.test.common.repo.BaseRepository
import com.test.model.Customer

interface CustomerRepository extends BaseRepository<Customer> {

	Customer findByName(String firstName)
}
