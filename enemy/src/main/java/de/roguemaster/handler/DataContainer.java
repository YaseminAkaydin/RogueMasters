package de.roguemaster.handler;

public class DataContainer<T> {
    private T data = null;

    public T getData() { return this.data; }

    public DataContainer(T data) {
        this.data = data;
    }
}
