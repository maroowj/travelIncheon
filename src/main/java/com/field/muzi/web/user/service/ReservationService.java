package com.field.muzi.web.user.service;

import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.domain.entity.ReservationEntity;
import com.field.muzi.domain.entity.SecondCourseEntity;
import com.field.muzi.repository.FirstCourseRepository;
import com.field.muzi.repository.ReservationRepository;
import com.field.muzi.repository.SecondCourseRepository;
import com.field.muzi.web.common.service.MailService;
import com.field.muzi.web.user.dto.reservation.*;
import lombok.Data;
import org.json.simple.parser.JSONParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final FirstCourseRepository firstCourseRepository;
    private final SecondCourseRepository secondCourseRepository;
    private final ReservationRepository reservationRepository;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    /** 예약 가능 여부 체크 **/
    @Transactional
    public int reservationCheck(ReservationCheckRequest request) {
        int result = 0;
        boolean chkFirstBookingDay = false;
        boolean chkSecondBookingDay = false;
        boolean chkReservation = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTime = sdf.format(request.getReservationDate()).substring(11, 19);
        List<String> firstDays = null;

        FirstCourseEntity firstCourse = firstCourseRepository.findById(request.getFirstCourseSeq()).orElseThrow(() -> new RuntimeException("1차코스 정보를 찾을 수 없습니다."));
        firstDays = firstCourse.getReservationDay();


        // 1차코스 예약 가능 요일 확인
        for(String day:firstDays) {
            if(day.equals(request.getReservationDay())){
                chkFirstBookingDay = true;
            }
        }

        Date startDate = firstCourse.getStartDate();
        Date endDate = firstCourse.getEndDate();
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        Date queryDate = request.getReservationDate();
        int compare = startDate.compareTo(queryDate);
        int compare2 = queryDate.compareTo(endDate);
        if(compare>0) {
            chkFirstBookingDay = false;
        }else if(compare2>0) {
            chkFirstBookingDay = false;
        }



//        System.out.println(">>>"+ request.getSecondCourseSeq());

        if(request.getSecondCourseSeq()!=null && request.getSecondCourseSeq().size() > 0) {
            SecondCourseEntity secondCourse = null;
            for(String secondCourseSeq:request.getSecondCourseSeq()) {
                List<String> secondDays = null;
                secondCourse = secondCourseRepository.findById(secondCourseSeq).orElseThrow(() -> new RuntimeException("2차코스 정보를 찾을 수 없습니다."));
                secondDays = secondCourse.getReservationDay();

                // 2차코스 예약 가능 요일 확인
                if(secondCourse != null) {
                    for(String day:secondDays) {
                        if(day.equals(request.getReservationDay())) {
                            chkSecondBookingDay = true;
                            break;
                        }else {
                            chkSecondBookingDay = false;
                        }
                    }
                }


                Date secondStartDate = secondCourse.getStartDate();
                Date secondEndDate = secondCourse.getEndDate();
                secondEndDate.setHours(23);
                secondEndDate.setMinutes(59);
                secondEndDate.setSeconds(59);
                int secondCompare = secondStartDate.compareTo(queryDate);
                int secondCompare2 = queryDate.compareTo(secondEndDate);
                if(secondCompare>0) {
                    chkSecondBookingDay = false;
                }else if(secondCompare2>0) {
                    chkSecondBookingDay = false;
                }

                if(!chkSecondBookingDay) {
                    break;
                }
            }
        }else {
            chkSecondBookingDay = true;
        }




        // 요청 일자의 예약 내역 확인
        List<String> reservationTimeList = firstCourse.getReservationTime(); // 조회한 1차코스의 예약 가능한 모든 시간들 (같은 시간 중복 허용)
        int availableCnt = 0;
        // 요청한 시간이 예약 가능한 시간 list에 있는지 조회
        for(String reservationTime:reservationTimeList) {
            if(requestTime.equals(reservationTime)){
                availableCnt++; // 요청 시간과 예약 가능 시간이 일치하면 예약 가능 수 ++
            }
        }

        List<ReservationCheckResponse> reservationList = reservationRepository.reservationCheckList(request);

        if(reservationList.size()==0) { // 해당 시간대에 요청한 코스의 예약 내역이 없음
            chkReservation = true;
        }else { // 예약 내역이 있음
            if(reservationList.size() < availableCnt){ // 예약 내역이 있지만 예약 할 수 있는 시간의 자리가 남아 있는 경우
                chkReservation = true;
            }
        }

        if(chkFirstBookingDay && chkSecondBookingDay && chkReservation) {
            result = 0; // 예약 가능함
        }

        if(!chkFirstBookingDay) {
            result = 1; // 1차코스를 예약할 수 있는 날짜가(요일 포함) 아님
        }

        if(chkFirstBookingDay && !chkSecondBookingDay) {
            result = 2; // 1차코스는 예약할 수 있지만, 2차코스를 예약할 수 있는 날짜가(요일 포함) 아님
        }

        if(!chkReservation) {
            result = 3; // 예약 가능한 자리가 없음
        }
        return result;
    }

    /** 예약하기 **/
    @Transactional
    public int reservationInsert(ReservationInsertRequest request) throws Exception {
//        System.out.println(">>>>" + request.toString());
        int result = 0;
        FirstCourseEntity firstCourse = firstCourseRepository.findById(request.getFirstCourseSeq()).orElseThrow(() -> new RuntimeException("1차 코스를 찾을 수 없습니다."));

        JSONParser jsonParser = new JSONParser();
        Map<String, Object> parseTerms1 = new HashMap<>();
        Map<String, Object> parseTerms2 = new HashMap<>();
        Map<String, Object> parseTerms3 = new HashMap<>();
        Map<String, Object> parseTerms4 = new HashMap<>();
        Map<String, Object> parseTerms5 = new HashMap<>();
        if(request.getTerms1()!=null) {
            parseTerms1 = request.getTerms1();
        }else {
            parseTerms1 = null;
        }
        if(request.getTerms2()!=null){
            parseTerms2 = request.getTerms2();
        }else {
            parseTerms2 = null;
        }
        if(request.getTerms3()!=null){
            parseTerms3 =request.getTerms3();
        }else {
            parseTerms3 = null;
        }
        if(request.getTerms4()!=null){
            parseTerms4 =request.getTerms4();
        }else {
            parseTerms4 = null;
        }
        if(request.getTerms5()!=null){
            parseTerms5 = request.getTerms5();
        }else {
            parseTerms5 = null;
        }

        int timeCnt = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTime = sdf.format(request.getReservationDate()).substring(11, 19);
        List<String> reservationTime = firstCourse.getReservationTime();

        for(String time:reservationTime) {
            if(time.equals(requestTime)){
                timeCnt++;
            }
        }

        List<ReservationEntity> reservationList = reservationRepository.findAllByFirstCourseAndReservationDateAndBookingStatusNot(firstCourse, request.getReservationDate(), "예약취소");
//        System.out.println(">>"+reservationList.size());

        if(timeCnt <= reservationList.size()) {
            result = 2; // 해당 시간에 1차코스가 전부 예약 되어 있음 (기예약)
        }else{
            reservationRepository.save(
                    ReservationEntity.builder()
                            .firstCourse(firstCourse)
                            .secondCourse(request.getSecondCourseSeq())
                            .applicant(request.getApplicant())
                            .applicantDetail(request.getApplicantDetail())
                            .guestType(request.getGuestType())
                            .guestNumber(request.getGuestNumber())
                            .managerPhone(request.getManagerPhone())
                            .reservationDate(request.getReservationDate())
                            .cost(request.getCost())
                            .vehicleType(request.getVehicleType())
                            .vehicleNumber(request.getVehicleNumber())
                            .etc(request.getEtc())
                            .bookingStatus("예약대기")
                            .password(passwordEncoder.encode(request.getPassword()))
                            .terms1((HashMap<String, Object>) parseTerms1)
                            .terms2((HashMap<String, Object>) parseTerms2)
                            .terms3((HashMap<String, Object>) parseTerms3)
                            .terms4((HashMap<String, Object>) parseTerms4)
                            .terms5((HashMap<String, Object>) parseTerms5)
                            .email(request.getEmail())
                            .busFare(request.getBusFare())
                            .guideFee(request.getGuideFee())
                            .insuranceFee(request.getInsuranceFee())
                    .build()
            );
            result = 1; // 예약 성공

            mailService.sendReservationMail(request);
        }
        return result;
    }

    /** 예약 상세 보기 **/
    @Transactional
    public Map<String, Object> reservationDetail(ReservationDetailRequest request) {
        Map<String, Object> data = new HashMap<>();

        ReservationEntity reservationEntity = reservationRepository.findById(request.getReservationSeq()).orElseThrow(() -> new RuntimeException("예약 정보를 찾을 수 없습니다."));

        ReservationDetailResponse.Reservation reservation = reservationRepository.userReservationInfo(request);
        data.put("reservationDate", reservation.getReservationDate());
        data.put("applicant", reservation.getApplicant());


        if(passwordEncoder.matches(request.getPassword(), reservationEntity.getPassword())){

            ReservationDetailResponse response = new ReservationDetailResponse();



//            System.out.println(">>>" + reservation.getReservationDate());

            ReservationDetailResponse.FirstCourse firstCourse = firstCourseRepository.reservationFirstCourse(reservation.getFirstCourseSeq());

            List<ReservationDetailResponse.SecondCourse> secondCourseList = new ArrayList<>();
            if(reservation.getSecondCourseSeq()!=null && reservation.getSecondCourseSeq().size()!=0){
                List<String> secondCourseSeqList = reservation.getSecondCourseSeq();

                for(String secondCourseSeq:secondCourseSeqList) {
                    ReservationDetailResponse.SecondCourse secondCourse = secondCourseRepository.reservationSecondCourse(secondCourseSeq);
//                    System.out.println(">>" + secondCourse);
                    secondCourseList.add(secondCourse);
                }
            }

            response.setReservation(reservation);
            response.setFirstCourse(firstCourse);
            response.setSecondCourseList(secondCourseList);

            data.put("result", "success");
            data.put("data", response);
        }else {
            data.put("result", "failed");
            data.put("message", "비밀번호가 일치하지 않습니다.");
        }

        return data;
    }


    /** 예약 달력 리스트 **/
    @Transactional
    public List<Courses> reservationCalendar(ReservationCalendarRequest request) throws ParseException {
        List<Courses> coursesList = new ArrayList<>();
        List<MonthlyDate> monthlyDateList = new ArrayList<>();

        /** 달력 날짜 **/

        //시작 , 끝 날짜 임의 세팅
        String s1=request.getStartDate();
        String s2=request.getEndDate();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //Date타입으로 변경
        Date d1 = df.parse( s1 );
        Date d2 = df.parse( s2 );

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        //Calendar 타입으로 변경 add()메소드로 1일씩 추가해 주기위해 변경
        c1.setTime( d1 );
        c2.setTime( d2 );

        //시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력

        while( c1.compareTo( c2 ) < 0 ){
            String year = String.valueOf(c1.get(Calendar.YEAR));
            String month = String.valueOf(c1.get(Calendar.MONTH)+1);
            if(month.length()==1){
                month = "0"+month;
            }
            String date = String.valueOf(c1.get(Calendar.DATE));
            int day = c1.get(Calendar.DAY_OF_WEEK);
            if(date.length()==1){
                date = "0"+date;
            }
            String strDate = year+"-"+month+"-"+date;

            String strDay="";
            switch (day) {
                case 1:
                    strDay="sun";
                    break;
                case 2:
                    strDay="mon";
                    break;
                case 3:
                    strDay="tue";
                    break;
                case 4:
                    strDay="wed";
                    break;
                case 5:
                    strDay="thu";
                    break;
                case 6:
                    strDay="fri";
                    break;
                case 7:
                    strDay="sat";
                    break;
            }

//            System.out.println(strDate + "/" + strDay);
            MonthlyDate monthlyDate = new MonthlyDate();
            monthlyDate.setDate(strDate);
            monthlyDate.setDay(strDay);
            monthlyDateList.add(monthlyDate);

        //시작날짜 + 1 일
            c1.add(Calendar.DATE, 1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(monthlyDateList);

        /** 실제 리턴하려는 데이타 값 세팅하기 **/

        for(MonthlyDate monthlyDate:monthlyDateList) {
            Courses courses = new Courses();
            List<Courses.Course> courseList = new ArrayList<>();
            courses.setCalendarDate(monthlyDate.getDate()); // 날짜 값 세팅

            Date mDate = sdf.parse(monthlyDate.getDate());
            String mDay = monthlyDate.getDay(); // 날짜의 요일
            List<MonthlyFirstCourse> monthlyFirstCourseList = firstCourseRepository.monthlyFirstCourseList(mDate); // 각각의 날짜 마다 실행되는 모든 1차코스 가져오기

            // 가져온 1차코스 목록에서 해당 요일에 예약이 가능한지 검사
            for(MonthlyFirstCourse firstCourse:monthlyFirstCourseList) {
                List<String> reservationDay = firstCourse.getReservationDay();
                for(String day:reservationDay) {
                    if(mDay.equals(day)){ // 날짜 반복문이 돌아가고 있는 날짜의 요일과, 불러온 코스 목록의 개별 코스 내의 예약 가능한 요일과 일치하면,
                        int cnt=0;
                        // 예약 가능한 시간의 index(개수) 만큼 리스트 만들어주기
                        for(int i=0; i<firstCourse.getReservationTime().size(); i++){
                            Courses.Course course = new Courses.Course();
                            course.setFirstCourseSeq(firstCourse.getFirstCourseSeq());
                            course.setFirstCourseTitle(firstCourse.getFirstCourseTitle());

                            String time = firstCourse.getReservationTime().get(i); // 시간 list의 반복문 내에서 같은 시간들이 있는지 검사
                            String nextTime = "";
                            if(i+1 >=firstCourse.getReservationTime().size()){
                                nextTime="";
                            }else{
                                nextTime = firstCourse.getReservationTime().get(i+1);
                            }
                            course.setTime(time);

                            // 예약 가능한 시간에 이미 예약된 건이 있는지 검사
                            String strCheckDate = monthlyDate.getDate()+" "+time;

                            Date checkDate = simpleDateFormat.parse(strCheckDate);
                            List<MonthlyReservationCheck> checkList = reservationRepository.checkList(firstCourse.getFirstCourseSeq(), checkDate);
                            if(checkList!=null && checkList.size()!=0) {
                                if(time.equals(nextTime)){ // 동일한 시간이 있으면 시간 순번(index)과 예약건 순번(index)에 맞춰서 예약 정보 세팅
                                    course.setReservation(checkList.get(cnt));
                                    cnt++;
                                }else{
                                    if(cnt < checkList.size()){
                                        course.setReservation(checkList.get(cnt)); // 동일한 시간이 더이상 없으면 예약건 순번(index)를 초기값 0으로 세팅
                                    }
                                    cnt=0;
                                }
                            }

                            courseList.add(course);

//                        for(String time:firstCourse.getReservationTime()){
//                            Courses.Course course = new Courses.Course();
//                            course.setFirstCourseTitle(firstCourse.getFirstCourseTitle());
//                            course.setFirstCourseSeq(firstCourse.getFirstCourseSeq());
//                            course.setTime(time);
//                            String strCheckDate = monthlyDate.getDate()+" "+time;
//                            Date checkDate = simpleDateFormat.parse(strCheckDate);
//                            List<MonthlyReservationCheck> checkList = reservationRepository.checkList(firstCourse.getFirstCourseSeq(), checkDate);
//                            int cnt=0;
//                            if(checkList.size()>1) {
//
//                            }

                        }
                    }
                }
            }
            courses.setCourseList(courseList);
            coursesList.add(courses);
        }
        return coursesList;
    }

    @Data
    public static class MonthlyDate {
        private String date;
        private String day;
    }

    @Data
    public static class Courses {

        private String calendarDate;
        private List<Course> courseList;

        @Data
        public static class Course{
            private String firstCourseSeq;
            private String firstCourseTitle;
            private String time;
            private MonthlyReservationCheck reservation;
        }
    }

    @Transactional
    public int cancelReservation(ReservationDetailRequest request) {
        int result=0;
        ReservationEntity reservation = reservationRepository.findById(request.getReservationSeq()).orElseThrow(()->new RuntimeException("예약 정보를 찾을 수 없습니다."));

        if(passwordEncoder.matches(request.getPassword(), reservation.getPassword())) {
            if(reservation.getBookingStatus().equals("예약대기")){
                reservation.updateCancel();

                result=1; // 예약 취소 성공 (예약 대기 상태 일때만)
            }else{
                result=2; // 예약 대기 상태가 아닐 경우
            }
        }else {
            result=0; // 비밀 번호 불일치 (예약취소 실패)
        }
        return result;
    }

}
