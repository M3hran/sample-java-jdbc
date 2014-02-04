JCC = javac
JFLAGS = -g
default: proj.class Customer.class Branch.class Credit.class Deal.class Dealtype.class Interest.class Location.class Merchant.class Purchase.class Review.class Voucher.class 
Voucher.class: Voucher.java
	$(JCC) $(JFLAGS) Voucher.java

Review.class: Review.java
	$(JCC) $(JFLAGS) Review.java

Purchase.class: Purchase.java
	$(JCC) $(JFLAGS) Purchase.java

Merchant.class: Merchant.java
	$(JCC) $(JFLAGS) Merchant.java
	
Location.class: Location.java
	$(JCC) $(JFLAGS) Location.java

Interest.class: Interest.java
	$(JCC) $(JFLAGS) Interest.java

Dealtype.class: Dealtype.java
	$(JCC) $(JFLAGS) Dealtype.java

Deal.class: Deal.java
	$(JCC) $(JFLAGS) Deal.java

Credit.class: Credit.java
	$(JCC) $(JFLAGS) Credit.java

Branch.class: Branch.java
	$(JCC) $(JFLAGS) Branch.java

Customer.class: Customer.java 
	$(JCC) $(JFLAGS) Customer.java

proj.class: proj.java
	$(JCC) $(JFLAGS) proj.java

clean:
	$(RM) *.class
