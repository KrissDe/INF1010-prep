// A
class Konte2014 {
  public static void main(String[] args){
    Person p1 = new Person("Steve");
    Person p2 = new Person("Alice");
    
    Student s1 = new Student("Anna", "123");
    Student s2 = new Student("Ali", "123");
    Student s3 = new Student("Kari", "456");
    
    Apple a1 = new Apple();
    Apple a2 = new Apple();
    
    Kiwi k1 = new Kiwi();
    Kiwi k2 = new Kiwi();
    
    Banan b1 = new Banan();
    
    int lineCounter = 1;
    
    ObjectScope<Object> os1 = new ObjectScope<Object>();
    os1.insert((Person) p1);
    os1.insert((Person) p2);
    os1.insert((Student) s1);
    os1.insert((Student) s2);
    os1.insert((Student) s3);
    os1.insert((Apple) a1);
    os1.insert((Apple) a2);
    os1.insert((Kiwi) k1);
    os1.insert((Kiwi) k2);
    os1.insert((Banan) b1);
    
    /*while(!os1.empty()){
      
      System.out.println(counter + ": " + os1.get());
      counter++;
    }*/
    

  }
}

class ObjectScope<Object> {
  private int size;
  private Node head;

  public void insert(Object obj){
    Node newNode = new Node(obj);
    Node currentNode = head;
    
    if(empty()){
      head = newNode;
      size++;
    }
    
    while(currentNode.next != null){
      if(!currentNode.data.compare(newNode.data)){
	currentNode = currentNode.next;
      }else{
	//how to do nothing?
      }
    }
    
    currentNode.next = newNode;
    size++;
  }
  
  
  
  public void remove(Object obj){
    Node helpNode = new Node(obj);
    
    if(!empty()) {
      if(head.data.compare(obj)){
	head = head.next;
	size--;
      }
      Node currentNode = head;
      Node prev = null;
      
      while(currentNode != null && !currentNode.data.compare(obj)){
	prev = currentNode;
	currentNode = currentNode.next;
      }
      
      if(currentNode.data.compare(obj)){
	prev.next = currentNode.next;
	size--;
      }
            
    }
  }
  
  public Object get(){
    if(empty()){
      return null;
    }else{
      return head.data;
    }
  }
  
  public void moveAllFromScope(ObjectScope<Object> os){
    
  }
  
  public boolean empty() {
    return size == 0;
  }
  
  public boolean isEqual(Object obj) {
    return this.data.compare(obj);
  }
  
  public int size() {
    return size;
  }
   
  
  private class Node {
    Object data;
    Node next;
    int size;
    
    Node(Object o) {
      data = o;
      next = null;
    }
  }
}

class Person { //equal on name
  protected String name;
  
  Person(String n){
    name = n;
  }
  
  public String id() {
    return "Person with name " + this.name;
  }
  
  public boolean compare(Object o){
    if(o instanceof Person){
      return this.name.equals((Person) o.name);
    }
    return false;
  }
  
}

class Student extends Person { //equal on nr
  protected String studentNr;
  
  Student(String name, String nr){
    super(name);
    studentNr = nr;
  }
  
  public String id() {
    return "Student with name " + this.name + " and student number " + this.studentNr;  
  }    
  
  public boolean compare(Object o){
    if(o instanceof Student) {
      return this.studentNr.equals((Student) o.studentNr);
    }
    return false;
  }
}

abstract class Frukt { //equal on fruit type
  protected String type;
  
  Frukt(String type){
    this.type = type;
  }
  
  public String id() {
    return this.type;
  }
  
  public boolean compare(Object o){
    if(o instanceof Frukt) {
      return this.type.equals((Frukt) o.id());
    }
    return false;
  }
}

class Banan extends Frukt {
  Banan(){
    super("Banan");
  }
}

class Kiwi extends Frukt {
  Kiwi() {
    super("Kiwi");
  }
}

class Apple extends Frukt {
  Apple() {
    super("Apple");
  }
}