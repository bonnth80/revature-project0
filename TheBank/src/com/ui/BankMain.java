package com.ui;

import java.util.Scanner;

import com.bank.exception.BusinessException;
import com.bank.to.User;
import com.userBO.UserBO;
import com.userBO.UserBoImp;

public class BankMain {
	public static User user = new User();
	static final String linebreak = "------------------------------------------------------------------------------------------------------------";
	
	public static void displayTitle() {
		System.out.println("--------------------------------------------- WELCOME TO: -------------------------------------------------");
		System.out.println("                  ___           ___                                  ___           ___           ___     ");
		System.out.println("      ___        /__/\\         /  /\\                  _____         /  /\\         /__/\\         /__/|    ");
		System.out.println("     /  /\\       \\  \\:\\       /  /:/_                /  /::\\       /  /::\\        \\  \\:\\       |  |:|    ");
		System.out.println("    /  /:/        \\__\\:\\     /  /:/ /\\              /  /:/\\:\\     /  /:/\\:\\        \\  \\:\\      |  |:|    ");
		System.out.println("   /  /:/     ___ /  /::\\   /  /:/ /:/_            /  /:/~/::\\   /  /:/~/::\\   _____\\__\\:\\   __|  |:|    ");
		System.out.println("  /  /::\\    /__/\\  /:/\\:\\ /__/:/ /:/ /\\          /__/:/ /:/\\:| /__/:/ /:/\\:\\ /__/::::::::\\ /__/\\_|:|____");
		System.out.println(" /__/:/\\:\\   \\  \\:\\/:/__\\/ \\  \\:\\/:/ /:/          \\  \\:\\/:/~/:/ \\  \\:\\/:/__\\/ \\  \\:\\~~\\~~\\/ \\  \\:\\/:::::/");
		System.out.println(" \\__\\/  \\:\\   \\  \\::/       \\  \\::/ /:/            \\  \\::/ /:/   \\  \\::/       \\  \\:\\  ~~~   \\  \\::/~~~~ ");
		System.out.println("      \\  \\:\\   \\  \\:\\        \\  \\:\\/:/              \\  \\:\\/:/     \\  \\:\\        \\  \\:\\        \\  \\:\\     ");
		System.out.println("       \\__\\/    \\  \\:\\        \\  \\::/                \\  \\::/       \\  \\:\\        \\  \\:\\        \\  \\:\\    ");
		System.out.println("                 \\__\\/         \\__\\/                  \\__\\/         \\__\\/         \\__\\/         \\__\\/ ");
		System.out.println(linebreak + "\n");
		System.out.println("If you are an existing user, please sign in. If you are a new customer and would like to apply for a new\naccount, please see one of our make-believe representatives at our make-believe teller stations.\n");
	}

	public static User getUser(String[] upass) throws BusinessException {
		UserBO user = new UserBoImp();
		return user.getUserByCredentials(upass[0], upass[1]);
	}
	
	public static int getApplyCount() {
		//stub
		return 2;
	}	
	
	public static int getPendingTransferCount() {
		//stub
		return 2;
	}
	
	public static void displayEmployeeMenu() {
		int menuSequence = 0;
		System.out.println("Please select from these available actions:");
		System.out.println(++menuSequence + ". View pending account applications. (" + getApplyCount() + ")");
		System.out.println(++menuSequence + ". Retrieve customer account info.");
		System.out.println(++menuSequence + ". Retrieve transaction log.");
		System.out.println(++menuSequence + ". Register new user.");
		System.out.println(++menuSequence + ". Log out.");
		System.out.println(++menuSequence + ". Exit application.");
	}
	
	public static void displayCustomerMenu() {
		System.out.println("Please select from these available actions:");
		System.out.println("1. Apply for a new account.");
		System.out.println("2. View account balance.");
		System.out.println("3. Make a withdrawal.");
		System.out.println("4. Make a deposit");
		System.out.println("5. Post Money Transfer.");
		System.out.println("6. Accept/Reject incoming transfers.(" + getPendingTransferCount() + ")");
		System.out.println("7. Log out.");
		System.out.println("8. Exit application.");
	}
		
	public static void main(String[] args) {
		User user;
		int selection = 0;
		Scanner scanner = new Scanner(System.in);
		
		displayTitle();
		System.out.println("Username:");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		String[] userCredentials = {username, password};
		
		try {
			user = getUser(userCredentials);
			
			switch (user.getArchetype()) {
			case 0:
				while (selection < 1 || selection > 5) {
					displayEmployeeMenu();
					selection = Integer.parseInt(scanner.nextLine());
					if (selection < 1 && selection > 5) {
						System.out.println("Please enter a valid number (1-5).");
					}
				}
				break;
			case 1:
				while (selection < 1 || selection > 8) {
					displayEmployeeMenu();
					selection = Integer.parseInt(scanner.nextLine());
					if (selection < 1 && selection > 8) {
						System.out.println("Please enter a valid number (1-8).");
					}
				}
				break;
			default:
				System.out.println("User type error.");
			}		
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("You selected: " + selection + ".\nGoodbye.");
		scanner.close();
	}

}
