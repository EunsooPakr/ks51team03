package ks51team03.funeral.serviceList.mapper;

import ks51team03.company.dto.Company;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveMemberPet;
import ks51team03.funeral.serviceList.dto.FuneralCompanyImgDto;
import ks51team03.funeral.serviceList.dto.ServiceImgDto;
import ks51team03.funeral.serviceList.dto.ServiceListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<FuneralCompanyImgDto> getCompanyInfoList();

    //장례 예약 전 회원 반려동물 정보 가져오기
    List<ReserveMemberPet> getMemberPet(@Param("memberId") String memberId);

    // 장례 서비스 이미지 등록
    void insertFuneralServiceImg(ServiceImgDto serviceImgDto);

    //장례업체 정보 조회를 위한 업체 코드 조회
    List<ServiceListDto> modifyServiceInfoByCode(String funeralserviceCode);

    //등록된 장례 서비스 수정
    void updateServiceInfo(ServiceListDto serviceListDto);

    //등록된 장례 서비스 이미지 삭제
    void deleteServiceImg(ServiceImgDto serviceImgDto);

    //등록된 장례 서비스 이미지 체크
    boolean checkServiceImgExists(@Param("fscode") String fscode, @Param("ccode") String ccode);

    //장례 서비스 등록 이미지 수정
    ServiceImgDto getServiceImg(@Param("fscode") String fscode, @Param("ccode") String ccode);

    //
    List<ServiceImgDto> getServiceImgs(@Param("fscodeList") List<String> fscodeList, @Param("ccode") String ccode);

}
