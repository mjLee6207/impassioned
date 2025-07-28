package egovframework.example.member.service.impl;


import java.util.UUID;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;
@Service
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {
   
    @Autowired
    private MemberMapper memberMapper;
    
//  카카오 탈퇴 Admin Key (application.properties에서 주입)
    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;
    
    private final RestTemplate restTemplate = new RestTemplate();

//  회원가입 처리
//  - 중복 ID 확인
//  - 비밀번호 해싱 (카카오/일반 회원 구분)
//  - DB 저장
    @Override
    public void register(MemberVO memberVO) throws Exception {
        // 1) ID 중복 확인
        MemberVO existing = memberMapper.authenticate(memberVO);
        if (existing != null) {
            throw processException("errors.register"); // 중복 ID
        }

        // 2) 비밀번호 처리
        if (memberVO.getPassword() == null || memberVO.getPassword().equals("kakao_dummy")) {
            // 카카오 회원가입: 임시 패스워드 부여 + TEMP_PASSWORD_YN = Y
            String tempPassword = UUID.randomUUID().toString().substring(0, 8);
            String hashed = BCrypt.hashpw(tempPassword, BCrypt.gensalt());
            memberVO.setPassword(hashed);
            memberVO.setTempPasswordYn("Y"); // 임시 비밀번호 여부 기록
        } else {
            // 일반 회원가입: 입력한 비밀번호 해시 처리
            String hashed = BCrypt.hashpw(memberVO.getPassword(), BCrypt.gensalt());
            memberVO.setPassword(hashed);
            memberVO.setTempPasswordYn("N");
        }

        // 3) DB 저장
        memberMapper.register(memberVO);
    }

 //  로그인 처리
 //  - 사용자 조회
 //  - 비밀번호 검증
    @Override
    public MemberVO authenticate(MemberVO loginVO) throws Exception {
        // 1) 사용자 조회
        MemberVO memberVO = memberMapper.authenticate(loginVO);
        if (memberVO == null) {
            throw processException("errors.login"); // ID 없음
        }

        // 2) 비밀번호 비교
        boolean matched = BCrypt.checkpw(loginVO.getPassword(), memberVO.getPassword());
        if (!matched) {
            throw processException("errors.login"); // 비밀번호 틀림
        }

        return memberVO;
    }

//  닉네임 중복 검사(전체)
    @Override
    public boolean isNicknameAvailable(String nickname) {
        return memberMapper.countByNickname(nickname) == 0;
    }
    
//  회원 정보 수정 시 닉네임 중복 검사
    @Override
    public boolean isNicknameAvailable(String nickname, Long currentMemberIdx) {
        return memberMapper.countNicknameExcludingSelf(nickname, currentMemberIdx) == 0;
    }
    
//  아이디 중복 검사
    @Override
    public boolean isIdAvailable(String id) {
        return memberMapper.countById(id) == 0;
    }
    
//  이메일 등록 여부 확인 (이메일 인증에 사용)
    @Override
    public boolean isEmailRegistered(String email) {
        return memberMapper.countByEmail(email) > 0;
    }
    
//  회원 정보 조회(회원번호 기준)
    @Override
    public MemberVO selectMemberByIdx(Long memberIdx) {
        return memberMapper.selectMemberByIdx(memberIdx);
    }
    
//  회원 정보 수정(비밀번호 입력 유무에 따라 조건 처리)
    @Override
    public void updateMember(MemberVO memberVO) {
        // 기존 비밀번호 및 TEMP_PASSWORD_YN 값 가져오기
        String currentPassword = memberMapper.selectPasswordByIdx(memberVO.getMemberIdx());
        String currentTempPasswordYn = memberMapper.selectTempPasswordYnByIdx(memberVO.getMemberIdx());

        if (memberVO.getPassword() != null && !memberVO.getPassword().isEmpty()) {
            // 비밀번호 변경 시: 암호화 + 임시비밀번호 해제
            String encrypted = BCrypt.hashpw(memberVO.getPassword(), BCrypt.gensalt());
            memberVO.setPassword(encrypted);
            memberVO.setTempPasswordYn("N");
        } else {
            // 비밀번호 미입력 시: 기존 비밀번호 및 상태 유지
            memberVO.setPassword(currentPassword);
            memberVO.setTempPasswordYn(currentTempPasswordYn);
        }

        memberMapper.updateMember(memberVO);
    }
    
//  인증 이메일로 아이디 찾기
    @Override
    public String findIdByEmail(String email) {
        return memberMapper.findIdByEmail(email);
    }
    
//  ID + 이메일로 회원 조회 (비밀번호 찾기 시 사용)
    @Override
    public MemberVO findByIdAndEmail(String id, String email) {
        return memberMapper.findByIdAndEmail(id, email);
    }
    
//  비밀번호 업데이트 (임시 비밀번호 포함)
    @Override
    public int updatePassword(MemberVO member) {
        return memberMapper.updatePassword(member);
    }
    
//  프로필 이미지 변경
    @Override
    public void updateProfileImage(Long memberId, String profileUrl) {
        memberMapper.updateProfileImage(memberId, profileUrl);
    }
    
//  회원 탈퇴 처리 (소프트 탈퇴)    
    @Override
    public void softDeleteMember(Long memberIdx) {
        memberMapper.softDeleteMember(memberIdx);
    }   
    
//  카카오 ID로 회원 조회    
    @Override
    public MemberVO selectByKakaoId(Long kakaoId) {
        return memberMapper.selectByKakaoId(kakaoId);
    }
    
//  카카오 회원을 우리 회원가입 로직에 맞춰 자동 등록
    @Override
    public void insertKakaoMember(MemberVO memberVO) {
        memberVO.setRole("USER");

        // 임시 비밀번호 생성
        String dummyPassword = UUID.randomUUID().toString().substring(0, 8);
        String encrypted = BCrypt.hashpw(dummyPassword, BCrypt.gensalt());
        memberVO.setPassword(encrypted);
        memberVO.setTempPasswordYn("N");

        memberMapper.insertKakaoMember(memberVO);
    }
    
// 닉네임 중복 여부 확인(카카오 자동 가입 시 사용)    
    @Override
    public boolean isNicknameDuplicate(String nickname) {
        return memberMapper.countByNickname(nickname) > 0;
    }
    
//  카카오 사용자 연결 해제 요청 (카카오 API 호출) - 카카오 탈퇴
    @Override
    public void unlinkKakaoUser(Long kakaoId) throws Exception {
        String url = "https://kapi.kakao.com/v1/user/unlink";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoAdminKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", kakaoId.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("카카오 unlink 요청 실패: " + response.getBody());
        }
    }

} 
