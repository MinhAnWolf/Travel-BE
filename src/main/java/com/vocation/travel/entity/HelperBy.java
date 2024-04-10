package com.vocation.travel.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class HelperBy {
  private String createBy;

  @CreationTimestamp
  private LocalDateTime createDate;

  private String updateBy;

  @UpdateTimestamp
  private LocalDateTime updateDate;
}
