package zhiwenyan.cmccaifu.com.androidadvanced.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description:
 * Data：4/3/2018-11:18 AM
 *
 * @author: yanzhiwen
 */
public class User implements Parcelable {
    private int id;
    private String userName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.userName);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.userName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
