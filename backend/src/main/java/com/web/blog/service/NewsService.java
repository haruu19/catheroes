package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.News;

public interface NewsService {
	public Optional<List<News>> retrieveNews();
}
