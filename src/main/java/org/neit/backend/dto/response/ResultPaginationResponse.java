package org.neit.backend.dto.response;

public class ResultPaginationResponse {
    private long total_records;
    private int total_records_page;
    private int current_page;
    private int total_pages;
    private Object data;

    public long getTotal_records() {
        return total_records;
    }

    public void setTotal_records(long total_records) {
        this.total_records = total_records;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getTotal_records_page() {
        return total_records_page;
    }

    public void setTotal_records_page(int total_records_page) {
        this.total_records_page = total_records_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
