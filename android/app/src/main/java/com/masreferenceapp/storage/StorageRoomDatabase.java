package com.masreferenceapp.storage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;
import com.masreferenceapp.storage.room.AppDatabase;
import com.masreferenceapp.storage.room.User;
import com.masreferenceapp.storage.room.UserDao;


public class StorageRoomDatabase extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageRoomDatabase(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageRoomDatabase";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String init(){

        AppDatabase db = Room.databaseBuilder(this.context,
                AppDatabase.class, "mas-refernce-app-room-db").build();

        return new ReturnStatus("OK", "Database created: " + db.toString()).toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeToRoomDb(){

        AppDatabase db = Room.databaseBuilder(this.context,
                AppDatabase.class, "mas-refernce-app-room-db").build();

        UserDao userDao = db.userDao();

        // we don't want a primary ID conflict
//        userDao.deleteAll();
        db.clearAllTables();

        User user = new User();
        user.uid = 1;
        user.firstName = "Maximilian";
        user.lastName = "Smith";
        user.password = "qw3rty";

        userDao.insert(user);

        return new ReturnStatus("OK", "Added data to database : " + userDao.toString()).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readFromRoomDb(){

        writeToRoomDb();

        AppDatabase db = Room.databaseBuilder(this.context,
                AppDatabase.class, "mas-refernce-app-room-db").build();

        UserDao userDao = db.userDao();
        User maximilian = userDao.findByName("Maximilian", "Smith");

        return new ReturnStatus("OK", "Read from database: " + maximilian.firstName + ", " + maximilian.lastName  + ", " + maximilian.password).toJsonString();

    }
}