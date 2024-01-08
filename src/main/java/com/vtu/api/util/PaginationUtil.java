package com.vtu.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Slf4j
public class PaginationUtil {

    private PaginationUtil() {}

    public static Pageable pageable(int page, int pageSize) {
        return PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
    }

}
