package ks51team03.funeral.serviceList.service;

import ks51team03.company.dto.Company;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveMemberPet;
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
    public void funeralReserve(ReserveDto reserveDto){

        int result = serviceListMapper.funeralReserve(reserveDto);
    }

    public List<Company> getCompanyInfo(Company company){

        return serviceListMapper.getCompanyInfo(company);
    }

    public void insertFuneralService(ServiceListDto serviceListDto){

        serviceListMapper.insertFuneralService(serviceListDto);
    };

    // 장례 업체에 등록된 장례 서비스를 가져오기 위한 company 코드
    public List<ServiceListDto> getServiceList(ServiceListDto serviceListDto){
        return serviceListMapper.getServiceList(serviceListDto);
    };

    // 장례 회사 정보를 가져오기 위한 조회
    public List<Company> getCompanyInfoList(){
        return serviceListMapper.getCompanyInfoList();
    }

    // 장례 예약 전 회원 반려동물 가져오기 위한 코드
    public List<ReserveMemberPet> getMemberPet(String memberId){
        return serviceListMapper.getMemberPet(memberId);
    }

}

