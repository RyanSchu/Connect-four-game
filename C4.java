import java.util.*;
import java.util.Scanner;
//random computer player
public class C4
{
public static int[][] board = new int[7][7]; //initializes new game board, this is the one where we will be testing for wins
public static String[][] displayBoard = new String[7][7]; //initializes a game board used to render a usable game 	//image for the player's convenience


	public static int[][] resetBoard()
	{
		for(int row = 0; row < board.length; row++)//Constructs the rows of the board
		{
			for(int col = 0; col< board[row].length; col++)//Constructs the columns of the board
			{
				board[row][col] = 0;//all values to 0
				displayBoard[row][col] = "0";//displays 0
			}
		}
		return board;
	}
//This method checks the height of a given column, ie how many non 0 elements it contains
  public static int colHeight(int col)
  	{
  		int num = 0;
  		for(int j = 0; j<7; j++)
  		  {
  			if(displayBoard[j][col].equals("Y") || displayBoard[j][col].equals("X"))
  			{
  				num++;
  			}
  		  }
  		return num;
  	}
	public static void computerPlayerMove() //The easy computer move
	{
		boolean worked = false;
		Random rand = new Random();
		int pos = rand.nextInt(7); //randomizes an integer, which will be the column position

		int start = 0;
		if(board[start][pos] == 0)
		{
			while((start < 6) && (board[start+1][pos] == 0))
			{
				start++; //ensures the game piece is at the bottom of column
			}
			board[start][pos] = -1;
			displayBoard[start][pos] = "Y";
			worked = true;
		}
		else
		{
			while(!worked) //if the entire column is full, the while loop moves the piece //to the next column and starts over again
			{
				pos++;
				if(pos > 6)
				{
					pos = 0;
				}
				computerPlayerMove();
			}
		}
	}
//method copies computerPlayerMove() only with pos changed from random and method call in conditional statement differs
  public static void mediumComputerMove()
  {
    boolean worked = false;
    Random rand = new Random();
    int pos = mediumComputer(); //if win/loss possible within next move returns the column, else random

    int start = 0;
    if(board[start][pos] == 0)
    {
      while((start < 6) && (board[start+1][pos] == 0))
      {
        start++; //ensures the game piece is at the bottom of column
      }
      board[start][pos] = -1;
      displayBoard[start][pos] = "Y";
      worked = true;
    }
    else
    {
      while(!worked)//if the entire column is full, the while loop moves the piece //to the next column and starts over again
      {
        pos++;
        if(pos > 6)
        {
          pos = 0;
        }
        mediumComputerMove();
      }
    }
  }
//how the medium computer picks a column
  public static int mediumComputer()
  {
    Random rand = new Random();
 //returns random in case the below code doesn’t return a specific column
    int pos = rand.nextInt(7);
    ArrayList<Integer> arr = new ArrayList<Integer>();
    ArrayList<Integer> indexArr = new ArrayList<Integer>();

    //adds the sum values of all permutations of 4 on the board to an array
    arr= subArrays();

    if(arr.contains(-3))
    {
    int j = 0;
    while(arr.contains(-3))
    {
      //indexArr now contains the subarray indexes where it is possible for it to win
      Integer three = new Integer(-3);
      indexArr.add(arr.indexOf(-3)+j);
      arr.remove(three);
      //to compensate for changing size of ArrayList
      j++;
    }
  }
  else
  {
  int j = 0;
  while(arr.contains(3))
  {
    //indexArr now contains the subarray indexes  where comp is in danger of losing
    indexArr.add(arr.indexOf(3)+j);
    arr.remove(3);
    //to compensate for changing size of ArrayList
    j++;
  }
  }
//iterate through the indexArr to check each subarray for win/lose conditions
  for(int i=0; i<indexArr.size(); i++)
  {
//indices follow a specific pattern, as such from the index value it is possible to determine the starting coordinates using modulo and Math.floor() operations
    int index = indexArr.get(i);
//first check the row subarrays
    if (index<28)
    {
      int h = pos;
      int r = pos;
      int floor = (int)Math.floor(index/4);
//search for the location of the 0
      for(int q = 0; q<4; q++)
      {
       if (board[floor][(index % 4) + q]==0)
       {
        h = (index % 4) + q;
        r = floor;
       }
     }
      //height check - if height is appropriate comp win/loss is possible and immediately returns column
      if( r == 6 - colHeight(h))
      {
        return h;
      }
    }
    else if (index < 56)
    {
      //columns do not need a height check, return appropriate column
      index = index - 28;
      return (int)Math.floor(index/4);
    }
    else if (index <72)
    {
//check left leaning diagonals
      int h = pos;
      int r = pos;
      index = index - 56;
      int floor = (int)Math.floor(index/4);
//search for 0
      for(int q = 0; q<4; q++)
      {
       if (board[(index % 4) + q][floor + q] == 0)
       {
        h = floor + q;
        r = (index % 4) + q;
       }
     }
//height check - if height is appropriate comp win/loss is possible and immediately returns column
      if(r == 6-colHeight(h))
      {
          return h;
      }
    }
      else if( index < 88)
      {
//check right leaning diagonals
        int h = pos;
        int r = pos;
        index = index - 72;
        int floor = (int)Math.floor(index/4);
//search for 0
        for(int q = 0; q<4; q++)
        {
         if (board[(index % 4) + q][6 - floor - q] == 0)
         {
          h = 6 - floor - q;
          r = (index % 4) + q;
         }
       }
//height check - if height is appropriate comp win/loss is possible and immediately returns subarray
        if(r == 6-colHeight(h))
        {
            return h;
        }
      }

  }
  return pos;
}


