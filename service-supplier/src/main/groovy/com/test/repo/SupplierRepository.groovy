package com.test.repo

import com.test.common.repo.BaseRepository
import com.test.model.Supplier

interface SupplierRepository extends BaseRepository<Supplier> {

	Supplier findByName(String firstName)
}
