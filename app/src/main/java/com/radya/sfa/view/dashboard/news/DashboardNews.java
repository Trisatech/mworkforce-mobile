package com.radya.sfa.view.dashboard.news;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class DashboardNews extends BaseModel{

    @SerializedName("data")
    private List<NewsData> data;

    public List<NewsData> getData() {
        return data;
    }

    public void setData(List<NewsData> data) {
        this.data = data;
    }

    @NoArgsConstructor
    @Data
    public static class NewsData {
        @SerializedName("newsId")
        private String newsId;
        @SerializedName("title")
        private String title;
        @SerializedName("desc")
        private String body;

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

}
