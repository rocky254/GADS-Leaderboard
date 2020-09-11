package rocky.android.gadsleaderboard;

import android.os.Parcel;
import android.os.Parcelable;

public class Skill implements Parcelable {
    public String name;
    public int score;
    public String country;
    public String badgeUrl;

    public Skill(String name, int score, String country, String badgeUrl) {
        this.name = name;
        this.score = score;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    protected Skill(Parcel in) {
        name = in.readString();
        score = in.readInt();
        country = in.readString();
        badgeUrl = in.readString();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeString(country);
        dest.writeString(badgeUrl);
    }
}
