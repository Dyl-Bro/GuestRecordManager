 import java.util.*;
import java.io.*;

public class GuestRecordscopy{
    
    static Scanner keyb = new Scanner(System.in);
    
    public static void main (String[]args){
        if(args.length<1){
            System.out.println("Enter a file name.");
            System.exit(0);
        }
        String filename = args[0];
        List<Guest> guestList = GetGuestInfo(filename);
        System.out.println("There are "+guestList.size()+" guests");
        ViewtheGuests(guestList);
        USEROPTIONS(guestList, filename);
    }
  
    static void ViewtheGuests(List<Guest> guestList){
        for(Guest a : guestList){
            System.out.println(" INDEX: " + guestList.indexOf(a) + " ELEMENT: " + a );
        }
    }
    
    static List<Guest> GetGuestInfo(String filename){
        List<Guest> guestList = new ArrayList<>();
        try{
            File inFile = new File(filename);
            Scanner inscan = new Scanner(inFile);
            String dataLine;
            while(inscan.hasNext()){
                dataLine = inscan.nextLine();
                String [] parts = dataLine.split("@");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String relationship = parts[2];
                Guest g = new Guest(name, age, relationship);
                guestList.add(g);
            }         
            inscan.close();
        }catch(Exception e ){
            System.out.println("File dosen't exist. Creating empty list.");            
        }
        return guestList;
    }
 
    static void USEROPTIONS(List<Guest> guestList, String filename){
        System.out.println("Would you like to (A)dd, (R)emove, (S)earch, (O)rganize the list, (D)isplay drinking age guests , (F)ind oldest and youngest, or (Q)uit the program?");
        char answer = keyb.next().charAt(0);
        do{
            if(answer=='a'||answer=='A'){ Add(guestList);}
            if(answer=='r'||answer=='R'){Delete(guestList);}
            if(answer=='f'||answer=='F'){Find(guestList);}
            if(answer=='s'||answer=='S'){Search(guestList);}
            if(answer=='d'||answer=='D'){WhoCanDrink(guestList);}
            if(answer=='o'||answer=='O'){
                System.out.println("Would you like to organzie by (A)ge or (N)ame and age? ");
                answer=keyb.next().charAt(0);
                if(answer=='a'||answer=='A'){
                    byOldestToYoungest(guestList);
                }
                if(answer=='n'||answer=='N'){
                    byNameandAge(guestList);
                }
            }
            System.out.println("Would you like to (A)dd, (R)emove, (S)earch, (O)rganize the list, (D)isplay drinking age guests, (F)ind oldest and youngest, or (Q)uit the program?");
            answer = keyb.next().charAt(0);
        }while(!(answer=='q'||answer=='Q'));
        SaveTheRecord(filename, guestList); 
    }
 
    static List<Guest> Add(List<Guest> guestList){
        System.out.println("Enter the name of the guest. ");
        keyb.nextLine();
        String name = keyb.nextLine();
        System.out.println("Enter an age for the guest. ");
        int age = keyb.nextInt();
        System.out.println("Enter the guests' relation to you. ");
        keyb.nextLine();
        String relationship = keyb.nextLine();
        Guest g = new Guest(name, age, relationship);
        guestList.add(g);
        System.out.println(g.toString());
        System.out.println("NUMBER OF GUESTS RECORDED: " + guestList.size());
        for(Guest a : guestList){
            System.out.println(" INDEX: " + guestList.indexOf(a) + " ELEMENT: " + a );
        }
       
       return guestList;    
    }
 
    static List<Guest> Delete(List<Guest> guestList){
      System.out.println("Enter a record index to delete. ");
      int ndx = keyb.nextInt();
      System.out.println("Is this the element you'd like to delete? ------>" + guestList.get(ndx));
      char response = keyb.next().charAt(0);
      if(!(response=='y' || response=='Y')){
          System.out.println("Try again. Enter the correct index this time. ");
          ndx = keyb.nextInt();
      }
      guestList.remove(ndx);
      System.out.println("Record deleted. ");
      System.out.println("This is the new record: ");
      for(Guest a : guestList){
          System.out.println(" INDEX: " + guestList.indexOf(a) + " ELEMENT: " + a );
      }
     
     return guestList;
    }
 
    static List<Guest> Search(List<Guest> guestList){
        System.out.println("Enter a name to Search for. ");
        String name = keyb.next();
        int OccurCount = 0;
        for(Guest a : guestList){
           if(a.getName().contains(name)){
              OccurCount++;
              System.out.println(a);
           }
         }
        System.out.println("There were a total of " + OccurCount + " guest(s) with the name " + "'" + name + "'" + " found. ");
        
        return guestList;
    }
 
    static List<Guest> Find(List<Guest> guestList){
      Guest guests = Collections.max(guestList, Comparator.comparing(s -> s.getAge()));
      System.out.println("THE OLDEST GUEST IS: " + guests);
      guests = Collections.min(guestList, Comparator.comparing(s -> s.getAge()));
      System.out.println("THE YOUNGEST GUEST IS: " + guests); 
      
      return guestList;
    }
    
    static void SaveTheRecord(String filename, List<Guest> guestList){
     try{
      PrintWriter pw = new PrintWriter(filename);
      for(Guest a : guestList){
       pw.println(a.toFileString());
      }
      pw.close();
      System.out.println("Entered records saved to file " + filename);
      }catch(FileNotFoundException e ){
      System.out.println("Could not open file.");
      }
    }
 
    static void byOldestToYoungest(List<Guest> guestList){
     Comparator<Guest> byAge = new Comparator<>(){
      @Override
      public int compare(Guest a, Guest b){
       return b.getAge() - a.getAge();
      }  
     };
     
     List<Guest> copyOfList = new ArrayList<>(guestList);
     copyOfList.sort(byAge);
     System.out.println("GUESTS IN ORDER FROM OLDEST TO YOUNGEST: ");
     for(Guest a:copyOfList){
      System.out.println("INDEX: " + copyOfList.indexOf(a) + " Element: " + a);
     }
     
    }
 
    static void byNameandAge(List<Guest> guestList){
       Comparator<Guest> byNameandAge = new Comparator<>(){
        @Override
        public int compare(Guest a, Guest b){
         if(a.getName().equals(b.getName())){
          return b.getAge()-a.getAge();
         }
              return a.getName().compareTo(b.getName());
        }
       };
       List<Guest> copyOfList = new ArrayList<>(guestList);
       copyOfList.sort(byNameandAge);
       System.out.println("GUESTS IN ORDER ALPHABETICALLY AND NUMERICALLY: ");
       for(Guest a: copyOfList){
        System.out.println("INDEX: " + copyOfList.indexOf(a) + " ELEMENT: " + a);
       }
      }
 
    static void WhoCanDrink(List<Guest> guestList){
       System.out.println("THESE ARE THE GUESTS WHO CAN DRINK: ");
       for(Guest a: guestList){
        if(a.getAge()>=21){
         System.out.println(a);
        }
       }
      }
}

