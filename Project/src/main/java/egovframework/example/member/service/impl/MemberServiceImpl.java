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
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {
   
    @Autowired
    private MemberMapper memberMapper;
    
    // 카카오 탈퇴 
    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;
    
    private final RestTemplate restTemplate = new RestTemplate();

    // 회원가입
    @Override
    public void register(MemberVO memberVO) throws Exception {
        // 1) ID 중복 확인
        MemberVO existing = memberMapper.authenticate(memberVO);
        if (existing != null) {
            throw processException("errors.register"); // 중복 ID
        }

        // 2) 비밀번호 처리
        if (memberVO.getPassword() == null || memberVO.getPassword().equals("kakao_dummy")) {
            // 👉 카카오 회원가입: 임시 패스워드 부여 + TEMP_PASSWORD_YN = Y
            String tempPassword = UUID.randomUUID().toString().substring(0, 8);
            String hashed = BCrypt.hashpw(tempPassword, BCrypt.gensalt());
            memberVO.setPassword(hashed);
            memberVO.setTempPasswordYn("Y"); // 임시 비밀번호 여부 기록
        } else {
            // 👉 일반 회원가입: 입력한 비밀번호 해시 처리
            String hashed = BCrypt.hashpw(memberVO.getPassword(), BCrypt.gensalt());
            memberVO.setPassword(hashed);
            memberVO.setTempPasswordYn("N");
        }

        // 3) DB 저장
        memberMapper.register(memberVO);
    }

    // 로그인
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

    // 닉네임 중복 검사
    @Override
    public boolean isNicknameAvailable(String nickname) {
        return memberMapper.countByNickname(nickname) == 0;
    }
    
    // 현재 로그인한 사용자 정보를 세션에서 가져오기
    @Override
    public boolean isNicknameAvailable(String nickname, Long currentMemberIdx) {
        return memberMapper.countNicknameExcludingSelf(nickname, currentMemberIdx) == 0;
    }
    
    // 아이디 중복 검사
    @Override
    public boolean isIdAvailable(String id) {
        return memberMapper.countById(id) == 0;
    }
    
//  인증 이메일 중복 검사
    @Override
    public boolean isEmailRegistered(String email) {
        return memberMapper.countByEmail(email) > 0;
    }
    
//  회원 정보 조회
    @Override
    public MemberVO selectMemberByIdx(Long memberIdx) {
        return memberMapper.selectMemberByIdx(memberIdx);
    }
    
//  회원 정보 수정   
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
            memberVO.setPassword(currentPassword); // ✅ 재선언 안함!
            memberVO.setTempPasswordYn(currentTempPasswordYn);
        }

        memberMapper.updateMember(memberVO);
    }
    
//  아이디 찾기
    @Override
    public String findIdByEmail(String email) {
        return memberMapper.findIdByEmail(email);
    }

    @Override
    public MemberVO findByIdAndEmail(String id, String email) {
        return memberMapper.findByIdAndEmail(id, email);
    }

    @Override
    public int updatePassword(MemberVO member) {
        return memberMapper.updatePassword(member);
    }
    
    @Override
    public void updateProfileImage(Long memberId, String profileUrl) {
        memberMapper.updateProfileImage(memberId, profileUrl);
    }
    
    @Override
    public void deleteMember(Long memberIdx) throws Exception {
        memberMapper.deleteMember(memberIdx);
    }    
    
    @Override
    public MemberVO selectByKakaoId(Long kakaoId) {
        return memberMapper.selectByKakaoId(kakaoId);
    }
    
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
    
    @Override
    public boolean isNicknameDuplicate(String nickname) {
        return memberMapper.countByNickname(nickname) > 0;
    }
    
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
