  package com.o1teck.model.entity;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.owasp.html.PolicyFactory;

import com.o1teck.model.dto.FileInfo;

@Entity
@Table(name="profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@OneToOne(targetEntity=SiteUser.class)
	@JoinColumn(name="user_id", nullable=false)
	private SiteUser user;
	
	@Column(name="about", length=5000)
	@Size(max=5000, message="{editprofile.about.size}")
	private String about;
	
	//"splitting up" the photo info as we have is wise
	//because of the potential for future changes (adding another layer of subDirs, or desiring to 
	//change all photos to a particular format).  Having this info atomistically makes changes
	//like that more manageable, so...
	
	//(we can get the baseDirectory from application.properties, so don't need that here)
	
	//Once we have these variables(below) we can save this info in the Controller
	
	/*
	
	@Column(name="photo_directory", length=20)
	private String photoDirectory;
	
	@Column(name="photo_name", length=20)
	private String photoName;
	
	@Column(name="photo_extension", length=5)
	private String photoExtension;
	
	*/
	
	 
	/*
	 * the the JoinTable annotation....  This is implemented behind the scenes by Hibernate creating a Join table,
	 * where each row has the id of ONE profile and the name of ONE interest.
	 * 
	 * We're saying there's a join table...and it's gonna have two columns in it, and here's the names
	 */
	@ManyToMany(fetch = FetchType.EAGER) //When a profile is loaded, all the interest will be loaded.  No lazy.
	@JoinTable(name="profile_interests", 
	joinColumns={@JoinColumn(name="profile_id")}, 
	inverseJoinColumns = { @JoinColumn(name="interest_id")})
	@OrderColumn(name="display_order")
	/*It doesn't matter what we call it.  This is purely specifying the name of a column in a table that's gonna connect interests with profiles.
	*/
	private Set<Interest> interests;
	
	

	public Profile(){

	}
	
	public Profile(SiteUser user){
		
		this.user = user;
	}
	
	//Helper method
	public void setPhotoDetails(FileInfo info){
		/*
		photoDirectory = info.getSubDirectory();
		photoName = info.getBasename();
		photoExtension = info.getExtension();
		*/
	}
	

	public Set<Interest> getInterests() {
		return interests;
	}

	
	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

/*
	//Helper method
	//we pass in the baseDir because that is not known in the profile
	public Path getPhoto(String baseDirectory){
		
		
		//also check for situation where photo has not been set in profile
		if(photoName == null){
			return null;
		}
		
		return Paths.get(baseDirectory, photoDirectory, photoName + "." + photoExtension);
		
	}
	*/
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SiteUser getUser() {
		return user;
	}

	public void setUser(SiteUser user) {
		this.user = user;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	
	/*
	
	public String getPhotoDirectory() {
		return photoDirectory;
	}

	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoExtension() {
		return photoExtension;
	}

	public void setPhotoExtension(String photoExtension) {
		this.photoExtension = photoExtension;
	}

	*/
	
	
	
	
	/*
	 * Create a profile that is suitable for displaying via JSP
	 * i.e. no identifying/private info (e.g. passwords)
	 */
	public void safeCopyFrom(Profile otherProfile){
		
		if(otherProfile.about != null){
			this.about = otherProfile.about;
		}
		
		if(otherProfile.interests != null){
			this.interests = otherProfile.interests;
		}
	}

	/*
	 * Create a profile that is suitable for saving 
	 * i.e. doesn't have any weird HTML tags in there that we don't want in it
	 */
	public void safeMergeFrom(Profile webProfile, PolicyFactory htmlPolicy) {
		if(webProfile.about != null){
			this.about = htmlPolicy.sanitize(webProfile.about);
		}
		
	}

	public void addInterest(Interest interest) {
		
		interests.add(interest);
	}

	
	public void removeInterest(String interestName) {
		//interests.remove(interestName);
		
		//Wait...
		interests.remove(new Interest(interestName));
	}
}