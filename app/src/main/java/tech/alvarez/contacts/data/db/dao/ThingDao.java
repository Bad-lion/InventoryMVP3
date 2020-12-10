package tech.alvarez.contacts.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import tech.alvarez.contacts.data.db.entity.Thing;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ThingDao {


    @Query("SELECT * FROM thing_table ORDER BY thingName ASC")
    LiveData<List<Thing>> findAllPersons();

    @Query("SELECT * FROM thing_table")
    List<Thing> getAllChannels();

    @Query("SELECT * FROM thing_table WHERE id=:id")
    Thing findPersonById(String id);

    @Query("SELECT * FROM thing_table WHERE id=:id")
    Thing findPerson(long id);

    @Insert(onConflict = IGNORE)
    long insertPerson(Thing person);

    @Update
    int updatePerson(Thing person);

    @Update
    void updatePerson(List<Thing> people);

    @Delete
    void deletePerson(Thing person);

    @Query("DELETE FROM thing_table")
    void deleteAll();
}
