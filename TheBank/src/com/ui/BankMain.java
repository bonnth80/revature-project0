package com.ui;

import java.util.ArrayList;
import java.util.Date;
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
import com.userBO.UserBoImp;


public class BankMain {
	//private static User user;
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
	}

	private static int getApplyCount() throws BusinessException {
		AccountBO acct = new AccountBoImp();
		return acct.getPendingApprovalCount();
	}	
	
	private static int getPendingTransferCount(int accountId) throws BusinessException {
		TransferBO transfer = new TransferBoImp();
		return transfer.getTransferCount(accountId);
	}
	
	private static void displaySignInPage() {
		log.info("Please make a selection:\n\t1. Existing User Sign-in\n\t2. New User Sign-up\n");
	}
	
	private static void displayEmployeeMenu() {
		try {
			log.info("\nPlease select from these available actions:");
			log.info("\t1. View pending account applications. (" + getApplyCount() + ")");
			log.info("\t2. Retrieve customer account info.");
			log.info("\t3. Retrieve transaction log.");
			log.info("\t4. Register new user.");
			log.info("\t5. Sign out.");
			log.info("\t6. Exit application.");
			
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
	}
	
	private static void displayCustomerMenu() {
		log.info("\nPlease select from these available actions:");
		log.info("\t1. Apply for a new account.");
		log.info("\t2. View account balance.");
		log.info("\t3. Make a withdrawal.");
		log.info("\t4. Make a deposit");
		log.info("\t5. Post Money Transfer.");
		log.info("\t6. Sign out.");
		log.info("\t7. Exit application.");
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
			do {
				displaySignInPage();
				selection = Integer.parseInt(scanner.nextLine());
				if (!(selection == 1 || selection == 2)) {
					log.info("Invalid selection.");
				}
			} while (!(selection == 1 || selection == 2));
			
			if (selection == 1) {
				// retrieve user credentials
				log.info("Username:");
				username = scanner.nextLine();
				log.info("Password: ");
				password = scanner.nextLine();			
				
				// Display user main menu
				try {
					user = new UserBoImp().getUserByCredentials(username, password);
					log.info("\nCurrent user is: " + (
							(user.getArchetype() == 0) ? ("Employee") : ("Customer")
									)
							);
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
								if (getApplyCount() > 0) {
									List<Account> pendingAccounts = new AccountBoImp().getAccountsByStatus(0);
									List<Integer> accountNums = new ArrayList<>();
									displayAccountsHeader();
									for (Account pa : pendingAccounts) {
										accountNums.add(pa.getAccountNumber());
										String acctString = pa.getAccountNumber() + "\t\t\t"
												+pa.getUserFirstName() + " " + pa.getUserLastName() + "\t\t\t"
												+pa.getCreationDate() + "\t\t"
												+"PENDING APPROVAL";
										log.info(acctString);
									}
									do {
										log.info("Choose an account number or type -1 to return to the employee menu");
										selection = Integer.parseInt(scanner.nextLine());
										if (accountNums.contains(selection)) {
											Account accountToApprove = new AccountBoImp().getAccountById(selection);
											int selectionB = -1;
											do {
												log.info("Do you want to approve (1) or reject (2) this account application?");
												selectionB = Integer.parseInt(scanner.nextLine());
												if (selectionB == 1) {
													new AccountBoImp().updateAccountStatus(accountToApprove, 1);
														log.info("Account " + accountToApprove.getAccountNumber() + " is now Active.");
												} else if (selectionB == 2) {
													new AccountBoImp().updateAccountStatus(accountToApprove, 2);
													log.info("Account " + accountToApprove.getAccountNumber() + " is now Rejected.");
												} else {
													System.out.println("Invalid Selection.");
												}									
											} while (selectionB != 1 && selectionB != 2);
											
										} else if (selection != -1) {
											log.info("This is not a valid account number.");
										}
									} while (!(accountNums.contains(selection) || selection == -1));								
								} else {
									log.info("There are no new accounts pending approval. Returning to menu.");
								}
								break;
							case 2: 	// Retrieve customer account info.
								
								break;
							case 3: 	// Retrieve transaction log.
								break;
							case 4:		// Register New User
								log.info("Welcome to New User creation. Please enter the information as follows.");				
								String firstName;
								String lastName;
								int archetype;
								String ssn;
								String homePhone;
								String mobilePhone;
								String email;
								String streetAddress;
								String city;
								String state;
								String country;
								String zip;
								String newUserName;
								String newPassword;
								Date dateCreated = new Date();
								
								log.info("\nFirst Name:");
								firstName = scanner.nextLine();
								log.info("\nLast Name: ");
								lastName = scanner.nextLine();
								log.info("\nEmployee(0) or Customer(1)?");
								archetype = Integer.parseInt(scanner.nextLine());
								log.info("\nSocial Security Number:");
								ssn = scanner.nextLine();
								log.info("\nHome phone numbe:");
								homePhone = scanner.nextLine();
								log.info("\nMobile phone number:");
								mobilePhone = scanner.nextLine();
								log.info("\nEmail address:");
								email = scanner.nextLine();
								log.info("\nStreet address:");
								streetAddress = scanner.nextLine();
								log.info("\nCity:");
								city = scanner.nextLine();
								log.info("\nState:");
								state = scanner.nextLine();
								log.info("\nCountry:");
								country = scanner.nextLine();
								log.info("\nZip Code:");
								zip = scanner.nextLine();
								log.info("\nUsername:");
								newUserName = scanner.nextLine();
								log.info("\nPassword:");
								newPassword = scanner.nextLine();
								
								try {
									new UserBoImp().addNewUser(new User(
											firstName,
											lastName,
											archetype,
											ssn,
											homePhone,
											mobilePhone,
											email,
											streetAddress,
											city,
											state,
											country,
											zip,
											newUserName,
											newPassword,
											dateCreated));
								} catch (BusinessException e) {
									log.error(e.getMessage());
								}
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
								displayCustomerMenu();
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
					log.error(e.getMessage());
				}
			} else { // User sign-in selection : register new user
				log.info("Welcome to New User creation. Please enter the information as follows.");				
				String firstName;
				String lastName;
//				int archetype;  archteype when created by customer can only be customer (0)
				String ssn;
				String homePhone;
				String mobilePhone;
				String email;
				String streetAddress;
				String city;
				String state;
				String country;
				String zip;
				String newUserName;
				String newPassword;
				Date dateCreated = new Date();
				
				log.info("\nFirst Name:");
				firstName = scanner.nextLine();
				log.info("\nLast Name: ");
				lastName = scanner.nextLine();
				log.info("\nSocial Security Number:");
				ssn = scanner.nextLine();
				log.info("\nHome phone numbe:");
				homePhone = scanner.nextLine();
				log.info("\nMobile phone number:");
				mobilePhone = scanner.nextLine();
				log.info("\nEmail address:");
				email = scanner.nextLine();
				log.info("\nStreet address:");
				streetAddress = scanner.nextLine();
				log.info("\nCity:");
				city = scanner.nextLine();
				log.info("\nState:");
				state = scanner.nextLine();
				log.info("\nCountry:");
				country = scanner.nextLine();
				log.info("\nZip Code:");
				zip = scanner.nextLine();
				log.info("\nUsername:");
				newUserName = scanner.nextLine();
				log.info("\nPassword:");
				newPassword = scanner.nextLine();
				
				try {
					new UserBoImp().addNewUser(new User(
							firstName,
							lastName,
							1,
							ssn,
							homePhone,
							mobilePhone,
							email,
							streetAddress,
							city,
							state,
							country,
							zip,
							newUserName,
							newPassword,
							dateCreated));
				} catch (BusinessException e) {
					log.error(e.getMessage());
				}
			}
		}
		
		log.info("\nGoodbye.\n");
	}

}
