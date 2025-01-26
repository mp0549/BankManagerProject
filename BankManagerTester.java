//import Transaction.Priority;
//import Transaction.Type;

import java.util.NoSuchElementException;

public class BankManagerTester {

	/**
	 * Tests the constructor for the Transaction class.
	 * 
	 * @return true if the test passes
	 */
	public static boolean testTransactionConstructor() {
	  Account user = new Account();
	  Transaction t1 = new Transaction(user , 30 , Transaction.Type.DEPOSIT);
	  user.deposit(30);
	  Transaction t2 = new Transaction(user , 50 , Transaction.Type.LOAN_APPLICATION);
	  Transaction t3 = new Transaction(user , 500 , Transaction.Type.LOAN_APPLICATION);
	  
	  
		// (1)Verify if the transaction was created correctly and assigned the
		// correct priority.
	  if (!(t1.getPriority() == Transaction.Priority.HIGH)) return false;
//	  System.out.println(1);
	  if (!(t2.getPriority() == Transaction.Priority.URGENT)) return false;
//	  System.out.println(2);
	  if (!(t3.getPriority() == Transaction.Priority.LOW)) return false;
//	  System.out.println(3);
		// (2)Verify the only correct amounts can be used, otherwise verify that an
		// exception is thrown.
		
	  try {
	    Transaction t4 = new Transaction(user , -1 , Transaction.Type.DEPOSIT);
	    return false;
	  } catch(IllegalArgumentException e) {}
	  return true;
	}

	/**
	 * tests the Transaction.compareTo when the priorities are different
	 * 
	 * @return true if the test passes
	 */
	public static boolean testTransactionCompareToPriority() {
		//  verify that the transaction with the higher priority is greater than the
		// one with the lower.
	  Account user1 = new Account();
      Transaction t1 = new Transaction(user1 , 30 , Transaction.Type.DEPOSIT);
      user1.deposit(30);
      Account user2 = new Account();
      user2.deposit(100);
      Transaction t2 = new Transaction(user1 , 50 , Transaction.Type.LOAN_APPLICATION);
      Transaction t3 = new Transaction(user2 , 500 , Transaction.Type.LOAN_APPLICATION);
      
      
		return t1.compareTo(t2) < 0 && t2.compareTo(t3) > 0 && t1.compareTo(t3) > 0;
	}

	/**
	 * tests the Transaction.compareTo when the priorities are the same
	 * 
	 * @return true if the test passes
	 */
	public static boolean testTransactionCompareToAccountBalance() {
		// TODO verify that when the transaction has the same priority, the account
		// balance is used.
	  Account user1 = new Account();
      Transaction t1 = new Transaction(user1 , 30 , Transaction.Type.DEPOSIT);
      Account user2 = new Account();
      user2.deposit(100);
      Transaction t2 = new Transaction(user2 , 30 , Transaction.Type.DEPOSIT);
      return t1.compareTo(t2) < 0;
	}

	/**
	 * Tests the TransactionHeap.addTransaction() method
	 * 
	 * @return true if the test passes
	 */
	public static boolean testAddTransactionToHeap() {
	    TransactionHeap th = new TransactionHeap(10);
	    Account user1 = new Account();
	    Account user2 = new Account();

	    for (int i = 0; i < 5; i++) {
	        th.addTransaction(new Transaction(user1, 30, Transaction.Type.DEPOSIT));
	        th.addTransaction(new Transaction(user2, 50, Transaction.Type.WITHDRAWAL));
	    }

	    Transaction[] tl = th.getHeapData();
	    Transaction topTransaction = tl[0];

	    if (topTransaction.getType() == Transaction.Type.DEPOSIT && topTransaction.getAmount() == 30) {
	        return true;
	    }
	    
	    return false;
	}

