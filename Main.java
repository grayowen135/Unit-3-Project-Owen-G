import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Main {

  // Sorting method //
  public static void sort_method(ArrayList<String> array_names, ArrayList<Integer> array_scores, int score_size) {
    for (int i = 0; i < score_size-1; i++) {
      for (int j = 0; j < score_size-1-i; j++) {
        if (array_scores.get(j) < array_scores.get(j+1)) {
          int temp_scores = array_scores.get(j);
          String temp_names = array_names.get(j);
          array_scores.set(j, (array_scores.get(j+1)));
          array_names.set(j, (array_names.get(j+1)));
          array_scores.set((j+1), temp_scores);
          array_names.set((j+1), temp_names);
        }
      }
    }
  }

  public static void add_method(ArrayList<String> array_names, ArrayList<Integer> array_scores, Scanner input) {
    System.out.println("Whose score is this?");
    input.nextLine();
    array_names.add(input.nextLine());
    System.out.println("What is their score?");
    array_scores.add(input.nextInt());
  }

  // Delete method //
  public static void delete_method(ArrayList<String> array_names, ArrayList<Integer> array_scores, Scanner input, int score_size) {
    byte i = 0;
    System.out.println("Whose score do you want to remove?");
    input.nextLine();
    String delete_key = input.nextLine();
    for (i = 0; i < score_size-1; i++) {
      if (array_names.get(i).equalsIgnoreCase(delete_key)) {
        array_names.remove(i);
        array_scores.remove(i);
      }
    }
  }

  // Search method //
  public static void search_method(ArrayList<String> array_names, ArrayList<Integer> array_scores, Scanner input, int score_size) {
    System.out.println("What name do you want to search?");
    input.nextLine();
    String search_key = input.nextLine();
    for (int i = 0; i < score_size-1; i++) {
      if (array_names.get(i).equalsIgnoreCase(search_key)) {
        System.out.println(search_key + " is ranked # " + (i + 1));
      }
    }
  }

  // Main //
  public static void main(String[] args) throws IOException {
    Scanner input = new Scanner(System.in);
    byte line_numb = 0; byte leader_numb = 0;
    String temp_name = ""; int temp_score = 0;
    char save_choice = ('o'); char saveChoiceCaps = ('o'); 
    char loop_choice = ('o'); char loopChoiceCaps = ('Y');
    File saved_scores = new File("Saved_Scores.txt");
    Scanner myReader = new Scanner(saved_scores);
    ArrayList<String> array_names = new ArrayList<String>();
    ArrayList<Integer> array_scores = new ArrayList<Integer>();

    // Adding The File //
    FileWriter write_file = new FileWriter("Saved_Scores.txt", true);
    saved_scores.createNewFile();

    // Getting how many lines there are in file //
    while (myReader.hasNextLine()) {
      if (myReader.hasNextLine()) {
        line_numb++;
      } 
      if (!myReader.hasNextLine()) {
        break;
      }
      myReader.nextLine();
    }
    myReader.close();
    

    // Reading file into the array //
    byte line_count = 0;
    myReader = new Scanner(saved_scores);
    while (myReader.hasNextLine()) {
      for (byte i = 0; i < line_numb; i++) {
        for (byte j = 0; j < 2; j++) {
          if (myReader.hasNextLine()) {
            String file_out = myReader.nextLine();
            line_count++;
            if (line_count % 2 == 0) {
              array_scores.add(Integer.parseInt(file_out));
            }
            else {
              array_names.add(file_out);
            }
          } 
          else {
            break;
          }
        }
      }
    }
    myReader.close();

    while (loopChoiceCaps == 'Y') {

      // Adding a score //
      System.out.println("Do you want to add a score? Y/N");
      save_choice = input.next().charAt(0);
      saveChoiceCaps = Character.toUpperCase(save_choice);
      if (saveChoiceCaps == 'Y') {
        add_method(array_names, array_scores, input);
      }
    
      int score_size = array_scores.size();
      sort_method(array_names, array_scores, score_size);

      // Delete a score //
      System.out.println("Do you want to delete a score? Y/N");
      save_choice = input.next().charAt(0);
      saveChoiceCaps = Character.toUpperCase(save_choice);
      if (saveChoiceCaps == 'Y') {
        delete_method(array_names, array_scores, input, score_size);
      }

      // Search //
      System.out.println("Do you want to search a score? Y/N");
      save_choice = input.next().charAt(0);
      saveChoiceCaps = Character.toUpperCase(save_choice);
      if (saveChoiceCaps == 'Y') {
        search_method(array_names, array_scores, input, score_size);
      }

      // Output //
      System.out.println("Do you want to see the leaderboard alphabetically instead of by rank?");
      save_choice = input.next().charAt(0);
      saveChoiceCaps = Character.toUpperCase(save_choice);
      if (saveChoiceCaps == 'Y') {
        for (int j = 0; j < score_size; j++) {
          for (int i = j + 1; i < score_size; i++) {
            if (array_names.get(i).compareTo(array_names.get(j)) < 0) {
              temp_name = array_names.get(j);
              temp_score = array_scores.get(j);
              array_names.set(j, array_names.get(i));
              array_scores.set(j, array_scores.get(i));
              array_names.set(i, temp_name);
              array_scores.set(i, temp_score);
            }
          }
        }
        for (int i = 0; i < score_size; i++) {
          System.out.println(array_names.get(i) + " - " + array_scores.get(i));
        }
      }
      else {
        leader_numb = 1;
        for (int i = 0; i < 10; i++) {
          if (i == 0) {
            System.out.print("\033[4;31m#" + leader_numb + ": " + array_names.get(i));
            System.out.println(" - " + array_scores.get(i) + "\033[0m");
          }
          else if (i > 0) {
            System.out.print("#" + leader_numb + ": " + array_names.get(i));
            System.out.println(" - " + array_scores.get(i));
          }
          leader_numb++;
        }
      }
    
      // Saving to the file (or choosing not to)//
      System.out.println("Do you want to save? Y/N");
      save_choice = input.next().charAt(0);
      saveChoiceCaps = Character.toUpperCase(save_choice);
      if (saveChoiceCaps == 'Y') {
        write_file = new FileWriter("Saved_Scores.txt", false);
        for (int i = 0; i < score_size-1; i++) {
          write_file.write(array_names.get(i) + "\n");
          write_file.write(array_scores.get(i) + "\n");
        }
      }
      write_file.close();

      System.out.println("Do you want to continue? Y/N");
      loop_choice = input.next().charAt(0);
      loopChoiceCaps = Character.toUpperCase(loop_choice);
    }
  }
}