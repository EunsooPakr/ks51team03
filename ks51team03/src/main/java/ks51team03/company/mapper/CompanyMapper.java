package ks51team03.company.mapper;

import ks51team03.company.dto.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    List<Company> getCompanyList(@Param("keyword") String keyword);
}
