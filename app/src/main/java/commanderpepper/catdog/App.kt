package commanderpepper.catdog

import android.app.Application
import commanderpepper.catdog.repo.DatabaseLocalSource

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseLocalSource.createInstance(applicationContext)
    }
}