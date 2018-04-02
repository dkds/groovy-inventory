package com.test.common.service

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

@Service
class CommonService {

	private static final def TOKENIZER_REGEX = "(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])"
	private static final def REPOSITORY_MAPPING = [:]

	@Autowired
	ApplicationContext applicationContext

	@PostConstruct
	void init() {
		applicationContext.getBeanNamesForType(MongoRepository.class)
				.collect { name -> applicationContext.getBean(name) }
				.each { repo ->
					repo.metaClass.findById = { int id -> repo.findByCustomId(id) }
				}
	}

	def methodMissing(String name, args) {
		def methodNameParts = name.split(TOKENIZER_REGEX)
		def verb = methodNameParts[0]
		def entityName = methodNameParts[1].toLowerCase()

		println "$verb: $entityName"

		def repository = REPOSITORY_MAPPING[entityName]
		if (!repository) {
			repository = applicationContext.getBean(entityName + "Repository")
			REPOSITORY_MAPPING[entityName] = repository
		}

		switch (verb) {
			case "list":
				repository.findAll()
				break
			case "get":
				repository.findById(args[0] as int)
				break
			case "find":
				break
			case "save":
				def arg0 = args[0]
				if (arg0 in Integer) {
					def arg1 = args[1]
					def optional = repository.findById(arg0 as int)
					if (optional.isPresent()) {
						def entity = optional.get()
						copyProperties(arg1, entity)
						repository.save(entity)
					} else null
				} else {
					def id = repository.count()++
					def optional = repository.findById(id)
					if (optional.isPresent()) {
						// don't
					} else {
						def entity = arg0
						entity.id = id
						repository.save(entity)
					}
				}
				break
			case "delete":
				def optional = repository.findById(args[0] as int)
				if (optional.isPresent()) {
					def entity = optional.get()
					repository.delete(entity)
				} else null
				break
		}
	}

	private static def getProperties(Expando obj) {
		obj.getProperties().keySet()
	}

	private static def getProperties(Object obj) {
		obj.getMetaClass()*.properties*.name
	}

	private static def copyProperties(source, target) {
		def (sProps, tProps) = [source, target].collect { getProperties(it) }
		def commonProps = sProps[0].intersect(tProps[0]) - ['class', 'metaClass', 'id', '_id']
		commonProps.each {
			if (target[it]) {
				target[it] = source[it]
			}
		}
	}
}
