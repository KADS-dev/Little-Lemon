package org.hoshiro.littlelemon.data

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import org.hoshiro.littlelemon.data.ofline.MenuDatabase
import org.hoshiro.littlelemon.data.ofline.MenuItemDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()


    @Singleton
    @Provides
    fun providesMenuDatabase(@ApplicationContext context: Context): MenuDatabase{
        return Room.databaseBuilder(
            context,
            MenuDatabase::class.java,
            "menu_database.db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }


    @Provides
    fun providesMenuItemDao(database: MenuDatabase): MenuItemDao{
        return database.menuItemDao()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("text", "plain"))
            }
        }
    }

}