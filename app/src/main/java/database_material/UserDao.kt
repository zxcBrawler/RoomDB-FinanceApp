package database_material

import androidx.room.*


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user : User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(category: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLog(log: List<FinanceLog>)
    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user : User)

    @Query("select * from user where login = :login and password = :password")
    fun getUserById(login : String, password : String) : Boolean

    @Query("select * from user where login = :login and password = :password")
    fun checkUser(login : String, password : String) : Boolean

    @Query("select nameCategory from category where typeCategory = :type")
    fun getAllCategories(type : String) : List<String>

    @Query("select idCategory from category where nameCategory = :name")
    fun findCategoryId(name : String) : Int
    @Transaction
    @Query("select idUser from user where login = :login")
    fun findUserId(login : String) : Int

    @Query("select * from finance join user on idUser = finance.userOwnerId join category on idCategory = finance.categoryId  where login = :owner")
    fun getFinanceById(owner : String) : List<CategoryFinance>

    @Query("select SUM(sum) from finance join category on idCategory = finance.categoryId join  user on idUser = finance.userOwnerId where login = :login and typeCategory = :type")
    fun getUserSpends(login : String, type : String) : Double

    @Query("select SUM(sum) from finance join  user on idUser = finance.userOwnerId join category on idCategory = finance.categoryId where login = :login and nameCategory = :typeC")
    fun getSpendings(login: String, typeC : String) : Double

}