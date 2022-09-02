package com.shop.shopservice.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shop.shopservice.entity.Search;

public interface SearchRepository extends JpaRepository<Search, Long> {
    //....

    @Modifying
    @Query(
            value = "truncate table search_keyword",
            nativeQuery = true
    )
    void truncateSearchKeyword();

}
