package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class AdminFeedback {
    
    @Id
    @GeneratedValue
    private int adminFeedbackId;
    
    @OneToOne
    private Admin adminId;
    
    private String adminreply;

	public AdminFeedback(Admin adminId, String adminreply) {
		super();
		this.adminId = adminId;
		this.adminreply = adminreply;
	}
    
}