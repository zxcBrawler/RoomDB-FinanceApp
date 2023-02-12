package database_material


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var idCategory : Int,
    @ColumnInfo("nameCategory") var name : String,
    @ColumnInfo("typeCategory") var type : String,
)
