package tech.alvarez.contacts.data.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;
import android.widget.ImageView;

import tech.alvarez.contacts.data.db.converter.ImageConverter;

@Entity(tableName = "thing_table")

public class Thing {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String thingName;
    public String supplier;
    public String price;
    public String quantity;
    public Bitmap image;

    @Ignore
    public Thing() {
        this.thingName = "";
        this.supplier = "";
        this.price = "";
        this.quantity = "";

    }

    public Thing(String thingName, String supplier, String price, String quantity, Bitmap image) {
        this.thingName = thingName;
        this.supplier = supplier;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }
}
