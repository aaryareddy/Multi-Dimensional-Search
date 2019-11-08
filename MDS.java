/** Starter code for MDS
 *  @author rbk
 * 
 * 
 *  Aarya Vardhan Reddy Paakaala (AXP 170019)
 *	Harshil Hiten Malavia (HHM 180000)
 *	Mrugapphan Kannan (MXK 170014)
 *	Shivapraksh Sivagurunathan (SXS 180104)
 */

// Change to your net id
package axp170019;

// If you want to create additional classes, place them in this file as subclasses of MDS
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import axp170019.MDS.Money;

public class MDS {
    // Add fields of MDS here

	// Total number of products;
	private long size;
	
	//prodMap: Tree map maps the product id to its object reference
	private TreeMap<Long, Product> prodMap;
	
	//dMap: Hash map maps the description id to the set of all the products which contain it.
	private HashMap<Long, HashSet<Product>> dMap;
	
    // Constructors
    public MDS() {
    	prodMap = new TreeMap<Long,Product>();
    	dMap = new HashMap<Long,HashSet<Product>>();
    	size=0;
    }
    
    
    /* Product class implementation
     * initializing default and parameterized constructors implementation
     * compareTo() and toString() methods implementation
     */
    static class Product{
    	private Long id;
    	private Money price;
    	private HashSet<Long> desc;
    	
    	// default constructor 
    	Product(){
    		id = (long) 0;
    		price = new Money();
    		desc = new HashSet<Long>();
    	}
    	
    	//parameterized constructor
    	Product(Long id, Money price, List<Long> desc){
    		this.id = id;
    		this.price = price;
    		this.desc = new HashSet<Long>();
    		
    		for(Long cur: desc) {
    			this.desc.add(cur);
    		}
    	}
    	
    	public long getId() {
    		return this.id;
    	}
    	
    	// Compares two Items on their id
    			public int compareTo(Product other) {
    				// comparison of id
    				return this.id.compareTo(other.id);
    			}
    	
    	public String toString() {
			return "{" + id + ",\t" + price.toString() + ",\t" 
					+ desc.toString() + "}";
		}
    	
    }
    
  

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       
     * @param id the id of Product to be inserted
	 * @param newPrice the price of the product to be inserted 
	 * @param list desc(s) of the Product to be inserted
	 * @return Returns 1 if the item is new, and 0 otherwise.
       Returns 1 if the item is new, and 0 otherwise.
    */
    
    public int insert(long id, Money price, java.util.List<Long> list) {
    	
    	if(prodMap.containsKey(id)) {
    		Product cur = prodMap.get(id);
    		
    		if(!(list == null || list.isEmpty()))  {
    		
    			deleteDMap(id); // removes previous desc - prod pairs from dMap
    			cur.desc.clear(); // removes previous desc of the product
    			
    			for(Long temp : list) // adds new desc of the prod
    				cur.desc.add(temp);
    			
    			insertDMap(id); // adds new desc-prod pair in dMap
    		}
    		
    		cur.price=price;
    		return 0;
    	}
    	
    	Product prod = new Product(id,price,list);
    	prodMap.put(id,prod);
    	insertDMap(id);
    	
    	size++;
	return 1;
    }
    
    
    /**
	 * Helper method: To remove all the descriptions from dMap 
	 * whatever existing descriptions the Item(id) has.
	 * 
	 * Postcondition: need to perform descriptions.clear() from pTree
	 * 
	 * @param id the id of the Item whose description is to be removed
	 */
    private void deleteDMap(Long id){
    	
    	Product cur = prodMap.get(id);
    	for(Long temp: cur.desc) { // iterates the desc list and removes the desc-product pair in dmap
    			dMap.get(temp).remove(cur);
    			
    			if(dMap.get(temp).isEmpty()) // if desc list has no products left, remove it
    				dMap.remove(temp);
    	}
    }
    
    
    /**
	 * Helper method: Inserts desc of the Product(id) in dMap and 
	 * adds mapping from that description to the HashSet 
	 * updated with the Item(id), if needed.
	 * 
	 * Precondition: Product(id) - desc  must be updated in prodMap
	 * 
	 * @param id the id of the Product whose desc is to be inserted
	 */
    private void insertDMap(Long id) {
	
		Product cur = prodMap.get(id);
		
		// for every desc d in cur.desc, update dmap
		for (Long d: cur.desc) {
			
			// When we already have that desc d in dmap
			if (dMap.containsKey(d))
					dMap.get(d).add(cur); // adds or replaces item (hashset)
			
			
			// We need a new HashSet for a mapping from d 
			else {
				HashSet<Product> temphs = new HashSet<Product>();
				temphs.add(cur); // adding the first item in Set
				dMap.put(d, temphs); // inserting the map from d to new Set 
			}
		}
	}
	
    
    /**
	 * find(id): return price of item with given id (or 0, if not found).
	 * @param id the id of the item
	 * @return price of the item, if exists, else 0
	 */
    public Money find(long id) {
    	
    	if(prodMap.containsKey(id)) {
    		return prodMap.get(id).price; //if product found in prodMap, return its price
    	}
    	
    	//if product not found, return 0;
	return new Money();
    }
    

