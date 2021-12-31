package com.ryudd.springbatch.job.example.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay,Long> {
}
