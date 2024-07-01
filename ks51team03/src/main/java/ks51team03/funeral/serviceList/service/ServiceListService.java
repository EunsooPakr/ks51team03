package ks51team03.funeral.serviceList.service;

import ks51team03.funeral.serviceList.dto.ServiceListDto;
import ks51team03.funeral.serviceList.mapper.ServiceListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceListService {

    private final ServiceListMapper serviceListMapper;

    public List<ServiceListDto> getServiceList() {
        return serviceListMapper.getServiceListDto();
    }
}

