package com.vocation.travel.service;

import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.dto.DistrictDTO;
import com.vocation.travel.dto.ProvinceDTO;
import com.vocation.travel.dto.WardDTO;

import java.util.List;

public interface AddressService {
    BaseResponse listDistrict();
    BaseResponse listProvince();
    BaseResponse listWard();
}
