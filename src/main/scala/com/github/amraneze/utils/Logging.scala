package com.github.amraneze.utils

import org.slf4j
import org.slf4j.LoggerFactory

trait Logging {
	protected val logger: slf4j.Logger = Logger(this.getClass)
}

object Logger {
	def apply(clazz: Class[_]): slf4j.Logger = {
		LoggerFactory.getLogger(clazz)
	}
}