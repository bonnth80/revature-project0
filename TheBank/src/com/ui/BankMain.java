package com.ui;

import java.util.Scanner;

import com.bank.exception.BusinessException;
import com.bank.to.User;
import com.userBO.UserBO;
import com.userBO.UserBoImp;
import org.apache.log4j.Logger;


public class BankMain {
	public static User user = new User();
	private static Logger log = Logger.getLogger(BankMain.class);
	static final String linebreak = "------------------------------------------------------------------------------------------------------------";
	public static void displayTitle() {
		log.info("--------------------------------------------- WELCOME TO: -------------------------------------------------");
		log.info("                  ___           ___                                  ___           ___           ___     ");
		log.info("      ___        /__/\\         /  /\\                  _____         /  /\\         /__/\\         /__/|    ");
		log.info("     /  /\\       \\  \\:\\       /  /:/_                /  /::\\       /  /::\\        \\  \\:\\       |  |:|    ");
		log.info("    /  /:/        \\__\\:\\     /  /:/ /\\              /  /:/\\:\\     /  /:/\\:\\        \\  \\:\\      |  |:|    ");
		log.info("   /  /:/     ___ /  /::\\   /  /:/ /:/_            /  /:/~/::\\   /  /:/~/::\\   _____\\__\\:\\   __|  |:|    ");
		log.info("  /  /::\\    /__/\\  /:/\\:\\ /__/:/ /:/ /\\          /__/:/ /:/\\:| /__/:/ /:/\\:\\ /__/::::::::\\ /__/\\_|:|____");
		log.info(" /__/:/\\:\\   \\  \\:\\/:/__\\/ \\  \\:\\/:/ /:/          \\  \\:\\/:/~/:/ \\  \\:\\/:/__\\/ \\  \\:\\~~\\~~\\/ \\  \\:\\/:::::/");
		log.info(" \\__\\/  \\:\\   \\  \\::/       \\  \\::/ /:/            \\  \\::/ /:/   \\  \\::/       \\  \\:\\  ~~~   \\  \\::/~~~~ ");
		log.info("      \\  \\:\\   \\  \\:\\        \\  \\:\\/:/              \\  \\:\\/:/     \\  \\:\\        \\  \\:\\        \\  \\:\\     ");
		log.info("       \\__\\/    \\  \\:\\        \\  \\::/                \\  \\::/       \\  \\:\\        \\  \\:\\        \\  \\:\\    ");
		log.info("                 \\__\\/         \\__\\/                  \\__\\/         \\__\\/         \\__\\/         \\__\\/ ");
		log.info(linebreak + "\n");
		log.info("If you are an existing user, please sign in. If you are a new customer and would like to apply for a new\naccount, please see one of our make-believe representatives at our make-believe teller stations.\n");
	}

	public static User getUser(String username, String password) throws BusinessException {
		UserBO user = new UserBoImp();
		return user.getUserByCredentials(username, password);
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
		log.info("\nPlease select from these available actions:");
		log.info("1. View pending account applications. (" + getApplyCount() + ")");
		log.info("2. Retrieve customer account info.");
		log.info("3. Retrieve transaction log.");
		log.info("4. Register new user.");
		log.info("5. Sign out.");
		log.info("6. Exit application.");
	}
	
	public static void displayCustomerMenu() {
		log.info("\nPlease select from these available actions:");
		log.info("1. Apply for a new account.");
		log.info("2. View account balance.");
		log.info("3. Make a withdrawal.");
		log.info("4. Make a deposit");
		log.info("5. Post Money Transfer.");
		log.info("6. Accept/Reject incoming transfers.(" + getPendingTransferCount() + ")");
		log.info("7. Sign out.");
		log.info("8. Exit application.");
	}
		
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		User user;
		String username;
		String password;
		int selection = 0;
		boolean runAppLoop = true;
		
		while (runAppLoop) {
			user = null;
			username = "";
			password = "";
			selection = 0;
			displayTitle();
			
			// retrieve user credentials
			log.info("Username:");
			username = scanner.nextLine();
			log.info("Password: ");
			password = scanner.nextLine();			
			
			// Display user main menu
			try {
				user = getUser(username, password);
				
				switch (user.getArchetype()) {
				// Employee
				case 0:
					while (selection < 1 || selection > 6) {
						displayEmployeeMenu();
						selection = Integer.parseInt(scanner.nextLine());
						if (selection < 1 && selection > 6) {
							log.info("Please enter a valid number (1-5).");
						}
					}
					
					switch (selection) {
					case 1:		// View pending account applications
						break;
					case 2: 	// Retrieve customer account info.
						break;
					case 3: 	// Retrieve transaction log.
						break;
					case 4:		// Register New User
						break;
					case 5:		// Sign out
						break;
					case 6:		// exit application
						runAppLoop = false;
						log.info("Goodbye.");
						break;
					default:
						
					}
					
					break;
					
				// Customer
				case 1:
					while (selection < 1 || selection > 8) {
						displayEmployeeMenu();
						selection = Integer.parseInt(scanner.nextLine());
						if (selection < 1 && selection > 8) {
							log.info("Please enter a valid number (1-8).");
						}
						
						switch (selection) {
						case 1:		// Apply for new account
							break;
						case 2: 	// View Account Balance
							break;
						case 3: 	// Make Withdrawal
							break;
						case 4:		// Make deposit
							break;
						case 5: 	// Post money transfer
							break;
						case 6:		// Accept / Reject incoming transfers
							break;
						case 7: 	// Sign out
							break;
						case 8:		// Exit application
							runAppLoop = false;
							log.info("Goodbye.");
							break;
						default:
							
						}
					}
					break;
				default:
					log.info("User type error.");
				}
			} catch (BusinessException e) {
				log.info(e.getMessage());
			}
			
		}
		
		log.info("You selected: " + selection + ".\nGoodbye.");
//		scanner.close();
	}

}