  public static int askUser() //asks human player which column they want their game piece to be placed in

	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter an available column: ");
    /*while(!scan.hasNextInt())  //implement once we can quit
    {
      scan.next();
      System.out.println("Not an acceptable input, please input an integer")
    }*/
		int col = scan.nextInt()-1;
//this bit of code makes sure index is within the proper range, and column is not filled
		if(col >7 || col < 0 || (colHeight(col) == 7))
		{
			System.out.println("Not an acceptable input");
			col = askUser();
		}
		return col; //returns position
	}

	public static boolean inputUserMove() //executes player's chosen move

	{
		boolean worked = false;
		int pos = askUser();
		int start = 0;
		if(board[start][pos] == 0)
		{
			while((start < 6) && (board[start+1][pos] == 0)) //places piece at lowest position in //chosen column

			{
				start++;
			}
			board[start][pos] = 1;
			displayBoard[start][pos] = "X";
			worked = true;
			return worked; //returns if the input was valid
		}
		else
		{
			System.out.println("This isn't a valid place dummy");
			return worked;
		}

	}

	public static void printBoard() //prints out the display board so user has visual of board

	{
		for(int i = 0; i< displayBoard.length; i++)
		{
		    for(int j = 0; j< displayBoard[i].length; j++)
		    {
		        System.out.print(displayBoard[i][j] + " ");
		    }
		    System.out.println();
		}
		for(int i =1; i <=7; i++)
		{
			System.out.print(i + " ");
		}
    System.out.println('\n');
	}

public static ArrayList<Integer> subArrays() //checks the game board for a win for either player, idea for logic of //method from a friend outside of class

{
  ArrayList<Integer> arr = new ArrayList<Integer>();
  //add value of all row subarrays
  for(int j = 0; j<7; j++)
  {
    for(int i = 0; i <4; i++)
    {
      arr.add(board[j][i] + board[j][i+1] + board[j][i+2] + board[j][i+3]);
    }
  }
  //add value of all column subarrays
  for(int j = 0; j<7; j++)
  {
    for(int i = 0; i<4; i++)
    {
      arr.add(board[i][j] + board[i+1][j] + board[i+2][j] + board[i+3][j]);
    }
  }
  //add value of left leaning diagonals
  for(int j = 0; j<4; j++)
  {
    for(int i = 0; i<4; i++)
    {
      arr.add(board[i][j] + board[i+1][j+1] + board[i+2][j+2] + board[i+3][j+3]);
    }
  }
  //add value of right leaning diagonals
  for(int j = 6; j>2; j--)
  {
    for(int i = 0; i<4; i++)
    {
      arr.add(board[i][j] + board[i+1][j-1] + board[i+2][j-2] + board[i+3][j-3]);
    }
  }
  return arr;
}
	public static boolean checkBoard()
	{
	  ArrayList<Integer> check = new ArrayList<Integer>();
	 check = subArrays();
	if(Collections.min(check)==-4)//condition for computer victory
	{
		printBoard();
	  System.out.println("Sorry you lose :(");
	  System.out.println("Do you want to play again? Y or N?");
	  Scanner p = new Scanner(System.in);
	  String ans = p.nextLine();
	  if (ans.equals("Y")){  //if yes, start game again
		  resetBoard();
	  }
	  else if(ans.equals("N")){
		  System.out.println("Thanks for Playing!");
		  return true;
	  }
	  else{ //if anything else is enter, game exits
		  return true;
		}
	  return true;
	}
	else if(Collections.max(check) == 4)//condition for player victory
	{
		printBoard();
	  System.out.println("Congrats you win!");
	System.out.println("Do you want to play again? Y or N?");
	Scanner p = new Scanner(System.in);
	String ans = p.nextLine();
	if (ans.equalsIgnoreCase("Y")){ //if yes, resets board
		resetBoard();
	}
	else if(ans.equalsIgnoreCase("N")){// if no, displays message and exits
		System.out.println("Thanks for Playing!");
		return true;
	}
	else{ //if anything else is entered, the game still quits
		return true;
	}

	}
	return false;
}
	public static void main(String[] args)
	{
    boolean flag = true;
    int difficulty;
    Scanner s = new Scanner(System.in);
    System.out.println("Welcome to Connect Four, the game where you try to make four in a row!");
    do{
    System.out.println("Would you like to play against an easy(1) or medium(2) computer?");
    while(!s.hasNextInt())  //implement once we can quit
    {
      s.next();
      System.out.println("Not an acceptable input, please input an integer");
    }
    difficulty = s.nextInt();
    if(difficulty < 0 || difficulty > 2)
    {
      System.out.println("Improper input, please enter a 1 or 2");
    }
  }while(difficulty < 0 || difficulty > 2);
    resetBoard(); //create and print new game board
		printBoard();
		//main game loop

		while(flag){
			inputUserMove(); //takes user input then prints board
			printBoard();
			if(checkBoard()){ //checks if user has four in a row
				flag = false; //stops loop
				break;
			}
      if (difficulty == 2)
      {
			mediumComputerMove();//computer move then prints board
			printBoard();
			if(checkBoard()){ //checks if computer has four in a row
				flag = false; //stops loop
				break;
      }
			}
      else if (difficulty == 1)
      {
        computerPlayerMove();//computer move then prints board
  			printBoard();
  			if(checkBoard()){ //checks if computer has four in a row
  				flag = false; //stops loop
  				break;
      }
    }
		}

	}
}
