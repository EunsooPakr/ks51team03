package ks51team03.company.service;


import ks51team03.company.dto.Company;
import ks51team03.company.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CompanyService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyMapper companyMapper;


    public List<Company> getCompanyList(String keyword) {
        return companyMapper.getCompanyList(keyword);
    }

}
