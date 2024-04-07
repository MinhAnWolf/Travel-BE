package com.vocation.travel.service;

import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.model.dto.DistrictDTO;
import com.vocation.travel.model.dto.ProvinceDTO;
import com.vocation.travel.model.dto.WardDTO;

import java.util.List;

public interface AddressService {
    BaseResponse listDistrict();
    BaseResponse listProvince();
    BaseResponse listWard();
}
