package com.example.networktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Xml
import com.example.networktest.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sendRequestBtn.setOnClickListener {
            //sendRequestWithHttpURLConnection()
            sendRequestWithOkHttp()
        }

    }

//    //使用HttpURLConnection
//    private fun sendRequestWithHttpURLConnection() {
//        thread {
//            var connection : HttpURLConnection? = null
//            try {
//                val response = StringBuilder()
//                val url = URL("https://www.bing.com")
//                connection = url.openConnection() as HttpURLConnection
//                connection.connectTimeout = 8000
//                connection.readTimeout = 8000
//                val input = connection.inputStream
//                val reader = BufferedReader(InputStreamReader(input))
//                reader.use {
//                    reader.forEachLine {
//                        response.append(it)
//                    }
//                }
//                showResponse(response.toString())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            } finally {
//                connection?.disconnect()
//            }
//        }
//    }

    //使用OkHttp
    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                        //.url("https://www.bing.com")
                        //.url("http://192.168.137.1:8080/get_data.xml")
                        .url("http://192.168.137.1:8080/get_data.json")
                        .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    //parseXMLWithPull(responseData)
                    //parseXMLWithSAX(responseData)
                    //parseJSONWithJSONObject(responseData)
                    parseJSONWithGSON(responseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    private fun showResponse(response: String) {
//        runOnUiThread {
//            binding.responseText.text = response
//        }
//    }

//    //XML：Pull解析方式
//    private fun parseXMLWithPull(xmlData: String) {
//        try {
//            val factory = XmlPullParserFactory.newInstance()
//            val xmlPullParser = factory.newPullParser()
//            xmlPullParser.setInput(StringReader(xmlData))
//            var eventType = xmlPullParser.eventType
//            var id = ""
//            var name = ""
//            var version = ""
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                val nodeName = xmlPullParser.name
//                when (eventType) {
//                    XmlPullParser.START_TAG -> {
//                        when (nodeName) {
//                            "id" -> id = xmlPullParser.nextText()
//                            "name" -> name = xmlPullParser.nextText()
//                            "version" -> version = xmlPullParser.nextText()
//                        }
//                    }
//                    XmlPullParser.END_TAG -> {
//                        if ("app" == nodeName) {
//                            Log.d(tag, "id is $id")
//                            Log.d(tag, "name is $name")
//                            Log.d(tag, "version is $version")
//                        }
//                    }
//                }
//                eventType = xmlPullParser.next()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

//    //XML：SAX解析方式
//    private fun parseXMLWithSAX(xmlData: String) {
//        try {
//            val factory = SAXParserFactory.newInstance()
//            val xmlReader = factory.newSAXParser().xmlReader
//            val handler = ContentHandler()
//            //将ContentHandler的实例设置到XMLReader中
//            xmlReader.contentHandler = handler
//            //开始执行解析
//            xmlReader.parse(InputSource(StringReader(xmlData)))
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

//    //JSON：JSONObject解析方式
//    private fun parseJSONWithJSONObject(jsonData: String) {
//        try {
//            val jsonArray = JSONArray(jsonData)
//            for (i in 0 until jsonArray.length()) {
//                val jsonObject = jsonArray.getJSONObject(i)
//                val id = jsonObject.getString("id")
//                val name = jsonObject.getString("name")
//                val version = jsonObject.getString("version")
//                Log.d(tag, "id is $id")
//                Log.d(tag, "name is $name")
//                Log.d(tag, "version is $version")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    //JSON：GSON解析方式
    private fun parseJSONWithGSON(jsonData: String) {
        val gson = Gson()
        val typeOf = object : TypeToken<List<App>>() {}.type
        val appList = gson.fromJson<List<App>>(jsonData, typeOf)
        for (app in appList) {
            Log.d(tag, "id is ${app.id}")
            Log.d(tag, "name is ${app.name}")
            Log.d(tag, "version is ${app.version}")
        }
    }


}