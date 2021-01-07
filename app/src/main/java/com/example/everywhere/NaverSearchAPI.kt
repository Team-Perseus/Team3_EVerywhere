package com.example.everywhere

import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.util.HashMap

//Naver Developers 사이트 참고하여 작성
class NaverSearchAPI {
    private val clientId = "OMDAUElxYukmn7Cm598R" //애플리케이션 클라이언트 아이디값"
    private val clientSecret = "pOtvpBHgOW" //애플리케이션 클라이언트 시크릿값"

    fun main() {
        var text: String? = null

        text = try {
            URLEncoder.encode("홍대 전기차", "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }

        val apiURL = "https://openapi.naver.com/v1/search/local?query=$text" // json 결과
        //val apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text // xml 결과

        val requestHeaders: HashMap<String, String> = HashMap()
        requestHeaders["X-Naver-Client-Id"] = clientId
        requestHeaders["X-Naver-Client-Secret"] = clientSecret
        val responseBody = get(apiURL, requestHeaders)

        println(responseBody)
    }

    private operator fun get(apiUrl: String, requestHeaders: Map<String, String>): String {
        val con: HttpURLConnection = connect(apiUrl)
        return try {
            con.requestMethod = "GET"
            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }

            val responseCode: Int = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                readBody(con.inputStream)
            } else { // 에러 발생
                readBody(con.errorStream)
            }
        } catch (e: IOException) {
            throw RuntimeException("API 요청과 응답 실패", e)
        } finally {
            con.disconnect()
        }
    }

    private fun connect(apiUrl: String): HttpURLConnection {
        try {
            val url = URL(apiUrl)
            return url.openConnection() as HttpURLConnection
        } catch (e: MalformedURLException) {
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl", e)
        } catch (e: IOException) {
            throw RuntimeException("연결이 실패했습니다. : $apiUrl", e)
        }
    }

    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)

        try {
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()
                var line: String = lineReader.readLine()

                while (line != null) {
                    responseBody.append(line)
                    line = lineReader.readLine()
                }
                /*while (lineReader.readLine().also({ line = it }) != null) {
                        responseBody.append(line)
                    }*/
                return responseBody.toString()
            }
        } catch (e: IOException) {
            throw RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }
    }

}