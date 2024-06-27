package ks51team03.map.service;

import ks51team03.company.dto.Company;
import ks51team03.map.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("mapService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MapService {
    private final MapMapper mapMapper;
    public List<Company> getCompanyByCompanyName(Company companyName){

        return mapMapper.getCompanyByCompanyName(companyName);
    }

}
