# 트래블 인천

* 🔗 링크 : http://www.travelincheon.com

* 🔊 프로젝트 소개
  * (초기 개발) 인천의 관광지를 지도 위에 마크로 표시하여 클릭 시 해당 관광지를 소개하는 웹페이지형 프로젝트입니다.
  * (리뉴얼 후 추가) 기존 관광지 소개 내용이 VR 포트폴리오 소개로 변경 되고, VR 제작의뢰, 여행자보험 문의를 할 수 있습니다.
  * 디자인과 퍼블리싱을 제외한 기존 DB, 백엔드, 프론트엔드까지 모두 담당했습니다.
  * 백엔드는 Restful-API를 개발하고, 프론트엔드는 Ajax(jQuery)로 API를 호출하는 방식으로 개발되었습니다.

* 🏗️개발 기간 및 인원 
  * 2022.02 ~ 2022.04 (1차 개발)
  * 풀스택 1명, 프론트엔드 1명 총 2명 중 풀스택 담당
  * 2022.12 ~ 2023.02 (리뉴얼)
  * 풀스택 1명, 디자인 퍼블리싱 1명 총 2명 중 풀스택 담당
  
* 🛠️사용 기술
  * Back-End: Spring Boot, Java, JPA, MySql, QueryDsl
  * Front-End: JavaScript, jQuery
 
* 💡부가기능
  * ckEditor를 이용한 코스 상세 정보 게시판형으로 작성 후 저장
  * MySql5.7 이상 버전으로 JSON COLUMN 사용하여 데이터 저장
  * Spring Security와 JWT 방식의 로그인 기능 구현
  * 기존의 사용중인 File 업로드 알고리즘 로직 변경

* 📅 DB
  * ERD
![travel_incheon_erd](https://github.com/maroowj/travelIncheon/assets/77284101/8689c3e2-2c5c-43ba-ab94-d62d8ab89427)


* ✏️ 시나리오
  * 관리자
    * (리뉴얼 전) 소개하려는 관광지의 업종을 1차 분류로 등록하고, 관광지의 세부 정보를 2차분류로 등록 수정 할 수 있습니다.
    * (리뉴얼 후) 기존의 관광지 정보가 VR 포트폴리오 정보로 변경되었습니다. 
    * (리뉴얼 후)사용자의 문의 내역 (VR제작의뢰, 여행자보험)을 확인하고 처리 할 수 있습니다.
    * 사용자 페이지에 노출되는 배너를 등록 수정 할 수 있습니다.
    * 전체 회원을 관리합니다.
        
  * 사용자
    * (리뉴얼 전) 지도 위에 마크를 클릭하여 해당 위치에 있는 관광지에 대한 안내를 볼 수 있습니다.
    * (리뉴얼 후) 관리자가 등록한 VR 포트폴리오를 클릭 하여 확인 할 수 있습니다.
    * (리뉴얼 후) VR제작의뢰, 여행자보험에 관한 문의를 할 수 있습니다.  
    
   
* 💻구동 화면
  * 사용자
![user_index](https://github.com/maroowj/travelIncheon/assets/77284101/4cbc4e86-a1d9-407a-9d75-5c990b5f8101)
![user_insurance](https://github.com/maroowj/travelIncheon/assets/77284101/d05e888e-7b35-435b-9c92-1f5fde38cbf3)

  * 관리자
![admin_login](https://github.com/maroowj/travelIncheon/assets/77284101/0e20ebc3-b631-402f-9aea-8d54ff67f384)
![admin_vrList](https://github.com/maroowj/travelIncheon/assets/77284101/f7939bd9-3b53-4015-88a3-ad964b751928)
![admin_vr_update](https://github.com/maroowj/travelIncheon/assets/77284101/b5867600-bd58-4853-92fd-a7dbc9932207)
![admin_banner](https://github.com/maroowj/travelIncheon/assets/77284101/67d9a549-04a2-48b2-a762-56c0e62bda84)

