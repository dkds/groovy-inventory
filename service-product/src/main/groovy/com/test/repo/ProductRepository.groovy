package com.test.repo

import com.test.common.repo.BaseRepository
import com.test.model.Product

interface ProductRepository extends BaseRepository<Product> {

	Product findByName(String name)
}
