package rocky.android.gadsleaderboard;

import android.os.Parcel;
import android.os.Parcelable;

public class Learner implements Parcelable {
    public String name;
    public int hours;
    public String country;
    public String badgeUrl;

    public Learner(String name, int hours, String country, String badgeUrl) {
        this.name = name;
        this.hours = hours;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    protected Learner(Parcel in) {
        name = in.readString();
        hours = in.readInt();
        country = in.readString();
        badgeUrl = in.readString();
    }

    public static final Creator<Learner> CREATOR = new Creator<Learner>() {
        @Override
        public Learner createFromParcel(Parcel in) {
            return new Learner(in);
        }

        @Override
        public Learner[] newArray(int size) {
            return new Learner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(hours);
        dest.writeString(country);
        dest.writeString(badgeUrl);
    }
}
