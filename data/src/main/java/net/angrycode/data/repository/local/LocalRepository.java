package net.angrycode.data.repository.local;

import net.angrycode.data.repository.Data;
import net.angrycode.data.repository.IDataSource;

import java.util.List;

/**
 * Created by wecodexyz on 2017/7/28.
 */

public class LocalRepository implements IDataSource<Data> {


    public LocalRepository() {
    }

    @Override
    public void add(Data data) {
        DBHelper.get().add(data);
    }

    @Override
    public void delete(Data data) {
        DBHelper.get().delete(data);
    }

    @Override
    public void update(Data data) {
        DBHelper.get().update(data);
    }

    @Override
    public List<Data> queryAll() {
        return DBHelper.get().queryAll();
    }

    @Override
    public Data queryById(int id) {
        return DBHelper.get().queryById(id);
    }
}
