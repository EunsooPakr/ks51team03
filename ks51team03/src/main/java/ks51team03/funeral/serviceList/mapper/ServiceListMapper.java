package ks51team03.funeral.serviceList.mapper;

import ks51team03.company.dto.Company;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveMemberPet;
import ks51team03.funeral.serviceList.dto.ServiceListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServiceListMapper {

    //장례 업체 서비스 조회
    List<ServiceListDto> getServiceListDto();

    //장례업체 정보 조회를 위한 업체 코드 조회
    List<ServiceListDto> getServiceInfoByCode(String funeralserviceCcode);

    //ServiceListDto getServiceInfoByCode(String funeralserviceCcode);

    //장례 예약
    int funeralReserve(ReserveDto reserveDto);

    List<Company> getCompanyInfo(Company company);

    //장례 서비스 등록하기
    void insertFuneralService(ServiceListDto serviceListDto);

    List<ServiceListDto> getServiceList(ServiceListDto serviceListDto);

    //장례 회사 노출을 위한 정보 가져오기
    List<Company> getCompanyInfoList();

    //장례 예약 전 회원 반려동물 정보 가져오기
    List<ReserveMemberPet> getMemberPet();

}