    /**
	 * delete(id): delete product from storage. 
	 * 
	 * @param id the id of the product to be deleted
	 * @return Returns the sum of the long integers that are in the 
	 * description of the product(id), or 0 if such an id does not exist.
	 */
    public long delete(long id) {
    	
    	if(prodMap.containsKey(id)) { // if product id is valid
    		
    		Product cur = prodMap.get(id); //get a reference to the object
    		
    		long sum=0;
    		
    		for(long temp : cur.desc) {
    			
    			// remove cur from the HashSet corresponding to that description temp
    			dMap.get(temp).remove(cur); 
    			
    			// When tempSet is emptied
    			if (dMap.get(temp).isEmpty()) 
    				dMap.remove(temp); // we don't need map for temp anymore
    			
    			sum+=temp;  //find sum of the desc long items
    		}
    		
    		prodMap.remove(id); // remove product from prodMap
    		//cur=null; // initialize the product to null
    		
    		return sum; // return sum
    	}
    	
	return 0;
    }

    /* 
       d. FindMinPrice(n): given a long int, find items whose description
       contains that number (exact match with one of the long ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
     * @param d the description
	 * @return minimum price if that description is present, else 0 (Money).
    */
    public Money findMinPrice(long n) {
    	if(dMap.containsKey(n)) {
    	
    		Money ans = toMoney(Long.MAX_VALUE); //initialize initial ans to infinity
    		HashSet<Product> hs = dMap.get(n); //obtain all the products which contains the given desc
    		for(Product cur : hs) {
    			
    			if(cur.price.compareTo(ans)<0) //iterate and find the min price
    				ans=cur.price;
    		}
    		return ans;
    	}
    	
	return new Money();
    }
    

    /* 
       e. FindMaxPrice(n): given a long int, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
       
     * @param d the description
	 * @return maximum price if that description is present, else 0 (Money).
    */
    public Money findMaxPrice(long n) {
    	if(dMap.containsKey(n)) {
    		
    		Money ans =new Money(); //initialize initial ans to -infinity
    		HashSet<Product> hs = dMap.get(n); //obtain all the products which contains the given desc
    		for(Product cur : hs) {
    			
    			if(cur.price.compareTo(ans)>0) //iterate and find the max price
    				ans=cur.price;
    		}	
    		return ans;
    	}
    	
	return new Money();
    }

   

    /**
	 * findPriceRange(d, low, high): given a long integer d, find the 
	 * number of products whose description contains d, and in addition, 
	 * their prices fall within the given range, [low, high]. 
	 * 
	 * @param d the description
	 * @param low the lower limit of price
	 * @param high the upper limit of price
	 * @return count of products whose prices are in [low, high] 
	 *    and has description d 
	 */
    public int findPriceRange(long n, Money low, Money high) {
    	if(low.compareTo(high)>0)
    		return 0;
    	
    	if(dMap.containsKey(n)) {
    		int count=0;
    	
    		HashSet<Product> hs = dMap.get(n);
    		
    		for(Product cur : hs) {
    			
    			if (! (cur.price.compareTo(low) < 0 || cur.price.compareTo(high) > 0))
    				count++;
    		}
    		return count;
    	}
    	
	return 0;
    }

    /* 
       g. PriceHike(l,h,r): increase the price of every product, whose id is
       in the range [l,h] by r%.  Discard any fractional pennies in the new
       prices of items.  Returns the sum of the net increases of the prices.
    */
    public Money priceHike(long l, long h, double rate) {
    	
    	if(l>h)
    		return new Money();
    	
    	if(prodMap.firstKey().compareTo((Long)h)>0)
    		return new Money();
    	
    	if(prodMap.lastKey().compareTo((long)l)<0)
    			return new Money();
    	
    	Long startId = prodMap.ceilingKey(l);
    	Long endId = prodMap.floorKey(h);
    	
    	long netInc=0;
    	if(startId==endId) {
    		netInc+= priceHikeProduct(startId,rate);
    		return toMoney(netInc);
    	}
    	
    	Map<Long, Product> sortedSubMap = prodMap.subMap(startId, true, endId, true);
		
		for (Long cur: sortedSubMap.keySet()) {
			// increment the price of each Item(i) with same rate
			netInc += priceHikeProduct(cur, rate);
		}
		return toMoney(netInc);
    }
    
    
    
