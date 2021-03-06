package me.pitok.networking.di.modules

import android.content.Context
import dagger.Lazy
import dagger.Module
import dagger.Provides
import me.pitok.androidcore.qulifiers.ApplicationContext
import me.pitok.datasource.Readable
import me.pitok.datasource.Writable
import me.pitok.networking.ApiInterface
import me.pitok.networking.AuthorizationInterceptor
import me.pitok.networking.OkHttpAuthenticator
import me.pitok.networking.dataSource.TokenResponseToEntityMapper
import me.pitok.networking.dataSource.TokenResponseToTokenEntity
import me.pitok.networking.di.scopes.NetworkScope
import me.pitok.networking.tokenController.*
import me.pitok.sharedpreferences.StoreModel
import me.pitok.sharedpreferences.di.qulifiers.TokenSP
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    companion object {
        const val OKHTTP_MAX_CACHE_SIZE = (50 * 1024 * 1024).toLong()
        const val SERVER_BASE_URL = ""
    }

    @Provides
    @NetworkScope
    fun providePkHttpClientCache( @ApplicationContext
        context: Context
    ): Cache {
        return Cache(context.cacheDir,
            OKHTTP_MAX_CACHE_SIZE
        )
    }

    @Provides
    @NetworkScope
    fun provideTokenResponseToEntityMapper(): TokenResponseToEntityMapper{
        return TokenResponseToTokenEntity()
    }

    @Provides
    @NetworkScope
    fun provideTokenWriter(@TokenSP spTokenWriter: Writable<StoreModel<String>>): TokenWritable{
        return TokenWriter(spTokenWriter)
    }

    @Provides
    @NetworkScope
    fun provideTokenReader(@TokenSP spTokenReader: Readable.IO<String, String>): TokenReadable{
        return TokenReader(spTokenReader)
    }

    @Provides
    @NetworkScope
    fun provideTokenRefresher(apiInterface: ApiInterface,
                              @TokenSP spTokenReader: Readable.IO<String,String>,
                              mapper: TokenResponseToEntityMapper): TokenRefresher{
        return TokenRefresher(apiInterface,spTokenReader,mapper)
    }

    @Provides
    @NetworkScope
    fun provideOkHttpAuthenticator(
        tokenRefresher: TokenRefresher,
        tokenWriter: TokenWritable,
        tokenReader: TokenReadable
    ): OkHttpAuthenticator {
        return OkHttpAuthenticator(
            tokenRefresher,
            tokenWriter,
            tokenReader
        )
    }

    @Provides
    @NetworkScope
    fun provideAuthorizationInterceptor(tokenReader: TokenReadable): AuthorizationInterceptor {
        return AuthorizationInterceptor(tokenReader)
    }

    @Provides
    @NetworkScope
    fun provideOkHttpClient(
        cache: Cache?, okHttpAuthenticator: OkHttpAuthenticator?,
        authorizationInterceptor: AuthorizationInterceptor?
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requireNotNull(authorizationInterceptor))
            .authenticator(requireNotNull(okHttpAuthenticator))
            .cache(requireNotNull(cache))
            .build()
    }

    @Provides
    @NetworkScope
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @NetworkScope
    fun getRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .callFactory { request ->
                okHttpClient.get().newCall(request)
            }
            .baseUrl(SERVER_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @NetworkScope
    fun provideNetworkApiInterface(retrofit: Retrofit): ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}