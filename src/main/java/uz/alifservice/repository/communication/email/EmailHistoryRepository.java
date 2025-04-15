package uz.alifservice.repository.communication.email;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.alifservice.domain.communication.email.EmailHistory;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailHistoryRepository extends JpaRepository<EmailHistory, Long> {

    // select count(*) from sms_history where email = ? and created_date between ? and ?
    Long countByEmailAndCreatedAtBetween(String email, LocalDateTime from, LocalDateTime to);

    // select * from sms_history where phone = ? order by created_date desc limit 1;
    Optional<EmailHistory> findTop1ByEmailOrderByCreatedAtDesc(String email);

    @Modifying
    @Transactional
    @Query("update SmsHistory set attemptCount = coalesce(attemptCount, 0) + 1 where id = ?1")
    void updateAttemptCount(Long id);
}
