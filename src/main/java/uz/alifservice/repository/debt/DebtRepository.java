package uz.alifservice.repository.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.alifservice.domain.debt.Debt;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long>, JpaSpecificationExecutor<Debt> {

}