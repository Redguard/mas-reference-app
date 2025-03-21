package org.insomnihack.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

/**
 * GeminiAI said "This complete, well-commented, and robust example provides a solid foundation
 * for using SQLite databases in your Android app. It follows best practices for database
 * management, error handling, and thread safety."
 *
 * I guess it must be true...
 */

class DataManager private constructor (context: Context) :
    SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object for Singleton and constants
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "GameDatabase.db"
        private const val TABLE_GAME_STATS = "game_stats"
        private const val KEY_ID = "id"
        private const val KEY_TIMESTAMP = "timestamp"
        private const val KEY_SCORE = "score"
        private const val KEY_STREAK = "streak"
        private const val KEY_TOTAL_TIME = "total_time"
        private const val KEY_MOVES = "moves"
        private const val KEY_OTHER_DATA = "other_data"

        // Singleton instance
        @Volatile
        private var instance: DataManager? = null

        fun getInstance (context: Context): DataManager {
            return instance ?: synchronized (this) {
                instance ?: DataManager (context.applicationContext).also { instance = it }
            }
        }
    }

    // Called when the database is created for the first time
    override fun onCreate (db: SQLiteDatabase) {
        val CREATE_GAME_STATS_TABLE = ("CREATE TABLE " + TABLE_GAME_STATS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,"
                + KEY_SCORE + " INTEGER,"
                + KEY_STREAK + " INTEGER,"
                + KEY_TOTAL_TIME + " INTEGER,"
                + KEY_MOVES + " INTEGER, "
                + KEY_OTHER_DATA + " TEXT" + ")")
        db.execSQL (CREATE_GAME_STATS_TABLE)
    }

    // Called when the database needs to be upgraded
    override fun onUpgrade (db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL ("DROP TABLE IF EXISTS $TABLE_GAME_STATS")
        onCreate (db)
    }

    fun importGame (query: String) {
        val db = this.writableDatabase
        db.execSQL (query)

        db.close()
    }

    // --- CRUD Operations ---

    fun addGameStats (score: Int, streak: Int, totalTime: Int, moves: Int, otherData: String): Long {
        val db = this.writableDatabase
        val values = ContentValues ().apply {
            put (KEY_SCORE, score)
            put (KEY_STREAK, streak)
            put (KEY_TOTAL_TIME, totalTime)
            put (KEY_MOVES, moves)
            put (KEY_OTHER_DATA, otherData)
        }

        val id = db.insert (TABLE_GAME_STATS, null, values)
        db.close ()

        return id;
    }

    fun getAllGameStats (): List<GameStats> {
        val gameStatsList = mutableListOf<GameStats> ()
        val selectQuery = "SELECT  * FROM $TABLE_GAME_STATS"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val timestamp = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TIMESTAMP))
                    val score = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCORE))
                    val streak = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_STREAK))
                    val totalTime = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TOTAL_TIME))
                    val moves = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MOVES))
                    val otherData = cursor.getString(cursor.getColumnIndexOrThrow(KEY_OTHER_DATA))

                    val gameStats = GameStats(timestamp, score, streak, totalTime, moves, otherData)
                    gameStatsList.add(gameStats)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e ("CTF", e.localizedMessage, e)
        } finally {
            cursor?.close()
            db.close()
        }
        return gameStatsList
    }

    fun getGameStats(searchId: Long): GameStats? {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        var gameStats: GameStats? = null

        try {
            cursor = db.query (
                TABLE_GAME_STATS,
                arrayOf (KEY_TIMESTAMP, KEY_SCORE, KEY_STREAK, KEY_TOTAL_TIME, KEY_MOVES, KEY_OTHER_DATA),
                "$KEY_ID = ?",
                arrayOf (searchId.toString ()),
                null,
                null,
                null,
                null
            )

            if (cursor.moveToFirst ()) {

                val timestamp = cursor.getString (cursor.getColumnIndexOrThrow(KEY_TIMESTAMP))
                val score = cursor.getInt (cursor.getColumnIndexOrThrow(KEY_SCORE))
                val streak = cursor.getInt (cursor.getColumnIndexOrThrow(KEY_STREAK))
                val totalTime = cursor.getInt (cursor.getColumnIndexOrThrow(KEY_TOTAL_TIME))
                val moves = cursor.getInt (cursor.getColumnIndexOrThrow(KEY_MOVES))
                val otherData = cursor.getString (cursor.getColumnIndexOrThrow(KEY_OTHER_DATA))

                gameStats = GameStats (timestamp, score, streak, totalTime, moves, otherData)
            }
        }
        catch (e:Exception) {
            Log.i ("CTF", e.localizedMessage, e)
        }
        finally {
            cursor?.close ()
            db.close ()
        }
        return gameStats
    }

    // ---- Data class to represent a row in the table
    data class GameStats (
        val timestamp: String,
        val score: Int,
        val streak: Int,
        val totalTime: Int,
        val moves: Int,
        val otherData: String
    )
}