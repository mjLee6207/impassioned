package egovframework.example.member.service;

import java.util.Date;

import egovframework.example.common.Criteria;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class MemberVO extends Criteria {
    private Long memberIdx;     // 회원 고유번호 (PK)
    private String id;          // 사용자 ID (로그인용)
    private String password;    // 비밀번호
    private String email;       // 이메일 주소
    private String role;        // 사용자 권한
    private String nickname;    // 닉네임 (DB 컬럼 추가됨)
    private Date joinDate;      // 가입일
    private String profile;     // 프로필 이미지 파일 경로

    // 프론트 처리용 입력값
    private String emailCode;   // 이메일 인증 코드 (입력값)
    
    // 임시 비밀번호 관련 필드
    private String tempPasswordYn;   // DB 저장용(Y,N)
    private boolean tempPassword;    // 임시 비밀번호 여부 (컨트롤러 판단용)
    
//  카카오 로그인
    private Long kakaoId;
} 
