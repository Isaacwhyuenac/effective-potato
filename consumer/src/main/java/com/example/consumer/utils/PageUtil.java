package com.example.consumer.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtil<T> {

  private int currentPage;
  private int page;
  private List<T> content;
  private long totalElements;

}

