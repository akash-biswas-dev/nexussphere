package com.biswasakashdev.nexussphere.common.response;

import java.util.List;
import java.util.Map;

public record Page<T>(
        Integer page,
        Integer size,
        Integer totalPages,
        Long totalElements,
        List<T> content

){

    public enum Direction {
        ASC,
        DESC
    }

    public record PageDetails(
            Integer page,
            Integer size,
            Map<String, Direction> sort
    ){

    }


    // If requiredCount is greater than count, then return the last page.
    public static int getRequiredPage(int requiredPage, int pageSize, long totalCount) {

        long requiredCount = (long) requiredPage * pageSize;

        if (totalCount == 0) {
            return 1;
        } else if (totalCount < requiredCount) {
            return (int) Math.ceil((double) totalCount / pageSize);
        } else {
            return requiredPage;
        }
    }


    public Page(
            Integer page,
            Integer size,
            Integer totalPages,
            Long totalElements,
            List<T> content
    ) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
    }

}
