package kmitl.lab09.weerabhat58070128.moneyflow.model;

import android.arch.persistence.room.ColumnInfo;

public class Balance {

    @ColumnInfo(name = "sum_income")
    private int sumIncome;

    @ColumnInfo(name = "sum_expense")
    private int sumExpense;

    public int getSumIncome() {
        return sumIncome;
    }

    public void setSumIncome(int sumIncome) {
        this.sumIncome = sumIncome;
    }

    public int getSumExpense() {
        return sumExpense;
    }

    public void setSumExpense(int sumExpense) {
        this.sumExpense = sumExpense;
    }

    public int getBalance() {
        return sumIncome - sumExpense;
    }
}
