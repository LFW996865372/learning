package com.cgb.luofenwu.prototype;

import java.io.*;

/**
 * @Autor:LFW
 * @Description:关键字对象
 * @Date:create in 2020/9/2016:32
 */
public class SearchWord3 implements Serializable, Cloneable {
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

    public SearchWord3() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return deepCopy();
    }

    public Object deepCopy() {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SearchWord3(String keyword, long count, long lastUpdateTime) {
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
