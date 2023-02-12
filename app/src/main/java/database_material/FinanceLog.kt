package database_material

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance")
data class FinanceLog(
    @PrimaryKey(autoGenerate = true)
    var idFinance: Int,
    @ColumnInfo("sum") var sumIncome : Int,
    var categoryId : Int,
    var userOwnerId : Int
)
