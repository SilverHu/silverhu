package com.common.utils.page;

import lombok.Getter;

/**
 * 分页工具
 * @author sliverhu
 *
 */
@Getter
public class Pageable {
    private final int page;
    private final int size;
    private final int start;

    public Pageable(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        this.page = page;
        this.size = size;
        this.start = page * size;
    }
}
