package com.cgb.luofenwu.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Autor:LFW
 * @Description: <p>深拷贝
 * 比较正统的做法，可以重写clone
 * </p>
 * @Date:create in 2020/9/2016:32
 */
public class SearchWordDeepDemo3 {
    private HashMap<String, SearchWord3> currentKeywords = new HashMap<String, SearchWord3>();
    private long lastUpdateTime = -1;

    private void init() {
        List<SearchWord3> list = getSearchWord3s(1);
        for (SearchWord3 searchWord : list) {
            currentKeywords.put(searchWord.getKeyword(), searchWord);
        }
    }

    public static void main(String[] args) {
        SearchWordDeepDemo3 searchWordDeepDemo2 = new SearchWordDeepDemo3();
        searchWordDeepDemo2.refresh();
    }

    public void refresh() {
        init();
        // Deep copy,使用序列化,注意 SearchWord3 implements Serializable
        HashMap<String, SearchWord3> newKeywords = new HashMap<String, SearchWord3>();
        for (HashMap.Entry<String, SearchWord3> e : currentKeywords.entrySet()) {
            SearchWord3 searchWord = e.getValue();
            SearchWord3 newSearchWord3 = null;
            try {
                newSearchWord3 = (SearchWord3) searchWord.clone();
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            }
            newKeywords.put(e.getKey(), newSearchWord3);
        }
        // 从数据库中取出更新时间>lastUpdateTime的数据，放入到newKeywords中
        List<SearchWord3> toBeUpdatedSearchWord3s = getSearchWord3s(lastUpdateTime);
        long maxNewUpdatedTime = lastUpdateTime;
        for (SearchWord3 searchWord : toBeUpdatedSearchWord3s) {
            if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
                maxNewUpdatedTime = searchWord.getLastUpdateTime();
            }
            if (newKeywords.containsKey(searchWord.getKeyword())) {
                SearchWord3 oldSearchWord3 = newKeywords.get(searchWord.getKeyword());
                oldSearchWord3.setCount(searchWord.getCount());
                oldSearchWord3.setLastUpdateTime(searchWord.getLastUpdateTime());
            } else {
                newKeywords.put(searchWord.getKeyword(), searchWord);
            }
        }

        lastUpdateTime = maxNewUpdatedTime;
        currentKeywords = newKeywords;
    }

    /**
     * 取到>lastUpdateTime的更新记录
     *
     * @param lastUpdateTime
     * @return
     */
    private List<SearchWord3> getSearchWord3s(long lastUpdateTime) {
        // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
        List<SearchWord3> list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            SearchWord3 searchWord = new SearchWord3();
            searchWord.setLastUpdateTime(System.currentTimeMillis() - 10 * i);
            searchWord.setCount(i);
            searchWord.setKeyword("i=" + i + "条数据");
            list.add(searchWord);
        }
        return list;
    }
}