	/**
	 * Tests the TransactionHeap.heapifyUp() and TransactionHeap.heapifyDown()
	 * methods
	 * 
	 * @return true if the test passes
	 */
	public static boolean testHeapify() {
	    TransactionHeap th = new TransactionHeap(10);
	    Account user1 = new Account();
	    Account user2 = new Account();
	    
	    th.addTransaction(new Transaction(user1, 30, Transaction.Type.DEPOSIT)); 
	    th.addTransaction(new Transaction(user2, 50, Transaction.Type.WITHDRAWAL));
	    th.addTransaction(new Transaction(user1, 100, Transaction.Type.DEPOSIT));
	    th.addTransaction(new Transaction(user2, 70, Transaction.Type.WITHDRAWAL));
	    
	    // (1) verify that the heap property is restored after adding a transaction
        // to the heap
	    Transaction root = th.peek();
	    System.out.println(root.getAmount());
	    if (root.getType() != Transaction.Type.DEPOSIT || root.getAmount() != 100) return false;
	    th.getNextTransaction();
	    System.out.println(1);
	    root = th.peek();
	    if (root.getType() != Transaction.Type.DEPOSIT || root.getAmount() != 30) return false;
	    th.getNextTransaction();
	    System.out.println("mkid");
	    // (2)verify that the heap property is restored after removing a transaction
        // from the heap
	    root = th.peek();
	    if (root.getType() != Transaction.Type.WITHDRAWAL || root.getAmount() != 70) return false;
	    th.getNextTransaction();
	    
	    if (!th.isEmpty()) return false;
	    
		
		
		return true;
	}

	/**
	 * Tests the TransactionHeap.getNextTransaction() method
	 * 
	 * @return true if the test passes
	 */
	public static boolean testGetNextTransactionFromHeap() {
	    // Create a transaction heap with a capacity of 10
	    TransactionHeap transactionHeap = new TransactionHeap(10);
	    
	    // Create accounts with different balances
	    Account user1 = new Account(165432,500);  // Account with balance 500
	    Account user2 = new Account(9876545, 1000); // Account with balance 1000
	    Account user3 = new Account(345678765, 1500); // Account with balance 1500
	    
	    // Create transactions with different types and amounts
	    Transaction withdrawalTransaction = new Transaction(user1, 100, Transaction.Type.WITHDRAWAL);   // NORMAL priority
	    Transaction depositTransaction = new Transaction(user2, 500, Transaction.Type.DEPOSIT);          // HIGH priority
	    Transaction loanApplicationTransaction = new Transaction(user3, 3000, Transaction.Type.LOAN_APPLICATION); // URGENT priority

	    // Add transactions to the heap
	    transactionHeap.addTransaction(withdrawalTransaction);
	    transactionHeap.addTransaction(depositTransaction);
	    transactionHeap.addTransaction(loanApplicationTransaction);

	    // (1) Verify that the highest priority transaction is returned first
	    Transaction nextTransaction = transactionHeap.getNextTransaction();
	    if (nextTransaction.getPriority() != Transaction.Priority.URGENT) {
	        System.out.println("Expected URGENT priority, but got " + nextTransaction.getPriority());
	        return false;  // URGENT priority transaction should come first
	    }

	    // (2) Verify that the next transaction is HIGH priority
	    nextTransaction = transactionHeap.getNextTransaction();
	    if (nextTransaction.getPriority() != Transaction.Priority.HIGH) {
	        System.out.println("Expected HIGH priority, but got " + nextTransaction.getPriority());
	        return false;  // HIGH priority transaction should come next
	    }

	    // (3) Verify that the last transaction is NORMAL priority
	    nextTransaction = transactionHeap.getNextTransaction();
	    if (nextTransaction.getPriority() != Transaction.Priority.NORMAL) {
	        System.out.println("Expected NORMAL priority, but got " + nextTransaction.getPriority());
	        return false;  // NORMAL priority transaction should come last
	    }

	    // (4) Ensure the heap is empty after all transactions are retrieved
	    if (!transactionHeap.isEmpty()) {
	        System.out.println("Expected heap to be empty after retrieving all transactions.");
	        return false;
	    }

	    return true;  // Passes if all assertions are met
	}


