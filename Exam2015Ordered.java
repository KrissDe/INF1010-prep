// Task 1a. Definitions

interface G {}
abstract class Q {}
abstract class QA extends Q{}
class QK extends Q implements G{}
class QAB extends QA implements G{}
class QAC extends QA {}
class QABC extends QAB {}

// Task 1b. Main-method implementation

class MainClass {
  public static void main(String[] args) {
    QABC abc = new QABC();
    QAC ac = new QAC();
    QAB ab1 = new QAB();
    QK k = new QK();
    
    Q[] objekter = new Q[5];
    objekter[0] = abc;
    objekter[1] = ac;
    objekter[2] = ab1;
    objekter[3] = k;
  }
}

// Task 2. Data structure (see 2015-2.jpg)

// Task 3. See Exam2015.java

// Task 4. GUI
