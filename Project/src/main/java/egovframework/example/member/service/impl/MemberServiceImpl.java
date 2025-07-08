package egovframework.example.member.service.impl;

import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;
import lombok.extern.log4j.Log4j2;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    // 회원가입
    @Override
    public void register(MemberVO memberVO) throws Exception {
        // 1) ID 중복 확인
        MemberVO existing = memberMapper.authenticate(memberVO);
        if (existing != null) {
            throw processException("errors.register"); // 중복 ID
        }

        // 2) 비밀번호 해싱
        String hashedPassword = BCrypt.hashpw(memberVO.getPassword(), BCrypt.gensalt());
        memberVO.setPassword(hashedPassword);

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
    
    // 아이디 중복 검사
    @Override
    public boolean isIdAvailable(String id) {
        return memberMapper.countById(id) == 0;
    }
<<<<<<< Updated upstream
=======
    
//  회원 정보 조회
    @Override
    public MemberVO selectMemberByIdx(Long memberIdx) {
        return memberMapper.selectMemberByIdx(memberIdx);
    }
    
//  회원 정보 수정   
    @Override
    public void updateMember(MemberVO memberVO) {
        log.info("⛳ 입력된 비밀번호: " + memberVO.getPassword());

        if (memberVO.getPassword() != null && !memberVO.getPassword().isEmpty()) {
        	log.info("1");
            String encrypted = BCrypt.hashpw(memberVO.getPassword(), BCrypt.gensalt());
            memberVO.setPassword(encrypted);
        } else {
        	log.info("2");
            String currentPassword = memberMapper.selectPasswordByIdx(memberVO.getMemberIdx());            
            memberVO.setPassword(currentPassword);
        }

        memberMapper.updateMember(memberVO);
    }
>>>>>>> Stashed changes
} 
