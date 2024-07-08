package ks51team03.funeral.reserve.service;

import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.mapper.ReserveMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ReserveService {

    private final ReserveMapper reserveMapper;

    public void funeralReserve(ReserveDto reserveDto) {

        reserveMapper.funeralReserve(reserveDto);

    }

    //장례 예약 서비스 코드 만들기위한 마지막 코드 찾기
    public String getLastFuneralServiceCode(){
        String getLastFuneralServiceCode = reserveMapper.getLastFuneralServiceCode();
        int newCodeNumber = Integer.parseInt(getLastFuneralServiceCode.substring(2)) + 1;
        return "fr" + newCodeNumber;
    }



}
