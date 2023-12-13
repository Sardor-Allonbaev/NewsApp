package sam.sultan.newsapp.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sam.sultan.newsapp.models.entities.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDataBase: RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object{

        @Volatile
        private var INSTANCE: NewsDataBase? = null

        fun getDatabase(context: Context): NewsDataBase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDataBase::class.java,
                    "newsDatabase").build()
                INSTANCE = instance
                return instance
            }
        }

    }

}