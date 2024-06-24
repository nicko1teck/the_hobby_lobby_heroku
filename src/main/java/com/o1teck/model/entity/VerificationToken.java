package com.o1teck.model.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="verification")
public class VerificationToken {
	
	@Column(name="token")
	private String token;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long Id;
	
	@OneToOne(targetEntity=SiteUser.class)
	@JoinColumn(name="user_id", nullable=false)
	private SiteUser user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiration_date")
	private Date expiration;
	
	@Column(name="token_type")
	@Enumerated(EnumType.STRING)
	private TokenType type;
	
	@PrePersist
	private void setDate(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 24);
		expiration = c.getTime();
	}
	
	public VerificationToken(){
		
	}

	public VerificationToken(String token, SiteUser user, TokenType type){
		this.token = token;
		this.user = user;
		this.type = type;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public SiteUser getUser() {
		return user;
	}

	public void setUser(SiteUser user) {
		this.user = user;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}
	
}
