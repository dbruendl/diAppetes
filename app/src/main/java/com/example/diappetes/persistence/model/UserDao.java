package com.example.diappetes.persistence.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    long insert(User user);

    @Query("SELECT * FROM user WHERE email = :email")
    Maybe<User> findByEmail(String email);
}
