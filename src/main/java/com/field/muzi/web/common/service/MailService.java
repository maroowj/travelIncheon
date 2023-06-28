package com.field.muzi.web.common.service;

import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.domain.entity.SecondCourseEntity;
import com.field.muzi.repository.FirstCourseRepository;
import com.field.muzi.repository.SecondCourseRepository;
import com.field.muzi.web.user.dto.reservation.ReservationInsertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final FirstCourseRepository firstCourseRepository;
    private final SecondCourseRepository secondCourseRepository;

    @Transactional
    public void  sendReservationMail(ReservationInsertRequest request) throws Exception {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 HH시 mm분");

        FirstCourseEntity firstCourse = firstCourseRepository.findById(request.getFirstCourseSeq()).orElseThrow(() -> new RuntimeException("1차코스 정보를 찾을 수 없습니다."));;

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(msg, true, "UTF-8");

        StringBuilder content = new StringBuilder(); // 메일 내용

        content.append("<a href='http://schooltrip.co.kr'><img src='cid:logo'></a><br/><br/><b>예약안내</b><br/><br/>");
        content.append("<b>예약일시</b>: " + sdf.format(request.getReservationDate()) + "<br/>");
        content.append("<b>신청기관명</b>: " + request.getApplicant() + " (" + request.getApplicantDetail()  + ")<br/>");
        content.append("<b>방문인원수</b>: " + request.getGuestNumber() + "명<br/>");
        content.append("<b>신청프로그램</b><br/>");
        content.append("<b>1차 코스</b>: " + firstCourse.getFirstCourseTitle() + "<br/>");
        if(request.getSecondCourseSeq().size() != 0 ) {
            for(String secondCourseSeq : request.getSecondCourseSeq()) {
                SecondCourseEntity secondCourse = secondCourseRepository.findById(secondCourseSeq).orElseThrow(() -> new RuntimeException("2차코스 정보를 찾을 수 없습니다."));

                content.append("<b>2차 코스</b>: " + secondCourse.getSecondCourseTitle() + "<br/>");
            }
        }
        if(request.getManagerPhone() != null) {
            content.append("<b>담당자 연락처</b>: " + request.getManagerPhone() + "<br/>");
        }

        content.append("<b>총 합계금액</b>: " + decFormat.format(request.getCost()) + "원<br/>");

        content.append("<img src='cid:step'><br/>");
        content.append("<img src='cid:refund'><br/>");

        content.append("<br/><br/><a href=''>http://schooltrip.co.kr</a>");

        mimeMessageHelper.setFrom(new InternetAddress("info@schooltrip.co.kr")); // 보내는 사람
        mimeMessageHelper.setSubject("<인천 친환경 교육여행> 예약 내역입니다."); // 메일 제목
        mimeMessageHelper.setText(content.toString(), true);


        /** 이미지 cid 설정 (배포 및 로컬테스트 시 주석 위치 위아래 변경) **/

        // 로컬
//        FileSystemResource logo = new FileSystemResource(new File("").getAbsolutePath()+"/src/main/resources/static/images/logo/ogSchoolTriplogo.png");
//        FileSystemResource step = new FileSystemResource(new File("").getAbsolutePath()+"/src/main/resources/static/images/admin/stepImage.png");
//        FileSystemResource refund = new FileSystemResource(new File("").getAbsolutePath()+"/src/main/resources/static/images/admin/refundImage.png");

        // 배포
        FileSystemResource logo = new FileSystemResource(new File("").getAbsolutePath()+"/tomcat/webapps/ROOT/WEB-INF/classes/static/images/logo/ogSchoolTriplogo.png");
        FileSystemResource step = new FileSystemResource(new File("").getAbsolutePath()+"/tomcat/webapps/ROOT/WEB-INF/classes/static/images/admin/stepImage.png");
        FileSystemResource refund = new FileSystemResource(new File("").getAbsolutePath()+"/tomcat/webapps/ROOT/WEB-INF/classes/static/images/admin/refundImage.png");

        mimeMessageHelper.addInline("logo", logo);
        mimeMessageHelper.addInline("step", step);
        mimeMessageHelper.addInline("refund", refund);


        /** 배포 및 로컬테스트 시 변경 **/
//        String adminTo = "left_right@naver.com";
        String adminTo = "travelincheon@naver.com";
        mimeMessageHelper.setTo(adminTo);
        javaMailSender.send(msg);
        mimeMessageHelper.setTo(request.getEmail());
        javaMailSender.send(msg);

    }

}
