package de.gravitex.rp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.gravitex.rp.logic.WindowStateInfo;

@NamedQueries(
		{
		@NamedQuery(name = WindowState.FIND_BY_IDENTIFIER, query = "SELECT o FROM WindowState AS o WHERE o.windowIdentifier = :windowIdentifier")
		})

@Entity
@Table(name="windowstate")
public class WindowState {
	
	public static final String FIND_BY_IDENTIFIER = "WindowState.findByIdentifier";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="windowidentifier")
	private String windowIdentifier;
	
	@Enumerated(EnumType.STRING)
	@Column(name="windowstate")
	private WindowStateInfo windowState;
	
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getWindowIdentifier() {
		return windowIdentifier;
	}

	public void setWindowIdentifier(String windowIdentifier) {
		this.windowIdentifier = windowIdentifier;
	}

	public WindowStateInfo getWindowState() {
		return windowState;
	}

	public void setWindowState(WindowStateInfo windowState) {
		this.windowState = windowState;
	}
}
