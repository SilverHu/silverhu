package com.common.utils.page;

import java.util.List;

import lombok.Getter;

@Getter
public class Page<T> {
    /** 页码 */
    private int page;

    /** 条数 */
    private int size;

    /** 总页数 */
    private int totalPages;

    /** 总数 */
    private int totalSize;

    /** 数据 */
    private List<T> content;

    /** 查询条件 */
    private Object query;

    /**
     * 
     * @param pageable
     *            分页条件
     * @param content
     *            数据
     * @param totalElements
     *            总数
     * @param query
     *            分页查询条件
     */
    public Page(Pageable pageable, List<T> content, int totalElements, Object query) {
        this(pageable, content, totalElements);
        this.query = query;
    }

    /**
     * 
     * @param pageable
     *            分页条件
     * @param content
     *            数据
     * @param totalElements
     *            总数
     */
    public Page(Pageable pageable, List<T> content, int totalElements) {
        this(pageable.getPage(), pageable.getSize(), totalElements, content);
        this.totalPages = (int) Math.ceil((Double.valueOf(totalElements) / Double.valueOf(pageable.getSize())));
    }

    private Page(int page, int size, int totalSize, List<T> content) {
        super();
        this.page = page;
        this.size = size;
        this.totalSize = totalSize;
        this.content = content;
    }
}
