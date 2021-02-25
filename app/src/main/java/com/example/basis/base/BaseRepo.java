package com.example.basis.base;

public class BaseRepo<T> {
    protected T remoteDataSource;

    public BaseRepo(T remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }
}
