
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	private JButton add = new JButton("Add");
	private JButton delete = new JButton("Delete");
	private JButton lookup = new JButton("Lookup");
	private JButton changeStatus = new JButton("Change Status");
	private JButton changePicture = new JButton("Change Picture");
	private JButton addFriend = new JButton("Add Friend");
	private JTextField name = new JTextField(TEXT_FIELD_SIZE);
	private JTextField status = new JTextField(TEXT_FIELD_SIZE);
	private JTextField photo = new JTextField(TEXT_FIELD_SIZE);
	private JTextField friend = new JTextField(TEXT_FIELD_SIZE);
	private FacePamphletProfile active;
	private FacePamphletCanvas canvas;
	private FacePamphletDatabase base;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		addButtons();
		active = null;
		canvas = new FacePamphletCanvas();
		add(canvas);
		base = new FacePamphletDatabase();
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add&&name.getText().length()!=0) {
			addProfile(name.getText());
		} else if (e.getSource() == delete&&name.getText().length()!=0) {
			deleteProfile(name.getText());
		} else if (e.getSource() == lookup&&name.getText().length()!=0) {
			lookupProfile(name.getText());
		} else if ((e.getSource() == status || e.getSource() == changeStatus)&&status.getText().length()!=0) {
			changeStatus(status.getText());
		} else if ((e.getSource() == photo || e.getSource() == changePicture)&&photo.getText().length()!=0) {
			changePicture(photo.getText());
		} else if ((e.getSource() == friend || e.getSource() == addFriend)&&friend.getText().length()!=0) {
			addFriend(friend.getText());
		}
	}

	//adds friend to the active profile if possible
	private void addFriend(String name) {
		friend.setText("");
		if(active==null) {
			canvas.showMessage("Please select a profile to add friend");
			return;
		}
		if(!base.containsProfile(name)) {
			canvas.showMessage(name+" does not exist");
			return;
		}
		if(!active.addFriend(name)) {
			canvas.showMessage(active.getName()+" already has "+ name+" as a friend");
			return;
		}else {
			FacePamphletProfile friend=base.getProfile(name);
			friend.addFriend(active.getName());
			canvas.displayProfile(active);
			canvas.showMessage(name+" added as a friend");
		}
	}

	//changes picture for active profile or displays proper error message
	private void changePicture(String picture) {
		if(active==null)
			canvas.showMessage("Please select a profile to change picture");
		else {
			GImage image = null; 
			try { 
				image = new GImage(picture); 
			} catch (ErrorException e) { 
				canvas.showMessage("Undable to open image file "+picture);
			}
			if(image!=null) {
				active.setImage(image);
				canvas.displayProfile(active);
				canvas.showMessage("Picture updated");
			}
		}
		photo.setText("");
	}

	//updates status for active profile
	private void changeStatus(String status) {
		if(active==null) {
			canvas.showMessage("Please select a profile to change status");
		}else {
			active.setStatus(status);
			canvas.displayProfile(active);
			canvas.showMessage("Status updated to "+status);
		}
		this.status.setText("");
	}

	//tries to lookup a profile with given name
	private void lookupProfile(String name) {
		if(!base.containsProfile(name)) {
			canvas.displayProfile(null);
			canvas.showMessage("A profile with the name "+name+" doesn't exist");
			active=null;
		}else {
			active=base.getProfile(name);
			canvas.displayProfile(active);
			canvas.showMessage("Displaying "+name);
		}
		this.name.setText("");
	}

	//deletes profile with given name if it exists 
	private void deleteProfile(String name) {
		if(!base.containsProfile(name)) {
			canvas.showMessage("A profile with the name "+name+" doesn't exist");
		}else {
			base.deleteProfile(name);
			canvas.displayProfile(null);
			canvas.showMessage("Profile of "+name+" deleted");
			active=null;
		}
		this.name.setText("");
	}

	//creates new profile if possible
	private void addProfile(String name) {
		if (!base.containsProfile(name)) {
			FacePamphletProfile profile = new FacePamphletProfile(name);
			base.addProfile(profile);
			canvas.displayProfile(profile);
			canvas.showMessage("New profile created");
			active = profile;
		}else {
			active=base.getProfile(name);
			canvas.displayProfile(active);
			canvas.showMessage("A profile with the name "+name+" already exists");
		}
		this.name.setText("");
	}

	//displays GUI components
	private void addButtons() {
		add(status, WEST);
		add(changeStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(photo, WEST);
		add(changePicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friend, WEST);
		add(addFriend, WEST);
		add(new JLabel("Name"), NORTH);
		add(name, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);
		addActionListeners();
		status.addActionListener(this);
		photo.addActionListener(this);
		friend.addActionListener(this);
	}

}
