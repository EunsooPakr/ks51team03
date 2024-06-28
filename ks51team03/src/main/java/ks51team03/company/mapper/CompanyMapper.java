package ks51team03.company.mapper;

import ks51team03.company.dto.ComMap;
import ks51team03.company.dto.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    List<Company> getCompanyList(@Param("keyword") String keyword);

    // 업체 좌표 가져오기
    ComMap getComMapByCCode(@Param("cCode") String cCode);
}
