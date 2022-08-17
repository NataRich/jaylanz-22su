package com.jaylanz.domain.vo;

import java.io.Serializable;
import java.util.List;

public class ResourcePage<T> implements Serializable {
    private Long currentPage;  // this page number
    private Long currentSize;  // number of items in this page
    private Long totalSize;    // total number of items in db
    private Long totalPages;   // total number of pages
    private List<T> resources;

    public ResourcePage() {
    }

    public ResourcePage(Long currentPage, Long currentSize, Long totalSize, Long totalPages, List<T> resources) {
        this.currentPage = currentPage;
        this.currentSize = currentSize;
        this.totalSize = totalSize;
        this.totalPages = totalPages;
        this.resources = resources;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Long currentSize) {
        this.currentSize = currentSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getResources() {
        return resources;
    }

    public void setResources(List<T> resources) {
        this.resources = resources;
    }
}
