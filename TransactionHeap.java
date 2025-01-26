import java.util.NoSuchElementException;

/**
 * Class for implementing a heap/priority queue on Transactions
 */
public class TransactionHeap {
	private Transaction[] transactions;
	private int size;

	/**
	 * Initializes transactions array with size capacity
	 * 
	 * @param capacity the length of the transactions heap array
	 */
	public TransactionHeap(int capacity) {
		transactions = new Transaction[capacity];
		size = 0;
	}

	/**
	 * This method adds a transaction to the heap if space allows.
	 * 
	 * @param transaction the transaction to add to the heap
	 * @throws IllegalStateException if the TransactionHeap is full.
	 */
	public void addTransaction(Transaction transaction) {
	  if (this.size >= this.transactions.length)
	    throw new IllegalStateException();
	  
	  transactions[size] = transaction;
	  size++;
	  
	  heapifyUp(size-1);
	}

	/**
	 * Reinforces the heap rules after adding a Transaction to the end
	 * 
	 * @param index the index of the new Transaction
	 */
	public void heapifyUp(int index) {
		while (index > 0) {
		  int parent = (index - 1) / 2;
		  
		  if (transactions[index].compareTo(transactions[parent]) > 0) {
		    swapTransactions(index , parent);
		    j
		    index = parent;
		  }
		  else
		    break;
		  
		}
	}
	
	private void swapTransactions(int t1 , int t2) {
	  Transaction hold = transactions[t1];
      transactions[t1] = transactions[t2];
      transactions[t2] = hold;
	}

	/**
	 * Removes the next transaction from the priority queue
	 * 
	 * @return the next transaction in the priority queue
	 * @throws NoSuchElementException if there are no transactions in the heap
	 */
	public Transaction getNextTransaction() {
	  if (isEmpty()) {
        throw new NoSuchElementException("Heap is empty.");
    }
	  Transaction root = transactions[0];
	  
	  transactions[0] = transactions[size - 1];
	  transactions[size - 1] = null;
	  size--;
	  
	  heapifyDown(0);
	  return root;
	}

	/**
	 * Enforces the heap conditions after removing a Transaction from the heap
	 * 
	 * @param index the index whose subtree needs to be heapified
	 */
	public void heapifyDown(int index) {
	  int left = 2 * index + 1;
	  int right = 2 * index + 2;
	  int largest = index;
	  
	  if (left < size && transactions[left].compareTo(transactions[largest]) > 0)
	    largest = left;
	  
	  if (right < size && transactions[right].compareTo(transactions[largest]) > 0) 
        largest = right;
	  
	  if (largest != index) {
	    swapTransactions(index , largest);
	        	        
	  heapifyDown(largest);
	  }


	}

	/**
	 * Returns the highest priority transaction without removing it from the heap.
	 * 
	 * @return the highest priority transaction without removing it from the heap.
	 * @throws NoSuchElementException if there are no transactions in the heap
	 */
	public Transaction peek() {
	  if (isEmpty()) {
        throw new NoSuchElementException("Heap is empty.");
      }
      return transactions[0];
	}

	/**
	 * Getter method for the heap size
	 * 
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Tells if the heap has any elements in it
	 * 
	 * @return whether or not the heap is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * PROVIDED Creates and returns a deep copy of the heap's array of data.
	 * 
	 * @return the deep copy of the array holding the heap's data
	 */
	public Transaction[] getHeapData() {
		Transaction[] list = new Transaction[this.transactions.length];
		for (int i = 0; i < list.length; i++)
			list[i] = this.transactions[i];
		return list;
	}

}
