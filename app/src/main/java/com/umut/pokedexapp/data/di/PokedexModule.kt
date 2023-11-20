package com.umut.pokedexapp.data.di

import android.util.Log
import com.umut.pokedexapp.BuildConfig
import com.umut.pokedexapp.data.remote.service.PokemonApi
import com.umut.pokedexapp.data.repository.PokemonRepositoryImpl
import com.umut.pokedexapp.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokedexModule {
    @Singleton
    @Provides
    fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor{
            Log.e("okhttp", it)
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
    @Singleton
    @Provides
    fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(client).addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL).build()
    }

    @Singleton
    @Provides
    fun createPokemonApi(retrofit: Retrofit): PokemonApi {
        return retrofit.create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun createPokemonRepository(pokemonApi: PokemonApi) =
        PokemonRepositoryImpl(pokemonApi) as PokemonRepository
}