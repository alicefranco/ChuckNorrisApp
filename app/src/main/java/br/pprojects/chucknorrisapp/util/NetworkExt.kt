package br.pprojects.chucknorrisapp.util

import br.pprojects.chucknorrisapp.data.model.ResultAPI
import br.pprojects.chucknorrisapp.data.model.ServerError
import br.pprojects.chucknorrisapp.data.network.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

fun <T : Any> Response<T>.result(): ResultAPI<T> {
    if (this.isSuccessful) {
        if (this.code() == 204) {
            return ResultAPI.SuccessNoBody("Success")
        }
        this.body()?.let {
            return ResultAPI.Success(it)
        }
        return ResultAPI.InternalError(IOException("Error"))
    } else if (this.code() == 404) {
        return ResultAPI.NotFound()
    }
    return try {
        val converter: Converter<ResponseBody, ServerError> = RetrofitManager().build().responseBodyConverter(ServerError::class.java, arrayOfNulls<Annotation>(0))
        errorBody()?.let {
            converter.convert(it)?.let {
                if (it.errorDescription == null) {
                    if (this.code() == 700) {
                        ResultAPI.InternalError(IOException("Canceled"))
                    } else {
                        ResultAPI.InternalError(IOException("Error"))
                    }
                } else {
                    ResultAPI.Error(it)
                }
            }
        } ?: run {
            ResultAPI.InternalError(IOException("Error"))
        }
    } catch (exception: Exception) {
        ResultAPI.InternalError(IOException("Error"))
    }
}

suspend fun <T : Any> safeCall(call: suspend () -> Call<T>): Response<T> = withContext(Dispatchers.Main) {
    withContext(Dispatchers.IO) {
        try {
            call.invoke().execute()
        } catch (exception: Exception) {
            if (exception.message == "Canceled") {
                exception.printStackTrace()
                Response.error<T>(700, ResponseBody.create("application/json".toMediaTypeOrNull(), "{}"))
            } else {
                exception.printStackTrace()
                Response.error<T>(500, ResponseBody.create("application/json".toMediaTypeOrNull(), "{}"))
            }
        }
    }
}