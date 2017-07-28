package net.angrycode.data.repository;

import java.util.List;

/**
 * Created by wecodexyz on 2017/7/28.
 */

public interface IDataSource<T> {
    void add(T t);

    void delete(T t);

    void update(T t);

    List<T> queryAll();

    T queryById(int id);
}
