package com.example.cartable.exceptions

import java.io.IOException

class BadRequestException(override var message: String) : IOException(message)
