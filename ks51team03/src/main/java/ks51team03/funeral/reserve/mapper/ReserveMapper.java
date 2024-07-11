package ks51team03.funeral.reserve.mapper;

import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveInfoDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReserveMapper {

    void funeralReserve(ReserveDto reserveDto);

    //장례 예약 코드
    String getLastFuneralServiceCode();

    // 장례 예약자 인적사항 확인하기
    void funeralReserveInfo(ReserveInfoDto reserveInfoDto);

}
