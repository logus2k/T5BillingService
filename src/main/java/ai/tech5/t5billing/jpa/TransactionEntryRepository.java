package ai.tech5.t5billing.jpa;

import ai.tech5.t5billing.entity.TransactionEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEntryRepository  extends JpaRepository<TransactionEntry,Long> {
}
