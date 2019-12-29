package com.ui;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.accountBO.AccountBO;
import com.accountBO.AccountBoImp;
import com.bank.exception.BusinessException;
import com.bank.to.User;
import com.transferBO.TransferBO;
import com.transferBO.TransferBoImp;
import com.userBO.UserBO;
import com.userBO.UserBoImp;


public class BankMain {
	private static User user = new User();
	private static Logger log = Logger.getLogger(BankMain.class);
	private static final String linebreak = "------------------------------------------------------------------------------------------------------------";
	
	// Main methods
	private static void displayTitle() {
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

	private static User getUser(String username, String password) throws BusinessException {
		UserBO user = new UserBoImp();
		return user.getUserByCredentials(username, password);
	}
	
	private static int getApplyCount() throws BusinessException {
		AccountBO acct = new AccountBoImp();
		return acct.getPendingApprovalCount();
	}	
	
	private static int getPendingTransferCount(int accountId) throws BusinessException {
		TransferBO transfer = new TransferBoImp();
		return transfer.getTransferCount(accountId);
	}
	
	private static void displayEmployeeMenu() {
		try {
			log.info("\nPlease select from these available actions:");
			log.info("1. View pending account applications. (" + getApplyCount() + ")");
			log.info("2. Retrieve customer account info.");
			log.info("3. Retrieve transaction log.");
			log.info("4. Register new user.");
			log.info("5. Sign out.");
			log.info("6. Exit application.");
			
		} catch (BusinessException e) {
			log.info(e);
		}
	}
	
	private static void displayCustomerMenu() {
		log.info("\nPlease select from these available actions:");
		log.info("1. Apply for a new account.");
		log.info("2. View account balance.");
		log.info("3. Make a withdrawal.");
		log.info("4. Make a deposit");
		log.info("5. Post Money Transfer.");
		log.info("6. Sign out.");
		log.info("7. Exit application.");
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
				// Employee Menu
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
					
				// Customer Menu
				case 1:
					while (selection < 1 || selection > 7) {
						displayEmployeeMenu();
						selection = Integer.parseInt(scanner.nextLine());
						if (selection < 1 && selection > 7) {
							log.info("Please enter a valid number (1-7).");
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
						case 6: 	// Sign out
							break;
						case 7:		// Exit application
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
	}

}
