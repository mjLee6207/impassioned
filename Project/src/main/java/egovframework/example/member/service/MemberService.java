package egovframework.example.member.service;

public interface MemberService {
	
    // 회원가입
    void register(MemberVO memberVO) throws Exception ;

    // 로그인
    MemberVO authenticate(MemberVO memberVO) throws Exception;

    // 닉네임 중복 검사
    boolean isNicknameAvailable(String nickname);
    
    // 아이디 중복확인용 메서드
    boolean isIdAvailable(String id);

//  회원 정보 조회
    MemberVO selectMemberByIdx(Long memberIdx);
    
//  회원 정보 수정  
    void updateMember(MemberVO memberVO);

}
