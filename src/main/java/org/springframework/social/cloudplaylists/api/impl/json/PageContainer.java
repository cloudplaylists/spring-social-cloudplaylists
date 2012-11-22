package org.springframework.social.cloudplaylists.api.impl.json;

/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Michael Lavelle
 */
public class PageContainer<T> implements Page<T> {

	private List<T> content = new ArrayList<T>();
	private int size;
	private int number;
	private int numberOfElements;
	private long totalElements;
	private boolean firstPage;
	private boolean lastPage;
	private int totalPages;
	private Sort sort;

	private PageImpl<T> createPageImpl() {
		if (content.size() > 0)
		{
			Pageable pageable = new PageRequest(number, size);
			return new PageImpl<T>(content, pageable, totalElements);
		}
		else
		{
			return new PageImpl<T>(content);
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	private Pageable pageable;

	private long total;

	@Override
	public boolean hasPreviousPage() {
		return createPageImpl().hasPreviousPage();
	}

	@Override
	public boolean hasNextPage() {
		return createPageImpl().hasNextPage();
	}

	@Override
	public Iterator<T> iterator() {
		return createPageImpl().iterator();
	}

	@Override
	public boolean hasContent() {
		return createPageImpl().hasContent();
	}

}
