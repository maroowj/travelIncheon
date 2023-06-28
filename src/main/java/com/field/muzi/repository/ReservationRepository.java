package com.field.muzi.repository;

import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.domain.entity.ReservationEntity;
import com.field.muzi.domain.entity.SecondCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, String>, ReservationQueryRepository {

    List<ReservationEntity> findAllByFirstCourseAndReservationDateAndBookingStatusNot(FirstCourseEntity firstCourse, Date reservationDate, String bookingStatus);
}
