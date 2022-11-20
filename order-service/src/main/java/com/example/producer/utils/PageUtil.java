package com.example.producer.utils;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtil<T> {

  private int currentPage;
  private int page;
  private List<T> content = Collections.EMPTY_LIST;
  private long totalElements;

}

