package com.hibay.goldking.net

class ApiException(var code: Int, override var message: String) : RuntimeException()