package egovframework.example.member.service.impl;

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
    
    // 아이디 중복 체크    
    int countById(String id);
    
//  회원 정보 조회
    MemberVO selectMemberByIdx(Long memberIdx);   
    
//  회원 정보 수정     
    void updateMember(MemberVO memberVO);
}
