package com.masreferenceapp.storage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;
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

        return Status.status("OK", db.toString());
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

        return Status.status("OK", userDao.toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readFromRoomDb(){

        writeToRoomDb();

        AppDatabase db = Room.databaseBuilder(this.context,
                AppDatabase.class, "mas-refernce-app-room-db").build();

        UserDao userDao = db.userDao();
        User maximilian = userDao.findByName("Maximilian", "Smith");


        return Status.status("OK", maximilian.firstName + ", " + maximilian.lastName  + ", " + maximilian.password  );
    }
}