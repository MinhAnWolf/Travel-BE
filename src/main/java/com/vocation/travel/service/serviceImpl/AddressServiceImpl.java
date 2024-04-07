package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.entity.District;
import com.vocation.travel.entity.Province;
import com.vocation.travel.entity.Ward;
import com.vocation.travel.model.dto.DistrictDTO;
import com.vocation.travel.model.dto.ProvinceDTO;
import com.vocation.travel.model.dto.WardDTO;
import com.vocation.travel.repository.DistrictRepository;
import com.vocation.travel.repository.ProvinceRepository;
import com.vocation.travel.repository.WardRepository;
import com.vocation.travel.security.AuthenticationService;
import com.vocation.travel.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<DistrictDTO> listDistrict() {
        List<District> districts = districtRepository.findAll();
        if (districts.isEmpty()) {
           return null;
        }
        return convertDistrict(districts);
    }

    @Override
    public List<ProvinceDTO> listProvince() {
        List<Province> province = provinceRepository.findAll();
        if (province.isEmpty()) {
            return null;
        }
        return convertProvince(province);
    }

    @Override
    public List<WardDTO> listWard() {
        List<Ward> wards = wardRepository.findAll();
        if (wards.isEmpty()) {
            return null;
        }
        return convertWard(wards);
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
