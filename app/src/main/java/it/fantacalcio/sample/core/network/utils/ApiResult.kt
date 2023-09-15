package it.fantacalcio.sample.core.network.utils

sealed class ApiResult<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {

    class Loading<T>(data: T? = null) : ApiResult<T>(data)
    class Success<T>(data: T?) : ApiResult<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : ApiResult<T>(data, throwable)

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading[data=$data]"
            is Success -> "Success[data=$data]"
            is Error -> "Error[message=${throwable?.localizedMessage} - data=$data]"
        }
    }

}