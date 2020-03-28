package commanderpepper.catdogtwo

import android.app.Application
import commanderpepper.catdogtwo.repo.DatabaseLocalSource

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseLocalSource.createInstance(applicationContext)
    }
}