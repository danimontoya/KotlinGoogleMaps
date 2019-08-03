package com.assignment.kotlingooglemaps.core.exception

/**
 * Created by danieh on 01/08/2019.
 */
sealed class Failure {

    abstract class BaseFailure : Failure()

    class NetworkConnection : BaseFailure()
    class ServerError(val throwable: Throwable?) : BaseFailure()
    class BodyNullError : BaseFailure()
}