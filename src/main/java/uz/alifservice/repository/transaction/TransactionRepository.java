package uz.alifservice.repository.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.alifservice.collection.PaymentInfoCollection;
import uz.alifservice.domain.transaction.Transaction;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query(value = """
            select s.id          as id,
                   s.last_name   as lastName,
                   s.first_name  as firstName,
                   sum(t.amount) as totalAmount,
                   count(t.id)   as transactionCount
            from students as s
                     inner join transactions as t on s.id = t.student_id
            where t.created_at between :fromDate and :toDate
            group by s.id, s.first_name, s.last_name
            """,
            countQuery = """
            select count(*) from (
                select s.id
                from students as s
                         inner join transactions as t on s.id = t.student_id
                where t.created_at between :fromDate and :toDate
                group by s.id, s.first_name, s.last_name
            ) as grouped
            """,
            nativeQuery = true)
    Page<PaymentInfoCollection> paymentInfoReport(@Param(value = "fromDate") LocalDateTime fromDate,
                                                  @Param(value = "toDate") LocalDateTime toDate,
                                                  Pageable pageable);
}