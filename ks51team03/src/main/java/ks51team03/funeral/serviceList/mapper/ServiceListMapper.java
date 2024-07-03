package ks51team03.funeral.serviceList.mapper;

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


}
