package net.angrycode.data.repository.remote;

import net.angrycode.data.repository.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wecodexyz on 2017/7/28.
 */

public class DataApi {
    /**
     * 这里简单展示存储，不是实际数据库操作
     */
    private List<Data> dataList = new ArrayList<>();

    private DataApi() {

    }

    public static final class SingleTon {
        private static final DataApi INSTANCE = new DataApi();
    }

    public static DataApi get() {
        return SingleTon.INSTANCE;
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
        List<Data> list = new ArrayList<>();
        for (int i = 0; i < 25; ++i) {
            Data data = new Data();
            data.id = i;
            data.text = "测试-" + i;
            list.add(data);
        }
        return list;
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
