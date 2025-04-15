package uz.alifservice.repository.communication.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.alifservice.domain.communication.sms.SmsProviderTokenHolder;

import java.util.Optional;

@Repository
public interface SmsProviderTokenHolderRepository extends JpaRepository<SmsProviderTokenHolder, Long> {

    Optional<SmsProviderTokenHolder> findTop1By();
}
