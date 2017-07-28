package net.angrycode.data.repository;

import android.support.annotation.NonNull;

import net.angrycode.data.repository.local.LocalRepository;
import net.angrycode.data.repository.remote.RemoteRepository;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wecodexyz on 2017/7/28.
 */

public class RepositoryFactory implements IDataSource<Data> {
    private IDataSource<Data> local;
    private IDataSource<Data> remote;

    private static RepositoryFactory INSTANCE;
    /**
     * 使用Map实现一个内存缓存
     */
    HashMap<String, Data> mCache = new HashMap<>();

    private RepositoryFactory(@NonNull IDataSource<Data> local, @NonNull IDataSource<Data> remote) {
        this.local = local;
        this.remote = remote;
    }

    public static RepositoryFactory get(@NonNull IDataSource<Data> local, @NonNull IDataSource<Data> remote) {
        if (INSTANCE == null) {
            INSTANCE = new RepositoryFactory(local, remote);
        }
        return INSTANCE;
    }

    public static RepositoryFactory get() {
        if (INSTANCE == null) {
            INSTANCE = new RepositoryFactory(new LocalRepository(), new RemoteRepository());
        }
        return INSTANCE;
    }

    public void destory() {
        INSTANCE = null;
    }

    @Override
    public void add(Data data) {
        local.add(data);
        remote.add(data);
        mCache.put(String.valueOf(data.id), data);
    }

    @Override
    public void delete(Data data) {
        local.delete(data);
        remote.delete(data);
        mCache.remove(String.valueOf(data.id));
    }

    @Override
    public void update(Data data) {
        local.update(data);
        remote.update(data);
        mCache.put(String.valueOf(data.id), data);
    }

    /**
     * @return
     */
    @Override
    public List<Data> queryAll() {
        List<Data> list = local.queryAll();
        if (list.isEmpty()) {
            list = remote.queryAll();
        }
        return list;
    }

    /**
     * 这里使用三级缓存获取一个Data对象
     *
     * @param id
     * @return
     */
    @Override
    public Data queryById(int id) {

        Data data = mCache.get(String.valueOf(id));
        if (data == null) {
            data = local.queryById(id);
        }
        if (data == null) {
            data = remote.queryById(id);
        }
        if (data != null) {
            mCache.put(String.valueOf(id), data);
        }
        return data;
    }

}
