package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.ExceptionHandler.BadRequestException;
import com.vocation.travel.config.ExceptionHandler.SystemErrorException;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.InformationDTO;
import com.vocation.travel.dto.MemberDTO;
import com.vocation.travel.entity.Member;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.InformationRepository;
import com.vocation.travel.repository.MemberRepository;
import com.vocation.travel.repository.TripRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.MemberService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends Message implements MemberService, CRUD<MemberDTO, BaseResponse> {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TripRepository tripRepository;

  @Autowired
  private InformationRepository infoRepository;

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
    return new BaseResponse(CommonConstant.RESPONSE_SUCCESS, Boolean.TRUE, getMessage("GetMemberSuccess"));
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

  /**
   * Get member by travel id.
   *
   * @param idMember String
   * @param idTravel String
   * @return List<MemberDTO>
   * */
  @Override
  public List<MemberDTO> getMemberByTravelId(String idMember, String idTravel) {
    if (checkMemberInTrip(idMember, idTravel)) {
      throw new SystemErrorException(getMessage("GetMemberFail"));
    }
    List<MemberDTO> list = memberRepository.getMemberByTravelDetailId(idTravel);

    //set information user
    for (MemberDTO memberDto: list) {
      InformationDTO infoDto = infoRepository.getInformationByUserId(memberDto.getIdUser());
      memberDto.setInformationDTO(infoDto);
    }
    return list;
  }

  private Member convertEntity(MemberDTO request) {
      Member member = new Member();
      member.setId(request.getId());
      member.setIdUser(request.getIdUser());
      member.setTrip(request.getTrip());
      member.setRole(request.getRole());
      return member;
  }

  private void checkInputParams(MemberDTO memberDTO) {
      if (Objects.isNull(memberDTO) || Objects.isNull(memberDTO.getRole())
      || Objects.isNull(memberDTO.getIdTrip()) || Objects.isNull(memberDTO.getIdUser())) {
        throw new BadRequestException(getMessage("RequestFail"));
      }
  }

  private boolean checkMemberInTrip(String memberId, String idTravel) {
    try {
      return memberRepository.getMemberInTrip(memberId, idTravel) > 0;
    } catch (Exception e) {
      throw new SystemErrorException(getMessage("SystemErr"));
    }
  }
}
