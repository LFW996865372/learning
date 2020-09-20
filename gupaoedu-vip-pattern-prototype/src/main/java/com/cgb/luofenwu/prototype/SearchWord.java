package com.cgb.luofenwu.prototype;

import java.io.Serializable;

/**
 * @Autor:LFW
 * @Description:关键字对象
 * @Date:create in 2020/9/2016:32
 */
public class SearchWord implements Serializable {
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 出现次数
     */
    private long count;
    /**
     * 最后更新时间
     */
    private long lastUpdateTime;

    public SearchWord() {
    }

    public SearchWord(String keyword, long count, long lastUpdateTime) {
        this.keyword = keyword;
        this.count = count;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
