package ks51team03.funeral.serviceList.service;

import ks51team03.funeral.serviceList.dto.ServiceListDto;
import ks51team03.funeral.serviceList.mapper.ServiceListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServiceListService {

    private final ServiceListMapper serviceListMapper;

    public List<ServiceListDto> getServiceList() {
        return serviceListMapper.getServiceListDto();
    }

    public List<ServiceListDto> getServiceInfoByCode(String funeralserviceCcode){

        log.info("getServiceInfoByCode: {}", serviceListMapper.getServiceInfoByCode(funeralserviceCcode));

        return serviceListMapper.getServiceInfoByCode(funeralserviceCcode);
    }

    /**
     * 회원가입 프로세스
     */
    public void reserveDetail(ServiceListDto serviceListDto){

        int result = serviceListMapper.reserveDetail(serviceListDto);
    }


//    public ServiceListDto getServiceInfoByCode(String funeralserviceCcode) {
//
//      return serviceListMapper.getServiceInfoByCode(funeralserviceCcode);
//    }
}

