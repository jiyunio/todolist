//package com.jiyunio.todolist;
//
//import com.jiyunio.todolist.member.Member;
//import com.jiyunio.todolist.member.MemberRepository;
//import com.jiyunio.todolist.member.MemberService;
//import com.jiyunio.todolist.member.dto.SignInDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//class TodolistApplicationTests {
//	MemberService memberService;
//
//	@MockBean
//	MemberRepository memberRepository;
//
//	@BeforeEach
//	void setUpTeat() {
//		// 가짜 객체 주입
//		memberService = new MemberService(memberRepository);
//	}
//	@DisplayName("회원 로그인 test")
//	@Test
//	void SignInMemberTest() {
//		// given
//        Member member = Member.builder()
//                .userId("jiyun")
//                .userPw("qwe123!")
//                .build();
//        Mockito.when(memberRepository.save(member)).thenReturn(member);
//
//		// when
//		SignInDTO signInDTO = new SignInDTO();
//		signInDTO.setUserId("jiyun");
//		signInDTO.setUserPw("qwe123!");
//
//		String userId = memberService.signIn(signInDTO);
//
//		// then
//		assertThat(userId).isEqualTo("jiyun");
//	}
//
//}
