package ks51team03.funeral.reserve.mapper;

import ks51team03.funeral.reserve.dto.ReserveDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public class ReserveMapper {

    int funeralReserve(ReserveDto.Funeral_reserve reserve);
}
