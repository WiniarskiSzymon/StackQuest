package com.example.swierk.stackquest

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.QueryResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is` as Is
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory


@RunWith(JUnit4::class)
class StackAPITest{



    private lateinit var stackAPI : StackAPI
    private lateinit var retrofit: Retrofit
    private lateinit var mockServer : MockWebServer
    private lateinit var moshi : Moshi
    private lateinit var adapter : JsonAdapter<QueryResult>
    private lateinit var getQueryResultMock : MockResponse

    private val queryJSON = """{ "items": [ { "tags": [ "dart", "flutter", "flutter-test" ], "owner": { "reputation": 13, "user_id": 9885733, "user_type": "registered", "profile_image": "https://i.stack.imgur.com/uQIsP.jpg?s=128&g=1", "display_name": "M. Krol", "link": "https://stackoverflow.com/users/9885733/m-krol" }, "is_answered": true, "view_count": 22, "accepted_answer_id": 53904579, "answer_count": 1, "score": 2, "last_activity_date": 1545587701, "creation_date": 1545571996, "question_id": 53904048, "link": "https://stackoverflow.com/questions/53904048/flutter-widget-test-of-a-custom-widget-fails", "title": "Flutter widget test of a custom widget fails" }, { "tags": [ "spring-security", "jwt" ], "owner": { "reputation": 59, "user_id": 10226771, "user_type": "registered", "profile_image": "https://lh4.googleusercontent.com/-BSV9wbYan0M/AAAAAAAAAAI/AAAAAAAAAAA/AAnnY7qqRQRvRx-UjQJyBlIiA-40FBzofQ/mo/photo.jpg?sz=128", "display_name": "flyordie", "link": "https://stackoverflow.com/users/10226771/flyordie" }, "is_answered": false, "view_count": 7, "answer_count": 0, "score": 0, "last_activity_date": 1545585578, "creation_date": 1545585578, "question_id": 53905632, "link": "https://stackoverflow.com/questions/53905632/why-i-have-code-302-found-jwt-token-spring-security-when-i-test-with-arc-rest", "title": "Why I have code 302 found jwt token spring security when i test with arc rest" }, { "tags": [ "java", "spring", "spring-boot", "junit5", "spring-restdocs" ], "owner": { "reputation": 53, "user_id": 1103606, "user_type": "registered", "accept_rate": 51, "profile_image": "https://www.gravatar.com/avatar/0d93cc650b93939ea91066fcb5aefae0?s=128&d=identicon&r=PG", "display_name": "Peter Penzov", "link": "https://stackoverflow.com/users/1103606/peter-penzov" }, "is_answered": false, "view_count": 23, "answer_count": 1, "score": 0, "last_activity_date": 1545584533, "creation_date": 1545579212, "question_id": 53904887, "link": "https://stackoverflow.com/questions/53904887/java-lang-assertionerror-status-expected200-but-was404-in-junit-test", "title": "java.lang.AssertionError: Status expected:&lt;200&gt; but was:&lt;404&gt; in Junit test" }, { "tags": [ "machine-learning", "xgboost", "gbm", "lightgbm", "test-results" ], "owner": { "reputation": 27, "user_id": 5226108, "user_type": "registered", "profile_image": "https://www.gravatar.com/avatar/1659632ead358a405e110b5a82fb62b0?s=128&d=identicon&r=PG&f=1", "display_name": "DaleSteyn", "link": "https://stackoverflow.com/users/5226108/dalesteyn" }, "is_answered": false, "view_count": 13, "answer_count": 0, "score": 0, "last_activity_date": 1545582838, "creation_date": 1545571697, "last_edit_date": 1545582838, "question_id": 53904010, "link": "https://stackoverflow.com/questions/53904010/xgboost-lightgbm-test-result-puzzle", "title": "XGBOOST/ LightGBM Test Result Puzzle" }, { "tags": [ "python", "pandas", "scikit-learn", "knn" ], "owner": { "reputation": 96, "user_id": 9906395, "user_type": "registered", "profile_image": "https://lh6.googleusercontent.com/-JXZ2ThqvEBQ/AAAAAAAAAAI/AAAAAAAAAAA/AB6qoq2U7S3nOW7f5gIMUA8Ims8XOcWf8g/mo/photo.jpg?sz=128", "display_name": "Filippo Sebastio", "link": "https://stackoverflow.com/users/9906395/filippo-sebastio" }, "is_answered": false, "view_count": 12, "answer_count": 0, "score": 1, "last_activity_date": 1545582085, "creation_date": 1545582085, "question_id": 53905190, "link": "https://stackoverflow.com/questions/53905190/how-to-standardize-a-training-and-a-test-dataset-through-make-pipeline", "title": "How to standardize a training and a test dataset through make_pipeline()" } ], "has_more": true, "quota_max": 10000, "quota_remaining": 9958 }"""

    @Before
    fun setUp() {

        mockServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url("/").toString())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        stackAPI=StackAPI(retrofit)
        moshi = Moshi.Builder().build()
        adapter = moshi.adapter(QueryResult::class.java)
        //mockServer.start()
        getQueryResultMock = MockResponse()
            .setResponseCode(200)
            .setBody(queryJSON)

    }

    @Test

    fun getQueryResultTest(){
        mockServer.enqueue(getQueryResultMock)
        var response : QueryResult? =null
        runBlocking {
         response =stackAPI.getQueryResults("test").await()
        }
        val queryResult = adapter.fromJson(queryJSON)
        assertThat(response,IsEqual(queryResult))
    }

    @After
    fun tearDown(){
        mockServer.shutdown()
    }



}