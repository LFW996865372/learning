package com.cgb.luofenwu.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Autor:LFW
 * @Description:浅拷贝
 * @Date:create in 2020/9/2016:32
 */
public class SearchWordShallowDemo {
    private HashMap<String, SearchWord> currentKeywords = new HashMap<String, SearchWord>();
    private long lastUpdateTime = -1;

    private void init() {
        List<SearchWord> list = getSearchWords(1);
        for (SearchWord searchWord : list) {
            currentKeywords.put(searchWord.getKeyword(), searchWord);
        }
    }

    /**
     * 浅拷贝刷新
     */
    public void refresh() {
        init();
        // 原型模式就这么简单，拷贝已有对象的数据，更新少量差值
        HashMap<String, SearchWord> newKeywords = (HashMap<String, SearchWord>) currentKeywords.clone();
        // 从数据库中取出更新时间>lastUpdateTime的数据，放入到newKeywords中
        List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
        long maxNewUpdatedTime = lastUpdateTime;
        for (SearchWord searchWord : toBeUpdatedSearchWords) {
            if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
                maxNewUpdatedTime = searchWord.getLastUpdateTime();
            }
            if (newKeywords.containsKey(searchWord.getKeyword())) {
                SearchWord oldSearchWord = newKeywords.get(searchWord.getKeyword());
                oldSearchWord.setCount(searchWord.getCount());
                oldSearchWord.setLastUpdateTime(searchWord.getLastUpdateTime());
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
    private List<SearchWord> getSearchWords(long lastUpdateTime) {
        // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
        List<SearchWord> list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            SearchWord searchWord = new SearchWord();
            searchWord.setLastUpdateTime(System.currentTimeMillis() - 10 * i);
            searchWord.setCount(i);
            searchWord.setKeyword("i=" + i + "条数据");
            list.add(searchWord);
        }
        return list;
    }
}
