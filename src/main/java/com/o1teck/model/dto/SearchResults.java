package com.o1teck.model.dto;

import java.util.Set;

import com.o1teck.model.entity.Interest;
import com.o1teck.model.entity.Profile;

public class SearchResults {
	
	private long userId;
	private Set<Interest> interests;
	private String firstname;
	private String surname;
	private String profilePhotoUrl;

	public SearchResults(){
	}
	
	public SearchResults(Profile profile){
		
		userId = profile.getUser().getId();
		interests = profile.getInterests();
		firstname = profile.getUser().getFirstname();
		surname = profile.getUser().getSurname();
		profilePhotoUrl = profile.getUser().getProfilePhotoUrl();
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}

	

	public Set<Interest> getInterests() {
		return interests;
	}


	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getProfilePhotoUrl() {
		return profilePhotoUrl;
	}

	public void setProfilePhotoUrl(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}

	@Override
	public String toString() {
		return "SearchResults [userId=" + userId + ", interests=" + interests + ", firstname=" + firstname
				+ ", surname=" + surname + "]";
	}
}
