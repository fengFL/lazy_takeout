package org.example.lazzy.pojo;

import java.util.List;

/**
 *  This class is used to encapsulate the data from paging selection
 *
 */
public class PageBean<T> {
    private List<T> records; // all the records are stored in records
    private Integer total; // the total number of records

    public PageBean() {
    }

    public PageBean(List<T> records, Integer total) {
        this.records = records;
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
