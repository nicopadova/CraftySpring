package com.example.demo.request;

	

	public class AuthRequest {
	    private String email;
	    private String password;

	    // Getter e Setter
	    
	    public String getPassword() {
	        return password;
	    }

	    public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPassword(String password) {
	        this.password = password;
	    }
	
}
