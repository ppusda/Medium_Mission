package com.ll.medium_mission.member.repository;

import com.ll.medium_mission.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
