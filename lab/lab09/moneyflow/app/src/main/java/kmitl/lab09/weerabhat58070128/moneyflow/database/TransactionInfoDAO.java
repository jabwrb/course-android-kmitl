package kmitl.lab09.weerabhat58070128.moneyflow.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kmitl.lab09.weerabhat58070128.moneyflow.model.Balance;
import kmitl.lab09.weerabhat58070128.moneyflow.model.TransactionInfo;

@Dao
public interface TransactionInfoDAO {

    @Query("SELECT * FROM TransactionInfo")
    List<TransactionInfo> getAll();

    @Query("SELECT sum_income, sum_expense FROM " +
            "(SELECT SUM(amount) AS sum_income FROM TransactionInfo WHERE type = 'income')" +
            "JOIN " +
            "(SELECT SUM(amount) AS sum_expense FROM TransactionInfo WHERE type = 'expense')")
    Balance getBalance();

    @Insert
    void insert(TransactionInfo transactionInfo);

    @Update
    void update(TransactionInfo transactionInfo);

    @Delete
    void delete(TransactionInfo transactionInfo);
}
