
package com.web.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.Donate;

public interface DonateDao extends JpaRepository<Donate, String> {
	Optional<Donate> findDonateByDid(long did);
}
