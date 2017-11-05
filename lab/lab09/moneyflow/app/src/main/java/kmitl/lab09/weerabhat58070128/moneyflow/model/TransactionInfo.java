package kmitl.lab09.weerabhat58070128.moneyflow.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class TransactionInfo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;

    private String describe;

    private int amount;

    public TransactionInfo() {
    }

    protected TransactionInfo(Parcel in) {
        id = in.readInt();
        type = in.readString();
        describe = in.readString();
        amount = in.readInt();
    }

    public static final Creator<TransactionInfo> CREATOR = new Creator<TransactionInfo>() {
        @Override
        public TransactionInfo createFromParcel(Parcel in) {
            return new TransactionInfo(in);
        }

        @Override
        public TransactionInfo[] newArray(int size) {
            return new TransactionInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeString(describe);
        parcel.writeInt(amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
