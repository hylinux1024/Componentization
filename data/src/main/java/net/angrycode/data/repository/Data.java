package net.angrycode.data.repository;

/**
 * Created by wecodexyz on 2017/7/28.
 */

public class Data {
    public int id;
    public String text;
    public String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        return id == data.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
