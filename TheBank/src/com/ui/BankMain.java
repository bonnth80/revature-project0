package com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.accountBO.AccountBoImp;
import com.accountDAO.AccountDaoImp;
import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Transaction;
import com.bank.to.Transfer;
import com.bank.to.User;
import com.dbutil.OracleConnection;
import com.transactionBO.TransactionBoImp;
import com.transferBO.TransferBoImp;
import com.transferDAO.TransferDaoImp;
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

	private static void displayEmployeeMenu() {
		try {
			log.info("\nPlease select from these available actions:");
			log.info("\t1. View pending account applications. (" + new AccountBoImp().getPendingApprovalCount() + ")");
			log.info("\t2. Retrieve customer account info.");
			log.info("\t3. Retrieve transaction log.");
			log.info("\t4. Register new user.");
			log.info("\t5. Sign out.");
			log.info("\t6. Exit application.");
			
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
	}
	
	private static void displayCustomerMenu(int userId) {
		try {
			log.info("\nPlease select from these available actions:");
			log.info("\t1. Apply for a new account.");
			log.info("\t2. View accounts list.");
			log.info("\t3. Make a withdrawal.");
			log.info("\t4. Make a deposit");
			log.info("\t5. Post Money Transfer.");
				log.info("\t6. View incumbent transfer requests. (" + 
					new TransferBoImp().getTransferCountByUserId(userId) + ")");
			log.info("\t7. Sign out.");
			log.info("\t8. Exit application.");
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
	}
	
	private static void displayPendingAccountsHeader() {
		String accountHeader = padStringRight("Account #", 16);
		String userNameHeader = padStringRight("User Name", 30);
		String startBalanceHeader = padStringRight("S. Balance", 20);
		String creationDateHeader = padStringRight("Creation Date", 22);
		String statusHeader = padStringRight("Status", 16);
		log.info(accountHeader + userNameHeader + startBalanceHeader + creationDateHeader + statusHeader);
		log.info(linebreak);
	}
	
	private static void displayUserActiveAccountsHeader() {
		String accountHeader = padStringRight("Account #", 25);
		String balanceHeader = padStringRight("Balance", 25);
		String creationDateHeader = padStringRight("Creation Date", 25);
		log.info(accountHeader + balanceHeader + creationDateHeader);
		log.info(linebreak);
	}

	private static void displayPendingTransfersHeader() {
		String transferHeader = padStringRight("Transfer Number", 20);
		String sourceHeader = padStringRight("Source Account", 25);
		String destinationHeader = padStringRight("Destination Account", 25);
		String amountHeader = padStringRight("Transfer Amount", 25);
		String dateHeader = padStringRight("Date posted", 25);
		log.info(transferHeader + sourceHeader + destinationHeader + amountHeader + dateHeader);
		log.info(linebreak);
	}
	
	private static void displayTransactionsHeader() {
		String transactionIdHeader = padStringRight("Transaction Id", 18);
		String accountIdHeader = padStringRight("Account #", 12);
		String actingPartyHeader = padStringRight("Acting Party", 20);
		String creditHeader = padStringRight("Credit", 12);
		String debitHeader = padStringRight("Debit", 12);
		String dateHeader = padStringRight("Date", 15);
		String transferId = padStringRight("Transfer Id", 15);
		log.info(transactionIdHeader + accountIdHeader + actingPartyHeader + creditHeader + debitHeader + dateHeader + transferId);
		log.info(linebreak);
	}
	
	private static String padStringRight(String str, int maxStringSize) {
		StringBuffer sb = new StringBuffer(str);
		if (sb.length() < maxStringSize) {
			while (sb.length() < maxStringSize) {
				sb.append(" ");
			}
		} else if (sb.length() > maxStringSize) {
			sb.replace(maxStringSize - 3, sb.length(), "...");
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		User user;
		String username;
		String password;
		String name;
		int selection = 0;
		boolean runAppLoop = true;
		boolean runUserLoop = true;
		
		// Setup up Oracle custom connection
		if (args.length >= 1) {
			System.out.println("HOLY SHIT!");
			String dbUrl = args[0];
			OracleConnection.setConnectionURL(dbUrl);			
		}
		
		if (args.length == 3) {
			System.out.println("HOLY SHIT!");
			String dbUn = args[1];
			String dbPw = args[2];
			OracleConnection.setUsername(dbUn);
			OracleConnection.setPassword(dbPw);			
		}
		
		while (runAppLoop) {
			runUserLoop = true;
			user = null;
			username = "";
			password = "";
			List<Account> activeAccounts;
			List<Account> pendingAccounts;
			List<Integer> accountNums;
			displayTitle();
			do {
				log.info("Please make a selection:\n\t1. Existing User Sign-in\n\t2. New User Sign-up\n");
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
								if (new AccountBoImp().getPendingApprovalCount() > 0) {
									pendingAccounts = new AccountBoImp().getAccountsByStatus(0);
									accountNums = new ArrayList<>();
									System.out.println();
									displayPendingAccountsHeader();
									for (Account pa : pendingAccounts) {
										accountNums.add(pa.getAccountNumber());
										String acctString = padStringRight(Integer.toString(pa.getAccountNumber()), 16)
												+ padStringRight(pa.getUserFirstName() + " " + pa.getUserLastName(), 30)
												+ padStringRight(Float.toString(pa.getStartingBalance()), 20)
												+ padStringRight(pa.getCreationDate().toString(), 22)
												+ padStringRight("PENDING APPROVAL",16);
										log.info(acctString);
									}
									do {
										log.info("Choose an account number or type -1 to return to the employee menu");
										selection = Integer.parseInt(scanner.nextLine());
										if (accountNums.contains(selection)) {
											Account accountToApprove = new AccountBoImp().getAccountByAccountNumber(selection);
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
								System.out.println();
								log.info("Please select how you want to search for an accout: ");
								log.info("1. Search by customer's first and last names.");
								log.info("2. Search by account number.");
								selection = Integer.parseInt( scanner.nextLine() );
								
								switch (selection) {
								case 1:
									log.info("\nEnter the first and last name to search for accounts on record for that customer.");
									name = scanner.nextLine();
									String[] fullName = name.split(" ");
									List<Account> accounts = new AccountBoImp().getAccountsByUserName(fullName[0], fullName[1]);
									if (accounts.size() == 0) {
										log.info("No accounts exist under that customer name.");
									} else {
										displayUserActiveAccountsHeader();
										for (Account acc : accounts) {
											log.info(padStringRight(Integer.toString(acc.getAccountNumber()), 25)
													+padStringRight(Float.toString(acc.getAvailableBalance()),25)
													+padStringRight(acc.getCreationDate().toString(),25)
													);
										}
									}									
									break;
								case 2:
									log.info("\nEnter the account number for the account you wish to view.");
									selection = Integer.parseInt(scanner.nextLine());
									if (new AccountBoImp().accountExists(selection)) {
										Account acct = new AccountBoImp().getAccountByAccountNumber(selection);
										displayUserActiveAccountsHeader();
										log.info(padStringRight(Integer.toString(acct.getAccountNumber()), 25)
												+padStringRight(Float.toString(acct.getAvailableBalance()),25)
												+padStringRight(acct.getCreationDate().toString(),25)
												);
									} else {
										log.info("No account with that accunt number exists.");
									}
									break;
								default:
									log.info("Invalid Selection. Returning to menu...");
								}
								break;
							case 3: 	// Retrieve transaction log.
								System.out.println();
								displayTransactionsHeader();
								
								List<Transaction> transactions = new TransactionBoImp().getAllTransactions();
								for (Transaction t : transactions) {									
									log.info( padStringRight(Integer.toString(t.getTransactionId()),18)
										+ padStringRight(Integer.toString(t.getAccountId()),12)
										+ padStringRight(t.getActingParty(),20)
										+ padStringRight((t.getCredit() == 0.0F ? "--" : Float.toString(t.getCredit()) ),12)
										+ padStringRight((t.getDebit() == 0.0F ? "--" : Float.toString(t.getDebit()) ),12)
										+ padStringRight(t.getTransactionDate().toString(),15)
										+ padStringRight( (t.getTransferId() == -1 ? "--" : Integer.toString((t.getTransferId())) ),15)
										);
								}
								break;
							case 4:		// Register New User
								System.out.println();
								log.info("Welcome to New User creation. Please enter the information as follows.");
								int newUserId;
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
								
								// get new user id
								newUserId = new UserBoImp().getMaxIdUsed();
								
								log.info("\nFirst Name:");
								firstName = scanner.nextLine();
								log.info("\nLast Name: ");
								lastName = scanner.nextLine();
								log.info("\nEmployee(0) or Customer(1)?");
								archetype = Integer.parseInt(scanner.nextLine());
								log.info("\nSocial Security Number:");
								ssn = scanner.nextLine();
								log.info("\nHome phone number:");
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
											newUserId + 1,	// increment user id to next highest
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
							while (selection < 1 || selection > 8) {
								displayCustomerMenu(user.getUserId());
								selection = Integer.parseInt(scanner.nextLine());
								if (selection < 1 && selection > 8) {
									log.info("Please enter a valid number (1-8).");
								}
							}
							
							// execute customer selection
							switch (selection) {
							case 1:		// Apply for new account
								System.out.println();
								log.info("Welcome to your Account application. Please enter the information as follows.");
								// Account number autogenerated from max + 1
								Float startingBalance = -1.0F;
								
								do {
									log.info("How much would you like to deposit for your starting balance? (0 to cancel)");								
									startingBalance = Float.parseFloat(scanner.nextLine());
									
									if (startingBalance < 0.0F) {
										log.info("Invalid Ammount");
									} else if (startingBalance > 0.0F) {
										int maxAccountNum = new AccountDaoImp().getMaxAccountNumber();
										Account newAccount = new Account(maxAccountNum + 1, user.getUserId(), new Date(), 0,startingBalance, startingBalance);
										new AccountBoImp().addNewAccount(newAccount);
										log.info("Application submitted. You will be notified when your account application has been approved.\n"
												+ "Congratulations on investing in your future, and we look forward to doing business with you.");
									} else {
										log.info("Returning to menu...\n\n");
									}
									
								} while (startingBalance < 0.0F);
								
								
								break;
							case 2: 	// View Accounts List
								activeAccounts = new AccountBoImp().getAccountsByUserId(user.getUserId());
								System.out.println();
								displayUserActiveAccountsHeader();
								if (activeAccounts.size() > 0 ) {
									for (Account acc : activeAccounts ) {
										log.info(padStringRight(Integer.toString(acc.getAccountNumber()), 25)
												+padStringRight(Float.toString(acc.getAvailableBalance()),25)
												+padStringRight(acc.getCreationDate().toString(),25)
												);
									}									
								} else {
									log.info("You have no active accounts.");
								}
																
								break;
							case 3: 	// Make Withdrawal
								activeAccounts = new AccountBoImp().getAccountsByUserId(user.getUserId());
								accountNums = new ArrayList<>();
								log.info("\nEnter the account number from which you'd like to make a withdrawal (-1 to return to menu). If you do not see your\n"
										+ "account listed here and you believe this is in error, please contact a representative.\n");
								displayUserActiveAccountsHeader();
								for (Account acc : activeAccounts) {
									accountNums.add(acc.getAccountNumber());
									log.info(padStringRight(Integer.toString(acc.getAccountNumber()), 25)
											+padStringRight(Float.toString(acc.getAvailableBalance()),25)
											+padStringRight(acc.getCreationDate().toString(),25)
											);
								}
								do {
									log.info("Choose an account number or type -1 to return to the customer menu");
									selection = Integer.parseInt(scanner.nextLine());
									if (accountNums.contains(selection)) {
										// need source account to verify available balance
										Account sourceAccount = new AccountBoImp().getAccountByAccountNumber(selection);
										Float selectionAmount = -1.0F;
										
										do {
											log.info("Enter the amount you want to withdraw.");
											selectionAmount = Float.parseFloat(scanner.nextLine());
											if (selectionAmount <= 0.0F) {
												log.info("You cannot withdraw an amount of $0 or less.");
											} else if (selectionAmount > sourceAccount.getAvailableBalance()) {
												log.info("Your withdrawal cannot exceed your available balance.");
											}
										} while (selectionAmount <= 0.0F || selectionAmount > sourceAccount.getAvailableBalance());
										
										new TransactionBoImp().addTransaction(new Transaction(
												new TransactionBoImp().getMaxTransactionId() + 1,
												selection,
												user.getFirstName() + " " + user.getLastName(),
												0.0F,
												selectionAmount,
												new Date(),
												-1
												));
										
									} else if (selection != -1) {
										log.info("This is not a valid account number.");
									}
								} while (!(accountNums.contains(selection) || selection == -1));
								break;
							case 4:		// Make deposit
								activeAccounts = new AccountBoImp().getAccountsByUserId(user.getUserId());
								accountNums = new ArrayList<>();
								System.out.println();
								log.info("\nEnter the account number from which you'd like to make a deposit (-1 to return to menu). If you do not see your\n"
										+ "account listed here and you believe this is in error, please contact a representative.\n");
								displayUserActiveAccountsHeader();
								for (Account acc : activeAccounts) {
									accountNums.add(acc.getAccountNumber());
									log.info(padStringRight(Integer.toString(acc.getAccountNumber()), 25)
											+padStringRight(Float.toString(acc.getAvailableBalance()),25)
											+padStringRight(acc.getCreationDate().toString(),25)
											);
								}
								do {
									log.info("Choose an account number or type -1 to return to the customer menu");
									selection = Integer.parseInt(scanner.nextLine());
									if (accountNums.contains(selection)) {
										// source account not required since available balance is not relevant for deposits
										Float selectionAmount = -1.0F;
										
										do {
											log.info("Enter the amount you want to deposit.");
											selectionAmount = Float.parseFloat(scanner.nextLine());											
											if (selectionAmount <= 0.0F) {
												log.info("You cannot deposit an amount of $0 or less.");
											}
											
										} while (selectionAmount <= 0.0F);
										
										new TransactionBoImp().addTransaction(new Transaction(
												new TransactionBoImp().getMaxTransactionId() + 1,
												selection,
												user.getFirstName() + " " + user.getLastName(),
												selectionAmount,
												0.0F,
												new Date(),
												-1
												));
										
									} else if (selection != -1) {
										log.info("This is not a valid account number.");
									}
								} while (!(accountNums.contains(selection) || selection == -1));
								break;
							case 5: 	// Post money transfer
								activeAccounts = new AccountBoImp().getAccountsByUserId(user.getUserId());
								accountNums = new ArrayList<>();
								System.out.println();
								log.info("\nEnter the account number for which you'd like to transfer money from (-1 to return to menu). If you do not see your\n"
										+ "account listed here and you believe this is in error, please contact a representative.\n");
								displayUserActiveAccountsHeader();
								for (Account acc : activeAccounts) {
									accountNums.add(acc.getAccountNumber());
									log.info(padStringRight(Integer.toString(acc.getAccountNumber()), 25)
											+padStringRight(Float.toString(acc.getAvailableBalance()),25)
											+padStringRight(acc.getCreationDate().toString(),25)
											);
								}
								do {
									log.info("Choose an account number or type -1 to return to the employee menu");
									selection = Integer.parseInt(scanner.nextLine());
									if (accountNums.contains(selection)) {
										Account sourceAccount = new AccountBoImp().getAccountByAccountNumber(selection);
										int selectionB = -1;
										do {
											log.info("Enter the account number you want to transfer to.");
											selectionB = Integer.parseInt(scanner.nextLine());
											Float selectionAmount;
											if (new AccountBoImp().accountExists(selectionB)) {
												do {
													log.info("Enter the ammount you want to transfer.");
													selectionAmount = Float.parseFloat(scanner.nextLine());
													if (selectionAmount <= 0.0F) {
														log.info("You cannot transfer an amount of $0 or less.");
													} else if (selectionAmount > sourceAccount.getAvailableBalance()) {
														log.info("Your transfer cannot exceed your available balance.");
													}
												} while (selectionAmount <= 0.0F || selectionAmount > sourceAccount.getAvailableBalance());
												
												Transfer transfer = new Transfer(
															new TransferBoImp().getMaxTransferId() +1,
															selectionAmount,
															selection,
															selectionB,
															0,
															new Date(),
															null
														);
												new TransferBoImp().addNewTransfer(transfer);
												log.info("Transfer request posted. You will be notified when your transfer has been approved.\n");
												
											} else {
												log.info("No such account exists. If you believe this is in error, please contact a representative.");
											}											
											
										} while (selectionB != -1);
										
									} else if (selection != -1) {
										log.info("This is not a valid account number.");
									}
								} while (!(accountNums.contains(selection) || selection == -1));
								
								
								break;
							case 6:		// View incumbent transfers
								System.out.println();
								displayPendingTransfersHeader();
								List<Transfer> transfers = new TransferBoImp().getTransfersByUserId(user.getUserId());
								List<Integer> validTransfers = new ArrayList<>();
								if (!transfers.isEmpty()) {
									for (Transfer t : transfers) {
										validTransfers.add(t.getTransferId());
										log.info(padStringRight(Integer.toString(t.getTransferId()),20)
												+padStringRight(Integer.toString(t.getSource()),25)
												+padStringRight(Integer.toString(t.getDestination()),25)
												+padStringRight(Float.toString(t.getAmount()),25)
												+padStringRight(t.getRequestDate().toString(),25)
												);
									}
									log.info("Select a transfer request number.");
								} else {
									log.info("Your accounts show no pending transfers. Returning to menu...\n");
								}
								
								selection = Integer.parseInt(scanner.nextLine());
								
								if (validTransfers.contains(selection)) {
									Transfer transfer = null;
									
									for (Transfer t : transfers) {
										if (t.getTransferId() == selection) {
											transfer = t;
										}
									}
									log.info("Do you wish to Approve (1) or Reject (0) this transfer request?");
									int selectionB = Integer.parseInt(scanner.nextLine());
									if (selectionB == 0) {
										log.info("Transfer request rejected. Returning to menu.\n");
										new TransferDaoImp().updateTransferStatus(transfer, 2);
									} else if (selectionB == 1) {
										log.info("Transfer request approved. Transaction complete. Returning to menu.\n");
										new TransferDaoImp().updateTransferStatus(transfer, 1);
									}
								} else {
									log.info("You have no transfers with that transfer number. Returning to menu.\n");
								}
								break;
							case 7: 	// Sign out
								runUserLoop = false;
								break;
							case 8:		// Exit application
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
				int newUserId;
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

				try {
					// get new user id
					newUserId = new UserBoImp().getMaxIdUsed();
					
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
				
					new UserBoImp().addNewUser(new User(
							newUserId + 1,	// increment user id to next highest
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
