package de.gravitex.rp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.gravitex.rp.logic.WindowStateInfo;

@Entity
@Table(name="componentmessage")
public class ComponentMessage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String componentidentifier;
	
	private Date eventtimestamp;
	
	@Enumerated(EnumType.STRING)
	private WindowStateInfo componentstate;

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getComponentidentifier() {
		return componentidentifier;
	}

	public void setComponentidentifier(String componentidentifier) {
		this.componentidentifier = componentidentifier;
	}

	public Date getEventtimestamp() {
		return eventtimestamp;
	}

	public void setEventtimestamp(Date eventtimestamp) {
		this.eventtimestamp = eventtimestamp;
	}

	public WindowStateInfo getComponentstate() {
		return componentstate;
	}

	public void setComponentstate(WindowStateInfo componentstate) {
		this.componentstate = componentstate;
	}
}
