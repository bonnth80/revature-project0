package com.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.accountBO.AccountBO;
import com.accountBO.AccountBoImp;
import com.bank.exception.BusinessException;
import com.bank.to.Account;
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
	
	private static void displayAccountsHeader() {
		log.info("Account #\t\tUser Name\t\t\tCreation Date\t\tStatus");
		log.info(linebreak);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		User user;
		String username;
		String password;
		int selection = 0;
		boolean runAppLoop = true;
		boolean runUserLoop = true;
		
		while (runAppLoop) {
			runUserLoop = true;
			user = null;
			username = "";
			password = "";
			displayTitle();
			
			// retrieve user credentials
			log.info("Username:");
			username = scanner.nextLine();
			log.info("Password: ");
			password = scanner.nextLine();			
			
			// Display user main menu
			try {
				user = getUser(username, password);
				
				while (runUserLoop) {
					selection = 0;
					switch (user.getArchetype()) {
					// Employee Menu
					case 0:
						// get valid input
						while (selection < 1 || selection > 6) {
							displayEmployeeMenu();
							selection = Integer.parseInt(scanner.nextLine());
							if (selection < 1 && selection > 6) {
								log.info("Please enter a valid number (1-6).");
							}
						}
						
						// execute employee selection
						switch (selection) {
						case 1:		// View pending account applications
							AccountBO acct = new AccountBoImp();
							List<Account> pendingAccounts = acct.getAccountsByStatus(0);
							displayAccountsHeader();
							for (Account pa : pendingAccounts) {
								String acctString = pa.getAccountNumber() + "\t\t\t"
										+pa.getUserFirstName() + " " + pa.getUserLastName() + "\t\t\t"
										+pa.getCreationDate() + "\t\t"
										+"PENDING APPROVAL";
								log.info(acctString);
							}
							break;
						case 2: 	// Retrieve customer account info.
							break;
						case 3: 	// Retrieve transaction log.
							break;
						case 4:		// Register New User
							break;
						case 5:		// Sign out
							runUserLoop = false;
							break;
						case 6:		// exit application
							runAppLoop = false;
							runUserLoop = false;
							break;
						default:
						}
						
						break;
						
					// Customer Menu
					case 1:
						// get valid input
						while (selection < 1 || selection > 7) {
							displayEmployeeMenu();
							selection = Integer.parseInt(scanner.nextLine());
							if (selection < 1 && selection > 7) {
								log.info("Please enter a valid number (1-7).");
							}
						}
						
						// execute customer selection
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
							runUserLoop = false;
							break;
						case 7:		// Exit application
							runUserLoop = false;
							runAppLoop = false;
							break;
						default:								
						}
						break;
					default:
						log.info("User type error.");
					}					
				}
			} catch (BusinessException e) {
				log.info(e.getMessage());
			}
			
		}
		
		log.info("\nGoodbye.\n");
	}

}
