package uz.alifservice.repository.communication.sms;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.alifservice.domain.communication.sms.SmsHistory;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {

    // select count(*) from sms_history where phone = ? and created_date between ? and ?
    Long countByPhoneAndCreatedAtBetween(String phone, LocalDateTime from, LocalDateTime to);

    // select * from sms_history where phone = ? order by created_date desc limit 1;
    Optional<SmsHistory> findTop1ByPhoneOrderByCreatedAtDesc(String phone);

    @Modifying
    @Transactional
    @Query("update SmsHistory set attemptCount = coalesce(attemptCount, 0) + 1 where id = ?1")
    void updateAttemptCount(Long id);
}
