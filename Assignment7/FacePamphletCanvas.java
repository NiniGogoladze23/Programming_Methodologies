/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		GObject obj = getElementAt(getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN - 1);
		if (obj != null)
			remove(obj);
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, getWidth() / 2 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile != null) {
			showProfile(profile);
		}
	}
	
	private void showProfile(FacePamphletProfile profile) {
		showName(profile);
		showPicture(profile);
		showStatus(profile);
		showFriends(profile);
	}

	private void showName(FacePamphletProfile profile) {
		GLabel label = new GLabel(profile.getName());
		label.setFont(PROFILE_NAME_FONT);
		label.setColor(Color.blue);
		add(label, LEFT_MARGIN, TOP_MARGIN);
	}

	private void showPicture(FacePamphletProfile profile) {
		GImage img = profile.getImage();
		if (img == null) {
			drawRect();
		} else {
			img.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(img, LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN);
		}
	}

	private void drawRect() {
		GRect rect = new GRect(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN,IMAGE_WIDTH,IMAGE_HEIGHT);
		GLabel label = new GLabel("No Image");
		label.setFont(PROFILE_IMAGE_FONT);
		add(rect);
		add(label, LEFT_MARGIN + IMAGE_WIDTH / 2 - label.getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT / 2);
	}

	private void showStatus(FacePamphletProfile profile) {
		String status = profile.getStatus();
		if (status.length() == 0) {
			status = "No current status";
		} else {
			status = profile.getName() + " is " + status;
		}
		GLabel label = new GLabel(status);
		label.setFont(PROFILE_STATUS_FONT);
		add(label, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN);
	}

	private void showFriends(FacePamphletProfile profile) {
		GLabel friends = new GLabel("Friends:");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN);
		Iterator<String> friendlist = profile.getFriends();
		double currentHeight = TOP_MARGIN + IMAGE_MARGIN;
		while (friendlist.hasNext()) {
			GLabel nextfriend = new GLabel(friendlist.next());
			nextfriend.setFont(PROFILE_FRIEND_FONT);
			currentHeight += nextfriend.getHeight() + 1;
			add(nextfriend, getWidth() / 2, currentHeight);
		}
	}

}
