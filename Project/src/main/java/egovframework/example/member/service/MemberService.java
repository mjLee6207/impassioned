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
    
    // 현재 로그인한 사용자 정보를 세션에서 가져오기
    boolean isNicknameAvailable(String nickname, Long currentMemberIdx);
    
//  인증 이메일 중복확인용 메서드
	boolean isEmailRegistered(String email);

//  회원 정보 조회
    MemberVO selectMemberByIdx(Long memberIdx);
    
//  회원 정보 수정  
    void updateMember(MemberVO memberVO);
    
//  아이디 찾기
    String findIdByEmail(String email);
    
//  비밀번호 찾기 시 사용자 존재 여부를 확인
    MemberVO findByIdAndEmail(String id, String email);
    
//  비밀번호찾기
    int updatePassword(MemberVO member);
    
//  프로필 이미지 경로를 갱신하는 메소드
    void updateProfileImage(Long memberId, String profileUrl);
    
//  회원 탈퇴 메소드    
    void deleteMember(Long memberIdx) throws Exception;
    
//  카카오 ID로 회원 조회 (카카오 로그인용)
    MemberVO selectByKakaoId(Long kakaoId);
    
 // 카카오 자동가입 (신규 회원 등록용)
    void insertKakaoMember(MemberVO memberVO);
    
//  카카오 닉네임 중복 확인
    boolean isNicknameDuplicate(String nickname);
    
//  카카오 회원 탈퇴
    void unlinkKakaoUser(Long kakaoId) throws Exception;
}
