package com.example.myhome.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.myhome.feature_home.data.local.CameraDatabaseManager
import com.example.myhome.feature_home.data.local.DoorDatabaseManager
import com.example.myhome.realm.model.Camera
import com.example.myhome.realm.model.Door
import com.example.myhome.feature_home.data.remote.HomeApi
import com.example.myhome.feature_home.data.remote.HomeApiImpl
import com.example.myhome.feature_home.data.repository.CameraRepositoryImpl
import com.example.myhome.feature_home.data.repository.DoorRepositoryImpl
import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.feature_home.domain.repository.DoorRepository
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
			schema = setOf(Camera::class, Door::class)
		)
			.compactOnLaunch()
			.build()
		return Realm.open(configuration = configuration)
	}

	@Provides
	@Singleton
	fun provideCameraDatabaseManager(realm: Realm): CameraDatabaseManager {
		return CameraDatabaseManager(realm = realm)
	}

	@Provides
	@Singleton
	fun provideCameraRepository(homeApi: HomeApi, cameraDatabaseManager: CameraDatabaseManager): CameraRepository {
		return CameraRepositoryImpl(homeApi = homeApi, cameraDatabaseManager = cameraDatabaseManager)
	}

	@Provides
	@Singleton
	fun provideDoorDatabaseManager(realm: Realm): DoorDatabaseManager {
		return DoorDatabaseManager(realm = realm)
	}

	@Provides
	@Singleton
	fun provideDoorRepository(homeApi: HomeApi, doorDatabaseManager: DoorDatabaseManager) : DoorRepository {
		return DoorRepositoryImpl(homeApi = homeApi, doorDatabaseManager = doorDatabaseManager)
	}

}