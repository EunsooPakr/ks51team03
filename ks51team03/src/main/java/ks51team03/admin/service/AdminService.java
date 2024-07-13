package ks51team03.admin.service;

import ks51team03.admin.mapper.AdminMapper;
import ks51team03.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class AdminService {

    private final AdminMapper adminMapper;

    public List<Member> getAdminList(Member member) {
        return adminMapper.getAdminList(member);
    }

}
