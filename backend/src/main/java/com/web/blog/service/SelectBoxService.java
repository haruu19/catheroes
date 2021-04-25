package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.SelectBox;

public interface SelectBoxService {
	public List<SelectBox> getGugun(String sidoname);

	public List<SelectBox> getDong(String sidoname, String gugun);
}
