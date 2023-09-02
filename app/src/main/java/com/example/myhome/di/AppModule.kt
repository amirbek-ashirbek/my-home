package com.example.myhome.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.myhome.realm.model.CameraRealm
import com.example.myhome.realm.model.DoorRealm
import com.example.myhome.feature_home.data.remote.HomeApi
import com.example.myhome.feature_home.data.remote.HomeApiImpl
import com.example.myhome.feature_home.data.repository.CameraRepositoryImpl
import com.example.myhome.feature_home.domain.repository.CameraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideKtorClient(@ApplicationContext context: Context): HttpClient {

		val okHttpEngine = io.ktor.client.engine.okhttp.OkHttp.create {
			addInterceptor(ChuckerInterceptor(context = context))
		}

		val client = HttpClient(okHttpEngine) {
			install(plugin = ContentNegotiation) {
				json()
			}
			install(plugin = Logging) {
				logger = Logger.DEFAULT
				level = LogLevel.ALL
			}
		}

		return client

	}

	@Provides
	@Singleton
	fun provideHomeApi(httpClient: HttpClient): HomeApi {
		return HomeApiImpl(httpClient)
	}

	@Provides
	@Singleton
	fun provideRealm(): Realm {
		val configuration = RealmConfiguration.Builder(
			schema = setOf(CameraRealm::class, DoorRealm::class)
		)
			.compactOnLaunch()
			.build()
		return Realm.open(configuration = configuration)
	}

	@Provides
	@Singleton
	fun provideCameraRepository(homeApi: HomeApi, realm: Realm): CameraRepository {
		return CameraRepositoryImpl(homeApi = homeApi, realm = realm)
	}

}