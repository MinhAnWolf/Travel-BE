package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
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

  private final String SERVICE_NAME = "MemberService";

  /**
   * Add member to trip.
   *
   * @param request MemberDTO
   * @return BaseResponse
   * */
  @Override
  public BaseResponse create(MemberDTO request) {
    try {
      Log.startLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
      Log.inputLog(request);
      checkInputParams(request);
      memberRepository.save(convertEntity(request));
      BaseResponse baseResponse = new BaseResponse(CommonConstant.RESPONSE_SUCCESS,
              Boolean.TRUE, getMessage("GetMemberSuccess"));
      Log.outputLog(baseResponse);
      Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
      return baseResponse;
    } catch (Exception e) {
      Log.errorLog(e);
      Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
      throw new SystemErrorException(getMessage("SystemErr"));
    }
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
    Log.startLog(SERVICE_NAME, CommonConstant.METHOD_DELETE);
    Log.inputLog(request);
    memberRepository.delete(convertEntity(request));
    BaseResponse baseResponse = new BaseResponse(CommonConstant.RESPONSE_SUCCESS, Boolean.TRUE, getMessage("DeleteSuccess"));
    Log.outputLog(baseResponse);
    Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
    return baseResponse;
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
    String METHOD_NAME = "getMemberByTravelId";
    Log.startLog(SERVICE_NAME, METHOD_NAME);
    Log.inputLog(idMember);
    Log.inputLog(idTravel);
    if (checkMemberInTrip(idMember, idTravel)) {
      throw new SystemErrorException(getMessage("GetMemberFail"));
    }
    List<MemberDTO> list = memberRepository.getMemberByTravelDetailId(idTravel);

    //set information user
    for (MemberDTO memberDto: list) {
      InformationDTO infoDto = infoRepository.getInformationByUserId(memberDto.getIdUser());
      memberDto.setInformationDTO(infoDto);
    }
    Log.outputLog(list);
    Log.endLog(SERVICE_NAME, METHOD_NAME);
    return list;
  }

  /**
   * Check user in trip.
   *
   * @param userId String
   * @param idTravel String
   * @return boolean
   * */
  @Override
  public boolean checkUserInTravel(String userId, String idTravel) {
    String METHOD_NAME = "checkUserInTravel";
    Log.startLog(SERVICE_NAME, METHOD_NAME);
    Log.inputLog(userId);
    Log.inputLog(idTravel);
    try {
      return memberRepository.checkUserIdInTrip(userId, idTravel) > 0;
    } catch (Exception e) {
      Log.errorLog(e);
      Log.endLog(SERVICE_NAME, METHOD_NAME);
      throw new SystemErrorException(getMessage("SystemErr"));
    }
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
          || Objects.isNull(memberDTO.getIdUser())) {
        throw new BadRequestException(getMessage("RequestFail"));
      }
  }

  private boolean checkMemberInTrip(String memberId, String idTravel) {
    try {
      return memberRepository.checkMemberInTrip(memberId, idTravel) > 0;
    } catch (Exception e) {
      throw new SystemErrorException(getMessage("SystemErr"));
    }
  }
}
