import java.util.ArrayList;
//This class is the farmer, wolf, goat, cabbage problem
//It can print a problem state, initialize the starting state, see if a state is the goal state
//and take in a state and make its successors 
public class fwgc implements problem 
{
	//constructor 
	public fwgc()
	{
		expanded=0;
		init();
	}
	//set up problem
	public void init() 
	{
		int[] temp={0,0,0,0};
		init_state= new state(temp);
		init_state.parent=null;
	}
	//if everything is on the east side this is done
	public boolean test_goal(state s) 
	{
		if(s.data[0]==1 && s.data[1]==1 && s.data[2]==1 && s.data[3]==1)
		{
			return true;
		}
		return false;
	}

	//makes an ArrayList of successor states and returns it
	public ArrayList<state> make_successors(state s) 
	{
		ArrayList<state> hold=new ArrayList<state>();
		state successor_state_farmer=new state(s);
		//try to move just farmer
		swap(0, successor_state_farmer);
		if(test_state(successor_state_farmer))
		{
			hold.add(successor_state_farmer);
		}
		
		//try to move farmer and wolf
		if(s.data[0]==s.data[1])
		{
			state successor_state=new state(s);
			swap(0,1, successor_state);
			if(test_state(successor_state))
			{
				hold.add(successor_state);
			}
		}
		//try to move farmer and goat
		if(s.data[0]==s.data[2])
		{
			state successor_state=new state(s);
			swap(0,2, successor_state);
			if(test_state(successor_state))
			{
				hold.add(successor_state);
			}
		}
		//try to move farmer and cabbage
		if(s.data[0]==s.data[3])
		{
			state successor_state=new state(s);
			swap(0,3, successor_state);
			if(test_state(successor_state))
			{
				hold.add(successor_state);
			}
		}
		expanded++;
		return hold;
	}
	//print where everyone is
	public void print_state(state s) 
	{
		System.out.println("");
		System.out.print("West:");
		if(s.data[0]==0)
		{
			System.out.print("man ");
		}
		if(s.data[1]==0)
		{
			System.out.print("wolf ");
		}
		if(s.data[2]==0)
		{
			System.out.print("goat ");
		}
		if(s.data[3]==0)
		{
			System.out.print("cabbage ");
		}
		System.out.print(" East:");
		if(s.data[0]==1)
		{
			System.out.print("man ");
		}
		if(s.data[1]==1)
		{
			System.out.print("wolf ");
		}
		if(s.data[2]==1)
		{
			System.out.print("goat ");
		}
		if(s.data[3]==1)
		{
			System.out.print("cabbage ");
		}
		System.out.print("");
	}
	
	public int get_expanded()
	{
		return expanded;
	}
	
	public int calc_heuristic(state s) 
	{
		//not done for this problem
		return 0;
	}
	//returns the initial state
	public state get_init_state()
	{
		return init_state;
	}
	//see if a successor is legal
	private boolean test_state(state s)
	{
		//wolf eats goat
		if(s.data[1]==s.data[2] && s.data[1]!=s.data[0])
		{
			return false;
		}
		//goat eats cabbage
		if(s.data[2]==s.data[3]&& s.data[2]!=s.data[0])
		{
			return false;
		}
		return true;
	}
	//done to change place of just farmer
	private void swap(int a, state s)
	{
		if(s.data[a]==0)
		{
			s.data[a]=1;
		}
		else
		{
			s.data[a]=0;
		}
	}
	//change place of farmer and something else
	private void swap(int a, int b, state s)
	{
		if(s.data[a]==0)
		{
			s.data[a]=1;
			s.data[b]=1;
		}
		else
		{
			s.data[a]=0;
			s.data[b]=0;
		}
	}
	
	//0 for west side, 1 for east
	//element 0 is farmer, 1 is wolf, 2 is goat, 3 is cabbage, 4 is parent state number in state_history
	//the starting state
	private state init_state;
	//for debugging see how many were expanded
	private int expanded;
}
