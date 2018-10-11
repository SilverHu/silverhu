package com.common.utils.xls;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class ExcelBean {

    /** excel文件 */
    private File excel;

    /** 页名 */
    private String sheetName;

    /** 表头 */
    private List<String> headers;

    /** 内容 */
    private List<List<Object>> rows;

    public ExcelBean() {
        super();
    }

    public ExcelBean(File excel, String sheetName, List<List<Object>> rows) {
        super();
        this.excel = excel;
        this.sheetName = sheetName;
        this.rows = rows;
    }

    /**
     * excel文件
     * 
     * @return
     */
    public File getExcel() {
        return excel;
    }

    /**
     * excel文件
     * 
     * @param excel
     */
    public void setExcel(File excel) {
        this.excel = excel;
    }

    /**
     * 页名
     * 
     * @return
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * 页名
     * 
     * @param sheetName
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * 表头
     * 
     * @return
     */
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * 表头
     * 
     * @param header
     */
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    /**
     * 内容
     * 
     * @return
     */
    public List<List<Object>> getRows() {
        return rows;
    }

    /**
     * 内容
     * 
     * @param rows
     */
    public void setRows(List<List<Object>> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
