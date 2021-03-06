package io.github.zwieback.familyfinance.app.lifecycle.destroyer

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DatabaseDestroyer(private val context: Context) {

    fun deleteDatabases() = runBlocking(Dispatchers.IO) {
        context.databaseList().forEach { databaseName ->
            if (context.deleteDatabase(databaseName)) {
                Log.d(TAG, "The '$databaseName' database was deleted")
            } else {
                Log.d(TAG, "Can't delete the '$databaseName' database")
            }
        }
    }

    companion object {
        private const val TAG = "DatabaseDestroyer"
    }
}
