package ks51team03.funeral.reserve.service;

import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveInfoDto;
import ks51team03.funeral.reserve.dto.ReservePaymentDto;
import ks51team03.funeral.reserve.dto.ReserveServiceInfoDto;
import ks51team03.funeral.reserve.mapper.ReserveMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 장례 예약자 인적사항 확인 맵핑
    public List<ReserveInfoDto> funeralReserveInfo(ReserveInfoDto reserveInfoDto) {
        reserveMapper.funeralReserveInfo(reserveInfoDto);
        return  reserveMapper.funeralReserveInfo(reserveInfoDto);
    }

    // 장례 예약 내역 확인
    public List<ReserveServiceInfoDto> funeralReserveServiceInfo(ReserveServiceInfoDto reserveServiceInfoDto) {
        reserveMapper.funeralReserveServiceInfo(reserveServiceInfoDto);
        return  reserveMapper.funeralReserveServiceInfo(reserveServiceInfoDto);
    }

    // 결제 콜백 로직
    public void handlePaymentCallback(ReservePaymentDto reservePaymentDto) {
        // 결제 정보를 저장하는 로직
        reserveMapper.insertPayment(reservePaymentDto);

        // 필요한 추가 로직 구현 (예: 결제 상태 업데이트, 알림 전송 등)
    }

    //fpcode 생성 메서드
    public String generateFpcode(){
        String lastFpcode = reserveMapper.getLastFpcode();
        int newFpcdoe = (lastFpcode == null) ? 1 : Integer.parseInt(lastFpcode.substring(2)) + 1;
        return "fp" + newFpcdoe;
    }

}
