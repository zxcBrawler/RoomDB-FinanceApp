package database_material

import androidx.room.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var idUser : Int,
    @ColumnInfo("login") var login : String,
    @ColumnInfo("password") var password : String,

    )










