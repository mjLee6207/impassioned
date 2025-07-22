package egovframework.example.member.service.impl;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.member.service.MemberVO;

@Mapper
public interface MemberMapper {

    // 회원가입 처리
    void register(MemberVO memberVO);

    // 로그인 시 ID 확인
    MemberVO authenticate(MemberVO memberVO);

    // 닉네임 중복 체크
    int countByNickname(String nickname);
    
    // 현재 로그인한 사용자 정보를 세션에서 가져오기
    int countNicknameExcludingSelf(@Param("nickname") String nickname, @Param("currentMemberIdx") Long currentMemberIdx);
    
    // 아이디 중복 체크    
    int countById(String id);
    
//  인증 이메일 중복 체크
    int countByEmail(String email);
    
//  회원 정보 조회
    MemberVO selectMemberByIdx(Long memberIdx);   
    
//  회원 정보 수정     
    void updateMember(MemberVO memberVO);

//  정보수정할때 비밀번호 안바꿔도 그대로 적용
    String selectPasswordByIdx(Long memberIdx);
    
//  아이디 찾기
    String findIdByEmail(String email);
    
//  비밀번호 찾기 시 사용자 존재 여부를 확인
    MemberVO findByIdAndEmail(@Param("id") String id, @Param("email") String email);
    
//  비밀번호 찾기
    int updatePassword(MemberVO member);
// 프로필 이미지 경로를 갱신하는 메소드
    void updateProfileImage(@Param("memberId") Long memberId, @Param("profileUrl") String profileUrl);

//  회원 탈퇴 메소드
    void deleteMember(Long memberIdx);
    
//  해당 회원이 임시 비밀번호 상태인지 아닌지를 확인하기 위해 DB에 저장된 값을 가져오는 용도.
    String selectTempPasswordYnByIdx(Long memberIdx);
    
    // 카카오 ID로 회원 조회
    MemberVO selectByKakaoId(Long kakaoId);

//  카카오 회원 자동 등록
    void insertKakaoMember(MemberVO memberVO);
}
