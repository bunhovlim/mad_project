package kh.edu.rupp.ite.mad_project.global

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun get(): App {
            return instance ?: throw IllegalStateException("Application instance is not initialized yet!")
        }
    }
}
