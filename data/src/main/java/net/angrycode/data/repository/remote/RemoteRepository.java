package net.angrycode.data.repository.remote;

import net.angrycode.data.repository.Data;
import net.angrycode.data.repository.IDataSource;

import java.util.List;

/**
 * 模拟远程访问数据
 * Created by huangyanglin on 2017/7/28.
 */

public class RemoteRepository implements IDataSource<Data> {
    @Override
    public void add(Data data) {
        DataApi.get().add(data);
    }

    @Override
    public void delete(Data data) {
        DataApi.get().delete(data);
    }

    @Override
    public void update(Data data) {
        DataApi.get().update(data);
    }

    @Override
    public List<Data> queryAll() {
        return DataApi.get().queryAll();
    }

    @Override
    public Data queryById(int id) {
        return DataApi.get().queryById(id);
    }
}