    /**
	 * Increments price of product(id) by rate%.
	 * @param id the id of the product
	 * @param rate the rate by which price increases
	 * @return the incremented price in long format
	 */
	private long priceHikeProduct(Long id, double rate) {
		
		long currentPrice = toLong(prodMap.get(id).price);
		
		long increment = (long) Math.floor((currentPrice * rate) / 100);
		long newPrice = currentPrice + increment;
		
		// only to change price with newPrice
		insert(id, toMoney(newPrice), null); 
		
		return increment;
	}
    
    
    /**
	 * Converts money from long format to Money. COULD BE PART of MONEY
	 * @param m the input money in long
	 * @return equivalent Money
	 */
	private Money toMoney(long m) {
		
		int cents = (int) (m % 100); 
		long dollars = m / 100;
		
		return new Money(dollars, cents);
	}
	
	

	/**
	 * Converts money from Money to long format. COULD BE PART of MONEY
	 * @param m input money in Money
	 * @return equivalent money in long
	 */
	private long toLong(Money m) {
		
		long money = m.dollars();
		money = money * 100;
		money = money + m.cents();
		
		return money;
	}
	
	/**
	 * removeNames(id, list): Remove elements of list from the 
	 * description of id. It is possible that some of the items in 
	 * the list are not in the id's description. 
	 * 
	 * @param id the Product(id) whose desc Names is to be removed
	 * @param list the Names to be removed
	 * @return Return the sum of the numbers that are actually deleted 
	 * from the description of id. 
	 * Return 0 if there is no such id.
	 */
    public long removeNames(long id, java.util.List<Long> list) {
    	if(prodMap.containsKey(id)) {
    		long sum=0;
    		
    		Product cur = prodMap.get(id);  //get a reference to the product
    		
    		for( long temp: list) { //iterate input list
    			
    			if(cur.desc.contains(temp)) { // check if the element is present in the product desc
    				
    				dMap.get(temp).remove(cur); //remove product from dMap desc- prod mapping
    				
    				if(dMap.get(temp).isEmpty()) // if current desc has no products in dmap, remove it
    					dMap.remove(temp);
    				
    				sum+=temp; // update sum
    				cur.desc.remove(temp); // remove the element from the product desc
    			}
    			
    		}
    		return sum; //return the sum of elements actually removed from the product desc
    	}
    	
    	//if id is not present in prodMap
	return 0;
    }
    
    
    /**
	 *Prints the existing prodMap for debugging purpose
	 */
	private void printTreeMap() {
		
		System.out.println("Current TreeMap: ");
		System.out.println("ID\tItem");
		System.out.println();
		
		for (Long k: prodMap.keySet()) {
			
			System.out.print(k);
			System.out.print("\t"+prodMap.get(k).toString());
			System.out.println();
		}
	}
	
	
	/**
	 * Prints the existing dMap for debugging purpose
	 */
	private void printDMap() {
		
		System.out.println("Current HashMap: ");
		System.out.println("Description\tHashSet");
		System.out.println();
		
		for (Long k: dMap.keySet()) {
			
			System.out.print(k);
			System.out.print("\t"+dMap.get(k).toString());
			System.out.println();
		}
	}
	
	public long size() {
		return size;
	}
	
    
	// Do not modify the Money class in a way that breaks LP3Driver.java
		public static class Money implements Comparable<Money> { 
			
			long d; // dollars
			int c; // cents
			
			public Money() { 
				d = 0; 
				c = 0; 
			}
			
			public Money(long d, int c) { 
				this.d = d; 
				this.c = c; 
			}
			
			public Money(String s) {
				String[] part = s.split("\\.");
				int len = part.length;
				
				if(len < 1) { 
					d = 0; 
					c = 0; 
				}
				
				else if(part.length == 1) { 
					d = Long.parseLong(s); 
					c = 0; 
				}
				
				else { 
					d = Long.parseLong(part[0]); 
					c = Integer.parseInt(part[1]); 
				}
			}
			
			public long dollars() { 
				return d; 
			}
			
			public int cents() { 
				return c; 
			}
			
			public String toString() { 
				//if (c < 10)
				//	return d + ".0" + c;
				return d + "." + c; 
			}

			@Override
			public int compareTo(Money other) {
				// Complete this, if needed: Yeah for sure!

				if (this.d < other.d) 
					return -1;
				
				else if (this.d > other.d) 
					return 1;
				
				// When we have exact same dollar values
				else {
					
					if (this.c < other.c) 
						return -1;
					
					else if (this.c > other.c) 
						return 1;
					
					// same exact Money values 
					else 
						return 0;
				}
			}
		}
}
