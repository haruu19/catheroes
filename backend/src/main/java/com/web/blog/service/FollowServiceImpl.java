package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.opsworkscm.model.ResourceNotFoundException;
import com.web.blog.dao.FollowDao;
import com.web.blog.model.Cat;
import com.web.blog.model.Follow;
import com.web.blog.model.Member;

@Service
public class FollowServiceImpl implements FollowService{
	@Autowired
	FollowDao followDao;
	
	public List<Follow> findByCatCatid(long catid) {		
		return followDao.findByCatCatid(catid);
	}

	public List<Follow> findByMebmerMid(long mid) {
		return followDao.findByMemberMid(mid);
	}

	@Override
	public void deleteByCatCatidAndMemberMid(long catid, long mid) {
		Optional<Follow> followDb = followDao.findByCatCatidAndMemberMid(catid, mid);
		if(followDb.isPresent()) {
			followDao.delete(followDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with catid,mid : " + catid+" "+ mid);			
		}		
	}

	@Override
	public Follow saveFollow(long catid, long mid) {
		Follow follow = new Follow();
		Cat cat = new Cat();
		cat.setCatid(catid);		
		Member member = new Member();
		member.setMid(mid);
		follow.setMember(member);
		follow.setCat(cat);
		return followDao.save(follow);		
	}

	@Override
	public Optional<Follow> isFollow(long catid, long mid) {
		return followDao.findByCatCatidAndMemberMid(catid, mid);
	}	
}
