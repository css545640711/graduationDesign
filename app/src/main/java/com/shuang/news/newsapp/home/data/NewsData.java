package com.shuang.news.newsapp.home.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午2:10
 */
public class NewsData implements Parcelable, Serializable {

    /**
     * votecount : 5981
     * docid : ECAGPMO100038FO9
     * lmodify : 2019-04-09 10:06:48
     * url_3w : http://ent.163.com/19/0409/09/ECAGPMO100038FO9.html
     * source : 网易娱乐
     * postid : ECAGPMO100038FO9
     * priority : 142
     * title : 毕滢小号疑曝光 称会与张丹峰结婚讽洪欣是老女人
     * mtime : 2019-04-09 10:06:48
     * url : http://3g.163.com/ent/19/0409/09/ECAGPMO100038FO9.html
     * replyCount : 6217
     * ltitle : 毕滢小号疑曝光 称会与张丹峰结婚讽洪欣是老女人
     * subtitle :
     * digest : 网易娱乐4月9日报道有媒体拍到男演员张丹峰和女助理毕滢二人偷搞暧昧的蛛丝马迹，二人深夜在同一房间独处3个多小时，直到凌晨之后，毕滢才从张丹峰的房间内出来，身上还
     * boardid : ent2_bbs
     * imgsrc : http://cms-bucket.ws.126.net/2019/04/09/8480a0c975da47b982d9099af1707652.png
     * ptime : 2019-04-09 09:46:43
     * daynum : 17995
     */

    public int votecount;
    public String docid;
//    public String lmodify;
    public String url_3w;
    public String source;
//    public String postid;
//    public int priority;
    public String title;
    public String mtime;
    public String url;
    public int replyCount;
    public String ltitle;
    public String subtitle;
//    public String digest;
//    public String boardid;
    public String imgsrc;
//    public String ptime;
//    public String daynum;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.votecount);
        dest.writeString(this.docid);
//        dest.writeString(this.lmodify);
        dest.writeString(this.url_3w);
        dest.writeString(this.source);
//        dest.writeString(this.postid);
//        dest.writeInt(this.priority);
        dest.writeString(this.title);
        dest.writeString(this.mtime);
        dest.writeString(this.url);
        dest.writeInt(this.replyCount);
        dest.writeString(this.ltitle);
        dest.writeString(this.subtitle);
//        dest.writeString(this.digest);
//        dest.writeString(this.boardid);
        dest.writeString(this.imgsrc);
//        dest.writeString(this.ptime);
//        dest.writeString(this.daynum);
    }

    public NewsData() {
    }

    protected NewsData(Parcel in) {
        this.votecount = in.readInt();
        this.docid = in.readString();
//        this.lmodify = in.readString();
        this.url_3w = in.readString();
        this.source = in.readString();
//        this.postid = in.readString();
//        this.priority = in.readInt();
        this.title = in.readString();
        this.mtime = in.readString();
        this.url = in.readString();
        this.replyCount = in.readInt();
        this.ltitle = in.readString();
        this.subtitle = in.readString();
//        this.digest = in.readString();
//        this.boardid = in.readString();
        this.imgsrc = in.readString();
//        this.ptime = in.readString();
//        this.daynum = in.readString();
    }

    public static final Creator<NewsData> CREATOR = new Creator<NewsData>() {
        @Override
        public NewsData createFromParcel(Parcel source) {
            return new NewsData(source);
        }

        @Override
        public NewsData[] newArray(int size) {
            return new NewsData[size];
        }
    };

    public String getTitle() {
        return TextUtils.isEmpty(ltitle)?title:ltitle;
    }

    public String getUrl() {
        return TextUtils.isEmpty(url_3w)?url:url_3w;
    }
}
