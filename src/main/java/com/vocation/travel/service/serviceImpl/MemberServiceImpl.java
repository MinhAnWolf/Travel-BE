package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.constant.CodeConstant;
import com.vocation.travel.config.ExceptionHandler.BadRequestException;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.MemberDTO;
import com.vocation.travel.entity.Member;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.MemberRepository;
import com.vocation.travel.repository.TripRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.MemberService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends Message implements MemberService, CRUD<MemberDTO, BaseResponse> {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TripRepository tripRepository;

  /**
   * Add member to trip.
   *
   * @param request MemberDTO
   * @return BaseResponse
   * */
  @Override
  public BaseResponse create(MemberDTO request) {
    checkInputParams(request);
    memberRepository.save(convertEntity(request));
    return new BaseResponse(CodeConstant.RESPONSE_SUCCESS, Boolean.TRUE, getMessage("CreateSuccess"));
  }

  @Override
  public BaseResponse read(MemberDTO request) {
    return null;
  }

  @Override
  public BaseResponse update(MemberDTO request) {
    return null;
  }

  @Override
  public BaseResponse delete(MemberDTO request) {
    return null;
  }

  @Override
  public List<MemberDTO> getMemberByTravelDetailId(String idTravelDetail) {

    return null;
  }

  private Member convertEntity(MemberDTO request) {
      if (Objects.isNull(request)) {
        throw new BadRequestException(getMessage("RequestFail"));
      }
      Member member = new Member();
      Optional<Trip> trip = tripRepository.findById(request.getIdTrip());
      if (trip.isEmpty()) {
        throw new BadRequestException(getMessage("TripNotExist"));
      }
      member.setId(request.getId());
      member.setIdUser(request.getIdUser());
      member.setTrip(trip.get());
      member.setRole(request.getRole());
      return member;
  }

  private void checkInputParams(MemberDTO memberDTO) {
    if (Objects.isNull(memberDTO) || Objects.isNull(memberDTO.getIdTrip()) || Objects.isNull(memberDTO.getRole())) {
      throw new BadRequestException(getMessage("RequestFail"));
    }
  }


}
