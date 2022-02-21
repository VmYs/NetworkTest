package com.example.networktest

import retrofit2.Call
import retrofit2.http.GET

interface AppService {

    @GET("get_data.json")
    //返回值必须声明成Retrofit中内置的Call类型，并通过泛型来指定服务器响应的数据应该转换成什么对象。
    //由于服务器响应的是一个包含App数据的JSON数组，因此这里我们将泛型声明成List<App>。
    fun getAppData(): Call<List<App>>
}