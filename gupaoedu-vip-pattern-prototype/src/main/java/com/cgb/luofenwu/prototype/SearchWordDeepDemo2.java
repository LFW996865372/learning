package com.cgb.luofenwu.prototype;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Autor:LFW
 * @Description:<p>深拷贝 第二种方法：先将对象序列化，然后再反序列化成新的对象。
 * </p>
 * @Date:create in 2020/9/2016:32
 */
public class SearchWordDeepDemo2 {
    private HashMap<String, SearchWord> currentKeywords = new HashMap<String, SearchWord>();
    private long lastUpdateTime = -1;

    public Object deepCopy(Object object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(object);

        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    private void init() {
        List<SearchWord> list = getSearchWords(1);
        for (SearchWord searchWord : list) {
            currentKeywords.put(searchWord.getKeyword(), searchWord);
        }
    }

    public static void main(String[] args) {
        SearchWordDeepDemo2 searchWordDeepDemo2 = new SearchWordDeepDemo2();
        searchWordDeepDemo2.refresh();
    }

    public void refresh() {
        init();
        // Deep copy,使用序列化,注意 SearchWord implements Serializable
        HashMap<String, SearchWord> newKeywords = new HashMap<String, SearchWord>();
        for (HashMap.Entry<String, SearchWord> e : currentKeywords.entrySet()) {
            SearchWord searchWord = e.getValue();
            SearchWord newSearchWord = null;
            try {
                newSearchWord = (SearchWord) deepCopy(searchWord);
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new RuntimeException("深拷贝异常");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                throw new RuntimeException("深拷贝异常");
            }
            newKeywords.put(e.getKey(), newSearchWord);
        }
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
