package com.tickets.rzd.repository;

import com.tickets.rzd.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketsRepository extends CrudRepository<TicketEntity, Integer> {

    @Override
    List<TicketEntity> findAll();

    List<TicketEntity> findByDateFromBetween(
            Date dateFromStart,
            Date dateFromEnd
    );

    Optional<TicketEntity> findById(Integer id);
}
