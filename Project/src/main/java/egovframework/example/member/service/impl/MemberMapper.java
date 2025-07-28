package egovframework.example.member.service.impl;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.member.service.MemberVO;

@Mapper
public interface MemberMapper {

//    회원가입 처리
    void register(MemberVO memberVO);

//    로그인 시 ID/PW 일치 여부 확인
    MemberVO authenticate(MemberVO memberVO);

//    닉네임 중복 체크 (전체 대상)
    int countByNickname(String nickname);

//    닉네임 중복 체크 (자기 자신 제외) 
    int countNicknameExcludingSelf(@Param("nickname") String nickname, @Param("currentMemberIdx") Long currentMemberIdx);

//    아이디(ID) 중복 여부 확인 
    int countById(String id);

//    이메일 중복 여부 확인
    int countByEmail(String email);

//    회원 정보 조회 (회원번호 기준)
    MemberVO selectMemberByIdx(Long memberIdx);

//    회원 정보 수정 (이메일, 닉네임, 비밀번호 등)
    void updateMember(MemberVO memberVO);

//    비밀번호 조회 (정보 수정 시 기존 비밀번호 유지용)
    String selectPasswordByIdx(Long memberIdx);
    
//    이메일을 통한 아이디 찾기
    String findIdByEmail(String email);

//    아이디 + 이메일로 회원 조회 (비밀번호 찾기 시 사용)
    MemberVO findByIdAndEmail(@Param("id") String id, @Param("email") String email);

//    비밀번호 업데이트 (임시 비밀번호 포함)
    int updatePassword(MemberVO member);

//    프로필 이미지 경로 업데이트
    void updateProfileImage(@Param("memberId") Long memberId, @Param("profileUrl") String profileUrl);

//    회원 탈퇴 처리 (소프트 삭제)
    void softDeleteMember(Long memberIdx);

//    임시 비밀번호 여부 조회
    String selectTempPasswordYnByIdx(Long memberIdx);

//    카카오 ID로 회원 조회
    MemberVO selectByKakaoId(Long kakaoId);

//    카카오 회원 자동 등록
    void insertKakaoMember(MemberVO memberVO);
}
