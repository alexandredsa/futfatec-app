package br.com.futfatec.model.auth;

public class ValidationStatus {
	private boolean permission;
	private Role role;
	private String leagueId;

	
	
	public ValidationStatus(boolean permission, Role role, String leagueId) {
		this.permission = permission;
		this.role = role;
		this.leagueId = leagueId;
	}
	
	

	public ValidationStatus() {
	}



	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
}
