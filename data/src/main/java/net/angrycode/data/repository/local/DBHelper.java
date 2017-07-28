package net.angrycode.data.repository.local;

import net.angrycode.data.repository.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库封装工具类，这里是简单展示，不是实际数据库操作
 * Created by wecodexyz on 2017/7/28.
 */

public class DBHelper {
    /**
     * 这里简单展示存储，不是实际数据库操作
     */
    private List<Data> dataList = new ArrayList<>();

    private DBHelper() {

    }

    private static final class Singleton {
        static final DBHelper INSTANCE = new DBHelper();
    }

    public static DBHelper get() {
        return Singleton.INSTANCE;
    }

    public boolean add(Data data) {
        return dataList.add(data);
    }

    public boolean delete(Data data) {
        return dataList.remove(data);
    }

    public boolean update(Data data) {
        if (dataList.contains(data)) {
            dataList.remove(data);
            dataList.add(data);
        } else {
            dataList.add(data);
        }
        return true;
    }

    public List<Data> queryAll() {
        return dataList;
    }

    public Data queryById(int id) {
        for (Data data : dataList) {
            if (data.id == id) {
                return data;
            }
        }
        return null;
    }
}
