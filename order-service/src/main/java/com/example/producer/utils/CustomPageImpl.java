package com.example.producer.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomPageImpl<T> implements Page<T> {
  private final Page<T> delegate;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  CustomPageImpl(@JsonProperty("content") List<T> content,
                 @JsonProperty("number") int number,
                 @JsonProperty("size") int size,
                 @JsonProperty("totalElements")
                 @JsonAlias({"total-elements", "total_elements", "totalelements", "TotalElements"}) long totalElements,
                 @JsonProperty("sort") Sort sort) {
    if (size > 0) {
      PageRequest pageRequest;
      if (sort != null) {
        pageRequest = PageRequest.of(number, size, sort);
      } else {
        pageRequest = PageRequest.of(number, size);
      }
      delegate = new PageImpl<>(content, pageRequest, totalElements);
    } else {
      delegate = new PageImpl<>(content);
    }
  }

  @JsonProperty
  @Override
  public int getTotalPages() {
    return delegate.getTotalPages();
  }

  @JsonProperty
  @Override
  public long getTotalElements() {
    return delegate.getTotalElements();
  }

  @JsonProperty
  @Override
  public int getNumber() {
    return delegate.getNumber();
  }

  @JsonProperty
  @Override
  public int getSize() {
    return delegate.getSize();
  }

  @JsonProperty
  @Override
  public int getNumberOfElements() {
    return delegate.getNumberOfElements();
  }

  @JsonProperty
  @Override
  public List<T> getContent() {
    return delegate.getContent();
  }

  @JsonProperty
  @Override
  public boolean hasContent() {
    return delegate.hasContent();
  }

  @JsonIgnore
  @Override
  public Sort getSort() {
    return delegate.getSort();
  }

  @JsonProperty
  @Override
  public boolean isFirst() {
    return delegate.isFirst();
  }

  @JsonProperty
  @Override
  public boolean isLast() {
    return delegate.isLast();
  }

  @JsonIgnore
  @Override
  public boolean hasNext() {
    return delegate.hasNext();
  }

  @JsonIgnore
  @Override
  public boolean hasPrevious() {
    return delegate.hasPrevious();
  }

  @JsonIgnore
  @Override
  public Pageable nextPageable() {
    return delegate.nextPageable();
  }

  @JsonIgnore
  @Override
  public Pageable previousPageable() {
    return delegate.previousPageable();
  }

  @JsonIgnore
  @Override
  public <S> Page<S> map(Function<? super T, ? extends S> converter) {
    return delegate.map(converter);
  }

  @JsonIgnore
  @Override
  public Iterator<T> iterator() {
    return delegate.iterator();
  }

  @JsonIgnore
  @Override
  public Pageable getPageable() {
    return delegate.getPageable();
  }

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return delegate.equals(obj);
  }

  @Override
  public String toString() {
    return delegate.toString();
  }

}
