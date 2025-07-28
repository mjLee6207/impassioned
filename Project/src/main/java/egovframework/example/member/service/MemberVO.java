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
//  DB 기본 회원 정보
    private Long memberIdx;      // 회원 고유번호 (PK)
    private String id;           // 로그인 ID
    private String password;     // 비밀번호 (BCrypt 해시 저장)
    private String email;        // 이메일 주소
    private String role;         // 사용자 권한 (예: USER, ADMIN)
    private String nickname;     // 닉네임
    private Date joinDate;       // 가입일
    private String profile;      // 프로필 이미지 경로

//  프론트 입력/처리용
    private String emailCode;    // 이메일 인증 코드 입력값

//  임시 비밀번호 관련
    private String tempPasswordYn;   // 임시 비밀번호 여부 (DB 저장: 'Y' or 'N')
    private boolean tempPassword;    // 임시 비밀번호 상태 여부 (Java 로직에서 판단)

//  카카오 연동 관련
    private Long kakaoId;        // 카카오 회원 ID (연동 로그인용)
} 
