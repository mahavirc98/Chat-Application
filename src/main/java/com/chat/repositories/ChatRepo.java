package com.chat.repositories;

import com.chat.entities.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface ChatRepo extends JpaRepository<Chat,Integer> {
    Page<Chat> findByUser(String user, Pageable pageable);

    @Modifying
    @Query("delete from Chat p where p.user = :user")
    void deleteByUser(@Param("user") String user);

}
