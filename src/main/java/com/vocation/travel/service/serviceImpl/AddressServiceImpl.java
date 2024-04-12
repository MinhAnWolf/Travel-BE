package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.entity.District;
import com.vocation.travel.entity.Province;
import com.vocation.travel.entity.Ward;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.dto.DistrictDTO;
import com.vocation.travel.dto.ProvinceDTO;
import com.vocation.travel.dto.WardDTO;
import com.vocation.travel.repository.DistrictRepository;
import com.vocation.travel.repository.ProvinceRepository;
import com.vocation.travel.repository.WardRepository;
import com.vocation.travel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    private final static String SERVICE_NAME = "AddressService";

    @Override
    public BaseResponse listDistrict() {
        final String METHOD_NAME = "listDistrict";
        Log.startLog(SERVICE_NAME, METHOD_NAME);
        List<District> districts = districtRepository.findAll();
        BaseResponse response;
        if (districts.isEmpty()) {
            response = new BaseResponse(CommonConstant.RESPONSE_FAIL, null, "Get district fail");
            Log.debugLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
           return response;
        }
        List<DistrictDTO> districtDtoList = convertDistrict(districts);
        response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS, districtDtoList, "Get district success");
        Log.debugLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    @Override
    public BaseResponse listProvince() {
        final String METHOD_NAME = "listProvince";
        Log.startLog(SERVICE_NAME, METHOD_NAME);
        List<Province> province = provinceRepository.findAll();
        BaseResponse response;
        if (province.isEmpty()) {
            response = new BaseResponse(CommonConstant.RESPONSE_FAIL, null, "Get province fail");
            Log.debugLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        List<ProvinceDTO> provinceDtoList = convertProvince(province);
        response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS, provinceDtoList, "Get province success");
        Log.debugLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    @Override
    public BaseResponse listWard() {
        final String METHOD_NAME = "listWard";
        Log.startLog(SERVICE_NAME, METHOD_NAME);
        List<Ward> wards = wardRepository.findAll();
        BaseResponse response;
        if (wards.isEmpty()) {
            response = new BaseResponse(CommonConstant.RESPONSE_FAIL, null, "Get ward fail");
            Log.debugLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        List<WardDTO> wardDtoList = convertWard(wards);
        response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS, wardDtoList, "Get ward success");
        Log.debugLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    private List<DistrictDTO> convertDistrict(List<District> districts) {
        List<DistrictDTO> list = new ArrayList<>();
        for (District district: districts) {
            DistrictDTO districtDto = new DistrictDTO();
            districtDto.setDistrictId(district.getDistrict_id());
            districtDto.setDistrictType(district.getDistrict_type());
            districtDto.setDistrictName(district.getDistrict_name());
            districtDto.setProvinceId(district.getProvince_id());
            districtDto.setProvinceName(district.getProvince_name());
            list.add(districtDto);
        }
        return list;
    }

    private List<ProvinceDTO> convertProvince(List<Province> provinces) {
        List<ProvinceDTO> list = new ArrayList<>();
        for (Province province: provinces) {
            ProvinceDTO provinceDto = new ProvinceDTO();
            provinceDto.setProvinceType(province.getProvince_type());
            provinceDto.setProvinceName(province.getProvince_name());
            provinceDto.setProvinceId(province.getProvince_id());
            provinceDto.setUrlImage(province.getUrl_image());
            list.add(provinceDto);
        }
        return list;
    }

    private List<WardDTO> convertWard(List<Ward> wards) {
        List<WardDTO> list = new ArrayList<>();
        for (Ward ward: wards) {
            WardDTO wardDto = new WardDTO();
            wardDto.setWardName(ward.getWard_name());
            wardDto.setWardType(ward.getWard_type());
            wardDto.setWardId(ward.getWard_id());
            wardDto.setDistrictId(ward.getDistrict_id());
            wardDto.setProvinceId(ward.getProvince_id());
            wardDto.setProvinceName(ward.getProvince_name());
            wardDto.setDistrictName(ward.getDistrict_name());
            list.add(wardDto);
        }
       return list;
    }
}
