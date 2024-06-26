package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findByStatus(String status);

    @Query("SELECT c FROM Contact c WHERE c.status=:status ")
    Page<Contact> findByStatus(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = :status WHERE c.contactId = :id")
    int updateStatusById(String status, int id);


    Page<Contact> findOpenMessages(String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMessageStatus(String status, int contactId);
}
