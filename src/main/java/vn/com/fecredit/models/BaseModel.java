package vn.com.fecredit.models;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OrderBy(clause = "id ASC")
	private long id;

	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date modified;

	@Column(columnDefinition = "tinyint(1) default 0", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(columnDefinition = "tinyint(1) default 0", nullable = false)
	private Boolean isUpdated = false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}


	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreated() {
		return created == null ? null : (Date) created.clone();
	}

	public void setCreated(Date created) {
		this.created = created == null ? null : (Date) created.clone();
	}

	public Date getModified() {
		return modified == null ? null : (Date) modified.clone();
	}

	public void setModified(Date modified) {
		this.modified = modified == null ? null : (Date) modified.clone();
	}

}
