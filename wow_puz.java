import java.util.ArrayList;


public class wow_puz implements problem
{
	//set up problem
	public wow_puz(int[] input)
	{
		expanded=0;
		start=new int[26];
		for(int i=0; i<25; i++)
		{
			start[i]=input[i];
		}
		//to say where to click
		start[25]=-1;
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
		for(int i=0; i<25; i++)
		{
			if(s.data[i]==1)
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
		/*
		 0  1  2  3  4
		 5  6  7  8  9 
		 10 11 12 13 14
		 15 16 17 18 19
		 20 21 22 23 24
		 */
		
		for(int i=0; i<25; i++)
		{
			state successor_state=new state(s);
			successor_state.data[25]=i;
			if(i==0)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i+1]=(successor_state.data[i+1]+1)%2;
				successor_state.data[i+5]=(successor_state.data[i+5]+1)%2;
			}
			else if(i==4)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i-1]=(successor_state.data[i-1]+1)%2;
				successor_state.data[i+5]=(successor_state.data[i+5]+1)%2;
			}
			else if(i==20)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i+1]=(successor_state.data[i+1]+1)%2;
				successor_state.data[i-5]=(successor_state.data[i-5]+1)%2;
			}
			else if(i==24)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i-1]=(successor_state.data[i-1]+1)%2;
				successor_state.data[i-5]=(successor_state.data[i-5]+1)%2;
			}
			else if(i<4)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i+1]=(successor_state.data[i+1]+1)%2;
				successor_state.data[i-1]=(successor_state.data[i-1]+1)%2;
				successor_state.data[i+5]=(successor_state.data[i+5]+1)%2;
			}
			else if(i>19)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i+1]=(successor_state.data[i+1]+1)%2;
				successor_state.data[i-1]=(successor_state.data[i-1]+1)%2;
				successor_state.data[i-5]=(successor_state.data[i-5]+1)%2;
			}
			else if(i%5==0)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i+1]=(successor_state.data[i+1]+1)%2;
				successor_state.data[i+5]=(successor_state.data[i+5]+1)%2;
				successor_state.data[i-5]=(successor_state.data[i-5]+1)%2;
			}
			else if(i%5==4)
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i-1]=(successor_state.data[i-1]+1)%2;
				successor_state.data[i+5]=(successor_state.data[i+5]+1)%2;
				successor_state.data[i-5]=(successor_state.data[i-5]+1)%2;
			}
			else
			{
				successor_state.data[i]=(successor_state.data[i]+1)%2;
				successor_state.data[i+1]=(successor_state.data[i+1]+1)%2;
				successor_state.data[i-1]=(successor_state.data[i-1]+1)%2;
				successor_state.data[i+5]=(successor_state.data[i+5]+1)%2;
				successor_state.data[i-5]=(successor_state.data[i-5]+1)%2;			
			}
			hold.add(successor_state);
		}
		expanded++;
		return hold;
	}
		
	//print current state
	public void print_state(state s) 
	{
		System.out.println("*******");
		for(int i=0; i<25; i++)
		{
			System.out.print(s.data[i]+" ");
			if(i%5==4)
			{
				System.out.println("");
			}
		}
		System.out.println("click on "+s.data[25]/5+","+s.data[25]%5);
	}

	public int get_expanded()
	{
		return expanded;
	}
		
	//this function calculates the heuristic of a state
	//count the number that are right
	public int calc_heuristic(state s) 
	{
		int heur=0;
		for(int i=0; i<25; i++)
		{
			if(s.data[i]==1)
			{
				heur++;
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
	// not used for this 
	private int heuristic_kind; 
}