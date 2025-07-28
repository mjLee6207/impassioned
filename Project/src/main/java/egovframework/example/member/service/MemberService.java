package egovframework.example.member.service;

public interface MemberService {
	
//  회원가입 처리 (일반 및 카카오 회원)
    void register(MemberVO memberVO) throws Exception;

//  로그인 인증 처리 (ID/PW 비교)
    MemberVO authenticate(MemberVO memberVO) throws Exception;

//  닉네임 중복 검사 (회원가입 시)
    boolean isNicknameAvailable(String nickname);

//  닉네임 중복 검사 (회원정보 수정 시 본인 제외)
    boolean isNicknameAvailable(String nickname, Long currentMemberIdx);

//  아이디(ID) 중복 확인
    boolean isIdAvailable(String id);

//  이메일 등록 여부 확인 (이메일 인증, 찾기 등)
    boolean isEmailRegistered(String email);

//  회원 정보 조회 (회원번호 기준)
    MemberVO selectMemberByIdx(Long memberIdx);

//  회원 정보 수정 (비밀번호 포함)
    void updateMember(MemberVO memberVO);

//  이메일로 아이디 찾기
    String findIdByEmail(String email);

//  아이디 + 이메일로 회원 조회 (비밀번호 찾기)
    MemberVO findByIdAndEmail(String id, String email);

//  비밀번호 변경 처리 (임시 비밀번호 포함)
    int updatePassword(MemberVO member);

//  프로필 이미지 경로 업데이트
    void updateProfileImage(Long memberId, String profileUrl);

//  회원 탈퇴 처리 (소프트 삭제 방식)
    void softDeleteMember(Long memberIdx);

//  카카오 ID로 회원 조회 (카카오 로그인 연동)
    MemberVO selectByKakaoId(Long kakaoId);

//  카카오 자동가입 처리 (신규 가입)
    void insertKakaoMember(MemberVO memberVO);

//  닉네임 중복 여부 확인 (카카오 회원 자동가입 시 사용)
    boolean isNicknameDuplicate(String nickname);

//  카카오 연결 해제 요청 (회원 탈퇴 시 사용)
    void unlinkKakaoUser(Long kakaoId) throws Exception;}
