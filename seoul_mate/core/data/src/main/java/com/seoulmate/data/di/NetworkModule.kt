package com.seoulmate.data.di

import android.annotation.SuppressLint
import com.seoulmate.data.BuildConfig
import com.seoulmate.data.UserInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttp.profiler.OkHttpProfilerInterceptor
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@SuppressLint("StaticFieldLeak")
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val PRINT_LOG = BuildConfig.DEV // 로그 출력여부
    private const val BASE_URL = BuildConfig.BASE_URL // 기본 URL
    private const val NAVER_MAP_BASE_URL = BuildConfig.NAVER_MAP_BASE_URL // 네이버 맵 기본 URL

    private const val CONNECT_TIMEOUT = 3000L // 커넥션 타임
    private const val WRITE_TIMEOUT = 3000L // 쓰기 타임
    private const val READ_TIMEOUT = 3000L // 읽기 타임

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseNetwork

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseNetworkExceptToken

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverMapNetwork

    @Provides
    @Singleton
    @BaseNetworkExceptToken
    fun provideBaseExceptTokenOkHttpClient(): OkHttpClient =
        if (PRINT_LOG) {
            OkHttpClient.Builder()
//                .cookieJar(JavaNetCookieJar(CookieManager()))       // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 연결 타임아웃 시간 설정
                .cache(null)                                 // 캐시사용 안함
                .addInterceptor { chain ->
                    if (UserInfo.isUserLogin()) {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .build()
                        )
                    } else {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .build()
                        )
                    }
                }
//                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()
        } else {
            OkHttpClient.Builder()
                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS)) // https 관련 보안 옵션
//                .cookieJar(JavaNetCookieJar(CookieManager()))       // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 연결 타임아웃 시간 설정
                .cache(null)                                 // 캐시사용 안함
                .addInterceptor { chain ->
                    if (UserInfo.isUserLogin()) {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .build()
                        )
                    } else {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .build()
                        )
                    }
                }
//                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()
        }

    @Provides
    @Singleton
    @BaseNetwork
    fun provideBaseOkHttpClient(): OkHttpClient =
        if (PRINT_LOG) {
            OkHttpClient.Builder()
//                .cookieJar(JavaNetCookieJar(CookieManager()))       // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 연결 타임아웃 시간 설정
                .cache(null)                                 // 캐시사용 안함
                .addInterceptor { chain ->
                    if (UserInfo.isUserLogin()) {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .header("Authorization","Bearer ${UserInfo.accessToken}")
                                .build()
                        )
                    } else {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .build()
                        )
                    }
                }
//                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()
        } else {
            OkHttpClient.Builder()
                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS)) // https 관련 보안 옵션
//                .cookieJar(JavaNetCookieJar(CookieManager()))       // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 연결 타임아웃 시간 설정
                .cache(null)                                 // 캐시사용 안함
                .addInterceptor { chain ->
                    if (UserInfo.isUserLogin()) {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .header("Authorization","Bearer ${UserInfo.accessToken}")
                                .build()
                        )
                    } else {
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .header("Accept", "application/json")
                                .build()
                        )
                    }
                }
//                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()
        }

    @Provides
    @Singleton
    @NaverMapNetwork
    fun provideNaverMapOkHttpClient(): OkHttpClient =
        if (PRINT_LOG) {
            OkHttpClient.Builder()
//                .cookieJar(JavaNetCookieJar(CookieManager()))       // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 연결 타임아웃 시간 설정
                .cache(null)                                 // 캐시사용 안함
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .header("x-ncp-apigw-api-key-id", "")
                            .header("x-ncp-apigw-api-key", "")
                            .header("Accept", "application/json")
                            .build()
                    )
                }
//                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()
        } else {
            OkHttpClient.Builder()
                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS)) // https 관련 보안 옵션
//                .cookieJar(JavaNetCookieJar(CookieManager()))       // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 연결 타임아웃 시간 설정
                .cache(null)                                 // 캐시사용 안함
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .header("x-ncp-apigw-api-key-id", "")
                            .header("x-ncp-apigw-api-key", "")
                            .header("Accept", "application/json")
                            .build()
                    )
                }
//                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()
        }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory() : MoshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    )

    @Provides
    @Singleton
    @NaverMapNetwork
    fun providerNaverMapRetrofit(
        @NaverMapNetwork okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(NAVER_MAP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)                 // MoshiConverter 적용
            .build()

    @Provides
    @Singleton
    @BaseNetwork
    fun providerBaseRetrofit(
        @BaseNetwork okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)                 // MoshiConverter 적용
            .build()

    @Provides
    @Singleton
    @BaseNetworkExceptToken
    fun providerBaseExceptTokenRetrofit(
        @BaseNetworkExceptToken okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)                 // MoshiConverter 적용
            .build()
}