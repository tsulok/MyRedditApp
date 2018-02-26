package com.tsulok.myreddit.domain.network.model;

/**
 * This is a java class, because Moya has an error currentyl with nested generic objects for parsing
 */

public class BaseRootResponseDTO<T> {
    public BaseRootItemResponseDTO<T> data;
}