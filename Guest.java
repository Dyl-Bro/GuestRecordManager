public class Guest{
 private String name;
 private int age;
 private String relationship;
 
 public Guest(){
  name = "Donna";
  age = 43;
  relationship = "Mother";
 }
 
 public Guest(String name_of_guest, int age_of_guest, String relation_to_guest){
  name = name_of_guest;
  age = age_of_guest;
  relationship = relation_to_guest ;
 }
 
 public String toString(){
  String str = name + " , " + age + " , " + relationship;
  return str;
 }
 
 
 public String toFileString(){
  String str = name + "@" + age + "@" + relationship;
  return str;
 }
 
 public String getName(){return name; }
 public String getRelation(){return relationship; }
 public int getAge(){return age;}
}