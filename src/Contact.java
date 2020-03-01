/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kamyar
 */
public class Contact {
    
	private String name;
	private String cellphone;
	private String email;

    public Contact(String name, String cellphone, String email) {
        this.name = name;
        this.cellphone = cellphone;
        this.email = email;
    }
	
//	public Contact(String name, String cellphone, String email) {
//		// TODO Auto-generated constructor stub
//	}

//    Contact() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getCellphone() {
		return cellphone;
	}

	public final void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return name + "," + cellphone + "," + email;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Contact) {
			Contact comparee = (Contact) obj;
			return name.equals(comparee.name)
					&& cellphone.equals(comparee.cellphone)
					&& email.equals(comparee.email);
		}else {
			return false;
		}
	}

}
