package com.web.blog.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.opsworkscm.model.ResourceNotFoundException;
import com.web.blog.dao.DonateDao;
import com.web.blog.dao.MemberDao;
import com.web.blog.model.Article;
import com.web.blog.model.Cat;
import com.web.blog.model.Comment;
import com.web.blog.model.Donate;
import com.web.blog.model.Member;
import com.web.blog.model.request.ArticleRequest;
import com.web.blog.model.request.CommentRequest;
import com.web.blog.utill.amazon.AmazonClient;

@Service
@Transactional
public class DonateServiceImpl implements DonateService {
	@Autowired
	DonateDao donateDao;
	
	@Autowired
	MemberDao memberDao;	
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Override
	public List<Donate> findAll() {
		return donateDao.findAll();
	}

	@Override
	public List<Donate> updateDonate(long did, long amount) {
		Optional<Donate> donateDb = donateDao.findDonateByDid(did);
		
		if(donateDb.isPresent()) {
			Donate donateUpdate = donateDb.get();
			donateUpdate.setCurrent(donateUpdate.getCurrent() + amount);

			donateDao.save(donateUpdate);
			return donateDao.findAll();
		}else {
			throw new ResourceNotFoundException("Record not found with did : " + did);
		}
	}

}