	/**
	 * Tests the BankManager.queueTransaction() method
	 * 
	 * @return true if the test passes
	 */
	public static boolean testQueueTransaction() {
	// Create a BankManager and different Account instances
	    BankManager manager = new BankManager(129);
	    Account user1 = new Account();
	    Account user2 = new Account();
	    
	    // Queue transactions with different amounts
	    manager.queueTransaction(new Transaction(user1, 500, Transaction.Type.WITHDRAWAL));
	    manager.queueTransaction(new Transaction(user2, 2000, Transaction.Type.DEPOSIT)); 
	    manager.queueTransaction(new Transaction(user1, 1500000, Transaction.Type.LOAN_APPLICATION));
	    
	    // (1) Verify that a transaction with amount < 1000 is added to the "low" heap
	    if (manager.low.getSize() != 1) return false;  
	    if (manager.low.peek().getAmount() != 500) return false;
	    
	    // (2) Verify that a transaction with amount >= 1000 but < 1000000 is added to the "medium" heap
	    if (manager.medium.getSize() != 1) return false; 
	    if (manager.medium.peek().getAmount() != 2000) return false; 
	    
	    // (3) Verify that a transaction with amount >= 1000000 is added to the "high" heap
	    if (manager.high.getSize() != 1) return false; 
	    if (manager.high.peek().getAmount() != 1500000) return false;
	    
	    return true;
	}

	/**
	 * Tests the BankManager.performTransaction() method
	 * 
	 * @return true if the test passes
	 */
	public static boolean testPerformTransaction() {
	  BankManager manager = new BankManager(120);
	    Account user1 = new Account();
	    Account user2 = new Account();
	    
	    // Queue a transaction
	    manager.queueTransaction(new Transaction(user1, 500, Transaction.Type.DEPOSIT)); // Low heap
	    
	    // (1) Verify that the transaction is removed from the correct heap
	    if (manager.low.getSize() != 1) return false; 
	    manager.performTransaction(); 
	    if (manager.low.getSize() != 0) return false; 
	    
	    // (2) Verify the transaction is processed correctly:
	    // (2a) The transaction (if it’s a deposit) should be processed successfully (e.g., adding funds)
	    if (user1.getBalance() != 500) return false;
	    
	    // (2b) Verify that an exception is thrown when necessary
	    try {
	        manager.performTransaction();
	        return false;
	    } catch (NoSuchElementException e) {
	        return true;
	    }
	}

	/**
	 * Tests the BankManager.peekHighestPriorityTransaction() method
	 * 
	 * @return true if the test passes
	 */
	public static boolean testPeekHighestPriorityTransaction() {
	  BankManager manager = new BankManager(120);
	    Account user1 = new Account();
	    Account user2 = new Account();
	    
	    // Queue some transactions
	    manager.queueTransaction(new Transaction(user1, 500, Transaction.Type.WITHDRAWAL)); 
	    manager.queueTransaction(new Transaction(user2, 1000, Transaction.Type.DEPOSIT));
	    manager.queueTransaction(new Transaction(user1, 10000, Transaction.Type.LOAN_APPLICATION)); 
	    System.out.println(2);
	    // (1) Verify that the highest priority transaction is returned
	    Transaction highest = manager.peekHighestPriorityTransaction();
	    if (highest.getAmount() != 10000 || highest.getType() != Transaction.Type.LOAN_APPLICATION) {
	        return false; 
	    }
	    System.out.println(1);
	    // (2) Verify that the transaction is not removed
	    if (manager.high.getSize() != 1) return false;
	    
	    return true;
	}

	public static void main(String[] args) {
		System.out.println("Transaction Constructor Tests: " + (testTransactionConstructor() ? "PASS" : "FAIL"));
		System.out.println("CompareTo Tests for Priority: " + (testTransactionCompareToPriority() ? "PASS" : "FAIL"));
		System.out.println(
				"CompareTo Tests for Account Balance: " + (testTransactionCompareToAccountBalance() ? "PASS" : "FAIL"));
		System.out.println("Testing Add Transaction to Heap: " + (testAddTransactionToHeap() ? "PASS" : "FAIL"));
		System.out.println("Testing Heapify: " + (testHeapify() ? "PASS" : "FAIL"));
		System.out.println("Testing Get Next Transaction: " + (testGetNextTransactionFromHeap() ? "PASS" : "FAIL"));
		System.out.println("Testing Queue Transaction: " + (testQueueTransaction() ? "PASS" : "FAIL"));
		System.out.println("Testing Perform Transaction: " + (testPerformTransaction() ? "PASS" : "FAIL"));
		System.out.println("Testing Peek Highest Priority Transaction: "
				+ (testPeekHighestPriorityTransaction() ? "PASS" : "FAIL"));
	}
}
