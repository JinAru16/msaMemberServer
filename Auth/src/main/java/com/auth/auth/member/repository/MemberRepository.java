package com.auth.auth.member.repository;

import com.auth.auth.member.domain.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

}
