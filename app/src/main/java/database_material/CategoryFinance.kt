package database_material

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryFinance(
    @Embedded val financeLog: FinanceLog,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "idCategory"
    )
    val category: Category
)
