package com.brainstation23.skeleton.common.utils;

import com.brainstation23.skeleton.presenter.domain.request.PaginationRequest;
import com.brainstation23.skeleton.presenter.domain.response.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

public class PageUtils {

    public static <E> PaginationResponse<E> mapToPaginationResponseDto(
            Page<E> page, PaginationRequest paginationRequest) {

        PaginationResponse<E> paginationResponse = new PaginationResponse<>();
        paginationResponse.setTotalItems(page.getTotalElements());
        paginationResponse.setCurrentPage(page.getNumber() + 1);
        paginationResponse.setPageSize(paginationRequest.getPageSize());
        paginationResponse.setTotalPages(page.getTotalPages());
        paginationResponse.setData(page.getContent());
        return paginationResponse;
    }

    public static Pageable getPageable(PaginationRequest request) {

        Sort sortOrder = getSort(request.getSortOrder(), request.getSortBy());
        Pageable pageable;
        Integer pageSize = request.getPageSize();
        if (request.getPageSize().equals(-1)) {
            pageSize = Integer.MAX_VALUE;
        }
        if (Boolean.FALSE.equals(ObjectUtils.isEmpty(sortOrder))) {
            pageable = PageRequest.of(request.getPageNumber(), pageSize, sortOrder);
        } else {
            pageable = PageRequest.of(request.getPageNumber(), pageSize);
        }
        return pageable;
    }

    private static Sort getSort(String sortOrder, String sortBy) {
        if (ObjectUtils.isEmpty(sortOrder) || ObjectUtils.isEmpty(sortBy)) {
            return null;
        }
        return switch (sortOrder.toLowerCase()) {
            case "asc" -> Sort.by(Sort.Order.asc(sortBy));
            case "desc" -> Sort.by(Sort.Order.desc(sortBy));
            default -> null;
        };
    }

    public static PaginationRequest mapToPaginationRequest(final Integer pageNumber, final Integer pageSize, final String sortBy, final String sortOrder) {
        final PaginationRequest paginationRequest = new PaginationRequest();
        paginationRequest.setPageNumber(pageNumber);
        paginationRequest.setPageSize(pageSize);
        paginationRequest.setSortBy(sortBy);
        paginationRequest.setSortOrder(sortOrder);
        return paginationRequest;
    }
}