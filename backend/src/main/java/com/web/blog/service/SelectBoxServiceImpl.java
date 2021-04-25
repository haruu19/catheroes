package com.web.blog.service;

import java.util.List;

import com.web.blog.dao.SelectBoxDao;
import com.web.blog.model.SelectBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectBoxServiceImpl implements SelectBoxService {

	@Autowired
	SelectBoxDao selectBoxDao;

	public List<SelectBox> getGugun(String sidoname) {
		return selectBoxDao.getGugun(sidoname);
	}

	public List<SelectBox> getDong(String sidoname, String gugun) {
		return selectBoxDao.getDong(sidoname, gugun);
	}
}
