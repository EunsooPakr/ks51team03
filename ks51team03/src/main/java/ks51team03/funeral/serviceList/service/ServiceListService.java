package ks51team03.funeral.serviceList.service;

import ks51team03.company.dto.Company;
import ks51team03.files.dto.FileRequest;
import ks51team03.files.mapper.FileMapper;
import ks51team03.files.util.FileUtils;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveMemberPet;
import ks51team03.funeral.serviceList.dto.FuneralCompanyImgDto;
import ks51team03.funeral.serviceList.dto.ServiceImgDto;
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
    private final FileUtils fileUtils;
    private final FileMapper fileMapper;


    public List<ServiceListDto> getServiceList() {
        return serviceListMapper.getServiceListDto();
    }

    public List<ServiceListDto> getServiceInfoByCode(String funeralserviceCcode){

        log.info("getServiceInfoByCode: {}", serviceListMapper.getServiceInfoByCode(funeralserviceCcode));

        return serviceListMapper.getServiceInfoByCode(funeralserviceCcode);
    }

    /**
     *  장례 예약 프로세스
     */
    public void funeralReserve(ReserveDto reserveDto){

        int result = serviceListMapper.funeralReserve(reserveDto);
    }

    public List<Company> getCompanyInfo(Company company){

        return serviceListMapper.getCompanyInfo(company);
    }

    public String insertFuneralService(ServiceListDto serviceListDto){
        serviceListMapper.insertFuneralService(serviceListDto);
        return serviceListDto.getFuneralserviceCode(); // 생성된 fscode 반환
    }

    // 장례 업체에 등록된 장례 서비스를 가져오기 위한 company 코드
    public List<ServiceListDto> getServiceList(ServiceListDto serviceListDto){
        return serviceListMapper.getServiceList(serviceListDto);
    };

    // 장례 회사 정보를 가져오기 위한 조회
    public List<FuneralCompanyImgDto> getCompanyInfoList(){
        return serviceListMapper.getCompanyInfoList();
    }

    // 장례 예약 전 회원 반려동물 가져오기 위한 코드
    public List<ReserveMemberPet> getMemberPet(String memberId){
        return serviceListMapper.getMemberPet(memberId);
    }

    // 장례 예약 서비스 이미지 업로드
    public void addFuneralServiceImg(ServiceImgDto serviceImgDto, String companyCode){

        FileRequest fileRequest = fileUtils.uploadFile(serviceImgDto.getFurImgFile(), companyCode);
        if(fileRequest != null){
            fileRequest.setFileCate(companyCode);
            log.info("fileRequest: {}", fileRequest);
            fileMapper.addFile(fileRequest);
            serviceImgDto.setFileIdx(fileRequest.getFileIdx());
        }
        serviceListMapper.insertFuneralServiceImg(serviceImgDto);
    }

    // 장례 예약 서비스 이미지 등록하기
    public void addFuneralServiceImg(ServiceImgDto serviceImgDto){
        serviceListMapper.insertFuneralServiceImg(serviceImgDto);
    }

    public List<ServiceListDto> modifyServiceInfoByCode(String funeralserviceCode){

        log.info("getServiceInfoByCode: {}", serviceListMapper.modifyServiceInfoByCode(funeralserviceCode));

        return serviceListMapper.modifyServiceInfoByCode(funeralserviceCode);
    }

    public void updateServiceInfo(ServiceListDto serviceListDto){
        serviceListMapper.updateServiceInfo(serviceListDto);
    }

    public void addOrUpdateFuneralServiceImg(ServiceImgDto serviceImgDto, String companyCode){
        // 파일 업로드 처리
        FileRequest fileRequest = fileUtils.uploadFile(serviceImgDto.getFurImgFile(), companyCode);
        if (fileRequest != null) {
            fileRequest.setFileCate(companyCode);
            log.info("fileRequest: {}", fileRequest);
            fileMapper.addFile(fileRequest);
            serviceImgDto.setFileIdx(fileRequest.getFileIdx());
        }
        serviceListMapper.insertFuneralServiceImg(serviceImgDto);
    }

    //장례 서비스 이미지를 가져오기 위한 메서드
    public ServiceImgDto getServiceImg(String fscode, String ccode){
        return serviceListMapper.getServiceImg(fscode, ccode);
    }

    //
    public List<ServiceImgDto> getServiceImgs(List<String> fscodeList, String ccode) {
        return serviceListMapper.getServiceImgs(fscodeList, ccode);
    }




}

