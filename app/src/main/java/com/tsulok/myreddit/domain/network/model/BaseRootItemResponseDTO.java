package com.tsulok.myreddit.domain.network.model;

import java.util.List;

/**
 * This is a java class, because Moya has an error currentyl with nested generic objects for parsing
 */

public class BaseRootItemResponseDTO<T> {
    public List<BaseItemResponseDTO<T>> children;
}