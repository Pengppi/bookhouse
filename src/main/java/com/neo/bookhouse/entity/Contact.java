package com.neo.bookhouse.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Contact {
   private Long senderId;
   
   private Long receiverId;
   
   private Integer unreadCount;
   
   private LocalDateTime upDateTime;
}
