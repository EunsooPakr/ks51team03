package ks51team03.funeral.serviceList.mapper;

import ks51team03.funeral.serviceList.dto.ServiceListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServiceListMapper {

    //장례 업체 서비스 조회
    List<ServiceListDto> getServiceListDto();
}
