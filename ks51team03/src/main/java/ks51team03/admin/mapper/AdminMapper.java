package ks51team03.admin.mapper;

import ks51team03.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<Member> getAdminList(Member member);

}
