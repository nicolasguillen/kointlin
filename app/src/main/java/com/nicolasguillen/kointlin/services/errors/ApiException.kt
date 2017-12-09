package com.nicolasguillen.kointlin.services.errors

import com.nicolasguillen.kointlin.services.reponses.ErrorEnvelope

/**
 * An exception class wrapping an [ErrorEnvelope].
 */
class ApiException(val errorEnvelope: ErrorEnvelope) : ResponseException()