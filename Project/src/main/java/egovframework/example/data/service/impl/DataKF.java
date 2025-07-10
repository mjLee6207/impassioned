//package egovframework.example.data.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import egovframework.example.data.service.DataVO;
//import egovframework.example.data.service.mapper.DataMapper;
//import egovframework.example.data.util.ApiKoreanParser; // ← 실 API 파서 클래스 이름에 맞게 수정
//
//@Component
//public class DataKF {
//
//    @Autowired
//    private DataMapper dataMapper;
//
//    @Autowired
//    private ApiKoreanParser parser;  // ← 실제 구현한 파싱 클래스 이름과 메소드명 확인 필요
//
//    public void execute() {
//        try {
//            // 1. 한식 API 호출 및 파싱
//            List<DataVO> dataList = parser.get();
//
//            // 2. DB 저장
//            dataMapper.insertRecipeList(dataList);
//            System.out.println("✅ 한식 레시피 저장 완료: " + dataList.size() + "건");
//
//        } catch (Exception e) {
//            System.err.println("❌ 한식 API → DB 저장 중 오류 발생: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}