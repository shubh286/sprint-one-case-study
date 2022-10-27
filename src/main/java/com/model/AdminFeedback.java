package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AdminFeedback {
    
    @Id
    @GeneratedValue
    private int adminFeedbackId;
    
    @OneToOne
    private Admin adminId;
    
    private String adminreply;
}