import java.util.ArrayList;
//This class is the 8 puzzle problem
//It can print a problem state, initialize the starting state, see if a state is the goal state
//and take in a state and make its successors 
public class puzzle_8 implements problem 
{
	//set up problem
	public puzzle_8(int[] input)
	{
		expanded=0;
		start=new int[10];
		int blank=-1;
		//find the location of the blank
		for(int i=0; i<9; i++)
		{
			start[i]=input[i];
			if(input[i]==0)
			{
				blank=i;
			}
		}
		if(blank!=-1)
		{
			start[9]=blank;
		}
		else
		{
			System.out.println("error no blank in puzzle");
			System.exit(1);
		}
		init();
	}
	public void init() 
	{
		init_state=new state(start);
		init_state.parent=null;
	}
	//see if problem is solved
	public boolean test_goal(state s) 
	{
		/* solved is in
		 * 1,2,3
		 * 4,5,6
		 * 7,8,0
		 */
		//the last piece is not looked at as it does not follow the pattern
		//however if the other pieces are right then the last one must be too
		for(int i=0; i<8; i++)
		{
			if(s.data[i]!=i+1)
			{
				return false;
			}
		}
		return true;
	}
	
	//make ArrayList of successor states
	public ArrayList<state> make_successors(state s) 
	{
		ArrayList<state> hold=new ArrayList<state>();
		int blank=s.data[9];
		int temp_num;
		//try to move up
		if(blank>2)
		{
			//store value that blank will move to then swap
			state successor_state=new state(s);
			temp_num=s.data[blank-3];
			successor_state.data[blank-3]=0;
			successor_state.data[blank]=temp_num;
			//update blank position
			successor_state.data[9]-=3;
			hold.add(successor_state);
		}
		
		//try to move down
		if(blank<6)
		{
			//store value that blank will move to then swap
			state successor_state=new state(s);
			temp_num=s.data[blank+3];
			successor_state.data[blank+3]=0;
			successor_state.data[blank]=temp_num;
			//update blank position
			successor_state.data[9]+=3;
			hold.add(successor_state);
		}
		//try to move right
		if(blank%3!=2)
		{
			//store value that blank will move to then swap
			state successor_state=new state(s);
			temp_num=s.data[blank+1];
			successor_state.data[blank+1]=0;
			successor_state.data[blank]=temp_num;
			//update blank position
			successor_state.data[9]++;
			hold.add(successor_state);
		}
		//try to move left
		if(blank%3!=0)
		{
			//store value that blank will move to then swap
			state successor_state=new state(s);
			temp_num=s.data[blank-1];
			successor_state.data[blank-1]=0;
			successor_state.data[blank]=temp_num;
			//update blank position
			successor_state.data[9]--;
			hold.add(successor_state);
		}
		expanded++;
		return hold;
	}
	
	//print current state
	public void print_state(state s) 
	{
		System.out.println("*******");
		for(int i=0; i<9; i++)
		{
			System.out.print(s.data[i]+" ");
			if(i%3==2)
			{
				System.out.println("");
			}
		}
	}

	public int get_expanded()
	{
		return expanded;
	}
	
	//this function calculates the heuristic of a state
	public int calc_heuristic(state s) 
	{
		int heur=0;
		//for out of place heuristic 
		if(heuristic_kind==0)
		{
			for(int i=0; i<9; i++)
			{
				//see if nonzero piece is where it should be
				if(s.data[i]!=0 && s.data[i]!=i+1)
				{
					heur++;
				}
			}
		}
		//first set is indices second is values of solved 
		/*
		 0 1 2    1 2 3
		 3 4 5    4 5 6
		 6 7 8    7 8 0
		 ////////////////////////////
		 //transform indices by /3
		 0 1 2         0 0 0
		 3 4 5  /3 ->  1 1 1
		 6 7 8         2 2 2
		 ////////////////////////////
		 //transform indices by %3
		 0 1 2         0 1 2
		 3 4 5  %3 ->  0 1 2
		 6 7 8         0 1 2
		 */
		//this is the manhattan distance 
		else
		{
			for(int i=0; i<9; i++)
			{
				int piece=s.data[i];
				int x_offset, y_offset, total;
				if(piece!=0)
				{
					//subtract one to get the index it should be at
					piece--;
					//use absolute value so it is always positive 
					//break into columns and see how far off it is 
					x_offset=java.lang.Math.abs((i%3)-(piece%3));
					//break into rows and see how far off it is
					y_offset=java.lang.Math.abs((i/3)-(piece/3));
					total=x_offset+y_offset;
					heur+=total;
				}
			}
		}
		return heur+s.cost;
	}
	//return the starting state 
	public state get_init_state() 
	{
		return init_state;
	}
	
	public void set_heuristic_kind(int i)
	{
		heuristic_kind=i;
	}
	
	//the starting state
	private state init_state;
	//number of states that have been expanded 
	private int expanded;
	//starting state puzzle configuration 
	private int[] start;
	//0 for out-of-place and 1 for manhattan distance
	private int heuristic_kind; 
	
	//first 9 elements of the array are the values of the pieces at that location
	//the 10th element is the location of the blank, which is represented by 0 	
}
